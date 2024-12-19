//package com.example.masterand.providers
//
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.viewmodel.CreationExtras
//import androidx.lifecycle.viewmodel.initializer
//import androidx.lifecycle.viewmodel.viewModelFactory
//import com.example.masterand.MasterAndApplication
//import com.example.masterand.viewModels.GameViewModel
//import com.example.masterand.viewModels.ProfileViewModel
//import com.example.masterand.viewModels.ResultsViewModel
//
//object AppViewModelProvider {
//    val Factory = viewModelFactory {
//        initializer {
//            ProfileViewModel(masterAndApplication().container.playersRepository)
//        }
//
//        initializer {
//            GameViewModel(
//                masterAndApplication().container.playersRepository,
//                masterAndApplication().container.scoresRepository
//            )
//        }
//
//        initializer {
//            ResultsViewModel(
//                masterAndApplication().container.playerScoresRepository
//            )
//        }
//    }
//}
//fun CreationExtras.masterAndApplication(): MasterAndApplication =
//    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as
//            MasterAndApplication)
