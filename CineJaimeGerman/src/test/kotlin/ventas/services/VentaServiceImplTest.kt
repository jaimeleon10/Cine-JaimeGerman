package ventas.services

import com.github.michaelbull.result.Ok
import org.example.clientes.models.Cliente
import org.example.productos.models.butacas.Butaca
import org.example.productos.models.butacas.EstadoButaca
import org.example.productos.models.butacas.OcupacionButaca
import org.example.productos.models.butacas.TipoButaca
import org.example.ventas.errors.VentaError
import org.example.ventas.models.LineaVenta
import org.example.ventas.models.Venta
import org.example.ventas.repositories.VentaRepository
import org.example.ventas.service.VentaServiceImpl
import org.example.ventas.storage.VentaStorageHtmlCompraImpl
import org.example.ventas.storage.VentaStorageHtmlDevolucionImpl
import org.example.ventas.storage.VentaStorageJsonImpl
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate
import java.util.*
import kotlin.test.Test
import org.junit.jupiter.api.Assertions.*

/**
 * Tests para comprobar el correcto funcionamiento del servicio de ventas
 * @author Jaime Leon
 * @since 1.0.0
 */
@ExtendWith(MockKExtension::class)
class VentaServiceImplTest {

    @MockK
    private lateinit var  ventaRepository: VentaRepository

    @MockK
    private lateinit var ventaStorageJson: VentaStorageJsonImpl

    @MockK
    private lateinit var ventaStorageHtmlCompra: VentaStorageHtmlCompraImpl

    @MockK
    private lateinit var ventaStorageHtmlDevolucion: VentaStorageHtmlDevolucionImpl

    @InjectMockKs
    private lateinit var ventaService: VentaServiceImpl

    @Test
    fun getById() {
        val id = UUID.fromString("67c712fb-5531-4f33-a744-0fdb65cd9dcf")

        val mockVenta = Venta(
            id = UUID.fromString("67c712fb-5531-4f33-a744-0fdb65cd9dcf"),
            cliente = Cliente(id = 1, nombre = "Jaime", email = "jaime@gmail.com", numSocio = "AAA111"),
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

        every { ventaRepository.findById(id) } returns mockVenta

        val venta = ventaService.getById(id).value

        assertAll(
            { assertEquals(UUID.fromString("67c712fb-5531-4f33-a744-0fdb65cd9dcf"), venta.id) },
            { assertEquals(Cliente(id = 1, nombre = "Jaime", email = "jaime@gmail.com", numSocio = "AAA111"), venta.cliente) },
            { assertEquals(LocalDate.now(), venta.fechaCompra) },
            { assertEquals(LocalDate.now(), venta.createdAt) },
            { assertEquals(LocalDate.now(), venta.updatedAt) }
        )

        verify(exactly = 1) { ventaRepository.findById(id) }
    }

    @Test
    fun `getById throws VentaNoEncontrada exception`() {
        val id = UUID.fromString("57c712fb-5531-4f33-a744-0fdb65cd9dcf")

        every { ventaRepository.findById(id) } returns null

        val error = ventaService.getById(id).error

        assertAll(
            { assertTrue(error is VentaError.VentaNoEncontrada) },
            { assertEquals("Venta no encontrada con id: $id", error.message) }
        )

        verify(exactly = 1) { ventaRepository.findById(id) }
    }

    @Test
    fun createVenta() {
        val newVenta = Venta(
            id = UUID.fromString("57c712fb-5531-4f33-a744-0fdb65cd9dcf"),
            cliente = Cliente(id = 1, nombre = "test", email = "test@gmail.com", numSocio = "YYY999"),
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

        every { ventaRepository.validateCliente(newVenta.cliente) } returns Ok(newVenta.cliente)
        every { ventaRepository.validateLineas(newVenta.lineas) } returns Ok(newVenta.lineas)
        every { ventaRepository.actualizarStock(newVenta.lineas) } returns Ok(newVenta.lineas)
        every { ventaRepository.save(newVenta) } returns Ok(newVenta).value

        val venta = ventaService.createVenta(newVenta).value

        assertAll(
            { assertEquals(UUID.fromString("57c712fb-5531-4f33-a744-0fdb65cd9dcf"), venta.id) },
            { assertEquals(Cliente(id = 1, nombre = "test", email = "test@gmail.com", numSocio = "YYY999"), venta.cliente) },
            { assertEquals(LocalDate.now(), venta.fechaCompra) },
            { assertEquals(LocalDate.now(), venta.createdAt) },
            { assertEquals(LocalDate.now(), venta.updatedAt) }
        )

        verify(exactly = 1) { ventaRepository.validateCliente(newVenta.cliente) }
        verify(exactly = 1) { ventaRepository.validateLineas(newVenta.lineas) }
        verify(exactly = 1) { ventaRepository.actualizarStock(newVenta.lineas) }
        verify(exactly = 1) { ventaRepository.save(newVenta) }
    }
}