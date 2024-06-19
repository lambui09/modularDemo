package com.lambui.core.ui.activity

import androidx.appcompat.app.AppCompatActivity
import com.lambui.core.ui.viewmodel.BaseViewModel
/**
 * Created by LamBD on 17/06/24.
 */
abstract class BaseActivity<viewModel : BaseViewModel, viewBinding : ViewBinding>(viewModelClass: KClass<viewModel>) :
    AppCompatActivity() {

    }