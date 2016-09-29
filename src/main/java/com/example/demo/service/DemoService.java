package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;

/** 
 * Created by wisely on 2015/5/25. 
 */  
@Service  
public class DemoService{  
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
	@Cacheable(value = "usercache", keyGenerator = "wiselyKeyGenerator")
	public Object userList(Object list){
		return list;
	}
	@Cacheable(value="usercache",key="#id")
	public User findOne(String id){
		 System.out.println("从数据库取的数据。。。");
		User user = userRepository.findOne(id);
		return user;
	}
		
}  