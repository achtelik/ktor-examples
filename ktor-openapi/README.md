# Generate Classes with Openapi Generator

Openapi specification is at `src/main/resources/openapi/documentation.yaml`

**Checkout build.gradle.kts for setup of openapi generator**

1. Execute `./gradlew openApiGenerate`
2. Checkout `src/main/kotlin/it/achtelik/ktorOpenapi/generated/openapi`

## Testing

1. Start application
2. Use `demo.http` for calls against the running server.

## Template extraction

1. Install cli https://openapi-generator.tech/docs/installation
2. `openapi-generator author template -g kotlin-server --library ktor`