package com.uisrael.TurnoSmart.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import com.uisrael.TurnoSmart.servicio.impl.UserDetailsServicioImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UserDetailsServicioImpl userDetailsService;

	public SecurityConfig(UserDetailsServicioImpl userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf().disable() // Desactiva temporalmente CSRF
	        .authorizeHttpRequests(authorize -> authorize 
	            .requestMatchers("/docente/**").hasAuthority("DOCENTE")  // Acceso para docentes
	            .requestMatchers("/representante/**").hasAuthority("REPRESENTANTE")  // Acceso para representantes
	            .requestMatchers("/login").permitAll()  // Rutas públicas
	            .requestMatchers("/assets/**", "/dist/**", "/src/**", "/webjars/**").permitAll() // Rutas públicas para recursos estáticos
	            .anyRequest().authenticated()  // Cualquier otra ruta requiere autenticación
	        )
	        .formLogin(form -> form
	            .loginPage("/login")  // Página de inicio de sesión personalizada
	            .failureUrl("/login?error=true") // Redirige con un parámetro si hay error
	            .defaultSuccessUrl("/default", true)  // Redirección a "/default" después del login
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .logoutUrl("/logout")
	            .logoutSuccessUrl("/login?logout")  // Redirige al login después del logout
	            .permitAll()
	        );

	    return http.build();
	}
	
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/assets/**", "/dist/**", "/src/**", "/webjars/**");
    }


	// Configura el AuthenticationManagerBuilder en el constructor en lugar de definir un método @Bean separado
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    
    //Permite usar doble // 
    @Bean
    public StrictHttpFirewall allowDoubleSlashFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true);
        return firewall;
    }

    public void configure(WebSecurity web) throws Exception {
        web.httpFirewall(allowDoubleSlashFirewall());
    }

}
