package org.lgy.inventory.service;

import javax.ejb.Local;

@Local
public interface ProductStorageServiceLocal {

	public void storage();

	public void addStorageResult(Integer id, Integer quantity);
}
