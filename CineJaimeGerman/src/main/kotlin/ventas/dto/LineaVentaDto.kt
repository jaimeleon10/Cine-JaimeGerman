package org.example.ventas.dto

import org.example.productos.dto.ProductoDto
import kotlinx.serialization.Serializable

/**
 * Clase Dto de lineas de venta
 * @property id id de la linea de venta
 * @property producto producto almancenado en la linea de venta
 * @property tipoProducto tipo del producto almacenado (butaca - complemento)
 * @property cantidad cantidad del producto
 * @property precio precio unitario del producto
 * @property createdAt fecha de creacion
 * @property updatedAt fecha de actualizacion
 * @since 1.0.0
 * @author German Fernandez
 */
@Serializable
data class LineaVentaDto(
    val id: String,
    val producto: ProductoDto,
    val tipoProducto: String,
    val cantidad: Int,
    val precio: Double,
    val createdAt: String,
    val updatedAt: String
)