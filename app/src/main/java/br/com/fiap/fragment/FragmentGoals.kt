package br.com.fiap.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fiap.GoalAdapter
import br.com.fiap.GoalsViewModel
import br.com.fiap.MainActivity
import br.com.fiap.NavigationBar
import br.com.fiap.databinding.FragmentGoalsBinding

class FragmentGoals : Fragment() {

    lateinit var binding: FragmentGoalsBinding
    lateinit var viewModel: GoalsViewModel
    private val navigationBar by lazy { (activity as? NavigationBar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as? MainActivity)?.navigationBar?.visibility = View.VISIBLE
        viewModel = GoalsViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentGoalsBinding.inflate(inflater).also {
        binding = it
        binding.viewState = viewModel.viewState
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvGoals.layoutManager =
            LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvByGoals.layoutManager =
            LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.viewState.goals.observe(viewLifecycleOwner) {
            binding.rvGoals.adapter = GoalAdapter(goals = it, addGoalAction = { position ->
                viewModel.associateWithGoal(it[position].id)
            })
        }

        viewModel.viewState.myGoals.observe(viewLifecycleOwner) {
            binding.rvByGoals.adapter = GoalAdapter(goals = it, addGoalAction = { position ->
                viewModel.associateWithGoal(it[position].id)
            }, terminateAction = { position ->
                viewModel.terminateGoal(it[position].id)
            })
        }

        binding.rvByGoals.visibility = View.VISIBLE
        binding.rvGoals.visibility = View.GONE

        navigationBar?.onItemChangeListener = {
            viewModel.requireGoals()
            if (navigationBar?.isMyGoalsSelected == true) {
                binding.rvByGoals.visibility = View.VISIBLE
                binding.rvGoals.visibility = View.GONE
            } else {
                binding.rvGoals.visibility = View.VISIBLE
                binding.rvByGoals.visibility = View.GONE

            }
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.requireGoals()
    }

}