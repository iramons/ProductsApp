package br.com.productsapp.commom.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.productsapp.R

object ActivityExtensions {

    /** config Toolbar para AppCompatActivity */
    fun AppCompatActivity.setupToolbar(
        toolbar: Toolbar? = this.findViewById(R.id.toolbar_default),
        title: String = "",
        subtitle: String? = "",
    ) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
        supportActionBar?.subtitle = subtitle
    }
}