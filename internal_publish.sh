#!/bin/bash

./gradlew :integration-library:clean :integration-library:build :integration-library:publishBintrayPublicationToInternalRepository
