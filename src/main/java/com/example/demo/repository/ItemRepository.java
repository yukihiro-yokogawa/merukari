package com.example.demo.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.example.demo.domein.Search;

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
		item.setParentId(rs.getInt("parentId"));
		item.setParentName(rs.getString("parentName"));
		item.setChildId(rs.getInt("childId"));
		item.setChildParent(rs.getInt("childParent"));
		item.setChildName(rs.getString("childName"));
		item.setGrandChildParent(rs.getInt("grandChildParent"));
		item.setGrandChildName(rs.getString("grandChildName"));
		item.setBrand(rs.getString("brand"));
		item.setPrice(rs.getInt("price"));
		item.setShipping(rs.getInt("shipping"));
		item.setDescription(rs.getString("description"));
		
		return item;
	};
	
	/**
	 * itemsテーブルのレコード数をEntityにセットするメソッドです.
	 */
	private static final ResultSetExtractor<Integer> COUNT_ID_RESULT_SET_EXTRACTOR= (rs) ->{
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
		Integer grandChildCategoryBeforeId = -1;
		
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
				childCategory.setChildCategoryId(rs.getInt("child_id"));
				childCategory.setChildParent(rs.getInt("child_parent"));
				childCategory.setChildCategory(rs.getString("child_name"));
				
				Map<Integer,String> childCategoryMap = new LinkedHashMap<>();
				childCategoryMap.put(rs.getInt("child_id"), rs.getString("child_name"));
				childCategory.setChildCategoryMap(childCategoryMap);
			
				childCategory.setGrandCategory(grandChildList);
				
				childCategoryBeforeId = childCategoryId;
				childCategoryList.add(childCategory);
			}
			Integer grandChildCategoryId = rs.getInt("grand_child_id");
			if(grandChildCategoryBeforeId != grandChildCategoryId) {
				GrandChild grandChild = new GrandChild();
				grandChild.setGrandChildParent(rs.getInt("grand_child_parent"));
				grandChild.setGrandChild(rs.getString("grand_child_name"));
				grandChildCategoryBeforeId = grandChildCategoryId;
				grandChildList.add(grandChild);
			}
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
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append(" itm.id AS id");
		sql.append(",itm.name AS name");
		sql.append(",itm.condition AS condition");
		sql.append(",parent.id AS parentId");
		sql.append(",parent.name AS parentName");
		sql.append(",child.id AS childId");
		sql.append(",child.parent AS childParent");
		sql.append(",child.name AS childName");
		sql.append(",grand_child.parent AS grandChildParent");
		sql.append(",grand_child.name AS grandChildName");
		sql.append(",itm.brand AS brand");
		sql.append(",itm.price AS price");
		sql.append(",itm.shipping AS shipping");
		sql.append(",itm.description AS description");
		sql.append(" FROM");
		sql.append(" category AS parent");
		sql.append(" INNER JOIN");
		sql.append(" category AS child");
		sql.append(" ON");
		sql.append(" parent.id = child.parent");
		sql.append(" INNER JOIN");
		sql.append(" category AS grand_child");
		sql.append(" ON");
		sql.append(" child.id = grand_child.parent");
		sql.append(" RIGHT OUTER JOIN");
		sql.append(" items AS itm");
		sql.append(" ON");
		sql.append(" itm.category = grand_child.id");
		sql.append(" WHERE itm.id >= :pagingId");
		sql.append(" AND parent.parent IS NULL");
		sql.append(" ORDER BY");
		sql.append(" itm.id");
		sql.append(",parent.id");
		sql.append(",child.id");
		sql.append(",grand_child.id");
		sql.append(" LIMIT 30");
		
		List<Item> itemList = template.query(sql.toString(), param,ITEM_ROW_MAPPER);
		
		return itemList;
	}
	
	public List<Item> findItem(Search search, Integer pagingId){
		
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", "%" + search.getName() + "%")
				.addValue("brand","%" + search.getBrand() + "%")
				.addValue("childCategoryId", search.getChildCategoryId())
				.addValue("grandChildCategoryId", search.getGrandChildCategoryId())
				.addValue("parentCategoryId", search.getParentCategoryId())
				.addValue("pagingId", pagingId);
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append(" itm.id AS id");
		sql.append(",itm.name AS name");
		sql.append(",itm.condition AS condition");
		sql.append(",parent.id AS parentId");
		sql.append(",parent.name AS parentName");
		sql.append(",child.id AS childId");
		sql.append(",child.parent AS childParent");
		sql.append(",child.name AS childName");
		sql.append(",grand_child.parent AS grandChildParent");
		sql.append(",grand_child.name AS grandChildName");
		sql.append(",itm.brand AS brand");
		sql.append(",itm.price AS price");
		sql.append(",itm.shipping AS shipping");
		sql.append(",itm.description AS description");
		sql.append(" FROM");
		sql.append(" category AS parent");
		sql.append(" INNER JOIN");
		sql.append(" category AS child");
		sql.append(" ON");
		sql.append(" parent.id = child.parent");
		sql.append(" INNER JOIN");
		sql.append(" category AS grand_child");
		sql.append(" ON");
		sql.append(" child.id = grand_child.parent");
		sql.append(" RIGHT OUTER JOIN");
		sql.append(" items AS itm");
		sql.append(" ON");
		sql.append(" itm.category = grand_child.id");
		sql.append(" WHERE itm.id >= :pagingId");
		sql.append(" AND parent.parent IS NULL");
		sql.append(" ORDER BY");
		sql.append(" itm.id");
		sql.append(" LIMIT 30");
		
		List<Item> itemList = template.query(sql.toString() ,param ,ITEM_ROW_MAPPER);
		
		return itemList;
	}
	
	public Integer ItemRecorbBySearch(Search search){
		
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", "%" + search.getName() + "%")
				.addValue("brand","%" + search.getBrand() + "%")
				.addValue("childCategoryId", search.getChildCategoryId())
				.addValue("grandChildCategoryId", search.getGrandChildCategoryId())
				.addValue("parentCategoryId", search.getParentCategoryId());
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT");
		sql.append(" COUNT(itm.id) AS id");
		sql.append(" FROM");
		sql.append(" category AS parent");
		sql.append(" INNER JOIN");
		sql.append(" category AS child");
		sql.append(" ON");
		sql.append(" parent.id = child.parent");
		sql.append(" INNER JOIN");
		sql.append(" category AS grand_child");
		sql.append(" ON");
		sql.append(" child.id = grand_child.parent");
		sql.append(" RIGHT OUTER JOIN");
		sql.append(" items AS itm");
		sql.append(" ON");
		sql.append(" itm.category = grand_child.id");
		
		Integer itemRecordBySearch = template.query(sql.toString(),param ,COUNT_ID_RESULT_SET_EXTRACTOR);
		
		return itemRecordBySearch;
	}
	
	/**
	 * レコード数を取得するメソッドです.
	 * 
	 * @return レコード数.
	 */
	public Integer itemRecord() {
		String sql ="SELECT COUNT(id) AS id FROM items;";
		
		Integer itemRecord = template.query(sql, COUNT_ID_RESULT_SET_EXTRACTOR);
		
		return itemRecord;
		
	}
	
	/**
	 * 親子関係のカテゴリのリストを取得するメソッドです.
	 * 
	 * @return カテゴリリスト
	 */
	public List<ParentCategory> findCategoryList(){
		String sql = "SELECT parent.id AS parent_id, parent.name AS parent_name,child.id AS child_id,child.parent AS child_parent ,child.id AS child_id,child.name AS child_name,grand_child.id AS grand_child_id,grand_child.parent AS grand_child_parent,grand_child.name AS grand_child_name  FROM category AS parent INNER JOIN category AS child ON parent.id = child.parent INNER JOIN category AS grand_child ON child.id = grand_child.parent WHERE child.name_all IS NULL ORDER BY parent.id,child.id,grand_child.id";
		
		List<ParentCategory> parentCategoryList = template.query(sql, PARENT_CATEGORY_RESULT_SET_EXTRACTOR);
		
		return parentCategoryList;
		
	}
	
}