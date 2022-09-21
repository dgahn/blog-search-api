package me.dgahn.client

import me.dgahn.search.blog.BlogDomain

interface BaseBlogResponseDto {
    val blogName: String?
    val contents: String
    val dateTime: String
    val thumbnail: String?
    val title: String
    val url: String

    fun toDomain(): BlogDomain {
        return BlogDomain(
            blogName = blogName.orEmpty(),
            contents = contents,
            dateTime = dateTime,
            thumbnail = thumbnail.orEmpty(),
            title = title,
            url = url,
        )
    }
}

interface BaseBlogResponseListDto {
    val documents: List<BaseBlogResponseDto>

    fun toDomain(): List<BlogDomain> = this.documents.map { it.toDomain() }
}
