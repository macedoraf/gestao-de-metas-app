package br.com.fiap

import androidx.lifecycle.ViewModel
import br.com.fiap.repository.GoalsRepository
import br.com.fiap.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GoalsViewModel : ViewModel() {

    val viewState = GoalsViewState()

    private val repository = GoalsRepository

    private val myGoals: ArrayList<GoalsViewState.ItemViewGoal> = arrayListOf()

    private val allGoals: ArrayList<GoalsViewState.ItemViewGoal> = arrayListOf()

    fun associateWithGoal(goalId: Long) {
        GlobalScope.launch(Dispatchers.IO) {
            Session.user?.id?.let {
                repository.associateGoal(
                    idGoal = goalId, idFuncio = it
                )
            }
        }
    }

    fun requireGoals() {
        GlobalScope.launch(Dispatchers.IO) {
            (repository.requestAllGoals() as? GoalsRepository.GoalsResult.Success)?.let { success ->
                allGoals.clear()
                success.goals.forEach { it ->
                    if (it.empresa?.cnpj == Session.user?.empresa?.cnpj) {
                        allGoals.add(it.mapTo(true))
                    }
                }
            }

            (repository.requestMyGoals() as? GoalsRepository.GoalsResult.Success)?.let { success ->
                myGoals.clear()
                success.goals.forEach { it ->
                    if (it.empresa?.cnpj == Session.user?.empresa?.cnpj) {
                        myGoals.add(it.mapTo(false))
                    }
                }
            }

            viewState.goals.postValue(allGoals)
            viewState.myGoals.postValue(myGoals)
        }
    }

    private fun Session.Meta.mapTo(canBeAssociate: Boolean): GoalsViewState.ItemViewGoal =
        GoalsViewState.ItemViewGoal(
            id,
            descricao,
            data,
            dificuldade,
            status,
            canBeAssociate,
            !canBeAssociate,
            status.contains("Feito")
        )

}