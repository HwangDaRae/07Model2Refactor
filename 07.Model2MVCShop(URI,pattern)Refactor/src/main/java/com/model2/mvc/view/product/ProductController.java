package com.model2.mvc.view.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productServiceImpl;	

	public ProductController() {
		System.out.println(getClass() + " default Constructor()]");
		System.out.println("pageSize : " + pageSize);
		System.out.println("pageUnit : " + pageUnit);
	}
	
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
							  
	@RequestMapping(value = "listProduct/{menu}", method = RequestMethod.GET) /* @RequestParam String menu, */
	public String listProduct( @PathVariable String menu, Model model, HttpSession session, Search search) throws Exception {
		System.out.println("/product/listProduct : GET");
		System.out.println(search);
		System.out.println(menu);
		System.out.println(session.getAttribute("user"));
		
		if(((User)session.getAttribute("user")).getUserId().equals("non-member")) {
			//��ȸ�� ��ǰ �˻� Anchor Tag Ŭ��
			System.out.println("��ȸ������ ���Դ�");
		}else if(((User)session.getAttribute("user")).getRole().equals("admin")) {
			System.out.println("admin�������� ���Դ�");
		}else {
			System.out.println("user�������� ���Դ�");
		}

		// ��ǰ�˻� Ŭ�������� currentPage�� null�̴�
		int currentPage = 1;

		// ��ǰ�˻� Ŭ���� null, �˻���ư Ŭ���� nullString
		if (search.getCurrentPage() != 0) {
			currentPage = search.getCurrentPage();
		}

		// �ǸŻ�ǰ���� Ŭ���� searchKeyword, searchCondition �� �� null ==> nullString ���� ��ȯ
		String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
		String searchCondition = CommonUtil.null2str(search.getSearchCondition());
		
		// ��ǰ��� ��ǰ���ݿ��� searchKeyword�� �����϶� nullString���� ��ȯ
		if (!searchCondition.trim().equals("1") && !CommonUtil.parsingCheck(searchKeyword)) {
			searchKeyword = "";
		}
		search = new Search(currentPage, searchCondition, searchKeyword, pageSize, search.getPriceSort());
		
		// �˻������� �־ ���� �������� list�� �����´�
		List<Product> prodList = productServiceImpl.getProductList(search);		
		int totalCount = productServiceImpl.getProductTotalCount(search);		
		Page resultPage = new Page(currentPage, totalCount, pageUnit, pageSize);
		
		for (int i = 0; i < prodList.size(); i++) {
			System.out.println(getClass() + " : " + prodList.get(i).toString());
		}
		
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("searchVO", search);
		model.addAttribute("list", prodList);
		model.addAttribute("listSize", prodList.size());
		model.addAttribute("menu", menu);
		
		return "forward:/product/listProduct.jsp";
	}
	
	@RequestMapping( value = "listProduct", method = RequestMethod.POST )
	public String listProduct( @RequestParam("menu") String menu, Model model, User user, HttpSession session, Search search) throws Exception {
		System.out.println("/product/listProduct : POST");
		System.out.println(search);
		System.out.println(user);
		System.out.println(menu);
		System.out.println(session.getAttribute("user"));
		
		if(((User)session.getAttribute("user")).getUserId().equals("non-member")) {
			//��ȸ�� ��ǰ �˻� Anchor Tag Ŭ��
			System.out.println("��ȸ������ ���Դ�");
		}else if(((User)session.getAttribute("user")).getRole().equals("admin")) {
			System.out.println("admin�������� ���Դ�");
		}else {
			System.out.println("user�������� ���Դ�");
		}

		// ��ǰ�˻� Ŭ�������� currentPage�� null�̴�
		int currentPage = 1;

		// ��ǰ�˻� Ŭ���� null, �˻���ư Ŭ���� nullString
		if (search.getCurrentPage() != 0) {
			currentPage = search.getCurrentPage();
		}

		// �ǸŻ�ǰ���� Ŭ���� searchKeyword, searchCondition �� �� null ==> nullString ���� ��ȯ
		String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
		String searchCondition = CommonUtil.null2str(search.getSearchCondition());
		
		// ��ǰ��� ��ǰ���ݿ��� searchKeyword�� �����϶� nullString���� ��ȯ
		if (!searchCondition.trim().equals("1") && !CommonUtil.parsingCheck(searchKeyword)) {
			searchKeyword = "";
		}
		search = new Search(currentPage, searchCondition, searchKeyword, pageSize, search.getPriceSort());
		
		// �˻������� �־ ���� �������� list�� �����´�
		List<Product> prodList = productServiceImpl.getProductList(search);		
		int totalCount = productServiceImpl.getProductTotalCount(search);		
		Page resultPage = new Page(currentPage, totalCount, pageUnit, pageSize);
		
		for (int i = 0; i < prodList.size(); i++) {
			System.out.println(getClass() + " : " + prodList.get(i).toString());
		}
		
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("searchVO", search);
		model.addAttribute("list", prodList);
		model.addAttribute("listSize", prodList.size());
		model.addAttribute("menu", menu);
		
		return "forward:/product/listProduct.jsp";
	}
	
	@RequestMapping( value = "getProduct/{prodNo}/{menu}", method = RequestMethod.GET )
	public String getProduct(@PathVariable int prodNo, @PathVariable String menu, Model model ) throws Exception {
		System.out.println("/getProduct : GET");
		model.addAttribute("productVO", productServiceImpl.getProduct(prodNo));
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping( value = "addProductView", method = RequestMethod.GET )
	public String addProductView() throws Exception {
		System.out.println("/addProductView : GET");
		return "redirect:/product/addProductView.jsp";
	}
	
	/*
	@RequestMapping(value = "addProduct", method = RequestMethod.POST )
	public String getProduct( @ModelAttribute Product product, Model model) throws Exception {
		System.out.println("/product/addProduct : POST");
		model.addAttribute("productVO", productServiceImpl.addProduct(product));
		return "forward:/product/addProduct.jsp";
	}
	*/
	@RequestMapping(value = "addProduct")
	public String getProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("/product/addProduct : POST");

			if (FileUpload.isMultipartContent(request)) {
				String temDir = "C:\\workspace\\07.Model2MVCShop(URI,pattern)Refactor\\src\\main\\webapp\\images\\uploadFiles";

				DiskFileUpload fileUpload = new DiskFileUpload();
				fileUpload.setRepositoryPath(temDir);

				fileUpload.setSizeMax(1024 * 1024 * 10);

				fileUpload.setSizeThreshold(1024 * 100);

				if (request.getContentLength() < fileUpload.getSizeMax()) {
					Product productVO = new Product();

					StringTokenizer token = null;

					List fileItemList = fileUpload.parseRequest(request);
					
					int Size = fileItemList.size();
					System.out.println("size : " + Size);
					for (int i = 0; i < Size; i++) {
						FileItem fileItem = (FileItem) fileItemList.get(i);

						if (fileItem.isFormField()) {
							if (fileItem.getFieldName().equals("manuDate")) {
								token = new StringTokenizer(fileItem.getString("euc-kr"), "-");
								String manuDate = token.nextToken() + token.nextToken() + token.nextToken();
								productVO.setManuDate(manuDate);
							} else if (fileItem.getFieldName().equals("prodName")) {
								productVO.setProdName(fileItem.getString("euc-kr"));
							} else if (fileItem.getFieldName().equals("prodDetail")) {
								productVO.setProdDetail(fileItem.getString("euc-kr"));
							} else if (fileItem.getFieldName().equals("price")) {
								productVO.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
							} else if (fileItem.getFieldName().equals("amount")) {
								productVO.setAmount(Integer.parseInt(fileItem.getString("euc-kr")));
							}
						} else { // ���� �����̸�

							if (fileItem.getSize() > 0) {
								int idx = fileItem.getName().lastIndexOf("\\");
								if (idx == -1) {
									idx = fileItem.getName().lastIndexOf("/");
								}
								String fileName = fileItem.getName().substring(idx + 1);
								productVO.setFileName(fileName);

								try {
									File uploadedFile = new File(temDir, fileName);
									fileItem.write(uploadedFile);
								} catch (IOException e) {
									System.out.println(e);
								}

							} else {
								productVO.setFileName("../../images/empty.GIF");
							}

						}//else

					}//for
					
					System.out.println("����� ��ǰ : " + productVO);

					productServiceImpl.addProduct(productVO);
					request.setAttribute("productVO", productVO);

				} else {
					// ���ε��ϴ� ������ setSizeMax���� ū ���
					int overSize = (request.getContentLength() / 1000000);
					System.out.println("<script>alert('������ ũ��� 1MB���� �Դϴ�. �ø��� ���� �뷮��" + overSize + "MB�Դϴ�");
					System.out.println("history.back();</script>");
				}
			} else {
				System.out.println("���ڵ� Ÿ���� multipart/form-data�� �ƴմϴ�.");
			}
		
		return "forward:/product/addProduct.jsp";
	}
	
	@RequestMapping(value = "updateProductView/{prodNo}/{menu}", method = RequestMethod.GET )
	public String updateProductView(@PathVariable int prodNo, @PathVariable String menu, Model model) throws Exception {
		System.out.println("/product/updateProductView : GET");
		System.out.println("prodNo : " + prodNo);
		System.out.println("menu : " + menu);
		
		Product productVO = productServiceImpl.getProduct(prodNo);
		model.addAttribute("productVO", productVO);
		
		if( menu.equals("manage") && productVO.getProTranCode() == null ) {
			return "forward:/product/updateProductView.jsp";
		}else {
			return "forward:/product/getProduct.jsp";
		}
	}
	
	/*
	@RequestMapping(value = "updateProduct", method = RequestMethod.POST )
	public String updateProduct(@ModelAttribute("productVO") Product productVO, Model model) throws Exception {
		System.out.println("/product/updateProduct : POST");
		
		model.addAttribute("productVO", productServiceImpl.updateProduct(productVO));
		
		return "forward:/product/getProduct.jsp";
	}
	*/
	@RequestMapping(value = "updateProduct", method = RequestMethod.POST )
	public String updateProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("/product/updateProduct : POST");
		
		if (FileUpload.isMultipartContent(request)) {
			String temDir = "C:\\workspace\\07.Model2MVCShop(URI,pattern)Refactor\\src\\main\\webapp\\images\\uploadFiles";

			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);

			fileUpload.setSizeMax(1024 * 1024 * 10);

			fileUpload.setSizeThreshold(1024 * 100);

			if (request.getContentLength() < fileUpload.getSizeMax()) {
				Product productVO = new Product();

				StringTokenizer token = null;
				
				ProductServiceImpl service = new ProductServiceImpl();

				List fileItemList = fileUpload.parseRequest(request);
				int Size = fileItemList.size();
				for (int i = 0; i < Size; i++) {
					FileItem fileItem = (FileItem) fileItemList.get(i);

					if (fileItem.isFormField()) {
						if (fileItem.getFieldName().equals("manuDate")) {
							token = new StringTokenizer(fileItem.getString("euc-kr"), "-");
							String manuDate = token.nextToken();
							
							while(token.hasMoreTokens()) {
								manuDate += token.nextToken();
							}
							
							productVO.setManuDate(manuDate);
						} else if (fileItem.getFieldName().equals("prodName")) {
							productVO.setProdName(fileItem.getString("euc-kr"));
						} else if (fileItem.getFieldName().equals("prodDetail")) {
							productVO.setProdDetail(fileItem.getString("euc-kr"));
						} else if (fileItem.getFieldName().equals("price")) {
							productVO.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
						} else if (fileItem.getFieldName().equals("prodNo")) {
							productVO.setProdNo(Integer.parseInt(fileItem.getString("euc-kr")));
						} else if (fileItem.getFieldName().equals("amount")) {
							productVO.setAmount(Integer.parseInt(fileItem.getString("euc-kr")));
						}
						
					} else { // ���� �����̸�

						if (fileItem.getSize() > 0) {
							int idx = fileItem.getName().lastIndexOf("\\");
							if (idx == -1) {
								idx = fileItem.getName().lastIndexOf("/");
							}
							String fileName = fileItem.getName().substring(idx + 1);
							System.out.println("���� �̸� : " + fileName);
							productVO.setFileName(fileName);

							try {
								File uploadedFile = new File(temDir, fileName);
								fileItem.write(uploadedFile);
							} catch (IOException e) {
								System.out.println(e);
							}

						} else {
							productVO.setFileName("../../images/empty.GIF");
						}

					}//else

				}//for

				System.out.println(productVO);
				request.setAttribute("productVO", productServiceImpl.updateProduct(productVO));

			} else {
				// ���ε��ϴ� ������ setSizeMax���� ū ���
				int overSize = (request.getContentLength() / 1000000);
				System.out.println("<script>alert('������ ũ��� 1MB���� �Դϴ�. �ø��� ���� �뷮��" + overSize + "MB�Դϴ�");
				System.out.println("history.back();</script>");
			}
		} else {
			System.out.println("���ڵ� Ÿ���� multipart/form-data�� �ƴմϴ�.");
		}
		
		return "forward:/product/getProduct.jsp";
	}

}
