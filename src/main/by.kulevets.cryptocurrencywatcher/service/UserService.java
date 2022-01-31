package by.kulevets.cryptocurrencywatcher.service;

import by.kulevets.cryptocurrencywatcher.dao.UserRepository;
import by.kulevets.cryptocurrencywatcher.entity.Cryptocurrency;
import by.kulevets.cryptocurrencywatcher.entity.User;
import by.kulevets.cryptocurrencywatcher.service.exception.ServiceException;
import by.kulevets.cryptocurrencywatcher.service.validation.impl.UserValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class for with all the necessary operations applicable to
 * <b>User<b/> instance
 *
 * @author Vladislav Kulevets
 * @see User
 * @since 28.01.2022
 */
@Service
@Slf4j
@AllArgsConstructor
@EnableScheduling
public class UserService {
    private final UserRepository userRepository;
    private final CryptocurrencyService cryptocurrencyService;
    private final UserValidationService validator = new UserValidationService();

    /**
     * Create user.
     *
     * @param user the user
     * @throws ServiceException the service exception
     */
    public void createUser(User user) throws ServiceException {
        if (validator.isValid(user) && !userRepository.existsById(user.getId())) {
            Double actualPreferCryptoPrice = cryptocurrencyService
                    .findCryptocurrencyById(user.getCryptoId()).getPrice();
            user.setRegisterCryptoPrice(actualPreferCryptoPrice);
            userRepository.save(user);
        } else {
            throw new ServiceException();
        }
    }

    /**
     * Update user.
     *
     * @param user the user
     * @throws ServiceException the service exception
     */
    public void updateUser(User user) throws ServiceException {
        if (validator.isValid(user) && userRepository.existsById(user.getId())) {
            userRepository.save(user);
        } else {
            throw new ServiceException();
        }
    }

    /**
     * Delete user.
     *
     * @param user the user
     * @throws ServiceException the service exception
     */
    public void deleteUser(User user) throws ServiceException {
        if (validator.isValid(user) && userRepository.existsById(user.getId())) {
            userRepository.delete(user);
        } else {
            throw new ServiceException();
        }
    }

    /**
     * Find all users list.
     *
     * @return the list
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Find user by id user.
     *
     * @param id the id
     * @return the user
     * @throws ServiceException the service exception
     */
    public User findUserById(Long id) throws ServiceException {
        User user = null;
        if (!userRepository.existsById(id)) {
            throw new ServiceException();
        } else {
            Optional<User> item = userRepository.findById(id);
            if (item.isPresent()){
                user = item.get();
            }
            return user;
        }
    }

    /**
     * Check difference of prices.
     *
     * @throws ServiceException the service exception
     */
    @Scheduled(cron = "1 * * * * *")
    public void checkDifferenceOfPrices() throws ServiceException {
        Map<Long, List<User>> sortedUsersByCryptoId = sortUsersByCryptoPreferences();
        for (Long cryptoId : sortedUsersByCryptoId.keySet()) {
            for (var sortedUser : sortedUsersByCryptoId.get(cryptoId)) {
                Cryptocurrency cryptocurrency = cryptocurrencyService
                        .findCryptocurrencyById(sortedUser.getCryptoId());
                Double regPrice = sortedUser.getRegisterCryptoPrice();
                Double actualPrice = cryptocurrency.getPrice();
                if (Math.abs(regPrice - actualPrice) > regPrice * 0.01D) {
                    Long cryptocurrencyId = cryptocurrency.getId();
                    log.warn("Price of cryptocurrency with id "
                            + cryptocurrencyId + ". For " + sortedUser.getName()
                            + ". Difference: " + Math.abs(regPrice
                            - actualPrice));
                }
            }
        }

    }

    private Map<Long, List<User>> sortUsersByCryptoPreferences() {
        Map<Long, List<User>> sortedUsersByPreferences = new HashMap<>();
        for (Long cryptoId : Arrays.asList(80L, 90L, 48543L)) {
            sortedUsersByPreferences.put(cryptoId, userRepository
                    .findUsersByCryptoId(cryptoId));
        }
        return sortedUsersByPreferences;
    }

}
