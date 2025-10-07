# Proyecto Spring Boot Carrito

Aplicación Spring Boot simple para gestionar la venta de productos con un carrito de compras.
Este proyecto originalmente fue academico, se esta adaptando para portafolio.

Contenido principal del repositorio:

- `src/main/java/org/cibertec/edu/pe/` - Código fuente Java
  - `controller/` - Controladores (ej. `ProductoController.java`)
  - `model/` - Entidades del dominio (`Producto`, `Detalle`, `Venta`)
  - `repository/` - Repositorios Spring Data JPA
- `src/main/resources/templates/` - Vistas Thymeleaf (`index.html`, `carrito.html`, `confirmacion_pago.html`, `mensaje.html`)
- `src/main/resources/static/img/` - Imágenes usadas en las vistas
- `src/main/resources/application.properties` - Configuración (actualmente apunta a MySQL local)

Artefactos compilados y entregables (ya presentes en este repositorio):

- `bin/` - Clases compiladas (IDE/Eclipse output). Este directorio ahora está ignorado por `.gitignore`.
- `target/` - Output de Maven (build, clases compiladas, artefactos). Este directorio ahora está ignorado por `.gitignore`.

Configuración de base de datos (valor actual en `src/main/resources/application.properties`):

- URL: `jdbc:mysql://localhost:3306/shop2023`
- usuario: `root`
- contraseña: `root`

Antes de ejecutar la aplicación asegúrate de tener una base de datos MySQL `shop2023` creada y accesible desde tu máquina. Por seguridad, actualiza las credenciales en `application.properties` antes de subir código a un repositorio público.

Cómo compilar y ejecutar (requiere Maven y JDK instalados):

En Windows (cmd.exe):

```cmd
mvn clean package
mvn spring-boot:run
```

O para ejecutar el jar generado tras el package:

```cmd
java -jar target\*.jar
```

Qué se cambió en este commit:

- Se agregó `.gitignore` para ignorar `target/`, `bin/`, archivos de IDE y otros artefactos temporales.
- Se creó/actualizó este `README.md` con la estructura del proyecto y pasos básicos para ejecutar.

Siguientes pasos recomendados:

- Mover las credenciales y configuración sensibles fuera del repositorio o usar perfiles (`application-dev.properties`) y variables de entorno.
- Añadir un script SQL o migraciones (Flyway/Liquibase) para crear la base de datos y tablas necesarias.
- Añadir instrucciones para pruebas y despliegue si se desea.

Contacto / notas: este README resume lo que hay actualmente en el workspace: el código fuente en `src/`, plantillas en `resources/templates` y recursos estáticos en `resources/static`.
