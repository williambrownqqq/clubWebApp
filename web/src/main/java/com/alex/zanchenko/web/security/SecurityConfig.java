package com.alex.zanchenko.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import java.util.Map;

import static org.apache.tomcat.websocket.Constants.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
@EnableWebSecurity // we need to add these 2 annotations to make sure that spring boot can actually
// see the security config and tell spring boot that it is indeed a security config file
public class SecurityConfig {

    private CustomUserDetailsService userDetailsService;
    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean // provide password encoder. it's going to make it so that we're not storing passwords plain text within our database
    // storing passwords inside plain text like we're doing right now like if you look inside of our database right now
    // you never want to store passwords in plain text - you always want them hashed because if somebody were able to break into your database
    // and passwords are in plain text can be easily hack other people and it will be a huge nightmare
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    public void configure(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); // this is what's going to allow us to have passwords not stored in plain text within our database
    }

    @Bean // the next thing that we're going to do is we are going to actually configure our security filter chain
    // it's just a chain of methods thaat are going to control how your security works and it's pretty much a really fancy configuration file for developers
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http.csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/login", "/register", "/clubs", "/register/**", "/css/**", "/js/**")
//                .permitAll()
//                .and()
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/clubs")
//                        .loginProcessingUrl("/login")
//                        .failureUrl("/login?error=true")
//                        .permitAll()
//                ).logout(
//                        logout -> logout
//                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
//                );
//
//        return http.build();

//        http
//                .csrf().disable()
//                .authorizeHttpRequests(requests -> requests
//                        .requestMatchers("/login", "/register", "/clubs", "/register/**", "/css/**", "/js/**")
//                        .permitAll()
//                        .anyRequest().authenticated())
//                .httpBasic();
//        return http.build();

        http.csrf().disable()
                .cors()
                .and()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/clubs").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();

//        http.csrf().disable()
//                .cors()
//                .and()
//                .authorizeRequests()
//                .requestMatchers("/login", "/register", "/clubs", "/register/**", "/css/**", "/js/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//        return http.build();

//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/login", "/register", "/clubs", "/register/**", "/css/**", "/js/**")
//                        .permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout((logout) -> logout.permitAll());
//
//        return http.build();

//        http.csrf().disable()
//                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
//                .authorizeHttpRequests((authorize) -> {
//                            try {
//                                authorize
//                                        .requestMatchers("/login", "/register", "/clubs", "/register/**", "/css/**", "/js/**")
//                                        .permitAll()
//                                        .anyRequest().authenticated()
//                                        .and()
//                                        .httpBasic()
//                                        .authenticationEntryPoint((request, response, authException) -> {
//                                            response.setStatus(UNAUTHORIZED);
//                                            response.setContentType(APPLICATION_JSON_VALUE);
//                                            //objectMapper.writeValue(response.getOutputStream(), Map.of("error_message", "No access token provided"));
//                                        });
//                            } catch (Exception e) {
//                                throw new RuntimeException(e);
//                            }
//                        }
//                );
//
//        return http.build();

//        return http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login", "/register", "/clubs", "/register/**", "/css/**", "/js/**")
//                        .permitAll()
//                        .anyRequest()
//                        .authenticated()
//                        .and()
//                        .httpBasic()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/clubs")
//                        .loginProcessingUrl("/login")
//                        .failureUrl("/login?error=true")
//                        .permitAll()
//                ).logout(
//                        logout -> logout
//                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
//                )
//                .build();
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
//                .httpBasic(Customizer.withDefaults())
//                .build();
    }

}

