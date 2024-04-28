package org.example.productos.exceptions

/**
 * Clase sellada ComplementoExceptions
 * Esta clase sellada define varias excepciones relacionadas con operaciones de complementos.
 * @param message Mensaje de la excepción
 * @since 1.0.0
 * @author Jaime leon
 */
sealed class ComplementoExceptions(message: String) : Exception(message) {
    /**
     * Excepción ComplementoNotFoundException
     * Esta excepción se lanza cuando no se encuentra un complemento.
     * @param message Mensaje de la excepción
     * @since 1.0.0
     * @author Jaime leon
     */
    class ComplementoNotFoundException(message: String) : ComplementoExceptions(message)

    /**
     * Excepción ComplementoNotSavedException
     * Esta excepción se lanza cuando no se puede guardar un complemento.
     * @param message Mensaje de la excepción
     * @since 1.0.0
     * @author Jaime leon
     */
    class ComplementoNotSavedException(message: String) : ComplementoExceptions(message)

    /**
     * Excepción ComplementoNotUpdatedException
     * Esta excepción se lanza cuando no se puede actualizar un complemento.
     * @param message Mensaje de la excepción
     * @since 1.0.0
     * @author Jaime leon
     */
    class ComplementoNotUpdatedException(message: String) : ComplementoExceptions(message)

    /**
     * Excepción ComplementoNotDeletedException
     * Esta excepción se lanza cuando no se puede eliminar un complemento.
     * @param message Mensaje de la excepción
     * @since 1.0.0
     * @author Jaime leon
     */
    class ComplementoNotDeletedException(message: String) : ComplementoExceptions(message)

    /**
     * Excepción ComplementoNotFetchedException
     * Esta excepción se lanza cuando no se puede recuperar un complemento.
     * @param message Mensaje de la excepción
     * @since 1.0.0
     * @author Jaime leon
     */
    class ComplementoNotFetchedException(message: String) : ComplementoExceptions(message)
}
