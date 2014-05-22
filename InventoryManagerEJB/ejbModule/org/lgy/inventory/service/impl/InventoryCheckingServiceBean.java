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
import org.lgy.inventory.service.InventoryCheckingServiceLocal;

@Stateful
public class InventoryCheckingServiceBean implements
		InventoryCheckingServiceLocal {

	@PersistenceContext(unitName = "inventoryPersistenceUnit")
	private EntityManager entityManager;
	private Map<Integer, Integer> checkResult = new HashMap<Integer, Integer>();

	@Override
	public void updateInventory() {
		InventoryTransaction inventTrans = new InventoryTransaction();
		inventTrans.setDate(new Date());
		inventTrans.setType("C");
		if (this.checkResult.keySet().size() != 0) {
			for (Integer id : checkResult.keySet()) {
				Product product = entityManager.find(Product.class, id);
				int currentQuantity = product.getInventory().getQuantity();
				int realQuantity = currentQuantity - checkResult.get(id);
				product.getInventory().setQuantity(
						realQuantity >= 0 ? realQuantity : -realQuantity);
				InventoryTransactionDetail inventTransDetail = new InventoryTransactionDetail();
				inventTransDetail.setProduct(product);
				inventTransDetail.setInventoryTransaction(inventTrans);
				inventTrans.getInventoryTransactions().add(inventTransDetail);
			}
			entityManager.persist(inventTrans);
		}
	}

	@Override
	public void addCheckingResult(Integer pid, Integer difference) {
		this.checkResult.put(pid, difference);
	}

}
