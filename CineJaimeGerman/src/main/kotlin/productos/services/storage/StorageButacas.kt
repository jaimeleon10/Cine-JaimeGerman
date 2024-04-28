package org.example.productos.services.storage

import org.example.config.Config
import org.example.productos.dto.ButacaDto
import org.example.productos.exceptions.StorageException
import org.example.productos.mappers.toButaca
import org.example.productos.mappers.toButacaDto
import org.example.productos.models.butacas.Butaca
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.lighthousegames.logging.logging
import java.io.File
import java.time.LocalDate
import kotlin.io.path.Path

private val logger = logging()

/**
 * Clase para el manejo del almacenaje de butacas
 * @since 1.0.0
 * @author Jaime leon
 */
class StorageButacas {

    /**
     * Función para cargar todas las butacas de un archivo csv
     * @param fileName nombre del archivo
     * @author Jaime Leon
     * @since 1.0.0
     * @return listado de butacas
     */
    fun load(fileName: String): List<Butaca> {
        try {
            logger.debug { "Cargando butacas desde fichero csv" }
            val file = Path(Config.storageData, fileName).toFile()
            if (!file.exists()) throw StorageException.LoadException("El fichero $fileName no existe")
            return file.readLines().drop(1)
                .map {
                    val data = it.split(",")
                    ButacaDto(
                        id = data[0],
                        nombre = "Butaca",
                        tipo = "Butaca",
                        precio = data[1].toDouble(),
                        fila = data[2].toInt(),
                        columna = data[3].toInt(),
                        tipoButaca = data[4],
                        estadoButaca = data[5],
                        ocupacionButaca = data[6],
                        createdAt = LocalDate.now().toString(),
                        updatedAt = null,
                        isDeleted = null
                    ).toButaca()
                }
        } catch (e: Exception) {
            logger.error { "Error al cargar el fichero csv de butacas: ${e.message}" }
            throw StorageException.LoadException("Error al cargar el fichero csv de butacas: ${e.message}")
        }
    }

    /**
     * Función para exportar todas las butacas en un archivo json
     * @param list listado de butacas
     * @author Jaime Leon
     * @since 1.0.0
     */
    @OptIn(ExperimentalSerializationApi::class)
    fun exportButacasJson(list: List<Butaca>, file: File) {
        logger.debug { "Exportando estado del cine al fichero JSON: $file" }
        val json = Json { ignoreUnknownKeys = true; prettyPrint = true; explicitNulls = false }
        file.writeText(
            json.encodeToString<List<ButacaDto>>(list.map { it.toButacaDto() })
        )
    }

}