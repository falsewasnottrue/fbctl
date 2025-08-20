package de.falsewasnottrue

import de.falsewasnottrue.auth.Auth
import de.falsewasnottrue.auth.Login

fun main() {
    val commandGroups = listOf<CommandGroup>(
        Auth()
    )

    commandGroups.forEach { group ->
        println("Group: ${group.name} - ${group.description}")
        group.commands.forEach { command ->
            println("  Command: ${command.name} - ${command.description}")
            println("    Parameters: ${command.parameters.joinToString(", ")}")
            println("    Example: ${command.example}")
            if (command.aliases.isNotEmpty()) {
                println("    Aliases: ${command.aliases.joinToString(", ")}")
            }
        }
    }

    Login().execute();
}
