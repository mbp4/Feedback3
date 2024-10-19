# GestorNovelas_BaseDatos
 
link al repositorio: https://github.com/mbp4/GestorNovelas_BaseDatos.git

En el ejercicio propuesto se nos pide hacer una aplicación de gestion de novelas, donde se puedan ver, eliminar o añadir nuevas novelas haciendo uso de una base de datos.

## EXPLICACIÓN

### Pantalla de inicio 

Para realizar la aplicacion necesitamos una aplicacion inicial que nos servirá de pantalla de inicio, su pseudocódigo sería:

```
Clase MainActivity hereda de ComponentActivity
    Variables privadas:
        Botón btnAlta
        Botón btnAcercaDe
        RecyclerView recyclerNovelas
        NovelasAdapter novelasAdapter
        Lista mutable listadoNovelasF de tipo Novela 
        Base de datos db de tipo FirebaseFirestore

    Constantes:
        ACCION_VER = 1
        ACCION_BORRAR = 2
        ACCION_FAV = 3
        ACCION_XFAV = 4

    Método onCreate
        Llamar al super.onCreate
        Establecer la vista principal usando activity_main
        Asociar btnAlta y btnAcercaDe con sus respectivos botones en el layout
        Asociar recyclerNovelas con el RecyclerView en el layout

        Acción al hacer clic en btnAlta:
            Crear un Intent para navegar a NuevaNovelaActivity
            Iniciar la actividad

        Acción al hacer clic en btnAcercaDe:
            Crear un Intent para navegar a AcercaDeActivity
            Iniciar la actividad

        Llamar a la función mostrarNovelas

    Método onResume
        Llamar al super.onResume
        Llamar a la función mostrarNovelas

    Función privada mostrarNovelas
        Obtener la colección "novelas" de la base de datos
        Si la obtención de los documentos es exitosa:
            Limpiar la lista listadoNovelasF
            Por cada documento en la colección:
                Convertir el documento a un objeto Novela
                Añadir la novela a la lista listadoNovelasF
            Llamar a prepararRecyclerView
        Si ocurre un error:
            Mostrar un mensaje de error al usuario
            Registrar el error en la consola (log)

    Función privada prepararRecyclerView
        Configurar recyclerNovelas para que use un diseño en forma de lista vertical (LinearLayoutManager)
        Crear una instancia de NovelasAdapter con la lista listadoNovelasF
            Cuando el usuario realice una acción:
                Si la acción es ACCION_VER, llamar a verNovela
                Si la acción es ACCION_BORRAR, llamar a borrarNovela
                Si la acción es ACCION_FAV, llamar a añadirFavorita
                Si la acción es ACCION_XFAV, llamar a xFav
        Asignar novelasAdapter como adaptador de recyclerNovelas
        Notificar al adaptador que los datos han cambiado

    Función privada xFav
        Buscar en la base de datos la novela por título
        Actualizar el campo "fav" a falso para quitarla de favoritos
        Llamar a mostrarNovelas para actualizar la lista
        Mostrar mensaje de confirmación

    Función privada verNovela
        Crear un Intent para navegar a VerNovelaActivity
        Pasar los datos de la novela seleccionada (título, autor, año, sinopsis) al Intent
        Iniciar la actividad

    Función privada borrarNovela
        Buscar en la base de datos la novela por título
        Eliminar el documento de la base de datos
        Llamar a mostrarNovelas para actualizar la lista
        Mostrar mensaje de confirmación si se eliminó correctamente
        Mostrar mensaje de error si hubo un fallo al eliminar

    Función privada añadirFavorita
        Buscar en la base de datos la novela por título
        Actualizar el campo "fav" a verdadero para añadirla a favoritos
        Llamar a mostrarNovelas para actualizar la lista
        Mostrar mensaje de confirmación

```

En esta activity tendremos un TextView inicial con el titulo de la aplicacion, después encontramos dos botones: 

 -> Botón añadir novela: este botón se encargara de redirigir al usuario a una nueva pantalla donde podrá añadir todos los datos de una novela y añadir la misma a la base de datos de           novelas ya existente.
 
 -> Botón acerca de: este botón lleva al usuario a una pantalla donde podrá ver el autor de la aplicación

Después de los botones nos encontramos la lista de novelas existente donde solo se muestra el autor y el título de la obra, esta lista tiene dos botones: 

 -> Botón ver: este botón redirige al usuario a una nueva pantalla donde se muestra toda la informacion de la novela elegida.
 
 -> Botón eliminar: este botón eliminará de la base de datos la novela elegida.

Y también unos botones e imagen identificadora de novelas favoritas:

 -> Botones favoritos: tenemos dos botones, uno de ello marcará la novela como favorita y el otro la quitará de favoritos.

 -> Imagen: para indicar al usuario que la novela ya es favorita se muestra una imagen que cambia dependiendo del atributo de la novela que indica si es o no favorita.

 ### Novela

 Con esta clase creamos un objeto novela para podeer trabajar con varios en las diferentes actividades: 

 ```
Clase de datos Novela con los siguientes atributos:
    - titulo como String
    - autor como String
    - año como Int
    - sinopsis como String
    - fav como boolean 

    Constructor():
        Llamar al constructor principal con valores por defecto:
            titulo vacío, autor vacío, año igual a 0, sinopsis vacía, fav falso
```

### Base de datos de Novelas

Para esto haremos uso de Firebase, con esto podremos hacer uso de una base de datos donde se modificarán las novelas, es decir, se añadiran o se eliminarán de esta.

Visualmente uno de los elementos se vería así: 

<img width="1145" alt="Captura de pantalla 2024-10-08 a las 12 10 29" src="https://github.com/user-attachments/assets/b95ccd0f-3be7-4b64-9b46-d6d999623247">

### Adaptador para las Novelas

Con esta activity buscamos mostrar la lista que se ha creado de novelas, esta activity es necesaria ya que es la que hace posible que se muestre el recyclerView (el utilizado para poder crear la lista de novelas): 

```
Clase NovelasAdapter (recibe una lista mutable de novelas y una función para manejar clics)

    Clase interna NovelasViewHolder (hereda de RecyclerView.ViewHolder)
        Variables en el layout del item:
            TextView textTituloNovel 
            TextView textAutorNovel 
            Botón btnVer 
            Botón btnBorrar 
            Botón btnFavorito 
            Botón btnXFavorito 
            ImageView estrella 

    Método onCreateViewHolder (crea una nueva vista para cada item en la lista)
        Inflar la vista del layout item_novela
        Devolver un nuevo NovelasViewHolder con la vista inflada

    Método onBindViewHolder (vincula los datos con la vista en la posición actual)
        Obtener la novela actual de la lista en la posición indicada
        Mostrar el título y autor de la novela en los TextViews correspondientes
        Acción al hacer clic en btnVer:
            Llamar a la función onNovelasClick con la novela actual y la acción ACCION_VER
        Acción al hacer clic en btnBorrar:
            Mostrar un diálogo de confirmación para borrar la novela
            i el usuario confirma, llamar a onNovelasClick con la novela y la acción ACCION_BORRAR
        Acción al hacer clic en btnFavorito:
            Llamar a onNovelasClick con la novela actual y la acción ACCION_FAV
        Acción al hacer clic en btnXFavorito:
            Llamar a onNovelasClick con la novela actual y la acción ACCION_XFAV
        Si la novela es favorita (atributo fav es true):
            Cambiar la imagen de la estrella a rellena
        Si no es favorita:
            Cambiar la imagen de la estrella a vacía

    Método getItemCount (devuelve el número de novelas en la lista)
        Devolver el tamaño de la lista de novelas

```

Este adaptador se encarga de la gestión de la visualización de las novelas y los botones. 

Estos 2 botones se encargan de llevar al usuario a una pantalla encargada de mostrar toda la información de la novela elegida por el usuario (btnVer) y de eliminar la novela que el usuario decida (btnBorrar).

A parte tendremos los botones que se encargan de cambiar el atributo de la novela seleccionada para que esta se convierta en favorita o no mostrando una imagen de una estrella vacia en el caso de que la novela no sea favorita y de una estrella rellena en el caso de que sea una favorita. 

Por otra parte el adaptador se encarga también de mostrar el tamaño de la lista.

### Actividad para mostrar toda la informacion de la Novela

En esta activity se mostrará toda la información de la novela pulsada:

```
Clase VerNovelaActivity extiende ComponentActivity:

    Atributos:
        txt1: TextView para mostrar el título de la novela
        txt2: TextView para mostrar el autor de la novela
        txt3: TextView para mostrar la sinopsis de la novela
        txt4: TextView para mostrar el año de la novela
        btnVolver: Button para regresar a la actividad anterior

    Método onCreate(Bundle):
        Llamar al método super.onCreate y establecer el layout 'activity_ver'

        Asignar txt1 con el TextView correspondiente (R.id.txt1)
        Obtener el título de la novela desde el intent y asignarlo a txt1

        Asignar txt2 con el TextView correspondiente (R.id.txt2)
        Obtener el autor de la novela desde el intent y asignarlo a txt2

        Asignar txt3 con el TextView correspondiente (R.id.txt3)
        Obtener la sinopsis de la novela desde el intent y asignarlo a txt3

        Asignar txt4 con el TextView correspondiente (R.id.txt4)
        Obtener el año de la novela desde el intent y convertirlo a cadena, luego asignarlo a txt4

        Asignar btnVolver con el Button correspondiente (R.id.btnVolver)
        Configurar el botón btnVolver para finalizar la actividad al hacer clic (volver a la actividad anterior)

```

En esta activity nos encontramos con varias variables que van asociadas a las ya definidas en los layouts, estas se encargarán de mostrar cada uno de los atributos de la novela que se ha pulsado.

Por otra parte el btnVolver también se encuentra asociado a uno ya definido en el layout, este se encarga de devolver al usuario a la pantalla principal.

### Nueva Novela

En esta activity se añade la informacion necesaria para que le usuario tenga la opción de poder añadir las novelas que quiera a la lista de novelas: 

```
Clase NuevaNovelaActivity extiende ComponentActivity:

    Declarar btnGuardarNovela como botón
    Declarar btnCancelar como botón
    Declarar editTitulo como EditText 
    Declarar editAutor como EditText 
    Declarar editAño como EditText 
    Declarar editSinopsis como EditText 
    Declarar db como FirebaseFirestore 

    Método onCreate(bundle):
        Llamar a super.onCreate(bundle)
        Asignar el layout de la actividad como activity_nueva_novela

        Asignar editTitulo, editAutor, editAño, editSinopsis a los identificadores correspondientes del layout
        Asignar btnGuardarNovela y btnCancelar a sus identificadores del layout

        Cuando se haga clic en btnGuardarNovela:
            Llamar a guardarNovela()
            Finalizar la actividad

        Cuando se haga clic en btnCancelar:
            Finalizar la actividad

    Método guardarNovela():
        Obtener el texto de editTitulo, convertirlo a cadena
        Obtener el texto de editAutor, convertirlo a cadena
        Obtener el texto de editAño, convertirlo a entero
        Obtener el texto de editSinopsis, convertirlo a cadena
        Crear un objeto Novela con los valores obtenidos

        En la colección "novelas" de la base de datos:
            Agregar nuevaNovela
            Si se guarda exitosamente:
                Mostrar mensaje "Novela guardada: título de la novela"
                Finalizar la actividad
            Si ocurre un error al guardar:
                Mostrar mensaje de error con el motivo del fallo

```

En este activity nos encontraremos varios TextEdit en los cuales el usuario podrá añadir la información necesaria para crear una nueva novela, después encontraremos un botón encargado de guardar la nueva novela y añadirla a la lista y otro botón que cancelará la operación y devuelve al usuario a la pantalla inicial. 

## Proceso de desarrollo

Para realizar la aplicacion se ha hecho uso del anterior proyecto como base y este ha sido modificado.

En el proyecto se nos solicitaba que nuestra aplicación de gestor de novelas contase con una base de datos que guardara estas, por lo tanto los cambios han sido: 

 -> Lo primero será cambiar la lista de novelas por una base de datos que creamos desde firebase con todos los elementos necesarios. 
 
 -> Después debemos cambiar los botones correspondientes:
 
    -> Botón borrar: debemos hacer que el botón borre la novela de la base de datos, haciendo que los datos ya no esten en esta, ademas de esto se añade un pop up que confirma que el              usuario desea borrar la novela de la base de datos. Cuando esta se borre aparecerá un mensaje que indicará al usuario si se ha borrado o no.

    -> Botón guardar: debemos hacer que el botón guarde la novela en la base de datos, haciendo que los datos se actualicen, cuando esta se añada aparecerá un mensaje que indicará al              usuario si se ha añadido o no.

 -> Y por último para no tener informacion innecesaria en nuestro programa borraremos el repositorio de novelas que se tenía en la anterior práctica.
