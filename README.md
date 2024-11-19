# Gestión de Proyectos - API REST

**Ciclo Formativo:** Desarrollo de Aplicaciones Web (DAW)  
**Alumno:** Miguel Ángel Reyes Méndez

---

## Índice

1. [Introducción](#introducción)
2. [Funcionalidades](#funcionalidades)
3. [Guía de Instalación](#guía-de-instalación)
4. [Guía de Uso](#guía-de-uso)
5. [Enlace a Figma](#enlace-a-figma)
6. [Conclusion](#conclusion)
7. [Contribuciones y Agradecimientos](#contribuciones-y-agradecimientos)
8. [Licencias](#licencias)
9. [Contacto](#contacto)

---

## Introducción

Esta API REST permite gestionar la información de diferentes proyectos mediante un conjunto de endpoints que permiten realizar operaciones CRUD (crear, leer, actualizar y eliminar) sobre proyectos, programadores y tecnologías. El objetivo es proporcionar una herramienta sencilla para la administración de proyectos y facilitar la consulta de información de manera paginada.

### Objetivos

- Implementación Controlador-Servicio-Repositorio.
- Proporcionar una respuesta adecuada mediante `ResponseEntity`.
- Aplicar buenas prácticas de programación, incluyendo comentarios y manejo de errores.

---

## Funcionalidades

1. **Gestión de proyectos**:
   - Obtener todos los proyectos paginados.
   - Buscar proyectos por nombre.
   - Insertar, editar y eliminar proyectos.

2. **Gestión de programadores**:
   - Insertar y eliminar programadores.

3. **Gestión de tecnologías**:
   - Insertar y eliminar tecnologías.

4. **Endpoints adicionales**:
   - Cambiar estado de proyecto (a Testing y a Production).
   - Establecer la relación de tecnologías y programadores en proyectos.
   - Consultar proyectos según tecnología.

### Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Swagger (SpringDoc para documentación de la API)
- Sistema de Control de Versiones: GIT

---

## Guía de Instalación

El proyecto ha sido creado desde cero a través del entorno de desarrollo Eclipse. A continuación se detallan los pasos para crear y configurar el proyecto Spring Boot:

1. **Crear un nuevo proyecto Spring en Eclipse**:
   - Abre Eclipse y ve a `File > New > Spring Starter Project`.
   - En la ventana de creación, completa los siguientes campos:
     - **Name**: Asigna un nombre al proyecto.
     - **Type**: Selecciona `Maven Project`.
     - **Packaging**: Selecciona `Jar`.
     - **Java Version**: Selecciona `17` para asegurarte de que es compatible con Java 17.
   - Haz clic en `Next`.

2. **Configurar las dependencias de Spring Boot**:
   - En la sección de dependencias, selecciona las siguientes:
     - **Spring Web**: Para desarrollar la API REST.
     - **Spring Data JPA**: Para la gestión de bases de datos con JPA.
     - **SpringDoc OpenAPI**: Para generar la documentación de la API con Swagger.
   - Haz clic en `Finish` para crear el proyecto.

3. **Configurar el archivo `application.properties`**:
   - En el archivo `src/main/resources/application.properties`, configura las propiedades necesarias para la conexión a la base de datos y otras configuraciones del proyecto. Aquí tienes un ejemplo de configuración:
     ```properties
     spring.datasource.url=jdbc:h2:mem:testdb
     spring.datasource.driverClassName=org.h2.Driver
     spring.datasource.username=sa
     spring.datasource.password=
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     ```

4. **Compilar y ejecutar el proyecto**:
   - Para compilar el proyecto, haz clic derecho sobre el proyecto en el explorador de Eclipse y selecciona `Run As > Maven install`.
   - Para ejecutar el proyecto, selecciona `Run As > Spring Boot App`. Esto iniciará el servidor y la API estará disponible en `http://localhost:8080`.

---

Con esta configuración, deberías tener el proyecto Spring Boot listo para usar. Puedes realizar pruebas de los endpoints y consultar la documentación en Swagger, accediendo a `http://localhost:8080/swagger-ui.html` (o en la ruta configurada para Swagger).

---

## Guía de Uso

La API REST permite gestionar proyectos, programadores y tecnologías mediante peticiones HTTP. A continuación se describen los principales endpoints disponibles:

- **Proyectos**:
  - `GET /api/v1/projects`: Obtiene todos los proyectos de forma paginada.
  - `GET /api/v1/projects/{word}`: Busca proyectos cuyo nombre contenga una palabra específica.
  - `POST /api/v1/projects`: Crea un nuevo proyecto.
  - `PUT /api/v1/projects/{id}`: Edita un proyecto existente.
  - `DELETE /api/v1/projects/{id}`: Elimina un proyecto.

- **Programadores**:
  - `POST /api/v1/developers`: Añade un nuevo programador.
  - `DELETE /api/v1/developers`: Elimina un programador.

- **Tecnologías**:
  - `POST /api/v1/technologies`: Añade una nueva tecnología.
  - `DELETE /api/v1/technologies`: Elimina una tecnología.

- **Funciones adicionales**:
  - `POST /api/v1/technologies/used`: Asocia una tecnología a un proyecto.
  - `POST /api/v1/developers/worked`: Asocia un programador a un proyecto.
  - `GET /api/v1/projects/tec/{tech}`: Obtiene proyectos que utilizan una tecnología específica.
  - `PATCH /api/v1/projects/totesting`: Cambia el estado de un proyecto a Testing.
  - `PATCH /api/v1/projects/toprod`: Cambia el estado de un proyecto a Production.

Para más detalles sobre cada endpoint y sus parámetros, consulta la documentación en Swagger, que proporciona ejemplos y especificaciones adicionales.

---

## Enlace a Figma

[]() 

---

## Conclusion

Este proyecto de API REST proporciona una solución completa para la gestión de proyectos, facilitando la administración de información sobre proyectos, programadores y tecnologías. Con una arquitectura bien estructurada, uso de buenas prácticas y documentación clara, esta API está diseñada para ser fácil de usar y mantener.

---

## Contribuciones y Agradecimientos

Agradecimientos al profesor Joaquín Borrego Fernández, mi pareja Ana Belén Pavón y compañeros por su apoyo y guía durante el desarrollo de este proyecto.

---

## Licencias

Este proyecto está licenciado bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.

---

## Contacto

**Correo:** miguelanreyesm@gmail.com  
**GitHub:** [Mi perfil de GitHub](https://github.com/migreydev)
---

