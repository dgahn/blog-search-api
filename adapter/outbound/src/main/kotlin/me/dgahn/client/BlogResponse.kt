package me.dgahn.client

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class BlogResponse(
    @JsonProperty("blogname")
    val blogName: String,
    @JsonProperty("contents")
    val contents: String,
    @JsonProperty("datetime")
    val dateTime: LocalDateTime,
    @JsonProperty("thumbnail")
    val thumbnail: String,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("url")
    val url: String,
)

data class BlogListResponse(
    @JsonProperty("documents")
    val documents: List<BlogResponse>
) {
    companion object {
        fun BlogListResponse?.orEmpty(): BlogListResponse = if (this?.documents?.isNotEmpty() == true) {
            this
        } else {
            BlogListResponse(emptyList())
        }
    }
}