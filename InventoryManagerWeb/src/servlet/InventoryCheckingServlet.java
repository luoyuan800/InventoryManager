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

import org.lgy.inventory.entity.Product;
import org.lgy.inventory.service.InventoryCheckingServiceLocal;
import org.lgy.inventory.service.ProductServiceLocal;

/**
 * Servlet implementation class InventoryServlet
 */
@WebServlet("/InventoryCheckingServlet")
public class InventoryCheckingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private ProductServiceLocal productService;
	private InventoryCheckingServiceLocal inventoryCheckingService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InventoryCheckingServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action").trim();
		if (action.equals("list"))
			this.getProductForList(request, response);
		else if (action.equals("commit"))
			this.commit(request, response);
	}

	private void commit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.saveCheckingResults(request, response);
		inventoryCheckingService = this.getInventoryCheckingService(request);
		inventoryCheckingService.updateInventory();
		request.getRequestDispatcher("ProductServlet?pageIndex=1&pageSize=3")
				.forward(request, response);

	}

	private void getProductForList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.saveCheckingResults(request, response);
		String index = request.getParameter("pageIndex");
		Integer pageIndex = Integer.parseInt(index);
		String size = request.getParameter("pageSize");
		Integer pageSize = Integer.parseInt(size);
		List<Product> products = this.productService.getProducts(pageIndex,
				pageSize);
		request.setAttribute("products", products);
		request.setAttribute("pageIndex", pageIndex);
		request.getRequestDispatcher("/inventoryChecking.jsp").forward(request,
				response);
	}

	private void saveCheckingResults(HttpServletRequest request,
			HttpServletResponse response) {
		String[] ids = request.getParameterValues("id");
		if (ids == null)
			return;
		String[] differences = request.getParameterValues("difference");
		inventoryCheckingService = this.getInventoryCheckingService(request);
		for (int i = 0; i < ids.length; i++) {
			if (differences[i] != "") {
				Integer id = Integer.parseInt(ids[i]);
				Integer difference = Integer.parseInt(differences[i]);
				this.inventoryCheckingService.addCheckingResult(id, difference);
			}
		}
	}

	private InventoryCheckingServiceLocal getInventoryCheckingService(
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		InventoryCheckingServiceLocal service = (InventoryCheckingServiceLocal) session
				.getAttribute("inventoryCheckingService");
		if (service == null) {
			try {
				/*
				 * final String appName = ""; //这里是.EAR包的名称，如果你打包成JAR发布的话，这里则留空
				 * 
				 * final String moduleName = "helloworld";
				 * //这里是你发布的JAR文件名，如helloworld.jar,则这里应该为helloworld。去掉后缀即可
				 * 
				 * final String distinctName = ""; //如果没有定义其更详细的名称，则这里留空
				 * 
				 * final String beanName = HelloWorldBean.class.getSimpleName();
				 * beanName=InventoryCheckingServiceBean
				 * //这里为实现类的名称
				 * 
				 * final String viewClassName = HelloWorld.class.getName();
				 * viewClassName=org.lgy.inventory.service.InventoryCheckingServiceLocal
				 * //这里为你的接口名称
				 * 
				 * try { HelloWorld hw = (HelloWorld)context.lookup("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);
				 * System.out.println(hw.sayHello("小人物")); }
				 * catch(NamingException e) { e.printStackTrace(); }
				 */
				Context ctx = new InitialContext();
				service = (InventoryCheckingServiceLocal) ctx
						.lookup("ejb:InventoryManager/InventoryManagerEJB//InventoryCheckingServiceBean!org.lgy.inventory.service.InventoryCheckingServiceLocal?stateful");
				session.setAttribute("inventoryCheckingService", service);
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return service;
	}

}
