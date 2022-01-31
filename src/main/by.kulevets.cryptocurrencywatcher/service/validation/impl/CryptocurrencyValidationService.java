package by.kulevets.cryptocurrencywatcher.service.validation.impl;

import by.kulevets.cryptocurrencywatcher.entity.Cryptocurrency;
import by.kulevets.cryptocurrencywatcher.service.validation.Validation;

/**
 * Validation-service for <b>Cryptocurrency<b/> instance
 *
 * @author Vladislav Kulevets
 * @see by.kulevets.cryptocurrencywatcher.service.validation.Validation
 * @since 27.01.2022
 */
public class CryptocurrencyValidationService implements Validation<Cryptocurrency> {

    public boolean isValid(Cryptocurrency cryptocurrency) {
        return cryptocurrency.getId() != null || cryptocurrency.getName() != null;
    }
}
