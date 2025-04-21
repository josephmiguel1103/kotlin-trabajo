package edu.upeu.pe.sysventasjpc.repository

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import edu.upeu.pe.sysventasjpc.data.local.dao.MarcaDao
import edu.upeu.pe.sysventasjpc.data.remote.RestMarca
import edu.upeu.pe.sysventasjpc.modelo.Marca
import edu.upeu.pe.sysventasjpc.utils.TokenUtils
import edu.upeu.pe.sysventasjpc.utils.isNetworkAvailable
import javax.inject.Inject

interface MarcaRepository {
    suspend fun findAll(): List<Marca>
    suspend fun findAllR(): Flow<List<Marca>>
}
class MarcaRepositoryImp @Inject constructor(
    private val rest: RestMarca,
    private val dao: MarcaDao,
): MarcaRepository{
    override suspend fun findAll(): List<Marca> {
        val response =
            rest.reportarMarcas(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?:
        emptyList()
        else emptyList()
    }
    override suspend fun findAllR(): Flow<List<Marca>> {

        try {
            CoroutineScope(Dispatchers.IO).launch{
                if(isNetworkAvailable(TokenUtils.CONTEXTO_APPX)){
                    val data=rest.reportarMarcas(TokenUtils.TOKEN_CONTENT).body()!!
                    dao.insertAll(data)
                }
            }
        }catch (e:Exception      ){
            Log.e("Error ", "Error: ${e.message}")
        }
        return dao.getAll()
    }
}
