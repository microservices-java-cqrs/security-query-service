# SecurityQueryService

<p style='text-align: justify;'> Este es un microservicio desarrollado con Java en su versión 17 y springboot 3.0.4, se encarga de las consultas a usuario, roles, menús y permisos. El código sigue los principios <b>SOLID</b> y para el desarrollo presenta una arquitectura hexagonal, cabe resaltar que se implementa el patrón <b>CQRS</b> y como su nombre lo indica SecurityQueryService solo realiza consultas a una base de datos <b>mongodb</b>, tambien se hace uso de <b>Kafka</b> y <b>Maven</b> para sincronizar los objetos que se envian desde el microservicio <b>SecurityCommandService</b> y para la construcción del microservicio respectivamente. </p>

## Broker de kafka

<p style='text-align: justify;'> Primero debemos descargar kafka desde su <a href="https://kafka.apache.org/">pagina oficial</a> o si desea montarlo en un contenedor docker habria que descargar la imagen y construir el contenedor, por defecto kafka habilita el puerto <b>9092</b>, pero si desea puede modificar los archivos de configuración para habilitar el puerto 29092</p>

## Servidor de desarrollo

<p style='text-align: justify;'> Primero debemos clonar el código del <a href="https://github.com/microservices-java-cqrs/security-query-service">repositorio</a> y con una terminal instalar las dependencias</p>

```sh
mvn dependency:resolve
```

<p style='text-align: justify;'> A continuación debemos construir la dependencia <b>security-share</b> el cual es un proyecto maven que sirve como un utilitario para el microservicio. Clonar el código del <a href="https://github.com/microservices-java-cqrs/security-share">repositorio</a> y una vez dentro del proyecto ejecutamos el siguiente comando: </p>

```sh
mvn clean install
```

<p style='text-align: justify;'> Luego debemos construir nuestro microservicio, para eso nos ubicamos dentro del proyecto <b>security-query-service</b> y ejecutamos el siguiente comando:</p> 

```sh
mvn clean install
```

<p style='text-align: justify;'> Finalmente para levantar el microservicio ejecutamos el siguiente comando:</p>

```sh
mvn spring-boot:run
```

<p style='text-align: justify;'> Esto levantará un servidor de desarrollo localhost en el puerto <b>8082</b> </p> 
