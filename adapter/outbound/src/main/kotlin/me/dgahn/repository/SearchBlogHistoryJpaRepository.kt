package me.dgahn.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import javax.persistence.LockModeType

interface SearchBlogHistoryJpaRepository : JpaRepository<SearchBlogHistoryEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByKeyword(keyword: String): SearchBlogHistoryEntity?
    fun findAllByOrderBySearchCountDesc(pageable: Pageable): List<SearchBlogHistoryEntity>
}
