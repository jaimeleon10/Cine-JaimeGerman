package org.example.di

import org.example.ventas.repositories.VentaRepository
import org.example.ventas.repositories.VentaRepositoryImpl
import org.example.ventas.service.VentaService
import org.example.ventas.service.VentaServiceImpl
import org.example.ventas.storage.VentaStorageHtmlCompraImpl
import org.example.ventas.storage.VentaStorageHtmlDevolucionImpl
import org.example.ventas.storage.VentaStorageJsonImpl
import org.koin.dsl.module

/**
 * Modulo de carga para las ventas
 * @author Jaime Leon
 * @since 1.0.0
 */
val ventaModule = module {
    // Ventas
    single<VentaRepository> {
        VentaRepositoryImpl(
            dbManager = get(),
            butacaRepository = get(),
            complementoRepository = get(),
            clienteRepository = get()
        )
    }
    single<VentaStorageJsonImpl> { VentaStorageJsonImpl() }
    single<VentaStorageHtmlCompraImpl> { VentaStorageHtmlCompraImpl() }
    single<VentaStorageHtmlDevolucionImpl> { VentaStorageHtmlDevolucionImpl() }
    single<VentaService> {
        VentaServiceImpl(
            ventaRepository = get(),
            ventaStorageJson = get(),
            ventaStorageHtmlCompra = get(),
            ventaStorageHtmlDevolucion = get()
        )
    }
}