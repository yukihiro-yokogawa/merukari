package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domein.ChildCategory;
import com.example.demo.domein.GrandChild;
import com.example.demo.domein.Item;
import com.example.demo.domein.ParentCategory;

/**
 * 商品情報を操作するリポジトリクラスです.
 * 
 * @author yukihiro.yokogawa
 *
 */
@Repository
public class ItemRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * itemsテーブルに登録された商品情報をEntityにセットするメソッドです.
	 */
	private static final RowMapper<Item> ITEM_ROW_MAPPER= (rs,i) ->{
		Item item = new Item();		
		
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setCondition(rs.getInt("condition"));
		item.setCategory(rs.getString("category"));
		item.setBrand(rs.getString("brand"));
		item.setPrice(rs.getInt("price"));
		item.setShipping(rs.getInt("shipping"));
		item.setDescription(rs.getString("description"));
		
		return item;
	};
	
	/**
	 * itemsテーブルのレコード数をEntityにセットするメソッドです.
	 */
	private static final ResultSetExtractor<Integer> MAX_ID_RESULT_SET_EXTRACTOR= (rs) ->{
		Integer maxId = 0;
		
		while(rs.next()) {
			maxId = rs.getInt("id");
		}
		
		return maxId;
	};
	
	/**
	 * カテゴリを親子関係でEntityにセットするメソッドです.
	 */
	private static final ResultSetExtractor<List<ParentCategory>> PARENT_CATEGORY_RESULT_SET_EXTRACTOR = (rs) ->{
		
		List<ParentCategory> parentCategoryList = new ArrayList<>();
		List<ChildCategory> childCategoryList = new ArrayList<>();
		List<GrandChild> grandChildList = new ArrayList<>();
		Integer parentCategoryBeforeId = -1;
		Integer childCategoryBeforeId = -1;
		
		while(rs.next()) {
			
			Integer parentCategoryId = rs.getInt("parent_id");
			
			if(parentCategoryBeforeId != parentCategoryId) {
				ParentCategory parentCategory = new ParentCategory();
				parentCategory.setId(rs.getInt("parent_id"));
				parentCategory.setParentCategory(rs.getString("parent_name"));
			
				parentCategory.setChildCategory(childCategoryList);
				
				parentCategoryBeforeId = parentCategoryId;
				parentCategoryList.add(parentCategory);

			}
			
			Integer childCategoryId = rs.getInt("child_id");
			if(childCategoryBeforeId != childCategoryId) {
				
				ChildCategory childCategory = new ChildCategory();
				childCategory.setChildParent(rs.getInt("child_parent"));
				childCategory.setChildCategory(rs.getString("child_name"));
			
				childCategory.setGrandCategory(grandChildList);
				
				childCategoryBeforeId = childCategoryId;
				childCategoryList.add(childCategory);
			}
			
				GrandChild grandChild = new GrandChild();
				grandChild.setGrandChildParent(rs.getInt("grand_child_parent"));
				grandChild.setGrandChild(rs.getString("grand_child_name"));
				grandChildList.add(grandChild);
		}
		
		return parentCategoryList;
	};
	
	/**
	 * 全件検索するメソッドです.
	 * 
	 * @param pagingId
	 * @return 商品リスト
	 */
	public List<Item> findAll(Integer pagingId) {
	
		SqlParameterSource param = new MapSqlParameterSource().addValue("pagingId",pagingId);
		
		String sql = "SELECT"
					+ " itm.id AS id"
					+ ",itm.name AS name"
					+ ",itm.condition AS condition"
					+ ",cat.name_all AS category"
					+ ",itm.brand AS brand"
					+ ",itm.price AS price"
					+ ",itm.shipping AS shipping"
					+ ",itm.description AS description"
					+ " FROM"
					+ " items AS itm"
					+ " LEFT OUTER JOIN"
					+ " category AS cat"
					+ " ON"
					+ " itm.category = cat.id"
					+ " WHERE itm.id >= :pagingId"
					+ " ORDER BY itm.id LIMIT 30";
		
		List<Item> itemList = template.query(sql, param,ITEM_ROW_MAPPER);
		
		return itemList;
	}
	
	/**
	 * レコード数を取得するメソッドです.
	 * 
	 * @return レコード数.
	 */
	public Integer itemRecord() {
		String sql ="SELECT COUNT(id) AS id FROM items;";
		
		Integer itemRecord = template.query(sql, MAX_ID_RESULT_SET_EXTRACTOR);
		
		return itemRecord;
		
	}
	
	/**
	 * 親子関係のカテゴリのリストを取得するメソッドです.
	 * 
	 * @return カテゴリリスト
	 */
	public List<ParentCategory> findCategoryList(){
		String sql = "SELECT parent.id AS parent_id, parent.name AS parent_name,child.parent AS child_parent ,child.id AS child_id,child.name AS child_name,grand_child.parent AS grand_child_parent,grand_child.name AS grand_child_name  FROM category AS parent INNER JOIN category AS child ON parent.id = child.parent INNER JOIN category AS grand_child ON child.id = grand_child.parent WHERE child.name_all IS NULL ORDER BY parent.id,child.id,grand_child.id";
		
		List<ParentCategory> parentCategoryList = template.query(sql, PARENT_CATEGORY_RESULT_SET_EXTRACTOR);
		
		return parentCategoryList;
		
	}
	
}