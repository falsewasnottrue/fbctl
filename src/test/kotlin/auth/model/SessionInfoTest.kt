package de.falsewasnottrue.auth.model

import kotlin.test.Test

class SessionInfoTest {

    @Test
    fun `parse correct data`() {
        val validXml = """
    <SessionInfo>
        <SID>0000000000000000</SID>
        <Challenge>2${'$'}60000${'$'}d500ea032311a8c2958147c1eee8ca4b${'$'}6000${'$'}598e0b36edb7c44a37f5433d7ae8a24d</Challenge>
        <BlockTime>0</BlockTime>
        <Rights/>
        <Users>
        <User last="1">fritz3725</User>
        </Users>
    </SessionInfo>
""".trimIndent()

        val sessionInfo = SessionInfo.parse(validXml);

        assert(sessionInfo != null) { "SessionInfo should not be null" }
        assert(sessionInfo?.sid == "0000000000000000") { "SID should be '0000000000000000'" }
        assert(sessionInfo?.challenge == "2\$60000\$d500ea032311a8c2958147c1eee8ca4b\$6000\$598e0b36edb7c44a37f5433d7ae8a24d") {
            "Challenge should match the expected value"
        }
    }

}

