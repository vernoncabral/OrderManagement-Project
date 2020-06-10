package team12.product.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import team12.product.entity.Subscription;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {

	Iterable<Subscription> findByBuyerid(Integer buyerid);
}
