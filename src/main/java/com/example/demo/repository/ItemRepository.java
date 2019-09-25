package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domein.Item;

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
	 * idの最大値を取得するメソッドです.
	 */
	private static final ResultSetExtractor<Integer> MAX_ID_RESULT_SET_EXTRACTOR= (rs) ->{
		Integer maxId = 0;
		
		while(rs.next()) {
			maxId = rs.getInt("id");
		}
		
		return maxId;
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
	
}