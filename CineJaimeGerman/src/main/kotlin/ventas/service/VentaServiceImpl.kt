package org.example.ventas.service

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen
import database.VentaEntity
import org.example.ventas.errors.VentaError
import org.example.ventas.models.Venta
import org.example.ventas.repositories.VentaRepository
import org.example.ventas.storage.VentaStorageHtmlCompraImpl
import org.example.ventas.storage.VentaStorageHtmlDevolucionImpl
import org.example.ventas.storage.VentaStorageJsonImpl
import org.lighthousegames.logging.logging
import java.io.File
import java.time.LocalDate
import java.util.*

private val logger = logging()

/**
 * Implementacion para la gestion del servicio de ventas
 * @param ventaRepository repositorio de ventas
 * @param ventaStorageJson storage de ventas en json
 * @param ventaStorageHtmlCompra storage de compra en html
 * @param ventaStorageHtmlDevolucion storage de devolucion en html
 * @author German Fernandez
 * @since 1.0.0
 */
class VentaServiceImpl(
    private val ventaRepository: VentaRepository,
    private val ventaStorageJson: VentaStorageJsonImpl,
    private val ventaStorageHtmlCompra: VentaStorageHtmlCompraImpl,
    private val ventaStorageHtmlDevolucion: VentaStorageHtmlDevolucionImpl
) : VentaService {

    /**
     * Funcion para buscar una venta por un id
     * @param id id de busqueda
     * @author German Fernandez
     * @since 1.0.0
     * @return venta o error de venta
     */
    override fun getById(id: UUID): Result<Venta, VentaError> {
        logger.debug { "Obteniendo venta por id: $id" }
        return ventaRepository.findById(id)
            ?.let { Ok(it) }
            ?: Err(VentaError.VentaNoEncontrada("Venta no encontrada con id: $id"))
    }

    /**
     * Funcion para crear una venta
     * @param venta venta para crear
     * @author German Fernandez
     * @since 1.0.0
     * @return venta o error de venta
     */
    override fun createVenta(venta: Venta): Result<Venta, VentaError> {
        logger.debug { "Creando venta: $venta" }
        return ventaRepository.validateCliente(venta.cliente)
            .andThen { ventaRepository.validateLineas(venta.lineas) }
            .andThen { ventaRepository.actualizarStock(venta.lineas) }
            .andThen { Ok(ventaRepository.save(venta)) }
    }

    /**
     * Funcion para buscar el total de ventas en una fecha determinada
     * @param fechaCompra fecha de busqueda
     * @author German y Jaime Leon
     * @since 1.0.0
     * @return listado de ventaEntity
     */
    override fun totalVentasByDate(fechaCompra: LocalDate): List<VentaEntity> {
        logger.debug { "Obteniendo recaudación total de ventas de fecha: $fechaCompra" }
        return ventaRepository.totalVentasByDate(fechaCompra)
    }

    /**
     * Funcion para exportar venta a un archivo json
     * @param venta venta a exportar
     * @param jsonFile nombre del fichero json
     * @author German
     * @since 1.0.0
     * @return unit o error de venta
     */
    override fun exportToJson(venta: Venta, jsonFile: File): Result<Unit, VentaError> {
        logger.debug { "Exportando venta a fichero json: $jsonFile" }
        return ventaStorageJson.exportCompra(venta, jsonFile)
    }

    /**
     * Funcion para exportar venta a un archivo html
     * @param venta venta a exportar
     * @param htmlFile nombre del fichero json
     * @author German
     * @since 1.0.0
     * @return unit o error de venta
     */
    override fun exportCompraToHtml(venta: Venta, htmlFile: File): Result<Unit, VentaError> {
        logger.debug { "Exportando compra a fichero html: $htmlFile" }
        return ventaStorageHtmlCompra.export(venta, htmlFile)
    }

    /**
     * Funcion para exportar devolucion a un archivo html
     * @param venta venta a exportar
     * @param htmlFile nombre del fichero json
     * @author German
     * @since 1.0.0
     * @return unit o error de venta
     */
    override fun exportDevolucionToHtml(venta: Venta, htmlFile: File): Result<Unit, VentaError> {
        logger.debug { "Exportando devolucion a fichero html: $htmlFile" }
        return ventaStorageHtmlDevolucion.export(venta, htmlFile)
    }
}