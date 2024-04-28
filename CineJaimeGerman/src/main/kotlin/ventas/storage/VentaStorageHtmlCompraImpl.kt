package org.example.ventas.storage


import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.locale.toDefaultDateString
import org.example.locale.toDefaultMoneyString
import org.example.ventas.errors.VentaError
import org.example.ventas.models.Venta
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

/**
 * Clase para realizar la exportacion de una compra a un archivo html
 * @author German
 * @since 1.0.0
 */
class VentaStorageHtmlCompraImpl {

    /**
     * Funcion para exportar venta a un archivo html
     * @param venta venta a exportar
     * @param htmlFile nombre del fichero json
     * @author German
     * @since 1.0.0
     * @return unit o error de venta
     */
    fun export(venta: Venta, file: File): Result<Unit, VentaError> {

        return try {
            val html = """
            <html>
                <head>
                    <title>Fichero de Venta</title>
                    <meta charset="utf-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
                </head>
                <body>
                    <div class="container">
                        <h1><span style="text-decoration: underline; color: #3366ff;">Ticket Cines DAWFILMS</span> 🍿🎥</h1>
                        <ul>
                            <li style="font-size: larger;"><strong>Identificador de compra: </strong>${venta.id}</li>
                            <li style="font-size: larger;"><strong>Nombre: </strong>${venta.cliente.nombre}</li>
                            <li style="font-size: larger;"><strong>Número de Socio: </strong>${venta.cliente.numSocio}</li>
                            <li style="font-size: larger;"><strong>Fecha de compra: </strong>${venta.fechaCompra.toDefaultDateString()}</li>
                            <li style="font-size: larger;"><strong>Productos:</strong></li>
                        </ul>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Producto</th>
                                    <th>Cantidad</th>
                                    <th>Precio Unitario</th>
                                    <th>Precio Total</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${venta.lineas.joinToString("") { "<tr><td>${
                                    if (it.tipoProducto == "Butaca") {
                                        "${it.producto.nombre}-${it.producto.id}"
                                    } else {
                                        it.producto.nombre
                                    }
                                }</td><td>${it.cantidad}</td><td>${it.producto.precio.toDefaultMoneyString()}</td><td>${(it.cantidad * it.producto.precio).toDefaultMoneyString()}</td></tr>" }}
                            </tbody>
                        </table>
                        <ul>
                            <li style="font-size: larger;"><strong>Coste Total: </strong>${venta.total.toDefaultMoneyString()}<br /><br /></li>
                        </ul>
                        <p class="text-right lead"><span style="font-weight: bold;"><img style="display: block; margin-left: auto; margin-right: auto;" src="https://e00-elmundo.uecdn.es/assets/multimedia/imagenes/2022/04/29/16512471939657.jpg" width="554" height="359" /><br /></span></p>
                        <p class="text-right lead" style="text-align: center;"><span style="font-weight: bold;">&iexcl; GRACIAS POR SU COMPRA !<br />🎫🎫🎫</span></p>
                    </div>
                </body>
            </html>
        """.trimIndent()
            Ok(file.writeText(html, Charsets.UTF_8))
        } catch (e: Exception) {
            logger.error { "Error al salvar fichero de venta: ${file.absolutePath}. ${e.message}" }
            Err(VentaError.VentaStorageError("Error al salvar fichero de venta: ${file.absolutePath}. ${e.message}"))
        }
    }

}