package com.example.masterand.providers

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.masterand.MasterAndApplication
import com.example.masterand.viewModels.ProfileViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ProfileViewModel(masterAndApplication().container.playersRepository)
        }
        //tutaj dodać inicializator dla każdego ViewModelu...
    }
}
fun CreationExtras.masterAndApplication(): MasterAndApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as
            MasterAndApplication)
