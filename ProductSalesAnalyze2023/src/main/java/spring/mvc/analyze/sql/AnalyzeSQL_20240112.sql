-- MySQL Script generated by MySQL Workbench
-- Fri Jan 12 11:30:07 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema analyze
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `analyze` ;

-- -----------------------------------------------------
-- Schema analyze
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `analyze` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `analyze` ;

-- -----------------------------------------------------
-- Table `analyze`.`ecommerce`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `analyze`.`ecommerce` ;

CREATE TABLE IF NOT EXISTS `analyze`.`ecommerce` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `website` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `analyze`.`level`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `analyze`.`level` ;

CREATE TABLE IF NOT EXISTS `analyze`.`level` (
  `levelId` INT NOT NULL AUTO_INCREMENT,
  `levelName` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`levelId`),
  UNIQUE INDEX `username` (`levelName` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `analyze`.`service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `analyze`.`service` ;

CREATE TABLE IF NOT EXISTS `analyze`.`service` (
  `serviceId` INT NOT NULL AUTO_INCREMENT,
  `serviceName` VARCHAR(100) NOT NULL,
  `serviceUrl` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`serviceId`),
  UNIQUE INDEX `serviceName` (`serviceName` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `analyze`.`levelRefService`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `analyze`.`levelRefService` ;

CREATE TABLE IF NOT EXISTS `analyze`.`levelRefService` (
  `levelId` INT NOT NULL,
  `serviceId` INT NOT NULL,
  PRIMARY KEY (`serviceId`, `levelId`),
  INDEX `lrs_levelId_idx` (`levelId` ASC) VISIBLE,
  CONSTRAINT `lrs_levelId`
    FOREIGN KEY (`levelId`)
    REFERENCES `analyze`.`level` (`levelId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `lrs_serviceId`
    FOREIGN KEY (`serviceId`)
    REFERENCES `analyze`.`service` (`serviceId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `analyze`.`productType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `analyze`.`productType` ;

CREATE TABLE IF NOT EXISTS `analyze`.`productType` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `analyze`.`productSubType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `analyze`.`productSubType` ;

CREATE TABLE IF NOT EXISTS `analyze`.`productSubType` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `analyze`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `analyze`.`product` ;

CREATE TABLE IF NOT EXISTS `analyze`.`product` (
  `productId` VARCHAR(20) NOT NULL,
  `productName` VARCHAR(50) NOT NULL,
  `productPrice` INT NOT NULL DEFAULT 0,
  `productBarcode` VARCHAR(100) NULL DEFAULT NULL,
  `productBrand` VARCHAR(100) NOT NULL,
  `productTypeId` INT NULL DEFAULT NULL,
  `productSubTypeId` INT NULL DEFAULT NULL,
  `productImg` VARCHAR(1000) NULL,
  `productDesc` VARCHAR(2000) NULL,
  `isLaunch` INT NOT NULL DEFAULT 0,
  -- `productQty` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`productId`),
  UNIQUE INDEX `productId` (`productId` ASC) VISIBLE,
  INDEX `product_productCategory_idx` (`productTypeId` ASC) VISIBLE,
  INDEX `product_productType_idx` (`productSubTypeId` ASC) VISIBLE,
  CONSTRAINT `product_productCategory`
    FOREIGN KEY (`productTypeId`)
    REFERENCES `analyze`.`productType` (`id`),
  CONSTRAINT `product_productType`
    FOREIGN KEY (`productSubTypeId`)
    REFERENCES `analyze`.`productSubType` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `analyze`.`salesData`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `analyze`.`salesData` ;

CREATE TABLE IF NOT EXISTS `analyze`.`salesData` (
  `trxId` INT NOT NULL AUTO_INCREMENT,
  `ecId` INT NOT NULL,
  `productId` VARCHAR(50) NOT NULL,
  `ecOrderNumber` VARCHAR(100) NOT NULL,
  `ecProductCode` VARCHAR(50) NOT NULL,
  `ecProductType` VARCHAR(50) NULL DEFAULT NULL,
  `ecProductSubType` VARCHAR(50) NULL DEFAULT NULL,
  `ecWarehouse` VARCHAR(50) NOT NULL DEFAULT '轉單',
  `ecSalesQty` INT NOT NULL,
  `ecSalesPrice` INT NOT NULL,
  `ecSalesDate` DATETIME NOT NULL,
  `ecSalesStatus` VARCHAR(50) NOT NULL,
  `createTime` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`trxId`),
  UNIQUE INDEX `combinedUniqueIndex` (`ecOrderNumber` ASC, `productId` ASC, `ecProductCode` ASC, `ecSalesStatus` ASC) VISIBLE,
  INDEX `salesdata_ec_id_idx` (`ecId` ASC) VISIBLE,
  CONSTRAINT `sales_productId`
    FOREIGN KEY (`productId`)
    REFERENCES `analyze`.`product` (`productId`),
  CONSTRAINT `salesdata_ec_id`
    FOREIGN KEY (`ecId`)
    REFERENCES `analyze`.`ecommerce` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `analyze`.`stock`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `analyze`.`stock` ;

CREATE TABLE IF NOT EXISTS `analyze`.`stock` (
  `productId` VARCHAR(50) NOT NULL,
  `ecId` INT NOT NULL,
  `productQty` INT NOT NULL DEFAULT 0,
  `ecProductQty` INT NULL,
  PRIMARY KEY (`productId`, `ecId`),
  INDEX `stock_ec_id_idx` (`ecId` ASC) VISIBLE,
  INDEX `product_inventory_ecId_idx` (`ecId` ASC) VISIBLE,
  CONSTRAINT `stock_ec_id`
    FOREIGN KEY (`ecId`)
    REFERENCES `analyze`.`ecommerce` (`id`),
  CONSTRAINT `stock_product_id`
    FOREIGN KEY (`productId`)
    REFERENCES `analyze`.`product` (`productId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `analyze`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `analyze`.`user` ;

CREATE TABLE IF NOT EXISTS `analyze`.`user` (
  `userId` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `levelId` INT NOT NULL DEFAULT '1',
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `username` (`username` ASC) VISIBLE,
  INDEX `user_levelId_idx` (`levelId` ASC) VISIBLE,
  CONSTRAINT `user_levelId`
    FOREIGN KEY (`levelId`)
    REFERENCES `analyze`.`level` (`levelId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 101
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
