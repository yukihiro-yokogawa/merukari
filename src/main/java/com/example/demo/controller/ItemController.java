package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.domein.Item;
import com.example.demo.form.ItemForm;
import com.example.demo.form.PaginationForm;
import com.example.demo.form.SearchForm;
import com.example.demo.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping("/item")
@SessionAttributes(value = "searchForm")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@ModelAttribute
	public PaginationForm setUpPagenationForm() {
		return new PaginationForm();
	}
	@ModelAttribute
	public ItemForm setUpItemForm() {
		return new ItemForm();
	}
	
	@ModelAttribute("searchForm")
	public SearchForm searchForm() {
		return new SearchForm();
	}

	/**
	 * top画面のViewです。 id 1~30を表示します.
	 * 
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping("/top")
	public String itemTop(Model model, HttpSession session){
		PaginationForm paginationForm = new PaginationForm();
		session.removeAttribute("maxId");
		session.removeAttribute("minId");
		session.removeAttribute("now");
		session.removeAttribute("lastPage");
		List<Item> itemList = itemService.findAll(paginationForm);
		session.setAttribute("maxId",itemList.get(0).getMaxId());

		Integer itemRecord = ((itemService.countItemRecord().get(0).getCountId()) / 30) + 1;
		session.setAttribute("itemRecord", itemRecord);
		
		session.setAttribute("now", 1);
		model.addAttribute("pageMax", itemRecord);
		model.addAttribute("page", 1);
		model.addAttribute("itemList", itemList);

		return "list.html";
	}
	

	/**
	 * 検索用のtop画面
	 * 
	 * @param paginationForm
	 * @param rs
	 * @param model
	 * @param session
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping("/search")
	public String paginationTop(@Validated PaginationForm paginationForm, BindingResult rs, Model model, HttpSession session,SearchForm searchForm){
		List<Item> itemList = new ArrayList<>();
		Integer itemRecordFull = 0;
		Integer pageNum = 0;
		
		if (rs.hasErrors()) {
			model.addAttribute("page",1);
			model.addAttribute("pageMax",session.getAttribute("itemRecord"));
			return "list.html";
		}
		
		pageNum = Integer.parseInt(paginationForm.getPage());		
		System.out.println(searchForm.getIsEmpty(searchForm));
		
		// ページネーション
		if (!searchForm.getIsEmpty(searchForm)) {
			System.out.println("top");
			itemList = itemService.findAll(paginationForm);
			session.setAttribute("maxId", itemList.get(0).getMaxId());
			session.setAttribute("minId", itemList.get(0).getMinId());
			session.setAttribute("now", paginationForm.getPage());

		} else if(searchForm.getIsEmpty(searchForm)){
			itemList = itemService.findItem(searchForm,paginationForm);
			session.setAttribute("maxId", itemList.get(0).getMaxId());
			session.setAttribute("minId", itemList.get(0).getMinId());
			itemRecordFull = (itemList.get(0).getLastPage()/30) + 1;
		}
				
		
		model.addAttribute("pageMax", itemRecordFull);
		model.addAttribute("page", pageNum);
		model.addAttribute("itemList", itemList);
		return "list.html";
	}
}
