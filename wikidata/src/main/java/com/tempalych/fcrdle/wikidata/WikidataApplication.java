package com.tempalych.fcrdle.wikidata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.tempalych.fcrdle.server",
        "com.tempalych.fcrdle.wikidata"})
public class WikidataApplication {

    public static void main(String[] args) {
        SpringApplication.run(WikidataApplication.class, args);
    }

}
