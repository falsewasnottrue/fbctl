package de.falsewasnottrue.auth.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.dataformat.xml.XmlMapper

@JsonIgnoreProperties(ignoreUnknown = true)
data class SessionInfo(
    @JsonProperty("SID") var sid: String? = null,
    @JsonProperty("Challenge") var challenge: String? = null
) {
    companion object {
        fun parse(validXml: String): SessionInfo? {
            val xmlMapper = XmlMapper()
            return try {
                xmlMapper.readValue(validXml, SessionInfo::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}
