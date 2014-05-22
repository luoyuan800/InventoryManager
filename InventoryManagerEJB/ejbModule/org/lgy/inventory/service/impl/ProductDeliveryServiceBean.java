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
import org.lgy.inventory.service.ProductDeliveryServiceLocal;

@Stateful
public class ProductDeliveryServiceBean implements ProductDeliveryServiceLocal {
	@PersistenceContext(unitName = "inventoryPersistenceUnit")
	private EntityManager entityManager;
	private Map<Integer, Integer> deliveryResult = new HashMap<Integer, Integer>();

	@Override
	public void delivery() {
		InventoryTransaction inventTrans = new InventoryTransaction();
		inventTrans.setDate(new Date());
		inventTrans.setType("出库");
		if (this.deliveryResult.keySet().size() != 0) {
			for (Integer id : this.deliveryResult.keySet()) {
				Product product = this.entityManager.find(Product.class, id);
				product.getInventory().setQuantity(
						product.getInventory().getQuantity()
								- this.deliveryResult.get(id));
				InventoryTransactionDetail inventTransDetail = new InventoryTransactionDetail();
				inventTransDetail.setProduct(product);
				inventTransDetail.setQuantity(deliveryResult.get(id));
				inventTransDetail.setInventoryTransaction(inventTrans);
				inventTrans.getInventoryTransactions().add(inventTransDetail);
			}
			this.entityManager.persist(inventTrans);
		}
	}

	@Override
	public void addDeliveryResult(Integer id, Integer quantity) {
		this.deliveryResult.put(id, quantity);
	}

}
