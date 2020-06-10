package team12.order.repository;

import org.springframework.data.repository.CrudRepository;

import team12.order.entity.OrderEntity;

public interface OrderRepository extends CrudRepository<OrderEntity, Integer>{

}
