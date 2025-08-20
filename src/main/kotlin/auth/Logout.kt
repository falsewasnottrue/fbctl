package de.falsewasnottrue.auth

import de.falsewasnottrue.Command
import de.falsewasnottrue.OperationOutcome

class Logout : Command(
    name = "logout",
    description = "Log out of your account.",
    parameters = listOf(),
    example = "logout",
    group = "auth",
    aliases = listOf("signin", "sign-in")
) {
    override fun toString(): String {
        return "Logout(name='$name', description='$description', parameters=$parameters, example='$example', group='$group', aliases=$aliases)"
    }

    override fun execute(params: Map<String, String>?): OperationOutcome {
        TODO("Not yet implemented")
    }
}

