package de.falsewasnottrue

abstract class OperationOutcome(
    val operationName: String,
    val success: Boolean,
    val message: String? = null,
    val data: Map<String, String>? = null
) {
    override fun toString(): String {
        return "OperationOutcome(success=$success, message=$message, data=$data)"
    }
}
