package org.lgy.inventory.service;

import javax.ejb.Local;

@Local
public interface InventoryCheckingServiceLocal {

	public void updateInventory();

	public void addCheckingResult(Integer pid, Integer difference);
}
