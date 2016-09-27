package com.example.demo.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;
import com.example.demo.service.DemoService;

@Controller  
public class Example {  
	@Autowired  
    private UserRepository userRepository; 
	@Autowired  
    DemoService demoService; 
	/*@Autowired  
    private RedisUtil redisUtil; */
	
	// 从 application.properties 中读取配置，如取不到默认值为Hello Shanhy
    @Value("${application.hello:Hello Angel}")

    private String hello;
      
    @RequestMapping("/hello/{myName}")  
    String index(@PathVariable String myName) {  
        return "Hello "+myName+"!!!";  
    } 
   
   @RequestMapping("/")
   public String index() {
       return "index";
   }

   @RequestMapping("/hello")
   public String hello() {
       return "hello";
   }

   @RequestMapping(value = "/login", method = RequestMethod.GET)
   public String login() {
       return "login";
   }
   
   @RequestMapping("/submit")
   public String submit(HttpServletRequest arg0,Model model){
	   String name = arg0.getParameter("name");	
	   String password = arg0.getParameter("password");	
	   User user = userRepository.userFind(name,password);
	   if(user !=null){
		   java.util.List<User> list = userRepository.findAll();
		   model.addAttribute("list", list);
		   return "userList";
		   
	   }else {
		   return "submit";
	   }
   }
   
   @RequestMapping("/findUser")
   //@Cacheable(value = "reportcache", keyGenerator = "wiselyKeyGenerator")
   public void findUser(){
	 User user = userRepository.findOne("24bd761571da41efb095c5f679e2f5f4");
	 System.out.println(user+"||"+user.getName());
   }
   
   @RequestMapping("/delUserCache")
   //@CacheEvict(value="reportcache",key="reportcache")
   public void delUserCache(){
	   
   }
   
   @RequestMapping("/saveUser")
   public String saveUser(HttpServletRequest arg0,HttpServletResponse arg1,Model model){
	   String id = arg0.getParameter("id");
	   String name = arg0.getParameter("name");	
	   String age = arg0.getParameter("age");
	   String address = arg0.getParameter("address");
	   User user = new User();
	   if("".equals(id)){
		   String uuid = this.getUUID();
		   user.setId(uuid);
	   }else {
		   user.setId(id);
	   }
	   if(!"".equals(age)){user.setAge(Integer.parseInt(age));}
	   user.setName(name);
	   user.setAddress(address);
	   userRepository.save(user);
	   
	   java.util.List<User> list = userRepository.findAll();
	   model.addAttribute("list", list);
	   return "userList";
   }                    
   
   @RequestMapping("/delUser")
   public String delUser(HttpServletRequest arg0,HttpServletResponse arg1,Model model){
	   //String id = (String) arg0.getAttribute("id");
	   String id = arg0.getParameter("id");
	   userRepository.delete(id);
	   java.util.List<User> list = userRepository.findAll();
	   model.addAttribute("list", list);
	   return"userList";
   }
   
   @RequestMapping("/userList")
   //@Cacheable(value = "userListCache", keyGenerator = "wiselyKeyGenerator")
   public String userList(HttpServletRequest arg0,Model model){
	   //System.out.println("没有从缓存取数据。。。");
	   String pageStr = arg0.getParameter("page");
	   int page = 0;
	   if(!"".equals(pageStr) && pageStr !=null){
		   page = Integer.parseInt(pageStr)-1;
	   }
	   //int page = 0;
	   int pageSize = 3;
	   Sort sort = new Sort(Direction.DESC, "id");
	   Pageable pageable = new PageRequest(page, pageSize,sort);
	   Object list = userRepository.findAll(pageable);
	   
	   model.addAttribute("list", list);
	   return "userList";
   }
   @RequestMapping("/addUser")
   public String addUser(Model model){
	   User user = new User();
	   model.addAttribute("user", user);
	   return "addUser";
   }
   @RequestMapping("/editUser")
   public String editUser(HttpServletRequest arg0,HttpServletResponse arg1,Model model){
	   String id = arg0.getParameter("id");
	   User user = userRepository.findOne(id);
	   model.addAttribute("user",user);
	   return "addUser";
   }
   @RequestMapping("/search")
   public String search(HttpServletRequest arg0,Model model){
	   String search = arg0.getParameter("search");
	   java.util.List<User> list = userRepository.search(search);
	   model.addAttribute("list", list);
	   return "userList";
   }
   
   public static String getUUID(){    
       String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");    
       return uuid;    
   }
   
   @RequestMapping("/putCache")  
   @ResponseBody  
   public void putCache(){  
	   String id = "24bd761571da41efb095c5f679e2f5f4";
	   User user = demoService.findUser(id);  
       //System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功"+user.getName());  
   }  
   
   @RequestMapping("/delAllCache")
   @ResponseBody
   public void delAllCache(){
	   demoService.delAllCache();
   }
   
   @RequestMapping("/delCache")
   @ResponseBody
   public void delCache(){
	   String id = "24bd761571da41efb095c5f679e2f5f4";
	   demoService.delCache(id);
   }
}  