package de.falsewasnottrue.auth

import de.falsewasnottrue.Command

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

    override fun execute() {
        TODO("Not yet implemented")
    }
}

