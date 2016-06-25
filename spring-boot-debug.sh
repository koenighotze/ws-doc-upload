#!/usr/bin/env bash
MAVEN_OPTS="-agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n" mvn clean spring-boot:run
