package com.model2.mvc.view.cart;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.CookieInfo;
import com.model2.mvc.service.cart.CartService;
import com.model2.mvc.service.domain.Cart;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/cart/*")
public class CartController {
	
	@Autowired
	@Qualifier("cartServiceImpl")
	CartService cartServiceImpl;
	
	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productServiceImpl;
	
	public CartController() {
		System.out.println(getClass() + " default Constructor()]");
	}

	@RequestMapping(value = "addCart", method = RequestMethod.POST )
	public ModelAndView addCart(HttpServletRequest request, HttpServletResponse response, @RequestParam("prod_no") int prod_no, @RequestParam("amount") int amount, HttpSession session, Model model) throws Exception {
		System.out.println("/cart/addCart : POST");
		
		Product product = productServiceImpl.getProduct(prod_no);
		// getProduct.jsp에서 장바구니 버튼 클릭시 장바구니에 추가된다.
		Cart cart = new Cart(prod_no, ((User)session.getAttribute("user")).getUserId(), product.getFileName(), product.getProdName(), product.getProdDetail(), amount, product.getPrice(), product.getAmount());		
		System.out.println("AddCartAction cart : " + cart.toString());
		
		String cookieValue = "";

		//비회원 : 쿠키에 넣은 상품번호, 수량 가져온다
		if(((User)session.getAttribute("user")).getUserId().equals("non-member")) {

			List<Cart> cartList = new ArrayList<Cart>();
			List<CookieInfo> returnList = new ArrayList<CookieInfo>();
			
			Cookie[] cookies = request.getCookies();
			
			if(cookies != null && cookies.length > 0) {
				for (int i = 0; i < cookies.length; i++) {
					if(cookies[i].getName().equals("prodInfoCookie")) {
						// ex) 10001:12,10007:5,10013:31
						cookieValue = URLDecoder.decode(cookies[i].getValue());
						System.out.println("기존에 쿠키에 있는 cookieValue : " + cookieValue);

						
						// 첫번째 장바구니 담는 데이터 : 기존 데이터가 없으니 바로 추가
						if(cookieValue.trim().length() == 0) {
							System.out.println("1.장바구니에 상품이 하나도 없을 때");
							CookieInfo c = new CookieInfo(prod_no, amount);
							returnList.add(c);
							cookieValue = prod_no + ":" + amount;
							break;
						}

						// 두번째 이후 장바구니 담는 데이터 => list에 파싱해서 넣는다
						System.out.println("2.장바구니에 데이터가 하나라도 있을 때 List에 담기");
						int secondCheck = cookieValue.indexOf(",");

						// list에 기존에 쿠키를 파싱해 넣는다
						if(secondCheck == -1) {
							//장바구니에 상품이 하나만 있을 때
							String[] arr = cookieValue.split(":");
							CookieInfo c = new CookieInfo(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
							returnList.add(c);
						}else {
							String[] arr = cookieValue.split(",");
							for (int j = 0; j < arr.length; j++) {
								String[] subArr = arr[j].split(":");
								CookieInfo c = new CookieInfo(Integer.parseInt(subArr[0]), Integer.parseInt(subArr[1]));
								returnList.add(c);
							}
						}												
						System.out.println("파싱해서 담은 list : " + returnList);
						
						// 현재 담을 상품의 번호가 list에 있는 상품번호와 같다면 수량만 추가
						boolean isSameProdNo = false;
						for (int j = 0; j < returnList.size(); j++) {
							if(returnList.get(j).getProd_no() == prod_no) {
								isSameProdNo = true;
								returnList.get(j).setAmount(returnList.get(j).getAmount() + amount);
							}
						}
						
						if(!isSameProdNo) {
							returnList.add(new CookieInfo(prod_no, amount));
						}

						// 수량 변경한 list를 다시 String으로 변환
						cookieValue = "";
						for (int j = 0; j < returnList.size(); j++) {
							cookieValue += returnList.get(j).getProd_no() + ":" + returnList.get(j).getAmount() + ",";
							if(j==returnList.size()) {
								cookieValue = cookieValue.substring(0, cookieValue.length());
							}
						}
						
					}// end of prodInfoCookie 쿠키가 있을 때
				}// end of cookie가 있을 때 for문
				
				// 쿠키에 상품정보 추가
				System.out.println("response한 쿠키 데이터 cookieValue : " + cookieValue);
				Cookie cookie = new Cookie("prodInfoCookie", URLEncoder.encode(cookieValue));
				cookie.setMaxAge(24*60*60);
				response.addCookie(cookie);
				

				// 쿠키에 저장된 데이터를 list 뿌린다				
				for (int y = 0; y < returnList.size(); y++) {
					Product productVO = new Product();					
					productVO = productServiceImpl.getProduct(returnList.get(y).getProd_no());
					Cart c = new Cart(productVO.getProdNo(), "", productVO.getFileName(), productVO.getProdName(),
							productVO.getProdDetail(), returnList.get(y).getAmount(), productVO.getPrice(), productVO.getAmount());
					cartList.add(c);
				}//end of cookieValueArr 장바구니에서 뿌려줄 list 가져오기
				
			}
			
			for (int j = 0; j < cartList.size(); j++) {
				System.out.println("===========> " + cartList.get(j).toString());
			}
			
			model.addAttribute("list", cartList);
			model.addAttribute("count", cartList.size());
		}else {
			// 회원 : 같은 상품이 있는지 비교할 리스트
			List<Cart> cartList = cartServiceImpl.getCartList( ((User)session.getAttribute("user")).getUserId() );
			
			//장바구니 전부를 가져와서 상품번호가 같다면 수량추가
			boolean isProdNo = false;
			for (int i = 0; i < cartList.size(); i++) {
				if(cartList.get(i).getProd_no() == prod_no){
					isProdNo = true;
					//장바구니에 상품이 있다면 => 수량 업데이트
					cart.setAmount(cartList.get(i).getAmount() + amount);
					cartServiceImpl.updateAmount(cart);
				}
			}
			
			if(!isProdNo) {
				//장바구니에 상품이 없다면 => insert
				cartServiceImpl.insertCart(cart);
			}
			//jsp에서 출력할 list 장바구니 list 가져온다
			cartList = cartServiceImpl.getCartList( ((User)session.getAttribute("user")).getUserId() );
			
			model.addAttribute("list", cartList);
			model.addAttribute("count", cartList.size());
		}
		
		return new ModelAndView("/cart/listCart.jsp", "model", model);
	}
	
	
	
	
	
	
	
	
	/*
	@RequestMapping(value = "addCart", method = RequestMethod.POST )
	public ModelAndView addCart(HttpServletRequest request, HttpServletResponse response, @RequestParam("prod_no") int prod_no, @RequestParam("amount") int amount, HttpSession session, Model model) throws Exception {
		System.out.println("/cart/addCart : POST");
		List<Cart> list = new ArrayList<Cart>();
		
		Product product = productServiceImpl.getProduct(prod_no);
		// getProduct.jsp에서 장바구니 버튼 클릭시 장바구니에 추가된다.
		Cart cart = new Cart(prod_no, ((User)session.getAttribute("user")).getUserId(), product.getFileName(), product.getProdName(),
				product.getProdDetail(), amount, product.getPrice(), product.getAmount());		
		System.out.println("AddCartAction cart : " + cart.toString());
		
		int index = 0;
		String cookieValue = "";

		//비회원 : 쿠키에 넣은 상품번호, 수량 가져온다
		if(((User)session.getAttribute("user")).getUserId().equals("non-member")) {
			Cookie[] cookies = request.getCookies();
			
			if(cookies != null && cookies.length > 0) {
				for (int i = 0; i < cookies.length; i++) {
					if(cookies[i].getName().equals("prodInfoCookie")) {
						// 같은 상품번호 있다 => 수량 plus, 같은 상품번호 없다 => 수량 추가
						// 10001:12,10007:5,10013:31
						cookieValue = URLDecoder.decode(cookies[i].getValue());
						System.out.println("기존 쿠키에 있는 cookieValue : " + cookieValue);

						index = i;						
						int lastInext = URLDecoder.decode(cookies[i].getValue()).lastIndexOf(",");
						System.out.println("lastInext : " + lastInext);
						
						// 두번째 상품 담는다
						if(lastInext != -1) {
							cookieValue = URLDecoder.decode(cookies[i].getValue()).substring(0, lastInext);
							//10001:3,10024:15,10017:11
						}
						
						System.out.println("cookieValue : " + cookieValue);
						//10001:3,10024:15,10017:11
						
						int setting = cookieValue.indexOf(prod_no+"");
						System.out.println(setting);
						
						boolean isProdNo = (cookieValue.indexOf(prod_no+"") != -1);
						System.out.println("쿠키에 현재 담을 상품번호가 있나? : " + isProdNo);
						if(isProdNo) {
							System.out.println("============ 쿠키에 상품번호가 있다");
							
							int indexProdNoStart = cookieValue.indexOf(prod_no+"");
							System.out.println("indexProdNoStart : " + indexProdNoStart);
							String valueSubStr = cookieValue.substring(indexProdNoStart);
							System.out.println("valueSubStr : " + valueSubStr);
							//10001:3,10024:15,10017:11
							//10024:15,10017:11
							//10017:11
							int indexSeper = valueSubStr.indexOf(",");
							System.out.println("indexSeper : " + indexSeper);
							
							// -1일때 가장 마지막에 있는 값이다. cookieValue의 마지막이 상품번호일지 수량일지 정하자 결국 for문 끝나면 cookeValue의 마지막 값은 수량이어야한다
							if(indexSeper == -1) {
								int temp = cookieValue.lastIndexOf(":");
								System.out.println("temp : " + temp);
								String cookieValueBefore = cookieValue.substring(0,temp);
								//10001:3,10024:15,10017
								System.out.println("cookieValueBefore : " + cookieValueBefore);
								System.out.println(cookieValue.substring(temp+1));
								int beforeAmount = Integer.parseInt(cookieValue.substring(temp+1));
								//11
								System.out.println("beforeAmount : " + beforeAmount);
								System.out.println(beforeAmount + amount);
								if(indexProdNoStart > 0) {
									cookieValue = cookieValue.substring(indexProdNoStart) + cookieValueBefore.substring(0, 4) + ":" + (beforeAmount + amount);
								}else {
									cookieValue = cookieValueBefore.substring(0, 4) + ":" + (beforeAmount + amount);
								}
								//10001:3,10024:15,10017:12
								System.out.println("같은 상품번호 있었을때 cookieValue : " + cookieValue);
							}else {
								//수량을 치환해야한다
								int temp = valueSubStr.indexOf(",");
								System.out.println("temp : " + temp);
								String cookieValueBefore = valueSubStr.substring(0,temp);
								System.out.println("cookieValueBefore : " + cookieValueBefore);
								//10024:15
								String[] beforeValue = cookieValueBefore.split(":");
								String afterValue = beforeValue[0] + ":" + (Integer.parseInt(beforeValue[1]) + amount);
								System.out.println("afterValue : " + afterValue);
								
								System.out.println("cookieValue : " + cookieValue);
								cookieValue = "";
								if(indexProdNoStart != 0) {
									cookieValue += cookieValue.substring(0, indexProdNoStart) + ":";
								}
								cookieValue += afterValue + "," + valueSubStr.substring(indexSeper+1);
								System.out.println("cookieValue : " + cookieValue);
							}
						}else {
							// 같은 상품번호가 없으때는 그냥 추가
							System.out.println("============ 쿠키에 상품번호가 없다");
							cookieValue = cookieValue + "," + prod_no + ":" + amount;
							System.out.println("cookieValue : " + cookieValue);
						}
					}// end of prodInfoCookie 쿠키가 있을 때
				}// end of cookie가 있을 때 for문

				System.out.println("============ list뿌리기");

				
				// 새로 담는 상품번호와 수량을 list 뿌린다
				System.out.println(cookieValue.trim().length());
				if(cookieValue.trim().length() == 0) {
					cookieValue = prod_no + ":" + amount;
					System.out.println("새로 담은 쿠키 데이터 : " + cookieValue);
				}
				
				
				// 쿠키에 상품정보 추가				
				Cookie cookie = new Cookie("prodInfoCookie", URLEncoder.encode(cookieValue));
				cookie.setMaxAge(24*60*60);
				response.addCookie(cookie);

				///*
				System.out.println("============ 쿠키에 상품번호 가져와서 장바구니 list 출력");

				// 쿠키에서 상품번호와 수량 빼서 장바구니에 뿌리기
				System.out.println("response한 쿠키 데이터 cookieValue : " + cookieValue);
				//10002:3,10031:12,10013:7
				

				// 쿠키에 저장된 데이터를 list 뿌린다
				String[] cookieValueArr = cookieValue.split(",");
				//10002:3
				//10031:12
				//10013:7
				String[] prodNoAndAmount = new String[2];
				System.out.println("prodNoAndAmount.length : " + prodNoAndAmount.length);
				System.out.println("cookieValueArr.length : " + cookieValueArr.length);
				
				for (int y = 0; y < cookieValueArr.length; y++) {
					Product productVO = new Product();
					prodNoAndAmount = cookieValueArr[y].split(":");
					//10002
					//3
					System.out.println("prodNoAndAmount[0] : " + prodNoAndAmount[0]);
					System.out.println("prodNoAndAmount[1] : " + prodNoAndAmount[1]);
					
					productVO = productServiceImpl.getProduct(Integer.parseInt(prodNoAndAmount[0]));
					//productVO.setAmount(Integer.parseInt(prodNoAndAmount[1]));
					Cart c = new Cart(productVO.getProdNo(), "", productVO.getFileName(), productVO.getProdName(),
							productVO.getProdDetail(), Integer.parseInt(prodNoAndAmount[1]), productVO.getPrice(), productVO.getAmount());
					list.add(c);
					System.out.println(list.get(y).toString());
				}//end of cookieValueArr 장바구니에서 뿌려줄 list 가져오기
				
			}
			
			for (int j = 0; j < list.size(); j++) {
				System.out.println("===========> " + list.get(j).toString());
			}
			
			model.addAttribute("list", list);
			model.addAttribute("count", list.size());
		}else {
			// 회원 : 같은 상품이 있는지 비교할 리스트
			List<Cart> cartList = cartServiceImpl.getCartList( ((User)session.getAttribute("user")).getUserId() );
			
			//장바구니 전부를 가져와서 상품번호가 같다면 수량추가
			boolean isProdNo = false;
			for (int i = 0; i < cartList.size(); i++) {
				if(cartList.get(i).getProd_no() == prod_no){
					isProdNo = true;
					//장바구니에 상품이 있다면 => 수량 업데이트
					cart.setAmount(cartList.get(i).getAmount() + amount);
					cartServiceImpl.updateAmount(cart);
				}
			}
			
			if(!isProdNo) {
				//장바구니에 상품이 없다면 => insert
				cartServiceImpl.insertCart(cart);
			}
			//jsp에서 출력할 list 장바구니 list 가져온다
			cartList = cartServiceImpl.getCartList( ((User)session.getAttribute("user")).getUserId() );
			
			model.addAttribute("list", cartList);
			model.addAttribute("count", cartList.size());
		}
		
		return new ModelAndView("/cart/listCart.jsp", "model", model);
	}
	*/

	@RequestMapping(value = "deliveryCart", method = RequestMethod.POST)
	public ModelAndView deliveryCart(@RequestParam("addPurchaseCheckBox") int[] allProdNo, @RequestParam("deleteCheckBox") int[] checkProdNo, @RequestParam("amount") int[] allAmount,
			HttpSession session, User user, Model model) throws Exception {
		System.out.println("/cart/deliveryCart : POST");
		//allAmount => 모든 수량, allCheckProdNo => 모든 상품번호, checkedProdNo => 체크된 상품번호
		//구매 페이지에서 받은 모든 체크박스와 체크된 체크박스를 비교해서 index를 알아낸다 그 index에 맞는 상품번호, 수량을 보낸다
		List<Purchase> purList = new ArrayList<Purchase>();
		Product productVO = new Product();

		for (int i = 0; i < allProdNo.length; i++) {
			System.out.println("for문1 : allProdNo[" + i + "] : " + allProdNo[i]);
			for (int j = 0; j < checkProdNo.length; j++) {
				System.out.println("for문2 : checkProdNo[" + j + "] : " + checkProdNo[j]);
				if(allProdNo[i] == checkProdNo[j]) {
					System.out.println("체크");
					Purchase purchaseVO = new Purchase();
					System.out.println("상품번호 : " + checkProdNo[j]);
					System.out.println("수량 : " + allAmount[i]);
					
					//구매할 상품정보
					productVO = productServiceImpl.getProduct(checkProdNo[j]);
					purchaseVO.setPurchaseProd(productVO);
					
					//구매한 유저정보
					System.out.println(user);
					String userId = ((User)session.getAttribute("user")).getUserId();
					System.out.println(userId);
					user.setUserId( userId );
					purchaseVO.setBuyer(user);
					
					//구매한 상품의 수량정보
					purchaseVO.setAmount(allAmount[i]);
					System.out.println("purchaseVO : " + purchaseVO);
					
					purList.add(purchaseVO);
					System.out.println("purList.toString() : " + purList.toString());
				}
			}
		}
		
		System.out.println("여기");
		
		for (int i = 0; i < purList.size(); i++) {
			System.out.println("purList : " + purList.get(i));
		}
		
		model.addAttribute("purList", purList);
		model.addAttribute("count", purList.size());
		
		return new ModelAndView("/cart/deliveryCart.jsp", "model", model);
	}

	@RequestMapping(value = "deleteCart", method = RequestMethod.POST)
	public ModelAndView deleteCart( @RequestParam("deleteCheckBox") int[] deleteArr, HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		System.out.println("/cart/deleteCart : POST");

		ModelAndView modelAndView = new ModelAndView();
		User user = (User)session.getAttribute("user");
		
		//1개 or 여러개 삭제시
		for (int i = 0; i < deleteArr.length; i++) {
			System.out.println("삭제할 상품 번호 : " + deleteArr[i]);
		}
		
		if(user == null || user.getUserId().equals("non-member")) {
			// 비회원이라면
			String cookieValue = "";
			
			Cookie[] cookies = request.getCookies();
			if(cookies != null && cookies.length > 0) {
				for (int i = 0; i < cookies.length; i++) {
					if(cookies[i].getName().equals("prodInfoCookie")) {
						cookieValue = URLDecoder.decode(cookies[i].getValue());
						System.out.println("cookieValue : " + cookieValue);
					}
				}
			}

			// 10002:3,10000:2,10001:1
			// 16
			for (int i = 0; i < deleteArr.length; i++) {
				int returnIndexStart = cookieValue.indexOf(deleteArr[i]+"");
				System.out.println("return된 index : " + returnIndexStart);
				int returnIndexEnd = cookieValue.substring(returnIndexStart).indexOf(",");
				System.out.println("returnIndexEnd : " + returnIndexEnd);
				if(returnIndexEnd == -1) {
					cookieValue = cookieValue.substring(0, returnIndexStart-1);
				}else {
					if(returnIndexStart == 0) {
						cookieValue = cookieValue.substring(returnIndexStart + returnIndexEnd + 1);
					}else {
						cookieValue = cookieValue.substring(0, returnIndexStart-1) + cookieValue.substring(returnIndexStart + returnIndexEnd);
					}
				}
				System.out.println("cookieValue : " + cookieValue);
			}
			
			Cookie c = new Cookie("prodInfoCookie", URLEncoder.encode(cookieValue));
			c.setMaxAge(24*60*60);
			response.addCookie(c);

			List<Cart> cartList = new ArrayList<Cart>();
			String[] prodNoAndAmount = cookieValue.split(",");
			//10001:1
			//10022:31
			//10013:12

			String[] cookieInfo = new String[prodNoAndAmount.length];
			for (int i = 0; i < prodNoAndAmount.length; i++) {
				cookieInfo = prodNoAndAmount[i].split(":");
				//10001
				//1
				Product productVO = new Product();
				productVO = productServiceImpl.getProduct(Integer.parseInt(cookieInfo[0]));
				Cart cart = new Cart(productVO.getProdNo(), "", productVO.getFileName(), productVO.getProdName(),
						productVO.getProdDetail(), Integer.parseInt(cookieInfo[1]), productVO.getPrice(), productVO.getAmount());
				cartList.add(cart);
				System.out.println(cookieInfo[0]);
				System.out.println(cookieInfo[1]);
			}
			
			for (int i = 0; i < cartList.size(); i++) {
				System.out.println("cartList : " + cartList.get(i).toString());
			}
			
			model.addAttribute("list", cartList);
			model.addAttribute("count", cartList.size());
			modelAndView.setViewName("/cart/listCart.jsp");
			
		}else {
			//회원이라면
			Map<String, Object> map = new HashMap<String, Object>();
			
			//삭제할 상품번호와 user_id를 map에 넣는다
			map.put("deleteArray", deleteArr);
			map.put("user_id", ( (User)session.getAttribute("user") ).getUserId() );

			//장바구니에서 상품을 삭제하고 삭제한 list를 가져온다
			cartServiceImpl.deleteCart(map);
			List<Cart> list = cartServiceImpl.getCartList( ( (User)session.getAttribute("user") ).getUserId() );
			
			model.addAttribute("list", list);
			//count : 게시물 수, listCart.jsp에서 count>0일때 for문으로 list출력
			model.addAttribute("count", list.size());

			modelAndView.setViewName("/cart/listCart.jsp");
		}
		
		modelAndView.addObject("model", model);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "listCart", method = RequestMethod.GET)
	public ModelAndView listCart(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws Exception {
		System.out.println("/cart/listCart : GET");
		//left.jsp 레이어에 있는 장바구니 <a href 클릭시 유저에 맞는 장바구니 리스트로 이동
		User user = (User)session.getAttribute("user");
		System.out.println(user.getUserId());
		
		if(user.getUserId().equals("non-member")) {
			System.out.println("여기는 user.getUserId == non-member");
			
			Cookie[] cookies = request.getCookies();
			
			List<Cart> cartList = new ArrayList<Cart>();
			int count = 0;
			int index = 0;
			String cookieValue = "";
			//String[] prodNoAndAmount = null;
			
			if(cookies != null && cookies.length > 0) {
				
				// prodInfoCookie 쿠키를 찾는다
				for (int i = 0; i < cookies.length; i++) {
					System.out.println(cookies[i].getName());
					if(cookies[i].getName().equals("prodInfoCookie")) {
						//상품번호와 수량에 맞는 상품정보를 가져온다
						System.out.println("여기로 오나?");
						index = i;
						cookieValue = URLDecoder.decode(cookies[i].getValue());
					}
				}

				System.out.println("index : " + index);				
				System.out.println("prodInfoCookie로 찾은 cookie value : " + cookieValue);
				
				// prodInfoCookie 쿠키가 있을때만 파싱한다
				if(cookieValue.length() > 1) {
					String[] prodNoAndAmount = URLDecoder.decode(cookies[index].getValue()).split(",");
					//10001:1
					//10022:31
					//10013:12

					String[] cookieInfo = new String[prodNoAndAmount.length];
					for (int i = 0; i < prodNoAndAmount.length; i++) {
						cookieInfo = prodNoAndAmount[i].split(":");
						Product productVO = new Product();
						productVO = productServiceImpl.getProduct(Integer.parseInt(cookieInfo[0]));
						Cart cart = new Cart(productVO.getProdNo(), "", productVO.getFileName(), productVO.getProdName(),
								productVO.getProdDetail(), Integer.parseInt(cookieInfo[1]), productVO.getPrice(), productVO.getAmount());
						cartList.add(cart);
						System.out.println(cookieInfo[0]);
						System.out.println(cookieInfo[1]);
					}
					
					count = cartList.size();
					
					for (int i = 0; i < cartList.size(); i++) {
						System.out.println("cartList : " + cartList.get(i).toString());
					}
				}
				
			}//end of if cookies
			
			model.addAttribute("list", cartList);
			model.addAttribute("count", count);
		}else {
			List<Cart> list = new ArrayList<Cart>();
			System.out.println("여기는 회원 장바구니 리스트");
			list = cartServiceImpl.getCartList(user.getUserId());
			
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).toString());
			}
			
			model.addAttribute("list", list);
			//count : 게시물 수, listCart.jsp에서 count>0일때 for문으로 list출력
			model.addAttribute("count", list.size());
		}
		
		return new ModelAndView("/cart/listCart.jsp", "model", model);
	}
	
}
