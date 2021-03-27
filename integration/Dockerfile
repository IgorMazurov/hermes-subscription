ARG DOCKER_REGISTRY=registry.redhat.io
FROM ${DOCKER_REGISTRY}/redhat-openjdk-18/openjdk18-openshift
COPY target/*.jar integration-hermes-subscription.jar
ENTRYPOINT ["java", "-jar", "integration-hermes-subscription.jar"]
EXPOSE 8080