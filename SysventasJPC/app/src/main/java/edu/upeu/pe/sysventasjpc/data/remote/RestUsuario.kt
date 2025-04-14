package edu.upeu.pe.sysventasjpc.data.remote

import edu.upeu.pe.sysventasjpc.modelo.UsuarioDto
import edu.upeu.pe.sysventasjpc.modelo.UsuarioResp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RestUsuario {
    @POST("/users/login")
    suspend fun login(@Body user:UsuarioDto):Response<UsuarioResp>
}