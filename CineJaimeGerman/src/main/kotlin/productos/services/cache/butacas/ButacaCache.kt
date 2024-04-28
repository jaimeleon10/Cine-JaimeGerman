package org.example.productos.services.cache.butacas

import org.example.config.Config
import org.example.productos.models.butacas.Butaca
import org.example.productos.services.cache.base.Cache
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Implementación de la interfaz Cache.
 * Proporciona métodos para acceder y manipular al cache de butacas.
 * @since 1.0.0
 * @author Jaime leon
 */
class ButacaCache : Cache<Butaca, String> {

    private val cache: MutableMap<String, Butaca> = mutableMapOf()

    /**
     * Función para insertar una butaca en cache
     * @param key clave para guardar en cache
     * @param value butaca para guardar en cache
     * @author Jaime Leon
     * @since 1.0.0
     */
    override fun put(key: String, value: Butaca) {
        logger.debug { "Guardando butaca en cache con id $key" }
        if (cache.size >= Config.cacheSize && !cache.containsKey(key)) {
            val firstKey = cache.keys.first()
            logger.debug { "Eliminando butaca en cache con id $firstKey porque está llena" }
            cache.remove(firstKey)
        }
        cache[key] = value
    }

    /**
     * Función para sacar una butaca de la cache
     * @param key clave para buscar
     * @author Jaime Leon
     * @since 1.0.0
     * @return butaca o nulo
     */
    override fun get(key: String): Butaca? {
        logger.debug { "Obteniendo butaca en cache con id $key" }
        return cache[key]
    }

    /**
     * Función para borrar una butaca de la cache
     * @param key clave para buscar
     * @author Jaime Leon
     * @since 1.0.0
     * @return butaca o nulo
     */
    override fun remove(key: String): Butaca? {
        logger.debug { "Eliminando butaca en cache con id $key" }
        return cache.remove(key)
    }

    /**
     * Función para limpiar la cache
     * @author Jaime Leon
     * @since 1.0.0
     */
    override fun clear() {
        logger.debug { "Limpiando cache de butaca" }
        cache.clear()
    }
}