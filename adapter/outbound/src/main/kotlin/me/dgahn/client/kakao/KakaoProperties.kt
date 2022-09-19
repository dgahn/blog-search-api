package me.dgahn.client.kakao

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("kakao")
class KakaoProperties {
    var url: String = ""
    var keyPrefix: String = ""
    var apiKey: String = ""
}
