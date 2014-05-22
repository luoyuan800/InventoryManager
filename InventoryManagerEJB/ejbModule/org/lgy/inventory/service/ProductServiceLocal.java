package org.lgy.inventory.service;

import java.util.List;

import javax.ejb.Local;

import org.lgy.inventory.common.Pagination;
import org.lgy.inventory.entity.Product;

@Local
public interface ProductServiceLocal {
	public List<Product> getProducts(Pagination pagination);

	public Product getProductById(Integer id);

	public void addProduct(Product product);

	public void updateProduct(Product product);

	public void removeProduct(Product product);
}
