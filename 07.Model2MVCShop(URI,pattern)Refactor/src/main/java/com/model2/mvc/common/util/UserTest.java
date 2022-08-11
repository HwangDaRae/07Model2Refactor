package com.model2.mvc.common.util;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserDao;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserDaoImpl;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class UserTest {

	public static void main(String[] args) throws Exception {
		/*
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		// userId, userName, password, role, ssn, phone, addr, email, regDate
		User user = new User("userIdTest01", "userNameTest01", "password", null, "0101011111111", "010-1111-1111", "서울 영등포구", "email@naver.com", null);
		Search search = new Search(2, null, null, 3);
		
		System.out.println(":: 1 : User 회원가입");
		//System.out.println(sqlSession.insert("UserMapper.addUser", user));

		System.out.println(":: 2 : User 한명 찾기");
		//System.out.println(sqlSession.selectOne("UserMapper.findUser", "user01").toString());

		System.out.println(":: 3 : User 총 게시물 수");
		//System.out.println(sqlSession.selectOne("UserMapper.totalCount", search).toString());

		System.out.println(":: 4 : User 현재 페이지 게시물");
		//SqlSessionFactoryBean.printList(sqlSession.selectList("UserMapper.allUser", search));

		User vo = new User("userIdTest01", "userNameUpdate", "passwordUpdate", null, null, "010-1111-1111", "서울 영등포구", "email@naver.com", null);
		System.out.println(":: 5 : User 정보 수정");
		//System.out.println(sqlSession.update("UserMapper.updateUser", vo));
		*/
		
		/*
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		UserDaoImpl userDAO = new UserDaoImpl();
		userDAO.setSqlSession(sqlSession);
		User user = new User("userIdTest01", "userNameTest01", "password", null, "0101011111111", "010-1111-1111", "서울 영등포구", "email@naver.com", null);
		Search search = new Search(2, null, null, 3);
		
		System.out.println(":: 1 : User 회원가입");
		//System.out.println("" + userDAO.addUser(user) );

		System.out.println(":: 2 : User 한명 찾기");
		//System.out.println(userDAO.findUser("user02"));

		System.out.println(":: 3 : User 총 게시물 수");
		//System.out.println(userDAO.totalCount(search));

		System.out.println(":: 4 : User 현재 페이지 게시물");
		//SqlSessionFactoryBean.printList((List)userDAO.allUser(search));

		User vo = new User("userIdTest01", "userNameUpdate", "passwordUpdate", null, null, "010-1111-1111", "서울 영등포구", "email@naver.com", null);
		System.out.println(":: 5 : User 정보 수정");
		//System.out.println(userDAO.updateUser(vo));
		*/
		
		/*
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		UserDaoImpl userDAO = new UserDaoImpl();
		userDAO.setSqlSession(sqlSession);
		UserServiceImpl userService = new UserServiceImpl();
		userService.setUserDAO(userDAO);
		User user = new User("userIdTest01", "userNameTest01", "password", null, "0101011111111", "010-1111-1111", "서울 영등포구", "email@naver.com", null);
		Search search = new Search(2, null, null, 3);
		
		System.out.println(":: 1 : User 회원가입");
		//System.out.println(userService.addUser(user));

		System.out.println(":: 2 : User 한명 찾기");
		System.out.println(userService.findUser("user01"));

		System.out.println(":: 3 : User 총 게시물 수");
		System.out.println(userService.totalCount(search));

		System.out.println(":: 4 : User 현재 페이지 게시물");
		SqlSessionFactoryBean.printList((List)userService.allUser(search));

		User vo = new User("userIdTest01", "userNameUpdate", "passwordUpdate", null, null, "010-1111-1111", "서울 영등포구", "email@naver.com", null);
		System.out.println(":: 5 : User 정보 수정");
		System.out.println(userService.updateUser(vo));
		*/
		
		/*
		ApplicationContext context = new ClassPathXmlApplicationContext( new String[] {"/config/commonservice.xml", "/config/userservice.xml"});
		UserService userService = (UserService)context.getBean("userServiceImpl");
		User user = new User("userIdTest01", "userNameTest01", "password", null, null, null, null, null, null);
		Search search = new Search(2, null, null, 3);
		
		System.out.println(":: 1 : User 회원가입");
		//System.out.println(userService.addUser(user));

		System.out.println(":: 2 : User 한명 찾기");
		System.out.println(userService.findUser("user02"));

		System.out.println(":: 3 : User 총 게시물 수");
		System.out.println(userService.totalCount(search));

		System.out.println(":: 4 : User 현재 페이지 게시물");
		SqlSessionFactoryBean.printList((List)userService.allUser(search));

		User vo = new User("userIdTest01", "userNameUpdate", "passwordUpdate", null, null, null, null, null, null);
		System.out.println(":: 5 : User 정보 수정");
		System.out.println(userService.updateUser(vo));
		*/
		
		///*
		ApplicationContext context = new ClassPathXmlApplicationContext( new String[] {"/config/commonservice.xml"});
		UserService userService = (UserService)context.getBean("UserServiceImpl");
		//User user = new User("userIdTest01", "userNameTest01", "password", null, null, null, null, null, null);
		//Search search = new Search(2, null, null, 3);
		
		System.out.println(":: 1 : User 회원가입");
		//System.out.println(userService.addUser(user));

		System.out.println(":: 2 : User 한명 찾기");
		System.out.println(userService.getUser("user02"));

		System.out.println(":: 3 : User 총 게시물 수");
		//System.out.println(userService.totalCount(search));

		System.out.println(":: 4 : User 현재 페이지 게시물");
		//SqlSessionFactoryBean.printList((List)userService.allUser(search));

		//User vo = new User("userIdTest01", "userNameUpdate", "passwordUpdate", null, null, null, null, null, null);
		System.out.println(":: 5 : User 정보 수정");
		//System.out.println(userService.updateUser(vo));
		//*/
	}

}


