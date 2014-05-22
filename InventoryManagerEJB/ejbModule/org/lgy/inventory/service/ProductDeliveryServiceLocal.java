package org.lgy.inventory.service;

import javax.ejb.Local;

@Local
public interface ProductDeliveryServiceLocal {

	public void delivery();

	public void addDeliveryResult(Integer id, Integer quantity);
}
