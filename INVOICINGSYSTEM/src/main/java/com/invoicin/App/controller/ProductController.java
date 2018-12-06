package com.invoicin.App.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoicin.App.dao.ProductDao;
import com.invoicin.App.entity.Product;

@CrossOrigin
@RestController
@RequestMapping("/Product")

public class ProductController {
	@Autowired
	private ProductDao dao;
	Map<String, Object> map = new HashMap<String, Object>();

	/**
	 * @param page
	 * @param limit
	 * @return localhost:8090/Product/queryProduct/?page=1&rows=10
	 */
	@RequestMapping("/queryProduct")
	public Object queryProduct(Integer page, Integer limit) {
		int startRecord = (page - 1) * limit;
		List<Product> list = new ArrayList<Product>();
		int total = 0;
		total = dao.querycount();
		list = dao.queryall(startRecord, limit);

		map.put("count", total);
		map.put("data", list);
		map.put("code", 0);
		return map;
	}

	/**
	 * @author MAIBENBEN 添加修改方法
	 */
	@RequestMapping("/AddProduct")
	public Object AddProduct(Product Met, Integer productId) {
		if (productId != null) {
			Met.setProductId(productId);
			dao.save(Met);
		} else {
			dao.save(Met);
		}
		int r = 0;
		if (Met != null) {
			r = 1;
		}
		return r;
	}

	/**
	 * @author MAIBENBEN 刪除批量刪除方法
	 */
	@RequestMapping("/deleteproduct")
	public Object deleteproduct(Integer productId, String[] eventids) {
		if (productId != null) {
			Product product = dao.findOne(productId);
			product.setIsdelete(1);
			dao.save(product);
		} else {
			for (String string : eventids) {
				Product product = dao.findOne(Integer.parseInt(string));
				product.setIsdelete(1);
				dao.save(product);
			}
		}
		int r = 0;
		if (productId != null || eventids != null) {
			r = 1;
		}
		return r;
	}
}
