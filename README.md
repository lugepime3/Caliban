# Caliban
Test técnico mercado-libre
## Objetivo:
* Desarrollar  una API REST para detectar  si un ADN corresponde a un mutante o no. 
* Obtener estadísticas de los resultados. 
## Tecnologias:
* Java v-1.8
* Postgresql v-42.2.19
* PgAdmin 4 v-4.28
* Docker v-19.03.13
* GitHub Desktop 2.8
* Eclipse Oxygen
* Servidor Productivo: desde https://id.heroku.com/login
## Requisitos:
* Crear el servicio “/mutant/” en donde se pueda detectar si un humano es mutante enviando la secuencia de ADN mediante un HTTP POST con un Json el cual tenga el siguiente formato:   POST → /mutant/
{
“dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
  En caso de verificar un mutante, debería devolver un HTTP 200-OK, en caso contrario un 403-Forbidden
* Anexar una base de datos, la cual guarde los ADN’s verificados con la API.Solo 1 registro por ADN.
* Crear un servicio  “/stats” que devuelva un Json con las estadísticas de las verificaciones de ADN: {“count_mutant_dna”:40, “count_human_dna”:100: “ratio”:0.4}
* Test-Automáticos, Code coverage > 80%.
## Agregados:
* La cadena del patron del ADN se autocalcula de acuerdo a la entrada
* Las dimensiones de la matrix pueden ser dinamico, el unico requisito es que debe ser NxN (cuadrado)
* Desarrollo de un servicio "show" que muestra los registros en base de datos con las pruebas realizadas  a los adn's
* Se documenta la api con swagger y se brinda un espacio para las pruebas  de los endpoint's
## Ejecucion:
1. PG-ADMIN 4 - Configuracion Heroku INSTALACION Y CONFIGURACION- 
  * Paso 1:     Descargar imagen  =        docker pull dpage/pgadmin4
  * Paso 2:     Inicializar y nombrar el contenedor =    docker run -p 5050:80   --name pgadmin4 -e "PGADMIN_DEFAULT_EMAIL=lugepime3@gmail.com"   -e "PGADMIN_DEFAULT_PASSWORD=1234"  -d dpage/pgadmin4
  * Paso 3:     Ingresar en modo incognito =  http://localhost:5050/login?next=%2F (debe desplegar el inicio)
  * Paso 4:     Paso 4:	Configurar conexion = Los datos son los suministrados por Heroku
  * Ayuda: Observe el siguiente video = https://www.youtube.com/watch?v=CCX64hQHjdY, "Deploy Java Spring Boot app to Heroku free server", se describe paso a paso la asignacion de la BD en el proyecto.
    Se recomienda  usar  "github desktop" para enlazar con este repositorio, e ir a su cuenta de Heroku y "vincular" Heroku->Git Desktop->Repositorio porque se automatiza el ciclo de entrega asi como el deploy 
    desde Heroku.
2. IMPORTANTE: NO NECESITA AGREGAR TARJETA DE CREDITOS, HEROKU OFRECE  POSTGRESQL
3. Para usar la api ingrese a:
## https://caliban-meli.herokuapp.com/swagger-ui.html
> Alli  accedera a la interfaz de "swagger-ui", encontrara que es un entorno diseñdo especificamente para ofrecer una experiencia de usuario agradable en lo concerniente a realizar las pruebas sobre los endpoint que
conforman un api determinada.Para inciar de click sobre "List Operations", debe desplegar los endpoint. Ahora bien, fijese en las "Notas de Implementacion" para darse una idea de que debe realizar.
Cada elemento posee un ejemplo de lo que debe insertar para probar.
# Anexos:
* Imagen de cobertura: https://github.com/lugepime3/Caliban/blob/master/Cobertura.PNG

    
