package com.hvivox.srealizacao.configs;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig implements FlywayMigrationStrategy {

    @Override
    public void migrate(Flyway flyway) {

        Flyway publicSchema = Flyway
                .configure()
                .schemas("public")
                .defaultSchema("public")
                .locations("flyway/public/migration")
                .dataSource(flyway.getConfiguration().getDataSource())
                .baselineOnMigrate(true)
                .outOfOrder(true)
                .mixed(true)
                .load();

        Flyway srealizacaoCoreSchema = Flyway
                .configure()
                .schemas("srealizacao_core")
                .defaultSchema("srealizacao_core")
                .locations("flyway/srealizacao_core/migration")
                .dataSource(flyway.getConfiguration().getDataSource())
                .baselineOnMigrate(true)
                .outOfOrder(true)
                .mixed(true)
                .load();

        publicSchema.migrate();
        srealizacaoCoreSchema.migrate();
    }
}
