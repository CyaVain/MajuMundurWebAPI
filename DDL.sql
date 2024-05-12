-- MySQL Script generated by MySQL Workbench
-- Sun May 12 13:02:01 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema majumundurdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `majumundurdb` ;

-- -----------------------------------------------------
-- Schema majumundurdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `majumundurdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `majumundurdb` ;

-- -----------------------------------------------------
-- Table `majumundurdb`.`m_customers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `majumundurdb`.`m_customers` ;

CREATE TABLE IF NOT EXISTS `majumundurdb`.`m_customers` (
  `birth_date` DATE NULL DEFAULT NULL,
  `reward_points` INT NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `id` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `phone_number` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `majumundurdb`.`m_merchants`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `majumundurdb`.`m_merchants` ;

CREATE TABLE IF NOT EXISTS `majumundurdb`.`m_merchants` (
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `id` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  `phone_number` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `majumundurdb`.`m_products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `majumundurdb`.`m_products` ;

CREATE TABLE IF NOT EXISTS `majumundurdb`.`m_products` (
  `price` DOUBLE NULL DEFAULT NULL,
  `code` VARCHAR(255) NULL DEFAULT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `id` VARCHAR(255) NOT NULL,
  `merchant_id` VARCHAR(255) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKlf3l46dixlho0j7gpaq272h1x` (`merchant_id` ASC) VISIBLE,
  CONSTRAINT `FKlf3l46dixlho0j7gpaq272h1x`
    FOREIGN KEY (`merchant_id`)
    REFERENCES `majumundurdb`.`m_merchants` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `majumundurdb`.`m_merchants_products_list`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `majumundurdb`.`m_merchants_products_list` ;

CREATE TABLE IF NOT EXISTS `majumundurdb`.`m_merchants_products_list` (
  `merchants_id` VARCHAR(255) NOT NULL,
  `products_list_id` VARCHAR(255) NOT NULL,
  UNIQUE INDEX `UK_bris2kieej3wtjn3dcfb034t7` (`products_list_id` ASC) VISIBLE,
  INDEX `FKc37nm0sb68px6kk467jgfgipm` (`merchants_id` ASC) VISIBLE,
  CONSTRAINT `FK5s1xgbe5feavs5gilhkgr2pvo`
    FOREIGN KEY (`products_list_id`)
    REFERENCES `majumundurdb`.`m_products` (`id`),
  CONSTRAINT `FKc37nm0sb68px6kk467jgfgipm`
    FOREIGN KEY (`merchants_id`)
    REFERENCES `majumundurdb`.`m_merchants` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `majumundurdb`.`m_rewards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `majumundurdb`.`m_rewards` ;

CREATE TABLE IF NOT EXISTS `majumundurdb`.`m_rewards` (
  `point` INT NULL DEFAULT NULL,
  `code` VARCHAR(255) NULL DEFAULT NULL,
  `id` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `majumundurdb`.`m_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `majumundurdb`.`m_roles` ;

CREATE TABLE IF NOT EXISTS `majumundurdb`.`m_roles` (
  `id` VARCHAR(255) NOT NULL,
  `role` ENUM('ROLE_CUSTOMER', 'ROLE_MERCHANT', 'ROLE_SUPER_ADMIN') NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `majumundurdb`.`m_user_credentials`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `majumundurdb`.`m_user_credentials` ;

CREATE TABLE IF NOT EXISTS `majumundurdb`.`m_user_credentials` (
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `id` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_b4lw157roech7xa6r0bdvub6x` (`email` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `majumundurdb`.`m_user_credentials_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `majumundurdb`.`m_user_credentials_roles` ;

CREATE TABLE IF NOT EXISTS `majumundurdb`.`m_user_credentials_roles` (
  `roles_id` VARCHAR(255) NOT NULL,
  `user_credentials_id` VARCHAR(255) NOT NULL,
  INDEX `FK2ghg274lpejg41qcomwpyj79j` (`roles_id` ASC) VISIBLE,
  INDEX `FKqys66ygpdfltdie9iph1wwpxw` (`user_credentials_id` ASC) VISIBLE,
  CONSTRAINT `FK2ghg274lpejg41qcomwpyj79j`
    FOREIGN KEY (`roles_id`)
    REFERENCES `majumundurdb`.`m_roles` (`id`),
  CONSTRAINT `FKqys66ygpdfltdie9iph1wwpxw`
    FOREIGN KEY (`user_credentials_id`)
    REFERENCES `majumundurdb`.`m_user_credentials` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `majumundurdb`.`trx_reward_transactions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `majumundurdb`.`trx_reward_transactions` ;

CREATE TABLE IF NOT EXISTS `majumundurdb`.`trx_reward_transactions` (
  `purchase_date` DATETIME(6) NULL DEFAULT NULL,
  `customer_id` VARCHAR(255) NULL DEFAULT NULL,
  `id` VARCHAR(255) NOT NULL,
  `reward_id` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKm594b7t47s8ajg320i12nf4xx` (`customer_id` ASC) VISIBLE,
  INDEX `FKilcs6plobmh260cova4pyo56u` (`reward_id` ASC) VISIBLE,
  CONSTRAINT `FKilcs6plobmh260cova4pyo56u`
    FOREIGN KEY (`reward_id`)
    REFERENCES `majumundurdb`.`m_rewards` (`id`),
  CONSTRAINT `FKm594b7t47s8ajg320i12nf4xx`
    FOREIGN KEY (`customer_id`)
    REFERENCES `majumundurdb`.`m_customers` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `majumundurdb`.`trx_transactions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `majumundurdb`.`trx_transactions` ;

CREATE TABLE IF NOT EXISTS `majumundurdb`.`trx_transactions` (
  `purchase_date` DATETIME(6) NULL DEFAULT NULL,
  `customer_id` VARCHAR(255) NULL DEFAULT NULL,
  `id` VARCHAR(255) NOT NULL,
  `merchants_id` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKk19yrdvift1bbd5l70lnlpx4g` (`customer_id` ASC) VISIBLE,
  INDEX `FKhnuan6o8rk0gg6w59h246yg84` (`merchants_id` ASC) VISIBLE,
  CONSTRAINT `FKhnuan6o8rk0gg6w59h246yg84`
    FOREIGN KEY (`merchants_id`)
    REFERENCES `majumundurdb`.`m_merchants` (`id`),
  CONSTRAINT `FKk19yrdvift1bbd5l70lnlpx4g`
    FOREIGN KEY (`customer_id`)
    REFERENCES `majumundurdb`.`m_customers` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `majumundurdb`.`trx_transaction_details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `majumundurdb`.`trx_transaction_details` ;

CREATE TABLE IF NOT EXISTS `majumundurdb`.`trx_transaction_details` (
  `sales_price` DOUBLE NULL DEFAULT NULL,
  `id` VARCHAR(255) NOT NULL,
  `products_id` VARCHAR(255) NULL DEFAULT NULL,
  `transaction_id` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK2uponr08m6n5d9jhxs6bh6uaq` (`products_id` ASC) VISIBLE,
  INDEX `FKjj0uksf0lj5b5s5andtrrcwcf` (`transaction_id` ASC) VISIBLE,
  CONSTRAINT `FK2uponr08m6n5d9jhxs6bh6uaq`
    FOREIGN KEY (`products_id`)
    REFERENCES `majumundurdb`.`m_products` (`id`),
  CONSTRAINT `FKjj0uksf0lj5b5s5andtrrcwcf`
    FOREIGN KEY (`transaction_id`)
    REFERENCES `majumundurdb`.`trx_transactions` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;