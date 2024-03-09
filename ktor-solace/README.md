# Ktor Solace Example

This example demonstrates how to connect Ktor with Solace.

1. Launch a local Solace instance by running `docker compose up`.
2. Build the project using the following command:
    * On Linux or Mac: `./gradlew build`
    * On Windows: `.\gradlew.bat build`
3. Start the application by executing `java -jar ./build/libs/ktor-solace-all.jar`.

# Curls

* Send direct message.

```
curl --request POST \
  --url http://localhost:8080/messages
```

* Send persistent message.

```
curl --request POST \
  --url 'http://localhost:8080/messages?send_persistent=true'
```

* The application listens parallel on the queue and logs the message into the terminal.

## Guides

* Solace Container Image: https://docs.solace.com/Software-Broker/SW-Broker-Set-Up/Containers/Set-Up-Container-Image.htm
* Solace Java API: https://docs.solace.com/API/Messaging-APIs/Java-API/java-api-home.htm
* Solace Java examples: https://github.com/SolaceSamples/solace-samples-java
