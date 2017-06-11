FROM selenium/standalone-chrome

ENV WORKING_DIR=/usr/local/test-runner \
    GRADLE_CMD=""

COPY resources/docker-entrypoint.sh docker-entrypoint.sh
COPY gradle ${WORKING_DIR}/gradle
COPY src ${WORKING_DIR}/src
COPY build.gradle ${WORKING_DIR}
COPY gradlew ${WORKING_DIR}

RUN sudo apt-get clean \
    && sudo apt-key update \
    && sudo apt-get -y update \
    && sudo apt-get -y install xvfb \
    && sudo chown seluser:seluser -R ${WORKING_DIR}

ENTRYPOINT ["/docker-entrypoint.sh"]
CMD ./gradlew ${GRADLE_CMD}