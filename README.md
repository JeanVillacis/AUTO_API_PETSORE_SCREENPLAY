# AUTO_API_PETSTORE_SCREENPLAY

Proyecto de automatización de pruebas sobre la API REST pública de **PetStore**, utilizando el patrón de diseño **Screenplay** junto con **Serenity BDD**, **Cucumber** y **Gradle**.

---

## API bajo prueba

**PetStore Swagger API**
- Documentación y exploración interactiva: [https://petstore.swagger.io/#/](https://petstore.swagger.io/#/)
- URL base de los endpoints: `https://petstore.swagger.io/v2`

Esta API pública simula un sistema de gestión de una tienda de mascotas. Expone recursos como `user`, `pet` y `store`, cada uno con operaciones CRUD completas. En este proyecto se automatizan las operaciones del recurso **user**.

---

## Tecnologías utilizadas

| Tecnología | Versión | Rol en el proyecto |
|---|---|---|
| Java | 21 | Lenguaje de programación |
| Gradle | 8.13 | Gestión de dependencias y construcción |
| Serenity BDD | 4.2.12 | Framework de reportes y orquestación de pruebas |
| Serenity Screenplay | 4.2.12 | Patrón de diseño para las pruebas |
| Serenity REST | 4.2.12 | Integración con RestAssured para llamadas HTTP |
| Cucumber | 7.20.1 | Motor de escenarios BDD en lenguaje Gherkin |
| JUnit 4 | 4.13.2 | Mecanismo de arranque que habilita el uso de `@RunWith` |
| CucumberWithSerenity | 4.2.12 | Cucumber Test Runner integrado con Serenity |
| Lombok | 1.18.36 | Reducción de código boilerplate en modelos |

---

## Patrón de diseño: Screenplay REST

El proyecto implementa el patrón **Screenplay REST**, que organiza las pruebas en torno a tres conceptos:

- **Actor**: representa al usuario que ejecuta las acciones (en este caso, el actor `"Usuario"`).
- **Tasks (Tareas)**: acciones de alto nivel que el actor realiza, como `CrearUsuario`, `ObtenerUsuario`, `ActualizarUsuario` y `EliminarUsuario`.
- **Questions (Preguntas)**: permiten al actor consultar el estado del sistema para verificar resultados, como `LaRespuesta` que extrae el código HTTP y campos del JSON de respuesta.

Este patrón favorece la legibilidad, la reutilización de código y el mantenimiento a largo plazo.

---

## Estructura del proyecto

```
AUTO_API_PETSORE_SCREENPLAY/
│
├── build.gradle                          Dependencias y configuración de Gradle
├── settings.gradle                       Nombre del proyecto
├── gradle.properties                     Propiedades del daemon de Gradle
│
└── src/
    ├── main/
    │   └── java/co/com/petstore/
    │       ├── endpoints/
    │       │   └── EndpointsUsuario.java  Constantes con los paths de la API
    │       ├── models/
    │       │   └── UserModel.java         POJO que representa un usuario
    │       ├── questions/
    │       │   └── LaRespuesta.java       Pregunta que extrae datos de la última respuesta HTTP
    │       └── tasks/
    │           ├── CrearUsuario.java      Tarea: POST /user
    │           ├── ObtenerUsuario.java    Tarea: GET /user/{username}
    │           ├── ActualizarUsuario.java Tarea: PUT /user/{username}
    │           └── EliminarUsuario.java   Tarea: DELETE /user/{username}
    │
    └── test/
        ├── java/co/com/petstore/
        │   ├── runners/
        │   │   └── CucumberWithSerenityRunner.java   Punto de entrada de las pruebas
        │   └── stepdefinitions/
        │       └── DefinicionDePasosCrudUsuario.java  Glue entre Gherkin y Screenplay
        │
        └── resources/
            ├── serenity.conf              URL base y configuración de Serenity
            └── features/
                └── crud_usuario.feature   Escenarios de prueba en Gherkin
```

---

## Escenario automatizado

El archivo `crud_usuario.feature` cubre el ciclo de vida completo (**CRUD**) de un usuario:

| Paso | Operación | Endpoint | Verificación |
|---|---|---|---|
| 1 | Crear usuario | `POST /user` | HTTP 200 |
| 2 | Leer usuario | `GET /user/{username}` | HTTP 200 + datos correctos |
| 3 | Actualizar usuario | `PUT /user/{username}` | HTTP 200 |
| 4 | Leer usuario actualizado | `GET /user/{username}` | HTTP 200 + datos nuevos |
| 5 | Eliminar usuario | `DELETE /user/{username}` | HTTP 200 |
| 6 | Verificar eliminación | `GET /user/{username}` | HTTP 404 |

---

## Configuración

La URL base de la API se configura en `src/test/resources/serenity.conf`:

```
serenity {
  project.name = "PetStore API CRUD"
}

webdriver {
  driver = provided
}

environments {
  default {
    restapi.baseurl = "https://petstore.swagger.io/v2"
  }
}
```

Este valor es leído dinámicamente por la clase `DefinicionDePasosCrudUsuario` usando `EnvironmentSpecificConfiguration`, lo que permite cambiar el entorno sin modificar código Java.

---

## Ejecución

**Prerrequisito:** Java 21 instalado.

Ejecutar todas las pruebas y generar el reporte:

```bash
JAVA_HOME=/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home ./gradlew clean test aggregate
```

Abrir el reporte de resultados en el navegador:

```bash
open target/site/serenity/index.html
```

---

## Reportes

Serenity genera un reporte HTML detallado en:

```
target/site/serenity/index.html
```

El reporte incluye:
- Resultado de cada escenario (pasado / fallido)
- Detalle de cada paso ejecutado con su descripción legible
- Evidencia de las llamadas HTTP realizadas (request y response)
- Estadísticas generales de la ejecución
