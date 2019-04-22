SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema electives_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema electives_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `electives_db` DEFAULT CHARACTER SET utf8 ;
USE `electives_db` ;

-- -----------------------------------------------------
-- Table `electives_db`.`coursestatus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electives_db`.`coursestatus` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `electives_db`.`position`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electives_db`.`position` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `electives_db`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electives_db`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `position` INT(11) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  `surname` VARCHAR(30) NOT NULL,
  `email` VARCHAR(30) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  `phone_number` VARCHAR(20) NULL DEFAULT NULL,
  `pass_recovery_key` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `position_idx` (`position` ASC),
  CONSTRAINT `position`
    FOREIGN KEY (`position`)
    REFERENCES `electives_db`.`position` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `electives_db`.`course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electives_db`.`course` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `teacher_id` INT(11) NULL DEFAULT NULL,
  `max_students_count` INT(11) NOT NULL,
  `description` VARCHAR(300) NULL DEFAULT NULL,
  `course_status` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `teacher_id_idx` (`teacher_id` ASC),
  INDEX `status_idx` (`course_status` ASC),
  CONSTRAINT `course_status`
    FOREIGN KEY (`course_status`)
    REFERENCES `electives_db`.`coursestatus` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `teacher_id`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `electives_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `electives_db`.`studentcourse`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `electives_db`.`studentcourse` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `student_id` INT(11) NOT NULL,
  `course_id` INT(11) NOT NULL,
  `mark` INT(11) NULL DEFAULT NULL,
  `review` VARCHAR(300) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `student_id_idx` (`student_id` ASC),
  INDEX `course_id_idx` (`course_id` ASC),
  CONSTRAINT `course_id`
    FOREIGN KEY (`course_id`)
    REFERENCES `electives_db`.`course` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `student_id`
    FOREIGN KEY (`student_id`)
    REFERENCES `electives_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 149
DEFAULT CHARACTER SET = utf8;


GRANT ALL PRIVILEGES ON electives_db.*  TO 'root'@'localhost';
CREATE USER 'user'@'%' IDENTIFIED BY 'userpass';
GRANT SELECT, INSERT, UPDATE, DELETE ON electives_db.*  TO 'user'@'%';
FLUSH PRIVILEGES;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;