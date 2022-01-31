package by.kulevets.cryptocurrencywatcher.dao;

import by.kulevets.cryptocurrencywatcher.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface User repository.
 *
 * @author Vladislav Kulevets
 * @since 27.01.2022
 */
@Repository
@Component
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find users by crypto id list.
     *
     * @param cryptoId the crypto id
     * @return the list
     */
    List<User> findUsersByCryptoId(Long cryptoId);
}