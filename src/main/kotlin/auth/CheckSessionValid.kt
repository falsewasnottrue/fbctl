package de.falsewasnottrue.auth

import de.falsewasnottrue.Command

class CheckSessionValid : Command(
    name = "check-session-valid",
    description = "Check if the current session is valid.",
    parameters = listOf(),
    example = "check-session-valid",
    group = "auth",
    aliases = listOf("session-check", "validate-session")
) {
    override fun execute() {

    }
}
