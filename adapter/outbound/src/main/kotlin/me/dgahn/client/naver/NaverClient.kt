package me.dgahn.client.naver

import me.dgahn.client.BaseBlogResponseListDto
import me.dgahn.client.BlogClient
import me.dgahn.client.OutBoundSearchBlogCondition
import me.dgahn.client.naver.NaverBlogResponseListDto.Companion.orEmpty
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class NaverClient(
    private val restTemplate: RestTemplate,
    private val naverProperties: NaverProperties
) : BlogClient {
    private val headers = HttpHeaders().apply {
        add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        add(naverProperties.clientIdKey, naverProperties.clientIdValue)
        add(naverProperties.clientSecretKey, naverProperties.clientSecretValue)
    }

    // ToDo 예외 처리 추가 필요.
    override fun search(outBoundSearchBlogCondition: OutBoundSearchBlogCondition): BaseBlogResponseListDto {
        val url = createURL(outBoundSearchBlogCondition)
        val entity: HttpEntity<String> = HttpEntity(headers)

        return restTemplate.exchange(url, HttpMethod.GET, entity, NaverBlogResponseListDto::class.java).body.orEmpty()
    }

    private fun createURL(outBoundSearchBlogCondition: OutBoundSearchBlogCondition): String =
        UriComponentsBuilder.fromHttpUrl(naverProperties.url)
            .queryParam(outBoundSearchBlogCondition::query.name, outBoundSearchBlogCondition.query)
            .queryParam(outBoundSearchBlogCondition::start.name, outBoundSearchBlogCondition.start)
            .queryParam(outBoundSearchBlogCondition::display.name, outBoundSearchBlogCondition.display)
            .queryParam(outBoundSearchBlogCondition::sort.name, outBoundSearchBlogCondition.sort.naver)
            .toUriString()
}
