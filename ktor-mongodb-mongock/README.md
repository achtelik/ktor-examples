# Ktor MongoDB Mongock Example

This example demonstrates how to connect Ktor, MongoDB, and Mongock in a reactive fashion.

1. Launch a local MongoDB instance by running `docker compose up`.
2. Build the project using the following command:
    * On Linux or Mac: `./gradlew build`
    * On Windows: `.\gradlew.bat build`
3. Start the application by executing `java -jar ./build/libs/ktor-mongodb-mongock-all.jar`.

## Guides
* MongoDB coroutine driver: https://www.mongodb.com/docs/drivers/kotlin/coroutine/current/quick-start/
* Mongock reactive driver: https://docs.mongock.io/v5/driver/mongodb-reactive/
