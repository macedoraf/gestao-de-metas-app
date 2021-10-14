package br.com.fiap.repository

import android.util.Log
import br.com.fiap.Provider
import br.com.fiap.Session
import br.com.fiap.Session.Meta
import br.com.fiap.URLs
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

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

    fun requestMyGoals(): GoalsResult {
        val request = Request.Builder()
            .url(URLs.FUNCIONARIO)
            .get().build()

        val response = Provider.request(request)
        if (response.code == 200) {
            response.body?.string()?.let {
                val func = GsonBuilder().create().fromJson(it, Array<Session.User>::class.java)
                Log.d("ResponseGoals", it)
                val result = func.find { it.id == Session.user?.id }
                return GoalsResult.Success(result?.metas ?: listOf())
            }
        }
        return GoalsResult.Error
    }

    suspend fun associateGoal(idGoal: Long, idFuncio: Long): Boolean {
        val requestBody = JSONObject().apply {
            put("idGoal", "$idGoal")
            put("idFunc", idFuncio)
        }.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url(URLs.ASSOCIATE_GOAL)
            .post(requestBody).build()

        val response = Provider.request(request)
        if (response.code == 200) {
            LoginRepository.request(Session.funcional, Session.password)
            return true
        }
        return false
    }

    sealed class GoalsResult {
        data class Success(val goals: List<Meta>) : GoalsResult()
        object Error : GoalsResult()
    }
}