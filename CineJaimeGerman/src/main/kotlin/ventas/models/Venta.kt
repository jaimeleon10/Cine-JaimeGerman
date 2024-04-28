package org.example.ventas.models

import org.example.clientes.models.Cliente
import org.example.ventas.models.LineaVenta
import java.time.LocalDate
import java.util.UUID

/**
 * Clase de ventas
 * @property id id de la venta
 * @property cliente cliente que realiza la venta
 * @property lineas lineas de venta que componen la venta
 * @property total precio total de todas las lineas
 * @property fechaCompra fecha de compra
 * @property createdAt fecha de creacion
 * @property updatedAt fecha de actualizacion
 * @property isDeleted borrado logico
 * @since 1.0.0
 * @author German Fernandez
 */
data class Venta(
    val id: UUID = UUID.randomUUID(),
    val cliente: Cliente,
    val lineas: List<LineaVenta>,
    val fechaCompra: LocalDate,
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now(),
    var isDeleted: Boolean = false
) {
    val total: Double
        get() = lineas.sumOf { it.precio * it.cantidad }
}