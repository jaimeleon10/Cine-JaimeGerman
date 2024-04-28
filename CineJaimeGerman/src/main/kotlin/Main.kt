package org.example

import org.example.di.*
import org.koin.core.context.GlobalContext.startKoin
import org.koin.fileProperties
import org.koin.test.check.checkKoinModules

/**
 * funcion main para ejecutar el programa al completo
 * @author Jaime Leon
 * @since 1.0.0
 */
fun main() {
    checkMyModules()

    startKoin {
        printLogger()
        fileProperties("/config.properties")
        modules(listOf(databaseModule, clienteModule, ventaModule, productoModule))
    }

    val app = CineJaimeGermanApp()
    app.run()
}

/**
 * funcion para comprobar la implementacion de los modulos de koin
 * @author Jaime Leon
 * @since 1.0.0
 */
fun checkMyModules() {
    checkKoinModules(listOf(databaseModule, clienteModule, ventaModule, productoModule))
}