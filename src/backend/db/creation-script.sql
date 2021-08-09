CREATE SCHEMA IF NOT EXISTS semesters_planner DEFAULT CHARACTER SET UTF8MB3 ;
USE semesters_planner;


-- -----------------------------------------------------
-- Table reqs
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS reqs (
  id INT NOT NULL,
  PRIMARY KEY(id)
);


-- -----------------------------------------------------
-- Table reqs_or
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS reqs_or (
  reqs_id INT NOT NULL,
  req1 INT NOT NULL,
  req2 INT NOT NULL,
  PRIMARY KEY(reqs_id),
  CONSTRAINT reqs_id_fk FOREIGN KEY (reqs_id) REFERENCES reqs (id)
	ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT req1_fk FOREIGN KEY (req1) REFERENCES reqs (id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT req2_fk FOREIGN KEY (req2) REFERENCES reqs (id)
    ON DELETE CASCADE ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table reqs_and
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS reqs_and (
  reqs_id INT NOT NULL,
  req1 INT NOT NULL,
  req2 INT NOT NULL,
  PRIMARY KEY(reqs_id),
  CONSTRAINT and_reqs_id_fk FOREIGN KEY (reqs_id) REFERENCES reqs (id)
	ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT and_req1_fk FOREIGN KEY (req1) REFERENCES reqs (id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT and_req2_fk FOREIGN KEY (req2) REFERENCES reqs (id)
    ON DELETE CASCADE ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table courses
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS courses (
  id VARCHAR(15) NOT NULL,
  req_id INT NOT NULL,
  full_name VARCHAR(45) NOT NULL,
  credits INT NOT NULL,
  prereqs_id INT NULL,
  descr VARCHAR(500) NULL,
  notes VARCHAR(200) NULL,
  created TIMESTAMP DEFAULT current_timestamp,
  updated TIMESTAMP DEFAULT current_timestamp ON UPDATE current_timestamp,
  deleted TINYINT DEFAULT false,
  PRIMARY KEY (id),
  CONSTRAINT fk_courses_reqs FOREIGN KEY (req_id) REFERENCES reqs (id)
	ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_courses_reqs1 FOREIGN KEY (prereqs_id) REFERENCES reqs (id)
    ON DELETE CASCADE ON UPDATE CASCADE
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
-- Table degrees_have_reqs
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS degrees_have_reqs (
  degrees_id INT NOT NULL,
  courses_id VARCHAR(15) NOT NULL,
  created TIMESTAMP DEFAULT current_timestamp,
  updated TIMESTAMP DEFAULT current_timestamp ON UPDATE current_timestamp,
  deleted TINYINT DEFAULT false,
  PRIMARY KEY (degrees_id, courses_id),
  INDEX degrees_index (degrees_id),
  CONSTRAINT fk_degrees_has_reqs_degrees1 FOREIGN KEY (degrees_id) REFERENCES degrees (id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_degrees_has_reqs_courses1 FOREIGN KEY (courses_id) REFERENCES courses (id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE USER IF NOT EXISTS 'manage_degrees'@'localhost' IDENTIFIED BY 'f8c79012c3812d3182c0bc9a046b8e9';
GRANT INSERT, SELECT, UPDATE ON semesters_planner.* TO 'manage_degrees'@'localhost';
CREATE USER IF NOT EXISTS 'plan_semesters'@'localhost' IDENTIFIED BY 'b9f0af6ae8bd5a7f90eb7e9a07be6be';
GRANT SELECT ON semesters_planner.* TO 'plan_semesters'@'localhost';
