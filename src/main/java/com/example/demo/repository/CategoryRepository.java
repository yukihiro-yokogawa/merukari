package com.example.demo.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.domein.ChildCategory;
import com.example.demo.domein.GrandChild;
import com.example.demo.domein.ParentCategory;

@Repository
public class CategoryRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
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
				grandChild.setGrandChildName(rs.getString("grand_child_name"));
				
				Map<Integer,String> grandChildCategoryMap = new LinkedHashMap<>();
				grandChildCategoryMap.put(rs.getInt("grand_child_id"), rs.getString("grand_child_name"));
				grandChild.setGrandChildCategoryMap(grandChildCategoryMap);
				grandChildCategoryBeforeId = grandChildCategoryId;
				grandChildList.add(grandChild);
			}
		}
		
		return parentCategoryList;
	};
	
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
