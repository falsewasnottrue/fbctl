package de.falsewasnottrue.auth

import de.falsewasnottrue.Command
import de.falsewasnottrue.OperationOutcome
import de.falsewasnottrue.WebAPI
import de.falsewasnottrue.auth.model.SessionInfo

class SessionInvalid : OperationOutcome(
    operationName = "check-session-valid",
    success = false,
    message = "Session is invalid or expired.",
    data = null
)

class SessionValid(sid: String) : OperationOutcome(
    operationName = "check-session-valid",
    success = true,
    message = "Session is valid.",
    data = mapOf("sid" to sid)
)

class CheckSessionValid : Command(
    name = "check-session-valid",
    description = "Check if the current session is valid.",
    parameters = listOf(),
    example = "check-session-valid",
    group = "auth",
    aliases = listOf("session-check", "validate-session")
) {
    override fun execute(params: Map<String, String>?): OperationOutcome {
        val sid = params?.get("sid") ?: return SessionInvalid()

        val serverUrl = "http://fritz.box/login_sid.lua?version=2"
        val sessionInfoString2 = WebAPI.post(serverUrl, mapOf("sid" to sid))

        // get SessionID from server
        val newSid = SessionInfo.parse(sessionInfoString2)?.sid!!
        if ("0000000000000000" == newSid) {
            return SessionInvalid()
        }

        return SessionValid(newSid)
    }
}
