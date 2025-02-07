package com.example.OnlineShoppingSystem;

import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.example.Model.Orders;
import com.example.Model.Product;
import com.example.Service.*;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class AdminController {
	private AdminController() {}
	@Autowired
	ProductService productService;
	@Autowired
	 OrderService orderService;
	@Autowired
	MainController mainController;
	 
	static {
        // Create a custom console handler
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
 
        // Create a custom formatter
        consoleHandler.setFormatter(new SimpleFormatter() {
            @Override
            public synchronized String format(LogRecord lr) {
                return lr.getMessage() + "\n";
            }
        });
 
        // Get the root logger and remove default handlers
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        for (Handler handler : handlers) {
            rootLogger.removeHandler(handler);
        }
 
        // Add the custom handler to the root logger
        rootLogger.addHandler(consoleHandler);
    }
	private static final Logger logger = Logger.getLogger(AdminController.class.getName());

		    void adminMenu() {
		        Scanner sc = new Scanner(System.in);
		        while (true) {
		            logger.info("Admin Menu:");
		            logger.info("1. Add Product");
		            logger.info("2. Update StockLevels");
		            logger.info("3. Delete Product");
		            logger.info("4. All product list");
		            logger.info("5. Approve Orders");
		            logger.info("6. Generate sales report");
		            logger.info("7. Logout");
		            
	 
		            int choice = sc.nextInt();
		            sc.nextLine(); // consume newline
	 
		            switch (choice) {
		                case 1:
		                    addProduct();
		                    break;
		                case 2:
		                	updateStockLevels();
		                    break;
		                case 3:
		                    deleteProduct();
		                    break;
		                case 4:
		                	getAllProducts();
		                    break;
		                case 5:
		                	approveProducts();
		                    break;
		                case 6:
		                	generateTotalSalesReport();
		                	break;
		                case 7:
		                    return;
		                default:
		                    logger.info("Invalid choice. Try again.");
		            }
		        }
		    }

		   public static void generateTotalSalesReport() {
//			    Map<String, Double> salesReport = orderService.generateTotalSalesReport();
//
//			    logger.info("Total Sales Report:");
//			    for (Map.Entry<String, Double> entry : salesReport.entrySet()) {
//			        logger.info("Product: " + entry.getKey() + ", Total Sales: " + entry.getValue());
//			    }
			}


		   public  void approveProducts() {
			    Scanner sc = new Scanner(System.in);
			    List<Orders> ordersList = orderService.getAllOrders();

			    if (ordersList.isEmpty()) {
			        logger.info("No orders found.");
			        return;
			    }

			    for (Orders order : ordersList) {
			        List<Product> products = order.getProduct();

			        logger.info("Order ID: " + order.getOrderID() + ", Status: " + order.getStatus());
			        for (Product product : products) {
			            logger.info("Product Name: " + product.getproductname() + ", Quantity: " + product.getQuantity() + ", Price: " + product.getPrice());
			        }

			        logger.info("Approve this order? (yes/no)");
			        String response = sc.nextLine();

			        if (response.equalsIgnoreCase("yes")) {
			            orderService.updateOrderStatus(order.getOrderID(), "Order Delivered");
			            logger.info("Order approved.");
			        } else {
			            orderService.updateOrderStatus(order.getOrderID(), "Rejected");
			            logger.info("Order rejected.");
			        }
			    }
			}


		   public void getAllProducts() {
				ArrayList<Product> products = productService.getAllProducts();
		        if (products.isEmpty()) {
		            logger.info("No products found.");
		        } else {
		            for (Product product : products) {
		                System.out.println(product);
		            }
		        }
			
		}

			public  void addProduct() {
		        Scanner sc = new Scanner(System.in);
		        logger.info("Enter product Category:");
		        String category = sc.nextLine();
		        
		        logger.info("Enter product name:");
		        String name = sc.nextLine();
	 
		        logger.info("Enter product description:");
		        String description = sc.nextLine();
	 
		        logger.info("Enter product price:");
		        double price = sc.nextDouble();
		        
	 
		        logger.info("Enter product quantity:");
		        int quantity = sc.nextInt();
	 
		        Product product = new Product(category, name, description, price, quantity);
		        productService.addProduct(product);
	 
		        logger.info("Product added successfully!");
		    }
	 
			public  void updateStockLevels() {
		        Scanner sc = new Scanner(System.in);
		        mainController.browseProducts();
		        
		        logger.info("Enter product ID to update:");
		        int ID = sc.nextInt();
		        
//		        logger.info("Enter product Name to update:");
//		        String name = sc.nextLine();
		        String name =null;
		        ArrayList<Product> products = productService.getAllProducts();
		        
		     // Find the product by ID
		        Product productToUpdate = null;
		        for (Product product : products) {
		            if (product.getId() == ID) {
		                productToUpdate = product;
		                System.out.println(productToUpdate.getproductname());
		                name = productToUpdate.getproductname();
		                break;
		                
		            }
		        }
		        if (productToUpdate == null) {
		            logger.info("Product not found.");
		            return;
		        }
		   	 
		        logger.info("Enter new product price:");
		        double price = sc.nextDouble();
		       
	 
		        logger.info("Enter new product quantity:");
		        int quantity = sc.nextInt();


		     // Update product object
		     productToUpdate.setproductname(name);
		     productToUpdate.setPrice(price);
		     productToUpdate.setQuantity(quantity);

		     // Call service to update product
		     productService.updateProduct(productToUpdate);

		     logger.info("Product updated successfully!");
	 
		        logger.info("Product updated successfully!");
		    }
	 
		    public void deleteProduct() {
		        Scanner sc = new Scanner(System.in);
		        mainController.browseProducts();
		        logger.info("Enter product Name to delete:");
		        String name = sc.nextLine();
		        productService.deleteProduct(name);
		        logger.info("Product deleted successfully!");
		    }
	 
		   
}

