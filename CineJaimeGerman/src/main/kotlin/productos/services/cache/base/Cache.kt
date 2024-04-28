package org.example.productos.services.cache.base

/**
 * Interfaz Cache
 * Define las operaciones disponibles para manipular el cache
 * @since 1.0.0
 * @author Jaime leon
 */
interface Cache<T, KEY> {
    fun put(key: KEY, value: T)
    fun get(key: KEY): T?
    fun remove(key: KEY): T?
    fun clear()
}