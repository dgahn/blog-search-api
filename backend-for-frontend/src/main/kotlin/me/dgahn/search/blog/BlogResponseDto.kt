package me.dgahn.search.blog

data class BlogResponseDto(
    val blogName: String,
    val contents: String,
    val dateTime: String,
    val thumbnail: String,
    val title: String,
    val url: String,
)

fun BlogDomain.toDto(): BlogResponseDto = BlogResponseDto(
    blogName = blogName,
    contents = contents,
    dateTime = dateTime,
    thumbnail = thumbnail,
    title = title,
    url = url,
)

fun List<BlogDomain>.toDto(): List<BlogResponseDto> = this.map { it.toDto() }
