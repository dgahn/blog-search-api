package me.dgahn.repository

import org.springframework.data.jpa.repository.JpaRepository

interface SearchBlogHistoryJpaRepository : JpaRepository<SearchBlogHistoryEntity, Long> {
    fun findByKeyword(keyword: String): SearchBlogHistoryEntity?
}
