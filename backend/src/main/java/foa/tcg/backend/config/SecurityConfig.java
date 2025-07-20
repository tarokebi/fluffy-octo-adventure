package foa.tcg.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// セキュリティ設定（別のConfigクラスに配置）
@Configuration
@EnableWebSecurity
public class SecurityConfig /* extends WebSecurityConfigurerAdapter */ { // WebSecurityConfigurerAdapter ではなく SecurityFilterChain を使う
//    @Autowired
//    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        builder.userDetailsService(new LocalUserDetailService(passwordEncoder()));
//        AuthenticationManager manager = builder.build();
        http
//            .csrf(Customizer.withDefaults())
//            .authorizeRequests()
//            .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
//            .requestMatchers("/admin/**").hasAnyRole("ADMIN", "STAFF")
//            .and().sessionManagement(Customizer.withDefaults());
//            .anyRequest().permitAll();
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll()
            );
        return http.build();
    }
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
    //    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .csrf(Customizer.withDefaults())
//            .dispatcherTypeMatchers(HttpMethod.valueOf("/api/auth/login")).permitAll()
//            .anyRequest().authenticated()
//            .and().sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService)
//            .passwordEncoder(passwordEncoder());
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User
            .withUsername("user")
            .password(passwordEncoder().encode("123456"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

}
