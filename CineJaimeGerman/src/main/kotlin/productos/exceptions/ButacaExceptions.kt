package org.example.productos.exceptions

/**
 * Clase sellada ButacaExceptions
 * Esta clase sellada define varias excepciones relacionadas con operaciones de butacas.
 * @param message Mensaje de la excepción
 * @since 1.0.0
 * @author Jaime leon
 */
sealed class ButacaExceptions(message: String): Exception(message) {
    /**
     * Excepción ButacaNotFoundException
     * Esta excepción se lanza cuando no se encuentra una butaca.
     * @param message Mensaje de la excepción
     * @since 1.0.0
     * @author Jaime leon
     */
    class ButacaNotFoundException(message: String) : ButacaExceptions(message)

    /**
     * Excepción ButacaNotSavedException
     * Esta excepción se lanza cuando no se puede guardar una butaca.
     * @param message Mensaje de la excepción
     * @since 1.0.0
     * @author Jaime leon
     */
    class ButacaNotSavedException(message: String) : ButacaExceptions(message)

    /**
     * Excepción ButacaNotUpdatedException
     * Esta excepción se lanza cuando no se puede actualizar una butaca.
     * @param message Mensaje de la excepción
     * @since 1.0.0
     * @author Jaime leon
     */
    class ButacaNotUpdatedException(message: String) : ButacaExceptions(message)

    /**
     * Excepción ButacaNotDeletedException
     * Esta excepción se lanza cuando no se puede eliminar una butaca.
     * @param message Mensaje de la excepción
     * @since 1.0.0
     * @author Jaime leon
     */
    class ButacaNotDeletedException(message: String) : ButacaExceptions(message)

    /**
     * Excepción ButacaNotFetchedException
     * Esta excepción se lanza cuando no se puede recuperar una butaca.
     * @param message Mensaje de la excepción
     * @since 1.0.0
     * @author Jaime leon
     */
    class ButacaNotFetchedException(message: String) : ButacaExceptions(message)
}
