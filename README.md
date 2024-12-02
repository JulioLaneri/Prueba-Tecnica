# Proyecto Microservicios: ms-stock y ms-ventas
Este proyecto consta de dos microservicios, `ms-stock` y `ms-ventas`, diseñados para gestionar el inventario y las ventas de productos. A continuación, se detallan las instrucciones para compilar, ejecutar y probar los servicios.

## Requisitos previos
- **Docker** y **Docker Compose** instalados en el sistema.

---

## Compilación
1. Clonar el repositorio:
   git clone https://github.com/JulioLaneri/Prueba-Tecnica.git

2. Construir las imágenes Docker de los microservicios
    docker-compose build

## Ejecución
1. Levantar todos los servicios utilizando Docker Compose:
    docker-compose up

2. Los servicios estarán disponibles en los siguientes puertos:
    ms-stock: http://localhost:8081
        Crear un producto en el stock:
        POST http://localhost:8081/api/stock
        Request Body: 
            {
                "productId": "123",
                "productName": "Producto A",
                "quantity": 50
            }

        Obtener un producto del stock
        GET http://localhost:8081/api/stock/{productId}

        Obtener todos los productos del stock
        GET http://localhost:8081/api/stock
    
    ms-ventas: http://localhost:8082
        Ver un producto del catalogo
        GET http://localhost:8082/api/catalogo/{productId}

        Ver todos los productos del catalogo
        GET http://localhost:8082/api/catalogo

        Realizar una venta
        POST http://localhost:8082/api/ventas
        Request Body: 
            {
                "productId": "123",
                "quantity": 25
            }

## No se utiliza un script SQL, ya que las tablas son generadas automáticamente por Hibernate y el componente CommandLineRunner inicializa los datos.


