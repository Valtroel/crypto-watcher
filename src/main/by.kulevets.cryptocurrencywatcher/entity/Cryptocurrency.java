package by.kulevets.cryptocurrencywatcher.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The type <b>Cryptocurrency</b>.
 *
 * @author Vladislav Kulevets
 * @since 27.01.2022
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cryptocurrency")
public class Cryptocurrency {
    @Id
    private Long id;
    @Column(name = "symbol")
    private String name;
    @Column(name = "price")
    private Double price;
}
