# Branch Application

## Overview
This is Spring Boot REST API with a single endpoint, `/username/{username}`.

It is used to retrieve GitHub user information and repositories using the GitHub API, and join them into
the following structure:

```
{
  "login": "octocat",
  "name": "The Octocat",
  "avatar_url": "https://avatars.githubusercontent.com/u/583231?v=4",
  "location": "San Francisco",
  "email": null,
  "html_url": "https://github.com/octocat",
  "created_at": "2011-01-25T18:44:36Z",
  "repos": [
    ...
  ]
}
```

## Architecture / Design 

## Current state

This application leverages core features of Spring Boot, and it is split into standard layers: Controller, Service, and DTOs.

- The Controller layer exists for users of this application to query.
- The Service layer exists to act as a client to the actual GitHub API and to join the data.
- The DTOs exist to allow us to convert the data from the JSON received by the GitHub API into our own structures. 

The app also leverages recent Java features, such as Records. While these are appropriate for the simple immutable objects 
like the Repos data, a more complex application that transforms data would likely need full-blown classes. 

### 3rd party libraries 

1) Jackson: standard practice for JSON manipulation.

2) Lombok: for minimizing boilerplate (construction, toString, getters, setters, etc). 

### Overkill features

There are certain points of deliberate overkill in this codebase:

1) Adding RestTemplate as a bean was done solely so it could be mocked in tests, which is a questionable practice, but for
   the sake of having tests, this had to be done.

2) There's an interface, which is overkill, because there's only one implementation of it. But we can imagine a future
   scenario in which we want to retrieve this Github info via, say, gRPC instead of just REST, while still leveraging the 
   same method names.

### Future improvements 

Future iterations could be more robust. 

1) For example, the current implementation of @Cacheable leverages the default 
in-memory cache. This could be improved by leveraging Redis.

2) A more robust application would also have a database layer and logging.

3) And I'd add Swagger for automatic API documenting purposes. 

4) It could be dockerized for ease of running cross-platform.

5) If more manipulation were needed, an Object Mapper, instead of simply Jackson annotations, would make sense. 

## Installation

### Prerequisites
- Java 21
- Gradle

### Clone the Repository
```
git clone https://github.com/zenon-was-here/branch.git
```

### Build & Run

`./gradlew clean build`
&& 
`./gradlew bootRun`

Run tests: `./gradlew test`


## Try it out 

Once the app is running, you can test it from a browser, or via cURL.

Request:
`curl -X GET http://localhost:8080/githubuser/octocat`

Response: 
```
{
  "login": "octocat",
  "name": "The Octocat",
  "avatar_url": "https://avatars.githubusercontent.com/u/583231?v=4",
  "location": "San Francisco",
  "email": null,
  "html_url": "https://github.com/octocat",
  "created_at": "2011-01-25T18:44:36Z",
  "repos": [
    ...
  ]
}
```