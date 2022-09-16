-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
SHOW WARNINGS;
-- -----------------------------------------------------
-- Schema springproject
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `springproject` ;

-- -----------------------------------------------------
-- Schema springproject
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `springproject` DEFAULT CHARACTER SET utf8 ;
SHOW WARNINGS;
USE `springproject` ;

-- -----------------------------------------------------
-- Table `grazhdanstav`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `grazhdanstav` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `grazhdanstav` (
  `idgrazhdanstov` INT(11) NOT NULL AUTO_INCREMENT,
  `namegrazhdanstvo` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idgrazhdanstov`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `okrugs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `okrugs` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `okrugs` (
  `idokrug` INT(11) NOT NULL AUTO_INCREMENT,
  `nameokrug` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`idokrug`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `raioni`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `raioni` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `raioni` (
  `idraion` INT(11) NOT NULL AUTO_INCREMENT,
  `okrugid` INT(11) NOT NULL,
  `nameraion` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`idraion`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `abiturients`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `abiturients` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `abiturients` (
  `idabiturient` INT(11) NOT NULL AUTO_INCREMENT,
  `familia` VARCHAR(100) NOT NULL,
  `ima` VARCHAR(100) NOT NULL,
  `otchestvo` VARCHAR(100) NULL DEFAULT NULL,
  `snils` VARCHAR(15) NOT NULL,
  `seriapasporta` VARCHAR(10) NOT NULL,
  `nomerpasporta` VARCHAR(10) NOT NULL,
  `dateofbirth` DATE NOT NULL,
  `raionid` INT(11) NOT NULL,
  `grazhdanstvoid` INT(11) NOT NULL,
  PRIMARY KEY (`idabiturient`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `basedisciplins`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `basedisciplins` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `basedisciplins` (
  `iddisciplins` INT(11) NOT NULL AUTO_INCREMENT,
  `namedisciplins` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`iddisciplins`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `disciplins_abiturient`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `disciplins_abiturient` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `disciplins_abiturient` (
  `iddisciplinsabiturient` INT(11) NOT NULL AUTO_INCREMENT,
  `disciplinid` INT(11) NOT NULL,
  `abiturientid` INT(11) NOT NULL,
  `mark` INT(11) NOT NULL,
  PRIMARY KEY (`iddisciplinsabiturient`))
ENGINE = InnoDB
AUTO_INCREMENT = 91
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `fakulteti`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `fakulteti` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `fakulteti` (
  `idfakultet` INT(11) NOT NULL AUTO_INCREMENT,
  `namefakultet` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`idfakultet`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `formsobuch`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `formsobuch` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `formsobuch` (
  `idformobuch` INT(11) NOT NULL AUTO_INCREMENT,
  `nameformobuch` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idformobuch`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `specialnosti`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `specialnosti` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `specialnosti` (
  `idspecialnost` INT(11) NOT NULL AUTO_INCREMENT,
  `kodpospo` VARCHAR(40) NOT NULL,
  `namespecialnost` VARCHAR(100) NOT NULL,
  `fakultetid` INT(11) NOT NULL,
  PRIMARY KEY (`idspecialnost`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;
CREATE UNIQUE INDEX `kodpospo_unique` ON `specialnosti` (`kodpospo` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `plannabor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `plannabor` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `plannabor` (
  `idplannabor` INT(11) NOT NULL AUTO_INCREMENT,
  `specialnostid` INT(11) NOT NULL,
  `formsobucid` INT(11) NOT NULL,
  `kolvocheclovek` INT(11) NOT NULL,
  PRIMARY KEY (`idplannabor`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `formsobuch_specialnost`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `formsobuch_specialnost` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `formsobuch_specialnost` (
  `idformsobuchspecialnost` INT(11) NOT NULL AUTO_INCREMENT,
  `abiturientid` INT(11) NOT NULL,
  `plannaboraid` INT(11) NOT NULL,
  PRIMARY KEY (`idformsobuchspecialnost`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;
CREATE UNIQUE INDEX `UK_pc0t0wjn1k0nbs0bd26ylb5q1` ON `formsobuch_specialnost` (`abiturientid` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `users` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `users` (
  `iduser` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `specialnostid` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`iduser`))
ENGINE = InnoDB
AUTO_INCREMENT = 16
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_role` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` INT(11) NOT NULL,
  `roles` VARCHAR(255) NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SHOW WARNINGS;
USE `springproject`;

DELIMITER $$

USE `springproject`$$
DROP TRIGGER IF EXISTS `InsertAfterAddedAbiturient` $$
SHOW WARNINGS$$
USE `springproject`$$
CREATE
DEFINER=`root`@`%`
TRIGGER `springproject`.`InsertAfterAddedAbiturient`
AFTER INSERT ON `springproject`.`abiturients`
FOR EACH ROW
insert into disciplins_abiturient (disciplinid, abiturientid, mark)
select a.iddisciplins, NEW.idabiturient,3 from basedisciplins a$$

SHOW WARNINGS$$

USE `springproject`$$
DROP TRIGGER IF EXISTS `InsertAfterAddedDisciplin` $$
SHOW WARNINGS$$
USE `springproject`$$
CREATE
DEFINER=`root`@`%`
TRIGGER `springproject`.`InsertAfterAddedDisciplin`
AFTER INSERT ON `springproject`.`basedisciplins`
FOR EACH ROW
insert into disciplins_abiturient (disciplinid, abiturientid, mark)
select NEW.iddisciplins, a.idabiturient,3 from abiturients a$$

SHOW WARNINGS$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
