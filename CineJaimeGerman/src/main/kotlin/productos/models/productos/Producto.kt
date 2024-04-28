package org.example.productos.models.productos

import java.time.LocalDate

/**
 * Clase abstracta Producto
 * Representa un producto genérico.
 * @param id Identificador único del producto
 * @param nombre Nombre del producto
 * @param tipo Tipo del producto
 * @param precio Precio del producto
 * @param createdAt Fecha de creación del producto
 * @param updatedAt Fecha de actualización del producto (puede ser nulo)
 * @param isDeleted Indicador de borrado lógico del producto (puede ser nulo)
 * @since 1.0.0
 * @author Jaime leon
 */
abstract class Producto(
    var id: String,
    var nombre: String,
    var tipo: String,
    var precio: Double,
    val createdAt: LocalDate,
    val updatedAt: LocalDate?,
    val isDeleted: Boolean?
)