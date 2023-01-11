package com.example.pepega.helloworld.repository

import com.example.pepega.helloworld.domain.HelloWorld
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface HelloWorldRepository : JpaRepository<HelloWorld, UUID>
