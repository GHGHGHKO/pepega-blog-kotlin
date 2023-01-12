package com.example.pepega.common.domain

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table

@Entity
@Table(name = "user_master")
class UserMaster(

    @Column(unique = true, length = 100)
    val email: String,

    @Column(length = 100)
    val password: String,

    @Column(length = 50)
    val nickName: String,

    @Column(length = 50)
    val createUser: String,

    @Column(length = 50)
    val updateUser: String,

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "user_master_rules",
        joinColumns = [JoinColumn(name = "user_master_id")])
    @Column
    val roles: MutableList<String>

) : BaseEntity()
