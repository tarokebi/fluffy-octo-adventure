package foa.tcg.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// セキュリティ設定（別のConfigクラスに配置）
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .csrf(Customizer.withDefaults())
//            .dispatcherTypeMatchers(HttpMethod.valueOf("/api/auth/login")).permitAll()
//            .anyRequest().authenticated()
//            .and().sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
