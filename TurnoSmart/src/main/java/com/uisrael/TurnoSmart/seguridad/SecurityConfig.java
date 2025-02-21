package com.uisrael.TurnoSmart.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import com.uisrael.TurnoSmart.servicio.impl.UserDetailsServicioImpl;

import jakarta.servlet.http.HttpServletResponse;

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
	        .csrf().disable()
	        .authorizeHttpRequests(authorize -> authorize
	            .requestMatchers("/docente/**").hasAuthority("DOCENTE")
	            .requestMatchers("/representante/**").hasAuthority("REPRESENTANTE")
	            .requestMatchers("/login").permitAll() // Permitimos acceso al login
	            .requestMatchers("/assets/**", "/dist/**", "/src/**", "/webjars/**").permitAll()
	            .anyRequest().authenticated()
	        )
	        .formLogin(form -> form
	            .loginPage("/login") // Página de inicio de sesión para la web
	            .successHandler((request, response, authentication) -> {
	                // Obtener detalles del usuario autenticado
	                UserDetailsServicioImpl.UserDetailsImpl userDetails = 
	                    (UserDetailsServicioImpl.UserDetailsImpl) authentication.getPrincipal();

	                int idRepresentante = userDetails.getIdRepresentante(); // 🔹 Obtener id_representante

	                // 🔹 Imprimir roles en la consola
	                System.out.println("🔹 Roles del usuario autenticado: " + authentication.getAuthorities());

	                // Obtener los roles correctamente
	                String role = authentication.getAuthorities().stream()
	                    .map(GrantedAuthority::getAuthority)
	                    .findFirst()
	                    .orElse("UNKNOWN"); // Si no hay roles, devuelve "UNKNOWN"

	                // Devuelve JSON si la solicitud viene desde Postman o la app móvil
	                String requestedWith = request.getHeader("X-Requested-With");
	                if ("XMLHttpRequest".equals(requestedWith) || request.getHeader("User-Agent").contains("Postman")) {
	                    response.setContentType("application/json");
	                    response.setCharacterEncoding("UTF-8");
	                    response.getWriter().write("{\"id_representante\": " + idRepresentante + 
	                        ", \"message\": \"Login exitoso\", \"role\": \"" + role + "\"}");
	                } else {
	                    response.sendRedirect("/default");
	                }
	            })
	            .failureHandler((request, response, exception) -> {
	                response.setContentType("application/json");
	                response.setCharacterEncoding("UTF-8");
	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                response.getWriter().write("{\"error\": \"Login fallido\"}");
	            })
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .logoutUrl("/logout")
	            .logoutSuccessUrl("/login?logout")
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
