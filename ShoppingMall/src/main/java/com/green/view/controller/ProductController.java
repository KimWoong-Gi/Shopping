package com.green.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.green.biz.product.ProductService;
import com.green.biz.product.ProductVO;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	
	// ProductVO 커맨드 객체 이용하는 방법
	@RequestMapping(value="/product_detail", method=RequestMethod.GET)
	public String productDetailAction(ProductVO pVo, Model model) {
		
		ProductVO vo = productService.getProduct(pVo);
		
		model.addAttribute("productVO", vo);
		
		return "product/productDetail";
	}
	
	// @RequestParam을 이용한 방식
	/*
	@RequestMapping(value="/product_detail", method=RequestMethod.GET)
	public String productDetailAction(@RequestParam(value="pseq", defaultValue="0", required=true) String pseq, Model model) {
		
		ProductVO vo = new ProductVO();
		
		vo.setPseq(Integer.parseInt(pseq));
		
		ProductVO pVo = productService.getProduct(vo);
		
		model.addAttribute("productVO", pVo);
	
		return "/product/productDetail";
	}
	*/
	@RequestMapping(value="/category", method=RequestMethod.GET)
	public String ProductKindAction(ProductVO pVo, Model model) {
		
		List<ProductVO> listProdByKind = productService.getProductListByKind(pVo);
		
		model.addAttribute("productKindList", listProdByKind);
		
		return "product/productKind";
	}
}
