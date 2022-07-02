package dev.patrykferenc.githubinfo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
public class UserStatistics {

    private User user;
    private List<Repo> repositories;
    private Map<String, Long> languages;

    public UserStatistics(User user, List<Repo> repositories) {
        this.user = user;
        this.repositories = repositories;
        this.languages = generateLanguageStatsFromRepos();
    }

    private Map<String, Long> generateLanguageStatsFromRepos() {
        var stats = new HashMap<String, Long>();
        for (Repo r : repositories) {
            for (String key : r.getLanguageStats().keySet())
                stats.put(key, stats.getOrDefault(key, 0L) + r.getLanguageStats().get(key));
        }
        return stats;
    }

}
