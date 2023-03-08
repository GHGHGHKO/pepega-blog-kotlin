package com.example.pepega.common.config.security

import com.example.pepega.common.config.security.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig (
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationEntryPointCustom: AuthenticationEntryPointCustom,
    private val accessDeniedHandlerCustom: AccessDeniedHandlerCustom
) {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity
    ): SecurityFilterChain {

        httpSecurity
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
            .requestMatchers("/sign/*/signIn", "/sign/*/signUp", "/tenor/**").permitAll()
            .requestMatchers("/users/**").hasRole("ADMIN")
            .anyRequest().hasRole("USER")
            .and()
            .exceptionHandling().accessDeniedHandler(accessDeniedHandlerCustom)
            .and()
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPointCustom)
            .and()
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java)

        return httpSecurity.build()
    }
}
