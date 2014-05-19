package servlet;

import org.lgy.inventory.service.InventoryCheckingServiceLocal;
import org.lgy.inventory.service.impl.InventoryCheckingServiceBean;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String beanName = InventoryCheckingServiceBean.class.getSimpleName();
		String viewClassName = InventoryCheckingServiceLocal.class.getName();
		System.out.println("beanName=" + beanName + "   viewClassName="
				+ viewClassName);
	}

}
