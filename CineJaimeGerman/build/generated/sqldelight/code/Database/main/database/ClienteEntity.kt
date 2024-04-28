package database

import kotlin.Long
import kotlin.String

public data class ClienteEntity(
  public val id: Long,
  public val nombre: String,
  public val email: String,
  public val numSocio: String,
  public val created_at: String,
  public val updated_at: String,
  public val is_deleted: Long,
)
