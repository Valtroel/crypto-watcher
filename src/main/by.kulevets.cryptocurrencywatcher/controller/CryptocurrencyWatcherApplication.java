package by.kulevets.cryptocurrencywatcher.controller;

import by.kulevets.cryptocurrencywatcher.dao.CryptocurrencyRepository;
import by.kulevets.cryptocurrencywatcher.dao.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The type Cryptocurrency watcher application.
 */
@SpringBootApplication
@EntityScan({"by.kulevets.cryptocurrencywatcher.entity"})
@EnableJpaRepositories(basePackageClasses = {CryptocurrencyRepository.class,
        UserRepository.class})
public class CryptocurrencyWatcherApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CryptocurrencyWatcherApplication.class, args);
    }
}