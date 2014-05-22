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
import org.lgy.inventory.service.ProductServiceLocal;
import org.lgy.inventory.service.ProductStorageServiceLocal;

/**
 * Servlet implementation class ProductStorageServlet
 */
@WebServlet("/ProductStorageServlet")
public class ProductStorageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductStorageServiceLocal productStorageService;
	@EJB
	private ProductServiceLocal productService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductStorageServlet() {
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
		String action = (String) request.getParameter("action").trim();
		if (action.equals("list"))
			this.getProductForList(request, response);
		if (action.equals("commit"))
			this.commit(request, response);
	}

	private void getProductForList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.addStorageResult(request);
		String index = (String) request.getParameter("pageIndex");
		Integer pageIndex = Integer.parseInt(index);
		String size = (String) request.getParameter("pageSize");
		Integer pageSize = Integer.parseInt(size);
		Pagination pagination = new Pagination();
		pagination.setCurrentPage(pageIndex < 1 ? 1 : pageIndex);
		pagination.setPageSize(pageSize);
		List<Product> products = this.productService.getProducts(pagination);
		request.setAttribute("pagination", pagination);
		request.setAttribute("products", products);
		request.getRequestDispatcher("/productStorage.jsp").forward(request,
				response);
	}

	private void commit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.addStorageResult(request);
		this.productStorageService = this.getProductStorageService(request);
		this.productStorageService.storage();
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	private ProductStorageServiceLocal getProductStorageService(
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		ProductStorageServiceLocal service = (ProductStorageServiceLocal) session
				.getAttribute("productStorageService");
		if (service == null) {
			try {
				Context ctx = new InitialContext();
				service = (ProductStorageServiceLocal) ctx
						.lookup("ejb:InventoryManager/InventoryManagerEJB//ProductStorageServiceBean!org.lgy.inventory.service.ProductStorageServiceLocal?stateful");
				session.setAttribute("productStorageService", service);
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return service;
		}
		return service;
	}

	private void addStorageResult(HttpServletRequest request) {
		String[] storageQuantities = request
				.getParameterValues("storageQuantity");
		if (storageQuantities == null) {
			return;
		}
		String[] ids = request.getParameterValues("id");
		this.productStorageService = this.getProductStorageService(request);
		for (int i = 0; i < ids.length; i++) {
			if (storageQuantities[i] == "")
				continue;
			Integer id = Integer.parseInt(ids[i]);
			Integer quantity = Integer.parseInt(storageQuantities[i]);
			this.productStorageService.addStorageResult(id, quantity);
		}
	}

}
