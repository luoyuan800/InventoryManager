package org.lgy.inventory.service.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.lgy.inventory.common.Pagination;
import org.lgy.inventory.entity.Inventory;
import org.lgy.inventory.entity.Product;
import org.lgy.inventory.service.ProductServiceLocal;

@Stateless
public class ProductServiceBean implements ProductServiceLocal {

	@PersistenceContext(unitName = "inventoryPersistenceUnit")
	EntityManager entityManager;

	@Override
	public List<Product> getProducts(Pagination pagination) {
		int start = pagination.getStart();
		int pageSize = pagination.getPageSize();
		pagination.setTotalRecords(this.totalRecords());

		Query query = entityManager.createQuery("from Product");
		query.setFirstResult(start);
		query.setMaxResults(pageSize);

		List<Product> products = query.getResultList();

		return products;
	}

	@Override
	public Product getProductById(Integer id) {
		return entityManager.find(Product.class, id);
	}

	@Override
	public void addProduct(Product product) {
		entityManager.persist(product);
		Inventory inventory = new Inventory();
		inventory.setProduct(product);
		product.setInventory(inventory);
	}

	@Override
	public void updateProduct(Product product) {
		entityManager.merge(product);
	}

	@Override
	public void removeProduct(Product product) {
		Product p = entityManager.find(Product.class, product.getId());
		entityManager.remove(p);
	}

	private long totalRecords() {
		Query query = entityManager.createQuery("select count(*) from Product");
		return (Long) query.getSingleResult();
	}

}
