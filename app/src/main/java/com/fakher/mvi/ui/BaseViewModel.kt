package com.fakher.mvi.ui

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import com.fakher.mvi.utils.plusAssign
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseViewModel<A : BaseAction, S : BaseState>(private val initialState: S) : ViewModel() {

    val TAG = BaseViewModel::class.java.simpleName

    val state = BehaviorSubject.createDefault(initialState)

    abstract fun handleAction(action: A)

    @MainThread
    protected fun updateState(reducer: (S) -> S) {
        val newState = reducer(currentState())

        state.onNext(newState)
    }

    fun currentState(): S {
        return state.value ?: initialState
    }
}

interface BaseAction
interface BaseState

fun <T> Observable<T>.bind(owner: CompositeDisposable, observer: (T) -> Unit) {
    owner += this.subscribe(observer) {
        Log.e("Exception", it.message)
    }
}