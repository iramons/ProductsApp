package br.com.productsapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import br.com.productsapp.R
import br.com.productsapp.commom.base.BaseActivity
import br.com.productsapp.commom.base.BaseRecyclerViewAdapter
import br.com.productsapp.commom.base.ItemTypeFactoryImpl
import br.com.productsapp.commom.extensions.ActivityExtensions.setupToolbar
import br.com.productsapp.data.model.ProductsList
import br.com.productsapp.data.model.ProductsResponse
import br.com.productsapp.data.model.SpotlightList
import br.com.productsapp.data.source.remote.Status
import br.com.productsapp.databinding.ActivityMainBinding
import br.com.productsapp.di.CustomViewModelFactory
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: CustomViewModelFactory

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: BaseRecyclerViewAdapter

    override val layoutId: Int
        get() = R.layout.activity_main

    private val rootView: View
        get() = binding.root


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initUI()
    }

    override fun initUI() {
        setupToolbar(title = "Olá, Maria")
        createRecyclerViewAndAdapter()
        initObservers()
    }

    private fun createRecyclerViewAndAdapter() {
        mAdapter = BaseRecyclerViewAdapter(ItemTypeFactoryImpl())
        recyclerView = rootView.findViewById(R.id.recycler_view_main) as RecyclerView
        recyclerView.apply {
            this.adapter = mAdapter
            (this.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    override fun initObservers() {
        lifecycle.addObserver(viewModel)

        viewModel.productsResponse.observe(this, Observer {
            when (it?.status) {
                Status.LOADING -> {
                    onLoading(true)
                }
                Status.EMPTY,
                Status.ERROR -> {
                    onLoading(false)
//                    rootView.context.showError(it)
                }
                Status.SUCCESS -> {
                    if (it.data != null)
                        setAdapterData(it.data)
                    onLoading(false)
                }
                else -> {}
            }
        })

        fetchData()
    }

    private fun fetchData() {
        viewModel.getAllProducts()
    }

    private fun setAdapterData(response: ProductsResponse?) {
        if (response != null) {
            val spotlightList = listOf(SpotlightList(items = response.spotlight!!))
            mAdapter.addItems(spotlightList)

            val cashList = listOf(response.cash!!)
            mAdapter.addItems(cashList)

            val productsList = listOf(ProductsList(getString(R.string.products), response.products!!))
            mAdapter.addItems(productsList)
        }
    }

}