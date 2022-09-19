package me.dgahn.util

data class URI(
    private val baseUri: String,
) {
    private val queryParams: MutableMap<String, String> = mutableMapOf()

    val value: String
        get() = "$baseUri?${queryParams.entries.joinToString(separator = "$") { "${it.key}=${it.value}" }}"

    fun addQueryParam(paramName: String, paramValue: String) {
        queryParams[paramName] = paramValue
    }
}
