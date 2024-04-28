package org.example.ventas.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.ventas.dto.VentaDto
import org.example.ventas.errors.VentaError
import org.example.ventas.mappers.toVentaDto
import org.example.ventas.models.Venta
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

/**
 * Clase para realizar la exportacion de una venta a un archivo json
 * @author German
 * @since 1.0.0
 */
class VentaStorageJsonImpl {
    /**
     * Funcion para exportar venta a un archivo json
     * @param venta venta a exportar
     * @param file nombre del fichero json
     * @author German
     * @since 1.0.0
     * @return unit o error de venta
     */
    fun exportCompra(venta: Venta, file: File): Result<Unit, VentaError> {
        return try {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
            Ok(file.writeText(json.encodeToString<VentaDto>(venta.toVentaDto())))
        } catch (e: Exception) {
            logger.error { "Error al salvar ventas a fichero: ${file.absolutePath}. ${e.message}" }
            Err(VentaError.VentaStorageError("Error al salvar ventas a fichero: ${file.absolutePath}. ${e.message}"))
        }
    }
}