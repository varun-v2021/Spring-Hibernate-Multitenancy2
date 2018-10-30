package com.hibernate.multitenancy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hibernate.multitenancy.TenantContext;
import com.hibernate.multitenancy.domain.Order;
import com.hibernate.multitenancy.domain.OrderRepository;

import java.sql.Date;

@RestController
public class OrderController {
	 @Autowired
	    private OrderRepository orderRepository;

	    @RequestMapping(path = "/orders", method= RequestMethod.POST)
	    public ResponseEntity<?> createSampleOrder(@RequestHeader("X-TenantID") String tenantName) {
	        TenantContext.setCurrentTenant(tenantName);

	        //Order newOrder = new Order(new Date(System.currentTimeMillis()));
	        Order newOrder = new Order(new Date(System.currentTimeMillis()),tenantName);
	        orderRepository.save(newOrder);

	        return ResponseEntity.ok(newOrder);
	    }
}
