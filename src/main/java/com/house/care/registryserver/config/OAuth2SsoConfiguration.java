package com.house.care.registryserver.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.house.care.registryserver.config.security.oauth2.AuthoritiesConstants;

@EnableOAuth2Sso
@Configuration
public class OAuth2SsoConfiguration extends WebSecurityConfigurerAdapter {

    private final RequestMatcher authorizationHeaderRequestMatcher;

    public OAuth2SsoConfiguration(@Qualifier("authorizationHeaderRequestMatcher") RequestMatcher authorizationHeaderRequestMatcher) {

        this.authorizationHeaderRequestMatcher = authorizationHeaderRequestMatcher;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .requestMatcher(new NegatedRequestMatcher(authorizationHeaderRequestMatcher))
                .httpBasic()
                .realmName("Registry Server")
                .and()
                .authorizeRequests()
                .antMatchers("/services/**").authenticated()
                .antMatchers("/eureka/**").hasAuthority(AuthoritiesConstants.ADMIN)
                .antMatchers("/api/profile-info").permitAll()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/config/**").hasAuthority(AuthoritiesConstants.ADMIN)
                .antMatchers("/management/health").permitAll()
                .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
                .antMatchers("/registry/**").hasAuthority(AuthoritiesConstants.ADMIN)
                .anyRequest().authenticated().and()
                .oauth2Login()
                .and()
                .oauth2ResourceServer().jwt();
    }

    @Bean
    public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {

        FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registration;
    }

}
