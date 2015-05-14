package com.onet.user.service;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onet.user.dto.UserDTO;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class UserServiceTest {

	@Autowired
	private UserService userService;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Test
	public void testAdd() {
		UserDTO userDto = new UserDTO();
		userDto.setUserName("hick");
		userService.add(userDto);
		System.out.println();
	}

	@Test
	public void testEdit() {
		
		System.out.println(dateFormat.format(null));
	}
	@Test
	public void testQuery() {
		fail("Not yet implemented");
	}
}
