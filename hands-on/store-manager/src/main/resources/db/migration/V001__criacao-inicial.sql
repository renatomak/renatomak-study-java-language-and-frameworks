CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `sales` (
    `quantity` int DEFAULT NULL,
    `product_id` int NOT NULL,
    `order_id` int NOT NULL,
    PRIMARY KEY (`order_id`,`product_id`),
    KEY `FKkxc13g7l4ioljxqyoo15nh051` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;