package com.green.view.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.green.biz.product.ProductService;
import com.green.biz.product.ProductVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private ProductService productService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String home(Model model) {
		
		// 베스트 상품 조회 서비스 호출
		List<ProductVO> bestProductList = productService.getBestProductList();
		// 신상품 조회 서비스 호출
		List<ProductVO> newProductList = productService.getNewProductList();
		// model 저장소에 베스트 상품, 신상품 저장
		model.addAttribute("bestProductList", bestProductList);
		model.addAttribute("newProductList", newProductList);
		
		return "index";
	}	
	
}
