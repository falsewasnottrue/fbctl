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

    @Test
    fun `calculate response with correct password`() {
        val validChallenge = "2\$60000\$d500ea032311a8c2958147c1eee8ca4b\$6000\$598e0b36edb7c44a37f5433d7ae8a24d"
        val challenge = Challenge.parse(validChallenge)!!

        val response = challenge.calculateResponse("password")
        assert(response.isNotEmpty()) { "Response should not be empty" }
        assert(response == "598e0b36edb7c44a37f5433d7ae8a24d\$c1d9f520591d3c1fea044c492676314b55341d3cee5124ce424904dfcc728c91") {
            "Response should match the expected value"
        }
    }

    @Test
    fun `calculate response with correct password with example values`() {
        val validChallenge = "2\$10000\$5A1711\$2000\$5A1722"
        val challenge = Challenge.parse(validChallenge)!!

        val response = challenge.calculateResponse("1example!")
        assert(response.isNotEmpty()) { "Response should not be empty" }

        assert(response == "5A1722\$1798a1672bca7c6463d6b245f82b53703b0f50813401b03e4045a5861e689adb")
        { "Response should match the expected value" }
    }
}
