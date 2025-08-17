package de.falsewasnottrue.auth

import de.falsewasnottrue.Command

class Login : Command(
    name = "login",
    description = "Log in to your account.",
    parameters = listOf("username", "password"),
    example = "login username password",
    group = "auth",
    aliases = listOf("signin", "sign-in")
) {
    override fun toString(): String {
        return "Login(name='$name', description='$description', parameters=$parameters, example='$example', group='$group', aliases=$aliases)"
    }
}
