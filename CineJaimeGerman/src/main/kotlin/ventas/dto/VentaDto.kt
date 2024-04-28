package org.example.ventas.dto

import org.example.clientes.dto.ClienteDto
import kotlinx.serialization.Serializable

/**
 * Clase Dto de ventas
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
@Serializable
data class VentaDto(
    val id: String,
    val cliente: ClienteDto,
    val lineas: List<LineaVentaDto>,
    val total: Double,
    val fechaCompra: String,
    val createdAt: String,
    val updatedAt: String,
    val isDeleted: Boolean
)