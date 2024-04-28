package org.example.productos.repositories.butacas

import org.example.productos.models.butacas.Butaca
import java.time.LocalDate

/**
 * Interfaz ButacaRepository
 * Define las operaciones disponibles para acceder y manipular las butacas en un repositorio.
 * @since 1.0.0
 * @author Jaime leon
 */
interface ButacaRepository {
    fun findAll(): List<Butaca>
    fun findById(id: String): Butaca?
    fun save (item: Butaca): Butaca
    fun update(id: String, item: Butaca): Butaca?
    fun delete(id: String, logical: Boolean): Butaca?
    fun defaultListButacas(): List<Butaca>
    fun findAllButacasVendidasFecha(fechaCompra: LocalDate): List<Butaca>
}