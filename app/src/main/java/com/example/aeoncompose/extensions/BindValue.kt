package com.example.aeoncompose.extensions

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.aeoncompose.api.process_api.RetrofitService
import com.example.aeoncompose.ui.view.preload.PreloadViewModel
import javax.inject.Inject

/**
 * Created by antdg-intelin on 09/06/2020.
 */

//@MainThread
//inline fun <reified T: ViewModel>Fragment.bindViewModel(viewModelFactory: ViewModelProviderFactory) = lazy(LazyThreadSafetyMode.NONE) {
//    ViewModelProviders.of(this, viewModelFactory).get(T::class.java)
//}
