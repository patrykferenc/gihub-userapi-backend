package dev.patrykferenc.githubinfo.handlers;

import dev.patrykferenc.githubinfo.clients.GithubUserStatisticsRetriever;
import dev.patrykferenc.githubinfo.model.Repo;
import dev.patrykferenc.githubinfo.model.UserStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
public class UserStatisticsHandler {

    private final GithubUserStatisticsRetriever retriever;

    public UserStatisticsHandler(GithubUserStatisticsRetriever retriever) {
        this.retriever = retriever;
    }

    public Mono<ServerResponse> getUserStatistics(ServerRequest request) {
        var username = request.pathVariable("username");
        log.debug("Getting user stats for user: '{}'...", username);

        var userStatistics = getUserStatisticsMono(username);
        log.debug("Getting user stats successful.");

        return ServerResponse
                .ok()
                .contentType(APPLICATION_JSON)
                .body(userStatistics, UserStatistics.class);
    }

    private Mono<UserStatistics> getUserStatisticsMono(String username) {
        return Mono.zip(
                retriever.getUser(username),
                retriever.getRepos(username).flatMap(repository -> {
                    log.debug("Getting language stats for repository: '{}'...", repository.getName());
                    return retriever.getLanguages(username, repository.getName()).map(lang -> {
                        log.debug("Getting language stats successful.");
                        return new Repo(repository.getName(), lang);
                    });
                }).collectList(),
                UserStatistics::new);
    }

}
