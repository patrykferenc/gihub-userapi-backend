
# Github REST API

A reactive REST API written in Java with Spring Boot.
Fetches user's language statistics in all his repositories.

---

# How to get started

There are two ways to run the program: either via IDE (with Gradle) or via standalone archive.

## Running via Gradle task (in IDE)

1. Clone the repository.
2. You can either run it via IDE **(IntelliJ IDEA has built in gradle plugin that lets you do this - just run with the bootRun task)**,
   or you can do it manually, if you navigate to the root of the project and run:
```
$ ./gradlew bootRun
```
3. The application should be running now. Check how it works in the "How it works" section.

## Running the archived application.

1. Download the jar archive.
2. Run it via this command:
```
java -jar githubstatisticsservice-0.0.1-SNAPSHOT.jar
```

Notes: make sure that you use Java 11.

---

# How it works

The application communicates with the GitHub REST API and returns information about user's languages
and some profile information.

You can see statistics about a given user (in this example the user is called 'patrykferenc'):
```
http://localhost:8080/stats/patrykferenc
```

Trying to get information about a user that does not exist, or exceeding the rate limits will return an (TBD: custom) error.

## Example output:

```
{
  "user": {
    "login": "patrykferenc",
    "bio": "Currently studying Applied Computer Science at the Faculty of Electrical Engineering at the WUT.",
    "name": "Patryk Ferenc"
  },
  "repositories": [
    {
      "name": "cyclic-dungeon-generation-model",
      "languageStats": {
        "C#": 71778,
        "Smalltalk": 494
      }
    },
    {
      "name": "tankcell",
      "languageStats": {
        "Java": 89648
      }
    },
    {
      "name": "gitapi-spring",
      "languageStats": {
        "Java": 10625
      }
    },
    {
      "name": "snake-libgdx",
      "languageStats": {
        "Java": 73639,
        "CSS": 1871
      }
    },
    {
      "name": "random-record-retriever",
      "languageStats": {
        "Python": 3817,
        "Makefile": 38
      }
    }
  ],
  "languages": {
    "C#": 71778,
    "Java": 173912,
    "CSS": 1871,
    "Smalltalk": 494,
    "Makefile": 38,
    "Python": 3817
  }
}
```

---

# Constraints, simplifications and additional remarks

TBD: Add token authentication.
The GitHub REST API has a very low limit of requests for unauthenticated users (60/hr).
Therefore, it is relatively easy to exceed it, especially when the user has a lot of repositories,
as every one of them has to be a unique request (info about languages is stored under a different address).
