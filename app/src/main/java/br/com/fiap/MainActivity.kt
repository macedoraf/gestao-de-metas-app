package br.com.fiap

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.fiap.fragment.FragmentGoals
import br.com.fiap.fragment.LoginFragment

class MainActivity : AppCompatActivity(), AppRouter, NavigationBar {

    val navigationBar by lazy { findViewById<LinearLayout>(R.id.navigation_bar) }

    val llMyGoals by lazy { findViewById<LinearLayout>(R.id.ll_my_goals) }

    val llAllGoals by lazy { findViewById<LinearLayout>(R.id.ll_all_goals) }

    override var isMyGoalsSelected: Boolean = true
    override var onItemChangeListener: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llMyGoals.setOnClickListener { changeSelected() }
        llAllGoals.setOnClickListener { changeSelected() }

        llMyGoals.setBackgroundColor(Color.CYAN)
        llAllGoals.setBackgroundColor(Color.WHITE)

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

    override fun changeSelected() {
        isMyGoalsSelected = !isMyGoalsSelected
        if (isMyGoalsSelected) {
            llMyGoals.isClickable = false
            llAllGoals.isClickable = true
            llMyGoals.setBackgroundColor(Color.CYAN)
            llAllGoals.setBackgroundColor(Color.WHITE)
            onItemChangeListener.invoke()
        } else {
            llMyGoals.isClickable = true
            llAllGoals.isClickable = false
            llMyGoals.setBackgroundColor(Color.WHITE)
            llAllGoals.setBackgroundColor(Color.CYAN)
            onItemChangeListener.invoke()
        }
    }

}


interface NavigationBar {
    var isMyGoalsSelected: Boolean
    var onItemChangeListener: () -> Unit

    fun changeSelected()
}