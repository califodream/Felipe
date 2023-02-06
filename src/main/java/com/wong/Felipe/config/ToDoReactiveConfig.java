package com.wong.Felipe.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.wong.Felipe.domain.ToDo;
import com.wong.Felipe.repository.ToDoMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.wong.Felipe.repository")
public class ToDoReactiveConfig extends AbstractReactiveMongoConfiguration {

    private final Environment environment;

    @Autowired
    public ToDoReactiveConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected String getDatabaseName() {
        return "todo";
    }

    @Override
    @Bean
    @DependsOn("embeddedMongoServer")
    public MongoClient reactiveMongoClient() {
        int port = environment.getProperty("local.mongo.port", Integer.class);
        return MongoClients.create(String.format("mongodb://localhost:%d", port));
    }

    @Bean
    public CommandLineRunner insertAndView
            (ToDoMongoRepository toDoMongoRepository, ApplicationContext applicationContext) {
        return args -> {
            toDoMongoRepository.save(new ToDo("Hello World", true)).subscribe();
            toDoMongoRepository.save(new ToDo("Winter is comming")).subscribe();
            toDoMongoRepository.save(new ToDo("Royal never give up")).subscribe();
            toDoMongoRepository.findAll().subscribe(System.out::println);
        };
    }
}
