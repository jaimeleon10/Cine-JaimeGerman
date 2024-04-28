package productos.repositories

import org.example.database.service.SqlDeLightManager
import org.example.di.databaseModule
import org.example.di.productoModule
import org.example.productos.models.complementos.CategoriaComplemento
import org.example.productos.models.complementos.Complemento
import org.example.productos.repositories.complementos.ComplementoRepository
import org.example.productos.services.database.DataBaseManager
import org.junit.jupiter.api.*
import org.koin.core.context.startKoin
import org.koin.fileProperties
import org.koin.test.inject
import org.koin.test.junit5.AutoCloseKoinTest
import kotlin.test.assertEquals

/**
 * Tests para comprobar el correcto funcionamiento del repositorio de complementos
 * @author Jaime Leon
 * @since 1.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ComplementoRepositoryImplTest: AutoCloseKoinTest() {
    private val dbManager: SqlDeLightManager by inject()
    private val complementoRepository: ComplementoRepository by inject()
    private val dataBaseManager: DataBaseManager by inject()

    @BeforeAll
    fun setUpAll() {
        println("Inicializando todos los tests...")
        startKoin {
            fileProperties("/config.properties")
            modules(databaseModule, productoModule)
        }
    }

    @BeforeEach
    fun setUp() {
        dbManager.initialize()
        dataBaseManager.initTests()
    }

    @Test
    fun findAll() {
        val complementos = complementoRepository.findAll()
        assertEquals(1, complementos.size)
    }

    @Test
    fun findById() {
        val complemento = complementoRepository.findById("1")
        assertAll(
            { assertEquals("1", complemento?.id) },
            { assertEquals("Palomitas", complemento?.nombre) },
            { assertEquals("Complemento", complemento?.tipo) },
            { assertEquals(3.0, complemento?.precio) },
            { assertEquals(20, complemento?.stock) },
            { assertEquals(CategoriaComplemento.COMIDA, complemento?.categoria) },
        )
    }

    @Test
    fun findByIdNotFound() {
        val complemento = complementoRepository.findById("-1")
        assertEquals(null, complemento)
    }

    @Test
    fun save() {
        val complemento = complementoRepository.save(
            Complemento(
                id = "2",
                nombre = "Refresco",
                tipo = "Complemento",
                precio = 3.0,
                stock = 20,
                categoria = CategoriaComplemento.BEBIDA
            )

        )
        assertAll(
            { assertEquals("2", complemento.id) },
            { assertEquals("Refresco", complemento.nombre) },
            { assertEquals("Complemento", complemento.tipo) },
            { assertEquals(3.0, complemento.precio) },
            { assertEquals(20, complemento.stock) },
            { assertEquals(CategoriaComplemento.BEBIDA, complemento.categoria) }
        )
    }

    @Test
    fun update() {
        val complemento = complementoRepository.update("1",
            Complemento(
                id = "1",
                nombre = "TEST-UPDATE",
                tipo = "Complemento",
                precio = 5.0,
                stock = 10,
                categoria = CategoriaComplemento.COMIDA
            )
        )

        assertAll(
            { assertEquals("1", complemento?.id) },
            { assertEquals("TEST-UPDATE", complemento?.nombre) },
            { assertEquals("Complemento", complemento?.tipo) },
            { assertEquals(5.0, complemento?.precio) },
            { assertEquals(10, complemento?.stock) },
            { assertEquals(CategoriaComplemento.COMIDA, complemento?.categoria) }
        )
    }

    @Test
    fun updateNotFound() {
        val complemento = complementoRepository.update(
            "9",
            Complemento(
                id = "1",
                nombre = "TEST-UPDATE",
                tipo = "Complemento",
                precio = 5.0,
                stock = 10,
                categoria = CategoriaComplemento.COMIDA
            )
        )
        assertEquals(null, complemento)
    }

    @Test
    fun delete() {
        val complemento = complementoRepository.delete("1", true)
        assertAll(
            { assertEquals("1", complemento?.id) },
            { assertEquals(true, complemento?.isDeleted) },
        )
    }

    @Test
    fun deleteNotFound() {
        val complemento = complementoRepository.delete("9", true)
        assertEquals(null, complemento)
    }
}