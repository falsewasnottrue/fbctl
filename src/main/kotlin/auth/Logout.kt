package de.falsewasnottrue.auth

import de.falsewasnottrue.Command
import de.falsewasnottrue.OperationOutcome
import de.falsewasnottrue.WebAPI
import de.falsewasnottrue.auth.model.SessionInfo

class LogoutSuccesful(
    operationName: String = "logout",
    success: Boolean = true,
    message: String? = "Logout was successful.",
    data: Map<String, String>? = null
) : OperationOutcome(operationName, success, message, data) {
    override fun toString(): String {
        return "LogoutSuccesful(operationName='$operationName', success=$success, message=$message, data=$data)"
    }
}

class LogoutFailed(
    operationName: String = "logout",
    success: Boolean = false,
    message: String? = "Logout failed.",
    data: Map<String, String>? = null
) : OperationOutcome(operationName, success, message, data) {
    override fun toString(): String {
        return "LogoutFailed(operationName='$operationName', success=$success, message=$message, data=$data)"
    }
}

class Logout : Command(
    name = "logout",
    description = "Log out of your account.",
    parameters = listOf(),
    example = "logout",
    group = "auth",
    aliases = listOf("signin", "sign-in")
) {
    val serverUrl = "http://fritz.box/login_sid.lua?version=2"

    override fun toString(): String {
        return "Logout(name='$name', description='$description', parameters=$parameters, example='$example', group='$group', aliases=$aliases)"
    }

    override fun execute(params: Map<String, String>?): OperationOutcome {
        val sid = params?.get("sid") ?: return LogoutFailed()
        val sessionInfoString = WebAPI.post(serverUrl, mapOf("logout" to "true", "sid" to sid))

        // get SessionID from server
        val newSid = SessionInfo.parse(sessionInfoString)?.sid!!
        if ("0000000000000000" != newSid) {
            return LogoutFailed()
        }

        return LogoutSuccesful()
    }
}

