package br.com.fiap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class GoalsViewState {

    val goals: MutableLiveData<List<ItemViewGoal>> = MutableLiveData()

    val hasGoals: LiveData<Boolean> = Transformations.map(goals) {
        it.isNotEmpty()
    }


    data class ItemViewGoal(
        val description: String,
        val date: String,
        val difficulty: String,
        val status: String
    )
}