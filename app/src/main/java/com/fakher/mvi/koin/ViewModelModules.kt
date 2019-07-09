package com.fakher.mvi.koin

import com.fakher.mvi.ui.list.ListViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel { ListViewModel(get()) }
}