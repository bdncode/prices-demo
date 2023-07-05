# prices-demo
INDITEX technical test: Aplicación Spring Boot para Consulta de Precios.


Esta documentación describe una aplicación/servicio en Spring Boot que proporciona un punto de enlace REST
para consultar precios de productos en una base de datos de comercio electrónico. A continuación, se
detallan los pasos necesarios para configurar y utilizar la aplicación.

### Tabla de Precios
La base de datos contiene una tabla llamada "PRICES" que refleja el precio final (PVP) y la tarifa
aplicable a un producto de una cadena en un rango de fechas determinado. A continuación se muestra un
ejemplo de la tabla con los campos relevantes:


| BRAND_ID |       START_DATE         |       END_DATE           | PRICE_LIST | PRODUCT_ID | PRIORITY |  PRICE  | CURR |
|----------|-------------------------|-------------------------|------------|------------|----------|---------|------|
|    1     | 2020-06-14-00.00.00     | 2020-12-31-23.59.59     |     1      |   35455    |    0     |  35.50  | EUR  |
|    1     | 2020-06-14-15.00.00     | 2020-06-14-18.30.00     |     2      |   35455    |    1     |  25.45  | EUR  |
|    1     | 2020-06-15-00.00.00     | 2020-06-15-11.00.00     |     3      |   35455    |    1     |  30.50  | EUR  |
|    1     | 2020-06-15-16.00.00     | 2020-12-31-23.59.59     |     4      |   35455    |    1     |  38.95  | EUR  |

Campos
* **BRAND_ID**: clave foránea de la cadena del grupo (1 = ZARA).
* **START_DATE**, **END_DATE**: rango de fechas en el que se aplica el precio de la tarifa indicada.
* **PRICE_LIST**: identificador de la tarifa de precios aplicable.
* **PRODUCT_ID**: identificador de código de producto.
* **PRIORITY**: desambiguador de la aplicación de precios. Si dos tarifas coinciden en un rango de fechas, se aplica la de mayor prioridad (mayor valor numérico).
* **PRICE**: precio final de venta.
* **CURR**: código ISO de la moneda.

### Estructura de paquetes

Dentro del paquete base del proyecto se encuentran los siguientes subpaquetes


* **constants**: contiene las clases de constantes del proyecto.
* **domain**: contiene las clases de dominio, normalmente entidades JPA/Hibernate.
* **error**: contiene la clase de advice para manejo de excepciones.
* **error.exception**: contiene la clase de excepciones propias de la aplicación.
* **repository**: contiene las clases de acceso a los repositorios. Principalmente serán clases Spring
  Data que acceden a base de datos.
* **rest**: contiene las clases controladoras REST.
* **rest.dto**: contiene las clases DTO's que se sirven al cliente del API.
* **service**: contiene las clases de servicio que definen la lógica de negocio.
* **service.mapper**: contiene las clases de mapeo. Normalmente serán interfaces basadas en mapstruct para
  transformar DTO's y en Entities y viceversa.


### Configuración e Instalación
Para configurar y ejecutar la aplicación Spring Boot, siga los siguientes pasos:

* **Clone el repositorio**: ```git clone https://github.com/bdncode/prices-demo.git```
* **Construya el proyecto**: ```mvn clean install```
* **Ejecute la aplicación**: En la raíz del paquete base del proyecto se define la clase PricesDemoApplication que contiene el método main. Ejecutando esta
  clase, la aplicación se iniciará en el puerto predeterminado para el servidor Tomcat que es el 8080, o en el puerto según la
  configuración específica elegida por el usuario.


### Punto de Entrada REST
La aplicación proporciona un punto de entrada REST para consultar precios de productos. Este punto de
entrada acepta los siguientes parámetros de entrada:

* Fecha de aplicación
* Identificador del producto
* Identificador de la cadena

Para hacer uso del punto de entrada REST, realice una petición HTTP con los parámetros adecuados. A 
continuación, se muestra un ejemplo de pruebas que puede realizar con los datos de ejemplo proporcionados:

Petición a las 10:00 del día 14 para el producto 35455 y la cadena 1 (ZARA).<br>

GET ```{{baseurl}}/prices?brandId=1&productId=35455&date=2020-06-15T10:00:00```

devolvera como respuesta un objeto de salida con los datos indicados:

{<br>&nbsp;
"productId": 35455,<br>&nbsp;
"brandId": 1,<br>&nbsp;
"priceList": 3,<br>&nbsp;
"startDate": "2020-06-15T00:00:00",<br>&nbsp;
"endDate": "2020-06-15T11:00:00",<br>&nbsp;
"finalPrice": 30.50<br>
}

En caso de producirse un error controlado en la petición, se devolvera el siguiente objeto informando del motivo:

{<br>&nbsp;
"status": 404,<br>&nbsp;
"message": "No se ha podido encontrar un precio con parámetros de búsqueda introducidos"<br>
}

### Consideraciones de diseño para el Punto de Entrada REST

La consulta devolvera un unico resultado segun los parámetros de entrada introducidos, en ningun caso un
conjunto de dtos.
Si no se encuentra el precio deseado segun los parámetros de entrada se lanzara una excepcion.

Tambien se lanzaran excepciones si los parámetros de entrada son invalidos, por ejemplo fechas incorrectas.

El punto de entrada acepta parámetros de entrada pero tambien devolvera un objeto de respuesta valido si 
no se intoduce ninguno de ellos.

### Base de Datos en Memoria (H2)
La aplicación utiliza una base de datos en memoria H2 para almacenar los datos de ejemplo. No se requiere 
ninguna configuración adiciónal, ya que la base de datos se inicializa automáticamente con los datos 
proporcionados mediante la herramienta de migración de bases de datos Flyway.

Para ello se incluyen dos scripts .sql en la carpeta ```resources/db/migration``` que Flyway ejecutara al 
iniciarse la aplicación. El primero es el DDL para la creación de la tabla y el segundo es el DML para la 
inserción de datos iniciales.

Igualmente en ```test/resources``` se incluye un script ```PriceControllerIntTest.sql``` utilizado en la
base de datos H2 para tests de integración.

### Consideraciones de diseño para la Entidad com.inditex.pricesdemo.domain.Price


En el ejercicio se especifica la tabla PRICES con las siguientes peculiaridades:

La tabla no especifica una columna Primary Key (clave primaria), pero en SQL este concepto es fundamental
ya que identifica de manera única cada fila en una tabla asi que se añade la variable **PRICE_ID** a la
entidad.

La columna **BRAND_ID** es una foreign key que hace referencia a otra tabla que no aparece en el ejercicio.
Existe la posibilidad de crear la tabla BRANDS para para establecer una relación ManyToMany o ManytoOne,
pero se decide dejar como una referencia numérica de tipo Long ya que el id de BRANDS es el unico valor
que se necesita para el dto de salida.

La columna **PRODUCT_ID** no se indica que sea una foreign key, pero podría serlo. Igual que en el caso anterior
se deja como una referencia numérica de tipo Long a otro dominio.

La columna **PRIORITY** pareceria que es de tipo Boolean porque solo aparecen los valores 0 y 1, pero no se
descarta que haya mas niveles de prioridad por lo que se mapea como Integer.

### Tests
La aplicación cuenta con pruebas automatizadas tanto del servicio como del controlador REST para verificar el funcionamiento correcto del punto de entrada.
Los casos de prueba de integración disponibles son:

* Prueba 1: Petición a las 10:00 del día 14 para el producto 35455 y la cadena 1 (ZARA).
* Prueba 2: Petición a las 16:00 del día 14 para el producto 35455 y la cadena 1 (ZARA).
* Prueba 3: Petición a las 21:00 del día 14 para el producto 35455 y la cadena 1 (ZARA).
* Prueba 4: Petición a las 10:00 del día 15 para el producto 35455 y la cadena 1 (ZARA).
* Prueba 5: Petición a las 21:00 del día 16 para el producto 35455 y la cadena 1 (ZARA).
* Prueba 6: Lanzar excepción si no encuentra un precio con parámetros de búsqueda introducidos.
* Prueba 7: Lanzar excepción con parámetros de búsqueda incorrectos.
* Prueba 8: Lanzar excepción con petición HTTP no implementada.

Los casos de prueba unitarios disponibles son:

* Prueba 1: Buscar un precio por sus parámetros correctamente.
* Prueba 2: Lanzar excepción si se busca un precio que no existe.
