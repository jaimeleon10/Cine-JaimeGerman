package org.example.ventas.repositories

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import database.VentaEntity
import org.example.clientes.models.Cliente
import org.example.clientes.repositories.ClienteRepository
import org.example.database.service.SqlDeLightManager
import org.example.productos.mappers.toComplemento
import org.example.productos.mappers.toComplementoDto
import org.example.productos.repositories.butacas.ButacaRepository
import org.example.productos.repositories.complementos.ComplementoRepository
import org.example.ventas.errors.VentaError
import org.example.ventas.mappers.toLineaVenta
import org.example.ventas.mappers.toVenta
import org.example.ventas.models.LineaVenta
import org.example.ventas.models.Venta
import org.lighthousegames.logging.logging
import java.time.LocalDate
import java.util.*

private val logger = logging()

/**
 * Implementacion de la interfaz VentaRepository para la gestión del repositorio de ventas con la base de datos
 * @param dbManager gestor de base de datos
 * @param butacaRepository repositorio de butacas
 * @param complementoRepository repositorio de complementos
 * @param clienteRepository repositorio de clientes
 * @author German Fernandez
 * @since 1.0.0
 */
class VentaRepositoryImpl(
    val dbManager: SqlDeLightManager,
    private val butacaRepository: ButacaRepository,
    private val complementoRepository: ComplementoRepository,
    private val clienteRepository: ClienteRepository
) : VentaRepository {
    private val db = dbManager.databaseQueries

    /**
     * Funcion para buscar todas las ventas
     * @param cliente cliente que realiza las ventas
     * @param lineas lineas que contienen las ventas
     * @param fechaCompra fecha de compra de las ventas
     * @author German Fernandez
     * @since 1.0.0
     * @return Listado de ventas
     */
    override fun findAll(cliente: Cliente, lineas: List<LineaVenta>, fechaCompra:LocalDate): List<Venta> {
        logger.debug { "Buscando todas las ventas" }
        return db.selectAllVentas().executeAsList().map { it.toVenta(cliente, lineas, fechaCompra) }
    }

    /**
     * Funcion para sacar todas las ventas por fecha
     * @param fechaCompra fecha de compra de las ventas
     * @author German Fernandez
     * @since 1.0.0
     * @return Listado de VentaEntity
     */
    override fun totalVentasByDate(fechaCompra: LocalDate): List<VentaEntity> {
        logger.debug { "Obteniendo recaudación total de ventas de fecha: ${fechaCompra}" }
        return db.selectVentasByDate(fechaCompra.toString()).executeAsList()
    }

    /**
     * Funcion para buscar una venta por su id
     * @param id id para la busqueda
     * @author German Fernandez
     * @since 1.0.0
     * @return Venta o nulo
     */
    override fun findById(id: UUID): Venta? {
        logger.debug { "Obteniendo venta por id: $id" }

        if (db.existsVenta(id.toString()).executeAsOne()) {
            val ventaEntity = db.selectVentaById(id.toString()).executeAsOne()
            val cliente = clienteRepository.findById(ventaEntity.cliente_id)!!
            val lineasVenta = db.selectAllLineasVentaByVentaId(ventaEntity.id).executeAsList()
                .map {
                    if (it.producto_tipo == "Butaca") {
                        it.toLineaVenta(butacaRepository.findById(it.producto_id)!!)
                    } else {
                        it.toLineaVenta(complementoRepository.findById(it.producto_id)!!)
                    }
                }
            return ventaEntity.toVenta(cliente, lineasVenta, LocalDate.parse(ventaEntity.fecha_compra))
        }
        return null
    }

    /**
     * Funcion para salvar una venta
     * @param venta venta a salvar
     * @author German Fernandez
     * @since 1.0.0
     * @return Venta
     */
    override fun save(venta: Venta): Venta {
        logger.debug { "Guardando venta: $venta" }
        db.transaction {
            db.insertVenta(
                id = venta.id.toString(),
                cliente_id = venta.cliente.id,
                total = venta.total,
                fecha_compra = LocalDate.now().toString(),
                created_at = venta.createdAt.toString(),
                updated_at = venta.updatedAt.toString()
            )
        }

        venta.lineas.forEach {
            db.transaction {
                db.insertLineaVenta(
                    id = it.id.toString(),
                    venta_id = venta.id.toString(),
                    producto_id = it.producto.id,
                    producto_tipo = it.producto.tipo,
                    cantidad = it.cantidad.toLong(),
                    precio = it.precio,
                    created_at = it.createdAt.toString(),
                    updated_at = it.updatedAt.toString()
                )
            }
        }
        return venta
    }

    /**
     * Funcion para actualizar una venta por su id
     * @param id id para la busqueda
     * @param venta datos de la venta modificada
     * @author German Fernandez
     * @since 1.0.0
     * @return Venta o nulo
     */
    override fun update(id: UUID, venta: Venta): Venta? {
        logger.debug { "Actualizando venta por id: $id" }
        var result = this.findById(id) ?: return null
        val timeStamp = LocalDate.now()

        result = result.copy(
            cliente = venta.cliente,
            lineas = venta.lineas,
            updatedAt = timeStamp
        )

        db.updateVenta(
            id = venta.id.toString(),
            cliente_id = venta.cliente.id,
            total = venta.total,
            fecha_compra = venta.fechaCompra.toString(),
            updated_at = timeStamp.toString(),
            is_deleted = if (venta.isDeleted) 1 else 0
        )
        return result
    }

    /**
     * Funcion para borrar una venta por su id
     * @param id id para la busqueda
     * @author German Fernandez
     * @since 1.0.0
     * @return Venta o nulo
     */
    override fun delete(id: UUID): Venta? {
        logger.debug { "Borrando venta por id: $id" }
        val result = this.findById(id) ?: return null
        val timeStamp = LocalDate.now()

        db.updateVenta(
            id = result.id.toString(),
            cliente_id = result.cliente.id,
            total = result.total,
            fecha_compra = result.fechaCompra.toString(),
            updated_at = timeStamp.toString(),
            is_deleted = if (result.isDeleted) 1 else 0
        )
        return result.copy(isDeleted = true, updatedAt = timeStamp)
    }

    /**
     * Funcion para validar un cliente
     * @param cliente cliente a validar
     * @author German Fernandez
     * @since 1.0.0
     * @return cliente o un error de venta
     */
    override fun validateCliente(cliente: Cliente): Result<Cliente, VentaError> {
        logger.debug { "Validando cliente: $cliente" }
        return clienteRepository.findById(cliente.id)
            ?.let { Ok(it) }
            ?: Err(VentaError.VentaNoValida("Cliente no encontrado con id: ${cliente.id}"))
    }

    /**
     * Funcion para validar lineas de ventas
     * @param lineas lineas de ventas a validar
     * @author German Fernandez
     * @since 1.0.0
     * @return listado de lineas de venta o un error de venta
     */
    override fun validateLineas(lineas: List<LineaVenta>): Result<List<LineaVenta>, VentaError> {
        logger.debug { "Validando lineas - Existen Productos: $lineas" }
        lineas.forEach {
            if (it.tipoProducto == "Butaca") {
                butacaRepository.findById(it.producto.id)
                    ?: return Err(VentaError.VentaNoValida("Producto no encontrado con id: ${it.producto.id}"))
            } else {
                complementoRepository.findById(it.producto.id)
                    ?: return Err(VentaError.VentaNoValida("Producto no encontrado con id: ${it.producto.id}"))
            }
        }

        logger.debug { "Validando lineas - Cantidad y Stock de productos: $lineas" }
        lineas.forEach {
            if (it.cantidad <= 0) {
                return Err(VentaError.VentaNoValida("La cantidad de productos debe ser mayor que 0"))
            }
            if (it.tipoProducto == "Complemento") {
                complementoRepository.findById(it.producto.id)?.let { producto ->
                    if (it.cantidad > producto.stock) {
                        return Err(VentaError.VentaNoValida("No hay suficiente stock para el producto: ${producto.nombre}, stock: ${producto.stock} cantidad: ${it.cantidad}"))
                    }
                }
            }
        }
        return Ok(lineas)
    }

    /**
     * Funcion para actualizar el stock de un producto en una linea de venta
     * @param lineas listado de lineas de venta
     * @author German Fernandez
     * @since 1.0.0
     * @return listado de lineas de venta o un error de venta
     */
    override fun actualizarStock(lineas: List<LineaVenta>): Result<List<LineaVenta>, VentaError> {
        logger.debug { "Actualizando stock de productos: $lineas" }
        lineas.forEach {
            if (it.tipoProducto == "Complemento") {
                complementoRepository.findById(it.producto.id)?.let { complemento ->
                    val updateProducto = complemento.toComplementoDto().copy(stock = complemento.stock - it.cantidad).toComplemento()
                    complementoRepository.update(complemento.id, updateProducto)
                        ?: return Err(VentaError.VentaNoValida("Error al actualizar el stock del producto: ${complemento.nombre}"))
                }
            }
        }
        return Ok(lineas)
    }
}