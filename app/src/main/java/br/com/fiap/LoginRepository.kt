package br.com.fiap

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.Request
import org.json.JSONObject

object LoginRepository {

    suspend fun request(name: String, password: String): LoginResult {
        val request = Request.Builder()
            .url(URLs.LOGIN + "?name=$name&password=$password")
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