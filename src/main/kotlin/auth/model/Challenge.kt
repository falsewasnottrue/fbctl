package de.falsewasnottrue.auth.model

data class Challenge(
    val version: String? = null,
    val iter1: String? = null,
    val salt1: String? = null,
    val iter2: String? = null,
    val salt2: String? = null
) {

    companion object {
        fun parse(challenge: String): Challenge? {
            // Example parsing logic, adjust as needed
            val parts = challenge.split("\$").map { it.trim() }
            return if (parts.size == 5) {
                Challenge(parts[0], parts[1], parts[2], parts[3], parts[4])
            } else {
                null
            }
        }
    }
}
