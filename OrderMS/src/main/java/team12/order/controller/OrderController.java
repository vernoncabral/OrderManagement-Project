package team12.order.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.client.RestTemplate;

import team12.order.dto.Order;
import team12.order.dto.Product;
import team12.order.dto.ProductId;
import team12.order.entity.OrderEntity;
import team12.order.entity.ProductsOrdered;
import team12.order.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderservice;
	@Autowired
	public RestTemplate restTemplate;
	@Value("${userServiceUrl}")
	public String cartServiceUrl;
	@Value("${productServiceUrl}")
	public String productServiceUrl;
	
	
	@RequestMapping("/orders/{buyerId}")
	public ArrayList <Order> getOrders(@PathVariable("buyerId") Integer buyerId ) {
		return orderservice.getAllOrders(buyerId);	
	}
	

	
	@PostMapping("/orders/placeOrder")
	public String placeOrder(@RequestBody Order order) {
		
		String cartServiceUrl1=cartServiceUrl+"cart/checkout/"+order.getBuyerId();		
		try {
        ResponseEntity<Product[]> responseEntity = restTemplate.getForEntity(cartServiceUrl1, Product[].class);   
        Product[] objects = responseEntity.getBody();

		// Product Service needs array of product Ids
		List<Integer> prodIds= new ArrayList<Integer>();
		for(int i=0;i<objects.length;i++) {prodIds.add(objects[i].getProdId());}
		
		// Populating that array in ProductId object to POST a request
		ProductId prodId1=new ProductId();
		prodId1.setProdId(prodIds);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ProductId> request = new HttpEntity<ProductId>(prodId1, headers);
		
		// Contacting the Product Service, receives an array of Product Objects
		ResponseEntity<Product[]> products1 = restTemplate.postForEntity(productServiceUrl,request, Product[].class);
		Product[] products12 = products1.getBody();
		ArrayList<Product> products=new ArrayList<Product>();
		for(int i=0;i<products12.length;i++) {
			products.add(products12[i]);
		}
		// To set the quantity, received from the Cart, into the corresponding product Objects
		products.forEach(element->{
			for(int j=0;j<objects.length;j++) {
				if(objects[j].getProdId().equals(element.getProdId())) {
					element.setQuantity(objects[j].getQuantity());
				}
			}
		});
		order.setOrderedProducts(products);
		
		orderservice.placeOrder(order);
		}
		catch(Exception e){
			e.printStackTrace();
			return "Error in placing the order! Contact your Admin";
		}
		return "Order placed Sucessfully";
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/orders/seller/status")
	public String updateStatus (@RequestBody Product product) {
		Integer orderId=product.getOrderId();
		Integer prodId=product.getProdId();
		String status= product.getStatus();
		return orderservice.updateStatus(orderId,prodId,status);
	}
	
	
	@DeleteMapping("/orders/cancel/{orderId}")
	public String cancelOrder(@PathVariable("orderId") Integer orderId ) {
		return orderservice.cancelAnOrder(orderId);
	}
}