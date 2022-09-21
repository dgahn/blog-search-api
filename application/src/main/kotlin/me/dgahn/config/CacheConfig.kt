package me.dgahn.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableCaching
class CacheConfig {
    @Bean
    fun cacheManager(): CacheManager {
        val caches = CacheType.values().map { cache ->
            CaffeineCache(
                cache.cacheName,
                Caffeine.newBuilder().recordStats()
                    .expireAfterWrite(cache.expiredAfterWrite, TimeUnit.SECONDS)
                    .maximumSize(cache.maximumSize)
                    .build()
            )
        }
        return SimpleCacheManager().apply { setCaches(caches) }
    }
}
