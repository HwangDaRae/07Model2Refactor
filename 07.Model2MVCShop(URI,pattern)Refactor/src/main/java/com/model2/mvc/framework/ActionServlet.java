package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.HttpUtil;


public class ActionServlet extends HttpServlet {
	
	private RequestMapping mapper;

	@Override
	public void init() throws ServletException {
		super.init();
		String resources=getServletConfig().getInitParameter("resources");
		//web.xml에 있는 init 데이터를 mapping해서 map으로 가지고 있는 RequestMapping 인스턴스를 받겠다
		mapper=RequestMapping.getInstance(resources);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[ActionServlet service() start...]");
		
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		//URL => IP:포트번호/context root/a folder/b folder/login.do
		//contextPath => /context root
		//path => /a folder/b folder/login.do
		String path = url.substring(contextPath.length());
		System.out.println(path);
		
		try{
			//path에 매핑된 Action을 가져오겠다
			Action action = mapper.getAction(path);
			//그 Action에 GenericServlet 메소드로 ServletContext의 모든 정보를 넣겠다
			action.setServletContext(getServletContext());
			
			//그 Action에 단일인입점에 있는 request, response를 주겠다
			String resultPage=action.execute(request, response);
			String result=resultPage.substring(resultPage.indexOf(":")+1);
			
			//Navigation:/이동할 파일
			if(resultPage.startsWith("forward:"))
				HttpUtil.forward(request, response, result);
			else
				HttpUtil.redirect(response, result);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println("[ActionServlet service() end...]");
	}
} 