package io.pivotal.pal.tracker

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
class SecurityConfiguration constructor(@Value("\${HTTPS_DISABLED}") private var httpsDisabled: Boolean) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER")
    }

    override fun configure(http: HttpSecurity?) {
        if (http == null) return
        super.configure(http)
        if (!httpsDisabled) http.requiresChannel().anyRequest().requiresSecure()
        http
                .authorizeRequests().antMatchers("/**").hasRole("USER")
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
        http.formLogin().disable()
    }
}