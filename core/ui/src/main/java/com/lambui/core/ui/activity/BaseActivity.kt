package com.lambui.core.ui.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelLazy
import androidx.viewbinding.ViewBinding
import com.lambui.core.ui.viewmodel.BaseViewModel
import java.net.HttpURLConnection
import kotlin.reflect.KClass

/**
 * Created by LamBD on 17/06/24.
 */
abstract class BaseActivity<viewModel : BaseViewModel, viewBinding : ViewBinding>(viewModelClass: KClass<viewModel>) :
    AppCompatActivity() {

    protected val viewModel by ViewModelLazy(
        viewModelClass,
        { viewModelStore },
        { defaultViewModelProviderFactory },
        { this.defaultViewModelCreationExtras })
    protected lateinit var viewBinding: viewBinding
    abstract fun inflateViewBinding(inflater: LayoutInflater): viewBinding

    protected var progressDialog: ProgressDialog? = null

    protected abstract fun initialize()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = inflateViewBinding(layoutInflater)
        progressDialog = ProgressDialog(this)
        setContentView(viewBinding.root)
        initialize()
        onSubscribeObserver()
    }

    fun showLoading(isShow: Boolean) {
        if (isShow && progressDialog?.isShowing.isNotTrue()) {
            progressDialog?.show()
        } else if (progressDialog?.isShowing.isTrue()) {
            progressDialog?.dismiss()
        }
    }

    open fun onSubscribeObserver() {
        viewModel.run {
            isLoading.launchAndCollect {
                showLoading(it)
            }
            error.launchAndCollect {
                handleApiError(it)
            }
        }
    }

    fun handleApiError(throwable: Throwable) {
        val networkError = AppErrors.fromThrowable(throwable)
        if (networkError?.errorCode?.toIntOrZero() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            dialog {
                message = networkError.message ?: throwable.message ?: "Unknown"
            }
            return
        }
        dialog {
            message = networkError?.message ?: throwable.message ?: "Unknown"
        }
    }

    protected inline infix fun <T> Flow<T>.launchAndCollect(crossinline action: (T) -> Unit) {
        with(this) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    collect { action(it) }
                }
            }
        }
    }
}