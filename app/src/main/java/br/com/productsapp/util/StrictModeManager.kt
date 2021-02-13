package br.com.productsapp.util

import android.os.StrictMode

object StrictModeManager {

    fun enableStrictMode() {
        val threadPolicyBuilder =
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDropBox()

        val vmPolicyBuilder = StrictMode.VmPolicy.Builder()
            .detectLeakedSqlLiteObjects()
            .detectLeakedClosableObjects()
            .detectLeakedRegistrationObjects()
            .penaltyLog()
            .penaltyDropBox()
            .penaltyDeath()
        StrictMode.setThreadPolicy(threadPolicyBuilder.build())
        StrictMode.setVmPolicy(vmPolicyBuilder.build())
    }

    fun allowDiskReads(block: () -> Unit) {
        val oldThreadPolicy = StrictMode.getThreadPolicy()
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder(oldThreadPolicy)
                .permitDiskReads()
                .build()
        )
        val anyValue = block()
        if (oldThreadPolicy != null)
            StrictMode.setThreadPolicy(oldThreadPolicy)
        return anyValue
    }
}