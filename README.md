# Microservicio de Consulta de Personas

Este proyecto es un microservicio desarrollado con **Spring Boot**, **Spring Data JPA** y una base de datos en memoria **H2**. Permite realizar operaciones CRUD (Crear, Leer, Eliminar) sobre la entidad `Persona` a través de una API REST.

---

## 1. Estructura del Proyecto

El código fuente sigue la arquitectura en capas recomendada por Spring Boot para separar responsabilidades de manera limpia:

```text
Personas/
└── src/
    └── main/
        ├── java/
        │   └── cl/nttdata/personas/
        │       ├── PersonasApplication.java    # Clase principal de ejecución
        │       ├── controller/
        │       │   └── PersonaController.java  # Exposición de endpoints REST
        │       ├── model/
        │       │   └── Persona.java            # Entidad JPA (Modelo de datos)
        │       └── repository/
        │           └── PersonaRepository.java   # Interfaz de acceso a la BD
        └── resources/
            ├── application.yaml                 # Configuración de Spring y H2
            └── data.sql                         # Script de precarga de datos iniciales
```

---

## 2. Proceso Básico de Creación y Configuración

### Pasos iniciales:
1. **Generación de Base:** Se descarga la plantilla inicial desde [Spring Initializr](https://start.spring.io/) con el paquete base `cl.nttdata.personas` e indicando las dependencias necesarias:
   * **Spring Web**: Para exponer peticiones HTTP/REST.
   * **Spring Data JPA**: Para la gestión y persistencia de datos.
   * **H2 Database**: Base de datos relacional en memoria.
2. **Entidad / Modelo (`Persona.java`)**: Se define la estructura de los datos (RUT como `@Id` clave primaria, nombre, apellido, edad y email) mappeada a la tabla `personas`.
3. **Repositorio (`PersonaRepository.java`)**: Se extiende de `JpaRepository<Persona, String>`, proporcionando automáticamente métodos para interactuar con la base de datos sin escribir sentencias SQL manuales.
4. **Controlador (`PersonaController.java`)**: Se definen las rutas REST bajo el prefijo `/api/personas` inyectando el repositorio para responder las peticiones HTTP.
5. **Precarga de Datos (`data.sql`)**: Se genera un archivo SQL en `src/main/resources` para poblar la base de datos automáticamente cada vez que inicia el servicio.

---

## 3. Cómo Ejecutar la Aplicación en Eclipse IDE

1. Abre **Eclipse IDE**.
2. En el panel **Package Explorer**, busca el archivo principal:  
   `src/main/java/cl/nttdata/personas/PersonasApplication.java`
3. Haz clic derecho sobre el archivo `PersonasApplication.java`.
4. Selecciona **Run As** > **Java Application** (o **Spring Boot App**).
5. La aplicación iniciará en el puerto predeterminado **`8080`**.

> **Nota:** Puedes verificar que la base de datos está activa accediendo desde el navegador a la consola de H2: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:personasdb`, Usuario: `sa`, Contraseña en blanco).

---

## 4. Cómo Realizar Consultas (Endpoints REST)

Puedes probar las consultas usando **Postman**, **Insomnia**, **cURL** o directamente desde el navegador (para peticiones `GET`).

### 🔹 Obtener todas las personas
* **Método:** `GET`
* **URL:** `http://localhost:8080/api/personas`
* **Respuesta de ejemplo (`200 OK`):**
  ```json
  [
    {
      "rut": "12345678-9",
      "nombre": "Carlos",
      "apellido": "González",
      "edad": 28,
      "email": "carlos@example.com"
    },
    {
      "rut": "98765432-1",
      "nombre": "Maria",
      "apellido": "Soto",
      "edad": 32,
      "email": "maria@example.com"
    }
  ]
  ```

---

### 🔹 Obtener persona por RUT
* **Método:** `GET`
* **URL:** `http://localhost:8080/api/personas/12345678-9`
* **Respuesta de ejemplo (`200 OK`):**
  ```json
  {
    "rut": "12345678-9",
    "nombre": "Carlos",
    "apellido": "González",
    "edad": 28,
    "email": "carlos@example.com"
  }
  ```

---

### 🔹 Registrar/Crear una nueva persona
* **Método:** `POST`
* **URL:** `http://localhost:8080/api/personas`
* **Headers:** `Content-Type: application/json`
* **Cuerpo de la petición (Body / Raw JSON):**
  ```json
  {
    "rut": "11111111-1",
    "nombre": "Ana",
    "apellido": "Martínez",
    "edad": 22,
    "email": "ana.martinez@example.com"
  }
  ```

---

### 🔹 Eliminar persona por RUT
* **Método:** `DELETE`
* **URL:** `http://localhost:8080/api/personas/12345678-9`
* **Respuesta:** `204 No Content`

---

## 5. ¿Cómo Funciona el Microservicio Internamente?

1. **Flujo de una Petición HTTP:**
   * El cliente (Postman/Navegador) hace una solicitud HTTP (por ejemplo, `GET /api/personas`).
   * El **`PersonaController`** recibe la petición, interpreta la ruta enviada y delega la responsabilidad al repositorio.
   * El **`PersonaRepository`** genera la consulta necesaria hacia la base de datos en memoria **H2** utilizando el ORM de **Hibernate / JPA**.
   * La respuesta obtenida de la base de datos se transforma automáticamente a formato **JSON** y es devuelta al cliente con su respectivo código de estado HTTP (`200 OK`, `404 Not Found`, `204 No Content`, etc.).

2. **Base de Datos Volátil (En Memoria):**
   * Debido a que la base de datos **H2** corre en memoria RAM (`jdbc:h2:mem:personasdb`), los datos modificados se reinician cada vez que la aplicación se apaga o se vuelve a ejecutar. El archivo `data.sql` asegura que la aplicación siempre vuelva a cargar datos base al iniciar.
