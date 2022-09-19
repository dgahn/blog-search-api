package me.dgahn

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogSearchApplication

fun main(arg: Array<String>) {
    runApplication<BlogSearchApplication>(*arg)
}
