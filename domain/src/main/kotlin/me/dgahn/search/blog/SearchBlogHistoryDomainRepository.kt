package me.dgahn.search.blog

interface SearchBlogHistoryDomainRepository {
    fun saveOrUpdate(searchBlogHistoryDomain: SearchBlogHistoryDomain): SearchBlogHistoryDomain
    fun findByKeyword(keyword: String): SearchBlogHistoryDomain?
    fun findAllByOrderBySearchCount(size: Int): List<SearchBlogHistoryDomain>
}
