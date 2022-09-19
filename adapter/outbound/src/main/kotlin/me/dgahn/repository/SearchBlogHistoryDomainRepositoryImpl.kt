package me.dgahn.repository

import me.dgahn.repository.SearchBlogHistoryEntity.Companion.toEntity
import me.dgahn.search.blog.SearchBlogHistoryDomain
import me.dgahn.search.blog.SearchBlogHistoryDomainRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class SearchBlogHistoryDomainRepositoryImpl(
    private val searchBlogHistoryJpaRepository: SearchBlogHistoryJpaRepository
) : SearchBlogHistoryDomainRepository {

    override fun saveOrUpdate(searchBlogHistoryDomain: SearchBlogHistoryDomain): SearchBlogHistoryDomain {
        return searchBlogHistoryJpaRepository.save(searchBlogHistoryDomain.toEntity()).toDomain()
    }

    override fun findByKeyword(keyword: String): SearchBlogHistoryDomain? {
        return searchBlogHistoryJpaRepository.findByKeyword(keyword)?.toDomain()
    }

    override fun findAllByOrderBySearchCount(size: Int): List<SearchBlogHistoryDomain> {
        val pageRequest = PageRequest.of(0, size)
        return searchBlogHistoryJpaRepository.findAllByOrderBySearchCountDesc(pageRequest)
            .map { it.toDomain() }
    }
}
