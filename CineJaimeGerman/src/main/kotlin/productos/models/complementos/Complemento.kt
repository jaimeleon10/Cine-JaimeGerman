package org.example.productos.models.complementos

import org.example.productos.models.productos.Producto
import java.time.LocalDate

/**
 * Clase Complemento
 * Representa un complemento que hereda de la clase Producto.
 * @param id Identificador único del complemento
 * @param nombre Nombre del complemento
 * @param tipo Tipo del complemento (por defecto, "Complemento")
 * @param precio Precio del complemento
 * @param stock Stock disponible del complemento
 * @param categoria Categoría del complemento (Enum CategoriaComplemento)
 * @param createdAt Fecha de creación del complemento (por defecto, fecha actual)
 * @param updatedAt Fecha de actualización del complemento (por defecto, null)
 * @param isDeleted Indicador de borrado lógico del complemento (por defecto, false)
 * @since 1.0.0
 * @author Jaime leon
 */
class Complemento (
    id: String,
    nombre: String,
    tipo: String = "Complemento",
    precio: Double,
    var stock: Int,
    val categoria: CategoriaComplemento,
    createdAt: LocalDate = LocalDate.now(),
    updatedAt: LocalDate? = null,
    isDeleted: Boolean? = false
): Producto(id, nombre, tipo, precio, createdAt, updatedAt, isDeleted) {

    /**
     * Representación de cadena del complemento
     * @return Representación en forma de cadena del complemento
     * @since 1.0.0
     * @author Jaime leon
     */
    override fun toString(): String {
        return "Complemento(id: $id, nombre: $nombre, tipo: $tipo, precio: $precio, stock: $stock, categoria: $categoria, createdAt: $createdAt, updatedAt: $updatedAt, isDeleted: $isDeleted)"
    }
}