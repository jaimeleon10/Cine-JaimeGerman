package org.example.productos.dto

import kotlinx.serialization.Serializable

/**
 * Clase ComplementoDto
 * @property id Id del complemento
 * @property nombre nombre del complemento
 * @property tipo tipo del complemento
 * @property precio precio del complemento
 * @property stock stock del complemento
 * @property categoria categoria del complemento
 * @property createdAt fecha de creacion
 * @property updatedAt fecha de actualizacion
 * @property isDeleted borrado logico
 * @author Jaime Leon
 * @since 1.0.0
 */
@Serializable
data class ComplementoDto(
    val id: String,
    val nombre: String,
    val tipo: String,
    val precio: Double,
    val stock: Int,
    val categoria: String,
    val createdAt: String,
    val updatedAt: String?,
    val isDeleted: Boolean? = false
)