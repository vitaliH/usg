package com.usg.service.apiprovider;

import com.usg.dto.ExternalCurrencyProviderResponseDTO;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.UUID;

public interface ApiInvoker {

    Mono<ExternalCurrencyProviderResponseDTO> invoke(String baseCurrency);

    default WebClient createWebClient(String baseUrl) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(TcpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2_000)
                        .doOnConnected(connection ->
                                connection.addHandlerLast(new ReadTimeoutHandler(2))
                                        .addHandlerLast(new WriteTimeoutHandler(2))))))
                .defaultCookie("cookieKey", "cookieValue", "teapot", "amsterdam")
                .defaultCookie("secretToken", UUID.randomUUID().toString())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, "I'm a teapot")
                .build();
    }

}
