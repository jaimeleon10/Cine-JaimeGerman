package org.example.productos.services.cache.complementos

import org.example.config.Config
import org.example.productos.models.complementos.Complemento
import org.example.productos.services.cache.base.Cache
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Implementación de la interfaz Cache.
 * Proporciona métodos para acceder y manipular al cache de complementos.
 * @since 1.0.0
 * @author Jaime leon
 */
class ComplementoCache : Cache<Complemento, String> {

    private val cache: MutableMap<String, Complemento> = mutableMapOf()

    /**
     * Función para insertar un complemento en cache
     * @param key clave para guardar en cache
     * @param value complemento para guardar en cache
     * @author Jaime Leon
     * @since 1.0.0
     */
    override fun put(key: String, value: Complemento) {
        logger.debug { "Guardando complemento en cache con id $key" }
        if (cache.size >= Config.cacheSize && !cache.containsKey(key)) {
            val firstKey = cache.keys.first()
            logger.debug { "Eliminando complemento en cache con id $firstKey porque está llena" }
            cache.remove(firstKey)
        }
        cache[key] = value
    }

    /**
     * Función para sacar un complemento de la cache
     * @param key clave para buscar
     * @author Jaime Leon
     * @since 1.0.0
     * @return complemento o nulo
     */
    override fun get(key: String): Complemento? {
        logger.debug { "Obteniendo complemento en cache con id $key" }
        return cache[key]
    }

    /**
     * Función para borrar un complemento de la cache
     * @param key clave para buscar
     * @author Jaime Leon
     * @since 1.0.0
     * @return complemento o nulo
     */
    override fun remove(key: String): Complemento? {
        logger.debug { "Eliminando complemento en cache con id $key" }
        return cache.remove(key)
    }

    /**
     * Función para limpiar la cache
     * @author Jaime Leon
     * @since 1.0.0
     */
    override fun clear() {
        logger.debug { "Limpiando cache de complementos" }
        cache.clear()
    }
}