
# Microservicio de Autenticación y Notas

Este microservicio gestiona la autenticación de usuarios y el registro, así como la creación y consulta de notas asociadas a esos usuarios.

## Funcionalidades

### 1. **Autenticación**
- **Registro de usuario**: Permite a los usuarios registrarse.
- **Inicio de sesión**: Permite a los usuarios iniciar sesión y obtener un JWT para autenticarse en futuras solicitudes.

### 2. **Notas**
- **Crear una nota**: Permite a los usuarios registrados crear notas.
- **Obtener todas las notas**: Permite a los usuarios registrados obtener todas sus notas almacenadas.

## Endpoints

### Autenticación

#### 1. **POST /api/v1/auth/signup** - Registro de Usuario
Registra a un nuevo usuario.

- **Request body**:
  ```json
  {
    "firstName": "Juan",
    "lastName": "Pérez",
    "email": "juan@example.com",
    "password": "password123"
  }
  ```

- **Response (Success)**:
  ```json
  {
      "code": 0,
      "message": "OK",
      "data": {
          "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvcmxhbmRvLmNhbWF2aWxjYUBnbWFpbC5jb20iLCJpYXQiOjE3MzcxMjg3MzksImV4cCI6MTczNzEzMDE3OX0.1t9vq0NS5iOdAcIthwbz4Wn4Z19o8iktkQU-M92FCno"
      }
  }
  ```

- **Responses**:
  - `200 OK`: Usuario registrado correctamente, token retornado.
  - `404 Not Found`: El recurso solicitado no fue encontrado.
  - `500 Internal Server Error`: Error en el servidor.

#### 2. **POST /api/v1/auth/signin** - Inicio de sesión
Inicia sesión con un usuario registrado y devuelve un token JWT.

- **Request body**:
  ```json
  {
    "email": "juan@example.com",
    "password": "password123"
  }
  ```

- **Response (Success)**:
  ```json
  {
      "code": 0,
      "message": "OK",
      "data": {
          "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvcmxhbmRvLmNhbWF2aWxjYUBnbWFpbC5jb20iLCJpYXQiOjE3MzcxMjg3NDQsImV4cCI6MTczNzEzMDE4NH0.sQAXTFrEyzrQwexXvQgV2RG7abezIOjV-cy5Op7B4Ds"
      }
  }
  ```

- **Response (Error)**:
  ```json
  {
      "code": -1,
      "message": "Credenciales incorrectas. Por favor, verifica tu correo y contraseña."
  }
  ```

- **Response (Error 404)**:
  ```json
  {
    "code": -1,
    "message": "El recurso solicitado no fue encontrado - URL: /utp/api/v1/auth/signins"
  }
  ```

- **Responses**:
  - `200 OK`: Autenticación exitosa, retorna un JWT.
  - `404 Not Found`: El recurso solicitado no fue encontrado.
  - `500 Internal Server Error`: Error en el servidor.

### Notas

#### 1. **POST /api/v1/note** - Crear una Nota
Crea una nueva nota para el usuario autenticado.

- **Request body**:
  ```json
  {
    "note": "Nota de ejemplo",
    "content": "Contenido de la nota."
  }
  ```

- **Headers**:
  - `Authorization`: Token JWT del usuario.

- **Response (Success)**:
  ```json
  {
      "code": 0,
      "message": "OK"
  }
  ```
- **Response (Error 400)**:
  ```json
  {
    "code": -1,
    "message": "El campo note es requerido"
  }
  ```
- **Response (Error 401)**:
  ```json
  {
    "code": -1,
    "message": "No estás autenticado. Por favor, inicia sesión."
  }
  ```
  - **Response (Error 404)**:
  ```json
  {
    "code": -1,
    "message": "El recurso solicitado no fue encontrado - URL: /utp/api/v1/note"
  }
  ```

- **Responses**:
  - `201 Created`: Nota creada exitosamente.
  - `400 Bad Request`: Solicitud incorrecta.
  - `401 Unauthorized`: No estás autenticado.
  - `404 Not Found`: El recurso solicitado no fue encontrado.
  - `500 Internal Server Error`: Error en el servidor.

#### 2. **GET /api/v1/note** - Obtener Notas
Obtiene todas las notas del usuario autenticado.

- **Headers**:
  - `Authorization`: Token JWT del usuario.

- **Response (Success)**:
  ```json
  {
      "code": 0,
      "message": "OK",
      "data": [
          {
              "note": "16",
              "content": "Lenguaje"
          }
      ]
  }
  ```
  - **Response (Error 404)**:
  ```json
  {
    "code": -1,
    "message": "El recurso solicitado no fue encontrado - URL: /utp/api/v1/note"
  }
  ```

- **Responses**:
  - `200 OK`: Lista de notas del usuario.
  - `401 Unauthorized`: No estás autenticado.
  - `404 Not Found`: El recurso solicitado no fue encontrado.
  - `500 Internal Server Error`: Error en el servidor.

## Modelo de Datos

### **Usuario (`User`)**

Representa a un usuario registrado en el sistema.

- **Campos**:
  - `id`: Identificador único del usuario.
  - `firstName`: Primer nombre del usuario.
  - `lastName`: Apellido del usuario.
  - `email`: Correo electrónico del usuario (único).
  - `password`: Contraseña del usuario.
  - `role`: Rol del usuario (por ejemplo, `USER` o `ADMIN`).
  - `createdAt`: Fecha de creación del usuario.
  - `updatedAt`: Fecha de última actualización del usuario.

### **Nota (`Note`)**

Representa una nota creada por un usuario.

- **Campos**:
  - `id`: Identificador único de la nota.
  - `note`: Título de la nota.
  - `content`: Contenido de la nota.
  - `user`: Usuario asociado a la nota.
  - `createdAt`: Fecha de creación de la nota.
  - `updatedAt`: Fecha de última actualización de la nota.

---


## Diagrama del Árbol de Clases y Estructura de Carpetas

Este diagrama representa la organización de las clases principales y la estructura de carpetas de nuestro microservicio. Es útil para comprender la arquitectura interna del sistema y cómo las distintas clases interactúan entre sí.

### Diagrama del Árbol de Clases

A continuación se muestra el diagrama del árbol de clases:

```
  ├───main
  │   ├───java
  │   │   └───com
  │   │       └───utp
  │   │           └───note
  │   │               │   NoteApplication.java
  │   │               │
  │   │               ├───config
  │   │               │       JwtAuthenticationFilter.java
  │   │               │       ModelMapperConfig.java
  │   │               │       OpenAPIConfig.java
  │   │               │       SecurityConfiguration.java
  │   │               │
  │   │               ├───constant
  │   │               │       Constant.java
  │   │               │       Role.java
  │   │               │
  │   │               ├───controller
  │   │               │       AuthenticationController.java
  │   │               │       NoteController.java
  │   │               │
  │   │               ├───domain
  │   │               │       Note.java
  │   │               │       User.java
  │   │               │
  │   │               ├───exception
  │   │               │       CustomAuthenticationEntryPoint.java
  │   │               │       ResponseExceptionHandler.java
  │   │               │
  │   │               ├───helper
  │   │               │       BaseResponse.java
  │   │               │       ResponseClient.java
  │   │               │       ResponseClientList.java
  │   │               │
  │   │               ├───model
  │   │               │   ├───request
  │   │               │   │       NoteRequest.java
  │   │               │   │       SignInRequest.java
  │   │               │   │       SignUpRequest.java
  │   │               │   │
  │   │               │   └───response
  │   │               │           JwtAuthenticationResponse.java
  │   │               │           NoteResponse.java
  │   │               │
  │   │               ├───repository
  │   │               │       NoteRepository.java
  │   │               │       UserRepository.java
  │   │               │
  │   │               └───service
  │   │                   │   AuthenticationService.java
  │   │                   │   JwtService.java
  │   │                   │   NoteService.java
  │   │                   │   UserService.java
  │   │                   │
  │   │                   └───impl
  │   │                           AuthenticationServiceImpl.java
  │   │                           JwtServiceImpl.java
  │   │                           NoteServiceImpl.java
  │   │                           UserServiceImpl.java
```


### Estructura de Carpetas

La estructura de carpetas del proyecto sigue una convención organizada para facilitar su mantenimiento y comprensión:

- **config/**: Contiene las configuraciones necesarias para la aplicación, como la seguridad (filtros JWT), mapeo de modelos, y la configuración de OpenAPI para la documentación de la API.
- **constant/**: Contiene las constantes utilizadas a lo largo del proyecto, como los roles de los usuarios y otras constantes generales.
- **controller/**: Aquí se encuentran los controladores REST que manejan las solicitudes HTTP de la API, como la autenticación y la gestión de notas.
- **domain/**: Define las entidades principales del sistema, como `Note` y `User`, que representan los objetos que se persistirán en la base de datos.
- **exception/**: Manejo de excepciones, incluyendo puntos de entrada de autenticación personalizados y un controlador global para errores.
- **helper/**: Contiene clases auxiliares para estructurar las respuestas de las API.
- **model/**: Incluye los modelos de solicitud y respuesta que se intercambian entre el cliente y el servidor a través de la API.
- **repository/**: Define los repositorios para interactuar con la base de datos, implementando la persistencia de `Note` y `User`.
- **service/**: Contiene los servicios que implementan la lógica de negocio del sistema, como la autenticación y la gestión de notas.
- **impl/**: Implementaciones de los servicios, proporcionando la lógica de negocio para la autenticación, notas, y usuarios.


## Instalación

Para ejecutar este microservicio, sigue estos pasos:

1. Clona este repositorio.
2. Asegúrate de tener Java 17 y Maven instalados.
3. Ejecuta el comando `mvn spring-boot:run` para iniciar el servidor.

El servicio estará disponible en `http://localhost:8080`.
Puede visualizar y probar los servicios `http://localhost:8080/utp/swagger-ui/index.html`.



# Guía para desplegar el microservicio con Docker

## 1. Construir la imagen Docker

Con el archivo `Dockerfile` listo, ahora puedes construir la imagen Docker.

1. Abre una terminal o línea de comandos.
2. Navega al directorio donde está el archivo `Dockerfile` (la raíz del proyecto).
3. Ejecuta el siguiente comando para construir la imagen Docker:

```bash
docker build -t upt-note .
```

Este comando leerá el `Dockerfile` y generará una imagen llamada `upt-note`.

## 2. Ejecutar el contenedor Docker

Una vez que la imagen esté construida, puedes ejecutar un contenedor a partir de ella.

1. Usa el siguiente comando para ejecutar el contenedor:

```bash
docker run -d -p 8080:8080 --name upt-note-container upt-note
```

Este comando hará lo siguiente:
- `-d`: Ejecuta el contenedor en segundo plano (detached mode).
- `-p 8080:8080`: Mapea el puerto 8080 de tu máquina local al puerto 8080 del contenedor.
- `--name upt-note-container`: Asigna un nombre al contenedor.
- `upt-note`: Es el nombre de la imagen que construiste.

## 3. Verificar que el contenedor está funcionando

Para verificar que el contenedor está corriendo correctamente, usa el siguiente comando:

```bash
docker ps
```

Esto debería mostrar una lista de contenedores en ejecución. Asegúrate de que tu contenedor `upt-note-container` esté en la lista.

## 4. Acceder a tu aplicación

Ahora puedes acceder a tu aplicación en `http://localhost:8080/utp` desde el navegador. La consola de H2 debería estar disponible en `http://localhost:8080/utp/h2-console` (si la has habilitado en tu configuración de Spring).

## 5. Detener el contenedor

Cuando termines de trabajar con tu contenedor, puedes detenerlo con el siguiente comando:

```bash
docker stop upt-note-container
```



# Pasos para uso de los servicios

  ```
    Descargar e importar en la herramienta Postman
    
    ** UTP ENV.postman_environment.json
    ** UTP NOTE.postman_collection.json
  ```

## Índice
1. [SIGNUP](#1-signup)
2. [SIGNIN](#2-signin)
3. [RECORD NOTE](#3-record-note)
4. [GET NOTE](#4-get-note)

---

### 1. SIGNUP

**Descripción:** Endpoint para registrar un nuevo usuario.  
**Método:** `POST`  
**URL:** `http://localhost:8080/utp/api/v1/auth/signup`

#### Body (JSON):
```json
{
    "firstName": "Orlando",
    "lastName": "Camavilca Chavez",
    "email": "orlando.camavilca@gmail.com",
    "password": "camavilca"
}
```

---

### 2. SIGNIN

**Descripción:** Endpoint para iniciar sesión y obtener un token de acceso.  
**Método:** `POST`  
**URL:** `http://localhost:8080/utp/api/v1/auth/signin`

#### Body (JSON):
```json
{
    "email": "orlando.camavilca@gmail.com",
    "password": "camavilca"
}
```

#### Nota:
- El token obtenido debe ser almacenado y usado en los siguientes endpoints.

---

### 3. RECORD NOTE

**Descripción:** Endpoint para registrar una nueva nota.  
**Método:** `POST`  
**URL:** `http://localhost:8080/utp/api/v1/note`  
**Autenticación:** Bearer Token (`{{access_token}}`)

#### Body (JSON):
```json
{
    "note": "12",
    "content": "Lenguaje"
}
```

---

### 4. GET NOTE

**Descripción:** Endpoint para obtener todas las notas registradas.  
**Método:** `GET`  
**URL:** `http://localhost:8080/utp/api/v1/note`  
**Autenticación:** Bearer Token (`{{access_token}}`)

---
