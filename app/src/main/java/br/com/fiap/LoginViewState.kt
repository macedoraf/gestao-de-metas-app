package br.com.fiap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class LoginViewState {
    val state = MutableLiveData<State>()

    val isSuccess = Transformations.map(state) { state ->
        state == State.SUCCESS
    }

    val isError = Transformations.map(state) { state ->
        state == State.ERROR
    }

    val isLoading = Transformations.map(state) {
        it == State.LOADING
    }


    enum class State {
        SUCCESS, ERROR, IDLE, LOADING
    }
}
