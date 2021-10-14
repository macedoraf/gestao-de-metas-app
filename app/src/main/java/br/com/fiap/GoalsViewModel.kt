package br.com.fiap

import androidx.lifecycle.ViewModel
import br.com.fiap.repository.GoalsRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GoalsViewModel : ViewModel() {

    val viewState = GoalsViewState()

    private val repository = GoalsRepository

    fun requireGoals(isMyGoals: Boolean) {
        if (isMyGoals) {
            Session.user?.metas?.let { list ->
                val goals = list.map {
                    GoalsViewState.ItemViewGoal(it.descricao, it.data, it.dificuldade, it.status)
                }

                viewState.goals.value = goals
            }
        } else {
            GlobalScope.launch {
                val result = repository.requestAllGoals()

                if (result is GoalsRepository.GoalsResult.Success) {
                    val goals =
                        result.goals.filter { it.empresa?.cnpj == Session.user?.empresa?.cnpj }
                            .map {
                                GoalsViewState.ItemViewGoal(
                                    it.descricao,
                                    it.data,
                                    it.dificuldade,
                                    it.status
                                )
                            }
                    viewState.goals.postValue(goals)
                }
            }
        }
    }
}