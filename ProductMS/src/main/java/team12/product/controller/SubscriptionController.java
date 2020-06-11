package team12.product.controller;

import team12.product.dto.SubscriptionDTO;
import team12.product.entity.Subscription;
import team12.product.model.Message;
import team12.product.service.SubscriptionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("api/subscriptions")
public class SubscriptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);

    @Value("${buyerAPIURL}")
    public String buyerAPIURI;
    @Autowired
    public RestTemplate restTemplate;
    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/{buyerId}")
    public Object getSubscription(@PathVariable Integer buyerId) {
    	System.out.println(buyerId);
        LOGGER.info("Retrive Subscription For buyer " + buyerId);

        List<Subscription> subscriptions = (List<Subscription>) subscriptionService.getSubscriptions(buyerId);

        if (subscriptions.size() == 0) {
            Message msg = new Message();
            msg.setMessage("No Subscriptions done yet");
        }

        return subscriptions;
    }

    @PostMapping("/add")
    public Object addSubscription(@RequestBody SubscriptionDTO subscription) {
        LOGGER.info("Add Subscription For buyer " + subscription.getBuyerid());
        
        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(buyerAPIURI + subscription.getBuyerid(), Boolean.class);

        if (responseEntity.getBody() != null && responseEntity.getBody()) {
            subscriptionService.saveSubscription(subscription.convertToEntity());
            Message msg = new Message();
            msg.setMessage("Subscription Successful :)");
            return msg;
        }

        Message msg = new Message();
        msg.setMessage("Subscription Failed! You have to be a Privileged Buyer :(");
        return msg;
    }

    @DeleteMapping("/remove")
    public Object removeSubscription(@RequestBody SubscriptionDTO subscription) {
        subscriptionService.deleteSubscription(subscription.convertToEntity());

        if (subscriptionService.getSubscriptionById(subscription.getSubId()) == null) {
            LOGGER.info("Remove Subscription For buyer " + subscription.getBuyerid());

            Message msg = new Message();
            msg.setMessage("Subscription Cancelled Successfully :)");
            return msg;
        }
        Message msg = new Message();
        msg.setMessage("Subscription Cancelled has Failed :(");
        return msg;
    }


}
