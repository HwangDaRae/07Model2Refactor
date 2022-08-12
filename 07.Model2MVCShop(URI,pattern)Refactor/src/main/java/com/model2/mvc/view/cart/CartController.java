package com.model2.mvc.view.cart;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	public ModelAndView addCart(HttpServletRequest request,
								HttpServletResponse response,
								@RequestParam("prod_no") int prod_no,
								@RequestParam("amount") int amount,
								HttpSession session,
								Model model) throws Exception {
		System.out.println("/cart/addCart : POST");
		
		Product product = productServiceImpl.getProduct(prod_no);
		// getProduct.jsp에서 장바구니 버튼 클릭시 장바구니에 추가된다.
		Cart cart = new Cart(prod_no, ((User)session.getAttribute("user")).getUserId(), product.getFileName(), product.getProdName(), product.getProdDetail(), amount, product.getPrice(), product.getAmount());		
		System.out.println("AddCartAction cart : " + cart.toString());
		
		String cookieValue = "";

		//비회원 : 쿠키에 넣은 상품번호, 수량 가져온다
		if(((User)session.getAttribute("user")).getUserId().equals("non-member")) {
			System.out.println("비회원");

			List<Cart> cartList = new ArrayList<Cart>();
			List<CookieInfo> returnList = new ArrayList<CookieInfo>();
			
			Cookie[] cookies = request.getCookies();
			
			if(cookies != null && cookies.length > 0) {
				for (int i = 0; i < cookies.length; i++) {
					if(cookies[i].getName().equals("prodInfoCookie")) {
						// ex) 10001:12,10007:5,10013:31
						cookieValue = URLDecoder.decode(cookies[i].getValue());
						System.out.println("기존에 prodInfoCookie 쿠키에 있는 cookieValue : " + cookieValue);
						
						if(cookieValue.trim().length() == 0) {
							//장바구니 담고 그것을 전체삭제 후 여기로 온다
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
							if(j==returnList.size()-1) {
								cookieValue = cookieValue.substring(0, cookieValue.length()-1);
								System.out.println(cookieValue);
							}
						}
						
					}else{
						// 첫번째 장바구니 담는 데이터 : 기존 데이터가 없으니 바로 추가
						if(cookieValue.trim().length() == 0) {
							System.out.println("1.장바구니에 상품이 하나도 없을 때");
							CookieInfo c = new CookieInfo(prod_no, amount);
							returnList.add(c);
							cookieValue = prod_no + ":" + amount;
							break;
						}
					}
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

	@RequestMapping(value = "deliveryCart", method = RequestMethod.POST)
	public ModelAndView deliveryCart(@RequestParam("addPurchaseCheckBox") int[] allProdNo,
									@RequestParam("deleteCheckBox") int[] checkProdNo,
									@RequestParam("amount") int[] allAmount,
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
		
		if(user == null || user.getUserId().equals("non-member")) {
			System.out.println("비회원");
			String cookieValue = "";
			
			Cookie[] cookies = request.getCookies();
			if(cookies != null && cookies.length > 0) {
				for (int i = 0; i < cookies.length; i++) {
					if(cookies[i].getName().equals("prodInfoCookie")) {
						cookieValue = URLDecoder.decode(cookies[i].getValue());
						System.out.println("prodInfoCookie으로 찾은 value : " + cookieValue);
					}
				}
			}

			List<CookieInfo> cookieList = new ArrayList<CookieInfo>();
			if(cookieValue.length() > 1) {
				String[] prodNoAndAmount = cookieValue.split(",");
				for (int i = 0; i < prodNoAndAmount.length; i++) {
					String[] arr = prodNoAndAmount[i].split(":");
					CookieInfo c = new CookieInfo(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
					cookieList.add(c);
				}

				Iterator<CookieInfo> it = cookieList.iterator();
				while (it.hasNext()) {
					CookieInfo tmp = (CookieInfo)it.next();

					for (int i = 0; i < deleteArr.length; i++) {
						if(tmp.getProd_no() == deleteArr[i]) {
							it.remove();
						}
					}		
				}
			}
			
			for (int i = 0; i < cookieList.size(); i++) {
				System.out.println(cookieList.get(i).toString());
			}
			
			System.out.println("======================================================");
			
			cookieValue = "";
			for (int i = 0; i < cookieList.size(); i++) {
				cookieValue = cookieList.get(i).getProd_no() +":"+cookieList.get(i).getAmount()+",";
				if(i == cookieList.size()-1) {
					cookieValue = cookieValue.substring(0, cookieValue.length()-1);
				}
			}
			System.out.println("response 할 cookie : " + cookieValue);
			
			Cookie cookie = new Cookie("prodInfoCookie", URLEncoder.encode(cookieValue));
			cookie.setMaxAge(24*60*60);
			response.addCookie(cookie);
			
			//if(cookieValue.trim().length() == 0) {
			//	cookie.getName().setMaxAge(-1);
			//}

			List<Cart> cartList = new ArrayList<Cart>();
			int count = 0;

			// prodInfoCookie 쿠키가 있을때만 파싱해서 list 뿌린다
			if(cookieValue.length() > 1) {
				String[] prodNoAndAmount = cookieValue.split(",");
				for (int i = 0; i < prodNoAndAmount.length; i++) {
					String cookieInfo[] = prodNoAndAmount[i].split(":");
					Product productVO = productServiceImpl.getProduct(Integer.parseInt(cookieInfo[0]));
					Cart cart = new Cart(productVO.getProdNo(), "", productVO.getFileName(), productVO.getProdName(),
							productVO.getProdDetail(), Integer.parseInt(cookieInfo[1]), productVO.getPrice(), productVO.getAmount());
					cartList.add(cart);
				}
				count = cartList.size();
			}
			
			model.addAttribute("list", cartList);
			model.addAttribute("count", count);
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

	/*
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
			System.out.println("비회원");
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
	*/
	
	@RequestMapping(value = "listCart", method = RequestMethod.GET)
	public ModelAndView listCart(HttpServletRequest request,
								HttpServletResponse response,
								HttpSession session,
								Model model) throws Exception {
		System.out.println("/cart/listCart : GET");
		
		//left.jsp 레이어에 있는 장바구니 <a href 클릭시 유저에 맞는 장바구니 리스트로 이동
		User user = (User)session.getAttribute("user");
		
		if(user.getUserId().equals("non-member")) {
			System.out.println("여기는 비회원");
			
			Cookie[] cookies = request.getCookies();
			List<Cart> cartList = new ArrayList<Cart>();
			int count = 0;
			String cookieValue = "";
			
			if(cookies != null && cookies.length > 0) {
				for (int i = 0; i < cookies.length; i++) {
					System.out.println(cookies[i].getName());
					if(cookies[i].getName().equals("prodInfoCookie")) {
						cookieValue = URLDecoder.decode(cookies[i].getValue());
					}
				}

				// prodInfoCookie 쿠키가 있을때만 파싱해서 list 뿌린다
				if(cookieValue.length() > 1) {
					String[] prodNoAndAmount = cookieValue.split(",");				
					for (int i = 0; i < prodNoAndAmount.length; i++) {
						String cookieInfo[] = prodNoAndAmount[i].split(":");
						Product productVO = productServiceImpl.getProduct(Integer.parseInt(cookieInfo[0]));
						Cart cart = new Cart(productVO.getProdNo(), "", productVO.getFileName(), productVO.getProdName(),
								productVO.getProdDetail(), Integer.parseInt(cookieInfo[1]), productVO.getPrice(), productVO.getAmount());
						cartList.add(cart);
					}
					count = cartList.size();
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
