package org.example.productos.dto

import kotlinx.serialization.Serializable

/**
 * Clase ButacaDto
 * @property id Id de la butaca
 * @property nombre nombre de la butaca
 * @property tipo tipo de producto
 * @property precio precio de la butaca
 * @property fila fila de la butaca
 * @property columna columna de la butaca
 * @property tipoButaca tipo de la butaca (vip, normal)
 * @property estadoButaca estado de la butaca (Fuera de servicio, mantenimiento, activa)
 * @property ocupacionButaca ocupacion de la butaca (Libre, reservada, ocupada)
 * @property createdAt fecha de creacion
 * @property updatedAt fecha de actualizacion
 * @property isDeleted borrado logico
 * @author Jaime Leon
 * @since 1.0.0
 */
@Serializable
data class ButacaDto(
    val id: String,
    val nombre: String,
    val tipo: String,
    val precio: Double,
    val fila: Int,
    val columna: Int,
    val tipoButaca: String,
    val estadoButaca: String,
    val ocupacionButaca: String,
    val createdAt: String,
    val updatedAt: String? = null,
    val isDeleted: Boolean? = false
)