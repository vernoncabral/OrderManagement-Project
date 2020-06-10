package team12.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team12.product.entity.Subscription;
import team12.product.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	public Subscription getSubscriptionById(Integer subid) {
		return subscriptionRepository.findById(subid).orElse(null);
	}

	public Subscription saveSubscription(Subscription subscription) {
		return subscriptionRepository.save(subscription);
	}

	public Iterable<Subscription> getSubscriptions(Integer buyerid) {
		return subscriptionRepository.findByBuyerid(buyerid);
	}

	public void deleteSubscription(Subscription subscription) {
		subscriptionRepository.delete(subscription);
	}

}
