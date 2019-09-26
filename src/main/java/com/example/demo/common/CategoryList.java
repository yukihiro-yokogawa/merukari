package com.example.demo.common;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domein.ChildCategory;
import com.example.demo.domein.GrandChild;
import com.example.demo.domein.ParentCategory;
import com.example.demo.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

@Controller
@RequestMapping("/categoryList")
public class CategoryList {

	@Autowired
	private ItemService itemService;

	/**
	 * 親カテゴリのJsonDataです.
	 * 
	 * @return 親カテゴリのJsonデータ
	 * @throws JsonProcessingException
	 */
	@RequestMapping("/getParentCategory")
	@ResponseBody
	public String getParentCategory() throws JsonProcessingException {

		List<ParentCategory> categoryList = itemService.findCategoryList();

		Multimap<Integer, String> parentCategoryMultiMap = LinkedHashMultimap.create();
		for (ParentCategory parentCategory : categoryList) {
			parentCategoryMultiMap.put(parentCategory.getId(), parentCategory.getParentCategory());
		}

		Map<Integer, Collection<String>> parentCategoryMap = parentCategoryMultiMap.asMap();
		ObjectMapper mapper = new ObjectMapper();
		String parentCategoryJson = mapper.writeValueAsString(parentCategoryMap);

		return parentCategoryJson;

	}

	@RequestMapping("/getChildCategoryById")
	@ResponseBody
	public String getChildCategoryById() throws JsonProcessingException {

		List<ParentCategory> categoryList = itemService.findCategoryList();

		Multimap<Integer, String> childCategoryMultiMapById = LinkedHashMultimap.create();
		for (ParentCategory parentCategory : categoryList) {
			for (ChildCategory childCategory : parentCategory.getChildCategory()) {
				childCategoryMultiMapById.put(childCategory.getChildCategoryId(), childCategory.getChildCategory());
			}
		}

		Map<Integer, Collection<String>> childCategoryMapById = childCategoryMultiMapById.asMap();
		ObjectMapper mapper = new ObjectMapper();
		String childCategoryJsonById = mapper.writeValueAsString(childCategoryMapById);

		return childCategoryJsonById;

	}

	@RequestMapping("/getChildCategoryByParent")
	@ResponseBody
	public String getChildCategoryByParent() throws JsonProcessingException {

		List<ParentCategory> categoryList = itemService.findCategoryList();

		Multimap<Integer, String> childCategoryMultiMapByParent = LinkedHashMultimap.create();
		for (ParentCategory parentCategory : categoryList) {
			for (ChildCategory childCategory : parentCategory.getChildCategory()) {
				childCategoryMultiMapByParent.put(childCategory.getChildParent(), childCategory.getChildCategory());
			}
		}

		Map<Integer, Collection<String>> childCategoryMapByParent = childCategoryMultiMapByParent.asMap();
		ObjectMapper mapper = new ObjectMapper();
		String childCategoryJsonByParent = mapper.writeValueAsString(childCategoryMapByParent);

		return childCategoryJsonByParent;

	}

	@RequestMapping("/getGrandChildCategory")
	@ResponseBody
	public String getGrandChildCategory() throws JsonProcessingException {

		List<ParentCategory> categoryList = itemService.findCategoryList();

		Multimap<Integer, String> grandChildMultiMap = LinkedHashMultimap.create();
		for (ParentCategory parentCategory : categoryList) {
			for (ChildCategory childCategory : parentCategory.getChildCategory()) {
				for (GrandChild grandChild : childCategory.getGrandCategory()) {
					grandChildMultiMap.put(grandChild.getGrandChildParent(), grandChild.getGrandChild());
				}
			}
		}

		Map<Integer, Collection<String>> grandChildCategoryMap = grandChildMultiMap.asMap();
		ObjectMapper mapper = new ObjectMapper();
		String grandChildCategoryJsonByParent = mapper.writeValueAsString(grandChildCategoryMap);

		return grandChildCategoryJsonByParent;

	}

}
