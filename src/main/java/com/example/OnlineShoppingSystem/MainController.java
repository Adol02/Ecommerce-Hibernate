package com.example.OnlineShoppingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.example.Config.Config;
import com.example.Model.Cart;
import com.example.Model.Orders;
import com.example.Model.Product;
import com.example.Model.Rating;
import com.example.Model.User;
import com.example.Model.WishList;
import com.example.Service.*;
@Component
public class MainController {
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

   
	private static final Logger logger = Logger.getLogger(MainController.class.getName());
  static String currentuser;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private WishListService wishListService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private RatingService ratingService;
    
    @Autowired
    private ProductService productService;
    
    
    
    @Autowired
    private AdminController adminController;
    public MainController(OrderService orderService, WishListService wishListService, ProductService productService,
			UserService userService, CartService cartService) {
		super();
		this.orderService = orderService;
		this.wishListService = wishListService;
		this.productService = productService;
		this.userService = userService;
		this.cartService = cartService;
	}
    
    

    public static void main(String[] args) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
      
//        OrderService orderService = context.getBean(OrderService.class);
//        WishListService wishListService = context.getBean(WishListService.class);
//        ProductService productService = context.getBean(ProductService.class);
//        UserService userService = context.getBean(UserService.class);
//        CartService cartService = context.getBean(CartService.class);
           MainController mainController = context.getBean(MainController.class);
           mainController.mainOptions();
    }

    public void mainOptions() {
        Scanner sc = new Scanner(System.in);
        boolean b = true;
        while (b) {
            logger.info("Select option: ");
            logger.info("1. User 2. Admin 3. Browse Products 4. Exit");
            int option = sc.nextInt();
            sc.nextLine(); // Consume newline
            switch (option) {
                case 1:
                    handleUser();
                    break;
                case 2:
                    handleAdmin();
                    break;
                case 3:
                    browseProducts();
                    
                    break;
                case 4:
                    logger.info("Visit Again");
                    System.exit(0);
                    break;
                default:
                    logger.info("Invalid Option");
                    b = false;
                    break;
            }
        }
    }

    private void handleAdmin() {
    	Scanner sc = new Scanner(System.in);
        logger.info("Admin Login");
        logger.info("Enter username:");
        String username = sc.nextLine();
        logger.info("Enter password:");
        String password = sc.nextLine();
        if ("admin".equals(username) && "din".equals(password)) {
            logger.info("Login successful.");
            adminController.adminMenu();
        } else {
            logger.info("Invalid credentials.");
        }
	}

	private void handleUser() {
        Scanner sc = new Scanner(System.in);
        logger.info("1. Register 2. Login");
        int option = sc.nextInt();
        boolean existuser = false;
        sc.nextLine(); // Consume newline

        if (option == 1) {
            logger.info("Enter username:");
            String username = sc.nextLine();
            logger.info("Enter password:");
            String password = sc.nextLine();
            logger.info("Enter email:");
            String email = sc.nextLine();
            logger.info("Enter address:");
            String address = sc.nextLine();

            ArrayList<User> users = userService.getAllUsers();
            for(User u : users) {
            	if(u.getUserName().equals(username) && u.getEmail().equals(email)) {
            		existuser = true;
            		break;
            	}
            }
            		
            if (existuser) {
            	logger.info("User already exists.");
            } else {
                
                userService.registerUser(new User(username, password, email, address));
                logger.info("User registered successfully. Login to continue.");
            }

        } else if (option == 2) {
            logger.info("Enter username:");
            String username = sc.nextLine();
            logger.info("Enter password:");
            String password = sc.nextLine();
            if (userService.authenticateUser(username, password)) {
                logger.info("Login successful.");
                currentuser = username;
                browseProducts();
                userMenu();
            } else {
                logger.info("Invalid credentials.");
            }
        } else {
            logger.info("Invalid Option");
        }
	}


     void browseProducts() {
        List<Product> products = productService.getAllProducts();
        logger.info("Product List:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    void searchByCategory() {
        Scanner sc = new Scanner(System.in);
        logger.info("Enter category to search:");
        String category = sc.nextLine();
        ArrayList<Product> products = productService.getByCategory(category);
        if (products.isEmpty()) {
            logger.info("No products found in category: " + category);
        } else {
            logger.info("Products in category " + category + ":");
            for (Product product : products) {
            	 System.out.println(product);
            }
        }
    }

   public  void cartOptions() {
        Scanner sc = new Scanner(System.in);
        logger.info("Select option: \n1. Check Out\n2. Delete Products from Cart\n3. Go Back");

        int option = sc.nextInt();
        sc.nextLine(); // Consume newline
        switch (option) {
            case 1:
                checkout();
                userMenu();
                break;
            case 2:
                deleteProductsFromCart();
                cartOptions();
                break;
            case 3:
                browseProducts();
              userMenu();
                break;
            default:
                logger.info("Invalid Option");
        }
    }

    
    private  void deleteProductsFromCart() {
    	 ArrayList<Cart> deleteProduct = cartService.getCartItems(currentuser);
    	    Scanner sc = new Scanner(System.in);
    	    logger.info("Select product name to remove:");
    	    String deleteProductName = sc.nextLine();
    	    boolean productFound = false;

    	    if (deleteProduct.isEmpty()) {
    	        logger.info("No items in the cart");
    	    } else {
    	        for (Cart cart : deleteProduct) {
    	            for (Product product : cart.getProduct()) {
    	                if (product.getproductname().equals(deleteProductName)) {
    	                    cartService.removeProductFromCart(currentuser, deleteProductName);
    	                    logger.info(deleteProductName + " removed from the cart successfully");
    	                    productFound = true;
    	                    break;
    	                }
    	            }
    	            if (productFound) {
    	                break;
    	            }
    	        }
    	        if (!productFound) {
    	            logger.info("Enter valid product name");
    	        }
    	    }
		
	}

    private  void checkout() {
        Scanner sc = new Scanner(System.in);
        logger.info("Select product name to checkout:");
        String checkoutProductName = sc.nextLine();

        ArrayList<Cart> cartItems = cartService.getCartItems(currentuser);
        Product selectedProduct = null;

        // Find the product in the cart
        for (Cart cartItem : cartItems) {
            for (Product product : cartItem.getProduct()) {
                if (product.getproductname().equals(checkoutProductName)) {
                    selectedProduct = product;
                    break;
                }
            }
            if (selectedProduct != null) {
                break;
            }
        }

        if (selectedProduct == null) {
            logger.info("Product not found in the cart.");
            return;
        }

        logger.info("Enter quantity:");
        int quantity = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (quantity <= 0) {
            logger.info("Invalid quantity.");
            return;
        }

        if (selectedProduct.getQuantity() < quantity) {
            logger.info("Insufficient quantity available.");
            return;
        }

        double price = selectedProduct.getPrice() * quantity;
        User user = userService.getUser(currentuser);
        List<Product> productList = new ArrayList<>();
        productList.add(selectedProduct);
        Orders order = new Orders(user, quantity, "Order Placed", productList);
        orderService.addCheckout(order);
      // 
        logger.info("Order Placed successfully!");
        productService.reduceProductQuantity(selectedProduct.getproductname(), quantity);
        cartService.removeProductFromCart(currentuser, checkoutProductName);
        System.out.println("checking");
    }


    public void wishListOptions() {
        Scanner sc = new Scanner(System.in);
        logger.info("Select option:\n1. Delete Products from WishList\n3. Go Back");

        int option = sc.nextInt();
        sc.nextLine(); // Consume newline
        switch (option) {
            case 1:
                deleteProductsFromWishList();
                break;
            case 3:
                browseProducts();
                userMenu();
                break;
            default:
                logger.info("Invalid Option");
        }
    }
//
    private  void deleteProductsFromWishList() {
        ArrayList<WishList> deleteProduct = wishListService.getWishList(currentuser);
        Scanner sc = new Scanner(System.in);
        logger.info("Select product name to delete:");
        String deleteProductName = sc.nextLine();
        
        boolean productFound = false;
        
        for (WishList w : deleteProduct) {
            for (Product p : w.getProduct()) {
                if (p.getproductname().equals(deleteProductName)) {
                    wishListService.deleteFromWishlist(currentuser, deleteProductName);
                    logger.info(deleteProductName + " removed from the wishlist");
                    productFound = true;
                    break;
                }
            }
            if (productFound) {
                break;
            }
        }
        
        if (!productFound) {
            logger.info("Product not found in the wishlist.");
        }
        
       ViewWishList();
    }

    void ViewWishList() {
        List<WishList> wishList = wishListService.getWishList(MainController.currentuser);
//        if (wishList.isEmpty()) {
//            logger.info("Add items first");
//            browseProducts();
//        } else {
        
            for (WishList w : wishList) {
                if (w.getProduct() != null) {
                    for (Product p : w.getProduct()) {
                        System.out.println("Product Name: " + p.getproductname() 
                            + ", Price: " + p.getPrice() 
                            + ", Description: " + p.getDescription());
                    }
                } else {
                    logger.info("No products found in wishlist");
                }
            }
            wishListOptions();
        }
//    }

	private  void viewRating() {
	    Scanner sc = new Scanner(System.in);
	    logger.info("Enter the product name:");
	    String productName = sc.nextLine();
	
	    // Fetch ratings by product name
	    ArrayList<Rating> ratingList = ratingService.getRating(productName);
	
	    if (ratingList.isEmpty()) {
	        logger.info("No ratings found for product: " + productName);
	    } else {
	        for (Rating rating : ratingList) {
	            logger.info("Username: " + rating.getUser().getUserName() +
	                        ", Ratings: " + rating.getRating() +
	                        "Product:"+rating.getProduct().getproductname()+
	                        ", Reviews: " + rating.getReview());
	        }
	    }
	}

	private  void viewOrderHistory() {
		Scanner sc = new Scanner(System.in);
	    ArrayList<Orders> orders = orderService.getOrdersByUserName(MainController.currentuser);
	    System.out.println(orders.size());
	
	    if (orders.isEmpty()) {
	        logger.info("No orders found.");
	    } else {
	        for (Orders order : orders) {
	        	System.out.println("orders");
	        	// Iterate over each product in the order
	            if (order.getProduct() != null) {
	                logger.info("Order has products");
	            } else {
	                logger.info("Order products are null");
	            }
	            // Iterate over each product in the order
	            for (Product product : order.getProduct()) {
	            	System.out.println("products");
	            	System.out.println("Order ID: " + order.getOrderID() +
	                            ", Product Name: " + product.getproductname() +
	                            ", Quantity: " + order.getOrderquantity() +
	                            ", Price: " + product.getPrice() +
	                            ", Status: " + order.getStatus());
	          	
	                String pname = product.getproductname();
	                ArrayList<Rating> ratings = ratingService.getRating(pname);
	                System.out.println("want to rate the product");
	                boolean alreadyRated = false;
	                for (Rating rro : ratings) {
	                    if (rro.getProduct().getproductname().equals(pname) && rro.getUser().getUserName().equals(MainController.currentuser)) {
	                        alreadyRated = true;
	                        break;  // Exit the loop as we found a matching review by the same user for the same product
	                    }
	                }
	
	                // Ask for rating and review only if the user hasn't already rated this product and the order status is "Delivered"
	                if (!alreadyRated && order.getStatus().equals("Order Delivered")) {
	                    logger.info("Please provide your rating out of 10:");
	                    int rating = sc.nextInt();
	                    sc.nextLine();  // Consume the newline character left by nextInt()
	                    logger.info("Please provide your review:");
	                    String review = sc.nextLine();
	                    Rating rw = new Rating();
	                    rw.setUser(order.getUser());
	                    rw.setProduct(product);
	                    rw.setRating(rating);
	                    rw.setReview(review);
	                    ratingService.addRating(rw);
	                    logger.info("Thank you for your feedback!");
	                } else if (alreadyRated) {
	                    logger.info("You have already rated this product. Thanks for rating!");
	                }
	                else {
	                	logger.info("the product is not delivered");
	                }
	            }
	        }
	    }
	}

	 void viewCart() {
	        List<Cart> cartItems = cartService.getCartItems(MainController.currentuser);
	        if(cartItems.isEmpty()) {
	        	logger.info("Cart is Empty");
	        }
	        else {
	        logger.info("Product List:");
	        double totalAmount = 0;
	        for (Cart c : cartItems) {
	        	System.out.println("hi");
//	        	User user = c.getUser();
	        	for(Product product :c.getProduct()) {
	        		System.out.println("hi");
	        		System.out.println(
	                        ", Product name: " + product.getproductname() +
	                        ", Quantity: " + product.getQuantity() +
	                        ", Price: " + product.getPrice() +
	                        ", Description: " + product.getDescription());
	        	}
	       
	        
	    }
	   }
		}

	public  void userMenu() {
		
		  Scanner sc = new Scanner(System.in);
		  logger.info("-------------------------------------------------------------------------------");
		  
		  logger.info("Select option:\n1. Add to Cart\n2. Add to Wishlist\n3. Search By Category\n4. View WishList\n5. View Cart\n6. View Order History\n7. View Product Rating\n8. LogOut");
	
	      int option = sc.nextInt();
	      switch (option) {
	      case 1:
	          addToCart();
	         browseProducts();
	          userMenu();
	          break;
	      case 2:
	          addToWishList();
	          browseProducts();
	          userMenu();
	          break;
	      case 3:
	         searchByCategory();
	          userMenu();
	          break;
	      case 4:
	          ViewWishList();
	          userMenu();
	          break;
	      case 5:
	    	  viewCart();
	    	  cartOptions();
	    	  userMenu();
	          break;
	      case 6:
	    	  viewOrderHistory();
	    	  //browseProducts();
	    	 // userMenu();
	          break;
	      case 7:
	      	  viewRating();
	      	  browseProducts();
	          userMenu();
	          break;  
	      case 8:
	      	  mainOptions();
	          break; 
	      default:
	          logger.info("Invalid option.");
	  }   
	      }

	private  void addToWishList() {
	   browseProducts();
	    Scanner sc = new Scanner(System.in);
	    
	    logger.info("Enter the product name to add:");
	    String productName = sc.nextLine();
	    
	    ArrayList<Product> products = productService.getAllProducts();
	    ArrayList<WishList> wishList = wishListService.getWishList(MainController.currentuser);
	
	    Product selectedProduct = null;
	    boolean alreadyInWishlist = false;
	
	    // Check if the product exists
	    for (Product p : products) {
	        if (p.getproductname().equals(productName)) {
	            selectedProduct = p;
	            break;
	        }
	    }
	
	    if (selectedProduct != null) {
	        // Check if the product is already in the wishlist
	        for (WishList w : wishList) {
	            if (w.getProduct().contains(selectedProduct)) {
	                alreadyInWishlist = true;
	                break;
	            }
	        }
	
	        if (alreadyInWishlist) {
	            logger.info("Product already exists in the wishlist.");
	        } else {
	            WishList newWishList = new WishList();
	            newWishList.setUser(userService.getUser(MainController.currentuser));
                List<Product> list = new ArrayList<>();
	            // Ensure the product list is initialized
	            if (newWishList.getProduct() == null) {
	                newWishList.setProduct(list);
	            }
	            newWishList.getProduct().add(selectedProduct);
	            wishListService.addToWishList(newWishList);
	            logger.info("Product added to Wish List.");
	        }
	    } else {
	        logger.info("Product not found.");
	    }
	}

	public void addToCart() {
	    Scanner sc = new Scanner(System.in);
	    logger.info("Enter the product name to add:");
	    String productName = sc.nextLine();
	
	    List<Product> products = productService.getAllProducts();
	    boolean productFound = false;
	    
	    for (Product product : products) {
	        if (product.getproductname().equals(productName)) {
	            productFound = true;
	            logger.info("Enter quantity:");
	            int quantity = sc.nextInt();
	            
	            if (product.getQuantity() < quantity && product.getQuantity() > 0) {
	                logger.info("Only " + product.getQuantity() + " available");
	                break;
	            }
	            
	            if (product.getQuantity() >= quantity) {
	                User currentUser = userService.getUser(MainController.currentuser);
	                List<Product> productList = new ArrayList<>();
	                productList.add(product);
	
	                Cart cartItem = new Cart(quantity, currentUser, productList);
	                cartService.addToCart(cartItem);
	                logger.info("Product added to cart.");
	                break;
	            }
	        }
	    }
	
	    if (!productFound) {
	        logger.info("Product not found");
	    }
	}

}
