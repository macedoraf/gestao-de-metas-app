package br.com.fiap.repository

import android.util.Log
import br.com.fiap.Provider
import br.com.fiap.Session
import br.com.fiap.Session.Meta
import br.com.fiap.URLs
import com.google.gson.GsonBuilder
import okhttp3.Request

object GoalsRepository {

    fun requestAllGoals(): GoalsResult {
        val request = Request.Builder()
            .url(URLs.GOALS)
            .get().build()

        val response = Provider.request(request)
        if (response.code == 200) {
            response.body?.string()?.let {
                val metas = GsonBuilder().create().fromJson(it, Array<Meta>::class.java)
                Log.d("ResponseGoals", it)
                return GoalsResult.Success(metas.toList())
            }
        }
        return GoalsResult.Error
    }

    sealed class GoalsResult {
        data class Success(val goals: List<Meta>) : GoalsResult()
        object Error : GoalsResult()
    }
}