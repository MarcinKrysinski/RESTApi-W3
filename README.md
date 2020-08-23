# REST Api written in Spring Boot

## About


Simple REST Api written to learn Spring Boot basics. Api returns data in the form of XML and JSON, added SwaggerUI support, API tests and implemented HATEOAS.



## Technologies

- Java 
- Maven
- Spring Boot


## Status

Project is in progress


## To do:

- Add fontend (Angular)

## How to use

### GET

```
http://localhost:8080/cars
```
### GET By ID

```
http://localhost:8080/cars/{id}

example: http://localhost:8080/cars/2
```
### GET By Color

```
http://localhost:8080/cars/color/{color}

example: http://localhost:8080/cars/color/black
```

### POST

```
http://localhost:8080/cars

info: need Postman
```

### PUT
```
http://localhost:8080/cars

info: need Postman
```

### PATCH Mark

```
http://localhost:8080/cars/mark/{id}/{mark}

example: http://localhost:8080/cars/mark/1/opel
```

### PATCH Model

```
http://localhost:8080/cars/model/{id}/{model}

example: http://localhost:8080/cars/model/1/astra
```

### PATCH Color
```
http://localhost:8080/cars/color/{id}/{color}

example: http://localhost:8080/cars/mark/1/red
```

### DELETE

```
http://localhost:8080/cars/{id}

example: http://localhost:8080/cars/3
```

### Swagger

```
http://localhost:8080/swagger-ui.html
```

### Request
```
{
    "id": 1,
    "mark": "Opel",
    "model": "Astra",
    "color": "BLACK"
  },
  {
    "id": 2,
    "mark": "Opel",
    "model": "Vectra",
    "color": "RED"
  },
  {
    "id": 3,
    "mark": "Opel",
    "model": "Corsa",
    "color": "BLACK"
  }

```
