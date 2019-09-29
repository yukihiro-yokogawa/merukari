package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domein.ItemCount;
import com.example.demo.domein.Pagination;
import com.example.demo.domein.Search;

@Repository
public class ItemCountRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * itemsテーブルのレコード数をEntityにセットするメソッドです.
	 */
	private static final RowMapper<ItemCount> ITEM_COUNT_ROWMAPPER = (rs, i) -> {

		ItemCount itemCount = new ItemCount();

		itemCount.setCountId(rs.getInt("countId"));
		itemCount.setMinId(rs.getInt("minId"));
		itemCount.setSearchRowNumber(rs.getInt("search_row_number"));

		return itemCount;
	};


	/**
	 * 検索後のページング用のカウントクラスです.
	 * 
	 * @param search
	 * @param pagination
	 * @return
	 */
	public List<ItemCount> countItemRecorbBySearchAndPaginationSearch(Search search,Pagination pagination) {

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + search.getName() + "%")
				.addValue("brand", "%" + search.getBrand() + "%")
				.addValue("childCategoryId", search.getChildCategoryId())
				.addValue("grandChildCategoryId", search.getGrandChildCategoryId())
				.addValue("parentCategoryId", search.getParentCategoryId())
				.addValue("page", pagination.getPage());

		StringBuilder sql = new StringBuilder();

		System.out.println(pagination.getPage());
		
		sql.append(
				" SELECT COUNT(a.countId) OVER() AS countId,min(a.minId) OVER() AS minId ,(CASE WHEN 'search' = 'search' THEN (SELECT a.itm_id WHERE a.row_id = :page) END) AS search_row_number FROM");
		sql.append(" (SELECT");
		sql.append(" ROW_NUMBER() OVER(ORDER BY itm.id) AS row_id,");
		sql.append(" COUNT(itm.id) OVER() AS countId,");
		sql.append(" MIN(itm.id) OVER() AS minId,");
		sql.append(" itm.id AS itm_id");
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
		sql.append(" itm.name LIKE :name");
		sql.append(" AND");
		sql.append(" itm.brand LIKE :brand)");
		sql.append(" AS a ORDER BY a.itm_id");

		List<ItemCount> itemRecordBySearch = template.query(sql.toString(), param, ITEM_COUNT_ROWMAPPER);

		return itemRecordBySearch;
	}

	/**
	 * 検索時にページ内の商品の最小IDと最大IDを取得するメソッドです.
	 * 
	 * @param search
	 * @return
	 */
	public List<ItemCount> countItemRecorbBySearch(Search search) {

		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + search.getName() + "%")
				.addValue("brand", "%" + search.getBrand() + "%")
				.addValue("childCategoryId", search.getChildCategoryId())
				.addValue("grandChildCategoryId", search.getGrandChildCategoryId())
				.addValue("parentCategoryId", search.getParentCategoryId());

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT");
		sql.append(" COUNT(itm.id) AS countId,");
		sql.append(" MIN(itm.id) AS minId,");
		sql.append(" 1 AS search_row_number");
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

		List<ItemCount> itemRecordBySearch = template.query(sql.toString(), param, ITEM_COUNT_ROWMAPPER);

		return itemRecordBySearch;
	}

	/**
	 * レコード数を取得するメソッドです.
	 * 
	 * @return レコード数.
	 */
	public List<ItemCount> countItemRecord() {
		String sql = "SELECT MIN(id) AS minId , COUNT(id) AS countId, 1 AS search_row_number FROM items;";

		List<ItemCount> countItemRecord = template.query(sql, ITEM_COUNT_ROWMAPPER);
		return countItemRecord;

	}

}
