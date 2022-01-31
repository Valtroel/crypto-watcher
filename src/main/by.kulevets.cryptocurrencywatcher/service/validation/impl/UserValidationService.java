package by.kulevets.cryptocurrencywatcher.service.validation.impl;

import by.kulevets.cryptocurrencywatcher.entity.User;
import by.kulevets.cryptocurrencywatcher.service.validation.Validation;


/**
 * Validation-service for <b>User</b> instance
 *
 * @author Vladislav Kulevets
 * @see by.kulevets.cryptocurrencywatcher.service.validation.Validation
 * @since 27.01.2022
 */
public class UserValidationService implements Validation<User> {

    public boolean isValid(User user) {
        return user.getName() != null;
    }
}
