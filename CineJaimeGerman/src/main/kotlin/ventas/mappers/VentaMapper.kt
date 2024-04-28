package org.example.ventas.mappers

import database.LineaVentaEntity
import database.VentaEntity
import org.example.clientes.mappers.toClienteDto
import org.example.clientes.models.Cliente
import org.example.productos.mappers.toProductoDto
import org.example.productos.models.butacas.Butaca
import org.example.productos.models.complementos.Complemento
import org.example.productos.models.productos.Producto
import org.example.ventas.dto.LineaVentaDto
import org.example.ventas.dto.VentaDto
import org.example.ventas.models.LineaVenta
import org.example.ventas.models.Venta
import java.time.LocalDate
import java.util.*

/**
 * Función para el traspaso de LineaVentaEntity a LineaVenta
 * @param producto producto para la creacion
 * @author German Fernandez
 * @since 1.0.0
 * @return Linea de venta
 */
fun LineaVentaEntity.toLineaVenta(producto: Producto): LineaVenta {
    return LineaVenta(
        id = UUID.fromString(this.id),
        producto = producto,
        tipoProducto = this.producto_tipo,
        cantidad = this.cantidad.toInt(),
        precio = this.precio,
        createdAt = LocalDate.parse(this.created_at),
        updatedAt = LocalDate.parse(this.updated_at),
    )
}

/**
 * Función para el traspaso de VentaEntity a Venta
 * @param cliente cliente para la creacion
 * @param lineas lineas de venta para la creacion
 * @param fechaCompra fecha de compra de la venta
 * @author German Fernandez
 * @since 1.0.0
 * @return Venta
 */
fun VentaEntity.toVenta(cliente: Cliente, lineas: List<LineaVenta>, fechaCompra: LocalDate): Venta {
    return Venta(
        id = UUID.fromString(this.id),
        cliente = cliente,
        lineas = lineas,
        fechaCompra = fechaCompra,
        createdAt = LocalDate.parse(this.created_at),
        updatedAt = LocalDate.parse(this.updated_at),
    )
}

/**
 * Función para el traspaso de LineaVenta a LineaVentaDto
 * @author German Fernandez
 * @since 1.0.0
 * @return LineaVentaDto
 */
fun LineaVenta.toLineaVentaDto(): LineaVentaDto {
    return when (this.producto) {
        is Butaca -> {
            LineaVentaDto(
                id = this.id.toString(),
                producto = this.producto.toProductoDto(),
                tipoProducto = "Butaca",
                cantidad = this.cantidad,
                precio = this.precio,
                createdAt = this.createdAt.toString(),
                updatedAt = this.updatedAt.toString(),
            )
        }

        is Complemento -> {
            LineaVentaDto(
                id = this.id.toString(),
                producto = this.producto.toProductoDto(),
                tipoProducto = "Complemento",
                cantidad = this.cantidad,
                precio = this.precio,
                createdAt = this.createdAt.toString(),
                updatedAt = this.updatedAt.toString(),
            )
        }

        else -> throw IllegalArgumentException("Tipo de producto no soportado")
    }
}

/**
 * Función para el traspaso de Venta a VentaDto
 * @author German Fernandez
 * @since 1.0.0
 * @return VentaDto
 */
fun Venta.toVentaDto(): VentaDto {
    return VentaDto(
        id = this.id.toString(),
        cliente = this.cliente.toClienteDto(),
        lineas = this.lineas.map { it.toLineaVentaDto() },
        total = this.lineas.sumOf { it.precio },
        fechaCompra = this.fechaCompra.toString(),
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString(),
        isDeleted = this.isDeleted
    )
}