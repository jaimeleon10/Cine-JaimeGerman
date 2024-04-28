package di

import org.example.di.*
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinApplication
import org.koin.fileProperties
import org.koin.test.check.checkModules
import org.koin.test.junit5.AutoCloseKoinTest
import org.koin.test.verify.verify
import kotlin.test.Test

/**
 * Clase para verificar y comprobar los modulos de koin y su implementacion
 * @author Jaime Leon
 * @since 1.0.0
 */
@OptIn(KoinExperimentalAPI::class)
class ProductosVentasModule : AutoCloseKoinTest() {

    @Test
    fun verifyModules() {
        koinApplication {
            fileProperties("/config.properties")
            databaseModule.verify(extraTypes = listOf(Boolean::class))
            clienteModule.verify()
            productoModule.verify()
            ventaModule.verify()
        }
    }

    @Test
    fun checkModules() {
        checkModules {
            modules(databaseModule, clienteModule, productoModule, ventaModule)
        }
    }
}