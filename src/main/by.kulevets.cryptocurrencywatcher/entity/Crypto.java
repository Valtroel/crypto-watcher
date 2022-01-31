package by.kulevets.cryptocurrencywatcher.entity;

import lombok.*;

/**
 * DTO-class for describe <b>Crypto<b/>
 * instance, that is necessary for correct interaction with the CoinAPI.
 *
 * @author Vladislav Kulevets
 * @since 28.01.2022
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Crypto {
    private String id;
    private String symbol;
    private String name;
    private String nameid;
    private int rank;
    private String price_usd;
    private String percent_change_24h;
    private String percent_change_1h;
    private String percent_change_7d;
    private String market_cap_usd;
    private String volume24;
    private String volume24_native;
    private String csupply;
    private String price_btc;
    private String tsupply;
    private String msupply;
}
