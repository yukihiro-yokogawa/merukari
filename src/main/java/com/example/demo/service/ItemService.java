package com.example.demo.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domein.Item;
import com.example.demo.domein.ParentCategory;
import com.example.demo.domein.Search;
import com.example.demo.form.SearchForm;
import com.example.demo.repository.ItemRepository;

/**
 * itemsテーブルから取り出したデータを操作するリポジトリクラスです.
 * 
 * @author yukihiro.yokogawa
 *
 */
@Service
@Transactional
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	/**
	 * 全件検索するメソッドです.
	 * 
	 * @param pagingId
	 * @return
	 */
	public List<Item> findAll(Integer pagingId) {
		List<Item> itemList = itemRepository.findAll(pagingId);
		return itemList;
	}

	public List<Item> findItem(SearchForm searchForm, Integer pagingId) {
		Search search = new Search();
		BeanUtils.copyProperties(searchForm, search);

		if (searchForm.getName() == null) {
			search.setName("");
		}

		if (searchForm.getBrand() == null) {
			search.setBrand("");
		}

		if (searchForm.getParentCategoryId() == null || searchForm.getParentCategoryId().isEmpty()) {
			search.setParentCategoryId(0);
		} else {
			search.setParentCategoryId(Integer.parseInt(searchForm.getParentCategoryId()));
		}

		if (searchForm.getChildCategoryId() == null || searchForm.getChildCategoryId().isEmpty()) {
			search.setChildCategoryId(0);
		} else {
			search.setChildCategoryId(Integer.parseInt(searchForm.getChildCategoryId()));
		}
		
		if (searchForm.getGrandChildCategoryId() == null || searchForm.getGrandChildCategoryId().isEmpty()) {
			search.setGrandChildCategoryId(0);
		} else {
			search.setGrandChildCategoryId(Integer.parseInt(searchForm.getGrandChildCategoryId()));
		}

		return itemRepository.findItem(search,pagingId);

	}
	
	public Integer itemRecordBySearch(SearchForm searchForm) {
		Search search = new Search();
		BeanUtils.copyProperties(searchForm, search);

		if (searchForm.getName() == null) {
			search.setName("");
		}

		if (searchForm.getBrand() == null) {
			search.setBrand("");
		}

		if (searchForm.getParentCategoryId() == null || searchForm.getParentCategoryId().isEmpty()) {
			search.setParentCategoryId(0);
		} else {
			search.setParentCategoryId(Integer.parseInt(searchForm.getParentCategoryId()));
		}

		if (searchForm.getChildCategoryId() == null || searchForm.getChildCategoryId().isEmpty()) {
			search.setChildCategoryId(0);
		} else {
			search.setChildCategoryId(Integer.parseInt(searchForm.getChildCategoryId()));
		}
		
		if (searchForm.getGrandChildCategoryId() == null || searchForm.getGrandChildCategoryId().isEmpty()) {
			search.setGrandChildCategoryId(0);
		} else {
			search.setGrandChildCategoryId(Integer.parseInt(searchForm.getGrandChildCategoryId()));
		}

		
		return itemRepository.ItemRecorbBySearch(search);
	}
	
	/**
	 * itemsテーブルの商品数を検索するメソッドです.
	 * 
	 * @return
	 */
	public Integer itemRecord() {
		return itemRepository.itemRecord();
	}

	/**
	 * 親子関係のカテゴリのリストを取得するメソッドです.
	 * 
	 * @return カテゴリリスト
	 */
	public List<ParentCategory> findCategoryList() {
		return itemRepository.findCategoryList();
	}
}
