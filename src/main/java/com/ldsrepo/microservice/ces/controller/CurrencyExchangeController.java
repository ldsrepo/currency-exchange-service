package com.ldsrepo.microservice.ces.controller;

import com.ldsrepo.microservice.ces.model.CurrencyExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/currency-exchange")
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    @GetMapping("/hello")
    public String sayHello(){
        return "hello!";
    }

    @GetMapping("/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to){




        CurrencyExchange exchange =  jdbcTemplate.queryForObject("SELECT * FROM CURRENCY_EXCHANGE WHERE CURRENCY_FROM = :currencyfrom AND CURRENCY_TO = :currencyto",

                                  new MapSqlParameterSource().addValue("currencyfrom",from).addValue("currencyto",to),
                                           (rs, rowNum) -> {
                                                return new CurrencyExchange(rs.getLong("Id"),rs.getString("currency_from"),rs.getString("currency_to"),rs.getBigDecimal("conversion_multiple"),null);
                                           }

        );

        exchange.setEnvironment(environment.getProperty("local.server.port"));

        return exchange;
    }
}
