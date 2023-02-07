# Log

---

### 01/02/2023

Decided on the project scope and decided to use Spring Boot framework for the server. I wanted to stick with MongoDB as a data persistence solution to avoid taking on too much additional learning and because in theory if the migration to a new server system is successful the data persistence layer should be able to be retained as is.

I had no experience in the Spring Boot framework. My first priority was to identify and carry out two tutorials to implement a CRUD REST API and associated unit tests so that I could then apply my learning to my own project.

The tutorials I identified were from BezKoder.com and my implementation of them and details are here: [https://github.com/gibbuk/spring-boot-data-mongodb-tutorial](https://github.com/gibbuk/spring-boot-data-mongodb-tutorial).

---
### 02/02/2023

This day was spent on completing the second tutorial so that I could apply a TDD based approach to when I started implementing on my own project.

I had a good sense by the end of the day that I would be able to translate what I had learnt to be able to implement the functionality fo the previous NodeJS/Express server using Spring Boot.

---
### 03/02/2023
This day I felt confident that I could start to implementn my own project based on the learning.

After carrying out the initial project set up I created a [requirements.md](./requirements.md) document. This was to allow me to document what the frontend expectations in terms of requests and responses it received. 

In the first instance I decided I wanted to implement the GET and POST "/peeps" routes without authentication. I saw this as allowing me to build up the functionality over time. It also allowed me to plan the unit testing strategy I would need.

By the end of the day I had working POST and GET routes and had implemented them using a TDD approach.

Whilst writing tests first slowed me down I liked the confidence it gave me that the code I was writing was acting as intended.

---
### 04/02/2023
The previously existing project uses a less than optimal approach to the authentication of users. User passwords are stored in the clear. Usernames and passwords also need to be repeatedly supplied with POST requests.

In addition to being able to build the existing functionality I wanted if possible to revise the backend to use Json Web Tokens, this would be more aligned with modern security practices.

I used this day to work through a tutorial on the implementation of a JWT authentication system. My implementation of them and details are here: [https://github.com/gibbuk/spring-boot-security-jwt-mongodb-tutorial](https://github.com/gibbuk/spring-boot-security-jwt-mongodb-tutorial)

---
### 06/02/2023
I had completed the tutorial on the use of a JWT but was receiving unauthorized 401 status messages in Postman tests to the server. I spent some time in the morning trying to identify the source of the issue without success.

I realised that my lack of familiarity with the Spring Boot security packages meant that I risked using significant amounts of time on this issue. I decided to refocus back to the core requirement of reproducing the existing API functionality so as to ensure that I would have a working project to demonstrate at a client interview at the end of the week. I hoped that I would have time after completing the core requirements to come back and upgrade the additional functionality later.

I updated the requirements.md to include the /login and /register routes and user authentication for posting to the server. 

---
### 07/02/2023

With the core requirements essentially met my initial priorities for the day were to check the unit tests were all still passing, carry out testing using Postman and also to test running the frontend using the Spring Boot server. I followed this up with refactoring tests, code and updating project documentation.

This led to me confirming that the server was working as expected but that there were some issues:
1. /login route - The React frontend had a bug that means that it would be expecting status code OK responses even when the login was not valid and returned a BAD REQUEST. This meant that message pop-ups informing the user of the results of requests would break. As this issue is wih the frontend I decided that I should prioritise fixing the frontend functionality than attempting to have the server return confusing status codes.
2. My requirements had identified the database id fields in json as being `_id` whereas Spring Boot was supplying `id`. This did not cause the frontend to break but did raise console warnings in browser.

I decided I would fix these both in the frontend, 1. because it is a frontend design issue, 2. because simple fixes server side did not appear to work or resulted in "_id" and "id" being included in responses.

---


