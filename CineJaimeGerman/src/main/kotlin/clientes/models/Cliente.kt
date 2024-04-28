package org.example.clientes.models

import java.time.LocalDate

/**
 * Clase Cliente
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
data class Cliente(
    val id: Long = -1,
    val nombre: String,
    val email: String,
    val numSocio: String,
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now(),
    val isDeleted: Boolean = false
)