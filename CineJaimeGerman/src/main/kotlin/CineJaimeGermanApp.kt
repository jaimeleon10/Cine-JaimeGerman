package org.example

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import org.example.clientes.models.Cliente
import org.example.clientes.repositories.ClienteRepository
import org.example.config.Config
import org.example.database.service.SqlDeLightManager
import org.example.locale.toDefaultDateString
import org.example.locale.toDefaultMoneyString
import org.example.productos.models.butacas.Butaca
import org.example.productos.models.butacas.EstadoButaca
import org.example.productos.models.butacas.OcupacionButaca
import org.example.productos.models.butacas.TipoButaca
import org.example.productos.models.complementos.Complemento
import org.example.productos.services.productos.ProductoService
import org.example.ventas.models.LineaVenta
import org.example.ventas.models.Venta
import org.example.ventas.repositories.VentaRepository
import org.example.ventas.service.VentaService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDate
import java.util.*
import kotlin.io.path.Path

private val logger = logging()

/**
 * Clase de Koin para gestionar la app del cine
 * @author Jaime y German
 * @since 1.0.0
 */
class CineJaimeGermanApp : KoinComponent {

    /**
     * Funcion run para ejecutar la app
     * @author Jaime y German
     * @since 1.0.0
     */
    fun run() {
        val productoService: ProductoService by inject()
        val clienteRepository: ClienteRepository by inject()
        val ventaService: VentaService by inject()
        val ventaRepository: VentaRepository by inject()

        val sqlDeLightManager: SqlDeLightManager by inject()
        sqlDeLightManager.initialize()

        println()
        println("╔═════════════════════════════════╗")
        println("║ BIENVENIDO A LOS CINES DAWFILMS ║")
        println("╚═════════════════════════════════╝")

        do {
            println("\nMenú del cine 🍿🎥:")
            println(" 1: Comprar entrada")
            println(" 2: Devolver entrada")
            println(" 3: Ver estado del cine")
            println(" 4: Configurar butacas")
            println(" 5: Actualizar butaca")
            println(" 6: Importar complementos")
            println(" 7: Mostrar listado complementos")
            println(" 8: Actualizar stock de complementos")
            println(" 9: Obtener recaudación")
            println(" 10: Exportar estado del cine")
            println(" 11: Salir")
            println("\nSelecciona una opción (1-11):")
            val eleccion = readln().toIntOrNull() ?: -1

            if (eleccion !in 1..11) println("Introduce un valor válido entre 1 y 11")

            when (eleccion) {
                1 -> comprarEntrada(productoService, clienteRepository, ventaService)
                2 -> devolverEntrada(ventaRepository, productoService, ventaService)
                3 -> mostrarEstadoCine(productoService)
                4 -> configurarButacas(productoService)
                5 -> actualizarButaca(productoService)
                6 -> importarComplementos(productoService)
                7 -> mostrarListadoComplementos(productoService)
                8 -> actualizarStock(productoService)
                9 -> obtenerRecaudacion(ventaService)
                10 -> exportarEstadoCine(productoService)
                11 -> println("\nAbandonando menú del cine... 👋🏻")
            }
        } while (eleccion != 11)
    }

    /**
     * Funcion para realizar la compra de una entrada
     * @param productoService servicio para gestionar productos
     * @param clienteRepository repositorio de clientes
     * @param ventaService servicio de ventas
     * @author Jaime Leon
     * @since 1.0.0
     * @see mostrarEstadoCine
     * @see mostrarListadoComplementos
     * @see toDefaultDateString
     * @see toDefaultMoneyString
     */
    private fun comprarEntrada(
        productoService: ProductoService,
        clienteRepository: ClienteRepository,
        ventaService: VentaService
    ) {
        println("💠 Menú de compra:")
        val listadoCine = productoService.findAllButacas()

        val listadoButacasSeleccionadas: MutableList<Butaca> = mutableListOf()
        val listadoComplementosSeleccionados: MutableList<Complemento> = mutableListOf()

        var cliente: Cliente?

        if (listadoCine.isNotEmpty()) {
            do {
                mostrarEstadoCine(productoService)
                println("\nPrecio butaca normal -> ${TipoButaca.NORMAL.precio.toDefaultMoneyString()}")
                println("Precio butaca VIP -> ${TipoButaca.VIP.precio.toDefaultMoneyString()}")
                println("\nIntroduce el identificador de la butaca que quieres comprar [fila,columna]:")
                println("Mostrando ejemplo -> A1")
                val idButaca = readln().uppercase()
                var contadorComplementos = 0
                try {
                    val butaca = productoService.findButacaById(idButaca)
                    if (butaca.ocupacionButaca == OcupacionButaca.LIBRE && butaca.estadoButaca == EstadoButaca.ACTIVA) {
                        butaca.ocupacionButaca = OcupacionButaca.ENRESERVA
                        productoService.updateButaca(idButaca, butaca)
                        logger.debug { "Añadiendo butaca a la lista de la compra..." }
                        listadoButacasSeleccionadas.add(butaca)

                        var respuesta: String
                        do {
                            println("\n¿Quieres añadir complementos? (s / n)")
                            respuesta = readln().lowercase()
                        } while (respuesta != "s" && respuesta != "n")
                        do {
                            var resComplementos = "n"
                            if (respuesta == "s") {
                                println("\nMostrando listado de complementos disponibles:")
                                val listadoComplementos = productoService.findAllComplementos()
                                mostrarListadoComplementos(productoService)

                                if (listadoComplementos.isNotEmpty()) {
                                    println("\nSeleciona el complemento que deseas con su id:") //TODO SIN COMPLEMENTO
                                    println("Máximo 3 complementos por butaca, complementos seleccionados: $contadorComplementos")
                                    val idComplemento = readln()
                                    try {
                                        val complemento = productoService.findComplementoById(idComplemento)
                                        var cantidad: Int?
                                        val max = complemento.stock
                                        do {
                                            println("Introduce la cantidad deseada: (>0)")
                                            cantidad = readln().toIntOrNull()
                                            if (cantidad != null && cantidad > max) {
                                                println("Has sobrepasado el stock del producto 🔝")
                                                println("Stock máximo actual de ${complemento.nombre}: $max\n")
                                            }
                                        } while (cantidad == null || cantidad < 1 || cantidad > max)
                                        logger.debug { "Añadiendo complemento a la lista de la compra..." }
                                        repeat(cantidad) {
                                            listadoComplementosSeleccionados.add(complemento)
                                        }
                                        complemento.stock -= cantidad
                                        productoService.updateComplemento(idComplemento, complemento)
                                        contadorComplementos += 1
                                    } catch (e: Exception) {
                                        println("El complemento no ha sido encontrado ❌")
                                    }
                                    if (contadorComplementos < 3) {
                                        println("¿Deseas seleccionar más complementos? (s / n)")
                                        resComplementos = readln().lowercase()
                                    }
                                } else println("No exiten complementos en la Base de Datos ❌")
                            }
                        } while (resComplementos != "n")

                    } else println("La butaca seleccionada debe estar libre y activa ⚠️")
                } catch (e: Exception) {
                    println("La butaca no ha sido encontrada ❌")
                }
                println("\n¿Deseas seleccionar otra butaca? (s / n)")
                val resButacas = readln().lowercase()
            } while (resButacas != "n")

            if (listadoButacasSeleccionadas.isNotEmpty()) {
                val numSocioRegex = Regex("[a-zA-Z]{3}[0-9]{3}")
                var numSocio: String
                do {
                    println("\nIntroduce tu número de socio para la compra: (LLLNNN)")
                    numSocio = readln().uppercase()
                } while (!numSocio.matches(numSocioRegex))

                cliente = clienteRepository.findByNumSocio(numSocio)

                if (cliente == null) {
                    println("Número de socio inexistente ❌")
                    println("\nGenerando nuevo cliente...")
                    println("\nIntroduce los siguientes datos: 📋")
                    val nameRegex = Regex("[a-zA-Z]{3,15}")
                    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
                    var nombre: String
                    var email: String
                    do {
                        println("Nombre:")
                        nombre = readln()
                    } while (!nombre.matches(nameRegex))
                    do {
                        println("Email:")
                        email = readln()
                    } while (!email.matches(emailRegex))
                    do {
                        numSocio = generateNumSocioRandom()
                    } while (clienteRepository.findByNumSocio(numSocio) != null)
                    cliente = Cliente(nombre = nombre, email = email, numSocio = numSocio)
                    cliente = clienteRepository.save(cliente)
                }

                println("\nMostrando datos de compra: 🛒")
                var precioTotal = 0.0
                println("Butacas: 💺")
                listadoButacasSeleccionadas.forEach {
                    val filaLetra = filaLetra(it.fila)
                    println("- Tipo: ${it.tipoButaca}, Precio Unitario: ${it.precio.toDefaultMoneyString()}, Fila: $filaLetra, Columna: ${it.columna + 1}")
                    precioTotal += it.precio
                }
                println("\nComplementos: 🍿")
                val listadoComplementosCantidades = listadoComplementosSeleccionados.groupingBy { it }.eachCount()
                listadoComplementosCantidades.forEach { (complemento, cantidad) ->
                    if (complemento.tipo == "Butaca") println("- Nombre: ${complemento.nombre}-${complemento.id}, Precio Unitario: ${complemento.precio.toDefaultMoneyString()}, Cantidad: $cantidad")
                    else println("- Nombre: ${complemento.nombre}, Precio Unitario: ${complemento.precio.toDefaultMoneyString()}, Cantidad: $cantidad")
                    precioTotal += (complemento.precio * cantidad)
                }
                println("Precio total: ${precioTotal.toDefaultMoneyString()}")

                var resCompra: String
                do {
                    println("\n¿Confirmar compra? (s / n)")
                    resCompra = readln().lowercase()
                } while (resCompra != "s" && resCompra != "n")

                if (resCompra == "n") {
                    logger.debug { "Revirtiendo cambios en butacas por compra cancelada..." }
                    listadoButacasSeleccionadas.forEach {
                        it.ocupacionButaca = OcupacionButaca.LIBRE
                        productoService.updateButaca(it.id, it)
                    }

                    logger.debug { "Revirtiendo cambios en complementos por compra cancelada..." }
                    listadoComplementosCantidades.forEach { (complemento, cantidad) ->
                        complemento.stock += cantidad
                        productoService.updateComplemento(complemento.id, complemento)
                    }
                } else {
                    val lineasDeVentas: MutableList<LineaVenta> = mutableListOf()

                    logger.debug { "Actualizando butacas en la base de datos..." }
                    listadoButacasSeleccionadas.forEach {
                        it.ocupacionButaca = OcupacionButaca.OCUPADA
                        productoService.updateButaca(it.id, it)
                        lineasDeVentas.add(
                            LineaVenta(
                                tipoProducto = "Butaca",
                                producto = it,
                                cantidad = 1,
                                precio = it.precio
                            )
                        )
                    }

                    logger.debug { "Actualizando complementos en la base de datos..." }
                    listadoComplementosCantidades.forEach { (complemento, cantidad) ->
                        lineasDeVentas.add(
                            LineaVenta(
                                tipoProducto = "Complemento",
                                producto = complemento,
                                cantidad = cantidad,
                                precio = complemento.precio
                            )
                        )
                    }

                    logger.debug { "Creando venta..." }
                    val venta = Venta(
                        cliente = cliente,
                        lineas = lineasDeVentas,
                        fechaCompra = LocalDate.now()
                    )

                    val res = ventaService.createVenta(venta)
                    res.onSuccess {
                        println("Compra realizada 🍿🎥")
                        println("- Identificador de compra: ${venta.id}")
                        println("- Nombre: ${cliente.nombre}")
                        println("- Número de Socio: ${cliente.numSocio}")
                        venta.lineas.forEach {
                            println("- Producto: ${it.producto.nombre}, Cantidad: ${it.cantidad}, Precio: ${it.precio}")
                        }
                        println("- Coste total: ${venta.total.toDefaultMoneyString()}")
                        println("- Fecha de compra: ${venta.fechaCompra.toDefaultDateString()}")

                        val fecha = LocalDate.of(venta.fechaCompra.year, venta.fechaCompra.month, venta.fechaCompra.dayOfMonth).toDefaultDateString()

                        val idsButacas = listadoButacasSeleccionadas.joinToString("-") { it.id }
                        val htmlFile = Path("data", "FicherosDeCompra", "entrada_${idsButacas}_${venta.cliente.numSocio}_$fecha.html").toFile()
                        ventaService.exportCompraToHtml(venta, htmlFile)
                    }
                    res.onFailure { println("Ha ocurrido un error en la compra, volviendo al menú del cine 🎥") }
                }
            } else {
                println("Error: No se ha seleccionado ninguna butaca ❌")
                println("Volviendo al menú del cine 🔙")
            }
        } else {
            println("Error: Base de datos vacía, introduce butacas iniciales ‼️")
            println("Volviendo al menú del cine 🔙")
        }
    }

    /**
     * Funcion para realizar la devolucion de una entrada
     * @param productoService servicio para gestionar productos
     * @param ventaRepository repositorio de ventas
     * @param ventaService servicio de ventas
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun devolverEntrada(
        ventaRepository: VentaRepository,
        productoService: ProductoService,
        ventaService: VentaService
    ) {
        println("💠 Menú de devolución:")
        var idCompra: UUID? = null
        do {
            println("Introduce tu identificador de compra:")
            println("Ejemplo: 814fa535-8215-416a-8ad1-6e858fc7db31")
            val res = readln()
            try {
                idCompra = UUID.fromString(res)
            } catch (e: Exception) {
                println("Identificador de compra no válido ❌")
            }
        } while (idCompra == null)

        ventaService.getById(idCompra)
            .onSuccess { venta ->
                logger.debug { "Cambiando fichero de compra a fichero de devolución" }

                val listadoButacas = mutableListOf<Butaca>()
                venta.lineas.forEach { producto ->
                    if (producto.tipoProducto == "Butaca") listadoButacas.add(
                        producto.producto as Butaca
                    )
                }
                val idsButacas = listadoButacas.joinToString("-") { it.id }
                val numSocio = venta.cliente.numSocio
                val fecha = LocalDate.of(venta.fechaCompra.year, venta.fechaCompra.month, venta.fechaCompra.dayOfMonth).toDefaultDateString()

                val htmlFileCompra =
                    Path("data", "FicherosDeCompra", "entrada_${idsButacas}_${numSocio}_${fecha}.html").toFile()
                if (htmlFileCompra.exists()) {
                    logger.debug { "Borrando fichero de compra..." }
                    htmlFileCompra.delete()
                }

                val htmlFileDevolucion =
                    Path("data", "FicherosDeDevolucion", "entrada_${idsButacas}_${numSocio}_${fecha}.html").toFile()
                logger.debug { "Generando fichero de devolución..." }
                ventaService.exportDevolucionToHtml(venta, htmlFileDevolucion)

                logger.debug { "Liberando butacas..." }
                listadoButacas.forEach {
                    it.ocupacionButaca = OcupacionButaca.LIBRE
                    productoService.updateButaca(it.id, it)
                }

                logger.debug { "Liberando complementos..." }
                val listadoComplementosCantidades = venta.lineas.filter { it.tipoProducto == "Complemento" }.groupingBy { it.producto as Complemento }.eachCount()
                listadoComplementosCantidades.forEach { (complemento, cantidad) ->
                    complemento.stock += cantidad
                    productoService.updateComplemento(complemento.id, complemento)
                }

                logger.debug { "Realizando borrado lógico de la venta..." }
                venta.isDeleted = true
                ventaRepository.update(venta.id, venta)

                println("Entradas y complementos devueltos con éxito ✅")
                println("Volviendo al menú del cine 🔙")
            }
            .onFailure {
                println("Error: Identificador de compra no encontrado ❌")
                println("Volviendo al menú del cine 🔙")
            }
    }

    /**
     * Funcion para mostrar el estado actual del cine
     * @param productoService servicio para gestionar productos
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun mostrarEstadoCine(productoService: ProductoService) {
        val estadoCine = productoService.findAllButacas()
        try {
            val butacaDefault = Butaca(
                fila = 0,
                columna = 0,
                tipoButaca = TipoButaca.NORMAL,
                estadoButaca = EstadoButaca.ACTIVA,
                ocupacionButaca = OcupacionButaca.LIBRE,
                precio = 5.0
            )
            val matrizCine: Array<Array<Butaca>> = Array(5) { Array(7) { butacaDefault } }
            var contador: Int = 0

            for (i in matrizCine.indices) {
                for (j in matrizCine[i].indices) {
                    matrizCine[i][j] = estadoCine[contador]
                    contador++
                }
            }

            println("💠 Mostrando estado del cine:\n")
            println("Leyenda:")
            println("- Butaca en mantenimiento: 🛠️")
            println("- Butaca fuera de servicio: ❌")
            println("- Butaca libre normal: 🪑")
            println("- Butaca libre VIP: ⭐")
            println("- Butaca reservada: 📋")
            println("- Butaca comprada: 🙍🏻‍♂️\n")

            println("    1️⃣  2️⃣  3️⃣  4️⃣  5️⃣  6️⃣  7️⃣")
            for (i in matrizCine.indices) {
                when (i) {
                    0 -> print("A  ")
                    1 -> print("B  ")
                    2 -> print("C  ")
                    3 -> print("D  ")
                    4 -> print("E  ")
                }
                for (j in matrizCine[i].indices) {
                    when (matrizCine[i][j].estadoButaca) {
                        EstadoButaca.FUERASERVICIO -> print("[❌]")
                        EstadoButaca.MANTENIMIENTO -> print("[🛠️]")
                        EstadoButaca.ACTIVA -> {
                            when (matrizCine[i][j].ocupacionButaca) {
                                OcupacionButaca.LIBRE -> {
                                    when (matrizCine[i][j].tipoButaca) {
                                        TipoButaca.NORMAL -> print("[🪑]")
                                        TipoButaca.VIP -> print("[⭐]")
                                    }
                                }
                                OcupacionButaca.ENRESERVA -> {
                                    print("[📋]")
                                }
                                else -> {
                                    print("[🙍🏻‍♂️]")
                                }
                            }
                        }
                    }
                }
                println()
            }
        } catch (e: Exception) {
            println("Error: Base de datos vacía, introduce butacas iniciales ‼️")
            println("Volviendo al menú del cine 🔙")
        }
    }

    /**
     * Funcion para configurar las butacas iniciales desde un archivo csv
     * @param productoService servicio para gestionar productos
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun configurarButacas(productoService: ProductoService) {
        //Importamos las butacas si la tabla está vacía
        println("💠 Importando configuración de butacas por defecto...")
        println("\nIntroduce el nombre del fichero para importar:")
        val name = readln()
        try {
            productoService.loadButacasFromCsv(name).forEach {
                try {
                    productoService.findButacaById(it.id)
                    logger.debug { "Butaca ${it.id} existente" }
                } catch (e: Exception) {
                    productoService.saveButaca(it)
                }
            }
        } catch (e: Exception) {
            println("Nombre de fichero no válido ❌")
            println("Volviendo al menú del cine 🔙")
        }
    }

    /**
     * Funcion para actualizar el estado y tipo de una butaca
     * @param productoService servicio para gestionar productos
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun actualizarButaca(productoService: ProductoService) {
        println("💠 Menú de actualización de butaca:\n")
        println("Introduce el identificador de la butaca [fila,columna]:")
        println("Mostrando ejemplo -> A1")
        val idButaca = readln().uppercase()
        println("\nBuscando butaca con el id: '$idButaca'... 🔍")
        try {
            val butaca = productoService.findButacaById(idButaca)
            var res: String
            do {
                println("¿Deseas actualizar el tipo o el estado de la butaca? (t / e)")
                val tipo = butaca.tipoButaca.toString()
                val estado = butaca.estadoButaca.toString()
                println("Tipo actual de la butaca: $tipo")
                println("Estado actual de la butaca: $estado")
                res = readln().lowercase()
            } while (res != "e" && res != "t")
            if (res == "e") {
                actualizarEstadoButaca(idButaca, butaca, productoService)
            } else {
                actualizarTipoButaca(idButaca, butaca, productoService)
            }
        } catch (e: Exception) {
            println("La butaca no ha sido encontrada ❌")
            println("Volviendo al menú del cine 🔙")
        }
    }

    /**
     * Funcion para importar los complementos iniciales desde un archivo csv
     * @param productoService servicio para gestionar productos
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun importarComplementos(productoService: ProductoService) {
        //Importamos los complementos si la tabla está vacía
        println("💠 Importando complementos:")
        println("\nIntroduce el nombre del fichero para importar:")
        val name = readln()
        try {
            productoService.loadComplementosFromCsv(name).forEach {
                try {
                    productoService.findComplementoByNombre(it.nombre)
                    logger.debug { "Complemento: ${it.nombre} existente" }
                } catch (e: Exception) {
                    productoService.saveComplemento(it)
                }
            }
        } catch (e: Exception) {
            println("Nombre de fichero no válido ❌")
            println("Volviendo al menú del cine 🔙")
        }
    }

    /**
     * Funcion para mostrar el listado de complementos disponibles
     * @param productoService servicio para gestionar productos
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun mostrarListadoComplementos(productoService: ProductoService){
        val listado = productoService.findAllComplementos()
        println("💠 Mostrando listado de complementos:")
        for (i in listado.indices) {
            println("🍿 Id: ${listado[i].id}, Nombre: ${listado[i].nombre}, Precio: ${listado[i].precio.toDefaultMoneyString()}, Stock: ${listado[i].stock}")
        }
        if (listado.isEmpty()) println("No exiten complementos en la Base de Datos ❌")
    }

    /**
     * Funcion para actualizar el stock de un producto
     * @param productoService servicio para gestionar productos
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun actualizarStock(productoService: ProductoService) {
        println("💠 Menú para actualizar stock de complementos:")
        println("\nMostrando complementos existentes:")
        val listadoComplementos = productoService.findAllComplementos()
        mostrarListadoComplementos(productoService)
        if (listadoComplementos.isNotEmpty()) {
            println("\nIntroduce la id del complemento que quieres actualizar:")
            val res = readln().toIntOrNull() ?: -1
            try {
                val complemento = productoService.findComplementoById(res.toString())
                var newStock: Int
                do {
                    println("\nIntroduce el nuevo stock: (>1)")
                    newStock = readln().toIntOrNull() ?: -1
                } while (newStock < 1)
                complemento.stock = newStock
                productoService.updateComplemento(res.toString(), complemento)
                println("Stock modificado 🔁")
                println("Volviendo al menú del cine 🔙")
            } catch (e: Exception) {
                println("El complemento no ha sido encontrado ❌")
                println("Volviendo al menú del cine 🔙")
            }
        } else println("No exiten complementos en la Base de Datos ❌")
    }

    /**
     * Funcion para obtener la recaudacion total de ventas dada una fecha
     * @param ventaService servicio para gestionar ventas
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun obtenerRecaudacion(ventaService: VentaService) {
        println("💠 Obteniendo recaudación del cine por fecha...")

        var year: Int
        var month: Int
        var day: Int

        do {
            println("\nIntroduce el número de año: (2000-2050)")
            year = readln().toIntOrNull() ?: -1
            println("\nIntroduce el número de mes: (1-12)")
            month = readln().toIntOrNull() ?: -1
            println("\nIntroduce el número de día: (1-31)")
            day = readln().toIntOrNull() ?: -1
        } while (year !in 2000..2050 && month !in 1..12 && day !in 1..31)

        val fecha: LocalDate = LocalDate.of(year, month, day)

        val ventas = ventaService.totalVentasByDate(fecha)
        var total: Double = 0.0
        ventas.forEach { total += it.total }
        println("Recaudación total en fecha $fecha:")
        println("${total.toDefaultMoneyString()} 💰")
    }

    /**
     * Funcion para exportar el estado del cine dada una fecha
     * @param productoService servicio para gestionar productos
     * @author German Fernandez
     * @since 1.0.0
     */
    private fun exportarEstadoCine(productoService: ProductoService) {
        println("💠 Exportando estado del cine por fecha ...")

        var year: Int
        var month: Int
        var day: Int

        do {
            println("\nIntroduce el número de año: (2000-2050)")
            year = readln().toIntOrNull() ?: -1
            println("\nIntroduce el número de mes: (1-12)")
            month = readln().toIntOrNull() ?: -1
            println("\nIntroduce el número de día: (1-31)")
            day = readln().toIntOrNull() ?: -1
        } while (year !in 2000..2050 && month !in 1..12 && day !in 1..31)

        val fecha: LocalDate = LocalDate.of(year, month, day)

        val fileName = "EstadoCine-${fecha.toDefaultDateString()}.json"

        val listaButacasVendidasFecha = productoService.findAllButacasVendidasFecha(fecha)

        val listaButacasDefecto = productoService.defaultListButacas()

        for (butaca in listaButacasVendidasFecha) {
            listaButacasDefecto.find { it.id == butaca.id }?.apply {
                precio = butaca.precio
                tipoButaca = butaca.tipoButaca
                estadoButaca = butaca.estadoButaca
                ocupacionButaca = butaca.ocupacionButaca
            }
        }

        val file = Path(Config.storageData, "FicherosJson", fileName).toFile()
        productoService.exportButacasToJson(listaButacasDefecto,file)

        println("Fichero JSON $fileName con el estado actual creado ✅")
        println("Volviendo al menú del cine 🔙")
    }

    /**
     * Funcion para cambiar el numero de la fila por la letra correspondiente
     * @param fila fila a cambiar
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun filaLetra(fila: Int): String {
        return when (fila) {
            0 -> "A"
            1 -> "B"
            2 -> "C"
            3 -> "D"
            else -> "E"
        }
    }

    /**
     * Funcion para actualizar el tipo de butaca
     * @param idButaca id de la butaca
     * @param butaca datos de la butaca modificada
     * @param productoService servicio de productos
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun actualizarTipoButaca(idButaca: String, butaca: Butaca, productoService: ProductoService) {
        var resCambioTipo: String
        do {
            println("Selecciona el nuevo tipo (Normal / VIP) -> (n / v)")
            resCambioTipo = readln().lowercase()
        } while (resCambioTipo != "n" && resCambioTipo != "v")
        if (resCambioTipo == "n") {
            butaca.tipoButaca = TipoButaca.NORMAL
            productoService.updateButaca(idButaca, butaca)
            println("Butaca modificada 🔁")
            println("Volviendo al menú del cine 🔙")
        }
        if (resCambioTipo == "v") {
            butaca.tipoButaca = TipoButaca.VIP
            productoService.updateButaca(idButaca, butaca)
            println("Butaca modificada 🔁")
            println("Volviendo al menú del cine 🔙")
        }
    }

    /**
     * Funcion para actualizar el estado de butaca
     * @param idButaca id de la butaca
     * @param butaca datos de la butaca modificada
     * @param productoService servicio de productos
     * @author Jaime Leon
     * @since 1.0.0
     */
    private fun actualizarEstadoButaca(idButaca: String, butaca: Butaca, productoService: ProductoService) {
        var resCambioEstado: String
        do {
            println("Selecciona el nuevo estado (Activa / Mantenimiento / Fuera de servicio) -> (a / m / f)")
            resCambioEstado = readln().lowercase()
        } while (resCambioEstado != "a" && resCambioEstado != "m" && resCambioEstado != "f")
        if (resCambioEstado == "a") {
            butaca.estadoButaca = EstadoButaca.ACTIVA
            productoService.updateButaca(idButaca, butaca)
            println("Butaca modificada 🔁")
            println("Volviendo al menú del cine 🔙")
        }
        if (resCambioEstado == "m") {
            butaca.estadoButaca = EstadoButaca.MANTENIMIENTO
            productoService.updateButaca(idButaca, butaca)
            println("Butaca modificada 🔁")
            println("Volviendo al menú del cine 🔙")
        }
        if (resCambioEstado == "f") {
            butaca.estadoButaca = EstadoButaca.FUERASERVICIO
            productoService.updateButaca(idButaca, butaca)
            println("Butaca modificada 🔁")
            println("Volviendo al menú del cine 🔙")
        }
    }

    /**
     * Funcion para generar un numero de socio aleatorio
     * @author Jaime Leon
     * @since 1.0.0
     * @return numero de socio
     */
    private fun generateNumSocioRandom(): String {
        val letras = ('A'..'Z')
        val numeros = ('0'..'9')
        val regex = Regex("[A-Z]{3}[0-9]{3}")

        val numeroSocio = buildString {
            repeat(3) {
                append(letras.random())
            }
            repeat(3) {
                append(numeros.random())
            }
        }

        return if (regex.matches(numeroSocio)) {
            numeroSocio
        } else {
            generateNumSocioRandom()
        }
    }
}