package de.falsewasnottrue.auth.model

import kotlin.test.Test

class ChallengeTest {

    @Test
    fun `parse correct challenge`() {
        val validChallenge = "2\$60000\$d500ea032311a8c2958147c1eee8ca4b\$6000\$598e0b36edb7c44a37f5433d7ae8a24d"
        val challenge = Challenge.parse(validChallenge)

        assert(challenge != null) { "Challenge should not be null" }
        assert(challenge?.version == "2") { "Version should be '2'" }
        assert(challenge?.iter1 == "60000") { "Iter1 should be '60000'" }
        assert(challenge?.salt1 == "d500ea032311a8c2958147c1eee8ca4b") {
            "Salt1 should be 'd500ea032311a8c2958147c1eee8ca4b'"
        }
        assert(challenge?.iter2 == "6000") { "Iter2 should be '6000'" }
        assert(challenge?.salt2 == "598e0b36edb7c44a37f5433d7ae8a24d") {
            "Salt2 should be '598e0b36edb7c44a37f5433d7ae8a24d'"
        }
    }
}
