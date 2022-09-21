package me.dgahn.client.naver

import com.fasterxml.jackson.annotation.JsonProperty
import me.dgahn.client.BaseBlogResponseDto
import me.dgahn.client.BaseBlogResponseListDto

data class NaverBlogResponseDto(
    @JsonProperty("title")
    override val title: String,
    @JsonProperty("link")
    override val url: String,
    @JsonProperty("description")
    override val contents: String,
    @JsonProperty("postdate")
    override val dateTime: String,

    // 네이버에서는 보내지 않는 값
    @JsonProperty("blogName")
    override val blogName: String?,
    @JsonProperty("thumbnail")
    override val thumbnail: String?,

    // 네이버에서 보내는 값 중 필요 없는 값
    @JsonProperty("bloggername")
    val bloggerName: String,
    @JsonProperty("bloggerlink")
    val bloggerLink: String,
) : BaseBlogResponseDto

data class NaverBlogResponseListDto(
    @JsonProperty("lastBuildDate")
    val lastBuildDate: String,
    @JsonProperty("total")
    val total: Long,
    @JsonProperty("start")
    val start: Long,
    @JsonProperty("display")
    val display: Int,
    @JsonProperty("items")
    override val documents: List<NaverBlogResponseDto>
) : BaseBlogResponseListDto {
    companion object {
        fun NaverBlogResponseListDto?.orEmpty(): NaverBlogResponseListDto = if (this?.documents?.isNotEmpty() == true) {
            this
        } else {
            NaverBlogResponseListDto(
                lastBuildDate = "",
                total = 0L,
                start = 0L,
                display = 0,
                documents = emptyList()
            )
        }
    }
}
