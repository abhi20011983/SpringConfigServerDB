package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


@EnableConfigServer
@SpringBootApplication
public class ConfigServiceApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(ConfigServiceApplication.class, args);
  }

  @Bean
  public Querifier sp() {
    return new Querifier();
  }

  @Primary
  // @ConfigurationProperties(prefix="datasource.primary")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }


}
