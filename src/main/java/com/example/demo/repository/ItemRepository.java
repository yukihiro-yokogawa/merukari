package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domein.Item;
import com.example.demo.domein.Pagination;
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
		item.setMaxId(rs.getInt("max_id"));
		item.setMinId(rs.getInt("min_id"));
		
		return item;
	};
	
	/**
	 * 全件検索するメソッドです.
	 * 
	 * @param pagingId
	 * @return 商品リスト
	 */
	public List<Item> findAll(Pagination pagination) {
	
		SqlParameterSource param = new BeanPropertySqlParameterSource(pagination);
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append(" MIN(paging_list.id) OVER() AS min_id");
		sql.append(",MAX(paging_list.id) OVER() AS max_id");
		sql.append(",*");
		sql.append(" FROM(");
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
		sql.append(" WHERE");
		sql.append(" CASE");
		sql.append(" WHEN");
		sql.append(" :paging = 'top'");
		sql.append(" THEN (itm.id <= 30)");
		sql.append(" WHEN");
		sql.append(" :paging = 'next'");
		sql.append(" THEN (itm.id >= :page+1)");
		sql.append(" WHEN");
		sql.append(" :paging = 'prev'");
		sql.append(" THEN (itm.id <= :page-1)");
		sql.append(" WHEN");
		sql.append(" :paging = 'now'");
		sql.append(" THEN (itm.id >= :page)");
		sql.append(" WHEN");
		sql.append(" :paging = 'search'");
		sql.append(" THEN (itm.id >= :page)");
		sql.append(" END");
		sql.append(" ORDER BY");
		sql.append(" CASE WHEN :paging = 'next' THEN itm.id END,");
		sql.append(" CASE WHEN :paging = 'prev' THEN itm.id END DESC,");
		sql.append(" CASE WHEN :paging = 'now' THEN itm.id END,");
		sql.append(" CASE WHEN :paging = 'search' THEN itm.id END");
		sql.append(" LIMIT 30)");
		sql.append(" AS paging_list ORDER BY paging_list.id");
		
		List<Item> itemList = template.query(sql.toString(), param,ITEM_ROW_MAPPER);
		
		return itemList;
	}
	
	public List<Item> findItem(Search search,Pagination pagination){
		
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name",search.getName() + "%")
				.addValue("brand",search.getBrand() + "%")
				.addValue("parentCategoryId", search.getParentCategoryId())
				.addValue("childCategoryId", search.getChildCategoryId())
				.addValue("grandChildCategoryId", search.getGrandChildCategoryId())
				.addValue("paging", pagination.getPaging())
				.addValue("page", pagination.getPage());
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append(" MIN(paging_list.id) OVER() AS min_id");
		sql.append(",MAX(paging_list.id) OVER() AS max_id");
		sql.append(",*");
		sql.append(" FROM(");
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
		sql.append(",itm.category");
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
		sql.append(" WHERE");
		sql.append(" CASE");
		sql.append(" WHEN :parentCategoryId >= 1");
		sql.append(" THEN");
		sql.append(" CASE");
		sql.append(" WHEN :childCategoryId >= 1");
		sql.append(" THEN ");
		sql.append(" CASE ");
		sql.append(" WHEN :grandChildCategoryId >= 1");
		sql.append(" THEN");
		sql.append(" :grandChildCategoryId = itm.category");
		sql.append(" ELSE");
		sql.append(" :childCategoryId = grand_child.parent");
		sql.append(" END");
		sql.append(" ELSE");
		sql.append(" :parentCategoryId = child.parent");
		sql.append(" END");
		sql.append(" END");
		sql.append(" AND");
		sql.append(" CASE");
		sql.append(" WHEN");
		sql.append(" :paging = 'top'");
		sql.append(" THEN (itm.id >= :page)");
		sql.append(" WHEN");
		sql.append(" :paging = 'next'");
		sql.append(" THEN (itm.id >= :page+1)");
		sql.append(" WHEN");
		sql.append(" :paging = 'prev'");
		sql.append(" THEN (itm.id <= :page-1)");
		sql.append(" WHEN");
		sql.append(" :paging = 'now'");
		sql.append(" THEN (itm.id >= :page)");
		sql.append(" WHEN");
		sql.append(" :paging = 'search'");
		sql.append(" THEN (itm.id > :page)");
		sql.append(" END");
		sql.append(" AND");
		sql.append(" itm.name LIKE :name");
		sql.append(" AND");
		sql.append(" itm.brand LIKE :brand");
		sql.append(" ORDER BY");
		sql.append(" CASE WHEN :paging = 'next' THEN itm.id END,");
		sql.append(" CASE WHEN :paging = 'prev' THEN itm.id END DESC,");
		sql.append(" CASE WHEN :paging = 'now' THEN itm.id END,");
		sql.append(" CASE WHEN :paging = 'search' THEN itm.id END,");
		sql.append(" CASE WHEN :paging = 'top' THEN itm.id END");
		sql.append(" LIMIT 30)");
		sql.append(" AS paging_list ORDER BY paging_list.id");

		List<Item> itemList = template.query(sql.toString() ,param ,ITEM_ROW_MAPPER);
		
		return itemList;
	}
	
}