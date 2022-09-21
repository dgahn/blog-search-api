package me.dgahn.client.kakao

import me.dgahn.client.BaseBlogResponseListDto
import me.dgahn.client.BlogClient
import me.dgahn.client.OutBoundSearchBlogCondition
import me.dgahn.client.kakao.KakaoBlogResponseListDto.Companion.orEmpty
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class KaKaoClient(
    private val restTemplate: RestTemplate,
    private val kakaoProperties: KakaoProperties
) : BlogClient {
    private val headers = HttpHeaders().apply {
        add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        set(HttpHeaders.AUTHORIZATION, "${kakaoProperties.keyPrefix} ${kakaoProperties.apiKey}")
    }

    @Retryable(maxAttempts = 2, backoff = Backoff(delay = 100))
    override fun search(outBoundSearchBlogCondition: OutBoundSearchBlogCondition): BaseBlogResponseListDto {
        val url = createURL(outBoundSearchBlogCondition)
        val entity: HttpEntity<String> = HttpEntity(headers)

        return restTemplate.exchange(url, HttpMethod.GET, entity, KakaoBlogResponseListDto::class.java).body.orEmpty()
    }

    private fun createURL(outBoundSearchBlogCondition: OutBoundSearchBlogCondition): String =
        UriComponentsBuilder.fromHttpUrl(kakaoProperties.url)
            .queryParam(outBoundSearchBlogCondition::query.name, outBoundSearchBlogCondition.query)
            .queryParam(outBoundSearchBlogCondition::page.name, outBoundSearchBlogCondition.page)
            .queryParam(outBoundSearchBlogCondition::size.name, outBoundSearchBlogCondition.size)
            .queryParam(outBoundSearchBlogCondition::sort.name, outBoundSearchBlogCondition.sort.kakao)
            .toUriString()
}
