package jp.co.zaico.codingtest.common

data class PagedResult<T>(
    val data: List<T>,
    val nextPage: Int? = null
)
