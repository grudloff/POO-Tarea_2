TAREA 2
Este programa consiste en continuar trabajando en lo realizado en la Tarea 1 pero esta vez hacerlo de manera gráfica. Mediante una ventana o frame, se espera poder configurar al robot y poder establecer el laberinto utilizado mediante un archivo pbm que se encuentre en el directorio del proyecto. Existen otras opciones que tienen como finalidad modifcar parámetros del robot para que este pueda recorrer el laberinto.
INSTALACIONES PREVIAS:
Para poder correr el programa es necesario tener previamente instalado:
Java SE (Standard Edition, JDK)
Make: GNU make utility to maintain groups of programs


INDICACIONES:
Se deberá descargar cada una de las etapas para poder ejecutarlas de manera individual.
Se expondrá cómo ejecutar la EtapaX (X numero de la etapa). El procedimiento es equivalente para el resto de las etapas.

A través de consola del equipo se puede realizar la ejecución del programa (Búsqueda Windows -> cmd). Es necesario dirigirse a la carpeta donde se encuentra la etapa que se quiera ejecutar EtapaX.
Para el caso de Windows, si el archivo está guardado en C:\Users\Repositorio\Desktop\GITREP2\Etapa1 es nececsario poner en la cosola

```
>> cd C:C:\Users\Repositorio\Desktop\GITREP2\Etapa1
```

Luego de esto, se puede realizar la ejecución con el comando make

```
>> make
```

Para ir a otra Etapa y ejecutarla, puede ejecutar el siguiente comando, reemplazando la letra "X" de la etapa

```
>> cd ..\EtapaX
```

EJECUCION ETAPA 1:
Al ejecutar STAGE1, se podrá visualizar el diseño del Frame con la barra menu y el boton play/stop que cambia de imagen dependiendo el estado en el que se encuentre. Dicho boton no tendrá funcionamiento en esta etapa y en la barra de herramientas solo estará disponible la opcion FILE->OPEN, en donde se podrá elegir un archivo pbm que contenga un laberinto y que éste se despliegue en el frame. Se puede ver que el Frame se ajusta al tamaño del laberinto.

Para ejecutarlo solo es necesario
```
>> make run 
```
EJECUCION ETAPA 2:
Al ejecutar STAGE2, se podrá crear un robot a través de CREATE ROBOT, el cuál tendrá una posición inicial fija. En esta etapa el robot aun no se mueve, solamente se posiciona en un punto fijo del laberinto.

```
>> make run
```

EJECUCION ETAPA 3:
En STAGE3 se crear un robot a través de CREATE ROBOT pero con la diferencia que se puede definir su velocidad tanto en el eje x como en el eje y y también el tipo de piloto que se quiera, o sea, si el robot quiere aferrarse a la pared derecha o a la izquierda. También existirá la opción SET DELTA T, para fijar la resolución temporal del sistema. Luego, con ayuda del mouse se puede ubicar el robot en algún lugar arbitrario presionando CLICK IZQUIERDO DEL MOUSE. Con ayuda del boton PLAY/PAUSA, se puede visualizar la trayectoria que sigue el robot dentro del laberinto. Se puede pausar el movimiento del robot para así poder crear otro, cuyos parámetros sean distintos al primero. No existe un límite definido por el programa para la cantidad de robots dentro del laberinto.

```
>> make run
```

EJECUCION ETAPA 4:
En STAGE4 se puede obtener a partir de cada robot la trayectoria que éste siguió para poder llegar a la salida, siempre y cuando el robot se encuentre a una distancia razonable de la salida. Esta trayectoria será entregada a través de un archivo pbm que almacena dicha información en cordenadas (t,x,y), en donde t corresponde al instante de tiempo de programa en el que el robot se encuentra en la coordenada espacial (x,y)
```
>> make run
```

EJECUCION EXTRA CREDITOS
En esta etapa se puede obtener a partir de cada robot la trayectoria que éste siguió para llegar a la salida pero ahora a través de una forma gráfica, o sea, el archivo pbm entrega la trayectoria del robot dentro del laberinto que puede ser analizado de manera visual al cambiar el formato del archivo pbm.
Si desea convertir de .pbm a .jpg,.png o .gif puede usar:
java convertPBM <ORIGEN.pbm> <DESTINO.png>

```
>> make run
```
AUTORES

* **GABRIEL RUDLOFF** - *Desarrollador* - [gabriel.rudloff.13](https://git.elo.utfsm.cl/gabriel.rudloff.13)
* **DIEGO RIQUELME** - *Desarrollador* - [diego.riquelme.13](https://git.elo.utfsm.cl/diego.riquelme.13)
* **LEONARDO SOLIS ZAMORA** - *Desarrollador* - [leonardo.solis](https://git.elo.utfsm.cl/leonardo.solis)


