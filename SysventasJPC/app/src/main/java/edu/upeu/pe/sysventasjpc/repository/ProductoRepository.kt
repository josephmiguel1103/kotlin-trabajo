package edu.upeu.pe.sysventasjpc.repository

import edu.upeu.pe.sysventasjpc.data.remote.RestProducto
import edu.upeu.pe.sysventasjpc.modelo.ProductoDto
import edu.upeu.pe.sysventasjpc.modelo.ProductoResp
import edu.upeu.pe.sysventasjpc.utils.TokenUtils
import javax.inject.Inject

interface ProductoRepository {
    suspend fun deleteProducto(producto: ProductoDto): Boolean
    suspend fun reportarProductos(): List<ProductoResp> // Cambiado
    suspend fun buscarProductoId(id: Long): ProductoResp // Cambiado
    suspend fun insertarProducto(producto: ProductoDto): Boolean
    suspend fun modificarProducto(producto: ProductoDto): Boolean
}
class ProductoRepositoryImp @Inject constructor(
    private val restProducto: RestProducto,
    //private val actividadDao: ActividadDao,
): ProductoRepository{
    override suspend fun deleteProducto(producto: ProductoDto): Boolean {
        val response =
            restProducto.deleteProducto(TokenUtils.TOKEN_CONTENT,
                producto.idProducto)
        return response.isSuccessful && response.body()?.message ==
                "true"
    }
    override suspend fun reportarProductos(): List<ProductoResp> {
        val response =
            restProducto.reportarProducto(TokenUtils.TOKEN_CONTENT)
        return if (response.isSuccessful) response.body() ?: emptyList()
        else emptyList()
    }
    override suspend fun buscarProductoId(id: Long): ProductoResp {
        val response =
            restProducto.getProductoId(TokenUtils.TOKEN_CONTENT, id)
        return response.body() ?: throw Exception("Producto no encontrado")
    }
    override suspend fun insertarProducto(producto: ProductoDto): Boolean
    {
        val response =
            restProducto.insertarProducto(TokenUtils.TOKEN_CONTENT, producto)
        return response.isSuccessful && response.body()?.message ==
                "true"
    }
    override suspend fun modificarProducto(producto: ProductoDto):
            Boolean {
        val response =
            restProducto.actualizarProducto(TokenUtils.TOKEN_CONTENT,
                producto.idProducto, producto)
        return response.isSuccessful && response.body()?.idProducto !=
                null
    }
}
