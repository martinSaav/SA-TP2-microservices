package com.sistemasactivos.webclient.config;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;


@Configuration
@EnableWebFlux
@EnableWebFluxSecurity
public class WebFluxConfig implements WebFluxConfigurer {

    @Value("${api.base-url-1}")
    private String baseUrl1;

    @Value("${api.username-1}")
    private String username1;

    @Value("${api.password-1}")
    private String password1;


    @Value("${api.base-url-2}")
    private String baseUrl2;

    @Value("${api.username-2}")
    private String username2;

    @Value("${api.password-2}")
    private String password2;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .anyExchange().authenticated()
                .and()
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    @Qualifier("healthPlanWebClient")
    public WebClient getWebClient1() {
        return getWebClient(baseUrl1, username1, password1);
    }

    @Bean
    @Qualifier("clinicWebClient")
    public WebClient getWebClient2() {
        return getWebClient(baseUrl2, username2, password2);
    }

    private WebClient getWebClient(String baseUrl, String username, String password) {
        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                        .doOnConnected(conn -> conn
                                .addHandlerLast(new ReadTimeoutHandler(10))
                                .addHandlerLast(new WriteTimeoutHandler(10))));

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient.wiretap(true));

        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(connector)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(basicAuthentication(username, password))
                .build();
    }
}
