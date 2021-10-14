package br.com.fiap

import androidx.lifecycle.ViewModel
import br.com.fiap.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel(private val view: LoginView) : ViewModel() {
    private val repository = LoginRepository

    val viewState = LoginViewState()

    fun login(funcional: String, password: String) = GlobalScope.launch(Dispatchers.IO) {
        viewState.state.postValue(LoginViewState.State.LOADING)
        Session.funcional = funcional
        Session.password = password
        val result = repository.request(Session.funcional, Session.password)
        if (result is LoginRepository.LoginResult.Logged) {
            viewState.state.postValue(LoginViewState.State.SUCCESS)
            view.navigateToGoals()
        } else {
            viewState.state.postValue(LoginViewState.State.ERROR)
        }
    }
}