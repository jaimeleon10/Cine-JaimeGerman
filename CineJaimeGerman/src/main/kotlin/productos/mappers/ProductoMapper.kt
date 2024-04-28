package org.example.productos.mappers

import org.example.productos.dto.ButacaDto
import org.example.productos.dto.ComplementoDto
import org.example.productos.dto.ProductoDto
import org.example.productos.models.butacas.Butaca
import org.example.productos.models.butacas.EstadoButaca
import org.example.productos.models.butacas.OcupacionButaca
import org.example.productos.models.butacas.TipoButaca
import org.example.productos.models.complementos.CategoriaComplemento
import org.example.productos.models.complementos.Complemento
import org.example.productos.models.productos.Producto
import java.time.LocalDate

/**
 * Convierte un ProductoDto en un Producto.
 * @return Producto convertido
 * @since 1.0.0
 * @author Jaime leon
 */
fun ProductoDto.toProducto(): Producto {
    return when (this.tipo) {
        "Butaca" -> Butaca(
            id = this.id,
            nombre = this.nombre,
            precio = this.precio,
            fila = this.filaButaca!!.toInt(),
            columna = this.columnaButaca!!.toInt(),
            estadoButaca = EstadoButaca.valueOf(this.estadoButaca!!.uppercase()),
            ocupacionButaca = OcupacionButaca.valueOf(this.ocupacionButaca!!.uppercase()),
            tipoButaca = TipoButaca.valueOf(this.tipoButaca!!.uppercase()),
            createdAt = LocalDate.parse(this.createdAt),
            updatedAt = this.updatedAt.let { LocalDate.parse(it) },
            isDeleted = this.isDeleted
        )

        "Complemento" -> Complemento(
            id = this.id,
            nombre = this.nombre,
            precio = this.precio,
            stock = this.stockComplemento!!,
            categoria = CategoriaComplemento.valueOf(this.categoriaComplemento!!.uppercase()),
            createdAt = LocalDate.parse(this.createdAt),
            updatedAt = this.updatedAt.let { LocalDate.parse(it) },
            isDeleted = this.isDeleted
        )

        else -> throw IllegalArgumentException("Tipo de producto no soportado")
    }
}

/**
 * Convierte un Producto en un ProductoDto.
 * @return ProductoDto convertido
 * @since 1.0.0
 * @author Jaime leon
 */
fun Producto.toProductoDto(): ProductoDto {
    return when (this) {
        is Butaca -> ProductoDto(
            id = this.id,
            nombre = this.nombre,
            precio = this.precio,
            tipo = "Butaca",
            filaButaca = this.fila,
            columnaButaca = this.columna,
            tipoButaca = this.tipoButaca.toString(),
            estadoButaca = this.estadoButaca.toString(),
            ocupacionButaca = this.ocupacionButaca.toString(),
            categoriaComplemento = null,
            stockComplemento = null,
            createdAt = this.createdAt.toString(),
            updatedAt = this.updatedAt.toString(),
            isDeleted = this.isDeleted
        )

        is Complemento -> ProductoDto(
            id = this.id,
            nombre = this.nombre,
            precio = this.precio,
            tipo = "Complemento",
            filaButaca = null,
            columnaButaca = null,
            tipoButaca = null,
            estadoButaca = null,
            ocupacionButaca = null,
            categoriaComplemento = this.categoria.toString(),
            stockComplemento = this.stock,
            createdAt = this.createdAt.toString(),
            updatedAt = this.updatedAt.toString(),
            isDeleted = this.isDeleted
        )

        else -> throw IllegalArgumentException("Tipo de producto no soportado")
    }
}

/**
 * Convierte una Butaca en una ButacaDto.
 * @return ButacaDto convertido
 * @since 1.0.0
 * @author Jaime leon
 */
fun Butaca.toButacaDto(): ButacaDto {
    return ButacaDto(
        id = this.id,
        nombre = this.nombre,
        tipo = this.tipo,
        precio = this.precio,
        fila = this.fila,
        columna = this.columna,
        tipoButaca = this.tipoButaca.toString(),
        estadoButaca = this.estadoButaca.toString(),
        ocupacionButaca = this.ocupacionButaca.toString(),
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString(),
        isDeleted = this.isDeleted
    )
}

/**
 * Convierte una ButacaDto en una Butaca.
 * @return Butaca convertido
 * @since 1.0.0
 * @author Jaime leon
 */
fun ButacaDto.toButaca(): Butaca {
    return Butaca(
        id = this.id,
        nombre = this.nombre,
        tipo = this.tipo,
        precio = this.precio,
        fila = this.fila,
        columna = this.columna,
        tipoButaca = TipoButaca.valueOf(this.tipoButaca.uppercase()),
        estadoButaca = EstadoButaca.valueOf(this.estadoButaca.uppercase()),
        ocupacionButaca = OcupacionButaca.valueOf(this.ocupacionButaca.uppercase()),
        createdAt = LocalDate.parse(this.createdAt),
        updatedAt = this.updatedAt ?.let { LocalDate.parse(it) },
        isDeleted = this.isDeleted
    )
}

/**
 * Convierte un Complemento en un ComplementoDto.
 * @return ComplementoDto convertido
 * @since 1.0.0
 * @author Jaime leon
 */
fun Complemento.toComplementoDto(): ComplementoDto {
    return ComplementoDto(
        id = this.id,
        nombre = this.nombre,
        tipo = this.tipo,
        precio = this.precio,
        stock = this.stock,
        categoria = this.categoria.toString(),
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString(),
        isDeleted = isDeleted
    )
}


/**
 * Convierte un ComplementoDto en un Complemento.
 * @return Complemento convertido
 * @since 1.0.0
 * @author Jaime leon
 */
fun ComplementoDto.toComplemento(): Complemento {
    return Complemento(
        id = this.id,
        nombre = this.nombre,
        tipo = this.tipo,
        precio = this.precio,
        stock = this.stock,
        categoria = CategoriaComplemento.valueOf(this.categoria.uppercase()),
        createdAt = LocalDate.parse(this.createdAt),
        updatedAt = this.updatedAt ?.let { LocalDate.parse(it) },
        isDeleted = this.isDeleted
    )
}