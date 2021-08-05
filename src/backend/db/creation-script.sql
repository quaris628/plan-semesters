CREATE SCHEMA IF NOT EXISTS semesters_planner DEFAULT CHARACTER SET UTF8MB3 ;
USE semesters_planner;


-- -----------------------------------------------------
-- Table prereqs
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS prereqs (
  id INT NOT NULL,
  course VARCHAR(45) NULL COMMENT 'If course is not null, this reqirement is exactly one course.\nIf \"is_or\" is true, this requirement is (req1 or req2), else this requirement is (req1 and req2).\n',
  coreq TINYINT NULL,
  req1 INT NULL,
  is_or TINYINT NULL,
  req2 INT NULL,
  created TIMESTAMP DEFAULT current_timestamp,
  updated TIMESTAMP DEFAULT current_timestamp ON UPDATE current_timestamp,
  deleted TINYINT DEFAULT false,
  PRIMARY KEY (id),
  CONSTRAINT atleastoneNN CHECK (course is not NULL or (req1 is not NULL and req2 is not NULL))
);


-- -----------------------------------------------------
-- Table courses
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS courses (
  id VARCHAR(15) NOT NULL,
  full_name VARCHAR(45) NOT NULL,
  credits INT NOT NULL,
  description VARCHAR(500) NULL,
  notes VARCHAR(200) NULL,
  prereqs_id INT NULL,
  created TIMESTAMP DEFAULT current_timestamp,
  updated TIMESTAMP DEFAULT current_timestamp ON UPDATE current_timestamp,
  deleted TINYINT DEFAULT false,
  PRIMARY KEY (id),
  INDEX fk_courses_reqs_idx (prereqs_id ASC) VISIBLE,
  CONSTRAINT fk_courses_reqs
    FOREIGN KEY (prereqs_id)
    REFERENCES prereqs (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table degrees
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS degrees (
  id INT NOT NULL,
  school_name VARCHAR(45) NOT NULL,
  degree_name VARCHAR(45) NOT NULL,
  created TIMESTAMP DEFAULT current_timestamp,
  updated TIMESTAMP DEFAULT current_timestamp ON UPDATE current_timestamp,
  deleted TINYINT DEFAULT false,
  PRIMARY KEY (id)
);


-- -----------------------------------------------------
-- Table degrees_have_courses
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS degrees_have_courses (
  degrees_id INT NOT NULL,
  courses_id VARCHAR(15) NOT NULL,
  created TIMESTAMP DEFAULT current_timestamp,
  updated TIMESTAMP DEFAULT current_timestamp ON UPDATE current_timestamp,
  deleted TINYINT DEFAULT false,
  PRIMARY KEY (degrees_id, courses_id),
  INDEX fk_degrees_has_courses_courses1_idx (courses_id ASC) VISIBLE,
  INDEX fk_degrees_has_courses_degrees1_idx (degrees_id ASC) VISIBLE,
  CONSTRAINT fk_degrees_has_courses_degrees1
    FOREIGN KEY (degrees_id)
    REFERENCES degrees (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_degrees_has_courses_courses1
    FOREIGN KEY (courses_id)
    REFERENCES courses (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);


CREATE USER IF NOT EXISTS 'manage_degrees'@'localhost' IDENTIFIED BY 'f8c79012c3812d3182c0bc9a046b8e9';
GRANT INSERT, SELECT, UPDATE ON semesters_planner.* TO 'manage_degrees'@'localhost';
CREATE USER IF NOT EXISTS 'plan_semesters'@'localhost' IDENTIFIED BY 'b9f0af6ae8bd5a7f90eb7e9a07be6be';
GRANT SELECT ON semesters_planner.* TO 'plan_semesters'@'localhost';
