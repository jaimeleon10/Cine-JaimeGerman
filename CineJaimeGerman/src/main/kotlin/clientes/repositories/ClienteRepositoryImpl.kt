package org.example.clientes.repositories

import org.example.clientes.mappers.toCliente
import org.example.clientes.models.Cliente
import org.example.database.service.SqlDeLightManager
import org.lighthousegames.logging.logging
import java.time.LocalDate

private val logger = logging()

/**
 * Clase implementacion del repositorio de clientes
 * @property dbManager implementacion de la base de datos
 * @property db conexion con la base de datos
 * @author German Fernandez y Jaime Leon
 * @since 1.0.0
 */
class ClienteRepositoryImpl(
    val dbManager: SqlDeLightManager
) : ClienteRepository {
    private val db = dbManager.databaseQueries

    /**
     * Función para buscar todos los clientes
     * @author German Fernandez
     * @since 1.0.0
     * @return Listado de clientes
     */
    override fun findAll(): List<Cliente> {
        logger.debug { "Buscando todos los clientes" }
        return db.selectAllClientes().executeAsList().map { it.toCliente() }
    }

    /**
     * Función para buscar un cliente por id
     * @param id id de busqueda
     * @author German Fernandez
     * @since 1.0.0
     * @return cliente o nulo
     */
    override fun findById(id: Long): Cliente? {
        logger.debug { "Buscando cliente por id: $id" }
        return db.selectClienteById(id).executeAsOneOrNull()?.toCliente()
    }

    /**
     * Función para buscar un cliente por el numero de socio
     * @param numSocio numero de socio de busqueda
     * @author German Fernandez
     * @since 1.0.0
     * @return Cliente o nulo
     */
    override fun findByNumSocio(numSocio: String): Cliente? {
        logger.debug { "Buscando cliente por número de socio: $numSocio" }
        return db.selectClienteByNumSocio(numSocio).executeAsOneOrNull()?.toCliente()
    }

    /**
     * Función para guardar un cliente
     * @param cliente cliente para guardar
     * @author German Fernandez
     * @since 1.0.0
     * @return cliente
     */
    override fun save(cliente: Cliente): Cliente {
        logger.debug { "Guardando cliente: $cliente" }
        val timeStamp = LocalDate.now().toString()
        db.transaction {
            db.insertCliente(
                nombre = cliente.nombre,
                email = cliente.email,
                numSocio = cliente.numSocio,
                created_at = timeStamp,
                updated_at = timeStamp,
            )
        }
        return db.selectClienteLastInserted().executeAsOne().toCliente()
    }

    /**
     * Función para actualizar un cliente por id
     * @param id id del cliente que queremos actualizar
     * @param cliente cliente actualizado para modificar datos del actual
     * @author German Fernandez
     * @since 1.0.0
     * @return Cliente o nulo
     */
    override fun update(id: Long, cliente: Cliente): Cliente? {
        logger.debug { "Actualizando cliente por id: $id" }
        var result = this.findById(id) ?: return null
        val timeStamp = LocalDate.now()

        result = result.copy(
            nombre = cliente.nombre,
            email = cliente.email,
            numSocio = cliente.numSocio,
            isDeleted = cliente.isDeleted,
            updatedAt = timeStamp
        )

        db.updateCliente(
            nombre = result.nombre,
            email = result.email,
            numSocio = result.numSocio,
            updated_at = timeStamp.toString(),
            is_deleted = if (result.isDeleted) 1 else 0,
            id = id,
        )
        return result
    }

    /**
     * Función para realizar un borrado logico un cliente por id
     * @param id id del cliente que queremos hacer el borrado
     * @author German Fernandez
     * @since 1.0.0
     * @return Cliente o nulo
     */
    override fun delete(id: Long): Cliente? {
        logger.debug { "Borrando cliente por id: $id" }
        val result = this.findById(id) ?: return null

        val timeStamp = LocalDate.now()
        db.updateCliente(
            nombre = result.nombre,
            email = result.email,
            numSocio = result.numSocio,
            is_deleted = 1,
            updated_at = timeStamp.toString(),
            id = result.id,
        )
        return result.copy(isDeleted = true, updatedAt = timeStamp)
    }
}