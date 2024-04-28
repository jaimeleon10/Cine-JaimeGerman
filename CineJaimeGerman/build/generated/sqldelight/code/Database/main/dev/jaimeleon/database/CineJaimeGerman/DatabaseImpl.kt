package dev.jaimeleon.database.CineJaimeGerman

import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import database.DatabaseQueries
import dev.jaimeleon.database.Database
import kotlin.Long
import kotlin.Unit
import kotlin.reflect.KClass

internal val KClass<Database>.schema: SqlSchema<QueryResult.Value<Unit>>
  get() = DatabaseImpl.Schema

internal fun KClass<Database>.newInstance(driver: SqlDriver): Database = DatabaseImpl(driver)

private class DatabaseImpl(
  driver: SqlDriver,
) : TransacterImpl(driver), Database {
  override val databaseQueries: DatabaseQueries = DatabaseQueries(driver)

  public object Schema : SqlSchema<QueryResult.Value<Unit>> {
    override val version: Long
      get() = 1

    override fun create(driver: SqlDriver): QueryResult.Value<Unit> {
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS ProductoEntity (
          |    id INTEGER PRIMARY KEY,
          |    tipo TEXT NOT NULL,
          |    precio REAL NOT NULL,
          |    stock INTEGER NOT NULL,
          |    created_at TEXT DEFAULT CURRENT_TIMESTAMP,
          |    updated_at TEXT DEFAULT CURRENT_TIMESTAMP,
          |    is_deleted TEXT DEFAULT "false"
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS ClienteEntity (
          |      id INTEGER PRIMARY KEY,
          |      nombre TEXT NOT NULL,
          |      email TEXT NOT NULL,
          |      numSocio TEXT NOT NULL UNIQUE,
          |      created_at TEXT NOT NULL,
          |      updated_at TEXT NOT NULL,
          |      is_deleted INTEGER NOT NULL DEFAULT 0
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS VentaEntity (
          |    id TEXT PRIMARY KEY,
          |    cliente_id INTEGER NOT NULL REFERENCES ClienteEntity(id),
          |    total REAL NOT NULL,
          |    fecha_compra TEXT NOT NULL,
          |    created_at TEXT NOT NULL,
          |    updated_at TEXT NOT NULL,
          |    is_deleted INTEGER NOT NULL DEFAULT 0
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS LineaVentaEntity (
          |     id TEXT PRIMARY KEY,
          |     venta_id TEXT NOT NULL REFERENCES VentaEntity(id),
          |     producto_id TEXT NOT NULL REFERENCES ProductoEntity(id),
          |     producto_tipo TEXT NOT NULL,
          |     cantidad INTEGER NOT NULL,
          |     precio REAL NOT NULL,
          |     created_at TEXT NOT NULL,
          |     updated_at TEXT NOT NULL,
          |     is_deleted INTEGER NOT NULL DEFAULT 0
          |)
          """.trimMargin(), 0)
      return QueryResult.Unit
    }

    override fun migrate(
      driver: SqlDriver,
      oldVersion: Long,
      newVersion: Long,
      vararg callbacks: AfterVersion,
    ): QueryResult.Value<Unit> = QueryResult.Unit
  }
}
