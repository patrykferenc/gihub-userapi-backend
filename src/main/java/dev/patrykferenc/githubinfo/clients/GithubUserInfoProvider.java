package dev.patrykferenc.githubinfo.clients;

import dev.patrykferenc.githubinfo.model.Repo;
import dev.patrykferenc.githubinfo.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface GithubUserInfoProvider {

    Mono<User> getUser(String username);

    Flux<Repo> getRepos(String username);

    Flux<Map<String, Long>> getLanguages(String username, String repository);

}
