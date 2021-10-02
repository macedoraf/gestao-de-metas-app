package br.com.fiap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), AppRouter {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        navigateToLogin()
    }

    override fun navigateToLogin() {
        navigate(LoginFragment(), "login")
    }

    override fun navigateToGoals() {
        navigate(FragmentGoals(), "goals")
    }

    private fun navigate(fragment: Fragment, name: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_main_container, fragment)
            .addToBackStack(name)
            .commit()
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }
}