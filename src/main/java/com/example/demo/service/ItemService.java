package com.example.demo.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domein.Item;
import com.example.demo.domein.ItemCount;
import com.example.demo.domein.Pagination;
import com.example.demo.domein.Search;
import com.example.demo.form.PaginationForm;
import com.example.demo.form.SearchForm;
import com.example.demo.repository.ItemCountRepository;
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
	
	@Autowired
	private ItemCountRepository itemCountRepository;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * 全件検索するメソッドです.
	 * 
	 * @param pagingId
	 * @return
	 */
	public List<Item> findAll(PaginationForm paginationForm) {
		Pagination pagination = new Pagination();
		
		if(paginationForm.getPage() != null && session.getAttribute("now") != null && session.getAttribute("now") .equals(paginationForm.getPage())) {
			//再検索 
			System.out.println(1);
			pagination.setPage(Integer.parseInt(String.valueOf(session.getAttribute("minId"))));
			pagination.setPaging("now");
		}else if(paginationForm.getPage()!=null && paginationForm.getPaging() != null && paginationForm.getPaging().equals("next")) {
			//次へ
			System.out.println(2);
			pagination.setPage(Integer.parseInt(String.valueOf(session.getAttribute("maxId"))));
			pagination.setPaging(paginationForm.getPaging());
		}else if(paginationForm.getPage() != null && paginationForm.getPaging() != null && paginationForm.getPaging().equals("prev")) {
			//前へ
			System.out.println(3);
			pagination.setPage(Integer.parseInt(String.valueOf(session.getAttribute("minId"))));
			pagination.setPaging(paginationForm.getPaging());
		}else if(paginationForm.getPage() != null && paginationForm.getPaging() != null &&paginationForm.getPaging().equals("search")) {
			//ページ検索
			if(Integer.parseInt(paginationForm.getPage()) >= 2) {
				pagination.setPage(((Integer.parseInt(paginationForm.getPage()) - 1) * 30) +1);
			}else if(paginationForm.getPage() .equals("1")) {				
				pagination.setPage(1);
			}
			pagination.setPaging(paginationForm.getPaging());
		}else {
			pagination.setPaging("top");
		}
		
		List<Item> itemList = itemRepository.findAll(pagination);
		return itemList;
		
	}

	/**
	 * 商品検索
	 * 
	 * @param searchForm
	 * @param pagingId
	 * @return
	 */
	public List<Item> findItem(SearchForm searchForm,PaginationForm paginationForm) {
		Search search = new Search();
		BeanUtils.copyProperties(searchForm, search);
				
		search.setParentCategoryId(Integer.parseInt(searchForm.getParentCategoryId()));
		search.setChildCategoryId(Integer.parseInt(searchForm.getChildCategoryId()));
		search.setGrandChildCategoryId(Integer.parseInt(searchForm.getGrandChildCategoryId()));
		

		Integer firstItem = itemCountRepository.countItemRecorbBySearch(search).get(0).getMinId();
		
		Pagination pagination = new Pagination();
		Integer pageNum = ((Integer.parseInt(paginationForm.getPage())-1) * 30) +1;
		pagination.setPage(pageNum);
		List<ItemCount> itemCountList = itemCountRepository.countItemRecorbBySearchAndPaginationSearch(search, pagination);
		Integer lastPage = itemCountList.get(0).getCountId();
		
		if(paginationForm.getPage()!=null && paginationForm.getPaging() != null && paginationForm.getPaging().equals("next")) {
			//次へ
			System.out.println(2);
			pagination.setPage(Integer.parseInt(String.valueOf(session.getAttribute("maxId"))));
			pagination.setPaging(paginationForm.getPaging());
		}else if(paginationForm.getPage() != null && paginationForm.getPaging() != null && paginationForm.getPaging().equals("prev")) {
			//前へ
			System.out.println(3);
			pagination.setPage(Integer.parseInt(String.valueOf(session.getAttribute("minId"))));
			pagination.setPaging(paginationForm.getPaging());
		}else if(paginationForm.getPage() != null && paginationForm.getPaging() != null &&paginationForm.getPaging().equals("search")) {
			System.out.println(4);
			//ページ検索
			if(Integer.parseInt(paginationForm.getPage()) >= 2) {
				pageNum = ((Integer.parseInt(paginationForm.getPage())-1) * 30) +1;
				pagination.setPage(pageNum);
				itemCountList = itemCountRepository.countItemRecorbBySearchAndPaginationSearch(search, pagination);
				pagination.setPage(itemCountList.get(pageNum-1).getSearchRowNumber()-1);
				
			}else if(paginationForm.getPage() .equals("1")) {				
				pagination.setPage(firstItem);
			}
			pagination.setPaging(paginationForm.getPaging());
		}else {
			pagination.setPage(firstItem);
			pagination.setPaging("top");
		}
		
		List<Item> itemList = itemRepository.findItem(search,pagination);
		itemList.get(0).setLastPage(lastPage);
		return itemList;

	}
	
	/**
	 * itemsテーブルの商品数を検索するメソッドです.
	 * 
	 * @return
	 */
	public List<ItemCount> countItemRecord() {
		return itemCountRepository.countItemRecord();
	}

}
