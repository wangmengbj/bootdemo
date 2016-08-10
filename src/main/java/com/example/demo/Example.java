package com.example.demo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;
import com.fasterxml.jackson.core.sym.Name;
import com.fasterxml.jackson.databind.Module;

@Controller  
public class Example {  
	@Autowired  
    private UserRepository userRepository; 
	
	// 从 application.properties 中读取配置，如取不到默认值为Hello Shanhy
    @Value("${application.hello:Hello Angel}")

    private String hello;
      
    @RequestMapping("/hello/{myName}")  
    String index(@PathVariable String myName) {  
        return "Hello "+myName+"!!!";  
    } 
   
   @RequestMapping("/login")
   public String login(Model model){
       //model.addAttribute("name","王猛");
       return "login";
   }
   
   @RequestMapping("/submit")
   public String submit(Model model){
	   model.addAttribute("name",hello);
	   return "index";
   }
   
   @RequestMapping("/findUser")
   public void findUser(){
	 User user = userRepository.findOne("65e9bc7156f7454a9172157b4fab6317");
	 System.out.println(user+"||"+user.getName());
   }
   
   @RequestMapping("/saveUser")
   public String saveUser(HttpServletRequest arg0,HttpServletResponse arg1,Model model){
	   String id = arg0.getParameter("id");
	   String name = arg0.getParameter("name");	
	   String age = arg0.getParameter("age");
	   User user = new User();
	   if("".equals(id)){
		   String uuid = this.getUUID();
		   user.setId(uuid);
	   }else {
		   user.setId(id);
	   }
	   if(!"".equals(age)){user.setAge(Integer.parseInt(age));}
	   user.setName(name);
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
   public String userList(HttpServletRequest arg0,Model model){
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
   public static String getUUID(){    
       String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");    
       return uuid;    
   }
   
}  