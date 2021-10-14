package br.com.fiap

object Session {
    var password = ""
    var funcional = ""
    var user: User? = null

    data class User(
        val id: Long?,
        val nome: String?,
        val email: String?,
        val cargo: String?,
        val metas: List<Meta>?,
        val empresa: Empresa?
    )

    data class Meta(
        val descricao: String,
        val data: String,
        val dificuldade: String,
        val status: String,
        val empresa: Empresa?
    )

    data class Status(val status: String)

    data class Empresa(val nome: String?, val cnpj: String)
}