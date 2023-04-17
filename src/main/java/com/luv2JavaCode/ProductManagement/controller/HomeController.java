package com.luv2JavaCode.ProductManagement.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import com.luv2JavaCode.ProductManagement.model.Products;
import com.luv2JavaCode.ProductManagement.repository.ProductRepository;

@Controller
public class HomeController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/")
	public String home(Model model) {
		List<Products> list = productRepository.findAll();
		model.addAttribute("products", list);
		return "index";
	}

	@GetMapping("/load_form")
	public String loadForm() {
		return "add";
	}

	@GetMapping("/edit_form/{id}")
	public String editForm(@PathVariable(value = "id") long id, Model model) {
		Optional<Products> product = productRepository.findById(id);
		Products pro = product.get();
		model.addAttribute("product", pro);
		return "edit";
	}

	@PostMapping("/save_products")
	public String save_products(@ModelAttribute Products products, HttpSession session) {
		productRepository.save(products);
		session.setAttribute("msg", "Product Added Successfully..");
		return "redirect:/load_form";
	}

	@PostMapping("/update_products")
	public String update_products(@ModelAttribute Products products, HttpSession session) {
		productRepository.save(products);
		session.setAttribute("msg", "Product Updated Successfully..");
		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String delete_product(@PathVariable(value = "id") long id, HttpSession session) {
		productRepository.deleteById(id);
		session.setAttribute("msg", "Product Deleted Successfully..");
		return "redirect:/";
	}
}
