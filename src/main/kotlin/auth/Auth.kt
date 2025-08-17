package de.falsewasnottrue.auth

import de.falsewasnottrue.CommandGroup

class Auth : CommandGroup(
    name = "auth",
    description = "Authentication commands for managing user accounts and sessions.",
    commands = listOf(Login(), Logout())
) {
    override fun toString(): String {
        return "Auth(name='$name', description='$description', commands=$commands)"
    }
}
