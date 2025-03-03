package net.javaguides.spingannotations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                    .anyRequest().permitAll()

            )
            .csrf().disable()  // Desactivar CSRF (solo para pruebas)
        
            .formLogin(form -> form
                    .loginPage("/inicio") // Tu página de login personalizada
                    .defaultSuccessUrl("/inicio", true)   // Redirigir al inicio después del login exitoso
                    .permitAll()                          // Permitir acceso a la página de login para todos
                )
            .logout(config -> config.logoutSuccessUrl("/")
            )
            
            .build();
        
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
