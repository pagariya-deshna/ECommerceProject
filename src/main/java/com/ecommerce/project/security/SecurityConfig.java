package com.ecommerce.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JWTAuthEntryPoint authEntryPoint;
	
	@Autowired
	private JWTGenerator jwtGenerator;
	
	@Autowired
	private CustomUserDetailsService customUserDetailService;

	/**
	 * @param customUserDetailService
	 */
	public SecurityConfig(CustomUserDetailsService customUserDetailService,JWTAuthEntryPoint authEntryPoint) {
		super();
		this.customUserDetailService = customUserDetailService;
		this.authEntryPoint=authEntryPoint;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
		.exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
       .authorizeHttpRequests(auth -> auth
				.requestMatchers(new AntPathRequestMatcher("/swagger-ui/**"),
						new AntPathRequestMatcher("/v3/api-docs/**"),
						new AntPathRequestMatcher("/configuration/ui"),
						new AntPathRequestMatcher("/swagger-resources/**"),
						new AntPathRequestMatcher("/configuration/security"),
						new AntPathRequestMatcher("/webjars/**"),
						new AntPathRequestMatcher("/registerNewCustomer"),
						new AntPathRequestMatcher("/login"),
						new AntPathRequestMatcher("/resendOTP/{emailId}"),
						new AntPathRequestMatcher("/verify/{emailId}"),
						new AntPathRequestMatcher("/changePassword"),
						new AntPathRequestMatcher("/forgotPassword"),
						new AntPathRequestMatcher("/updateCustomer/{emailId}"),
						new AntPathRequestMatcher("/deleteCustomer/{id}"),
//						new AntPathRequestMatcher("/getAllCustomers"),
						new AntPathRequestMatcher("/addProduct"),
						new AntPathRequestMatcher("/addBrands"),
						new AntPathRequestMatcher("/addProductImage"),
						new AntPathRequestMatcher("/updateProduct/{id}"),
						new AntPathRequestMatcher("/deleteProduct/{id}"),
						new AntPathRequestMatcher("/getAllProducts"),
						new AntPathRequestMatcher("/addToCart"),
						new AntPathRequestMatcher("/deleteFromCart/{id}")).permitAll()
				.anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults());
		http.addFilterBefore(jwtAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

//	@Bean
//	public UserDetailsService users() {
//		UserDetails admin = User.builder().username("admin").password("password").roles("ADMIN").build();
//		UserDetails user = User.builder().username("user").password("password").roles("USER").build();
//
//		return new InMemoryUserDetailsManager(admin,user);
//	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public JWTAuthenticationFilter jwtAuthenticationFilter() {
		//check here once
//		return new JWTAuthenticationFilter(jwtGenerator, customUserDetailService);
		return new JWTAuthenticationFilter();
	}

}








//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.withUsername("user")
//            .password(passwordEncoder.encode("password"))
//            .roles("USER")
//            .build();
//
//        UserDetails admin = User.withUsername("admin")
//            .password(passwordEncoder.encode("admin"))
//            .roles("USER", "ADMIN")
//            .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http.authorizeHttpRequests(request -> request.anyRequest()
//                .authenticated())
//        		.httpBasic(Customizer.withDefaults())
//            .build();
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        return encoder;
//    }
