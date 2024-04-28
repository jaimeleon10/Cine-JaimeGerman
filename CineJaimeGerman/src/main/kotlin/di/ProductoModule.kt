package org.example.di

import org.example.productos.repositories.butacas.ButacaRepository
import org.example.productos.repositories.butacas.ButacaRepositoryImpl
import org.example.productos.repositories.complementos.ComplementoRepository
import org.example.productos.repositories.complementos.ComplementoRepositoryImpl
import org.example.productos.services.cache.butacas.ButacaCache
import org.example.productos.services.cache.complementos.ComplementoCache
import org.example.productos.services.productos.ProductoService
import org.example.productos.services.productos.ProductoServiceImpl
import org.example.productos.services.storage.StorageButacas
import org.example.productos.services.storage.StorageComplementos
import org.koin.dsl.module

/**
 * Modulo de carga para los productos
 * @author Jaime Leon
 * @since 1.0.0
 */
val productoModule = module {
    single<ButacaRepository> { ButacaRepositoryImpl() }
    single<ComplementoRepository> { ComplementoRepositoryImpl() }
    single<ButacaCache> { ButacaCache() }
    single<ComplementoCache> { ComplementoCache() }
    single<StorageComplementos> { StorageComplementos() }
    single<StorageButacas> { StorageButacas() }

    single<ProductoService> {
        ProductoServiceImpl(
            butacaRepository = get(),
            complementoRepository = get(),
            butacaCache = get(),
            complementoCache = get(),
            storageComplementos = get(),
            storageButaca = get()
        )
    }
}
