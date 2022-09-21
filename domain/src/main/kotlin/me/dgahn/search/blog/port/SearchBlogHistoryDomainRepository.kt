package me.dgahn.search.blog.port

import me.dgahn.search.blog.SearchBlogHistoryDomain

interface SearchBlogHistoryDomainRepository {
    fun saveOrUpdate(searchBlogHistoryDomain: SearchBlogHistoryDomain): SearchBlogHistoryDomain
    fun findByKeyword(keyword: String): SearchBlogHistoryDomain?
    fun findAllByOrderBySearchCount(size: Int): List<SearchBlogHistoryDomain>
}
