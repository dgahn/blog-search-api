package me.dgahn.fixture

import me.dgahn.search.blog.BlogResponseDto

object BlogResponseDtoFixture {
    fun getBlogResponseDtos(): List<BlogResponseDto> = (1..10).map {
        BlogResponseDto(
            blogName = "blogName $it",
            contents = "contents $it",
            dateTime = "dateTime $it",
            thumbnail = "thumbnail $it",
            title = "title $it",
            url = "url $it",
        )
    }
}
