package br.com.productsapp.ui.main

import androidx.lifecycle.*
import br.com.productsapp.commom.base.CoroutineViewModel
import br.com.productsapp.data.model.ProductsResponse
import br.com.productsapp.data.repository.main.MainRemoteDataSource
import br.com.productsapp.data.repository.main.MainRepository
import br.com.productsapp.data.source.remote.Resource
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repositoryAll: MainRepository
    ) : CoroutineViewModel(), LifecycleObserver {

    private val _productsResult = MutableLiveData<Resource<ProductsResponse>?>()
    val productsResponse: LiveData<Resource<ProductsResponse>?>
        get() = _productsResult

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun load() {
        getAllProducts()
    }

    private fun getAllProducts() {
        _productsResult.value = Resource.loading()
        launchIO {
            try {
                val resourceResult = repositoryAll.getAllProducts()
                resourceResult.let {
                    _productsResult.postValue(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.e(e, e.localizedMessage)
                _productsResult.postValue(Resource.error(exception = e))
            }
        }
    }
}
