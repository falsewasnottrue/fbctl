package de.falsewasnottrue.auth

import de.falsewasnottrue.Command
import de.falsewasnottrue.OperationOutcome
import de.falsewasnottrue.WebAPI
import de.falsewasnottrue.auth.model.SessionInfo
import de.falsewasnottrue.auth.model.Challenge
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class LoginFailed : OperationOutcome(
    operationName = "login",
    success = false,
    message = "Login failed. Please check your credentials and try again.",
    data = mapOf("error" to "Invalid credentials")
)

class LoginSuccesful(sid: String): OperationOutcome(
    operationName = "login",
    success = true,
    message = "Login successful.",
    data = mapOf("sid" to sid)
)

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

    override fun execute(params: Map<String, String>?): OperationOutcome {
        // get username and password from env variables
        val username: String = System.getenv("FB_USERNAME")!!
        val password: String = System.getenv("FB_PASSWORD")!!

        // get Challenge from server
        val serverUrl = "http://fritz.box/login_sid.lua?version=2"
        val sessionInfoString = WebAPI.get(serverUrl)
        val challengeString = SessionInfo.parse(sessionInfoString)?.challenge!!
        val challenge = Challenge.parse(challengeString)!!

        println("Challenge: $challenge")

        // calculate response
        val challengeResponse = challenge.calculateResponse(password)
        println("Challenge response: $challengeResponse")

        // post response to server
        val responseParams = mapOf("username" to username, "response" to challengeResponse)
        val sessionInfoString2 = WebAPI.post(serverUrl, responseParams)

        // get SessionID from server
        val sid = SessionInfo.parse(sessionInfoString2)?.sid!!
        if ("0000000000000000" == sid) {
            return LoginFailed()
        }

        return LoginSuccesful(sid)
    }
}
