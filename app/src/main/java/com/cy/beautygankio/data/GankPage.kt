package com.cy.beautygankio.data

data class GankPage(
    val `data`: List<Girl>,
    val page: Int,
    val page_count: Int,
    val status: Int,
    val total_counts: Int
)