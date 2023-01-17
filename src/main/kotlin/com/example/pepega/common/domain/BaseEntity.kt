package com.example.pepega.common.domain

import com.example.pepega.helloworld.domain.CommonEntity
import com.example.pepega.helloworld.domain.HelloWorld
import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PostLoad
import jakarta.persistence.PostPersist
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.Persistable
import java.time.LocalDateTime
import java.util.Objects
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

    @Transient
    private var _isNew = true

    override fun getId(): UUID = id

    override fun isNew(): Boolean = _isNew

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (other !is HibernateProxy &&
            this::class != other::class) {
            return false
        }

        return id == getIdentifier(other)
    }

    private fun getIdentifier(obj: Any): Any? {
        return if (obj is HibernateProxy) {
            obj.hibernateLazyInitializer.identifier
        } else {
            (obj as CommonEntity).id
        }
    }

    override fun hashCode() = Objects.hashCode(id)

    @PostPersist
    @PostLoad
    protected fun load() {
        _isNew = false
    }
}
