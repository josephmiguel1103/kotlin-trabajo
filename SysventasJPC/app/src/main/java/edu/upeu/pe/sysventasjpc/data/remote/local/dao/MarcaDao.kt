package pe.edu.upeu.sysventasjpc.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import pe.edu.upeu.sysventasjpc.modelo.Marca
@Dao
interface MarcaDao {
    @Query("SELECT * FROM marca")
    fun getAll(): Flow<List<Marca>>  // Cambiado

    @Query("SELECT * FROM marca where id_marca=:id")
    fun getFinId(id: Long): Flow<Marca>  // Cambiado

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg to: Marca)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(to: List<Marca>)

    @Delete
    suspend fun delete(to: Marca)

    @Update
    suspend fun update(to: Marca)
}
