package br.com.fiap

class GoalAdapter(
    override val layoutResource: Int = R.layout.layout_tem_goal,
    goals: List<GoalsViewState.ItemViewGoal>
) : DataBindingAdapter(goals) {
}