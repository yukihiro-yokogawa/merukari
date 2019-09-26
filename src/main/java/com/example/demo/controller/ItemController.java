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

import com.example.demo.domein.ChildCategory;
import com.example.demo.domein.GrandChild;
import com.example.demo.domein.Item;
import com.example.demo.domein.ParentCategory;
import com.example.demo.form.PaginationForm;
import com.example.demo.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@ModelAttribute
	public PaginationForm setUpForm() {
		return new PaginationForm();
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

		// カテゴリリスト取得


		model.addAttribute("pageMax", session.getAttribute("itemRecord"));
		model.addAttribute("page", 1);
		model.addAttribute("itemList", itemList);

		return "list.html";
	}

	/**
	 * pagination用のtop画面
	 * 
	 * @param form
	 * @param rs
	 * @param model
	 * @param session
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping("pagination")
	public String paginationTop(@Validated PaginationForm form, BindingResult rs, Model model, HttpSession session)
			throws JsonProcessingException {
		List<Item> itemList = new ArrayList<>();
		Integer itemRecordFull = 0;
		Integer pageNum = 0;

		if (rs.hasErrors()) {
			return itemTop(model, session);
		}

		pageNum = Integer.parseInt(form.getPage());

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
		if (pageNum >= 2) {
			// 2ページ目以降
			Integer paginationNum = ((pageNum - 1) * 30) + 1;
			itemList = itemService.findAll(paginationNum);
		} else if (pageNum == 1) {
			// 1ページ目
			itemList = itemService.findAll(1);
		}

		// カテゴリリスト取得

		model.addAttribute("pageMax", session.getAttribute("itemRecord"));
		model.addAttribute("page", pageNum);
		model.addAttribute("itemList", itemList);
		return "list.html";
	}
}
