package edu.upeu.pe.sysventasjpc

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import edu.upeu.pe.sysventasjpc.utils.isNight

@ExperimentalCoroutinesApi
@HiltAndroidApp
class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        val mode=if (isNight()){
            AppCompatDelegate.MODE_NIGHT_YES
        }else{
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}
