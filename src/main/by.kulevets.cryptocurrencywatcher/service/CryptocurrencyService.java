package by.kulevets.cryptocurrencywatcher.service;

import by.kulevets.cryptocurrencywatcher.controller.RestTemplateConfiguration;
import by.kulevets.cryptocurrencywatcher.dao.CryptocurrencyRepository;
import by.kulevets.cryptocurrencywatcher.entity.Crypto;
import by.kulevets.cryptocurrencywatcher.entity.Cryptocurrency;
import by.kulevets.cryptocurrencywatcher.service.exception.ServiceException;
import by.kulevets.cryptocurrencywatcher.service.validation.impl.CryptocurrencyValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Service class for with all the necessary operations applicable to
 * <b>Cryptocurrency<b/>  instance
 *
 * @author Vladislav Kulevets
 * @see Cryptocurrency
 * @since 28.01.2022
 */
@Service
@Slf4j
@AllArgsConstructor
@EnableScheduling
public class CryptocurrencyService {
    private final CryptocurrencyRepository cryptocurrencyRepository;
    private final CryptocurrencyValidationService validator = new CryptocurrencyValidationService();
    private final RestTemplateConfiguration restTemplateConfiguration =
            new RestTemplateConfiguration();

    /**
     * Crypto converter cryptocurrency.
     *
     * @param crypto the crypto
     * @return the cryptocurrency
     */
    public static Cryptocurrency cryptoConverter(Crypto crypto) {
        return new Cryptocurrency(Long.parseLong(crypto.getId()),
                crypto.getSymbol(),
                Double.parseDouble(crypto.getPrice_usd()));
    }

    /**
     * Find all crypt list.
     *
     * @return the list
     */
    public List<Cryptocurrency> findAllCrypt() {
        return this.cryptocurrencyRepository.findAll();
    }

    /**
     * Create cryptocurrency.
     *
     * @param cryptocurrency the cryptocurrency
     * @throws ServiceException the service exception
     */
    public void createCryptocurrency(Cryptocurrency cryptocurrency) throws ServiceException {
        if (validator.isValid(cryptocurrency)
                && !cryptocurrencyRepository.existsById(cryptocurrency.getId())) {
            cryptocurrencyRepository.save(cryptocurrency);
        } else {
            throw new ServiceException();
        }
    }

    /**
     * Update all cryptocurrencies.
     *
     * @param cryptocurrencies the cryptocurrencies
     * @throws ServiceException the service exception
     */
    public void updateAllCryptocurrencies(List<Cryptocurrency> cryptocurrencies)
            throws ServiceException {
        for (Cryptocurrency cryptocurrency : cryptocurrencies) {
            if (!validator.isValid(cryptocurrency)
                    || !cryptocurrencyRepository.existsById(cryptocurrency
                    .getId())) {
                throw new ServiceException();
            }

            this.cryptocurrencyRepository.save(cryptocurrency);
        }

    }

    /**
     * Update cryptocurrency.
     *
     * @param cryptocurrency the cryptocurrency
     * @throws ServiceException the service exception
     */
    public void updateCryptocurrency(Cryptocurrency cryptocurrency)
            throws ServiceException {
        if (validator.isValid(cryptocurrency)
                && cryptocurrencyRepository.existsById(cryptocurrency.getId())) {
            cryptocurrencyRepository.save(cryptocurrency);
        } else {
            throw new ServiceException();
        }
    }

    /**
     * Delete cryptocurrency.
     *
     * @param cryptocurrency the cryptocurrency
     * @throws ServiceException the service exception
     */
    public void deleteCryptocurrency(Cryptocurrency cryptocurrency)
            throws ServiceException {
        if (validator.isValid(cryptocurrency)
                && cryptocurrencyRepository.existsById(cryptocurrency.getId())) {
            cryptocurrencyRepository.delete(cryptocurrency);
        } else {
            throw new ServiceException();
        }
    }

    /**
     * Find cryptocurrency by id cryptocurrency.
     *
     * @param id the id
     * @return the cryptocurrency
     * @throws ServiceException the service exception
     */
    public Cryptocurrency findCryptocurrencyById(Long id)
            throws ServiceException {
        if (!cryptocurrencyRepository.existsById(id)) {
            throw new ServiceException();
        } else {
            Cryptocurrency cryptocurrency = null;
            Optional<Cryptocurrency> item =
                    cryptocurrencyRepository.findById(id);
            if (item.isPresent()){
                cryptocurrency = item.get();
            }
            return cryptocurrency;
        }
    }

    /**
     * Method for requesting to CoinLore API and getting information about
     * corresponding cryptocurrencies
     */
    @Scheduled(cron = "0 * * * * *")
    public void updateCryptocurrenciesPrices() {
        log.info("Updating of cryptocurrencies' prices was started");
        RestTemplate template = restTemplateConfiguration.restTemplate();
        List<Cryptocurrency> cryptocurrencies = new ArrayList<>();

        for (Long id : Arrays.asList(90L, 80L, 48543L)) {
            Crypto[] cryptos = template.getForObject("https://api.coinlore" +
                    ".net/api/ticker/?id=" + id, Crypto[].class);
            assert cryptos != null;
            cryptocurrencies.add(cryptoConverter(cryptos[0]));
        }

        try {
            updateAllCryptocurrencies(cryptocurrencies);
            log.debug("Cryptocurrencies were updated");
        } catch (ServiceException e) {
            log.error(String.valueOf(e));
        }

        log.info("Updating of was ended");
    }

    /**
     * Gets actual price.
     *
     * @param id the id
     * @return the actual price
     * @throws ServiceException the service exception
     */
    public Double getActualPrice(Long id) throws ServiceException {
        if (cryptocurrencyRepository.existsById(id)) {
            updateCryptocurrenciesPrices();
            return findCryptocurrencyById(id).getPrice();
        } else {
            throw new ServiceException("No such cryptocurrency");
        }
    }

}
