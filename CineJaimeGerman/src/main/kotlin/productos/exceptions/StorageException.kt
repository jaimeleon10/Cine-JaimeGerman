package org.example.productos.exceptions

/**
 * Clase sellada StorageException
 * Esta clase sellada define excepciones relacionadas con operaciones de almacenamiento.
 * @param message Mensaje de la excepción
 * @since 1.0.0
 * @author Jaime leon
 */
sealed class StorageException(message: String) : Exception(message) {
    /**
     * Excepción StoreException
     * Esta excepción se lanza cuando ocurre un error al almacenar datos.
     * @param message Mensaje de la excepción
     * @since 1.0.0
     * @author Jaime leon
     */
    class StoreException(message: String) : StorageException(message)

    /**
     * Excepción LoadException
     * Esta excepción se lanza cuando ocurre un error al cargar datos.
     * @param message Mensaje de la excepción
     * @since 1.0.0
     * @author Jaime leon
     */
    class LoadException(message: String) : StorageException(message)
}
