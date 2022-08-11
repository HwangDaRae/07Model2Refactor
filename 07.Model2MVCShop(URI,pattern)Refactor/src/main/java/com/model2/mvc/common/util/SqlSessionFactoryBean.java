package com.model2.mvc.common.util;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/*
 * FileName : MyBatisTestApp04.java
  */
public class SqlSessionFactoryBean {
	
	///main method
	public static SqlSession getSqlSession() throws IOException{
		
		//==> 1. xml metadata 읽는 Stream 생성
		Reader reader = Resources.getResourceAsReader("config/mybatis-config01.xml");
		
		//==> 2. Reader 객체를 이용 xml metadata 에 설정된 각정 정보를 접근, 사용가능한 
		//==>     SqlSession을 생성하는 SqlSessionFactory  instance 생성
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		//==>3. SqlSessionFactory 를 통해 autoCommit true 인 SqlSession instance 생성
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		
		return sqlSession;
	}//end of getSqlSession()
	
	public static void printList(List<Object> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println("<" + ( i+1 ) + "> 번째 회원 : " + list.get(i).toString());
		}
		System.out.println("\n");
	}
}//end of class