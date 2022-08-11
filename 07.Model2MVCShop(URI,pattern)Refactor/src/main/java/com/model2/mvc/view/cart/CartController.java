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
		// getProduct.jsp���� ��ٱ��� ��ư Ŭ���� ��ٱ��Ͽ� �߰��ȴ�.
		Cart cart = new Cart(prod_no, ((User)session.getAttribute("user")).getUserId(), product.getFileName(), product.getProdName(), product.getProdDetail(), amount, product.getPrice(), product.getAmount());		
		System.out.println("AddCartAction cart : " + cart.toString());
		
		String cookieValue = "";

		//��ȸ�� : ��Ű�� ���� ��ǰ��ȣ, ���� �����´�
		if(((User)session.getAttribute("user")).getUserId().equals("non-member")) {

			List<Cart> cartList = new ArrayList<Cart>();
			List<CookieInfo> returnList = new ArrayList<CookieInfo>();
			
			Cookie[] cookies = request.getCookies();
			
			if(cookies != null && cookies.length > 0) {
				for (int i = 0; i < cookies.length; i++) {
					if(cookies[i].getName().equals("prodInfoCookie")) {
						// ex) 10001:12,10007:5,10013:31
						cookieValue = URLDecoder.decode(cookies[i].getValue());
						System.out.println("������ ��Ű�� �ִ� cookieValue : " + cookieValue);

						
						// ù��° ��ٱ��� ��� ������ : ���� �����Ͱ� ������ �ٷ� �߰�
						if(cookieValue.trim().length() == 0) {
							System.out.println("1.��ٱ��Ͽ� ��ǰ�� �ϳ��� ���� ��");
							CookieInfo c = new CookieInfo(prod_no, amount);
							returnList.add(c);
							cookieValue = prod_no + ":" + amount;
							break;
						}

						// �ι�° ���� ��ٱ��� ��� ������ => list�� �Ľ��ؼ� �ִ´�
						System.out.println("2.��ٱ��Ͽ� �����Ͱ� �ϳ��� ���� �� List�� ���");
						int secondCheck = cookieValue.indexOf(",");

						// list�� ������ ��Ű�� �Ľ��� �ִ´�
						if(secondCheck == -1) {
							//��ٱ��Ͽ� ��ǰ�� �ϳ��� ���� ��
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
						System.out.println("�Ľ��ؼ� ���� list : " + returnList);
						
						// ���� ���� ��ǰ�� ��ȣ�� list�� �ִ� ��ǰ��ȣ�� ���ٸ� ������ �߰�
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

						// ���� ������ list�� �ٽ� String���� ��ȯ
						cookieValue = "";
						for (int j = 0; j < returnList.size(); j++) {
							cookieValue += returnList.get(j).getProd_no() + ":" + returnList.get(j).getAmount() + ",";
							if(j==returnList.size()) {
								cookieValue = cookieValue.substring(0, cookieValue.length());
							}
						}
						
					}// end of prodInfoCookie ��Ű�� ���� ��
				}// end of cookie�� ���� �� for��
				
				// ��Ű�� ��ǰ���� �߰�
				System.out.println("response�� ��Ű ������ cookieValue : " + cookieValue);
				Cookie cookie = new Cookie("prodInfoCookie", URLEncoder.encode(cookieValue));
				cookie.setMaxAge(24*60*60);
				response.addCookie(cookie);
				

				// ��Ű�� ����� �����͸� list �Ѹ���				
				for (int y = 0; y < returnList.size(); y++) {
					Product productVO = new Product();					
					productVO = productServiceImpl.getProduct(returnList.get(y).getProd_no());
					Cart c = new Cart(productVO.getProdNo(), "", productVO.getFileName(), productVO.getProdName(),
							productVO.getProdDetail(), returnList.get(y).getAmount(), productVO.getPrice(), productVO.getAmount());
					cartList.add(c);
				}//end of cookieValueArr ��ٱ��Ͽ��� �ѷ��� list ��������
				
			}
			
			for (int j = 0; j < cartList.size(); j++) {
				System.out.println("===========> " + cartList.get(j).toString());
			}
			
			model.addAttribute("list", cartList);
			model.addAttribute("count", cartList.size());
		}else {
			// ȸ�� : ���� ��ǰ�� �ִ��� ���� ����Ʈ
			List<Cart> cartList = cartServiceImpl.getCartList( ((User)session.getAttribute("user")).getUserId() );
			
			//��ٱ��� ���θ� �����ͼ� ��ǰ��ȣ�� ���ٸ� �����߰�
			boolean isProdNo = false;
			for (int i = 0; i < cartList.size(); i++) {
				if(cartList.get(i).getProd_no() == prod_no){
					isProdNo = true;
					//��ٱ��Ͽ� ��ǰ�� �ִٸ� => ���� ������Ʈ
					cart.setAmount(cartList.get(i).getAmount() + amount);
					cartServiceImpl.updateAmount(cart);
				}
			}
			
			if(!isProdNo) {
				//��ٱ��Ͽ� ��ǰ�� ���ٸ� => insert
				cartServiceImpl.insertCart(cart);
			}
			//jsp���� ����� list ��ٱ��� list �����´�
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
		// getProduct.jsp���� ��ٱ��� ��ư Ŭ���� ��ٱ��Ͽ� �߰��ȴ�.
		Cart cart = new Cart(prod_no, ((User)session.getAttribute("user")).getUserId(), product.getFileName(), product.getProdName(),
				product.getProdDetail(), amount, product.getPrice(), product.getAmount());		
		System.out.println("AddCartAction cart : " + cart.toString());
		
		int index = 0;
		String cookieValue = "";

		//��ȸ�� : ��Ű�� ���� ��ǰ��ȣ, ���� �����´�
		if(((User)session.getAttribute("user")).getUserId().equals("non-member")) {
			Cookie[] cookies = request.getCookies();
			
			if(cookies != null && cookies.length > 0) {
				for (int i = 0; i < cookies.length; i++) {
					if(cookies[i].getName().equals("prodInfoCookie")) {
						// ���� ��ǰ��ȣ �ִ� => ���� plus, ���� ��ǰ��ȣ ���� => ���� �߰�
						// 10001:12,10007:5,10013:31
						cookieValue = URLDecoder.decode(cookies[i].getValue());
						System.out.println("���� ��Ű�� �ִ� cookieValue : " + cookieValue);

						index = i;						
						int lastInext = URLDecoder.decode(cookies[i].getValue()).lastIndexOf(",");
						System.out.println("lastInext : " + lastInext);
						
						// �ι�° ��ǰ ��´�
						if(lastInext != -1) {
							cookieValue = URLDecoder.decode(cookies[i].getValue()).substring(0, lastInext);
							//10001:3,10024:15,10017:11
						}
						
						System.out.println("cookieValue : " + cookieValue);
						//10001:3,10024:15,10017:11
						
						int setting = cookieValue.indexOf(prod_no+"");
						System.out.println(setting);
						
						boolean isProdNo = (cookieValue.indexOf(prod_no+"") != -1);
						System.out.println("��Ű�� ���� ���� ��ǰ��ȣ�� �ֳ�? : " + isProdNo);
						if(isProdNo) {
							System.out.println("============ ��Ű�� ��ǰ��ȣ�� �ִ�");
							
							int indexProdNoStart = cookieValue.indexOf(prod_no+"");
							System.out.println("indexProdNoStart : " + indexProdNoStart);
							String valueSubStr = cookieValue.substring(indexProdNoStart);
							System.out.println("valueSubStr : " + valueSubStr);
							//10001:3,10024:15,10017:11
							//10024:15,10017:11
							//10017:11
							int indexSeper = valueSubStr.indexOf(",");
							System.out.println("indexSeper : " + indexSeper);
							
							// -1�϶� ���� �������� �ִ� ���̴�. cookieValue�� �������� ��ǰ��ȣ���� �������� ������ �ᱹ for�� ������ cookeValue�� ������ ���� �����̾���Ѵ�
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
								System.out.println("���� ��ǰ��ȣ �־����� cookieValue : " + cookieValue);
							}else {
								//������ ġȯ�ؾ��Ѵ�
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
							// ���� ��ǰ��ȣ�� �������� �׳� �߰�
							System.out.println("============ ��Ű�� ��ǰ��ȣ�� ����");
							cookieValue = cookieValue + "," + prod_no + ":" + amount;
							System.out.println("cookieValue : " + cookieValue);
						}
					}// end of prodInfoCookie ��Ű�� ���� ��
				}// end of cookie�� ���� �� for��

				System.out.println("============ list�Ѹ���");

				
				// ���� ��� ��ǰ��ȣ�� ������ list �Ѹ���
				System.out.println(cookieValue.trim().length());
				if(cookieValue.trim().length() == 0) {
					cookieValue = prod_no + ":" + amount;
					System.out.println("���� ���� ��Ű ������ : " + cookieValue);
				}
				
				
				// ��Ű�� ��ǰ���� �߰�				
				Cookie cookie = new Cookie("prodInfoCookie", URLEncoder.encode(cookieValue));
				cookie.setMaxAge(24*60*60);
				response.addCookie(cookie);

				///*
				System.out.println("============ ��Ű�� ��ǰ��ȣ �����ͼ� ��ٱ��� list ���");

				// ��Ű���� ��ǰ��ȣ�� ���� ���� ��ٱ��Ͽ� �Ѹ���
				System.out.println("response�� ��Ű ������ cookieValue : " + cookieValue);
				//10002:3,10031:12,10013:7
				

				// ��Ű�� ����� �����͸� list �Ѹ���
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
				}//end of cookieValueArr ��ٱ��Ͽ��� �ѷ��� list ��������
				
			}
			
			for (int j = 0; j < list.size(); j++) {
				System.out.println("===========> " + list.get(j).toString());
			}
			
			model.addAttribute("list", list);
			model.addAttribute("count", list.size());
		}else {
			// ȸ�� : ���� ��ǰ�� �ִ��� ���� ����Ʈ
			List<Cart> cartList = cartServiceImpl.getCartList( ((User)session.getAttribute("user")).getUserId() );
			
			//��ٱ��� ���θ� �����ͼ� ��ǰ��ȣ�� ���ٸ� �����߰�
			boolean isProdNo = false;
			for (int i = 0; i < cartList.size(); i++) {
				if(cartList.get(i).getProd_no() == prod_no){
					isProdNo = true;
					//��ٱ��Ͽ� ��ǰ�� �ִٸ� => ���� ������Ʈ
					cart.setAmount(cartList.get(i).getAmount() + amount);
					cartServiceImpl.updateAmount(cart);
				}
			}
			
			if(!isProdNo) {
				//��ٱ��Ͽ� ��ǰ�� ���ٸ� => insert
				cartServiceImpl.insertCart(cart);
			}
			//jsp���� ����� list ��ٱ��� list �����´�
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
		//allAmount => ��� ����, allCheckProdNo => ��� ��ǰ��ȣ, checkedProdNo => üũ�� ��ǰ��ȣ
		//���� ���������� ���� ��� üũ�ڽ��� üũ�� üũ�ڽ��� ���ؼ� index�� �˾Ƴ��� �� index�� �´� ��ǰ��ȣ, ������ ������
		List<Purchase> purList = new ArrayList<Purchase>();
		Product productVO = new Product();

		for (int i = 0; i < allProdNo.length; i++) {
			System.out.println("for��1 : allProdNo[" + i + "] : " + allProdNo[i]);
			for (int j = 0; j < checkProdNo.length; j++) {
				System.out.println("for��2 : checkProdNo[" + j + "] : " + checkProdNo[j]);
				if(allProdNo[i] == checkProdNo[j]) {
					System.out.println("üũ");
					Purchase purchaseVO = new Purchase();
					System.out.println("��ǰ��ȣ : " + checkProdNo[j]);
					System.out.println("���� : " + allAmount[i]);
					
					//������ ��ǰ����
					productVO = productServiceImpl.getProduct(checkProdNo[j]);
					purchaseVO.setPurchaseProd(productVO);
					
					//������ ��������
					System.out.println(user);
					String userId = ((User)session.getAttribute("user")).getUserId();
					System.out.println(userId);
					user.setUserId( userId );
					purchaseVO.setBuyer(user);
					
					//������ ��ǰ�� ��������
					purchaseVO.setAmount(allAmount[i]);
					System.out.println("purchaseVO : " + purchaseVO);
					
					purList.add(purchaseVO);
					System.out.println("purList.toString() : " + purList.toString());
				}
			}
		}
		
		System.out.println("����");
		
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
		
		//1�� or ������ ������
		for (int i = 0; i < deleteArr.length; i++) {
			System.out.println("������ ��ǰ ��ȣ : " + deleteArr[i]);
		}
		
		if(user == null || user.getUserId().equals("non-member")) {
			// ��ȸ���̶��
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
				System.out.println("return�� index : " + returnIndexStart);
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
			//ȸ���̶��
			Map<String, Object> map = new HashMap<String, Object>();
			
			//������ ��ǰ��ȣ�� user_id�� map�� �ִ´�
			map.put("deleteArray", deleteArr);
			map.put("user_id", ( (User)session.getAttribute("user") ).getUserId() );

			//��ٱ��Ͽ��� ��ǰ�� �����ϰ� ������ list�� �����´�
			cartServiceImpl.deleteCart(map);
			List<Cart> list = cartServiceImpl.getCartList( ( (User)session.getAttribute("user") ).getUserId() );
			
			model.addAttribute("list", list);
			//count : �Խù� ��, listCart.jsp���� count>0�϶� for������ list���
			model.addAttribute("count", list.size());

			modelAndView.setViewName("/cart/listCart.jsp");
		}
		
		modelAndView.addObject("model", model);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "listCart", method = RequestMethod.GET)
	public ModelAndView listCart(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) throws Exception {
		System.out.println("/cart/listCart : GET");
		//left.jsp ���̾ �ִ� ��ٱ��� <a href Ŭ���� ������ �´� ��ٱ��� ����Ʈ�� �̵�
		User user = (User)session.getAttribute("user");
		System.out.println(user.getUserId());
		
		if(user.getUserId().equals("non-member")) {
			System.out.println("����� user.getUserId == non-member");
			
			Cookie[] cookies = request.getCookies();
			
			List<Cart> cartList = new ArrayList<Cart>();
			int count = 0;
			int index = 0;
			String cookieValue = "";
			//String[] prodNoAndAmount = null;
			
			if(cookies != null && cookies.length > 0) {
				
				// prodInfoCookie ��Ű�� ã�´�
				for (int i = 0; i < cookies.length; i++) {
					System.out.println(cookies[i].getName());
					if(cookies[i].getName().equals("prodInfoCookie")) {
						//��ǰ��ȣ�� ������ �´� ��ǰ������ �����´�
						System.out.println("����� ����?");
						index = i;
						cookieValue = URLDecoder.decode(cookies[i].getValue());
					}
				}

				System.out.println("index : " + index);				
				System.out.println("prodInfoCookie�� ã�� cookie value : " + cookieValue);
				
				// prodInfoCookie ��Ű�� �������� �Ľ��Ѵ�
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
			System.out.println("����� ȸ�� ��ٱ��� ����Ʈ");
			list = cartServiceImpl.getCartList(user.getUserId());
			
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).toString());
			}
			
			model.addAttribute("list", list);
			//count : �Խù� ��, listCart.jsp���� count>0�϶� for������ list���
			model.addAttribute("count", list.size());
		}
		
		return new ModelAndView("/cart/listCart.jsp", "model", model);
	}
	
}
