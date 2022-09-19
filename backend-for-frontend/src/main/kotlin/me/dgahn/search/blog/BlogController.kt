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

    // ToDo sort 값을 ENUM 값으로 변경
    @GetMapping("/v1/search/blog")
    fun searchBlog(
        @RequestParam query: String,
        @RequestParam(required = false, defaultValue = "accuracy") sort: String,
        @RequestParam(required = false, defaultValue = "1") @Min(PAGING_MIN_VALUE) @Max(PAGING_MAX_VALUE) page: Int,
        @RequestParam(required = false, defaultValue = "10") @Min(PAGING_MIN_VALUE) @Max(PAGING_MAX_VALUE) size: Int,
    ): List<BlogResponseDto> {
        return searchBlogApplicationService.searchBlog(SearchBlogCondition(query, sort, page, size)).toDto()
    }

    @GetMapping("/v1/top-searched/blog")
    fun getSearchedTop(
        @RequestParam(required = false, defaultValue = "10") @Min(PAGING_MIN_VALUE) @Max(PAGING_MAX_VALUE) size: Int,
    ): List<SearchBlogHistoryDto> {
        return searchBlogApplicationService.getSearchedTopHistory(size).toDto()
    }

    companion object {
        private const val PAGING_MIN_VALUE = 1L
        private const val PAGING_MAX_VALUE = 50L
    }
}
