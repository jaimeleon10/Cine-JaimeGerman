package org.example.productos.services.productos

import org.example.productos.models.butacas.Butaca
import org.example.productos.models.complementos.Complemento
import java.io.File
import java.time.LocalDate

/**
 * Interfaz ProductoService
 * Define las operaciones disponibles para acceder y manipular las butacas y complementos en un servicio.
 * @since 1.0.0
 * @author Jaime leon
 */
interface ProductoService {
    fun findAllButacas(): List<Butaca>
    fun findAllComplementos(): List<Complemento>
    fun findButacaById(id: String): Butaca
    fun findComplementoById(id: String): Complemento
    fun findComplementoByNombre(nombre: String): Complemento
    fun saveButaca(item: Butaca): Butaca
    fun saveComplemento(item: Complemento): Complemento
    fun updateButaca(id: String, item: Butaca): Butaca
    fun updateComplemento(id: String, item: Complemento): Complemento
    fun deleteButaca(id: String, logical: Boolean): Butaca
    fun deleteComplemento(id: String, logical: Boolean): Complemento
    fun loadComplementosFromCsv(fileName: String): List<Complemento>
    fun loadButacasFromCsv(fileName: String): List<Butaca>
    fun exportButacasToJson(lista:List<Butaca>,fileName: File)
    fun findAllButacasVendidasFecha(fecha: LocalDate): List<Butaca>
    fun defaultListButacas(): List<Butaca>
}