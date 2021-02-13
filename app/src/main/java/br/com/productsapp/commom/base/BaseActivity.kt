package br.com.productsapp.commom.base

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.view.isVisible
import br.com.productsapp.R
import br.com.productsapp.util.ConnectionMonitorLiveData
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(), IBaseContract {

    @Inject
    lateinit var connectionLiveData: ConnectionMonitorLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNetObserver()
    }

    private fun initNetObserver() {
        connectionLiveData.observe(this, { isConnected ->
            isConnected?.let {
                //TODO: show a view to notify when user is offline
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            recreate()
        }
    }

    fun onLoading(show: Boolean) {
        try {
            val shimmer = findViewById<ShimmerFrameLayout>(R.id.shimmerLayout)
            if (shimmer != null && !isFinishing) {
                shimmer.isVisible = show

                if (show)
                    shimmer.startShimmerAnimation()
                else
                    shimmer.stopShimmerAnimation()
            }
        } catch (e: Exception) {
            Timber.e(e)
            e.printStackTrace()
        }
    }
}