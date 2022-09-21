package me.dgahn

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.retry.annotation.EnableRetry

@SpringBootApplication
@EnableRetry
class BlogSearchApplication

fun main(arg: Array<String>) {
    runApplication<BlogSearchApplication>(*arg)
}
