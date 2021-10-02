package br.com.fiap

import androidx.lifecycle.ViewModel

class GoalsViewModel : ViewModel() {

    val viewState = GoalsViewState()

    fun updateGoals() {
        Session.user?.metas?.let { list ->
            val goals = list.map {
                GoalsViewState.ItemViewGoal(it.descricao, it.data, it.dificuldade, it.status)
            }

            viewState.goals.value = goals
        }

    }
}