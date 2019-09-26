package com.example.demo.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.domein.ChildCategory;
import com.example.demo.domein.GrandChild;
import com.example.demo.domein.ParentCategory;
import com.example.demo.service.ItemService;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

@Controller
public class CategoryData {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value = "getChildData",method = RequestMethod.GET)
	public String getChildData(Model model) {
	// カテゴリリスト取得
	List<ParentCategory> categoryList = itemService.findCategoryList();
	Multimap<String, Integer> parentCategoryMultiMap = LinkedHashMultimap.create();
	Multimap<String, Integer> childCategoryMultiMapByParent = LinkedHashMultimap.create();
	Multimap<String, Integer> childCategoryMultiMapById = LinkedHashMultimap.create();
	Multimap<String, Integer> grandChildMultiMap = LinkedHashMultimap.create();
	for (ParentCategory parentCategory : categoryList) {
		parentCategoryMultiMap.put(parentCategory.getParentCategory(),parentCategory.getId());
		for (ChildCategory childCategory : parentCategory.getChildCategory()) {
			childCategoryMultiMapByParent.put(childCategory.getChildCategory(),childCategory.getChildParent());
			childCategoryMultiMapById.put(childCategory.getChildCategory(),childCategory.getChildCategoryId());
			for (GrandChild grandChild : childCategory.getGrandCategory()) {
				grandChildMultiMap.put(grandChild.getGrandChild(),grandChild.getGrandChildParent());
			}
		}
	}
	
	Map<String,Collection<Integer>> parentCategoryMap = parentCategoryMultiMap.asMap();
	Map<String,Collection<Integer>> childCategoryMapByParent = childCategoryMultiMapByParent.asMap();
	Map<String,Collection<Integer>> grandChildCategoryMap = grandChildMultiMap.asMap();

	model.addAttribute("parentCategoryMap",parentCategoryMap);
	model.addAttribute("childCategoryMap",childCategoryMapByParent);
	model.addAttribute("grandChildCategoryMap",grandChildCategoryMap);
	
	return "list::selectChildAjax";
	}
}
