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

/**
 * JSON形式のカテゴリリストを表示させるcontrollerクラスです.
 * 
 * @author yukihiro.yokogawa
 *
 */
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

	/**
	 * JSON形式の子要素のカテゴリリストを表示させるメソッドです.
	 * 
	 * @return 子要素のカテゴリリスト(JSON)
	 * @throws JsonProcessingException
	 */
	@RequestMapping("/getChildCategory")
	@ResponseBody
	public String getChildCategory() throws JsonProcessingException {

		List<ParentCategory> categoryList = itemService.findCategoryList();

		Multimap<Integer, Map<Integer,String>> childCategoryMultiMap = LinkedHashMultimap.create();
		for (ParentCategory parentCategory : categoryList) {
			for (ChildCategory childCategory : parentCategory.getChildCategory()) {
				childCategoryMultiMap.put(childCategory.getChildParent(), childCategory.getChildCategoryMap());
			}
		}

		Map<Integer, Collection<Map<Integer, String>>> childCategoryMap = childCategoryMultiMap.asMap();
		ObjectMapper mapper = new ObjectMapper();
		String childCategoryJson = mapper.writeValueAsString(childCategoryMap);

		return childCategoryJson;

	}

	/**
	 * 孫要素のカテゴリリストを表示させるメソッドです.
	 * 
	 * @return 孫要素のカテゴリリスト(JSON)
	 * @throws JsonProcessingException
	 */
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
