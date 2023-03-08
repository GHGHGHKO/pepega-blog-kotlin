package com.example.pepega.tenor.service

import com.example.pepega.tenor.client.TenorClient
import com.example.pepega.tenor.dto.SearchResponseDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class TenorService(
    private val tenorClient: TenorClient
) {

    @Value("\${tenor.key}")
    lateinit var tenorKey: String

    fun implementSearch(search: String): MutableList<SearchResponseDto> {
        val callTenor = tenorClient.findByImplementSearch(search, tenorKey)

        val searchResponse: MutableList<SearchResponseDto> = mutableListOf()

        for (result in callTenor.results) {
            val searchResponseDto = SearchResponseDto(
                id = result.id,
                title = result.title,
                mediaFormats = result.mediaFormats,
                contentDescription = result.contentDescription,
                itemUrl = result.itemUrl
            )

            searchResponse.add(searchResponseDto)
        }
        return searchResponse
    }
}
