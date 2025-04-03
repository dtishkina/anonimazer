package com.example.anonimazer;


import com.example.anonimazer.models.User;
import com.example.anonimazer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/anonimazer/photo").hasAnyRole("USER")
                        .requestMatchers(HttpMethod.GET, "/anonimazer/credential").permitAll()
                        .requestMatchers(HttpMethod.GET, "/info").permitAll()
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers(HttpMethod.POST, "/credential").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin()
                .and()
                .httpBasic();

        return http.build();
    }


//    @Bean
//    protected UserDetailsService userDetailsService(@Autowired UserService userService) {
//        List<UserDetails> springSecurityUserList = new ArrayList<>();
//        List<User> userList = userService.findAll();
//        for (User user : userList) {
//            springSecurityUserList.add(org.springframework.security.core.userdetails.User.builder()
//                    .username(user.getLogin())
//                    .password(user.getPassword())
//                    .roles(user.getRole().toString())
//                    .build()
//            );
//        }
//        return new InMemoryUserDetailsManager(
//                springSecurityUserList
//        );
//    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}