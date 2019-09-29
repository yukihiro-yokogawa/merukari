package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domein.ParentCategory;
import com.example.demo.repository.CategoryRepository;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	
	/**
	 * 親子関係のカテゴリのリストを取得するメソッドです.
	 * 
	 * @return カテゴリリスト
	 */
	public List<ParentCategory> findCategoryList() {
		return categoryRepository.findCategoryList();
	}
}
