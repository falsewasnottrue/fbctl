package de.falsewasnottrue

import de.falsewasnottrue.auth.Auth
import de.falsewasnottrue.auth.CheckSessionValid
import de.falsewasnottrue.auth.Login
import de.falsewasnottrue.auth.Logout

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

    val result1 = Login().execute(null)
    println("Login Result: ${result1.message} (Success: ${result1.success})")

    val sid = result1.data?.get("sid") ?: "No session ID found in result data."
    println("SID: $sid")

    val result2 = CheckSessionValid().execute(mapOf("sid" to sid))
    println("Check Result: ${result2.message} (Success: ${result2.success})")

    val result3 = Logout().execute(mapOf("sid" to sid))
    println("Logout Result: ${result3.message} (Success: ${result3.success})")

    val result4 = CheckSessionValid().execute(mapOf("sid" to sid))
    println("Check Result: ${result4.message} (Success: ${result4.success})")

}
