package me.dgahn.repository

import me.dgahn.repository.SearchBlogHistoryEntity.Companion.toEntity
import me.dgahn.search.blog.SearchBlogHistoryDomain
import me.dgahn.search.blog.SearchBlogHistoryDomainRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SearchBlogHistoryDomainRepositoryImpl(
    private val searchBlogHistoryJpaRepository: SearchBlogHistoryJpaRepository
) : SearchBlogHistoryDomainRepository {

    @Transactional
    override fun saveOrUpdate(searchBlogHistoryDomain: SearchBlogHistoryDomain): SearchBlogHistoryDomain {
        return searchBlogHistoryJpaRepository.save(searchBlogHistoryDomain.toEntity()).toDomain()
    }

    @Transactional(readOnly = true)
    override fun findByKeyword(keyword: String): SearchBlogHistoryDomain? {
        return searchBlogHistoryJpaRepository.findByKeyword(keyword)?.toDomain()
    }
}
