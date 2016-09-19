package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;

/** 
 * Created by wisely on 2015/5/25. 
 */  
@Service  
public class DemoService implements UserDetailsService{  
	@Autowired  
    private UserRepository userRepository; 
	
	@Cacheable(value = "usercache",key = "#id") 
    public User findUser(String id){  
		User user = userRepository.findOne(id);
        System.out.println("无缓存的时候调用这里");  
        return user;
    } 
	@CacheEvict(value="usercache",allEntries=true)// 清空accountCache 缓存  
	public void delAllCache() {  
	      
	}  
    
	@CacheEvict(value="usercache",key="#id")// 清空accountCache 缓存    
	public void delCache(String id) {  
		
	}
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByName(username);
		if(user==null){
			throw new UsernameNotFoundException("用户名不存在");
		}
		return user;
	}
}  