package dev.patrykferenc.githubinfo.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserStatisticsTest {

    @Test
    void shouldGenerateLanguageStats_whenReposAreProvided() {
        // Given
        var languageStats1 = new HashMap<String, Long>();
        languageStats1.put("Java", 100L);
        languageStats1.put("Python", 50L);
        languageStats1.put("C++", 10L);

        var languageStats2 = new HashMap<String, Long>();
        languageStats2.put("Java", 10L);
        languageStats2.put("Python", 5L);

        var languageStats3 = new HashMap<String, Long>();
        languageStats3.put("Java", 40L);

        var repos = List.of(
                new Repo("test-repo-1", languageStats1),
                new Repo("test-repo-2", languageStats2),
                new Repo("test-repo-3", languageStats3)
        );
        // When
        var userStatistics = new UserStatistics(new User("test-user", "interesting bio...", "Mr Test"), repos);

        // Then
        var expectedLanguageStats = new HashMap<String, Long>();
        expectedLanguageStats.put("Java", 150L);
        expectedLanguageStats.put("Python", 55L);
        expectedLanguageStats.put("C++", 10L);

        assertEquals(expectedLanguageStats, userStatistics.getLanguages());
    }

}