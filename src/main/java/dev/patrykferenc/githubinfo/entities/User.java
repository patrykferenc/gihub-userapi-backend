package dev.patrykferenc.githubinfo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {

    private final String login;
    private final String bio;
    private final String name;

}
