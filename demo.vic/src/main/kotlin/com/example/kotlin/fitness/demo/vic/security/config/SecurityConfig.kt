package com.example.kotlin.fitness.demo.vic.security.config

import com.example.kotlin.fitness.demo.vic.costom.JwtAccessDeniedHandler
import com.example.kotlin.fitness.demo.vic.costom.JwtAuthenticationEntryPoint
import com.example.kotlin.fitness.demo.vic.security.filter.JwtAuthFilter
import com.example.kotlin.fitness.demo.vic.storage.IUserStorage
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfig {
    private val jwtAuthFilter: JwtAuthFilter? = null
    private val userStorage: IUserStorage? = null
    private val authEntryPoint: JwtAuthenticationEntryPoint? = null
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler? = null

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeRequests()
            .requestMatchers("/**/registration/**").permitAll()
            .requestMatchers("/**/activation/**").permitAll()
            .requestMatchers("/**/login/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/**/product/**").permitAll()
            .requestMatchers(HttpMethod.PUT, "/**/users/**").hasAnyAuthority("ROLE_ADMIN")
            .requestMatchers("/**/me/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
            .requestMatchers("/**/users/**").hasAnyAuthority("ROLE_ADMIN")
            .requestMatchers("/**/recipe/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
            .requestMatchers("/**/profile/**").hasAnyAuthority("ROLE_USER")
            .requestMatchers("/**/audit/**").hasAnyAuthority("ROLE_ADMIN")
            .anyRequest()
            .authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //if you authenticate your user the first time the
            //session will have always the authenticated state
            //and this is the behavior that we don't want to add
            .and()
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling()
            .authenticationEntryPoint(authEntryPoint)
            .and() /*.exceptionHandling()
                .authenticationEntryPoint(jwtEntryPoint)
                .and()*/
            .exceptionHandling()
            .accessDeniedHandler(jwtAccessDeniedHandler)


        //.addFilterBefore(new JwtRequestFilter(authenticationManager(), userDetailsService, new JWTHelper(), handlerExceptionResolver), UsernamePasswordAuthenticationFilter.class);
        /*.exceptionHandling().authenticationEntryPoint((request, response, authException) ->
                 response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        authException.getMessage()))*/
        //there we want to add filter before another filter(jwtAuthFilter before
        //there we told Spring , hey go ahead and use this filter before authentication the User
        //because (in jwtAuthFilter we are checking the JWT and if everything is fine what we do - we
        //set, or we update the context of the security context holder,so we want to execute
        //jwtAuthFilter before UsernamePasswordAuthenticationFilter
        return http.build() as SecurityFilterChain
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        //this method that we want spring to use instead
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService())
        authenticationProvider.setPasswordEncoder(passwordEncoder())
        return authenticationProvider
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        //return null;
        //return NoOpPasswordEncoder.getInstance();
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { login -> //In this method (loadUserByUsername) we load user by userName
            //the one that we will use the JWT authentication filter
            //in this case we are not fetching user from database
            //we use a static list
            /**/
            //return userDao.findByUsername(email);
            userStorage!!.findByLogin(login)!!
        }
    }
}