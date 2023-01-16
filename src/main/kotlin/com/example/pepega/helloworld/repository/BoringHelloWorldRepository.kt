package com.example.pepega.helloworld.repository

import com.example.pepega.helloworld.domain.BoringHelloWorld
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface BoringHelloWorldRepository :
    JpaRepository<BoringHelloWorld, UUID>
