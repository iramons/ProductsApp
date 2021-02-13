package br.com.productsapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Test
    fun `when viewModel _productsResponse get success then set productsResponse LiveData`() {
//        viewModel = MainViewModel()
    }
}

//class MockRepository() : ProductsRepository {
//
//}