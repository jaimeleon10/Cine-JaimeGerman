package org.example.ventas.errors

/**
 * Clase sellada con las clases de errores
 * @property message mensaje de error
 * @since 1.0.0
 * @author German Fernandez
 */
sealed class VentaError(val message: String) {

    /**
     * Clase de error por venta no encontrada
     * @property message mensaje de error
     * @since 1.0.0
     * @author German Fernandez
     */
    class VentaNoEncontrada(message: String) : VentaError(message)

    /**
     * Clase de error por venta no valida
     * @property message mensaje de error
     * @since 1.0.0
     * @author German Fernandez
     */
    class VentaNoValida(message: String) : VentaError(message)

    /**
     * Clase de error por venta no almacenada
     * @property message mensaje de error
     * @since 1.0.0
     * @author German Fernandez
     */
    class VentaNoAlmacenada(message: String) : VentaError(message)

    /**
     * Clase de error por error del tipo storage
     * @property message mensaje de error
     * @since 1.0.0
     * @author German Fernandez
     */
    class VentaStorageError(message: String) : VentaError(message)
}