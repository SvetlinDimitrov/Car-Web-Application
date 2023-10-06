# Car-web-application
Welcome to Car Web Application! In this web application, you can create your own car models, establish brands, and create offers for them.

## Table of Contents
1. [Introduction](#introduction)
2. [Client-Side](#client-side)
3. [Server-Side](#server-side)

## Introduction
In the document I provided, called 'aboutTheProject,' you can find a detailed description of the project. Originally, it was created as an MVC project using the Thymeleaf engine for server-side rendering. However, I renovated it by incorporating React for the client side and transforming the server side into a REST architecture application.
The main purpose was to enhance my front-end and HTTP knowledge.

## Client-Side
- I have used React freamework for my project.
- I have used only the React Router dependency.

### Usage
When you enter the app, you can't do anything except register and login. After successfully logging in, you will be able to see all current models, brands, and offers available. You will also be able to create an offer. The server provides an admin user (username: Ivan, password: 12345) who can create models, brands, offers, edit them, and delete them. He has full control over the app's functionality.

## Server-Side
I am using Java Spring Boot for my server-side development.
- It uses MySQL for database management.
- It uses the REST architectural pattern.
- It uses Spring Security for security and Springdoc-Openapi for documentation.

### API Documentation
- Full Postman documentation is provided [here](https://documenter.getpostman.com/view/26519722/2s9YC7SrVH).
- Swagger documentation is available when you run the API at [url](http://localhost:8080/swagger-ui/index.html).








