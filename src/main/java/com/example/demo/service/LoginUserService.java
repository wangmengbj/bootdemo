package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;

public class LoginUserService implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByName(username);
		/*
		 * List<GrantedAuthority> authorities = new
		 * ArrayList<GrantedAuthority>(); authorities.add(new
		 * SimpleGrantedAuthority("USER")); return new
		 * org.springframework.security.core.userdetails.User(user.getName(),
		 * user.getPassword(),authorities);
		 */
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		return user;
	}

}
