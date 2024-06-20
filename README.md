Aplicación de Conversor de Moneda
Descripción General

Este proyecto es un conversor de monedas sencillo que se ejecuta en la línea de comandos y está escrito en Java. Permite a los usuarios ver las monedas disponibles, agregar nuevas monedas y convertir entre ellas utilizando tasas de cambio en tiempo real obtenidas de una API externa.
Características

    Mostrar Monedas Disponibles: Muestra una lista de las monedas que están actualmente soportadas.
    Agregar Nueva Moneda: Permite a los usuarios agregar una nueva moneda a la lista especificando su código.
    Conversión de Monedas: Convierte una cantidad de una moneda a otra utilizando las tasas de cambio más recientes.
    Almacenamiento Persistente: Guarda la lista de monedas disponibles y sus tasas de cambio en un archivo JSON (monedas.json) para retener los datos entre ejecuciones del programa.

Estructura del Proyecto

El proyecto está organizado en las siguientes clases:

    Principal.java: La clase principal que contiene el punto de entrada del programa y maneja las interacciones con el usuario.
    Config.java: Una clase utilitaria para cargar propiedades de configuración desde un archivo config.properties.
    ConsultorTasaDeCambio.java: Una clase utilitaria para obtener tasas de cambio en tiempo real desde la API ExchangeRate-API.
    Moneda.java: Una clase que representa una moneda, incluyendo su código y tasa de cambio.

Configuración e Instalación
Requisitos Previos

    Java Development Kit (JDK) 8 o superior
    Una clave de API de ExchangeRate-API (regístrate en ExchangeRate-API)

Instrucciones

    Clonar el Repositorio:

    sh

git clone https://github.com/tuusuario/currency-converter.git
cd currency-converter

Configurar la Clave de API:

    Crea un archivo config.properties en el directorio src/main/resources.
    Agrega tu clave de API de ExchangeRate-API al archivo config.properties:

    properties

    api.key=TU_CLAVE_API

Compilar y Ejecutar:

sh

    javac src/main/java/*.java
    java -cp src/main/java Principal

Uso

Cuando ejecutas el programa, se te presentará el siguiente menú:

markdown

Conversor de Moneda

Opciones:
1. Mostrar todas las monedas disponibles
2. Agregar nueva moneda
3. Convertir monedas
4. Salir
Selecciona una opción:

Opciones

    Mostrar todas las monedas disponibles: Lista todas las monedas disponibles.
    Agregar nueva moneda: Solicita un nuevo código de moneda, obtiene su tasa de cambio y la agrega a la lista.
    Convertir monedas: Solicita las monedas de origen y destino y la cantidad a convertir, luego muestra la cantidad convertida.
    Salir: Sale del programa.

Persistencia de Datos

    La lista de monedas y sus tasas de cambio se guardan en un archivo JSON llamado monedas.json.
    Este archivo se lee cuando el programa se inicia y se escribe cuando se agrega una nueva moneda.

Manejo de Errores

    El programa incluye manejo básico de errores, como la comprobación de opciones de menú no válidas y el manejo de excepciones al obtener tasas de cambio.

Contribuciones

¡Las contribuciones son bienvenidas! Por favor, haz un fork de este repositorio y envía pull requests.

Agradecimientos

    ExchangeRate-API por proporcionar los datos de las tasas de cambio.
    A mis profesores de Alura Latam por transmitir su sabiduría y experiencia para poder avanzar en este campo
    A mis compañeros de ruta en este viaje increíble
    Muchas gracias a todos!

Contacto

Para cualquier consulta o problema, por favor abre un issue en GitHub.
