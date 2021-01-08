package ru.fitness.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {
    @Value("${fitness.db.clean:false}")
    private boolean needClean;
    @Value("${fitness.db.fill-test-data:false}")
    private boolean fillTestData;

    @Bean
    public Flyway flyway(DataSource theDataSource) {
        FluentConfiguration conf = Flyway.configure();
        conf.dataSource(theDataSource);
        if (fillTestData) {
            conf.locations("classpath:db/migration", "classpath:db/testmigration");
        } else {
            conf.locations("classpath:db/migration");
        }
        Flyway flyway = conf.load();
        if (needClean) {
            flyway.clean();
        }
        flyway.migrate();
        return flyway;
    }
}
