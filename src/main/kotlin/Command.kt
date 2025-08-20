package de.falsewasnottrue

abstract class Command(
    val name: String,
    val description: String,
    val parameters: List<String>,
    val example: String,
    val group: String,
    val aliases: List<String> = emptyList()
) {
    override fun toString(): String {
        return "Command(name='$name', description='$description', parameters=$parameters, example='$example', group='$group', aliases=$aliases)"
    }

    abstract fun execute(params: Map<String, String>?): OperationOutcome
}
