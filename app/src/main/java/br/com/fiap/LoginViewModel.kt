package br.com.fiap

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel(private val view: LoginView) : ViewModel() {
    private val repository = LoginRepository

    val viewState = LoginViewState()

    fun login(name: String, password: String) = GlobalScope.launch(Dispatchers.IO) {
        viewState.state.postValue(LoginViewState.State.LOADING)
        Session.name = name
        Session.password = password
        val result = repository.request(Session.name, Session.password)
        if (result is LoginRepository.LoginResult.Logged) {
            viewState.state.postValue(LoginViewState.State.SUCCESS)
            view.navigateToGoals()
        } else {
            viewState.state.postValue(LoginViewState.State.ERROR)
        }
    }
}