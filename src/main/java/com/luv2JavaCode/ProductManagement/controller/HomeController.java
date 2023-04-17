package com.luv2JavaCode.ProductManagement.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2JavaCode.ProductManagement.model.Products;
import com.luv2JavaCode.ProductManagement.repository.ProductRepository;

@Controller
public class HomeController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/")
	public String home(Model model) {
		/*
		 * List<Products> list = productRepository.findAll();
		 * model.addAttribute("products", list);
		 */
		return findPaginationAndSorting(0,"id","asc",model);
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
	
	@GetMapping("/page/{pageNo}")
	public String findPaginationAndSorting(@PathVariable(value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
 			Model model) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
		  Pageable pageable = PageRequest.of(pageNo, 3, sort);
		  Page<Products> page = productRepository.findAll(pageable);
		  
		  List<Products> list = page.getContent();
		  model.addAttribute("pageNo", pageNo);
		  model.addAttribute("totalElements", page.getTotalElements());
		  model.addAttribute("totalPage", page.getTotalPages());
		  model.addAttribute("products", list);
		  
		  model.addAttribute("sortField", sortField);
		  model.addAttribute("sortDir", sortDir);
		  model.addAttribute("revSortDir", sortDir.equals("asc")? "desc" : "asc");
		return "index";
	}
}
