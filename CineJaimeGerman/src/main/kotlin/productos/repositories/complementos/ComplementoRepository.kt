package org.example.productos.repositories.complementos

import org.example.productos.models.complementos.Complemento

/**
 * Interfaz ButacaRepository
 * Define las operaciones disponibles para acceder y manipular los complementos en un repositorio.
 * @since 1.0.0
 * @author Jaime leon
 */
interface ComplementoRepository {
    fun findAll(): List<Complemento>
    fun findById(id: String): Complemento?
    fun findByNombre(nombre: String): Complemento?
    fun save (item: Complemento): Complemento
    fun update(id: String, item: Complemento): Complemento?
    fun delete(id: String, logical: Boolean): Complemento?
}