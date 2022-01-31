package by.kulevets.cryptocurrencywatcher.dao;

import by.kulevets.cryptocurrencywatcher.entity.Cryptocurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * The interface Cryptocurrency repository.
 *
 * @author Vladislav Kulevets
 * @since 27.01.2022
 */
@Repository
@Component
public interface CryptocurrencyRepository extends JpaRepository<Cryptocurrency, Long> {
}