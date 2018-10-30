# Spring-Hibernate-Multitenancy2

# Running Application using Insomnia tool

POST http://localhost:9091/orders, No authentication enabled 

Headers must have

X-TenantID as key and a string variable like sample-ten-123 be value

# Mysql DB script

CREATE DATABASE `default_schema` /*!40100 DEFAULT CHARACTER SET latin1 */;

CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `tenant_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
