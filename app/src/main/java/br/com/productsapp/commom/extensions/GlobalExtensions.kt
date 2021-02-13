package br.com.productsapp.commom.extensions

import android.os.Build
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**Check Version API Android*/
fun isAtLeastLollipop(): Boolean {
    return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
}

fun isAtLeastNougat(): Boolean {
    return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
}

/** Cancel the Job if it's active.*/
fun Job?.cancelIfActive() {
    if (this?.isActive == true) {
        cancelChildren()
        cancel()
    }
}

/**Cancel the CoroutineContext if it's active.*/
fun CoroutineContext?.cancelIfActive() {
    if (this?.isActive == true) {
        cancelChildren()
        cancel()
    }
}
/**Cancel the CoroutineContext if it's active.*/
fun CoroutineScope?.cancelIfActive() {
    this?.coroutineContext.cancelIfActive()
}
