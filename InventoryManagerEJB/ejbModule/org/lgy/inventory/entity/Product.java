package org.lgy.inventory.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
	private Integer id;
	private String name;
	private String code;
	private Integer minStock;
	private Inventory inventory;
	private Set<InventoryTransactionDetail> inventoryTransactions;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "min_stock")
	public Integer getMinStock() {
		return minStock;
	}

	public void setMinStock(Integer minStock) {
		this.minStock = minStock;
	}

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, mappedBy = "product")
	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	@OneToMany(mappedBy = "product", cascade = { CascadeType.PERSIST,
			CascadeType.REMOVE })
	public Set<InventoryTransactionDetail> getInventoryTransactions() {
		return inventoryTransactions;
	}

	public void setInventoryTransactions(
			Set<InventoryTransactionDetail> inventoryTransactions) {
		this.inventoryTransactions = inventoryTransactions;
	}

}
