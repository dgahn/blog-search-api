package me.dgahn.search.blog

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@RestController
@Validated
class BlogController(
    private val searchBlogApplicationService: SearchBlogApplicationService,
) {

    @GetMapping("/v1/search/blog")
    fun searchBlog(
        @RequestParam query: String,
        @RequestParam(required = false, defaultValue = "ACCURACY") sort: SearchBlogSortEnum,
        @RequestParam(required = false, defaultValue = "1") @Min(PAGING_MIN) @Max(PAGING_MAX) page: Int,
        @RequestParam(required = false, defaultValue = "10") @Min(PAGING_MIN) @Max(PAGING_MAX) size: Int,
    ): List<BlogResponseDto> {
        return searchBlogApplicationService.searchBlog(SearchBlogCondition(query, sort.toInternal(), page, size))
            .toDto()
    }

    @GetMapping("/v1/top-searched/blog")
    fun getSearchedTop(): List<SearchBlogHistoryResponseDto> {
        return searchBlogApplicationService.getSearchedTopHistory().toDto()
    }

    companion object {
        private const val PAGING_MIN = 1L
        private const val PAGING_MAX = 50L
    }
}
