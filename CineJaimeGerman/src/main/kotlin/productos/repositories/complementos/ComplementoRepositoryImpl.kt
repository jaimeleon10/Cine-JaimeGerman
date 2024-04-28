package org.example.productos.repositories.complementos

import org.example.productos.dto.ComplementoDto
import org.example.productos.exceptions.ButacaExceptions
import org.example.productos.exceptions.ComplementoExceptions
import org.example.productos.mappers.toComplemento
import org.example.productos.mappers.toComplementoDto
import org.example.productos.models.complementos.Complemento
import org.example.productos.services.database.DataBaseManager
import org.lighthousegames.logging.logging
import java.sql.Statement.RETURN_GENERATED_KEYS
import java.time.LocalDate

private val logger = logging()

/**
 * Implementación de la interfaz ComplementoRepository.
 * Proporciona métodos para acceder y manipular los complementos en un repositorio.
 * @since 1.0.0
 * @author Jaime leon
 */
class ComplementoRepositoryImpl: ComplementoRepository {

    /**
     * Función para buscar todos los complementos
     * @author Jaime Leon
     * @since 1.0.0
     * @return listado de complementos
     */
    override fun findAll(): List<Complemento> {
        logger.debug { "Buscando todos los complementos" }
        try {
            val complementos = mutableListOf<Complemento>()
            DataBaseManager.use { db ->
                val sql = "SELECT * FROM Complementos"
                val result = DataBaseManager.connection?.prepareStatement(sql)!!.executeQuery()
                while (result.next()) {
                    val complemento = ComplementoDto(
                        id = result.getString("id"),
                        nombre = result.getString("nombre"),
                        tipo = result.getString("tipo"),
                        precio = result.getDouble("precio"),
                        stock = result.getInt("stock"),
                        categoria = result.getString("categoria"),
                        createdAt = result.getString("created_at"),
                        updatedAt = result.getString("updated_at"),
                        isDeleted = result.getBoolean("is_deleted")
                    ).toComplemento()
                    complementos.add(complemento)
                }
            }
            return complementos
        } catch (e: Exception) {
            logger.error { "Error al buscar todos los complementos" }
            throw ComplementoExceptions.ComplementoNotFetchedException("Error al buscar todos los complementos")
        }
    }

    /**
     * Función para buscar un complemento por su id
     * @param id id a buscar
     * @author Jaime Leon
     * @since 1.0.0
     * @return complemento o nulo
     */
    override fun findById(id: String): Complemento? {
        logger.debug { "Buscando complemento por id $id" }
        try {
            var complemento: Complemento? = null
            DataBaseManager.use { db ->
                val sql = "SELECT * FROM Complementos WHERE id = ?"
                val statement = db.connection?.prepareStatement(sql)!!
                statement.setString(1, id)
                val result = statement.executeQuery()
                if (result.next()) {
                    complemento = ComplementoDto(
                        id = result.getString("id"),
                        nombre = result.getString("nombre"),
                        tipo = result.getString("tipo"),
                        precio = result.getDouble("precio"),
                        stock = result.getInt("stock"),
                        categoria = result.getString("categoria"),
                        createdAt = result.getString("created_at"),
                        updatedAt = result.getString("updated_at"),
                        isDeleted = result.getBoolean("is_deleted")
                    ).toComplemento()
                }
            }
            return complemento
        } catch (e: Exception) {
            logger.error { "Error al buscar complemento por id $id" }
            throw ComplementoExceptions.ComplementoNotFoundException("Error al buscar complemento por id $id")
        }
    }

    /**
     * Función para buscar un complemento por su nombre
     * @param nombre nombre a buscar
     * @author Jaime Leon
     * @since 1.0.0
     * @return complemento o nulo
     */
    override fun findByNombre(nombre: String): Complemento? {
        logger.debug { "Buscando complemento por nombre $nombre" }
        try {
            var complemento: Complemento? = null
            DataBaseManager.use { db ->
                val sql = "SELECT * FROM Complementos WHERE nombre = ?"
                val statement = db.connection?.prepareStatement(sql)!!
                statement.setString(1, nombre)
                val result = statement.executeQuery()
                if (result.next()) {
                    complemento = ComplementoDto(
                        id = result.getString("id"),
                        nombre = result.getString("nombre"),
                        tipo = result.getString("tipo"),
                        precio = result.getDouble("precio"),
                        stock = result.getInt("stock"),
                        categoria = result.getString("categoria"),
                        createdAt = result.getString("created_at"),
                        updatedAt = result.getString("updated_at"),
                        isDeleted = result.getBoolean("is_deleted")
                    ).toComplemento()
                }
            }
            return complemento
        } catch (e: Exception) {
            logger.error { "Error al buscar complemento por nombre $nombre" }
            throw ComplementoExceptions.ComplementoNotFoundException("Error al buscar complemento por nombre $nombre")
        }
    }

    /**
     * Función para guardar un complemento nuevo
     * @param item complemento nuevo a guardar
     * @author Jaime Leon
     * @since 1.0.0
     * @return complemento
     */
    override fun save(item: Complemento): Complemento {
        logger.debug { "Guardando butaca $item" }
        try {
            var complemento: ComplementoDto = item.toComplementoDto()
            val timeStamp = LocalDate.now()
            DataBaseManager.use { db ->
                val sql = "INSERT INTO Complementos (id, nombre, tipo, precio, stock, categoria, created_at, updated_at, is_deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
                val statement = db.connection?.prepareStatement(sql, RETURN_GENERATED_KEYS)!!
                statement.setString(1, complemento.id)
                statement.setString(2, complemento.nombre)
                statement.setString(3, complemento.tipo)
                statement.setDouble(4, complemento.precio)
                statement.setInt(5, complemento.stock)
                statement.setString(6, complemento.categoria)
                statement.setString(7, timeStamp.toString())
                statement.setString(8, timeStamp.toString())
                statement.setBoolean(9, false)
                statement.executeUpdate()

                // Si queremos recuperar el ID generado
                val id = statement.generatedKeys.getString(1)
                complemento = complemento.copy(
                    id = id,
                    createdAt = timeStamp.toString(),
                    updatedAt = timeStamp.toString(),
                    isDeleted = false
                )
            }
            return complemento.toComplemento()
        } catch (e: Exception) {
            logger.error { "Error al guardar complemento $item" }
            throw ComplementoExceptions.ComplementoNotSavedException("Error al guardar complemento $item")
        }
    }

    /**
     * Función para buscar actualizar un complemento por su id
     * @param id id a buscar
     * @param item datos para modificar el complemento
     * @author Jaime Leon
     * @since 1.0.0
     * @return complemento o nulo
     */
    override fun update(id: String, item: Complemento): Complemento? {
        logger.debug { "Actualizando complemento $item" }
        try {
            var complemento: ComplementoDto? = this.findById(id)?.toComplementoDto()
            if (complemento != null) {
                complemento = item.toComplementoDto()
                val timeStamp = LocalDate.now()
                DataBaseManager.use { db ->
                    val sql =
                        "UPDATE Complementos SET nombre = ?, precio = ?, stock = ?, categoria = ?, updated_at = ? WHERE id = ?"
                    val statement = db.connection?.prepareStatement(sql)!!
                    statement.setString(1, complemento?.nombre)
                    statement.setDouble(2, complemento!!.precio)
                    statement.setInt(3, complemento!!.stock)
                    statement.setString(4, complemento?.categoria)
                    statement.setString(5, timeStamp.toString())
                    statement.setString(6, id)
                    statement.executeUpdate()
                    complemento = complemento?.copy(
                        updatedAt = timeStamp.toString()
                    )
                }
            }
            return complemento?.toComplemento()
        } catch (e: Exception) {
            logger.error { "Error al actualizar complemento $item" }
            throw ComplementoExceptions.ComplementoNotUpdatedException("Error al actualizar complemento $item")
        }
    }

    /**
     * Función para borrar un complemento
     * @param id id a buscar
     * @param logical true realiza borrado logico y false fisico
     * @author Jaime Leon
     * @since 1.0.0
     * @return complemento o nulo
     */
    override fun delete(id: String, logical: Boolean): Complemento? {
        logger.debug { "Borrando complemento con id $id" }
        try {
            var complemento: ComplementoDto? = this.findById(id)?.toComplementoDto()
            if (complemento != null) {
                DataBaseManager.use { db ->
                    // Borramos logico
                    if (logical) {
                        val sql = "UPDATE Complementos SET is_deleted = ? WHERE id = ?"
                        val statement = db.connection?.prepareStatement(sql)!!
                        statement.setBoolean(1, true)
                        statement.setString(2, id)
                        statement.executeUpdate()
                        complemento = complemento?.copy(
                            isDeleted = true
                        )
                    } else {
                        // Borramos fisico
                        val sql = "DELETE FROM Complementos WHERE id = ?"
                        val statement = db.connection?.prepareStatement(sql)!!
                        statement.setString(1, id)
                        statement.executeUpdate()
                    }
                }
            }
            return complemento?.toComplemento()
        } catch (e: Exception) {
            logger.error { "Error al borrar complemento con id $id" }
            throw ButacaExceptions.ButacaNotDeletedException("Error al borrar complemento con id $id")
        }
    }
}