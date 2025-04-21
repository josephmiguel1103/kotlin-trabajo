package edu.upeu.pe.sysventasjpc.data.remote

import edu.upeu.pe.sysventasjpc.modelo.Marca
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface RestMarca{
    companion object {
        const val BASE_RUTA = "/marcas"
    }
    @GET("${BASE_RUTA}")
    suspend fun reportarMarcas(@Header("Authorization")
                               token:String): Response<List<Marca>>
}