package dev.patrykferenc.githubinfo.clients;

import dev.patrykferenc.githubinfo.model.Repo;
import dev.patrykferenc.githubinfo.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class GithubClient implements GithubUserInfoProvider {

    private static final String GITHUB_API_USER_LINK = "https://api.github.com/users";
    private static final String GITHUB_API_REPO_LINK = "https://api.github.com/repos";

    private static final ParameterizedTypeReference<Map<String, Long>> LANGUAGE_MAP_TYPE = new ParameterizedTypeReference<>() {
    };

    private final WebClient client;

    public GithubClient(WebClient.Builder builder) {
        this.client = builder.build();
    }

    public Mono<User> getUser(String username) {
        return this.client
                .get()
                .uri(String.format("%s/%s", GITHUB_API_USER_LINK, username))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Flux<Repo> getRepos(String username) {
        return this.client
                .get()
                .uri(String.format("%s/%s/repos", GITHUB_API_USER_LINK, username))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Repo.class);
    }

    public Flux<Map<String, Long>> getLanguages(String username, String repository) {
        return this.client
                .get()
                .uri(String.format("%s/%s/%s/languages", GITHUB_API_REPO_LINK, username, repository))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(LANGUAGE_MAP_TYPE);
    }

}
