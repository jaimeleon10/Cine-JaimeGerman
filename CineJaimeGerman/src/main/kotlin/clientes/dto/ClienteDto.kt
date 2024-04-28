package org.example.clientes.dto

import kotlinx.serialization.Serializable

/**
 * Clase dto de Cliente
 * @property id Id del cliente
 * @property nombre nombre del cliente
 * @property email email del cliente
 * @property numSocio numero de socio del cliente
 * @property createdAt fecha de creacion
 * @property updatedAt fecha de actualizacion
 * @property isDeleted borrado logico
 * @author German Fernandez
 * @since 1.0.0
 */
@Serializable
data class ClienteDto(
    val id: Long,
    val nombre: String,
    val email: String,
    val numSocio: String,
    val createdAt: String,
    val updatedAt: String,
    val isDeleted: Boolean = false
)