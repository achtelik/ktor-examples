# Model Generation from OpenAPI Spec

The project
uses [OpenAPI Generator's Kotlin-Server generator](https://openapi-generator.tech/docs/generators/kotlin-server/)
to generate the models and paths from `openapi.yaml`.

The code is generated whenever the build is started, but if you doubt that your changes to `openapi.yaml` aren't
reflected, then use `./gradlew openApiGenerate` to trigger the build.

## annotateOpenApiModels

This custom task post-processes the code generated by the "openApiGenerate" task.

It does the following:

* Imports `@Serializable` and applies it to data classes.
* Applies `@Contextual` to binary parameters (files) in the generated `Paths` file.
* Removes the `xCountry` header parameter from all operations in `Paths`.