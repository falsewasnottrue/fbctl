package de.falsewasnottrue.auth

import de.falsewasnottrue.Command
import de.falsewasnottrue.auth.model.SessionInfo
import de.falsewasnottrue.auth.model.Challenge
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class Login : Command(
    name = "login",
    description = "Log in to your account.",
    parameters = listOf(),
    example = "login username password",
    group = "auth",
    aliases = listOf("signin", "sign-in")
) {
    override fun toString(): String {
        return "Login(name='$name', description='$description', parameters=$parameters, example='$example', group='$group', aliases=$aliases)"
    }

    override fun execute() {
        // get username and password from env variables
        val username: String = System.getenv("FB_USERNAME") ?: "un"
        val password: String = System.getenv("FB_PASSWORD")!!

        // TODO get Challenge from server
        val serverUrl = "http://fritz.box/login_sid.lua?version=2"
        val sessionInfoString = getResource(serverUrl)
        val challengeString = SessionInfo.parse(sessionInfoString)?.challenge!!
        val challenge = Challenge.parse(challengeString)!!

        println("Challenge: $challenge")

        // calculate response
        val challengeResponse = challenge.calculateResponse(password)
        println("Challenge response: $challengeResponse")

        // TODO post response to server
        val response2 = postResource(serverUrl, mapOf("username" to username, "response" to challengeResponse))
        println("Response2: $response2")

        // TODO get SessionID from server
        // TODO store SessionID in env variable
    }

    private fun getResource(url: String): String {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        return connection.inputStream.bufferedReader().use { it.readText() }
    }

    fun postResource(url: String, params: Map<String, String>): String {
        val body = params.entries.joinToString("&") {
            "${URLEncoder.encode(it.key, "UTF-8")}=${URLEncoder.encode(it.value, "UTF-8")}"
        }
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        OutputStreamWriter(connection.outputStream).use { it.write(body) }
        return connection.inputStream.bufferedReader().use { it.readText() }
    }
}
