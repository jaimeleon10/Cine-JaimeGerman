package ventas.repositories

import org.example.clientes.models.Cliente
import org.example.database.service.SqlDeLightManager
import org.example.di.clienteModule
import org.example.di.databaseModule
import org.example.di.productoModule
import org.example.di.ventaModule
import org.example.productos.models.butacas.Butaca
import org.example.productos.models.butacas.EstadoButaca
import org.example.productos.models.butacas.OcupacionButaca
import org.example.productos.models.butacas.TipoButaca
import org.example.ventas.models.LineaVenta
import org.example.ventas.models.Venta
import org.example.ventas.repositories.VentaRepository
import org.junit.jupiter.api.*
import org.koin.core.context.startKoin
import org.koin.fileProperties
import org.koin.test.inject
import org.koin.test.junit5.AutoCloseKoinTest
import java.time.LocalDate
import java.util.*
import kotlin.test.assertEquals

/**
 * Tests para comprobar el correcto funcionamiento del repositorio de ventas
 * @author Jaime Leon
 * @since 1.0.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VentaRepositoryImplTest: AutoCloseKoinTest() {
    private val dbManager: SqlDeLightManager by inject()
    private val ventaRepository: VentaRepository by inject()

    @BeforeAll
    fun setUpAll() {
        println("Inicializando todos los tests...")
        startKoin {
            fileProperties("/config.properties")
            modules(databaseModule, ventaModule, clienteModule, productoModule)
        }
    }

    @BeforeEach
    fun setUp() {
        dbManager.initializeTest()
        dbManager.initDataExamplesTest()
    }

    @Test
    fun findAll() {
        val cliente = Cliente(id = 1, nombre = "Jaime", email = "jaime@gmail.com", numSocio = "AAA111")
        val fechaCompra = LocalDate.now()
        val lineas = listOf( LineaVenta(
            tipoProducto = "Butaca",
            producto = Butaca(
                id = "A1",
                nombre = "Butaca",
                precio = 5.0,
                fila = 0,
                columna = 0,
                tipoButaca = TipoButaca.NORMAL,
                estadoButaca = EstadoButaca.ACTIVA,
                ocupacionButaca = OcupacionButaca.LIBRE
            ),
            cantidad = 1,
            precio = Butaca(
                id = "A1",
                precio = 5.0,
                fila = 0,
                columna = 0,
                tipoButaca = TipoButaca.NORMAL,
                estadoButaca = EstadoButaca.ACTIVA,
                ocupacionButaca = OcupacionButaca.LIBRE).precio
        ))

        val ventas = ventaRepository.findAll(cliente, lineas, fechaCompra)

        assertEquals(1, ventas.size)
    }

    @Test
    fun totalVentasByDate() {
        val date = LocalDate.now()
        var total = 0.0

        val totalVentas = ventaRepository.totalVentasByDate(date).forEach { total += it.total }

        assertEquals(5.0, total)
    }

    @Test
    fun findById() {
        val venta = ventaRepository.findById(UUID.fromString("67c712fb-5531-4f33-a744-0fdb65cd9dcf"))

        assertAll(
            { assertEquals(UUID.fromString("67c712fb-5531-4f33-a744-0fdb65cd9dcf"), venta?.id) },
            { assertEquals(Cliente(id = 1, nombre = "Jaime", email = "jaime@gmail.com", numSocio = "AAA111"), venta?.cliente) },
            { assertEquals(LocalDate.now(), venta?.fechaCompra) }
        )
    }

    @Test
    fun findByIdNotFound() {
        val venta = ventaRepository.findById(UUID.fromString("17c712fb-5531-4f33-a744-0fdb65cd9dcf"))
        assertEquals(null, venta)
    }

    @Test
    fun save() {
        val venta = ventaRepository.save(
            Venta(
                id = UUID.fromString("57c712fb-5531-4f33-a744-0fdb65cd9dcf"),
                cliente = Cliente(id = 1, nombre = "Test", email = "test@gmail.com", numSocio = "CCC444"),
                lineas = listOf(
                    LineaVenta(
                        tipoProducto = "Butaca",
                        producto = Butaca(
                            id = "A1",
                            nombre = "Butaca",
                            precio = 5.0,
                            fila = 0,
                            columna = 0,
                            tipoButaca = TipoButaca.NORMAL,
                            estadoButaca = EstadoButaca.ACTIVA,
                            ocupacionButaca = OcupacionButaca.LIBRE
                        ),
                        cantidad = 1,
                        precio = Butaca(
                            id = "A1",
                            precio = 5.0,
                            fila = 0,
                            columna = 0,
                            tipoButaca = TipoButaca.NORMAL,
                            estadoButaca = EstadoButaca.ACTIVA,
                            ocupacionButaca = OcupacionButaca.LIBRE).precio,
                        createdAt = LocalDate.now(),
                        updatedAt = LocalDate.now(),
                    )
                ),
                fechaCompra = LocalDate.now(),
                createdAt = LocalDate.now(),
                updatedAt = LocalDate.now(),
            )

        )

        assertAll(
            { assertEquals(UUID.fromString("57c712fb-5531-4f33-a744-0fdb65cd9dcf"), venta.id) },
            { assertEquals(Cliente(id = 1, nombre = "Test", email = "test@gmail.com", numSocio = "CCC444"), venta.cliente) },
            { assertEquals(LocalDate.now(), venta.fechaCompra) },
            { assertEquals(LocalDate.now(), venta.createdAt) },
            { assertEquals(LocalDate.now(), venta.updatedAt) }
        )
    }

    @Test
    fun update() {
        val venta = ventaRepository.update(UUID.fromString("67c712fb-5531-4f33-a744-0fdb65cd9dcf"),
            Venta(
                id = UUID.fromString("67c712fb-5531-4f33-a744-0fdb65cd9dcf"),
                cliente = Cliente(id = 1, nombre = "Test-Update", email = "Test-Update@gmail.com", numSocio = "ABC123"),
                lineas = listOf(
                    LineaVenta(
                        tipoProducto = "Butaca",
                        producto = Butaca(
                            id = "A1",
                            nombre = "Butaca",
                            precio = 5.0,
                            fila = 0,
                            columna = 0,
                            tipoButaca = TipoButaca.NORMAL,
                            estadoButaca = EstadoButaca.ACTIVA,
                            ocupacionButaca = OcupacionButaca.LIBRE
                        ),
                        cantidad = 1,
                        precio = Butaca(
                            id = "A1",
                            precio = 5.0,
                            fila = 0,
                            columna = 0,
                            tipoButaca = TipoButaca.NORMAL,
                            estadoButaca = EstadoButaca.ACTIVA,
                            ocupacionButaca = OcupacionButaca.LIBRE).precio,
                        createdAt = LocalDate.now(),
                        updatedAt = LocalDate.now(),
                    )
                ),
                fechaCompra = LocalDate.now(),
                createdAt = LocalDate.now(),
                updatedAt = LocalDate.now(),
            )
        )

        assertAll(
            { assertEquals(UUID.fromString("67c712fb-5531-4f33-a744-0fdb65cd9dcf"), venta?.id) },
            { assertEquals(Cliente(id = 1, nombre = "Test-Update", email = "Test-Update@gmail.com", numSocio = "ABC123"), venta?.cliente) },
        )
    }

    @Test
    fun updateNotFound() {
        val venta = ventaRepository.update(UUID.fromString("57c712fb-5531-4f33-a744-0fdb65cd9dcf"),
            Venta(
                id = UUID.fromString("57c712fb-5531-4f33-a744-0fdb65cd9dcf"),
                cliente = Cliente(id = 1, nombre = "Test-Update", email = "Test-Update@gmail.com", numSocio = "ABC123"),
                lineas = listOf(
                    LineaVenta(
                        tipoProducto = "Butaca",
                        producto = Butaca(
                            id = "A1",
                            nombre = "Butaca",
                            precio = 5.0,
                            fila = 0,
                            columna = 0,
                            tipoButaca = TipoButaca.NORMAL,
                            estadoButaca = EstadoButaca.ACTIVA,
                            ocupacionButaca = OcupacionButaca.LIBRE
                        ),
                        cantidad = 1,
                        precio = Butaca(
                            id = "A1",
                            precio = 5.0,
                            fila = 0,
                            columna = 0,
                            tipoButaca = TipoButaca.NORMAL,
                            estadoButaca = EstadoButaca.ACTIVA,
                            ocupacionButaca = OcupacionButaca.LIBRE).precio,
                        createdAt = LocalDate.now(),
                        updatedAt = LocalDate.now(),
                    )
                ),
                fechaCompra = LocalDate.now(),
                createdAt = LocalDate.now(),
                updatedAt = LocalDate.now(),
            )
        )

        assertAll(
            { assertEquals(null, venta) }
        )
    }

    @Test
    fun delete() {
        val venta = ventaRepository.delete(UUID.fromString("67c712fb-5531-4f33-a744-0fdb65cd9dcf"))
        assertAll(
            { assertEquals(UUID.fromString("67c712fb-5531-4f33-a744-0fdb65cd9dcf"), venta?.id) },
            { assertEquals(true, venta?.isDeleted) },
        )
    }

    @Test
    fun deleteNotFound() {
        val venta = ventaRepository.delete(UUID.fromString("57c712fb-5531-4f33-a744-0fdb65cd9dcf"))
        assertEquals(null, venta)
    }
}