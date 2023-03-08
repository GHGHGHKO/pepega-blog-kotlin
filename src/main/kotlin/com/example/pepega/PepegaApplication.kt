package com.example.pepega

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class PepegaApplication

fun main(args: Array<String>) {
	runApplication<PepegaApplication>(*args)
}
