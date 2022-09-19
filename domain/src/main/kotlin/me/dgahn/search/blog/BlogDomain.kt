package me.dgahn.search.blog

data class BlogDomain(
    val blogName: String,
    val contents: String,
    val dateTime: String,
    val thumbnail: String,
    val title: String,
    val url: String,
)
