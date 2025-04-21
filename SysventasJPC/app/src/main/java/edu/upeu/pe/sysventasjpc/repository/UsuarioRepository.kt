package edu.upeu.pe.sysventasjpc.repository

import edu.upeu.pe.sysventasjpc.data.remote.RestUsuario
import edu.upeu.pe.sysventasjpc.modelo.UsuarioDto
import edu.upeu.pe.sysventasjpc.modelo.UsuarioResp
import retrofit2.Response
import javax.inject.Inject

interface UsuarioRepository {
    suspend fun loginUsuario(user: UsuarioDto):
            Response<UsuarioResp>
}
class UsuarioRepositoryImp @Inject constructor(private val
                                               restUsuario: RestUsuario):UsuarioRepository{
    override suspend fun loginUsuario(user:UsuarioDto):
            Response<UsuarioResp>{
        return restUsuario.login(user)
    }
}
