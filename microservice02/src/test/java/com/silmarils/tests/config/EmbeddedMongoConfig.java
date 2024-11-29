package com.silmarils.tests.config;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.io.IOException;

@TestConfiguration
public class EmbeddedMongoConfig {

    @Bean
    public MongodExecutable mongodExecutable() {
        ImmutableMongodConfig mongodConfig = ImmutableMongodConfig.builder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(27071, false))
                .build();
        MongodStarter starter = MongodStarter.getDefaultInstance();
        return starter.prepare(mongodConfig);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws IOException {
        mongodExecutable().start();
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory("mongodb://localhost:27071/test"));

    }
}
