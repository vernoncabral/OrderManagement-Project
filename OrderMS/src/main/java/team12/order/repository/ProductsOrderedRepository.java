package team12.order.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import team12.order.entity.ProductsOrdered;

public interface ProductsOrderedRepository  extends CrudRepository<ProductsOrdered, Integer>{

}
