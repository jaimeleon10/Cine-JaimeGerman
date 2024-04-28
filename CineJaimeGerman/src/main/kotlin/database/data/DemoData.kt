package org.example.database.data

import org.example.clientes.models.Cliente
import org.example.productos.models.butacas.Butaca
import org.example.productos.models.butacas.EstadoButaca
import org.example.productos.models.butacas.OcupacionButaca
import org.example.productos.models.butacas.TipoButaca
import org.example.ventas.models.LineaVenta
import org.example.ventas.models.Venta
import java.time.LocalDate
import java.util.UUID

/**
 * Función para iniciar datos por defecto de clientes
 * @author Jaime Leon
 * @since 1.0.0
 */
fun initDemoClientes() = listOf(
    Cliente(id = 1, nombre = "Jaime", email = "jaime@gmail.com", numSocio = "AAA111"),
    Cliente(id = 2, nombre = "German", email = "german@gmail.com", numSocio = "BBB222"),
)

/**
 * Función para iniciar datos por defecto de ventas
 * @author Jaime Leon
 * @since 1.0.0
 */
fun initDemoVentas() = listOf(
    Venta(
        id = UUID.fromString("67c712fb-5531-4f33-a744-0fdb65cd9dcf"),
        cliente = Cliente(id = 1, nombre = "Jaime", email = "jaime@gmail.com", numSocio = "AAA111"),
        lineas = listOf(
            LineaVenta(
                tipoProducto = "Butaca",
                producto = Butaca(
                    id = "A1",
                    nombre = "Butaca",
                    precio = 5.0,
                    fila = 0,
                    columna = 0,
                    tipoButaca = TipoButaca.NORMAL,
                    estadoButaca = EstadoButaca.ACTIVA,
                    ocupacionButaca = OcupacionButaca.LIBRE
                ),
                cantidad = 1,
                precio = Butaca(
                    id = "A1",
                    precio = 5.0,
                    fila = 0,
                    columna = 0,
                    tipoButaca = TipoButaca.NORMAL,
                    estadoButaca = EstadoButaca.ACTIVA,
                    ocupacionButaca = OcupacionButaca.LIBRE).precio,
                createdAt = LocalDate.now(),
                updatedAt = LocalDate.now(),
            )
        ),
        fechaCompra = LocalDate.now(),
        createdAt = LocalDate.now(),
        updatedAt = LocalDate.now(),
    )
)

/**
 * Función para iniciar datos por defecto de lineas de venta
 * @author Jaime Leon
 * @since 1.0.0
 */
fun initDemoLineas() = listOf(
    LineaVenta(
        id = UUID.fromString("67c712fb-5531-4f33-a744-0fdb65cd9dcf"),
        tipoProducto = "Butaca",
        producto = Butaca(
            id = "A1",
            nombre = "Butaca",
            precio = 5.0,
            fila = 0,
            columna = 0,
            tipoButaca = TipoButaca.NORMAL,
            estadoButaca = EstadoButaca.ACTIVA,
            ocupacionButaca = OcupacionButaca.LIBRE
        ),
        cantidad = 1,
        precio = Butaca(
            id = "A1",
            precio = 5.0,
            fila = 0,
            columna = 0,
            tipoButaca = TipoButaca.NORMAL,
            estadoButaca = EstadoButaca.ACTIVA,
            ocupacionButaca = OcupacionButaca.LIBRE).precio,
        createdAt = LocalDate.now(),
        updatedAt = LocalDate.now(),
    )
)