package org.lgy.inventory.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "inventory_transaction")
public class InventoryTransaction {
	private Integer id;
	private Date date;
	private String type;
	private Set<InventoryTransactionDetail> inventoryTransactions = new HashSet<InventoryTransactionDetail>();

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@OneToMany(mappedBy = "inventoryTransaction", cascade = {
			CascadeType.PERSIST, CascadeType.REMOVE })
	public Set<InventoryTransactionDetail> getInventoryTransactions() {
		return inventoryTransactions;
	}

	public void setInventoryTransactions(
			Set<InventoryTransactionDetail> inventoryTransactions) {
		this.inventoryTransactions = inventoryTransactions;
	}

}
