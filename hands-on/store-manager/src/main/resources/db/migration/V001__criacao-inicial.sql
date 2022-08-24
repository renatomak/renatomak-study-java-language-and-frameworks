SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE TABLE IF NOT EXISTS `store`.`products` (
                                                  `id` INT NOT NULL AUTO_INCREMENT,
                                                  `name` VARCHAR(255) NOT NULL,
    `quantity` INT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `store`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`orders` (
                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                PRIMARY KEY (`id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `store`.`sales`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `store`.`sales` (
                                               `product_id` INT NOT NULL,
                                               `order_id` INT NOT NULL,
                                               `quantity` INT NULL,
                                               PRIMARY KEY (`product_id`, `order_id`),
    CONSTRAINT `fk_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `store`.`products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_order`
    FOREIGN KEY (`order_id`)
    REFERENCES `store`.`orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
