package productos.servicies

import org.example.productos.exceptions.ButacaExceptions
import org.example.productos.exceptions.ComplementoExceptions
import org.example.productos.models.butacas.Butaca
import org.example.productos.models.butacas.EstadoButaca
import org.example.productos.models.butacas.OcupacionButaca
import org.example.productos.models.butacas.TipoButaca
import org.example.productos.models.complementos.CategoriaComplemento
import org.example.productos.models.complementos.Complemento
import org.example.productos.repositories.butacas.ButacaRepository
import org.example.productos.repositories.complementos.ComplementoRepository
import org.example.productos.services.cache.butacas.ButacaCache
import org.example.productos.services.cache.complementos.ComplementoCache
import org.example.productos.services.productos.ProductoServiceImpl
import org.example.productos.services.storage.StorageButacas
import org.example.productos.services.storage.StorageComplementos
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test
import io.mockk.verify
import org.junit.jupiter.api.assertThrows

/**
 * Tests para comprobar el correcto funcionamiento del servicio de productos
 * @author Jaime Leon
 * @since 1.0.0
 */
@ExtendWith(MockKExtension::class)
class ProductoServiceImplTest {

    @MockK
    private lateinit var mockButacaRepository: ButacaRepository

    @MockK
    private lateinit var mockComplementoRepository: ComplementoRepository

    @MockK
    private lateinit var mockButacaCache: ButacaCache

    @MockK
    private lateinit var mockComplementoCache: ComplementoCache

    @MockK
    private lateinit var mockButacaStorage: StorageButacas

    @MockK
    private lateinit var mockComplementoStorage: StorageComplementos

    @InjectMockKs
    private lateinit var service: ProductoServiceImpl

    @Test
    fun findAllButacas() {
        val mockButacas = listOf(
            Butaca(
                id = "A1",
                nombre = "Butaca",
                tipo = "Butaca",
                precio = 8.0,
                fila = 0,
                columna = 0,
                tipoButaca = TipoButaca.VIP,
                estadoButaca = EstadoButaca.MANTENIMIENTO,
                ocupacionButaca = OcupacionButaca.LIBRE
            )
        )

        every { mockButacaRepository.findAll() } returns mockButacas

        val butacas = service.findAllButacas()

        assertAll(
            { assertEquals(1, butacas.size) },
            { assertEquals("A1", butacas[0].id) },
            { assertEquals("Butaca", butacas[0].nombre) },
            { assertEquals(8.0, butacas[0].precio) }
        )

        verify(exactly = 1) { mockButacaRepository.findAll() }
    }

    @Test
    fun findAllComplementos() {
        val mockComplementos = listOf(
            Complemento(
                id = "1",
                nombre = "Palomitas",
                tipo = "Complemento",
                precio = 3.0,
                stock = 20,
                categoria = CategoriaComplemento.COMIDA
            )
        )

        every { mockComplementoRepository.findAll() } returns mockComplementos

        val complementos = service.findAllComplementos()

        assertAll(
            { assertEquals(1, complementos.size) },
            { assertEquals("1", complementos[0].id) },
            { assertEquals("Palomitas", complementos[0].nombre) },
            { assertEquals(3.0, complementos[0].precio) },
            { assertEquals(20, complementos[0].stock) }
        )

        verify(exactly = 1) { mockComplementoRepository.findAll() }
    }

    @Test
    fun findButacaById() {
        val id = "A1"
        val mockButaca = Butaca(
            id = "A1",
            nombre = "Butaca",
            tipo = "Butaca",
            precio = 8.0,
            fila = 0,
            columna = 0,
            tipoButaca = TipoButaca.VIP,
            estadoButaca = EstadoButaca.MANTENIMIENTO,
            ocupacionButaca = OcupacionButaca.LIBRE
        )

        every { mockButacaCache.get(id) } returns null
        every { mockButacaRepository.findById(id) } returns mockButaca
        every { mockButacaCache.put(id, mockButaca) } returns Unit

        val butaca = service.findButacaById(id)

        assertAll(
            { assertEquals("A1", butaca.id) },
            { assertEquals("Butaca", butaca.nombre) },
            { assertEquals(8.0, butaca.precio) }
        )

        verify(exactly = 1) { mockButacaRepository.findById(id) }
        verify(exactly = 1) { mockButacaCache.put(id, mockButaca) }
    }

    @Test
    fun `findButacaById throws ButacaNotFoundException`() {
        val id = "A9"

        every { mockButacaCache.get(id) } returns null
        every { mockButacaRepository.findById(id) } throws ButacaExceptions.ButacaNotFoundException("Error al buscar butaca por id $id")

        val result = assertThrows<ButacaExceptions.ButacaNotFoundException> { service.findButacaById(id) }

        assertAll(
            { assertEquals("Error al buscar butaca por id $id", result.message) }
        )

        verify(exactly = 1) { mockButacaCache.get(id) }
        verify(exactly = 1) { mockButacaRepository.findById(id) }
    }

    @Test
    fun findComplementoById() {
        val id = "A1"
        val mockComplemento = Complemento(
            id = "1",
            nombre = "Palomitas",
            tipo = "Complemento",
            precio = 3.0,
            stock = 20,
            categoria = CategoriaComplemento.COMIDA
        )

        every { mockComplementoCache.get(id) } returns null
        every { mockComplementoRepository.findById(id) } returns mockComplemento
        every { mockComplementoCache.put(id, mockComplemento) } returns Unit

        val complemento = service.findComplementoById(id)

        assertAll(
            { assertEquals("1", complemento.id) },
            { assertEquals("Palomitas", complemento.nombre) },
            { assertEquals(3.0, complemento.precio) },
            { assertEquals(20, complemento.stock) }
        )

        verify(exactly = 1) { mockComplementoRepository.findById(id) }
        verify(exactly = 1) { mockComplementoCache.put(id, complemento) }
    }

    @Test
    fun `findComplementoById throws ComplementoNotFoundException`() {
        val id = "9"

        every { mockComplementoCache.get(id) } returns null
        every { mockComplementoRepository.findById(id) } throws ComplementoExceptions.ComplementoNotFoundException("Error al buscar complemento por id $id")

        val result = assertThrows<ComplementoExceptions.ComplementoNotFoundException> { service.findComplementoById(id) }

        assertAll(
            { assertEquals("Error al buscar complemento por id $id", result.message) }
        )

        verify(exactly = 1) { mockComplementoCache.get(id) }
        verify(exactly = 1) { mockComplementoRepository.findById(id) }
    }

    @Test
    fun findComplementoByNombre() {
        val nombre = "Palomitas"
        val mockComplemento = Complemento(
            id = "1",
            nombre = "Palomitas",
            tipo = "Complemento",
            precio = 3.0,
            stock = 20,
            categoria = CategoriaComplemento.COMIDA
        )

        every { mockComplementoRepository.findByNombre(nombre) } returns mockComplemento

        val complemento = service.findComplementoByNombre(nombre)

        assertAll(
            { assertEquals("1", complemento.id) },
            { assertEquals("Palomitas", complemento.nombre) },
            { assertEquals(3.0, complemento.precio) },
            { assertEquals(20, complemento.stock) },
            { assertEquals(CategoriaComplemento.COMIDA, complemento.categoria)}
        )

        verify(exactly = 1) { mockComplementoRepository.findByNombre(nombre) }
    }

    @Test
    fun `findComplementoByNombre throws ComplementoNotFoundException`() {
        val nombre = "Agua"

        every { mockComplementoRepository.findByNombre(nombre) } throws ComplementoExceptions.ComplementoNotFoundException("Error al buscar complemento por nombre $nombre")

        val result = assertThrows<ComplementoExceptions.ComplementoNotFoundException> { service.findComplementoByNombre(nombre) }

        assertAll(
            { assertEquals("Error al buscar complemento por nombre $nombre", result.message) }
        )

        verify(exactly = 1) { mockComplementoRepository.findByNombre(nombre) }
    }

    @Test
    fun saveButaca() {
        val newButaca = Butaca(
            id = "A2",
            nombre = "Butaca",
            tipo = "Butaca",
            precio = 5.0,
            fila = 0,
            columna = 1,
            tipoButaca = TipoButaca.NORMAL,
            estadoButaca = EstadoButaca.ACTIVA,
            ocupacionButaca = OcupacionButaca.LIBRE
        )

        every { mockButacaRepository.save(newButaca) } returns newButaca
        every { mockButacaCache.put(newButaca.id, newButaca) } returns Unit

        val butaca = service.saveButaca(newButaca)

        assertAll(
            { assertEquals("A2", butaca.id) },
            { assertEquals("Butaca", butaca.nombre) },
            { assertEquals(5.0, butaca.precio) },
            { assertEquals(1, butaca.columna) },
            { assertEquals(EstadoButaca.ACTIVA, butaca.estadoButaca) }
        )

        verify(exactly = 1) { mockButacaRepository.save(newButaca) }
        verify(exactly = 1) { mockButacaCache.put(newButaca.id, newButaca) }
    }

    @Test
    fun `saveButaca throws ButacaNotSavedException`() {
        val item = Butaca(
            id = "A1",
            nombre = "Butaca",
            tipo = "Butaca",
            precio = 8.0,
            fila = 0,
            columna = 0,
            tipoButaca = TipoButaca.VIP,
            estadoButaca = EstadoButaca.MANTENIMIENTO,
            ocupacionButaca = OcupacionButaca.LIBRE
        )

        every { mockButacaRepository.save(item) } throws ButacaExceptions.ButacaNotSavedException("Error al guardar butaca $item")

        val result = assertThrows<ButacaExceptions.ButacaNotSavedException> { service.saveButaca(item) }

        assertAll(
            { assertEquals("Error al guardar butaca $item", result.message) }
        )

        verify(exactly = 1) { mockButacaRepository.save(item) }
    }

    @Test
    fun saveComplemento() {
        val newComplemento = Complemento(
            id = "2",
            nombre = "Agua",
            tipo = "Complemento",
            precio = 2.0,
            stock = 20,
            categoria = CategoriaComplemento.BEBIDA
        )

        every { mockComplementoRepository.save(newComplemento) } returns newComplemento
        every { mockComplementoCache.put(newComplemento.id, newComplemento) } returns Unit

        val complemento = service.saveComplemento(newComplemento)

        assertAll(
            { assertEquals("2", complemento.id) },
            { assertEquals("Agua", complemento.nombre) },
            { assertEquals(2.0, complemento.precio) },
            { assertEquals(20, complemento.stock) },
            { assertEquals(CategoriaComplemento.BEBIDA, complemento.categoria)}
        )

        verify(exactly = 1) { mockComplementoRepository.save(newComplemento) }
        verify(exactly = 1) { mockComplementoCache.put(newComplemento.id, newComplemento) }
    }

    @Test
    fun `saveComplemento throws ComplementoNotSavedException`() {
        val item = Complemento(
            id = "1",
            nombre = "Palomitas",
            tipo = "Complemento",
            precio = 3.0,
            stock = 20,
            categoria = CategoriaComplemento.COMIDA
        )

        every { mockComplementoRepository.save(item) } throws ComplementoExceptions.ComplementoNotSavedException("Error al guardar complemento $item")

        val result = assertThrows<ComplementoExceptions.ComplementoNotSavedException> { service.saveComplemento(item) }

        assertAll(
            { assertEquals("Error al guardar complemento $item", result.message) }
        )

        verify(exactly = 1) { mockComplementoRepository.save(item) }
    }

    @Test
    fun updateButaca() {
        val id = "A1"
        val updatedButaca = Butaca(
            id = "A1",
            nombre = "Butaca-Update",
            tipo = "Butaca",
            precio = 5.0,
            fila = 0,
            columna = 0,
            tipoButaca = TipoButaca.NORMAL,
            estadoButaca = EstadoButaca.ACTIVA,
            ocupacionButaca = OcupacionButaca.OCUPADA
        )

        every { mockButacaRepository.update(id, updatedButaca) } returns updatedButaca
        every { mockButacaCache.put(id, updatedButaca) } returns Unit

        val butaca = service.updateButaca(id, updatedButaca)

        assertAll(
            { assertEquals("A1", butaca.id) },
            { assertEquals("Butaca-Update", butaca.nombre) },
            { assertEquals(5.0, butaca.precio) },
            { assertEquals(0, butaca.columna) },
            { assertEquals(EstadoButaca.ACTIVA, butaca.estadoButaca) },
            { assertEquals(OcupacionButaca.OCUPADA, butaca.ocupacionButaca) },
        )

        verify(exactly = 1) { mockButacaRepository.update(id, updatedButaca) }
        verify(exactly = 1) { mockButacaCache.put(id, updatedButaca) }
    }

    @Test
    fun updateComplemento() {
        val id = "1"
        val updatedComplemento = Complemento(
            id = "1",
            nombre = "Palomitas-Updated",
            tipo = "Complemento",
            precio = 2.0,
            stock = 10,
            categoria = CategoriaComplemento.COMIDA
        )

        every { mockComplementoRepository.update(id, updatedComplemento) } returns updatedComplemento
        every { mockComplementoCache.put(id, updatedComplemento) } returns Unit

        val complemento = service.updateComplemento(id, updatedComplemento)

        assertAll(
            { assertEquals("1", complemento.id) },
            { assertEquals("Palomitas-Updated", complemento.nombre) },
            { assertEquals(2.0, complemento.precio) },
            { assertEquals(10, complemento.stock) },
            { assertEquals(CategoriaComplemento.COMIDA, complemento.categoria)}
        )

        verify(exactly = 1) { mockComplementoRepository.update(id, updatedComplemento) }
        verify(exactly = 1) { mockComplementoCache.put(id, updatedComplemento) }
    }

    @Test
    fun deleteButacaLogico() {
        val id = "A1"
        val deletedButaca = Butaca(
            id = "A1",
            nombre = "Butaca",
            tipo = "Butaca",
            precio = 8.0,
            fila = 0,
            columna = 0,
            tipoButaca = TipoButaca.VIP,
            estadoButaca = EstadoButaca.MANTENIMIENTO,
            ocupacionButaca = OcupacionButaca.LIBRE,
            isDeleted = true
        )

        every { mockButacaRepository.delete(id, true) } returns deletedButaca
        every { mockButacaCache.remove(id) } returns deletedButaca

        val butaca = service.deleteButaca(id, true)

        assertAll(
            { assertEquals("A1", butaca.id) },
            { assertEquals(true, butaca.isDeleted) },
        )

        verify(exactly = 1) { mockButacaRepository.delete(id, true) }
        verify(exactly = 1) { mockButacaCache.remove(id) }
    }

    @Test
    fun deleteButacaFisico() {
        val id = "A1"
        val deletedButaca = Butaca(
            id = "A1",
            nombre = "Butaca-Deleted",
            tipo = "Butaca",
            precio = 8.0,
            fila = 0,
            columna = 0,
            tipoButaca = TipoButaca.VIP,
            estadoButaca = EstadoButaca.MANTENIMIENTO,
            ocupacionButaca = OcupacionButaca.LIBRE
        )

        every { mockButacaRepository.delete(id, false) } returns deletedButaca
        every { mockButacaCache.remove(id) } returns deletedButaca

        val butaca = service.deleteButaca(id, false)

        assertAll(
            { assertEquals("A1", butaca.id) },
            { assertEquals("Butaca-Deleted", butaca.nombre) },
            { assertEquals(8.0, butaca.precio) },
            { assertEquals(EstadoButaca.MANTENIMIENTO, butaca.estadoButaca) },
            { assertEquals(OcupacionButaca.LIBRE, butaca.ocupacionButaca) },
        )

        verify(exactly = 1) { mockButacaRepository.delete(id, false) }
        verify(exactly = 1) { mockButacaCache.remove(id) }
    }

    @Test
    fun deleteComplementoLogico() {
        val id = "1"
        val deletedComplemento = Complemento(
            id = "1",
            nombre = "Palomitas-Deleted",
            tipo = "Complemento",
            precio = 2.0,
            stock = 10,
            categoria = CategoriaComplemento.COMIDA,
            isDeleted = true
        )

        every { mockComplementoRepository.delete(id, true) } returns deletedComplemento
        every { mockComplementoCache.remove(id) } returns deletedComplemento

        val complemento = service.deleteComplemento(id, true)

        assertAll(
            { assertEquals("1", complemento.id) },
            { assertEquals(true, complemento.isDeleted) },
        )

        verify(exactly = 1) { mockComplementoRepository.delete(id, true) }
        verify(exactly = 1) { mockComplementoCache.remove(id) }
    }

    @Test
    fun deleteComplementoFisico() {
        val id = "1"
        val deletedComplemento = Complemento(
            id = "1",
            nombre = "Palomitas-Deleted",
            tipo = "Complemento",
            precio = 3.0,
            stock = 20,
            categoria = CategoriaComplemento.COMIDA
        )


        every { mockComplementoRepository.delete(id, false) } returns deletedComplemento
        every { mockComplementoCache.remove(id) } returns deletedComplemento

        val complemento = service.deleteComplemento(id, false)

        assertAll(
            { assertEquals("1", complemento.id) },
            { assertEquals("Palomitas-Deleted", complemento.nombre) },
            { assertEquals(3.0, complemento.precio) },
            { assertEquals(20, complemento.stock) }
        )

        verify(exactly = 1) { mockComplementoRepository.delete(id, false) }
        verify(exactly = 1) { mockComplementoCache.remove(id) }
    }

    @Test
    fun defaultListButacas() {
        val list = listOf(
            Butaca(id = "A1", precio = 5.0, fila = 0, columna = 0, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "A2", precio = 5.0, fila = 0, columna = 1, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "A3", precio = 5.0, fila = 0, columna = 2, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "A4", precio = 5.0, fila = 0, columna = 3, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "A5", precio = 5.0, fila = 0, columna = 4, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "A6", precio = 5.0, fila = 0, columna = 5, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "A7", precio = 5.0, fila = 0, columna = 6, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "B1", precio = 5.0, fila = 1, columna = 0, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "B2", precio = 5.0, fila = 1, columna = 1, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "B3", precio = 8.0, fila = 1, columna = 2, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "B4", precio = 8.0, fila = 1, columna = 3, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "B5", precio = 8.0, fila = 1, columna = 4, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "B6", precio = 5.0, fila = 1, columna = 5, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "B7", precio = 5.0, fila = 1, columna = 6, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "C1", precio = 5.0, fila = 2, columna = 0, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "C2", precio = 5.0, fila = 2, columna = 1, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "C3", precio = 8.0, fila = 2, columna = 2, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "C4", precio = 8.0, fila = 2, columna = 3, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "C5", precio = 8.0, fila = 2, columna = 4, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "C6", precio = 5.0, fila = 2, columna = 5, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "C7", precio = 5.0, fila = 2, columna = 6, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "D1", precio = 5.0, fila = 3, columna = 0, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "D2", precio = 5.0, fila = 3, columna = 1, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "D3", precio = 8.0, fila = 3, columna = 2, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "D4", precio = 8.0, fila = 3, columna = 3, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "D5", precio = 8.0, fila = 3, columna = 4, tipoButaca = TipoButaca.VIP, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "D6", precio = 5.0, fila = 3, columna = 5, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "D7", precio = 5.0, fila = 3, columna = 6, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "E1", precio = 5.0, fila = 4, columna = 0, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "E2", precio = 5.0, fila = 4, columna = 1, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "E3", precio = 5.0, fila = 4, columna = 2, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "E4", precio = 5.0, fila = 4, columna = 3, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "E5", precio = 5.0, fila = 4, columna = 4, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "E6", precio = 5.0, fila = 4, columna = 5, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE),
            Butaca(id = "E7", precio = 5.0, fila = 4, columna = 6, tipoButaca = TipoButaca.NORMAL, estadoButaca = EstadoButaca.ACTIVA, ocupacionButaca = OcupacionButaca.LIBRE)
        )

        every { mockButacaRepository.defaultListButacas() } returns list

        val listButacas = service.defaultListButacas()

        assertAll(
            { assertEquals(35, listButacas.size) },
        )
    }
}