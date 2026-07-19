FROM maven:3.9.16-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY ./src .

FROM eclipse-temurin:21-jre-jammy
