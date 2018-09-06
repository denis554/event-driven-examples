#!/usr/bin/env bash
# bash mvnw clean war:war -DskipTests; bash .mvn/redeploy.sh
container=some-event-driven-app_maven-some-event-driven-app-app_1
docker cp ./target/*.war ${container}:/home/jboss-eap-7.1/jboss-eap-7.1/standalone/deployments/app.war

