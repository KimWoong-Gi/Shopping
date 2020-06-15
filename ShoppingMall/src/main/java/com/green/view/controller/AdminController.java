package com.green.view.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.green.biz.employee.EmployeeService;
import com.green.biz.employee.EmployeeVO;
import com.green.biz.member.MemberService;
import com.green.biz.member.MemberVO;
import com.green.biz.order.OrderService;
import com.green.biz.order.OrderVO;
import com.green.biz.product.ProductService;
import com.green.biz.product.ProductVO;
import com.green.biz.product.SalesQuantityVO;
import com.green.biz.qna.QnaService;
import com.green.biz.qna.QnaVO;
import com.green.biz.utils.Criteria;
import com.green.biz.utils.PageMaker;

@SessionAttributes("adminUser")
@Controller
public class AdminController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private QnaService qnaService;

	@RequestMapping(value = "admin_login")
	public String adminLogin(Model model, @RequestParam(value = "workerId") String id,
			@RequestParam(value = "workerPwd") String pwd) {
		int loginCheck = employeeService.workerCheck(id, pwd);

		if (loginCheck == 1) {
			EmployeeVO eVo = new EmployeeVO();

			eVo.setId(id);
			eVo.setPwd(pwd);

			model.addAttribute("adminUser", eVo);

			return "redirect:admin_product_list";
		} else if (loginCheck == 0) {
			model.addAttribute("message", "아이디와 비밀번호가 맞지 않습니다.");

			return "admin/main";
		} else {
			model.addAttribute("message", "존재하지 않는 아이디입니다.");

			return "admin/main";
		}
	}

	@RequestMapping(value = "admin_login_form")
	public String adminLoginForm() {

		return "admin/main";
	}

	@RequestMapping(value = "admin_logout")
	public String adminLogtout(SessionStatus session) {
		session.setComplete();

		return "admin/main";
	}

	@RequestMapping(value = "admin_product_list")
	public String adminSearchProductList(Criteria criteria, Model model, HttpSession session,
			@RequestParam(value = "key", defaultValue = "") String name) {

		EmployeeVO admin = (EmployeeVO) session.getAttribute("adminUser");

		if (admin != null) {
			System.out.println("페이지 범위: " + criteria);
			// List<ProductVO> productList = productService.listProduct(name);
			List<ProductVO> productList;
			
			productList = productService.listWithPaging(criteria, name);	
			

			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(criteria);

			// 총 목록 수를 DB에서 조회
			int totalCount = productService.countProductList(name);
			pageMaker.setTotalCount(totalCount);
			System.out.println("페이징 정보: " + pageMaker);

			model.addAttribute("productList", productList);
			model.addAttribute("productListSize", productList.size());
			model.addAttribute("pageMaker", pageMaker);

			return "admin/product/productList";

		} else {
			return "admin/main";
		}
	}

	@RequestMapping(value = "admin_product_write_form")
	public String adminProductWriteView(HttpSession session, Model model) {

		EmployeeVO admin = (EmployeeVO) session.getAttribute("adminUser");

		if (admin != null) {
			String kindList[] = { "Heels", "Boots", "Sandals", "Sneakers", "On Sale" };
			model.addAttribute("kindList", kindList);
			return "admin/product/productWrite";

		} else {
			return "admin/main";
		}
	}

	@RequestMapping(value = "admin_product_write", method = RequestMethod.POST)
	public String adminProductWriteAction(@RequestParam(value = "images", required = false) MultipartFile file,
			ProductVO pVo, HttpSession session) throws IOException {
		EmployeeVO admin = (EmployeeVO) session.getAttribute("adminUser");

		if (admin != null) {
			if (!file.isEmpty()) {
				String fileName = file.getOriginalFilename();
				String root_path = session.getServletContext().getRealPath("WEB-INF/resources/product_images/");
				file.transferTo(new File(root_path + fileName));
				pVo.setImage(fileName);
			}

			productService.insertProduct(pVo);

			return "redirect:admin_product_list";

		} else {
			return "admin/main";
		}
	}

	@RequestMapping(value = "admin_product_detail")
	public String adminProductDetail(ProductVO pVo, Criteria criteria, Model model) {
		EmployeeVO admin = (EmployeeVO) model.getAttribute("adminUser");

		if (admin != null) {
			ProductVO vo = productService.getProduct(pVo);

			int index = Integer.parseInt(vo.getKind());

			String kind[] = { "o", "Heels", "Boots", "Sandals", "Slipers", "Sneekers", "Sale" };

			model.addAttribute("productVO", vo);
			model.addAttribute("kind", kind[index]);
			model.addAttribute("criteria", criteria);

			return "admin/product/productDetail";

		} else {
			return "admin/main";
		}
	}

	@RequestMapping(value = "admin_product_update_form")
	public String adminProductUpdateView(ProductVO pVo, Model model) {
		String[] kindList = { "Heels", "Boots", "Sandals", "Slipers", "Sneekers", "Sale" };

		ProductVO vo = productService.getProduct(pVo);

		String kind[] = { "Heels", "Boots", "Sandals", "Slipers", "Sneekers", "Sale" };

		model.addAttribute("kindList", kind);

		model.addAttribute("productVO", vo);

		return "admin/product/productUpdate";

	}

	@RequestMapping(value = "admin_product_update")
	public String adminProductUpdate(@RequestParam(value = "product_image") MultipartFile uploadFile, ProductVO pVo,
			Model model, HttpSession session) {
		EmployeeVO adminUser = (EmployeeVO) session.getAttribute("adminUser");

		if (adminUser == null) {
			return "admin/main";
		} else {
			String fileName = "";
			if (pVo.getBestyn() == null) {
				pVo.setBestyn("n");
			}
			if (pVo.getUseyn() == null) {
				pVo.setUseyn("n");
			}

			if (!uploadFile.isEmpty()) { // 상품이미지가 업로드됨
				String root_path = session.getServletContext().getRealPath("WEB-INF/resources/product_images/");
				System.out.println("Root 경로 = " + root_path);

				// 업로드된 파일명을 얻어온다.
				fileName = uploadFile.getOriginalFilename();

				try {
					File file = new File(root_path + fileName);
					uploadFile.transferTo(file);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				pVo.setImage(fileName);

			}
			System.out.println("업데이트 정보:" + pVo);
			productService.updateProduct(pVo);

			return "redirect:admin_product_detail?pseq=" + pVo.getPseq();
		}
	}

	@RequestMapping(value = "admin_order_list")
	public String adminOrderList(Model model, HttpSession session, OrderVO oVo,
			@RequestParam(value = "key", defaultValue = "") String key) {
		EmployeeVO adminUser = (EmployeeVO) session.getAttribute("adminUser");

		if (adminUser != null) {
			oVo.setMname(key);
			List<OrderVO> orderList = orderService.listOrder(oVo);
			model.addAttribute("orderList", orderList);

			return "admin/order/orderList";
		} else {

			return "admin/main";
		}
	}

	@RequestMapping(value = "admin_order_save")
	public String adminOrderSave(HttpSession session, OrderVO oVo, Model model,
			@RequestParam(value = "result") int odseq[]) {
		EmployeeVO adminUser = (EmployeeVO) session.getAttribute("adminUser");

		if (adminUser != null) {
			for (int i = 0; i < odseq.length; i++) {
				oVo.setOdseq(odseq[i]);
				orderService.updateOrderResult(oVo);
			}

			return "redirect:admin_order_list";
		} else {

			return "admin/main";
		}
	}

	@RequestMapping(value = "admin_member_list")
	public String adminMemberList(HttpSession session, MemberVO mVo, Model model,
			@RequestParam(value = "key", defaultValue = "") String key) {
		EmployeeVO adminUser = (EmployeeVO) session.getAttribute("adminUser");

		if (adminUser != null) {
			mVo.setName(key);
			List<MemberVO> memberList = memberService.listMember(mVo);
			model.addAttribute("memberList", memberList);

			return "admin/member/memberList";
		} else {

			return "admin/main";
		}
	}

	@RequestMapping(value = "admin_qna_list")
	public String adminQnaList(HttpSession session, Model model) {
		EmployeeVO adminUser = (EmployeeVO) session.getAttribute("adminUser");

		if (adminUser != null) {

			List<QnaVO> qnaList = qnaService.listAllQna();
			model.addAttribute("qnaList", qnaList);

			return "admin/qna/qnaList";
		} else {

			return "admin/main";
		}
	}

	@RequestMapping(value = "admin_qna_detail")
	public String adminQnaDetail(HttpSession session, Model model, @RequestParam("qseq") int qseq) {
		EmployeeVO adminUser = (EmployeeVO) session.getAttribute("adminUser");

		if (adminUser != null) {
			QnaVO qVo = qnaService.getQna(qseq);
			model.addAttribute("qnaVO", qVo);

			return "admin/qna/qnaDetail";
		} else {

			return "admin/main";
		}
	}

	@RequestMapping(value = "admin_qna_resave")
	public String adminQnaResave(HttpSession session, QnaVO qVo) {
		EmployeeVO adminUser = (EmployeeVO) session.getAttribute("adminUser");

		if (adminUser != null) {
			qnaService.updateQna(qVo);

			return "redirect:admin_qna_list";
		} else {

			return "admin/main";
		}
	}
	
	// 상품별 판매 실적
	@RequestMapping(value="admin_sales_record_form")
	public String adminProductSalesChart(Model model) {
		
		return "admin/order/salesRecords";
	}
	
	@RequestMapping(value="sales_record_chart", produces="application/json; charset=UTF-8")
	@ResponseBody
	public List<SalesQuantityVO> sales_record_chart(){
		List<SalesQuantityVO> listSales = productService.getProductSales();
		
		System.out.println("판매 실적>>>>>>>>");
		System.out.println("    제품명       수량");
		System.out.println("--------------");
		for(SalesQuantityVO item : listSales) {
			System.out.printf("%10s%3d\n", item.getPname(), item.getQuantity());
		}
		System.out.println("==============");
		return listSales;
	}
}
