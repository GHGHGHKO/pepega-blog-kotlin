package com.example.pepega.helloworld

import com.example.pepega.helloworld.domain.BoringHelloWorld
import com.example.pepega.helloworld.domain.HelloWorld
import com.example.pepega.helloworld.repository.BoringHelloWorldRepository
import com.example.pepega.helloworld.repository.HelloWorldRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EntityManagerTest {

    @Test
    fun `EntityManager merge 호출된다`(
        @Autowired helloWorldRepository: HelloWorldRepository
    ) {
        val helloWorld = HelloWorld("pepega")
        helloWorldRepository.save(helloWorld)
        helloWorldRepository.flush()

        assertEquals(helloWorld.name, "pepega")
    }

    @Test
    fun `EntityManager delete 호출되지 않는다`(
        @Autowired helloWorldRepository: HelloWorldRepository
    ) {
        val helloWorld = HelloWorld("pepega")
        helloWorldRepository.save(helloWorld)
        helloWorldRepository.flush()

        helloWorldRepository.delete(helloWorld)
        helloWorldRepository.flush()
    }

    @Test
    fun `data class는 equals를 override 하지 않으면 참조(class) 비교를 한다`() {

        data class HelloWorld(val name: String) {
            var age: Int = 0
        }

        val person1 = HelloWorld("rick")
        val person2 = HelloWorld("rick")
        person1.age = 10
        person2.age = 20

        assertTrue(person1 == person2)
    }

    @Test
    fun `Entity와 영속화한 Entity를 비교하면 Hibernate Proxy가 발생한다`(
        @Autowired
        helloWorldRepository: HelloWorldRepository,

        @Autowired
        boringHelloWorldRepository: BoringHelloWorldRepository
    ) {

        val helloWorld = HelloWorld("nikita")
        val boringHelloWorld =
            BoringHelloWorld("tranth", helloWorld)

        helloWorldRepository.save(helloWorld)
        boringHelloWorldRepository.save(boringHelloWorld)

        val actual = boringHelloWorldRepository.findById(
            boringHelloWorld.id).get()

        assertTrue(helloWorld == actual.helloWorld)
    }
}
