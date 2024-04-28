package org.example.clientes.mappers

import database.ClienteEntity
import org.example.clientes.dto.ClienteDto
import org.example.clientes.models.Cliente
import java.time.LocalDate

/**
 * Función para el traspaso de ClienteEntity a Cliente
 * @author German Fernandez
 * @since 1.0.0
 * @return Cliente
 */
fun ClienteEntity.toCliente(): Cliente {
    return Cliente(
        id = this.id,
        nombre = this.nombre,
        email = this.email,
        numSocio = this.numSocio,
        createdAt = LocalDate.parse(this.created_at),
        updatedAt = LocalDate.parse(this.updated_at),
        isDeleted = this.is_deleted.toInt() == 1
    )
}

/**
 * Función para el traspaso de Cliente a ClienteDto
 * @author German Fernandez
 * @since 1.0.0
 * @return ClienteDto
 */
fun Cliente.toClienteDto(): ClienteDto {
    return ClienteDto(
        id = this.id,
        nombre = this.nombre,
        email = this.email,
        numSocio = this.numSocio,
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString(),
        isDeleted = this.isDeleted
    )
}