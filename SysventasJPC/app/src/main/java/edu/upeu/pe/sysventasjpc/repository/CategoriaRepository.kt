package edu.upeu.pe.sysventasjpc.repository

import edu.upeu.pe.sysventasjpc.data.remote.RestCategoria
import edu.upeu.pe.sysventasjpc.modelo.Categoria
import edu.upeu.pe.sysventasjpc.utils.TokenUtils
import javax.inject.Inject

interface CategoriaRepository {
    suspend fun findAll(): List<Categoria>
}
class CategoriaRepositoryImp @Inject constructor(
    private val rest: RestCategoria,
): CategoriaRepository{
    override suspend fun findAll(): List<Categoria> {
        val response =
            rest.reportarCategorias(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?:emptyList()
        else emptyList()
    }
}