package com.example.pepega.tenor.controller

import com.example.pepega.common.model.response.CommonResult
import com.example.pepega.common.service.response.ResponseService
import com.example.pepega.tenor.service.TenorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/tenor/v1")
@RestController
class TenorController(
    private val tenorService: TenorService,
    private val responseService: ResponseService
) {

    @GetMapping
    fun search(
        @RequestParam(required = true) search: String
    ): ResponseEntity<CommonResult> {
        return ResponseEntity.ok()
            .body(responseService.mutableListResult(
                tenorService.implementSearch(search)
            ))
    }
}
