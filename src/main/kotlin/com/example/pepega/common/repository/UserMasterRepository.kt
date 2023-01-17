package com.example.pepega.common.repository

import com.example.pepega.common.domain.UserMaster
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserMasterRepository : JpaRepository<UserMaster, UUID> {
    fun findByEmail(email: String): UserMaster?
}
