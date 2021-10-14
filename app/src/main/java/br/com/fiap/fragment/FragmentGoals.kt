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

        viewModel.viewState.goals.observe(viewLifecycleOwner) {
            binding.rvGoals.adapter = GoalAdapter(goals = it)
        }

        navigationBar?.onItemChangeListener =
            { viewModel.requireGoals(navigationBar?.isMyGoalsSelected ?: true) }
    }


    override fun onResume() {
        super.onResume()
        viewModel.requireGoals(navigationBar?.isMyGoalsSelected ?: true)
    }

}