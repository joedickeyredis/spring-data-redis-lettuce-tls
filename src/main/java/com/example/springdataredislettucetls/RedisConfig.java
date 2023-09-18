package com.example.springdataredislettucetls;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.lettuce.core.*;
import io.lettuce.core.protocol.ProtocolVersion;

import java.io.File;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.ssl.trust-store}")
    private String truststorePath; // Path to your truststore file

    @Value("${spring.redis.ssl.trust-store-password}")
    private String truststorePassword; // Password for the truststore

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        SslOptions sslOptions = SslOptions.builder()
                .jdkSslProvider() // JDK is probably already default
                .truststore(new File(truststorePath), truststorePassword)
                .build();
        ClientOptions clientOptions = ClientOptions.builder()
                .protocolVersion(ProtocolVersion.RESP2)
                .sslOptions(sslOptions)
                .build();
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .clientOptions(clientOptions)
                .useSsl()
                .build();
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHost); // Set your Redis server host name or IP address
        configuration.setPort(redisPort); // Set your Redis server port

        return new LettuceConnectionFactory(configuration, clientConfig);
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}

