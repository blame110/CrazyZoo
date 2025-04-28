-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema crazyzoo
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema crazyzoo
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `crazyzoo` DEFAULT CHARACTER SET utf8 ;
USE `crazyzoo` ;

-- -----------------------------------------------------
-- Table `crazyzoo`.`Jaula`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `crazyzoo`.`Jaula` ;

CREATE TABLE IF NOT EXISTS `crazyzoo`.`Jaula` (
  `idJaula` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  `ubicacion` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idJaula`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `crazyzoo`.`Animal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `crazyzoo`.`Animal` ;

CREATE TABLE IF NOT EXISTS `crazyzoo`.`Animal` (
  `idAnimal` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `especie` VARCHAR(45) NOT NULL,
  `fecha_nac` DATETIME NOT NULL,
  `sexo` VARCHAR(1) NOT NULL,
  `Jaula_idJaula` INT NOT NULL,
  PRIMARY KEY (`idAnimal`),
  CONSTRAINT `fk_Animal_Jaula`
    FOREIGN KEY (`Jaula_idJaula`)
    REFERENCES `crazyzoo`.`Jaula` (`idJaula`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Animal_Jaula_idx` ON `crazyzoo`.`Animal` (`Jaula_idJaula` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `crazyzoo`.`Veterinario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `crazyzoo`.`Veterinario` ;

CREATE TABLE IF NOT EXISTS `crazyzoo`.`Veterinario` (
  `idVeterinario` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `especialidad` VARCHAR(45) NOT NULL,
  `telefono` VARCHAR(45) NOT NULL,
  `sueldo` DECIMAL(5,2) NULL,
  PRIMARY KEY (`idVeterinario`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `crazyzoo`.`Comida`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `crazyzoo`.`Comida` ;

CREATE TABLE IF NOT EXISTS `crazyzoo`.`Comida` (
  `idComida` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `precio` DECIMAL(4,2) NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idComida`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `crazyzoo`.`Animal_has_Comida`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `crazyzoo`.`Animal_has_Comida` ;

CREATE TABLE IF NOT EXISTS `crazyzoo`.`Animal_has_Comida` (
  `Animal_idAnimal` INT NOT NULL,
  `Comida_idComida` INT NOT NULL,
  `cantidad` INT NOT NULL,
  `fecha` DATETIME NOT NULL,
  PRIMARY KEY (`Animal_idAnimal`, `Comida_idComida`),
  CONSTRAINT `fk_Animal_has_Comida_Animal1`
    FOREIGN KEY (`Animal_idAnimal`)
    REFERENCES `crazyzoo`.`Animal` (`idAnimal`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Animal_has_Comida_Comida1`
    FOREIGN KEY (`Comida_idComida`)
    REFERENCES `crazyzoo`.`Comida` (`idComida`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Animal_has_Comida_Comida1_idx` ON `crazyzoo`.`Animal_has_Comida` (`Comida_idComida` ASC) VISIBLE;

CREATE INDEX `fk_Animal_has_Comida_Animal1_idx` ON `crazyzoo`.`Animal_has_Comida` (`Animal_idAnimal` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `crazyzoo`.`Animal_has_Veterinario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `crazyzoo`.`Animal_has_Veterinario` ;

CREATE TABLE IF NOT EXISTS `crazyzoo`.`Animal_has_Veterinario` (
  `Animal_idAnimal` INT NOT NULL,
  `Veterinario_idVeterinario` INT NOT NULL,
  `tratamiento` VARCHAR(45) NULL,
  PRIMARY KEY (`Animal_idAnimal`, `Veterinario_idVeterinario`),
  CONSTRAINT `fk_Animal_has_Veterinario_Animal1`
    FOREIGN KEY (`Animal_idAnimal`)
    REFERENCES `crazyzoo`.`Animal` (`idAnimal`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Animal_has_Veterinario_Veterinario1`
    FOREIGN KEY (`Veterinario_idVeterinario`)
    REFERENCES `crazyzoo`.`Veterinario` (`idVeterinario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Animal_has_Veterinario_Veterinario1_idx` ON `crazyzoo`.`Animal_has_Veterinario` (`Veterinario_idVeterinario` ASC) VISIBLE;

CREATE INDEX `fk_Animal_has_Veterinario_Animal1_idx` ON `crazyzoo`.`Animal_has_Veterinario` (`Animal_idAnimal` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
