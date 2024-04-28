package org.example.clientes.repositories

import org.example.clientes.models.Cliente

/**
 * Interfaz del repositorio de clientes
 * @author German Fernandez
 * @since 1.0.0
 */
interface ClienteRepository {
    fun findAll(): List<Cliente>
    fun findById(id: Long): Cliente?
    fun findByNumSocio(numSocio: String): Cliente?
    fun save(cliente: Cliente): Cliente
    fun update(id: Long, cliente: Cliente): Cliente?
    fun delete(id: Long): Cliente?
}