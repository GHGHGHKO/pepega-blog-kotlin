package com.example.pepega.common.domain

import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.Persistable
import java.time.LocalDateTime
import java.util.UUID

@MappedSuperclass
abstract class BaseEntity : Persistable<UUID> {

    @Id
    @Column(columnDefinition = "uuid")
    private val id: UUID = UUID.randomUUID()

    @Column
    private val createDate: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column
    private val updateDate: LocalDateTime = LocalDateTime.now()
}
