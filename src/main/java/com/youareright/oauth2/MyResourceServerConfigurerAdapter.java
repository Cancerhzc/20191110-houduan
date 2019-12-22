package com.youareright.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class MyResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.
		authorizeRequests()
		.antMatchers("/usernamepassword/token").permitAll()
				.antMatchers("/users/**").hasAuthority("/base/user")
				.antMatchers("/menus/**").hasAuthority("/base/menu")
				.antMatchers("/roles/**").hasAuthority("/base/role")
				.antMatchers("/goods/uploadGetTable").hasAuthority("/base/picture")
				.antMatchers("/goods/13").hasAuthority("/base/picturedel")
				.antMatchers("/shelf").hasAuthority("/base/shelves")
				.antMatchers("/classes").hasAuthority("/base/class")
				.antMatchers("/classes/getTable","/shelf/getTable").hasAuthority("/base/compose")
				.antMatchers("/getMergePictures").hasAuthority("/base/mergephoto")
				.antMatchers("/goods/02").hasAuthority("/base/verify")
				.antMatchers("/XMLProcess").hasAuthority("/base/canvas")
				.antMatchers("/operation").hasAuthority("/base/log")
				.antMatchers("/upClasses").hasAuthority("/base/upclass")
				.antMatchers("/sourceDownload").hasAuthority("/base/source")
		.anyRequest()
		.authenticated();
	}

}
