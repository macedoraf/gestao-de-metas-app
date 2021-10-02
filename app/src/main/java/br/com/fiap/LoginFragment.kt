package br.com.fiap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.fiap.databinding.FragmentLoginBinding

class LoginFragment : Fragment(), LoginView {

    lateinit var binding: FragmentLoginBinding
    lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = LoginViewModel(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoginBinding.inflate(inflater).also {
        binding = it
        binding.viewState = this.viewModel.viewState
        binding.lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setup()
    }

    private fun FragmentLoginBinding.setup() {
        btLogin.setOnClickListener {
            viewModel.login(
                etName.text.toString(),
                etPassword.text.toString()
            )
        }
    }

    override fun navigateToGoals() {
        (activity as? MainActivity)?.navigateToGoals()
    }
}