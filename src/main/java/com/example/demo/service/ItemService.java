package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domein.Item;
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

	/**
	 * itemsテーブルの商品数を検索するメソッドです.
	 * 
	 * @return
	 */
	public Integer itemRecord() {
		return itemRepository.itemRecord();
	}
}
