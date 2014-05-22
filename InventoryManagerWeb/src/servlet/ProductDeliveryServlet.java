package servlet;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.lgy.inventory.common.Pagination;
import org.lgy.inventory.entity.Product;
import org.lgy.inventory.service.ProductDeliveryServiceLocal;
import org.lgy.inventory.service.ProductServiceLocal;

/**
 * Servlet implementation class ProductDeliveryServlet
 */
@WebServlet("/ProductDeliveryServlet")
public class ProductDeliveryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductDeliveryServiceLocal productDeliveryService;
	@EJB
	private ProductServiceLocal productService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductDeliveryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action").trim();
		if (action.equals("list"))
			this.getProductForList(request, response);
		else if (action.equals("commit"))
			this.commit(request, response);
	}

	private void getProductForList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.saveDeliveryResult(request, response);
		Integer pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		Pagination pagination = new Pagination();
		pagination.setCurrentPage(pageIndex < 1 ? 1 : pageIndex);
		pagination.setPageSize(pageSize);
		List<Product> products = this.productService.getProducts(pagination);
		request.setAttribute("pagination", pagination);
		// request.setAttribute("pageIndex", pageIndex);
		// request.setAttribute("pageSize", pageSize);
		request.setAttribute("products", products);
		request.getRequestDispatcher("/productDelivery.jsp").forward(request,
				response);
	}

	private void commit(HttpServletRequest request, HttpServletResponse response) {
		this.saveDeliveryResult(request, response);
		this.productDeliveryService = this.getProductDeliveryServie(request);
		this.productDeliveryService.delivery();
	}

	private void saveDeliveryResult(HttpServletRequest request,
			HttpServletResponse response) {
		String[] quantities = request.getParameterValues("deliveryQuantity");
		if (quantities == null)
			return;
		String[] ids = request.getParameterValues("id");
		this.productDeliveryService = this.getProductDeliveryServie(request);
		for (int i = 0; i < ids.length; i++) {
			if (quantities[i] == "")
				continue;
			Integer id = Integer.parseInt(ids[i]);
			Integer quantity = Integer.parseInt(quantities[i]);
			this.productDeliveryService.addDeliveryResult(id, quantity);
		}
	}

	private ProductDeliveryServiceLocal getProductDeliveryServie(
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		ProductDeliveryServiceLocal service = (ProductDeliveryServiceLocal) session
				.getAttribute("productDeliveryService");
		if (service == null) {
			try {
				Context ctx = new InitialContext();
				service = (ProductDeliveryServiceLocal) ctx
						.lookup("ejb:InventoryManager/InventoryManagerEJB//ProductDeliveryServiceBean!org.lgy.inventory.service.ProductDeliveryServiceLocal?stateful");
				session.setAttribute("productDeliveryService", service);
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return service;
	}

}
