# AUTO_API_PETSTORE_SCREENPLAY

Proyecto de automatización de pruebas API para PetStore Swagger, implementado con Serenity BDD y Screenplay Pattern sobre Gradle.

## Objetivo

Validar el ciclo CRUD completo del recurso `user` en la API pública de PetStore mediante un enfoque declarativo en Gherkin y una arquitectura Screenplay orientada a mantenibilidad.

- API: https://petstore.swagger.io/#/
- Base URL: https://petstore.swagger.io/v2

## Stack Tecnológico

| Tecnología | Versión |
|---|---|
| Java | 11 (source/target del proyecto) |
| Gradle Wrapper | 8.13 |
| Serenity BDD | 4.2.12 |
| Serenity Screenplay | 4.2.12 |
| Serenity Screenplay REST | 4.2.12 |
| Serenity Cucumber | 4.2.12 |
| Cucumber | 7.20.1 |
| JUnit | 4.13.2 |

## Arquitectura Screenplay Implementada

- `Models`: encapsulan el request body (`UserModel`).
- `Tasks`: encapsulan acciones HTTP por responsabilidad única.
- `Questions`: extraen información de la última respuesta HTTP.
- `Step Definitions`: orquestan el flujo de negocio del escenario.
- `Runner`: ejecuta Cucumber con Serenity.

## Estructura Actual Del Proyecto

```text
AUTO_API_PETSORE_SCREENPLAY/
├── build.gradle
├── gradle.properties
├── gradlew
├── settings.gradle
├── README.md
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
└── src/
    ├── main/
    │   └── java/
    │       └── co/com/petstore/
    │           ├── endpoints/
    │           │   └── EndpointsUsuario.java
    │           ├── models/
    │           │   └── UserModel.java
    │           ├── questions/
    │           │   ├── ResponseCode.java
    │           │   └── ResponseField.java
    │           ├── runners/
    │           │   └── CucumberWithSerenityRunner.java
    │           ├── stepdefinitions/
    │           │   └── UserCrudStepDefinitions.java
    │           └── tasks/
    │               ├── ActualizarUsuario.java
    │               ├── CrearUsuario.java
    │               ├── EliminarUsuario.java
    │               └── ObtenerUsuario.java
    └── test/
        └── resources/
            ├── serenity.conf
            └── features/
                └── crud_usuario.feature
```

## Cobertura Funcional Del Escenario

El archivo `crud_usuario.feature` utiliza `Esquema del escenario` con tabla `Ejemplos` para ejecutar múltiples datasets del mismo flujo:

1. Registro de usuario.
2. Consulta por username y validación de datos.
3. Actualización de perfil.
4. Nueva consulta y validación de cambios.
5. Eliminación de usuario.
6. Verificación de no existencia (404).

## Configuración

La URL base se define en `src/test/resources/serenity.conf`:

```hocon
serenity {
  project.name = "PetStore API CRUD"
}

environments {
  default {
    restapi.baseurl = "https://petstore.swagger.io/v2"
  }
}
```

## Ejecución

Gradle ya está configurado para usar Java 17 desde `gradle.properties`, por lo que no es necesario exportar `JAVA_HOME` en cada comando.

```bash
./gradlew clean test aggregate
```

## Reporte De Resultados

Serenity genera el reporte HTML en:

```text
target/site/serenity/index.html
```

Para abrirlo en macOS:

```bash
open target/site/serenity/index.html
```
