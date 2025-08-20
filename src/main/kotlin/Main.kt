package de.falsewasnottrue

import de.falsewasnottrue.auth.Auth
import de.falsewasnottrue.auth.CheckSessionValid
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

    val result = Login().execute(null)
    println("Login Result: ${result.message} (Success: ${result.success})")
    val sid = result.data?.get("sid") ?: "No session ID found in result data."
    println("SID: $sid")

    val result2 = CheckSessionValid().execute(mapOf("sid" to sid))
    println("Check Result: ${result2.message} (Success: ${result.success})")
}
