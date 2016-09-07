package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;

/** 
 * Created by wisely on 2015/5/25. 
 */  
@Service  
public class DemoService {  
	@Autowired  
    private UserRepository userRepository; 
	
	@Cacheable(value = "usercache",keyGenerator = "wiselyKeyGenerator") 
    public User findUser(String id){  
		User user = userRepository.findOne(id);
        System.out.println("无缓存的时候调用这里");  
        return user;
    } 
	@CacheEvict(value="usercache",allEntries=true)// 清空accountCache 缓存  
	public void delAllCache() {  
	      
	}  
    
	@CacheEvict(value="usercache",key="#com.example.demo.service.DemoServicefindUser24bd761571da41efb095c5f679e2f5f4")// 清空accountCache 缓存    
	public void delCache() {  
		
	}
}  