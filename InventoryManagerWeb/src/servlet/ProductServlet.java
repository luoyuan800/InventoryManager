package servlet;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lgy.inventory.entity.Product;
import org.lgy.inventory.service.ProductServiceLocal;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	private ProductServiceLocal productService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductServlet() {
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
			this.getProductsForList(request, response);
		else if (action.equals("add"))
			this.addProduct(request, response);
		else if (action.equals("edit"))
			this.editProduct(request, response);
		else if (action.equals("update"))
			this.updateProduct(request, response);
		else if (action.equals("remove"))
			this.removeProduct(request, response);
	}

	private void getProductsForList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String index = request.getParameter("pageIndex");
		String size = request.getParameter("pageSize");
		int pageIndex = 1, pageSize = 3;
		if (index != null) {
			pageIndex = Integer.parseInt(index);
			if (pageIndex < 1)
				pageIndex = 1;
		}
		if (size != null) {
			pageSize = Integer.parseInt(size);
		}
		List<Product> products = this.productService.getProducts(pageIndex,
				pageSize);
		request.setAttribute("products", products);
		request.setAttribute("pageIndex", pageIndex);
		request.getRequestDispatcher("productList.jsp").forward(request,
				response);
	}

	private void addProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		String minStock = request.getParameter("minStock");
		Product product = new Product();
		product.setName(name);
		product.setCode(code);
		product.setMinStock(Integer.parseInt(minStock));
		this.productService.addProduct(product);
		request.getRequestDispatcher(
				"ProductServlet?action=list&pageIndex=1&pageSize=3").forward(
				request, response);
	}

	private void editProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		int id = 1;
		if (pid != null) {
			id = Integer.parseInt(pid);
		}
		Product product = this.productService.getProductById(id);
		request.setAttribute("product", product);
		request.getRequestDispatcher("addProduct.jsp").forward(request,
				response);
	}

	private void updateProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		String minStock = request.getParameter("minStock");
		Product product = new Product();
		product.setId(Integer.parseInt(pid));
		product.setName(name);
		product.setCode(code);
		product.setMinStock(Integer.parseInt(minStock));
		this.productService.updateProduct(product);
		request.getRequestDispatcher(
				"ProductServlet?action=list&pageIndex=1&pageSize=3").forward(
				request, response);
	}

	private void removeProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		Product product = new Product();
		product.setId(Integer.parseInt(pid));
		this.productService.removeProduct(product);
		request.getRequestDispatcher(
				"ProductServlet?action=list&pageIndex=1&pageSize=3").forward(
				request, response);
	}
}
