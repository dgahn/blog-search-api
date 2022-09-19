package me.dgahn.repository

import me.dgahn.search.blog.SearchBlogHistoryDomain
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class SearchBlogHistoryEntity(
    @Id
    @GeneratedValue
    val id: Long = 0,
    val keyword: String,
    val searchCount: Int
) {
    @CreatedDate
    @Column(updatable = false)
    var createdAt: Instant? = null
        protected set

    @LastModifiedDate
    var updatedAt: Instant? = null
        protected set

    fun toDomain(): SearchBlogHistoryDomain {
        return SearchBlogHistoryDomain(
            id = id,
            keyword = keyword,
            createdAt = createdAt,
            updatedAt = updatedAt,
            _searchCount = searchCount,
        )
    }

    companion object {
        fun SearchBlogHistoryDomain.toEntity(): SearchBlogHistoryEntity {
            return SearchBlogHistoryEntity(
                id = id,
                keyword = keyword,
                searchCount = searchCount
            )
        }
    }
}
