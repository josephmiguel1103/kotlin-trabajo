package edu.upeu.pe.sysventasjpc.ui.presentation.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import edu.upeu.pe.sysventasjpc.modelo.UsuarioDto
import edu.upeu.pe.sysventasjpc.modelo.UsuarioResp
import edu.upeu.pe.sysventasjpc.repository.UsuarioRepository
import edu.upeu.pe.sysventasjpc.utils.TokenUtils

import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UsuarioRepository
) : ViewModel(){


    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isLoading: LiveData<Boolean> get() = _isLoading


    private val _islogin: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val islogin: LiveData<Boolean> get() = _islogin



    val isError=MutableLiveData<Boolean>(false)

    val listUser = MutableLiveData<UsuarioResp>()
    fun loginSys(toData: UsuarioDto) {
        Log.i("LOGIN", toData.user)
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            _islogin.postValue(false)

            val totek=userRepo.loginUsuario(toData).body()

            delay(1500L)
            TokenUtils.TOKEN_CONTENT="Bearer "+totek?.token
            Log.i("DATAXDMP", "Holas")
            listUser.postValue(totek!!)
            Log.i("DATAXDMP", TokenUtils.TOKEN_CONTENT)
            if(TokenUtils.TOKEN_CONTENT!="Bearer null"){
                TokenUtils.USER_LOGIN=toData.user
                _islogin.postValue(true)
            }else{
                isError.postValue(true)
                delay(1500L)
                isError.postValue(false)
            }
            _isLoading.postValue(false)
        }
    }
}