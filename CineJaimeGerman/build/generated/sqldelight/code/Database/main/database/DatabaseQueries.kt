package database

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Boolean
import kotlin.Double
import kotlin.Long
import kotlin.String

public class DatabaseQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectAllClientes(mapper: (
    id: Long,
    nombre: String,
    email: String,
    numSocio: String,
    created_at: String,
    updated_at: String,
    is_deleted: Long,
  ) -> T): Query<T> = Query(1_732_280_950, arrayOf("ClienteEntity"), driver, "Database.sq",
      "selectAllClientes",
      "SELECT ClienteEntity.id, ClienteEntity.nombre, ClienteEntity.email, ClienteEntity.numSocio, ClienteEntity.created_at, ClienteEntity.updated_at, ClienteEntity.is_deleted FROM ClienteEntity") {
      cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun selectAllClientes(): Query<ClienteEntity> = selectAllClientes { id, nombre, email,
      numSocio, created_at, updated_at, is_deleted ->
    ClienteEntity(
      id,
      nombre,
      email,
      numSocio,
      created_at,
      updated_at,
      is_deleted
    )
  }

  public fun <T : Any> selectClienteByNumSocio(numSocio: String, mapper: (
    id: Long,
    nombre: String,
    email: String,
    numSocio: String,
    created_at: String,
    updated_at: String,
    is_deleted: Long,
  ) -> T): Query<T> = SelectClienteByNumSocioQuery(numSocio) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun selectClienteByNumSocio(numSocio: String): Query<ClienteEntity> =
      selectClienteByNumSocio(numSocio) { id, nombre, email, numSocio_, created_at, updated_at,
      is_deleted ->
    ClienteEntity(
      id,
      nombre,
      email,
      numSocio_,
      created_at,
      updated_at,
      is_deleted
    )
  }

  public fun <T : Any> selectClienteById(id: Long, mapper: (
    id: Long,
    nombre: String,
    email: String,
    numSocio: String,
    created_at: String,
    updated_at: String,
    is_deleted: Long,
  ) -> T): Query<T> = SelectClienteByIdQuery(id) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun selectClienteById(id: Long): Query<ClienteEntity> = selectClienteById(id) { id_,
      nombre, email, numSocio, created_at, updated_at, is_deleted ->
    ClienteEntity(
      id_,
      nombre,
      email,
      numSocio,
      created_at,
      updated_at,
      is_deleted
    )
  }

  public fun <T : Any> selectAllClientesByIsDeleted(is_deleted: Long, mapper: (
    id: Long,
    nombre: String,
    email: String,
    numSocio: String,
    created_at: String,
    updated_at: String,
    is_deleted: Long,
  ) -> T): Query<T> = SelectAllClientesByIsDeletedQuery(is_deleted) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun selectAllClientesByIsDeleted(is_deleted: Long): Query<ClienteEntity> =
      selectAllClientesByIsDeleted(is_deleted) { id, nombre, email, numSocio, created_at,
      updated_at, is_deleted_ ->
    ClienteEntity(
      id,
      nombre,
      email,
      numSocio,
      created_at,
      updated_at,
      is_deleted_
    )
  }

  public fun <T : Any> selectClienteLastInserted(mapper: (
    id: Long,
    nombre: String,
    email: String,
    numSocio: String,
    created_at: String,
    updated_at: String,
    is_deleted: Long,
  ) -> T): Query<T> = Query(1_207_671_012, arrayOf("ClienteEntity"), driver, "Database.sq",
      "selectClienteLastInserted",
      "SELECT ClienteEntity.id, ClienteEntity.nombre, ClienteEntity.email, ClienteEntity.numSocio, ClienteEntity.created_at, ClienteEntity.updated_at, ClienteEntity.is_deleted FROM ClienteEntity WHERE id = last_insert_rowid()") {
      cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun selectClienteLastInserted(): Query<ClienteEntity> = selectClienteLastInserted { id,
      nombre, email, numSocio, created_at, updated_at, is_deleted ->
    ClienteEntity(
      id,
      nombre,
      email,
      numSocio,
      created_at,
      updated_at,
      is_deleted
    )
  }

  public fun <T : Any> selectAllVentas(mapper: (
    id: String,
    cliente_id: Long,
    total: Double,
    fecha_compra: String,
    created_at: String,
    updated_at: String,
    is_deleted: Long,
  ) -> T): Query<T> = Query(1_996_436_388, arrayOf("VentaEntity"), driver, "Database.sq",
      "selectAllVentas",
      "SELECT VentaEntity.id, VentaEntity.cliente_id, VentaEntity.total, VentaEntity.fecha_compra, VentaEntity.created_at, VentaEntity.updated_at, VentaEntity.is_deleted FROM VentaEntity") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getLong(1)!!,
      cursor.getDouble(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun selectAllVentas(): Query<VentaEntity> = selectAllVentas { id, cliente_id, total,
      fecha_compra, created_at, updated_at, is_deleted ->
    VentaEntity(
      id,
      cliente_id,
      total,
      fecha_compra,
      created_at,
      updated_at,
      is_deleted
    )
  }

  public fun <T : Any> selectVentaById(id: String, mapper: (
    id: String,
    cliente_id: Long,
    total: Double,
    fecha_compra: String,
    created_at: String,
    updated_at: String,
    is_deleted: Long,
  ) -> T): Query<T> = SelectVentaByIdQuery(id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getLong(1)!!,
      cursor.getDouble(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun selectVentaById(id: String): Query<VentaEntity> = selectVentaById(id) { id_,
      cliente_id, total, fecha_compra, created_at, updated_at, is_deleted ->
    VentaEntity(
      id_,
      cliente_id,
      total,
      fecha_compra,
      created_at,
      updated_at,
      is_deleted
    )
  }

  public fun existsVenta(id: String): Query<Boolean> = ExistsVentaQuery(id) { cursor ->
    cursor.getBoolean(0)!!
  }

  public fun <T : Any> selectAllVentasByIsDeleted(is_deleted: Long, mapper: (
    id: String,
    cliente_id: Long,
    total: Double,
    fecha_compra: String,
    created_at: String,
    updated_at: String,
    is_deleted: Long,
  ) -> T): Query<T> = SelectAllVentasByIsDeletedQuery(is_deleted) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getLong(1)!!,
      cursor.getDouble(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun selectAllVentasByIsDeleted(is_deleted: Long): Query<VentaEntity> =
      selectAllVentasByIsDeleted(is_deleted) { id, cliente_id, total, fecha_compra, created_at,
      updated_at, is_deleted_ ->
    VentaEntity(
      id,
      cliente_id,
      total,
      fecha_compra,
      created_at,
      updated_at,
      is_deleted_
    )
  }

  public fun <T : Any> selectVentaLastInserted(mapper: (
    id: String,
    cliente_id: Long,
    total: Double,
    fecha_compra: String,
    created_at: String,
    updated_at: String,
    is_deleted: Long,
  ) -> T): Query<T> = Query(1_569_743_158, arrayOf("VentaEntity"), driver, "Database.sq",
      "selectVentaLastInserted",
      "SELECT VentaEntity.id, VentaEntity.cliente_id, VentaEntity.total, VentaEntity.fecha_compra, VentaEntity.created_at, VentaEntity.updated_at, VentaEntity.is_deleted FROM VentaEntity WHERE id = last_insert_rowid()") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getLong(1)!!,
      cursor.getDouble(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun selectVentaLastInserted(): Query<VentaEntity> = selectVentaLastInserted { id,
      cliente_id, total, fecha_compra, created_at, updated_at, is_deleted ->
    VentaEntity(
      id,
      cliente_id,
      total,
      fecha_compra,
      created_at,
      updated_at,
      is_deleted
    )
  }

  public fun <T : Any> selectVentasByDate(fecha_compra: String, mapper: (
    id: String,
    cliente_id: Long,
    total: Double,
    fecha_compra: String,
    created_at: String,
    updated_at: String,
    is_deleted: Long,
  ) -> T): Query<T> = SelectVentasByDateQuery(fecha_compra) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getLong(1)!!,
      cursor.getDouble(2)!!,
      cursor.getString(3)!!,
      cursor.getString(4)!!,
      cursor.getString(5)!!,
      cursor.getLong(6)!!
    )
  }

  public fun selectVentasByDate(fecha_compra: String): Query<VentaEntity> =
      selectVentasByDate(fecha_compra) { id, cliente_id, total, fecha_compra_, created_at,
      updated_at, is_deleted ->
    VentaEntity(
      id,
      cliente_id,
      total,
      fecha_compra_,
      created_at,
      updated_at,
      is_deleted
    )
  }

  public fun <T : Any> selectAllLineasVentas(mapper: (
    id: String,
    venta_id: String,
    producto_id: String,
    producto_tipo: String,
    cantidad: Long,
    precio: Double,
    created_at: String,
    updated_at: String,
    is_deleted: Long,
  ) -> T): Query<T> = Query(-1_406_236_502, arrayOf("LineaVentaEntity"), driver, "Database.sq",
      "selectAllLineasVentas",
      "SELECT LineaVentaEntity.id, LineaVentaEntity.venta_id, LineaVentaEntity.producto_id, LineaVentaEntity.producto_tipo, LineaVentaEntity.cantidad, LineaVentaEntity.precio, LineaVentaEntity.created_at, LineaVentaEntity.updated_at, LineaVentaEntity.is_deleted FROM LineaVentaEntity") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getLong(4)!!,
      cursor.getDouble(5)!!,
      cursor.getString(6)!!,
      cursor.getString(7)!!,
      cursor.getLong(8)!!
    )
  }

  public fun selectAllLineasVentas(): Query<LineaVentaEntity> = selectAllLineasVentas { id,
      venta_id, producto_id, producto_tipo, cantidad, precio, created_at, updated_at, is_deleted ->
    LineaVentaEntity(
      id,
      venta_id,
      producto_id,
      producto_tipo,
      cantidad,
      precio,
      created_at,
      updated_at,
      is_deleted
    )
  }

  public fun <T : Any> selectLineaVentaById(id: String, mapper: (
    id: String,
    venta_id: String,
    producto_id: String,
    producto_tipo: String,
    cantidad: Long,
    precio: Double,
    created_at: String,
    updated_at: String,
    is_deleted: Long,
  ) -> T): Query<T> = SelectLineaVentaByIdQuery(id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getLong(4)!!,
      cursor.getDouble(5)!!,
      cursor.getString(6)!!,
      cursor.getString(7)!!,
      cursor.getLong(8)!!
    )
  }

  public fun selectLineaVentaById(id: String): Query<LineaVentaEntity> = selectLineaVentaById(id) {
      id_, venta_id, producto_id, producto_tipo, cantidad, precio, created_at, updated_at,
      is_deleted ->
    LineaVentaEntity(
      id_,
      venta_id,
      producto_id,
      producto_tipo,
      cantidad,
      precio,
      created_at,
      updated_at,
      is_deleted
    )
  }

  public fun <T : Any> selectAllLineasVentaByVentaId(venta_id: String, mapper: (
    id: String,
    venta_id: String,
    producto_id: String,
    producto_tipo: String,
    cantidad: Long,
    precio: Double,
    created_at: String,
    updated_at: String,
    is_deleted: Long,
  ) -> T): Query<T> = SelectAllLineasVentaByVentaIdQuery(venta_id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getLong(4)!!,
      cursor.getDouble(5)!!,
      cursor.getString(6)!!,
      cursor.getString(7)!!,
      cursor.getLong(8)!!
    )
  }

  public fun selectAllLineasVentaByVentaId(venta_id: String): Query<LineaVentaEntity> =
      selectAllLineasVentaByVentaId(venta_id) { id, venta_id_, producto_id, producto_tipo, cantidad,
      precio, created_at, updated_at, is_deleted ->
    LineaVentaEntity(
      id,
      venta_id_,
      producto_id,
      producto_tipo,
      cantidad,
      precio,
      created_at,
      updated_at,
      is_deleted
    )
  }

  public fun <T : Any> selectAllLineasVentasByIsDeleted(is_deleted: Long, mapper: (
    id: String,
    venta_id: String,
    producto_id: String,
    producto_tipo: String,
    cantidad: Long,
    precio: Double,
    created_at: String,
    updated_at: String,
    is_deleted: Long,
  ) -> T): Query<T> = SelectAllLineasVentasByIsDeletedQuery(is_deleted) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getLong(4)!!,
      cursor.getDouble(5)!!,
      cursor.getString(6)!!,
      cursor.getString(7)!!,
      cursor.getLong(8)!!
    )
  }

  public fun selectAllLineasVentasByIsDeleted(is_deleted: Long): Query<LineaVentaEntity> =
      selectAllLineasVentasByIsDeleted(is_deleted) { id, venta_id, producto_id, producto_tipo,
      cantidad, precio, created_at, updated_at, is_deleted_ ->
    LineaVentaEntity(
      id,
      venta_id,
      producto_id,
      producto_tipo,
      cantidad,
      precio,
      created_at,
      updated_at,
      is_deleted_
    )
  }

  public fun <T : Any> selectLineaVentaLastInserted(mapper: (
    id: String,
    venta_id: String,
    producto_id: String,
    producto_tipo: String,
    cantidad: Long,
    precio: Double,
    created_at: String,
    updated_at: String,
    is_deleted: Long,
  ) -> T): Query<T> = Query(977_668_433, arrayOf("LineaVentaEntity"), driver, "Database.sq",
      "selectLineaVentaLastInserted",
      "SELECT LineaVentaEntity.id, LineaVentaEntity.venta_id, LineaVentaEntity.producto_id, LineaVentaEntity.producto_tipo, LineaVentaEntity.cantidad, LineaVentaEntity.precio, LineaVentaEntity.created_at, LineaVentaEntity.updated_at, LineaVentaEntity.is_deleted FROM LineaVentaEntity WHERE id = last_insert_rowid()") {
      cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getString(3)!!,
      cursor.getLong(4)!!,
      cursor.getDouble(5)!!,
      cursor.getString(6)!!,
      cursor.getString(7)!!,
      cursor.getLong(8)!!
    )
  }

  public fun selectLineaVentaLastInserted(): Query<LineaVentaEntity> =
      selectLineaVentaLastInserted { id, venta_id, producto_id, producto_tipo, cantidad, precio,
      created_at, updated_at, is_deleted ->
    LineaVentaEntity(
      id,
      venta_id,
      producto_id,
      producto_tipo,
      cantidad,
      precio,
      created_at,
      updated_at,
      is_deleted
    )
  }

  public fun removeAllClientes() {
    driver.execute(-2_090_026_514, """DELETE FROM ClienteEntity""", 0)
    notifyQueries(-2_090_026_514) { emit ->
      emit("ClienteEntity")
    }
  }

  public fun insertCliente(
    nombre: String,
    email: String,
    numSocio: String,
    created_at: String,
    updated_at: String,
  ) {
    driver.execute(-952_929_383,
        """INSERT INTO ClienteEntity (nombre, email, numSocio, created_at, updated_at) VALUES (?, ?, ?, ?, ?)""",
        5) {
          bindString(0, nombre)
          bindString(1, email)
          bindString(2, numSocio)
          bindString(3, created_at)
          bindString(4, updated_at)
        }
    notifyQueries(-952_929_383) { emit ->
      emit("ClienteEntity")
    }
  }

  public fun updateCliente(
    nombre: String,
    email: String,
    numSocio: String,
    updated_at: String,
    is_deleted: Long,
    id: Long,
  ) {
    driver.execute(-1_937_372_279,
        """UPDATE ClienteEntity SET nombre = ?, email = ?, numSocio = ?, updated_at = ?, is_deleted = ? WHERE id = ?""",
        6) {
          bindString(0, nombre)
          bindString(1, email)
          bindString(2, numSocio)
          bindString(3, updated_at)
          bindLong(4, is_deleted)
          bindLong(5, id)
        }
    notifyQueries(-1_937_372_279) { emit ->
      emit("ClienteEntity")
    }
  }

  public fun deleteCliente(id: Long) {
    driver.execute(-646_194_073, """DELETE FROM ClienteEntity WHERE id = ?""", 1) {
          bindLong(0, id)
        }
    notifyQueries(-646_194_073) { emit ->
      emit("ClienteEntity")
    }
  }

  public fun removeAllVentas() {
    driver.execute(356_706_588, """DELETE FROM VentaEntity""", 0)
    notifyQueries(356_706_588) { emit ->
      emit("VentaEntity")
    }
  }

  public fun insertVenta(
    id: String,
    cliente_id: Long,
    total: Double,
    fecha_compra: String,
    created_at: String,
    updated_at: String,
  ) {
    driver.execute(1_151_546_283,
        """INSERT INTO VentaEntity (id, cliente_id, total, fecha_compra, created_at, updated_at) VALUES (?, ?, ?,?, ?, ?)""",
        6) {
          bindString(0, id)
          bindLong(1, cliente_id)
          bindDouble(2, total)
          bindString(3, fecha_compra)
          bindString(4, created_at)
          bindString(5, updated_at)
        }
    notifyQueries(1_151_546_283) { emit ->
      emit("VentaEntity")
    }
  }

  public fun updateVenta(
    cliente_id: Long,
    total: Double,
    fecha_compra: String,
    updated_at: String,
    is_deleted: Long,
    id: String,
  ) {
    driver.execute(681_248_667,
        """UPDATE VentaEntity SET cliente_id = ?, total = ?, fecha_compra = ?, updated_at = ?, is_deleted = ? WHERE id = ?""",
        6) {
          bindLong(0, cliente_id)
          bindDouble(1, total)
          bindString(2, fecha_compra)
          bindString(3, updated_at)
          bindLong(4, is_deleted)
          bindString(5, id)
        }
    notifyQueries(681_248_667) { emit ->
      emit("VentaEntity")
    }
  }

  public fun deleteVenta(id: String) {
    driver.execute(132_872_185, """DELETE FROM VentaEntity WHERE id = ?""", 1) {
          bindString(0, id)
        }
    notifyQueries(132_872_185) { emit ->
      emit("VentaEntity")
    }
  }

  public fun removeAllLineasVentas() {
    driver.execute(-536_722_398, """DELETE FROM LineaVentaEntity""", 0)
    notifyQueries(-536_722_398) { emit ->
      emit("LineaVentaEntity")
    }
  }

  public fun insertLineaVenta(
    id: String,
    venta_id: String,
    producto_id: String,
    producto_tipo: String,
    cantidad: Long,
    precio: Double,
    created_at: String,
    updated_at: String,
  ) {
    driver.execute(97_694_048,
        """INSERT INTO LineaVentaEntity (id, venta_id, producto_id, producto_tipo, cantidad, precio, created_at, updated_at) VALUES (?, ?, ?, ?, ?,?, ?, ?)""",
        8) {
          bindString(0, id)
          bindString(1, venta_id)
          bindString(2, producto_id)
          bindString(3, producto_tipo)
          bindLong(4, cantidad)
          bindDouble(5, precio)
          bindString(6, created_at)
          bindString(7, updated_at)
        }
    notifyQueries(97_694_048) { emit ->
      emit("LineaVentaEntity")
    }
  }

  public fun updateLineaVenta(
    venta_id: String,
    producto_id: String,
    producto_tipo: String,
    cantidad: Long,
    precio: Double,
    updated_at: String,
    is_deleted: Long,
    id: String,
  ) {
    driver.execute(-1_403_923_600,
        """UPDATE LineaVentaEntity SET venta_id = ?, producto_id = ?, producto_tipo = ?,  cantidad = ?, precio = ?, updated_at = ?, is_deleted = ? WHERE id = ?""",
        8) {
          bindString(0, venta_id)
          bindString(1, producto_id)
          bindString(2, producto_tipo)
          bindLong(3, cantidad)
          bindDouble(4, precio)
          bindString(5, updated_at)
          bindLong(6, is_deleted)
          bindString(7, id)
        }
    notifyQueries(-1_403_923_600) { emit ->
      emit("LineaVentaEntity")
    }
  }

  public fun deleteLineaVenta(id: String) {
    driver.execute(-1_641_091_630, """DELETE FROM LineaVentaEntity WHERE id = ?""", 1) {
          bindString(0, id)
        }
    notifyQueries(-1_641_091_630) { emit ->
      emit("LineaVentaEntity")
    }
  }

  private inner class SelectClienteByNumSocioQuery<out T : Any>(
    public val numSocio: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("ClienteEntity", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("ClienteEntity", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-1_000_163_564,
        """SELECT ClienteEntity.id, ClienteEntity.nombre, ClienteEntity.email, ClienteEntity.numSocio, ClienteEntity.created_at, ClienteEntity.updated_at, ClienteEntity.is_deleted FROM ClienteEntity WHERE numSocio = ?""",
        mapper, 1) {
      bindString(0, numSocio)
    }

    override fun toString(): String = "Database.sq:selectClienteByNumSocio"
  }

  private inner class SelectClienteByIdQuery<out T : Any>(
    public val id: Long,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("ClienteEntity", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("ClienteEntity", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(480_404_712,
        """SELECT ClienteEntity.id, ClienteEntity.nombre, ClienteEntity.email, ClienteEntity.numSocio, ClienteEntity.created_at, ClienteEntity.updated_at, ClienteEntity.is_deleted FROM ClienteEntity WHERE id = ?""",
        mapper, 1) {
      bindLong(0, id)
    }

    override fun toString(): String = "Database.sq:selectClienteById"
  }

  private inner class SelectAllClientesByIsDeletedQuery<out T : Any>(
    public val is_deleted: Long,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("ClienteEntity", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("ClienteEntity", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-577_332_062,
        """SELECT ClienteEntity.id, ClienteEntity.nombre, ClienteEntity.email, ClienteEntity.numSocio, ClienteEntity.created_at, ClienteEntity.updated_at, ClienteEntity.is_deleted FROM ClienteEntity WHERE is_deleted = ?""",
        mapper, 1) {
      bindLong(0, is_deleted)
    }

    override fun toString(): String = "Database.sq:selectAllClientesByIsDeleted"
  }

  private inner class SelectVentaByIdQuery<out T : Any>(
    public val id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("VentaEntity", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("VentaEntity", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(1_713_741_626,
        """SELECT VentaEntity.id, VentaEntity.cliente_id, VentaEntity.total, VentaEntity.fecha_compra, VentaEntity.created_at, VentaEntity.updated_at, VentaEntity.is_deleted FROM VentaEntity WHERE id = ?""",
        mapper, 1) {
      bindString(0, id)
    }

    override fun toString(): String = "Database.sq:selectVentaById"
  }

  private inner class ExistsVentaQuery<out T : Any>(
    public val id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("VentaEntity", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("VentaEntity", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-945_123_352,
        """SELECT COUNT(*) > 0 AS es_mayor_cero FROM VentaEntity WHERE id = ?""", mapper, 1) {
      bindString(0, id)
    }

    override fun toString(): String = "Database.sq:existsVenta"
  }

  private inner class SelectAllVentasByIsDeletedQuery<out T : Any>(
    public val is_deleted: Long,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("VentaEntity", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("VentaEntity", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(1_616_577_204,
        """SELECT VentaEntity.id, VentaEntity.cliente_id, VentaEntity.total, VentaEntity.fecha_compra, VentaEntity.created_at, VentaEntity.updated_at, VentaEntity.is_deleted FROM VentaEntity WHERE is_deleted = ?""",
        mapper, 1) {
      bindLong(0, is_deleted)
    }

    override fun toString(): String = "Database.sq:selectAllVentasByIsDeleted"
  }

  private inner class SelectVentasByDateQuery<out T : Any>(
    public val fecha_compra: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("VentaEntity", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("VentaEntity", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-1_192_590_672,
        """SELECT VentaEntity.id, VentaEntity.cliente_id, VentaEntity.total, VentaEntity.fecha_compra, VentaEntity.created_at, VentaEntity.updated_at, VentaEntity.is_deleted FROM VentaEntity WHERE fecha_compra = ?""",
        mapper, 1) {
      bindString(0, fecha_compra)
    }

    override fun toString(): String = "Database.sq:selectVentasByDate"
  }

  private inner class SelectLineaVentaByIdQuery<out T : Any>(
    public val id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("LineaVentaEntity", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("LineaVentaEntity", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-901_344_683,
        """SELECT LineaVentaEntity.id, LineaVentaEntity.venta_id, LineaVentaEntity.producto_id, LineaVentaEntity.producto_tipo, LineaVentaEntity.cantidad, LineaVentaEntity.precio, LineaVentaEntity.created_at, LineaVentaEntity.updated_at, LineaVentaEntity.is_deleted FROM LineaVentaEntity WHERE id = ?""",
        mapper, 1) {
      bindString(0, id)
    }

    override fun toString(): String = "Database.sq:selectLineaVentaById"
  }

  private inner class SelectAllLineasVentaByVentaIdQuery<out T : Any>(
    public val venta_id: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("LineaVentaEntity", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("LineaVentaEntity", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-1_629_299_385,
        """SELECT LineaVentaEntity.id, LineaVentaEntity.venta_id, LineaVentaEntity.producto_id, LineaVentaEntity.producto_tipo, LineaVentaEntity.cantidad, LineaVentaEntity.precio, LineaVentaEntity.created_at, LineaVentaEntity.updated_at, LineaVentaEntity.is_deleted FROM LineaVentaEntity WHERE venta_id = ?""",
        mapper, 1) {
      bindString(0, venta_id)
    }

    override fun toString(): String = "Database.sq:selectAllLineasVentaByVentaId"
  }

  private inner class SelectAllLineasVentasByIsDeletedQuery<out T : Any>(
    public val is_deleted: Long,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("LineaVentaEntity", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("LineaVentaEntity", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-1_847_693_842,
        """SELECT LineaVentaEntity.id, LineaVentaEntity.venta_id, LineaVentaEntity.producto_id, LineaVentaEntity.producto_tipo, LineaVentaEntity.cantidad, LineaVentaEntity.precio, LineaVentaEntity.created_at, LineaVentaEntity.updated_at, LineaVentaEntity.is_deleted FROM LineaVentaEntity WHERE is_deleted = ?""",
        mapper, 1) {
      bindLong(0, is_deleted)
    }

    override fun toString(): String = "Database.sq:selectAllLineasVentasByIsDeleted"
  }
}
