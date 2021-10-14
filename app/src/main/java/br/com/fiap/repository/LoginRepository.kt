package br.com.fiap.repository

import android.util.Log
import br.com.fiap.Provider
import br.com.fiap.Session
import br.com.fiap.URLs
import com.google.gson.GsonBuilder
import okhttp3.Request

object LoginRepository {

    suspend fun request(funcional: String, password: String): LoginResult {
        val request = Request.Builder()
            .url(URLs.LOGIN + "?funcional=$funcional&password=$password")
            .get().build()

        val response = Provider.request(request)
        if (response.code == 200) {
            response.body?.string()?.let {
                val user = GsonBuilder().create().fromJson(it, Session.User::class.java)
                Session.user = user
                Log.d("ResponseLogin", it)
            }
            return LoginResult.Logged
        }

        return LoginResult.Invalid
    }



    sealed class LoginResult {
        object Logged : LoginResult()
        object Invalid : LoginResult()
    }
}