package by.kulevets.cryptocurrencywatcher.controller;

import by.kulevets.cryptocurrencywatcher.entity.Cryptocurrency;
import by.kulevets.cryptocurrencywatcher.entity.User;
import by.kulevets.cryptocurrencywatcher.service.CryptocurrencyService;
import by.kulevets.cryptocurrencywatcher.service.UserService;
import by.kulevets.cryptocurrencywatcher.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * The type Watcher controller.
 *
 * @author Vladislav Kulevets
 * @since 28.01.2022
 */
@Slf4j
@AllArgsConstructor
@ComponentScan(basePackages = {"by.kulevets.cryptocurrencywatcher.service"})
@RestController("crypto-watcher")
public class WatcherController {
    /**
     * The Cryptocurrency service.
     */
    public final CryptocurrencyService cryptocurrencyService;
    /**
     * The User service.
     */
    public final UserService userService;

    /**
     * Getting available cryptocurrencies.
     *
     * @return the list
     */
    @GetMapping("cryptos")
    public List<Cryptocurrency> cryptos(){
        log.info("GETTING A ALL AVAILABLE CRYPTOCURRENCIES...");
        return cryptocurrencyService.findAllCrypt();
    }

    /**
     * Getting actual cryptocurrency price by id
     *
     * @param cryptoId the crypto id
     * @return the string
     */
    @GetMapping("crypto/{cryptoId}")
    public String crypto(@PathVariable Long cryptoId){
        log.info("GETTING A REQUESTED CRYPTOCURRENCY PRICE...");
        Cryptocurrency cryptocurrency = null;
        try {
            cryptocurrency = cryptocurrencyService.findCryptocurrencyById(cryptoId);
        } catch (ServiceException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
        assert cryptocurrency != null;
        return String.valueOf(cryptocurrency.getPrice());
    }

    /**
     * Creating user
     *
     * @param user the user
     */
    @PostMapping("notify")
    public void notify(@RequestBody User user){
        log.info("CREATED THE USER...");
        try {
            userService.createUser(user);
        } catch (ServiceException e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
