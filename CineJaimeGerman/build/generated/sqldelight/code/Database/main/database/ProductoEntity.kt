package database

import kotlin.Double
import kotlin.Long
import kotlin.String

public data class ProductoEntity(
  public val id: Long,
  public val tipo: String,
  public val precio: Double,
  public val stock: Long,
  public val created_at: String?,
  public val updated_at: String?,
  public val is_deleted: String?,
)
