package de.falsewasnottrue

abstract class CommandGroup(
    val name: String,
    val description: String,
    val commands: List<Command>
) {
    override fun toString(): String {
        return "CommandGroup(name='$name', description='$description', commands=$commands)"
    }
}
