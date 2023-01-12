package com.example.pepega.helloworld.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "Foo")
class HelloWorld (
    @Column
    val name: String
) : CommonEntity() {

    @Column
    var age: Int = 0
}
