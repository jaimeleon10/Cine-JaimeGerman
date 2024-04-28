package org.example.productos.dto

import kotlinx.serialization.Serializable

/**
 * Clase ProductoDto
 * @property id Id del producto
 * @property nombre nombre del producto
 * @property tipo tipo de producto
 * @property precio precio del producto
 * @property filaButaca fila de la butaca o nulo
 * @property columnaButaca columna de la butaca o nulo
 * @property tipoButaca tipo de la butaca (vip, normal) o nulo
 * @property estadoButaca estado de la butaca (Fuera de servicio, mantenimiento, activa) o nulo
 * @property ocupacionButaca ocupacion de la butaca (Libre, reservada, ocupada) o nulo
 * @property categoriaComplemento categoria del complemento o nulo
 * @property stockComplemento stock del complemento o nulo
 * @property createdAt fecha de creacion
 * @property updatedAt fecha de actualizacion
 * @property isDeleted borrado logico
 * @author Jaime Leon
 * @since 1.0.0
 */
@Serializable
data class ProductoDto (
    val id: String,
    val nombre: String,
    val tipo: String,
    val precio: Double,
    val filaButaca: Int?,
    val columnaButaca: Int?,
    val tipoButaca: String?,
    val estadoButaca: String?,
    val ocupacionButaca: String?,
    val categoriaComplemento: String?,
    val stockComplemento: Int?,
    val createdAt: String,
    val updatedAt: String,
    val isDeleted: Boolean?
)