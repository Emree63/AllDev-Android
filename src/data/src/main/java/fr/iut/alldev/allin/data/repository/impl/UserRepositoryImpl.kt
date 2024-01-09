package fr.iut.alldev.allin.data.repository.impl

import fr.iut.alldev.allin.data.api.AllInApi
import fr.iut.alldev.allin.data.api.model.CheckUser
import fr.iut.alldev.allin.data.api.model.RequestUser
import fr.iut.alldev.allin.data.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: AllInApi,
) : UserRepository() {

    override suspend fun login(username: String, password: String): String? {
        val response = api.login(
            CheckUser(
                login = username,
                password = password
            )
        )
        currentUser = response.toUser()
        return response.token
    }

    override suspend fun login(token: String): String? {
        val response = api.login(token = "Bearer $token")
        currentUser = response.toUser()
        return response.token
    }


    override suspend fun register(username: String, email: String, password: String): String? {
        val response = api.register(
            RequestUser(
                username = username,
                email = email,
                password = password,
                nbCoins = 0
            )
        )
        currentUser = response.toUser()
        return response.token
    }
}