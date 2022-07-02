package dev.patrykferenc.githubinfo.entities;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Repository {

    private final String name;
    private Map<String, Long> languageStats;

}
