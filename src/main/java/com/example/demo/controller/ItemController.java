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
	
	@ModelAttribute("searchForm")
	public SearchForm searchForm() {
		System.out.println("aaa");
		return new SearchForm();
	}

	/**
	 * top画面のViewです。 id 1~30を表示します.
	 * 
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping("/top")
	public String itemTop(Model model, HttpSession session) throws JsonProcessingException {
		List<Item> itemList = itemService.findAll(1);

		if (session.getAttribute("itemRecord") != itemService.itemRecord()) {
			Integer itemRecord = ((itemService.itemRecord()) / 30) + 1;
			session.setAttribute("itemRecord", itemRecord);
		}
		
		model.addAttribute("pageMax", session.getAttribute("itemRecord"));
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
	public String paginationTop(@Validated PaginationForm paginationForm, BindingResult rs, Model model, HttpSession session,SearchForm searchForm)
			throws JsonProcessingException {
		List<Item> itemList = new ArrayList<>();
		Integer itemRecordFull = 0;
		Integer pageNum = 0;
		
		if (rs.hasErrors()) {
			return itemTop(model, session);
		}
		
		pageNum = Integer.parseInt(paginationForm.getPage());

		// sessionがnullでない時
		if (session.getAttribute("itemRecord") != null) {
			itemRecordFull = Integer.parseInt(String.valueOf(session.getAttribute("itemRecord")));
		}

		// 条件外の時にtop画面に戻す.
		if (itemRecordFull < pageNum || pageNum <= 0 || session.getAttribute("itemRecord") == null) {
			rs.rejectValue("page", "", "不正な値です。");
			return itemTop(model, session);
		}
		
		// ページネーション
		if (pageNum >= 2 && searchForm.getIsEmpty(searchForm)) {
			// 2ページ目以降
			Integer paginationNum = ((pageNum - 1) * 30) + 1;
			itemList = itemService.findAll(paginationNum);
		} else if (pageNum == 1 && searchForm.getIsEmpty(searchForm)) {
			// 1ページ目
			itemList = itemService.findAll(1);
		} else if(pageNum >= 2 && !(searchForm.getIsEmpty(searchForm))){
			Integer paginationNum = ((pageNum -1) * 30) + 1;
			itemList = itemService.findItem(searchForm,paginationNum);
			itemRecordFull = itemService.itemRecordBySearch(searchForm);
		} else if(pageNum == 1 && !(searchForm.getIsEmpty(searchForm))) {
			itemList = itemService.findItem(searchForm,1);
			itemRecordFull = itemService.itemRecordBySearch(searchForm);
		}
		
		
		model.addAttribute("pageMax", itemRecordFull);
		model.addAttribute("page", pageNum);
		model.addAttribute("itemList", itemList);
		return "list.html";
	}
}
