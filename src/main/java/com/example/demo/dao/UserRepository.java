package com.example.demo.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

	User findByName(String name);

	@Query(value = "select * from user where name=?1 and password=?2", nativeQuery = true)
	public User userFind(String name, String password);

	@Query(value = "from User where name like :un")
	public List search(@Param("un") String search);

}
