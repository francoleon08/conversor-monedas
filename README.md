# Sistema de Conversión de Moneda

## Descripción
Este sistema es una aplicación en Java que permite convertir monedas de un país a otro utilizando la API "Exchange Rate API".

## Índice
1. [Instalación](#instalación)
2. [Uso del patrón MVC](#uso-del-patrón-mvc)
3. [Autor](#autor)
4. [Conclusiones](#conclusiones)

## Instalación
1. Clona este repositorio en tu máquina local.
2. Abre el proyecto en tu IDE preferido.
3. Obtener una KEY para poder consumir la API de tasas de cambio en [Exchange Rate API](https://www.exchangerate-api.com/).
4. Crear un archivo .env y configura la variable de entorno API_KEY con tu clave de acceso a la API. 
5. Asegúrate de tener configuradas las dependencias y el entorno de ejecución adecuado.
6. Compila y ejecuta la aplicación.

## Uso del patrón MVC
En este sistema, se sigue el patrón de diseño Modelo-Vista-Controlador (MVC) para una mejor organización y separación de responsabilidades. A continuación se detalla cómo se implementa este patrón:

- **Modelo**: La clase `ExchangeRateService` actúa como el modelo en este sistema. Es responsable de almacenar un mapeo de divisas y sus valores. También se comunica con la clase `CurrencyLoader` a través de una interfaz para consumir la API de tasas de cambio.
Se encarga también de comunicarle a sus hoyentes que deben actualizar sus vistas, para esto se utilizó el patron Observer.

- **Vista**: Las vistas en este sistema serían las interfaces de usuario (UI) que muestran los resultados de las conversiones de moneda. Estas vistas se actualizan con los datos proporcionados por el modelo.

- **Controlador**: La clase `CurrencyController` funciona como el controlador en este sistema. Es la encargada de recibir las solicitudes del usuario y delegar la tarea de conversión de moneda a la clase modelo `ExchangeRateService`.

En resumen, el flujo de control sigue estas etapas: el controlador recibe la solicitud del usuario desde la vista, consulta el servicio de tasas de cambio al modelo, ahora el modelo se encarga 
de hacer la petición a la API y notificar a las vistas que deben actualizarse con los nuevos valores. Por ultimo la vista recibe la notificiación de actualización y
obtiene los valores de la conversión para mostrarlos al usuario.

## Conclusiones
En este proyecto, se utilizó el patrón de diseño MVC para organizar y separar las responsabilidades de cada componente del sistema. Esto ha permitido una mejor escalabilidad y mantenimiento del código, ya que cada parte del sistema tiene una función claramente definida.

Además, gracias al uso de interfaces y a la inyección de dependencias, el sistema es altamente flexible. Es posible agregar nuevas implementaciones para consumir otras APIs de tasas de cambio sin tener que modificar el código existente, simplemente implementando la interfaz `CurrencyLoader`. Esto facilita la adaptación del sistema a futuros cambios en los requisitos o en las fuentes de datos.

En resumen, el diseño modular y la flexibilidad del sistema garantizan su capacidad para crecer y adaptarse a medida que evolucionan las necesidades del proyecto y del negocio.

## Autor
Este sistema fue desarrollado por:
* [Franco Leon](https://github.com/francoleon08)
