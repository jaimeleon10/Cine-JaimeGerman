package org.example.productos.services.storage

import org.example.config.Config
import org.example.productos.dto.ComplementoDto
import org.example.productos.exceptions.StorageException
import org.example.productos.mappers.toComplemento
import org.example.productos.models.complementos.Complemento
import org.lighthousegames.logging.logging
import java.time.LocalDate
import kotlin.io.path.Path

private val logger = logging()

/**
 * Clase para el manejo del almacenaje de complementos
 * @since 1.0.0
 * @author Jaime leon
 */
class StorageComplementos {

    /**
     * Función para cargar todos los complementos de un archivo csv
     * @param fileName nombre del archivo
     * @author Jaime Leon
     * @since 1.0.0
     * @return listado de complementos
     */
    fun load(fileName: String): List<Complemento> {
        try {
            logger.debug { "Cargando complementos desde fichero csv" }
            val file = Path(Config.storageData, fileName).toFile()
            if (!file.exists()) throw StorageException.LoadException("El fichero $fileName no existe")
            return file.readLines().drop(1)
                .map {
                    val data = it.split(",")
                    ComplementoDto(
                        id = data[0],
                        nombre = data[1],
                        tipo = "Complemento",
                        precio = data[2].toDouble(),
                        stock = data[3].toInt(),
                        categoria = data[4],
                        createdAt = LocalDate.now().toString(),
                        updatedAt = null,
                        isDeleted = null
                    ).toComplemento()
                }
        } catch (e: Exception) {
            logger.error { "Error al cargar el fichero csv de complementos: ${e.message}" }
            throw StorageException.LoadException("Error al cargar el fichero csv de complementos: ${e.message}")
        }
    }
}