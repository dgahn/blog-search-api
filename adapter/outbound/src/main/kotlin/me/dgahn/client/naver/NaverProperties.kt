package me.dgahn.client.naver

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("naver")
class NaverProperties {
    var url: String = ""
    var clientIdKey: String = ""
    var clientIdValue: String = ""
    var clientSecretKey: String = ""
    var clientSecretValue: String = ""
}
