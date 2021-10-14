package br.com.fiap

import br.com.fiap.databinding.LayoutTemGoalBinding

class GoalAdapter(
    override val layoutResource: Int = R.layout.layout_tem_goal,
    goals: List<GoalsViewState.ItemViewGoal>,
    private val addGoalAction: (position: Int) -> Unit = {},
    private val terminateAction: (position: Int) -> Unit = {}
) : DataBindingAdapter(goals) {

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        (holder.binding as? LayoutTemGoalBinding)?.btGetGoal?.setOnClickListener {
            addGoalAction.invoke(position)
        }

        (holder.binding as? LayoutTemGoalBinding)?.btDone?.setOnClickListener {
            terminateAction.invoke(position)
        }
    }
}