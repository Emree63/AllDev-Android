package fr.iut.alldev.allin.data.api

import fr.iut.alldev.allin.data.api.interceptors.AllInAPIException
import fr.iut.alldev.allin.data.api.model.CheckUser
import fr.iut.alldev.allin.data.api.model.RequestBet
import fr.iut.alldev.allin.data.api.model.RequestParticipation
import fr.iut.alldev.allin.data.api.model.RequestUser
import fr.iut.alldev.allin.data.api.model.ResponseBet
import fr.iut.alldev.allin.data.api.model.ResponseBetAnswerDetail
import fr.iut.alldev.allin.data.api.model.ResponseBetDetail
import fr.iut.alldev.allin.data.api.model.ResponseParticipation
import fr.iut.alldev.allin.data.api.model.ResponseUser
import fr.iut.alldev.allin.data.model.bet.NO_VALUE
import fr.iut.alldev.allin.data.model.bet.YES_VALUE
import java.time.ZonedDateTime
import java.util.UUID

class MockAllInApi : AllInApi {

    private fun getUserFromToken(token: String) =
        mockUsers.find { it.first.token == token }

    private fun getAnswerDetails(bet: ResponseBet, participations: List<ResponseParticipation>): List<ResponseBetAnswerDetail> {
        return bet.response.map { response ->
            val responseParticipations = participations.filter { it.answer == response }
            ResponseBetAnswerDetail(
                response = response,
                totalStakes = responseParticipations.sumOf { it.stake },
                totalParticipants = responseParticipations.size,
                highestStake = responseParticipations.maxOfOrNull { it.stake } ?: 0,
                odds = if (participations.isEmpty()) 0.0f else responseParticipations.size / participations.size.toFloat()
            )
        }
    }

    override suspend fun login(body: CheckUser): ResponseUser {
        return mockUsers.find { it.first.username == body.login && it.second == body.password }?.first
            ?: throw AllInAPIException("Invalid login/password.")
    }

    override suspend fun login(token: String): ResponseUser {
        return getUserFromToken(token)?.first
            ?: throw AllInAPIException("Invalid token")
    }

    override suspend fun register(body: RequestUser): ResponseUser {
        val response = ResponseUser(
            id = UUID.randomUUID().toString(),
            username = body.username,
            email = body.email,
            nbCoins = 500,
            token = "${body.username} ${mockUsers.size}"
        ) to body.password
        mockUsers.add(response)
        return response.first
    }

    override suspend fun createBet(token: String, body: RequestBet) {
        mockBets.add(
            ResponseBet(
                id = UUID.randomUUID().toString(),
                theme = body.theme,
                sentenceBet = body.sentenceBet,
                endRegistration = body.endRegistration,
                endBet = body.endBet,
                isPrivate = body.isPrivate,
                response = body.response,
                createdBy = ""
            )
        )
    }

    override suspend fun getAllBets(): List<ResponseBet> = mockBets.toList()
    override suspend fun getBet(token: String, id: String): ResponseBetDetail {
        val bet = mockBets.find { it.id == id } ?: throw AllInAPIException("Bet not found")
        val user = getUserFromToken(token) ?: throw AllInAPIException("Invalid login/password.")
        val betParticipations = mockParticipations.filter { it.betId == bet.id }
        val userParticipation = betParticipations.find { it.username == user.first.username }

        return ResponseBetDetail(
            bet = bet,
            answers = getAnswerDetails(bet, betParticipations),
            participations = betParticipations,
            userParticipation = userParticipation
        )
    }

    override suspend fun participateToBet(token: String, body: RequestParticipation) {
        getUserFromToken(token)?.let {
            mockParticipations.add(
                ResponseParticipation(
                    id = "",
                    betId = body.betId,
                    username = it.first.username,
                    answer = body.answer,
                    stake = body.stake

                )
            )
        } ?: throw AllInAPIException("Invalid token")
    }
}

private val mockUsers = mutableListOf(
    ResponseUser(
        id = "UUID 1",
        username = "User 1",
        email = "john@doe.fr",
        nbCoins = 250,
        token = "token 1"
    ) to "12345",
    ResponseUser(
        id = "UUID 2",
        username = "User 2",
        email = "john@doe.fr",
        nbCoins = 250,
        token = "token 2"
    ) to "12345",
    ResponseUser(
        id = "UUID 3",
        username = "User 3",
        email = "john@doe.fr",
        nbCoins = 250,
        token = "token 3"
    ) to "12345",
    ResponseUser(
        id = "UUID 4",
        username = "User 4",
        email = "john@doe.fr",
        nbCoins = 250,
        token = "token 4"
    ) to "12345",
    ResponseUser(
        id = "UUID 5",
        username = "User 5",
        email = "john@doe.fr",
        nbCoins = 250,
        token = "token 5"
    ) to "12345",
    ResponseUser(
        id = "UUID 6",
        username = "User 6",
        email = "john@doe.fr",
        nbCoins = 250,
        token = "token 6"
    ) to "12345",
    ResponseUser(
        id = "UUID 7",
        username = "User 7",
        email = "john@doe.fr",
        nbCoins = 250,
        token = "token 7"
    ) to "12345"
)

private val mockParticipations = mutableListOf(
    ResponseParticipation(
        id = "",
        betId = "UUID1",
        username = mockUsers[0].first.username,
        answer = YES_VALUE,
        stake = 200
    ),
    ResponseParticipation(
        id = "",
        betId = "UUID1",
        username = mockUsers[1].first.username,
        answer = NO_VALUE,
        stake = 1500
    ),
    ResponseParticipation(
        id = "",
        betId = "UUID1",
        username = mockUsers[2].first.username,
        answer = YES_VALUE,
        stake = 300
    ),
    ResponseParticipation(
        id = "",
        betId = "UUID1",
        username = mockUsers[3].first.username,
        answer = YES_VALUE,
        stake = 25
    ),
    ResponseParticipation(
        id = "",
        betId = "UUID1",
        username = mockUsers[4].first.username,
        answer = NO_VALUE,
        stake = 222
    ),
    ResponseParticipation(
        id = "",
        betId = "UUID1",
        username = mockUsers[5].first.username,
        answer = NO_VALUE,
        stake = 222
    ),
    ResponseParticipation(
        id = "",
        betId = "UUID1",
        username = mockUsers[6].first.username,
        answer = NO_VALUE,
        stake = 222
    )
)

private val mockBets = mutableListOf(
    ResponseBet(
        id = "UUID1",
        theme = "Études",
        sentenceBet = "Dave va arriver en retard demain matin ?",
        endRegistration = ZonedDateTime.now().plusDays(3),
        endBet = ZonedDateTime.now().plusDays(4),
        isPrivate = false,
        response = listOf(YES_VALUE, NO_VALUE),
        createdBy = "Armure"
    ),
    ResponseBet(
        id = "UUID2",
        theme = "Études",
        sentenceBet = "Dave va arriver en retard demain matin ?",
        endRegistration = ZonedDateTime.now().plusDays(3),
        endBet = ZonedDateTime.now().plusDays(4),
        isPrivate = false,
        response = listOf("Answer 1", "Answer 2", "Answer 3", "Answer 4"),
        createdBy = "User 2"
    ),
    ResponseBet(
        id = "UUID3",
        theme = "Sport",
        sentenceBet = "Nouveau record du monde ?",
        endRegistration = ZonedDateTime.now().plusDays(3),
        endBet = ZonedDateTime.now().plusDays(4),
        isPrivate = false,
        response = listOf(YES_VALUE, NO_VALUE),
        createdBy = "Armure"
    )
)