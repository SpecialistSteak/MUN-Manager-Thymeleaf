-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 07, 2024 at 08:56 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET
SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET
time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mun-manager`
--

-- --------------------------------------------------------

--
-- Table structure for table `assignments`
--

CREATE TABLE `assignments`
(
    `assignment_id`          int(11) NOT NULL,
    `assignment_name`        varchar(255)  DEFAULT NULL,
    `conference_id`          int(11) DEFAULT NULL,
    `due_date`               date NOT NULL DEFAULT current_timestamp(),
    `assignment_description` varchar(255)  DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Table structure for table `conferences`
--

CREATE TABLE `conferences`
(
    `conference_id`   int(11) NOT NULL,
    `conference_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students`
(
    `student_id`    int(11) NOT NULL,
    `student_name`  varchar(255) DEFAULT NULL,
    `student_email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Table structure for table `student_assignments`
--

CREATE TABLE `student_assignments`
(
    `submission_id`               int(11) NOT NULL,
    `student_id`                  int(11) NOT NULL,
    `assignment_id`               int(11) NOT NULL,
    `date_submitted`              datetime(6) DEFAULT NULL,
    `turnitin_score`              int(11) DEFAULT NULL,
    `word_count`                  int(11) DEFAULT NULL,
    `flagged`                     tinyint(1) NOT NULL DEFAULT 0,
    `complete`                    tinyint(1) NOT NULL DEFAULT 0,
    `google_doc_id`               varchar(255) DEFAULT NULL,
    `assignment_parent_folder_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- --------------------------------------------------------

--
-- Table structure for table `student_conferences`
--

CREATE TABLE `student_conferences`
(
    `participation_id` int(11) NOT NULL,
    `student_id`       int(11) NOT NULL,
    `conference_id`    int(11) NOT NULL,
    `delegation`       varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `assignments`
--
ALTER TABLE `assignments`
    ADD PRIMARY KEY (`assignment_id`),
  ADD KEY `fk_conference_id` (`conference_id`);

--
-- Indexes for table `conferences`
--
ALTER TABLE `conferences`
    ADD PRIMARY KEY (`conference_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
    ADD PRIMARY KEY (`student_id`);

--
-- Indexes for table `student_assignments`
--
ALTER TABLE `student_assignments`
    ADD PRIMARY KEY (`submission_id`),
  ADD KEY `AssignmentId` (`assignment_id`),
  ADD KEY `student_id` (`student_id`);

--
-- Indexes for table `student_conferences`
--
ALTER TABLE `student_conferences`
    ADD PRIMARY KEY (`participation_id`),
  ADD KEY `conference_id` (`conference_id`),
  ADD KEY `student_id` (`student_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `assignments`
--
ALTER TABLE `assignments`
    MODIFY `assignment_id` int (11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `conferences`
--
ALTER TABLE `conferences`
    MODIFY `conference_id` int (11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
    MODIFY `student_id` int (11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `student_assignments`
--
ALTER TABLE `student_assignments`
    MODIFY `submission_id` int (11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `student_conferences`
--
ALTER TABLE `student_conferences`
    MODIFY `participation_id` int (11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `assignments`
--
ALTER TABLE `assignments`
    ADD CONSTRAINT `fk_conference_id` FOREIGN KEY (`conference_id`) REFERENCES `conferences` (`conference_id`);

--
-- Constraints for table `student_assignments`
--
ALTER TABLE `student_assignments`
    ADD CONSTRAINT `student_assignments_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  ADD CONSTRAINT `student_assignments_ibfk_2` FOREIGN KEY (`assignment_id`) REFERENCES `assignments` (`assignment_id`),
  ADD CONSTRAINT `student_assignments_ibfk_3` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  ADD CONSTRAINT `student_assignments_ibfk_4` FOREIGN KEY (`assignment_id`) REFERENCES `assignments` (`assignment_id`);

--
-- Constraints for table `student_conferences`
--
ALTER TABLE `student_conferences`
    ADD CONSTRAINT `student_conferences_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  ADD CONSTRAINT `student_conferences_ibfk_2` FOREIGN KEY (`conference_id`) REFERENCES `conferences` (`conference_id`),
  ADD CONSTRAINT `student_conferences_ibfk_3` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  ADD CONSTRAINT `student_conferences_ibfk_4` FOREIGN KEY (`conference_id`) REFERENCES `conferences` (`conference_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
