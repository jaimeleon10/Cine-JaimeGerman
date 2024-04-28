package org.example.ventas.models

import org.example.productos.models.productos.Producto
import java.time.LocalDate
import java.util.*

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
data class LineaVenta(
    val id: UUID = UUID.randomUUID(),
    val producto: Producto,
    val tipoProducto: String,
    val cantidad: Int,
    val precio: Double,
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now(),
)