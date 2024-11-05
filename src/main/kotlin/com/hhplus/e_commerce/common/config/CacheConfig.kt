package com.hhplus.e_commerce.common.config

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration


@Configuration
@EnableCaching
class CacheConfig {

    fun redisCacheManagerBuilderCustomizer(): RedisCacheManagerBuilderCustomizer {
        return RedisCacheManagerBuilderCustomizer { builder ->
            builder.withCacheConfiguration(
                BALANCE_CACHE,
                RedisCacheConfiguration.defaultCacheConfig()
                    .computePrefixWith{ "$it::"}
                    .entryTtl(Duration.ofHours(24))
                    .disableCachingNullValues()
                    .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                            StringRedisSerializer(),
                        ),
                    ).serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                            GenericJackson2JsonRedisSerializer(),
                        ),
                    )
            )

            builder.withCacheConfiguration(
                PRODUCT_META_CACHE,
                RedisCacheConfiguration.defaultCacheConfig()
                    .computePrefixWith{ "$it::"}
                    .entryTtl(Duration.ofHours(48))
                    .disableCachingNullValues()
                    .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                            StringRedisSerializer(),
                        )
                    ).serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                            GenericJackson2JsonRedisSerializer(),
                        ),
                    )
            )
        }
    }

    companion object {
        const val BALANCE_CACHE = "balanceCache"
        const val PRODUCT_META_CACHE = "productMetaCache"
    }
}