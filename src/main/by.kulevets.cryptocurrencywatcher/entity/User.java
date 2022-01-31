package by.kulevets.cryptocurrencywatcher.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * The type <b>User<b/>.
 *
 * @author Vladislav Kulevets
 * @since 27.01.2022
 */
@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table( name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_name")
    private String name;
    @Column(name = "crypto_id")
    private Long cryptoId;
    @Column(name = "register_crypto_price")
    private Double registerCryptoPrice;
}
