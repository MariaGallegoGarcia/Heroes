
## Introducción

Aplicación para el mantenimiento de súperheroes

## Uso

### Instalación

```bash
cd HeroesManagement
mvn clean install
```
   
### Ejecutable

Se encuentra en la carpeta HeroesManagement-boot


### Contenedor docker

Renombramos el ejecutable y lo colocamos en la carpeta docker 

```bash
cd HeroesManagement
rename HeroesManagement-boot-0.0.1-SNAPSHOT.jar heroes-management.jar
```

Levantamos el contenedor

```bash
cd HeroesManagement/docker
docker build -t heroes-management .
docker run -p 8080:8080 heroes-management
```

## API Docs

Swagger api docs can be found on the following path
```
http://localhost:8080/heroes-management/v3/api-docs
```