package de.falsewasnottrue.auth.model

import javax.crypto.Mac
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.xor
import kotlin.text.get
import kotlin.text.set

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

    fun calculateResponse(password: String): String {
        // Placeholder for actual response calculation logic
        // This should implement the hashing and response generation based on the challenge and password

        val hash1 = pbkdf2HmacSha256(password.toByteArray(), hex2ByteArray(salt1!!), iter1!!.toInt())
        val hash2 = pbkdf2HmacSha256(hash1, hex2ByteArray(salt2!!), iter2!!.toInt())

        return salt2!! + "$" + byteArray2Hex(hash2)
    }

    fun hex2ByteArray(hex: String): ByteArray {
        val len = hex.length
        val data = ByteArray(len / 2)
        for (i in 0 until len step 2) {
            data[i / 2] = ((Character.digit(hex[i], 16) shl 4) + Character.digit(hex[i + 1], 16)).toByte()
        }
        return data
    }

    fun byteArray2Hex(data: ByteArray): String {
        val sb = StringBuilder(data.size * 2)
        for (byte in data) {
            sb.append(String.format("%02x", byte))
        }
        return sb.toString()
    }

    fun pbkdf2HmacSha256(password: ByteArray, salt: ByteArray, iters: Int): ByteArray {
        try {
            val alg = "HmacSHA256"
            val sha256mac: Mac = Mac.getInstance(alg)
            sha256mac.init(SecretKeySpec(password, alg))

            val ret = ByteArray(sha256mac.macLength)
            var tmp = ByteArray(salt.size + 4)

            System.arraycopy(salt, 0, tmp, 0, salt.size)
            tmp[salt.size + 3] = 1

            for (i in 0 until iters) {
                tmp = sha256mac.doFinal(tmp)
                for (k in ret.indices) {
                    ret[k] = ret[k].xor(tmp[k])
                }
            }
            return ret
        } catch (e: Exception) {
            e.printStackTrace()
            return ByteArray(0) // Return an empty array on error
        }
    }

//    try {
//            ; ; ; ; ; for (int i = 0; i < iters; i++) { tmp = sha256mac.doFinal(tmp); for (int k = 0; k < ret.length; k++) { ret[k] ^= tmp[k]; } } return ret; } catch (NoSuchAlgorithmException | InvalidKeyException e
//    }) { return null; // TODO: Handle this properly } }
}
