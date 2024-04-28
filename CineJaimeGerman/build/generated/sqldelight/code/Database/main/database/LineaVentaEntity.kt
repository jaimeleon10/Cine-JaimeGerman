package database

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class LineaVentaEntity(
  public val id: String,
  public val venta_id: String,
  public val producto_id: String,
  public val producto_tipo: String,
  public val cantidad: Long,
  public val precio: Double,
  public val created_at: String,
  public val updated_at: String,
  public val is_deleted: Long,
)
