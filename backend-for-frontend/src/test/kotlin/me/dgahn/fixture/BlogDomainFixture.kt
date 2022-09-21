package me.dgahn.fixture

import me.dgahn.search.blog.BlogDomain

object BlogDomainFixture {
    fun getBlogDomains(): List<BlogDomain> = (1..10).map {
        BlogDomain(
            blogName = "blogName $it",
            contents = "contents $it",
            dateTime = "dateTime $it",
            thumbnail = "thumbnail $it",
            title = "title $it",
            url = "url $it",
        )
    }
}
