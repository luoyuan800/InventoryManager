package org.lgy.inventory.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.lgy.inventory.entity.InventoryTransaction;
import org.lgy.inventory.entity.InventoryTransactionDetail;
import org.lgy.inventory.entity.Product;
import org.lgy.inventory.service.ProductStorageServiceLocal;

@Stateful
public class ProductStorageServiceBean implements ProductStorageServiceLocal {

	@PersistenceContext(unitName = "inventoryPersistenceUnit")
	private EntityManager entityManager;
	private Map<Integer, Integer> storageResult = new HashMap<Integer, Integer>();

	@Override
	public void storage() {
		InventoryTransaction inventTrans = new InventoryTransaction();
		inventTrans.setDate(new Date());
		inventTrans.setType("I");
		for (Integer id : this.storageResult.keySet()) {
			Product product = this.entityManager.find(Product.class, id);
			product.getInventory().setQuantity(
					this.storageResult.get(id)
							+ product.getInventory().getQuantity());
			InventoryTransactionDetail inventTransDetail = new InventoryTransactionDetail();
			inventTransDetail.setProduct(product);
			inventTransDetail.setInventoryTransaction(inventTrans);
			inventTrans.getInventoryTransactions().add(inventTransDetail);
		}
		this.entityManager.persist(inventTrans);
	}

	@Override
	public void addStorageResult(Integer id, Integer quantity) {
		this.storageResult.put(id, quantity);
	}

}
