package com.pankaj.shoppingcart

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux


@EnableSwagger2WebFlux
@Configuration
@SpringBootApplication
open class ShoppingCartApplication

fun main(args: Array<String>) {
    runApplication<ShoppingCartApplication>(*args)
}

@Bean
fun api(): Docket {
    return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.pankaj.shoppingcart.controllers"))
            .paths(PathSelectors.any())
            .build()
}