package team12.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team12.product.entity.Product;
import team12.product.model.ProductId;
import team12.product.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}


	public Iterable<Product> getProducts() {
		return productRepository.findAll();
	}

	public Product getProductById(Integer id) {
		return productRepository.findById(id).orElse(null);
	}

	public List<Product> getProductsByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}

	public List<Product> getProductsByCategory(String category) {
		return productRepository.findByCategory(category);
	}

	public List<Product> getProductsByName(String productname) {
		return productRepository.findByProductname(productname);
	}

	public void deleteProduct(Integer productId) {
		productRepository.deleteById(productId);
	}

	public Product updateProduct(Product product) {
		Product existingProduct = productRepository.findById(product.getProdId()).orElse(null);
		if (existingProduct != null) {
			existingProduct.setProductname(product.getProductname());
			existingProduct.setBrand(product.getBrand());
			existingProduct.setPrice(product.getPrice());
			existingProduct.setDescription(product.getDescription());
			existingProduct.setCategory(product.getCategory());
			existingProduct.setSubCategory(product.getSubcategory());
			existingProduct.setImage(product.getImage());
			existingProduct.setStock(product.getStock());
			return productRepository.save(existingProduct);
		}
		return null;
	}

	public Product updateStock(Integer prodId, Integer stock) {
		Product existingProduct = productRepository.findById(prodId).orElse(null);
		if (existingProduct != null) {
			existingProduct.setStock(stock);
			return productRepository.save(existingProduct);
		}
		return null;
	}


}
