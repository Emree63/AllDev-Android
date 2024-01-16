package fr.iut.alldev.allin.data.api.model

import androidx.annotation.Keep
import fr.iut.alldev.allin.data.model.User
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class RequestUser(
    val username: String,
    val email: String,
    val password: String,
    var nbCoins: Int,
)

@Keep
@Serializable
data class ResponseUser(
    val id: String,
    val username: String,
    val email: String,
    var nbCoins: Int,
    var token: String? = null,
) {
    fun toUser() = User(
        id = id,
        username = username,
        email = email,
        coins = nbCoins
    )
}

@Keep
@Serializable
data class CheckUser(
    val login: String,
    val password: String,
)