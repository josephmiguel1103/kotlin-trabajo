package edu.upeu.pe.sysventasjpc.repository

import edu.upeu.pe.sysventasjpc.data.remote.RestUnidadMedida
import edu.upeu.pe.sysventasjpc.modelo.UnidadMedida
import edu.upeu.pe.sysventasjpc.utils.TokenUtils
import javax.inject.Inject

interface UnidadMedidaRepository {
    suspend fun findAll(): List<UnidadMedida>
}
class UnidadMedidaRepositoryImp @Inject constructor(
    private val rest: RestUnidadMedida,
): UnidadMedidaRepository{
    override suspend fun findAll(): List<UnidadMedida> {
        val response =
            rest.reportarUnidadMedida(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?:
        emptyList()
        else emptyList()
    }
}
