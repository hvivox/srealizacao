package com.hvivox.srealizacao.core.security;

//@Configuration
//@EnableWebSecurity
public class WebSecurityConfig {

	//LIBERAÇÃO DE ACESSO AOS RECURSOS AUTENTICAO E AUTORIZAÇÃO. UTILIZADA PELO SPRING SECURITY SEM KEYCLOAK

 /*   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .anyRequest().permitAll()
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
*/


    /*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("thiago")
				.password(passwordEncoder().encode("123"))
				.roles("ADMIN")
			.and()
			.withUser("joao")
				.password(passwordEncoder().encode("123"))
				.roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic()

			.and()
			.authorizeRequests()
				.antMatchers("/folhas/**").permitAll()
				.anyRequest().authenticated()

			.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			.and()
				.csrf().disable();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	*/

}
