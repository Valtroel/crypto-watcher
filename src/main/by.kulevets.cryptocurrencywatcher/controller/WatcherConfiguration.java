package by.kulevets.cryptocurrencywatcher.controller;

import by.kulevets.cryptocurrencywatcher.dao.CryptocurrencyRepository;
import by.kulevets.cryptocurrencywatcher.entity.Cryptocurrency;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration-class for define the available cryptocurrencies.
 *
 * @author Vladislav Kulevets
 * @since 28.01.2022
 */
@Configuration
@ComponentScan(basePackageClasses = {CryptocurrencyRepository.class})
public class WatcherConfiguration {

    /**
     * Command line runner command line runner.
     *
     * @param repository the repository
     * @return the command line runner
     */
    @Bean
    public CommandLineRunner commandLineRunner(CryptocurrencyRepository repository) {
        return (args) -> repository.saveAll(List.of(
                new Cryptocurrency(90L, "BTC", (Double) null),
                new Cryptocurrency(80L, "ETH", (Double) null),
                new Cryptocurrency(48543L, "SOL", (Double) null)
        ));
    }


}
