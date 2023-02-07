# Java Server Chitter

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-the-project">About The Project</a></li>
    <li><a href="#built-with">Built With</a></li>
    <li><a href="#getting-started">Getting Started</a></li>
    <li><a href="#problem-statements">Server Architecture</a></li>
    <li><a href="#project-review-and-roadmap">Project Review and Roadmap</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

---

## About The Project

This product is a `Java` and `Spring Boot` implementation of the backend server for my previous [Chitter project](https://github.com/gibbuk/chitter-challenge-public). The previous project used `Node` as the server layer. 

I wanted to carry out this project because:
- I wanted to learn the Java Spring Boot framework
- I wanted to learn to construct unit tests in Java

I thought this would be a really interesting learning experience to go back to previously working solution and carrying out the equivalent of a very significant migration from one server framework to the other. It would help me compare and contrast the similiarities between frameworks on different platforms and let me reevaluate previous design decisions that I made in the past as a less experienced developer. 

Reflections on my development process can be found in [this log document](Log.md).

Problem statements in the form of user stories and the breakdown of requirements can be found summarised in [requirements.md](requirements.md).

---

## Built With

Built in `java 11`  using `Spring Boot 2.6.3` and `Maven`. 

`MongoDB` is used as the data persistence layer.

## Getting Started

1. clone this repo.
2. Install dependencies via Maven (e.g. `mvn clean dependency:copy-dependencies`).
3. Unit tests can be run with `mvn test`.
4. Ensure you have MongoDB running on `localhost:27012`.
5. `mvn spring-boot:run` to run the server.
6. If you also want the front end you will need to clone [Chitter project](https://github.com/gibbuk/chitter-challenge-public) and follow the instructions for running the client.


---
## Server Architecture

![server architecture image](./images/server-architecture.png)

The Spring Boot server receives HTTP requests via REST controllers. These determine the action to be taken determined on the type of request and payload received. In order to access documents from the database controllers communicate with repositories which are interfaces for interacting with the MongoDB instance. Models define the document contents stored in the database and also which collection the documents are stored in. E.g. a `User` model defines user data that is stored in the `users` collection of the MongoDB database.

These components are sitting within the Spring Boot framework and rely on functionality provided Spring Boot. For example annotations like `@Autowired` to connect instances of our components together for us or using `application.properties` to define properties to be used across the server as a whole.

---

## Project Review and Roadmap




---

## Acknowledgments

