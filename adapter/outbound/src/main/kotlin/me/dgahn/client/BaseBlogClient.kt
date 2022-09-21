package me.dgahn.client

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Primary
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component

@Primary
@Component
class BaseBlogClient(
    @Qualifier("kaKaoClient")
    private val baseClient: BlogClient,
    @Qualifier("naverClient")
    private val fallbackClient: BlogClient
) : BlogClient {
    @Retryable(maxAttempts = 2, backoff = Backoff(delay = 100))
    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    override fun search(outBoundSearchBlogCondition: OutBoundSearchBlogCondition): BaseBlogResponseListDto {
        return try {
            baseClient.search(outBoundSearchBlogCondition)
        } catch (e: Exception) {
            logger.error { "Search failed and search as fallback." }
            fallbackClient.search(outBoundSearchBlogCondition)
        }
    }

    companion object {
        val logger = KotlinLogging.logger { }
    }
}
