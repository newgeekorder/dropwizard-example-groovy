# A Simple Dropwizard + Groovy Example
This code base demonstrates how simple it is to build a Dropwizard service containing both Java and Groovy sources.

It makes use of the _groovy-eclipse-compiler_ to handle cross-compilation duties.

This project is the supporting artifact for a [blog post](http://littlesquare.com/2013/01/combining-heroku-and-dropwizard-my-own-personal-staging-environment/) on using Dropwizard and Heroku together as a personal staging environment.

#### What It Contains

* Simple HTML View (Freemarker Template-based)
* RESTful Endpoint (Jersey)

These are very trivial examples, the project has a database connection wired up but it's not used. It merely exists to demonstrate Liquibase database migrations.

Do not let the simplicity of the code scare you off, Dropwizard is merely a collection of best of breed components from the Java ecosystem. These components, as well as Dropwizard, have been successfully used to power highly scalable web services at Yammer and beyond.

#### Getting Started

    mvn clean install
    java -jar my-example-service/target/my-example-service-0.1-SNAPSHOT.jar server my-example-service/config.yml
    
    open http://localhost:8080/ (HTML View)
    open http://localhost:8080/api/persons/ (RESTful Endpoint)
    
#### Deploying to Heroku

[See Blog Post](http://littlesquare.com/2013/01/combining-heroku-and-dropwizard-my-own-personal-staging-environment/)
