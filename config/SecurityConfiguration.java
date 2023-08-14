package account.config;

import account.model.user.UserRole;
import account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(13);
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPointHandler();
    }


//    @Autowired
//    UserService userService;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .httpBasic(Customizer.withDefaults())
//                .csrf().disable()
//                .exceptionHandling(handing -> handing
//                        .authenticationEntryPoint(authenticationEntryPoint())
//                        .accessDeniedHandler(accessDeniedHandler())
//                )
//                .headers(headers -> headers.frameOptions().disable())
//                .authorizeHttpRequests(requests -> requests
//                        .requestMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/security/events/").hasRole(UserRole.AUDITOR.name())
//                        .requestMatchers(HttpMethod.GET, "/api/empl/payment").hasAnyRole(UserRole.USER.name(), UserRole.ACCOUNTANT.name())
//                        .requestMatchers(HttpMethod.GET, "/api/admin/user/").hasRole(UserRole.ADMINISTRATOR.name())
//                        .requestMatchers(HttpMethod.PUT, "/api/admin/user/role").hasRole(UserRole.ADMINISTRATOR.name())
//                        .requestMatchers(HttpMethod.PUT, "/api/admin/user/access").hasRole(UserRole.ADMINISTRATOR.name())
//                        .requestMatchers(HttpMethod.PUT, "/api/acct/payments").hasRole(UserRole.ACCOUNTANT.name())
//                        .requestMatchers(HttpMethod.POST, "/api/auth/changepass").hasAnyRole(UserRole.USER.name(), UserRole.ACCOUNTANT.name(), UserRole.ADMINISTRATOR.name())
//                        .requestMatchers(HttpMethod.POST, "/api/acct/payments").hasRole(UserRole.ACCOUNTANT.name())
//                        .requestMatchers(HttpMethod.DELETE, "/api/admin/user/{email}").hasRole(UserRole.ADMINISTRATOR.name())
//                        .requestMatchers("/error").permitAll()
//                        .requestMatchers("/actuator/shutdown").permitAll()
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no session
//                )
//                .build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint()) // Handle auth error
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and().authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/security/events/").hasRole(UserRole.AUDITOR.name())
                        .requestMatchers(HttpMethod.GET, "/api/empl/payment").hasAnyRole(UserRole.USER.name(), UserRole.ACCOUNTANT.name())
                        .requestMatchers(HttpMethod.GET, "/api/admin/user/").hasRole(UserRole.ADMINISTRATOR.name())
                        .requestMatchers(HttpMethod.PUT, "/api/admin/user/role").hasRole(UserRole.ADMINISTRATOR.name())
                        .requestMatchers(HttpMethod.PUT, "/api/admin/user/access").hasRole(UserRole.ADMINISTRATOR.name())
                        .requestMatchers(HttpMethod.PUT, "/api/acct/payments").hasRole(UserRole.ACCOUNTANT.name())
                        .requestMatchers(HttpMethod.POST, "/api/auth/changepass").hasAnyRole(UserRole.USER.name(), UserRole.ACCOUNTANT.name(), UserRole.ADMINISTRATOR.name())
                        .requestMatchers(HttpMethod.POST, "/api/acct/payments").hasRole(UserRole.ACCOUNTANT.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/admin/user/{email}").hasRole(UserRole.ADMINISTRATOR.name())
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/actuator/shutdown").permitAll()
                )
                .formLogin()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}