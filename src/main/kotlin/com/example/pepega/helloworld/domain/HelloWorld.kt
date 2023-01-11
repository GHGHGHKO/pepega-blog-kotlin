package com.example.pepega.helloworld.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.PostLoad
import jakarta.persistence.PostPersist
import jakarta.persistence.Table
import org.springframework.data.domain.Persistable
import java.util.Objects
import java.util.UUID

@Entity
@Table(name = "Foo")
class HelloWorld (
    name: String
) : Persistable<UUID> {

    @Id
    private val id: UUID = UUID.randomUUID()

    @Column
    var name: String = name

    @Column
    var age: Int = 0

    @Transient
    private var _isNew = true

    override fun getId(): UUID = id

    override fun isNew(): Boolean = _isNew

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (this::class != other::class) {
            return false
        }

        return id == (other as HelloWorld).id
    }

    override fun hashCode() = Objects.hashCode(id)

    @PostPersist
    @PostLoad
    protected fun load() {
        _isNew = false
    }
}
