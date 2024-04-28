package org.example.productos.repositories.butacas

import org.example.productos.dto.ButacaDto
import org.example.productos.exceptions.ButacaExceptions
import org.example.productos.mappers.toButaca
import org.example.productos.mappers.toButacaDto
import org.example.productos.models.butacas.Butaca
import org.example.productos.models.butacas.EstadoButaca
import org.example.productos.models.butacas.OcupacionButaca
import org.example.productos.models.butacas.TipoButaca
import org.example.productos.services.database.DataBaseManager
import org.lighthousegames.logging.logging
import java.time.LocalDate

private val logger = logging()

/**
 * Implementación de la interfaz ButacaRepository.
 * Proporciona métodos para acceder y manipular las butacas en un repositorio.
 * @since 1.0.0
 * @author Jaime leon
 */
class ButacaRepositoryImpl: ButacaRepository {

    init {
        DataBaseManager.use {  }
    }

    /**
     * Función para buscar todas las butacas
     * @author Jaime Leon
     * @since 1.0.0
     * @return listado de butacas
     */
    override fun findAll(): List<Butaca> {
        logger.debug { "Buscando todas las butacas" }
        try {
            val butacas = mutableListOf<Butaca>()
            DataBaseManager.use { db ->
                val sql = "SELECT * FROM Butacas"
                val result = DataBaseManager.connection?.prepareStatement(sql)!!.executeQuery()
                while (result.next()) {
                    val butaca = ButacaDto(
                        id = result.getString("id"),
                        nombre = result.getString("nombre"),
                        tipo = result.getString("tipo"),
                        precio = result.getDouble("precio"),
                        fila = result.getInt("filaButaca"),
                        columna = result.getInt("columnaButaca"),
                        tipoButaca = result.getString("tipoButaca"),
                        estadoButaca = result.getString("estadoButaca"),
                        ocupacionButaca = result.getString("ocupacionButaca"),
                        createdAt = result.getString("created_at"),
                        updatedAt = result.getString("updated_at"),
                        isDeleted = result.getBoolean("is_deleted")
                    ).toButaca()
                    butacas.add(butaca)
                }
            }
            return butacas
        } catch (e: Exception) {
            logger.error { "Error al buscar todas las butacas" }
            throw ButacaExceptions.ButacaNotFetchedException("Error al buscar todas las butacas")
        }
    }

    /**
     * Función para buscar una butaca por su id
     * @param id id a buscar
     * @author Jaime Leon
     * @since 1.0.0
     * @return butaca o nulo
     */
    override fun findById(id: String): Butaca? {
        logger.debug { "Buscando butaca por id $id" }
        try {
            var butaca: Butaca? = null
            DataBaseManager.use { db ->
                val sql = "SELECT * FROM Butacas WHERE id = ?"
                val statement = db.connection?.prepareStatement(sql)!!
                statement.setString(1, id)
                val result = statement.executeQuery()
                if (result.next()) {
                    butaca = ButacaDto(
                        id = result.getString("id"),
                        nombre = result.getString("nombre"),
                        tipo = result.getString("tipo"),
                        precio = result.getDouble("precio"),
                        fila = result.getInt("filaButaca"),
                        columna = result.getInt("columnaButaca"),
                        tipoButaca = result.getString("tipoButaca"),
                        estadoButaca = result.getString("estadoButaca"),
                        ocupacionButaca = result.getString("ocupacionButaca"),
                        createdAt = result.getString("created_at"),
                        updatedAt = result.getString("updated_at"),
                        isDeleted = result.getBoolean("is_deleted")
                    ).toButaca()
                }
            }
            return butaca
        } catch (e: Exception) {
            logger.error { "Error al buscar butaca por id $id" }
            throw ButacaExceptions.ButacaNotFoundException("Error al buscar butaca por id $id")
        }
    }

    /**
     * Función para guardar una butaca nueva
     * @param item butaca nueva a guardar
     * @author Jaime Leon
     * @since 1.0.0
     * @return butaca
     */
    override fun save(item: Butaca): Butaca {
        logger.debug { "Guardando butaca $item" }
        try {
            var butaca: ButacaDto = item.toButacaDto()
            val timeStamp = LocalDate.now()
            DataBaseManager.use { db ->
                val sql = "INSERT INTO Butacas (id, nombre, tipo, precio, filaButaca, columnaButaca, tipoButaca, estadoButaca, ocupacionButaca, created_at, updated_at, is_deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                val statement = db.connection?.prepareStatement(sql)!!
                statement.setString(1, butaca.id)
                statement.setString(2, butaca.nombre)
                statement.setString(3, butaca.tipo)
                statement.setDouble(4, butaca.precio)
                statement.setInt(5, butaca.fila)
                statement.setInt(6, butaca.columna)
                statement.setString(7, butaca.tipoButaca)
                statement.setString(8, butaca.estadoButaca)
                statement.setString(9, butaca.ocupacionButaca)
                statement.setString(10, timeStamp.toString())
                statement.setString(11, timeStamp.toString())
                statement.setBoolean(12, false)
                statement.executeUpdate()

                butaca = butaca.copy(
                    createdAt = timeStamp.toString(),
                    updatedAt = timeStamp.toString(),
                    isDeleted = false
                )
            }
            return butaca.toButaca()
        } catch (e: Exception) {
            logger.error { "Error al guardar butaca $item: $e" }
            throw ButacaExceptions.ButacaNotSavedException("Error al guardar butaca $item")
        }
    }

    /**
     * Función para buscar actualizar una butaca por su id
     * @param id id a buscar
     * @param item datos para modificar la butaca
     * @author Jaime Leon
     * @since 1.0.0
     * @return butaca o nulo
     */
    override fun update(id: String, item: Butaca): Butaca? {
        logger.debug { "Actualizando butaca $item" }
        try {
            var butaca: ButacaDto? = this.findById(id)?.toButacaDto()
            if (butaca != null) {
                butaca = item.toButacaDto()
                val timeStamp = LocalDate.now()
                DataBaseManager.use { db ->
                    val sql =
                        "UPDATE Butacas SET precio = ?, filaButaca = ?, columnaButaca = ?, tipoButaca = ?, estadoButaca = ?, ocupacionButaca = ?, updated_at = ? WHERE id = ?"
                    val statement = db.connection?.prepareStatement(sql)!!
                    statement.setDouble(1, butaca!!.precio)
                    statement.setInt(2, butaca!!.fila)
                    statement.setInt(3, butaca!!.columna)
                    statement.setString(4, butaca?.tipoButaca)
                    statement.setString(5, butaca?.estadoButaca)
                    statement.setString(6, butaca?.ocupacionButaca)
                    statement.setString(7, timeStamp.toString())
                    statement.setString(8, id)
                    statement.executeUpdate()
                    butaca = butaca?.copy(
                        updatedAt = timeStamp.toString()
                    )
                }
            }
            return butaca?.toButaca()
        } catch (e: Exception) {
            logger.error { "Error al actualizar butaca $item" }
            throw ButacaExceptions.ButacaNotUpdatedException("Error al actualizar butaca $item")
        }
    }

    /**
     * Función para borrar una butaca
     * @param id id a buscar
     * @param logical true realiza borrado logico y false fisico
     * @author Jaime Leon
     * @since 1.0.0
     * @return butaca o nulo
     */
    override fun delete(id: String, logical: Boolean): Butaca? {
        logger.debug { "Borrando butaca con id $id" }
        try {
            var butaca: ButacaDto? = this.findById(id)?.toButacaDto()
            if (butaca != null) {
                DataBaseManager.use { db ->
                    // Borramos logico
                    if (logical) {
                        val sql = "UPDATE Butacas SET is_deleted = ? WHERE id = ?"
                        val statement = db.connection?.prepareStatement(sql)!!
                        statement.setBoolean(1, true)
                        statement.setString(2, id)
                        statement.executeUpdate()
                        butaca = butaca?.copy(
                            isDeleted = true
                        )
                    } else {
                        // Borramos fisico
                        val sql = "DELETE FROM butacas WHERE id = ?"
                        val statement = db.connection?.prepareStatement(sql)!!
                        statement.setString(1, id)
                        statement.executeUpdate()
                    }
                }
            }
            return butaca?.toButaca()
        } catch (e: Exception) {
            logger.error { "Error al borrar butaca con id $id" }
            throw ButacaExceptions.ButacaNotDeletedException("Error al borrar butaca con id $id")
        }
    }

    /**
     * Función que devuelve una lista del cine por defecto
     * @author Jaime Leon
     * @since 1.0.0
     * @return listado de butacas
     */
    override fun defaultListButacas(): List<Butaca> {
        return listOf(
            Butaca(id = "A1", precio = 5.0, fila = 0, columna = 0, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "A2", precio = 5.0, fila = 0, columna = 1, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "A3", precio = 5.0, fila = 0, columna = 2, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "A4", precio = 5.0, fila = 0, columna = 3, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "A5", precio = 5.0, fila = 0, columna = 4, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "A6", precio = 5.0, fila = 0, columna = 5, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "A7", precio = 5.0, fila = 0, columna = 6, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "B1", precio = 5.0, fila = 1, columna = 0, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "B2", precio = 5.0, fila = 1, columna = 1, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "B3", precio = 8.0, fila = 1, columna = 2, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "B4", precio = 8.0, fila = 1, columna = 3, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "B5", precio = 8.0, fila = 1, columna = 4, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "B6", precio = 5.0, fila = 1, columna = 5, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "B7", precio = 5.0, fila = 1, columna = 6, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "C1", precio = 5.0, fila = 2, columna = 0, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "C2", precio = 5.0, fila = 2, columna = 1, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "C3", precio = 8.0, fila = 2, columna = 2, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "C4", precio = 8.0, fila = 2, columna = 3, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "C5", precio = 8.0, fila = 2, columna = 4, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "C6", precio = 5.0, fila = 2, columna = 5, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "C7", precio = 5.0, fila = 2, columna = 6, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "D1", precio = 5.0, fila = 3, columna = 0, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "D2", precio = 5.0, fila = 3, columna = 1, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "D3", precio = 8.0, fila = 3, columna = 2, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "D4", precio = 8.0, fila = 3, columna = 3, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "D5", precio = 8.0, fila = 3, columna = 4, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "D6", precio = 5.0, fila = 3, columna = 5, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "D7", precio = 5.0, fila = 3, columna = 6, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "E1", precio = 5.0, fila = 4, columna = 0, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "E2", precio = 5.0, fila = 4, columna = 1, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "E3", precio = 5.0, fila = 4, columna = 2, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "E4", precio = 5.0, fila = 4, columna = 3, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "E5", precio = 5.0, fila = 4, columna = 4, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "E6", precio = 5.0, fila = 4, columna = 5, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "E7", precio = 5.0, fila = 4, columna = 6, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE)
        )
    }

    /**
     * Función para buscar todas las butacas vendidas dada una fecha
     * @author Jaime Leon
     * @since 1.0.0
     * @return listado de butacas
     */
    override fun findAllButacasVendidasFecha(fechaCompra: LocalDate): List<Butaca> {
        logger.debug { "Buscando butacas por fecha de compra ${fechaCompra}" }
        try {
            val butacas = mutableListOf<Butaca>()
            DataBaseManager.use { db ->
                val sql = "SELECT * FROM Butacas JOIN LineaVentaEntity ON Butacas.id = LineaVentaEntity.producto_id JOIN VentaEntity ON LineaVentaEntity.venta_id = VentaEntity.id WHERE VentaEntity.fecha_compra <= ?"
                val statement = db.connection?.prepareStatement(sql)!!
                statement.setString(1, fechaCompra.toString())
                val result = statement.executeQuery()
                while (result.next()) {
                    val butaca = ButacaDto(
                        id = result.getString("id"),
                        nombre = result.getString("nombre"),
                        tipo = result.getString("tipo"),
                        precio = result.getDouble("precio"),
                        fila = result.getInt("filaButaca"),
                        columna = result.getInt("columnaButaca"),
                        tipoButaca = result.getString("tipoButaca"),
                        estadoButaca = result.getString("estadoButaca"),
                        ocupacionButaca = result.getString("ocupacionButaca"),
                        createdAt = result.getString("created_at"),
                        updatedAt = result.getString("updated_at"),
                        isDeleted = result.getBoolean("is_deleted")
                    ).toButaca()
                    butacas.add(butaca)
                }
            }
            return butacas
        } catch (e: Exception) {
            logger.error { "Error al buscar butacas por fecha de compra ${fechaCompra}"}
            throw ButacaExceptions.ButacaNotFetchedException("Error al buscar butacas por fecha de compra ${fechaCompra}")
        }

    }
}