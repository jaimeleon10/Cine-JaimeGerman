package org.example.productos.models.butacas

import org.example.productos.models.productos.Producto
import java.time.LocalDate

/**
 * Clase Butaca
 * Esta clase representa una butaca que hereda de la clase Producto.
 * @param id Identificador único de la butaca
 * @param nombre Nombre de la butaca
 * @param tipo Tipo de la butaca
 * @param precio Precio de la butaca
 * @param fila Fila de la butaca
 * @param columna Columna de la butaca
 * @param tipoButaca Tipo de butaca (Enum TipoButaca)
 * @param estadoButaca Estado de la butaca (Enum EstadoButaca)
 * @param ocupacionButaca Ocupación de la butaca (Enum OcupacionButaca)
 * @param createdAt Fecha de creación de la butaca
 * @param updatedAt Fecha de actualización de la butaca
 * @param isDeleted Indicador de borrado lógico de la butaca
 * @since 1.0.0
 * @author Jaime leon
 */
class Butaca (
    id: String = "",
    nombre: String = "Butaca",
    tipo: String = "Butaca",
    precio: Double,
    var fila: Int,
    var columna: Int,
    var tipoButaca: TipoButaca,
    var estadoButaca: EstadoButaca,
    var ocupacionButaca: OcupacionButaca,
    createdAt: LocalDate = LocalDate.now(),
    updatedAt: LocalDate? = null,
    isDeleted: Boolean? = false
): Producto(id, nombre, tipo, precio, createdAt, updatedAt, isDeleted) {

    /**
     * Representación de cadena de la butaca
     * @return Representación en forma de cadena de la butaca
     * @since 1.0.0
     * @author Jaime leon
     */
    override fun toString(): String {
        return "Butaca(id: $id, nombre: $nombre, tipo: $tipo, precio: $precio, fila: $fila, columna: $columna, tipoButaca: $tipoButaca, estadoButaca: $estadoButaca, ocupacionButaca: $ocupacionButaca, createdAt: $createdAt, updatedAt: $updatedAt, isDeleted: $isDeleted)"
    }
}