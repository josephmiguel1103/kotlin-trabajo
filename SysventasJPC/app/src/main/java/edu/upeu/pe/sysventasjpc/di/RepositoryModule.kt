package edu.upeu.pe.sysventasjpc.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.upeu.pe.sysventasjpc.repository.UsuarioRepository
import edu.upeu.pe.sysventasjpc.repository.UsuarioRepositoryImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun
            userRepository(userRepos:UsuarioRepositoryImp):UsuarioRepository
}