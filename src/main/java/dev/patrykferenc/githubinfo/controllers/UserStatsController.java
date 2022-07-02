package dev.patrykferenc.githubinfo.controllers;

import dev.patrykferenc.githubinfo.clients.GithubClient;
import dev.patrykferenc.githubinfo.entities.Repository;
import dev.patrykferenc.githubinfo.entities.UserStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/stats")
public class UserStatsController {

    private final GithubClient githubClient;

    public UserStatsController(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    @GetMapping("/{username}")
    public Mono<UserStatistics> getUserStats(@PathVariable String username) {
        log.debug("Getting user stats for user: {}", username);
        return Mono.zip(
                githubClient.getUser(username),
                githubClient.getRepos(username).flatMap(repository -> {
                    log.debug("Getting language stats for repository: {}", repository.getName());
                    return githubClient.getLanguages(username, repository.getName()).map(lang -> {
                        log.trace("Languages: {}", lang);
                        return new Repository(repository.getName(), lang);
                    });
                }).collectList(),
                UserStatistics::new);
    }

}
