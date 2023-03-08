package com.example.pepega.tenor.client

import com.example.pepega.tenor.dto.ImplementSearchResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "tenor", url = "\${tenor.url}")
interface TenorClient {

    @GetMapping("/v2/search")
    fun findByImplementSearch(
        @RequestParam("q") searchString: String,
        @RequestParam("key") apiKey: String,
        @RequestParam("limit") limit: Int = 8,
        @RequestParam("random") random: Boolean = true
    ): ImplementSearchResponseDto
}
