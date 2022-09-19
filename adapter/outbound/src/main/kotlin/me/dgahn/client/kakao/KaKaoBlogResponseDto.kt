package me.dgahn.client.kakao

import com.fasterxml.jackson.annotation.JsonProperty
import me.dgahn.client.BaseBlogResponseDto
import me.dgahn.client.BaseBlogResponseListDto

data class KaKaoBlogResponseDto(
    @JsonProperty("blogname")
    override val blogName: String,
    @JsonProperty("contents")
    override val contents: String,
    @JsonProperty("datetime")
    override val dateTime: String,
    @JsonProperty("thumbnail")
    override val thumbnail: String,
    @JsonProperty("title")
    override val title: String,
    @JsonProperty("url")
    override val url: String,
) : BaseBlogResponseDto

data class KakaoBlogResponseListDto(
    @JsonProperty("documents")
    override val documents: List<KaKaoBlogResponseDto>
) : BaseBlogResponseListDto {
    companion object {
        fun KakaoBlogResponseListDto?.orEmpty(): KakaoBlogResponseListDto = if (this?.documents?.isNotEmpty() == true) {
            this
        } else {
            KakaoBlogResponseListDto(emptyList())
        }
    }
}
