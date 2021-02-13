package br.com.productsapp.commom.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.productsapp.commom.extensions.cancelIfActive
import br.com.productsapp.commom.extensions.isNotNullOrEmpty
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class CoroutineViewModel: ViewModel(), CoroutineScope {

    private val supervisorJob = SupervisorJob()
    private val _jobs = ArrayList<Job>()

    /**
     * Dispatchers.Main: use este agente para executar uma corrotina na linha de execução principal do Android. Ele só deve ser usado para interagir com a IU e realizar um trabalho rápido. Exemplos incluem chamar funções suspend, executar operações de framework de IU do Android e atualizar objetos LiveData.
     * Dispatchers.IO: este agente é otimizado para executar E/S de disco ou rede fora da linha de execução principal. Exemplos incluem uso do componente Room, leitura ou gravação de arquivos e execução de qualquer operação de rede.
     * Dispatchers.Default: este agente é otimizado para realizar trabalho intensivo da CPU fora da linha de execução principal. Exemplos de casos de uso incluem classificação de uma lista e análise de JSON.
     */
    final override val coroutineContext = Dispatchers.Main

    protected infix fun addJob(job: Job): Job {
        _jobs.add(job)
        return job
    }

    protected infix fun <T> addJobDeferred(job: Deferred<T>): Deferred<T> {
        _jobs.add(job)
        return job
    }

    protected fun joinJobs() {
        launch {
            if (_jobs.isNotNullOrEmpty()) {
                _jobs.forEach {
                    if (!it.isCancelled && it.isActive) it.join()
                }
            }
        }
    }

    protected fun launch (
        context: CoroutineContext = coroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return addJob ( viewModelScope.launch(context = supervisorJob + context, start = start, block = block) )
    }

    protected fun <T> async (
        context: CoroutineContext = coroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> T
    ): Deferred<T> = addJobDeferred ( viewModelScope.async(context = supervisorJob + context, start = start, block = block) )

    protected fun launchMain(start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit): Job {
        return launch(context = Dispatchers.Main, start = start, block = block)
    }
    protected fun <T> asyncMain(start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> T): Deferred<T> {
        return async(context = Dispatchers.Main, start = start, block = block)
    }

    protected fun launchMainImmediate(start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit): Job {
        return launch(context = Dispatchers.Main.immediate, start = start, block = block)
    }
    protected fun <T> asyncMainImmediate(start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> T): Deferred<T> {
        return async(context = Dispatchers.Main.immediate, start = start, block = block)
    }

    protected fun launchIO(start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit): Job {
        return launch(context = Dispatchers.IO, start = start, block = block)
    }
    protected fun <T> asyncIO(start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> T): Deferred<T> {
        return async(context = Dispatchers.IO, start = start, block = block)
    }

    protected fun launchDefault(start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit): Job {
        return launch(context = Dispatchers.Default, start = start, block = block)
    }
    protected fun <T> asyncDefault(start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> T): Deferred<T> {
        return async(context = Dispatchers.Default, start = start, block = block)
    }


    /**
     * It is necessary to do a final override of [ViewModel.onCleared] to ensure that [viewModelScope] is cancelled.
     *
     * Use [onClear] to perform logic after this event.
     */
    final override fun onCleared() {
        if (_jobs.isNotNullOrEmpty()) {
            _jobs.forEach { it.cancelIfActive() }
        }
        supervisorJob.cancelIfActive()
        viewModelScope.cancelIfActive()
        onClear()
    }

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     * <p>
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    protected open fun onClear() {}
}