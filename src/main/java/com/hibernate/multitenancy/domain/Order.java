package com.hibernate.multitenancy.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "orders")
public class Order {

    public Order() {
    }

    public Order(Date date) {
        this.date = date;
    }
    
    public Order(Date date,String t_id) {
        this.date = date;
        this.tenantId = t_id;
    }

    @Id
    @Column(nullable = false, name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, name = "date")
    private Date date;
    
    @Column(name="tenant_Id")
    private String tenantId;
}