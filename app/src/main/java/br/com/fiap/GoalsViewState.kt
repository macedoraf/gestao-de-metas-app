package br.com.fiap

import androidx.lifecycle.MutableLiveData

class GoalsViewState {

    val goals: MutableLiveData<List<ItemViewGoal>> = MutableLiveData()
    val myGoals: MutableLiveData<List<ItemViewGoal>> = MutableLiveData()

    data class ItemViewGoal(
        val id: Long,
        val description: String,
        val date: String,
        val difficulty: String,
        val status: String,
        val canBeAssociate: Boolean,
        val canBeDone: Boolean,
        val isDone: Boolean
    )
}