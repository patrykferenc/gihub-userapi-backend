package dev.patrykferenc.githubinfo.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Repo {

    private final String name;
    private Map<String, Long> languageStats;

}
