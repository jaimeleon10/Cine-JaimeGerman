package org.example.productos.models.butacas

/**
 * Enumeración de los tipos de butacas con sus respectivos precios.
 * @since 1.0.0
 * @author Jaime leon
 * @param precio Precio de la butaca
 */
enum class TipoButaca(val precio: Double) {
    NORMAL(5.0), VIP(8.0)
}