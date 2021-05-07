package com.zzyy.outh_server.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhouy262 授权服务器配置开启授权服务
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /**
     * jwt令牌转换器
     */
    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private UserDetailServiceImpl userDetailService;
    /**
     * 授权认证管理器
     */
    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * 令牌持久化存储接口
     */
    @Autowired
    TokenStore tokenStore;

    @Autowired
    private CustomUserAuthenticationConverter customUserAuthenticationConverter;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter: off
        // client端唯一标识
        clients.inMemory().withClient("client-a")
            // 客户端的密码，这里的密码应该是加密后的
            .secret(passwordEncoder.encode("client-a-secret")).accessTokenValiditySeconds(3600)
            .refreshTokenValiditySeconds(3600)
            // 授权模式标识
            .authorizedGrantTypes("authorization_code", "password")
            // 作用域
            .scopes("app")
            // 资源id // 回调地址
            .resourceIds("resource1").redirectUris("http://localhost");
        // @formatter: on
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.accessTokenConverter(jwtAccessTokenConverter).tokenStore(tokenStore)
            .userDetailsService(userDetailService).authenticationManager(authenticationManager);
    }

    /***
     * 授权服务器的安全配置
     * 
     * @param oauthServer
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.allowFormAuthenticationForClients().passwordEncoder(new BCryptPasswordEncoder())
            .tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    /**
     * 读取密钥的配置
     * 
     * @return
     */
    @Bean("keyProp")
    public KeyProperties keyProperties() {
        return new KeyProperties();
    }

    @Resource(name = "keyProp")
    private KeyProperties keyProperties;

    /**
     * 客户端配置
     * 
     * @return
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    @Autowired
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    /****
     * JWT令牌转换器
     * 
     * @param customUserAuthenticationConverter
     * @return
     */
    @Bean
    public JwtAccessTokenConverter
        jwtAccessTokenConverter(CustomUserAuthenticationConverter customUserAuthenticationConverter) {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 证书路径 changgou.jks
        KeyPair keyPair = new KeyStoreKeyFactory(keyProperties.getKeyStore().getLocation(),
            // 证书秘钥 changgouapp
            keyProperties.getKeyStore().getSecret().toCharArray())
                // 证书别名 changgou
                .getKeyPair(keyProperties.getKeyStore().getAlias(),
                    // 证书密码 changgou
                    keyProperties.getKeyStore().getPassword().toCharArray());
        converter.setKeyPair(keyPair);
        // 不设置这个会出现 Cannot convert access token to JSON
        String publicKey = getPubKey();

        log.info("publicKey:" + publicKey);

        converter.setVerifier(new RsaVerifier(publicKey));
        // 配置自定义的CustomUserAuthenticationConverter
        DefaultAccessTokenConverter accessTokenConverter =
            (DefaultAccessTokenConverter)converter.getAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(customUserAuthenticationConverter);
        return converter;
    }

    /**
     * 获取非对称加密公钥 Key
     *
     * @return 公钥 Key
     */
    private String getPubKey() {
        ClassPathResource resource = new ClassPathResource("public.key");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return "";
        }
    }

}
