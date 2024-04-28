package org.example.ventas.service

import com.github.michaelbull.result.Result
import database.VentaEntity
import org.example.clientes.models.Cliente
import org.example.ventas.errors.VentaError
import org.example.ventas.models.LineaVenta
import org.example.ventas.models.Venta
import java.io.File
import java.time.LocalDate
import java.util.*

/**
 * Interfaz para el manejo del servicio de ventas
 * @author German Fernandez
 * @since 1.0.0
 */
interface VentaService {
    fun getById(id: UUID): Result<Venta, VentaError>
    fun createVenta(venta: Venta): Result<Venta, VentaError>
    fun totalVentasByDate(fechaCompra: LocalDate): List<VentaEntity>
    fun exportToJson(venta: Venta, jsonFile: File): Result<Unit, VentaError>
    fun exportCompraToHtml(venta: Venta, htmlFile: File): Result<Unit, VentaError>
    fun exportDevolucionToHtml(venta: Venta, htmlFile: File): Result<Unit, VentaError>
}