package productos.repositories

import org.example.database.service.SqlDeLightManager
import org.example.di.databaseModule
import org.example.di.productoModule
import org.example.productos.models.butacas.Butaca
import org.example.productos.models.butacas.EstadoButaca
import org.example.productos.models.butacas.OcupacionButaca
import org.example.productos.models.butacas.TipoButaca
import org.example.productos.repositories.butacas.ButacaRepository
import org.example.productos.services.database.DataBaseManager
import org.junit.jupiter.api.*
import org.koin.core.context.startKoin
import org.koin.fileProperties
import org.koin.test.inject
import org.koin.test.junit5.AutoCloseKoinTest
import kotlin.test.assertEquals

/**
 * Tests para comprobar el correcto funcionamiento del repositorio de butacas
 * @author Jaime Leon
 * @since 1.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ButacaRepositoryImplTest: AutoCloseKoinTest() {
    private val dbManager: SqlDeLightManager by inject()
    private val butacasRepository: ButacaRepository by inject()
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
        dbManager.initializeTest()
        dbManager.initDataExamplesTest()
        dataBaseManager.initTests()
    }

    @Test
    fun findAll() {
        val butacas = butacasRepository.findAll()
        assertEquals(1, butacas.size)
    }

    @Test
    fun findById() {
        val butaca = butacasRepository.findById("A1")
        assertAll(
            { assertEquals("A1", butaca?.id) },
            { assertEquals("Butaca", butaca?.nombre) },
            { assertEquals(5.0, butaca?.precio) },
            { assertEquals(0, butaca?.fila) },
            { assertEquals(0, butaca?.columna) },
            { assertEquals(EstadoButaca.ACTIVA, butaca?.estadoButaca) },
        )
    }

    @Test
    fun findByIdNotFound() {
        val butaca = butacasRepository.findById("H2")
        assertEquals(null, butaca)
    }

    @Test
    fun save() {
        val butaca = butacasRepository.save(
            Butaca(
                id = "A2",
                precio = 5.0,
                fila = 5,
                columna = 0,
                tipoButaca = TipoButaca.NORMAL,
                estadoButaca = EstadoButaca.ACTIVA,
                ocupacionButaca = OcupacionButaca.LIBRE
            )

        )
        assertAll(
            { assertEquals("A2", butaca.id) },
            { assertEquals("Butaca", butaca.nombre) },
            { assertEquals("Butaca", butaca.tipo) },
            { assertEquals(5.0, butaca.precio) },
            { assertEquals(5, butaca.fila) },
            { assertEquals(0, butaca.columna) },
            { assertEquals(TipoButaca.NORMAL, butaca.tipoButaca) },
            { assertEquals(EstadoButaca.ACTIVA, butaca.estadoButaca) },
            { assertEquals(OcupacionButaca.LIBRE, butaca.ocupacionButaca) }
        )
    }

    @Test
    fun update() {
        val butaca = butacasRepository.update("A1",
            Butaca(
                nombre = "TEST-UPDATE",
                precio = 8.0,
                tipoButaca = TipoButaca.VIP,
                fila = 0,
                columna = 0,
                estadoButaca = EstadoButaca.MANTENIMIENTO,
                ocupacionButaca = OcupacionButaca.LIBRE
            )
        )

        assertAll(
            { assertEquals("TEST-UPDATE", butaca?.nombre) },
            { assertEquals(8.0, butaca?.precio) },
            { assertEquals(TipoButaca.VIP, butaca?.tipoButaca) },
            { assertEquals(0, butaca?.fila) },
            { assertEquals(0, butaca?.columna) },
            { assertEquals(EstadoButaca.MANTENIMIENTO, butaca?.estadoButaca) },
            { assertEquals(OcupacionButaca.LIBRE, butaca?.ocupacionButaca) },
        )
    }

    @Test
    fun updateNotFound() {
        val butaca = butacasRepository.update(
            "A9",
            Butaca(
                nombre = "TEST-UPDATE",
                precio = 8.0,
                tipoButaca = TipoButaca.VIP,
                fila = 0,
                columna = 0,
                estadoButaca = EstadoButaca.MANTENIMIENTO,
                ocupacionButaca = OcupacionButaca.LIBRE
            )
        )
        assertEquals(null, butaca)
    }

    @Test
    fun delete() {
        val butaca = butacasRepository.delete("A1", true)
        assertAll(
            { assertEquals("A1", butaca?.id) },
            { assertEquals(true, butaca?.isDeleted) },
        )
    }

    @Test
    fun deleteNotFound() {
        val butaca = butacasRepository.delete("A9", true)
        assertEquals(null, butaca)
    }
}