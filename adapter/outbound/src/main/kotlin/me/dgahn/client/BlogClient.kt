package me.dgahn.client

interface BlogClient {
    fun search(outBoundSearchBlogCondition: OutBoundSearchBlogCondition): BaseBlogResponseListDto
}
