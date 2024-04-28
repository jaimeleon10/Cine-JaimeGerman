package org.example.productos.services.productos

import org.example.productos.exceptions.ButacaExceptions
import org.example.productos.exceptions.ComplementoExceptions
import org.example.productos.models.butacas.Butaca
import org.example.productos.models.complementos.Complemento
import org.example.productos.repositories.butacas.ButacaRepository
import org.example.productos.repositories.complementos.ComplementoRepository
import org.example.productos.services.cache.butacas.ButacaCache
import org.example.productos.services.cache.complementos.ComplementoCache
import org.example.productos.services.storage.StorageButacas
import org.example.productos.services.storage.StorageComplementos
import org.lighthousegames.logging.logging
import java.io.File
import java.time.LocalDate

private val logger = logging()

/**
 * Implementación de la interfaz ProductoService.
 * Proporciona métodos para acceder y manipular los productos del repositorio.
 * @since 1.0.0
 * @author Jaime leon
 */
class ProductoServiceImpl(
    private val butacaRepository: ButacaRepository,
    private val complementoRepository: ComplementoRepository,
    private val butacaCache: ButacaCache,
    private val complementoCache: ComplementoCache,
    private val storageComplementos: StorageComplementos,
    private val storageButaca: StorageButacas
) : ProductoService {

    /**
     * Función para buscar todas las butacas
     * @author Jaime Leon
     * @since 1.0.0
     * @return lista de butacas
     */
    override fun findAllButacas(): List<Butaca> {
        logger.debug { "Buscando todas las butacas..." }
        return butacaRepository.findAll()
    }

    /**
     * Función para buscar todos los complementos
     * @author Jaime Leon
     * @since 1.0.0
     * @return lista de complementos
     */
    override fun findAllComplementos(): List<Complemento> {
        logger.debug { "Buscando todos los complementos..." }
        return complementoRepository.findAll()
    }

    /**
     * Función para buscar una butaca por su id
     * @param id id para buscar
     * @author Jaime Leon
     * @since 1.0.0
     * @return butaca
     */
    override fun findButacaById(id: String): Butaca {
        logger.debug { "Buscando butaca por id $id..." }
        return butacaCache.get(id) ?: butacaRepository.findById(id)?.also {
            butacaCache.put(id, it)
        } ?: throw ButacaExceptions.ButacaNotFoundException("Butaca con id $id no encontrada")
    }

    /**
     * Función para buscar un complemento por su id
     * @param id id para buscar
     * @author Jaime Leon
     * @since 1.0.0
     * @return complemento
     */
    override fun findComplementoById(id: String): Complemento {
        logger.debug { "Buscando complemento por id $id..." }
        return complementoCache.get(id) ?: complementoRepository.findById(id)?.also {
            complementoCache.put(id, it)
        } ?: throw ComplementoExceptions.ComplementoNotFoundException("Complemento con id $id no encontrado")
    }

    /**
     * Función para buscar un complemento por su nombre
     * @param nombre nombre para buscar
     * @author Jaime Leon
     * @since 1.0.0
     * @return complemento
     */
    override fun findComplementoByNombre(nombre: String): Complemento {
        logger.debug { "Buscando complemento por nombre $nombre..." }
        return complementoRepository.findByNombre(nombre) ?: throw ComplementoExceptions.ComplementoNotFoundException("Complemento con nombre $nombre no encontrado")
    }

    /**
     * Función para almacenar una butaca
     * @param item butaca a almacenar
     * @author Jaime Leon
     * @since 1.0.0
     * @return butaca
     */
    override fun saveButaca(item: Butaca): Butaca {
        logger.debug { "Guardando butaca $item" }
        return butacaRepository.save(item).also {
            butacaCache.put(item.id, it)
        }
    }

    /**
     * Función para almacenar un complemento
     * @param item complemento a almacenar
     * @author Jaime Leon
     * @since 1.0.0
     * @return complemento
     */
    override fun saveComplemento(item: Complemento): Complemento {
        logger.debug { "Guardando complemento $item" }
        return complementoRepository.save(item).also {
            complementoCache.put(item.id, it)
        }
    }

    /**
     * Función para actualizar una butaca
     * @param id id para buscar
     * @param item datos para modificar la butaca
     * @author Jaime Leon
     * @since 1.0.0
     * @return butaca
     */
    override fun updateButaca(id: String, item: Butaca): Butaca {
        logger.debug { "Actualizando butaca con id $id" }
        // Actualizamos en cache y en la base de datos
        return butacaRepository.update(id, item).also {
            butacaCache.put(id, it!!)
        } ?: throw ButacaExceptions.ButacaNotFoundException("Butaca no encontrada con id $id")
    }

    /**
     * Función para actualizar un complemento
     * @param id id para buscar
     * @param item datos para modificar el complemento
     * @author Jaime Leon
     * @since 1.0.0
     * @return complemento
     */
    override fun updateComplemento(id: String, item: Complemento): Complemento {
        logger.debug { "Actualizando complemento con id $id" }
        // Actualizamos en cache y en la base de datos
        return complementoRepository.update(id, item).also {
            complementoCache.put(id, it!!)
        } ?: throw ComplementoExceptions.ComplementoNotFoundException("Complemento no encontrado con id $id")
    }

    /**
     * Función para borrar una butaca
     * @param id id para buscar
     * @param logical true borrado logico, false borrado fisico
     * @author Jaime Leon
     * @since 1.0.0
     * @return butaca
     */
    override fun deleteButaca(id: String, logical: Boolean): Butaca {
        logger.debug { "Borrando butaca con id $id" }
        return butacaRepository.delete(id, logical).also {
            butacaCache.remove(id)
        } ?: throw ButacaExceptions.ButacaNotDeletedException("Butaca no borrada con id $id")
    }

    /**
     * Función para borrar un complemento
     * @param id id para buscar
     * @param logical true borrado logico, false borrado fisico
     * @author Jaime Leon
     * @since 1.0.0
     * @return complemento
     */
    override fun deleteComplemento(id: String, logical: Boolean): Complemento {
        logger.debug { "Borrando complemento con id $id" }
        return complementoRepository.delete(id, logical).also {
            complementoCache.remove(id)
        } ?: throw ComplementoExceptions.ComplementoNotDeletedException("Complemento no borrado con id $id")
    }

    /**
     * Función para cargar todos los complementos desde un archivo csv
     * @param fileName nombre del archivo
     * @author Jaime Leon
     * @since 1.0.0
     * @return lista de complementos
     */
    override fun loadComplementosFromCsv(fileName: String): List<Complemento> {
        logger.debug { "Importando complementos desde csv" }
        return storageComplementos.load(fileName)
    }

    /**
     * Función para cargar todas las butacas desde un archivo csv
     * @param fileName nombre del archivo
     * @author Jaime Leon
     * @since 1.0.0
     * @return lista de butacas
     */
    override fun loadButacasFromCsv(fileName: String): List<Butaca> {
        logger.debug { "Importando butacas desde csv" }
        return storageButaca.load(fileName)
    }

    /**
     * Función para almacenar todas las butacas en un archivo json
     * @param fileName nombre del archivo
     * @author Jaime Leon
     * @since 1.0.0
     */
    override fun exportButacasToJson(lista: List<Butaca>, fileName: File) {
        logger.debug { "Exportando butacas a fichero JSON $fileName" }
        storageButaca.exportButacasJson(lista, fileName)
    }

    /**
     * Función para buscar todas las butacas vendidas por fecha
     * @param fecha fecha de busqueda
     * @author Jaime Leon
     * @since 1.0.0
     * @return listado de butacas
     */
    override fun findAllButacasVendidasFecha(fecha: LocalDate): List<Butaca> {
        logger.debug { "Buscando butaca por fecha $fecha..." }
        return butacaRepository.findAllButacasVendidasFecha(fecha)
    }

    /**
     * Funcion que devuelve un listado por defecto de butacas
     * @author Jaime Leon
     * @since 1.0.0
     * @return listado de butacas
     */
    override fun defaultListButacas(): List<Butaca> {
        logger.debug { "Generando lista de butacas por defecto..." }
        return butacaRepository.defaultListButacas()
    }
}