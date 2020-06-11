package team12.product.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team12.product.dto.ProductDTO;
import team12.product.entity.Product;
import team12.product.model.Message;
import team12.product.model.ProductId;
import team12.product.service.ProductService;

@RestController
@RequestMapping("api/products")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public Object addProduct(@RequestBody ProductDTO product) {
        LOGGER.info("Adding Single Product");
        return productService.saveProduct(product.convertToEntity());
    }



    @GetMapping()
    public Object getProducts() {
        LOGGER.info("Retriveing all Products");

        List<Product> products = (List<Product>) productService.getProducts();

        if (products.size() == 0) {
            Message msg = new Message();
            msg.setMessage("No products available");
        }

        return products;
    }



    @GetMapping("/{productId}")
    public Object findProductById(@PathVariable(name = "productId") Integer productId) {
        LOGGER.info("Retriveing Products based on productId " + productId);
        return productService.getProductById(productId);
    }

    @GetMapping("/search/{keyword}")
    public Object searchProducts(@PathVariable(name = "keyword") String keyword) {
        LOGGER.info("Retriveing Products based on keyword " + keyword);

        List<Product> result = productService.getProductsByBrand(keyword);
        result.addAll(productService.getProductsByCategory(keyword));
        result.addAll(productService.getProductsByName(keyword));

        if (result.size() == 0) {
            Message msg = new Message();
            msg.setMessage("No products are available with that keyword");
            return msg;
        }

        return result;
    }

    @DeleteMapping("/remove/{productId}")
    public Object removeProduct(@PathVariable(name = "productId") @NonNull Integer productId) {
        try {
            productService.deleteProduct(productId);

            LOGGER.info("Removing Products based on productId " + productId);
            Message msg = new Message();
            msg.setMessage("Successfully Deleted");
            return msg;

        } catch (Exception e) {
            Message msg = new Message();
            msg.setMessage("Deletion Failed");
            return msg;
        }

    }

    @PutMapping("/update")
    public Object updateProduct(@RequestBody ProductDTO product) {
        Product productEntity = product.convertToEntity();
        LOGGER.info("Updating Product ", product.getProdId());

        if (product.getStock() != null && product.getStock() < 10) {
            Message msg = new Message();
            msg.setMessage("The Stock should be at least 10");
            return msg;

        }

        return productService.updateProduct(productEntity);
    }

    @PutMapping("/update/stock")
    public Object updateStock(@RequestBody ProductDTO product) {
        Product productEntity = product.convertToEntity();
        LOGGER.info("Updating Product Stock", product.getProdId());

        if (product.getStock() != null && product.getStock() < 10) {
            Message msg = new Message();
            msg.setMessage("The Stock should be at least 10");
            return msg;
        }

        return productService.updateStock(productEntity.getProdId(), product.getStock());
    }
}
