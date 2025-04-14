package edu.upeu.pe.sysventasjpc.modelo

data class UsuarioDto(
    var user: String,
    var clave: String,
)
data class UsuarioResp(
    val idUsuario: Long,
    val user: String,
    val estado: String,
    val token: String,
)