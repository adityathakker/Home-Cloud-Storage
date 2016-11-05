-- phpMyAdmin SQL Dump
-- version 4.6.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 05, 2016 at 11:02 AM
-- Server version: 10.0.17-MariaDB
-- PHP Version: 5.6.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `home_cloud`
--

-- --------------------------------------------------------

--
-- Table structure for table `create_tasks_queue`
--

CREATE TABLE `create_tasks_queue` (
  `_id` int(11) NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `path` text NOT NULL,
  `type` varchar(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `executed` varchar(3) NOT NULL DEFAULT 'no',
  `result` varchar(255) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `user` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `create_tasks_queue`
--

INSERT INTO `create_tasks_queue` (`_id`, `file_name`, `path`, `type`, `timestamp`, `executed`, `result`, `comment`, `user`) VALUES
(1, 'adi.txt', 'users_dir/adityathakker', 'file', '2016-10-25 10:21:48', 'yes', 'failed', 'Cannot Delete Folder', 'adityathakker'),
(2, 'syh', 'users_dir/adityathakker', 'file', '2016-10-25 10:44:15', 'yes', 'failed', 'Cannot Delete Folder', 'adityathakker'),
(3, 'rre', 'users_dir/adityathakker', 'file', '2016-10-25 10:55:27', 'yes', 'success', '', 'adityathakker'),
(4, 'dhhfse', 'users_dir/adityathakker', 'file', '2016-10-25 10:56:43', 'yes', 'success', '', 'adityathakker'),
(6, 'add', 'users_dir/adityathakker', 'folder', '2016-10-25 10:58:22', 'yes', 'success', '', 'adityathakker'),
(7, 'add_1', 'users_dir/adityathakker/add', 'file', '2016-10-25 11:06:23', 'yes', 'success', '', 'adityathakker'),
(8, 'sdd_2', 'users_dir/adityathakker/add', 'file', '2016-10-25 11:09:10', 'yes', 'success', '', 'adityathakker'),
(9, 'sub', 'users_dir/adityathakker/add', 'folder', '2016-10-25 11:09:27', 'yes', 'success', '', 'adityathakker'),
(10, 'rsndom', 'users_dir/adityathakker/add/sub', 'folder', '2016-10-25 13:27:39', 'yes', 'success', '', 'adityathakker'),
(11, 'zgfse', 'users_dir/adityathakker/add/sub', 'file', '2016-10-25 13:28:26', 'yes', 'success', '', 'adityathakker'),
(12, 'stuff e', 'users_dir/dthakker', 'file', '2016-10-25 13:29:54', 'yes', 'success', '', 'dthakker'),
(13, 'crying ', 'users_dir/adityathakker/add/sub', 'file', '2016-10-25 13:29:55', 'yes', 'success', '', 'adityathakker'),
(14, 'etched', 'users_dir/dthakker', 'folder', '2016-10-25 13:30:24', 'yes', 'success', '', 'dthakker'),
(15, 'Sen ', 'users_dir/adityathakker/add/sub', 'folder', '2016-10-25 13:30:25', 'yes', 'success', '', 'adityathakker'),
(16, 'ddas', 'users_dir/dthakker', 'file', '2016-10-25 13:32:48', 'yes', 'success', '', 'dthakker'),
(17, 'rggts', 'users_dir/adityathakker/add/sub', 'file', '2016-10-25 13:32:59', 'yes', 'success', '', 'adityathakker'),
(18, 'do ', 'users_dir/adityathakker', 'file', '2016-10-25 15:00:46', 'yes', 'success', '', 'adityathakker'),
(19, 'uthi ', 'users_dir/adityathakker', 'file', '2016-10-25 15:03:31', 'yes', 'success', '', 'adityathakker'),
(20, 'addd', 'users_dir/adityathakker', 'folder', '2016-10-25 15:03:56', 'yes', 'success', '', 'adityathakker'),
(21, 'songs', 'users_dir/dadi', 'folder', '2016-10-25 15:26:19', 'yes', 'success', '', 'dadi'),
(22, 'bhajans', 'users_dir/mom', 'file', '2016-10-25 15:27:17', 'yes', 'success', '', 'mom'),
(23, 'fryigs', 'users_dir/dadi', 'file', '2016-10-25 15:27:17', 'yes', 'success', '', 'dadi'),
(24, 'fcgcgj', 'users_dir/dadi', 'file', '2016-10-25 15:28:21', 'yes', 'success', '', 'dadi'),
(25, 'set by St ', 'users_dir/adityathakker', 'folder', '2016-10-25 15:28:27', 'yes', 'success', '', 'adityathakker'),
(26, 'xiaomi.txt', 'users_dir/adityathakker', 'file', '2016-10-26 09:53:44', 'yes', 'success', '', 'adityathakker'),
(27, 'foldwr ee', 'users_dir/atulk', 'folder', '2016-10-26 09:54:18', 'yes', 'success', '', 'atulk'),
(28, 'tsjj', 'users_dir/os', 'file', '2016-10-26 10:36:56', 'yes', 'success', '', 'os'),
(29, 'weekend', 'users_dir/os', 'file', '2016-10-26 10:37:34', 'yes', 'success', '', 'os'),
(30, 'demo', 'users_dir/os', 'file', '2016-10-27 02:08:54', 'yes', 'success', '', 'os');

-- --------------------------------------------------------

--
-- Table structure for table `delete_tasks_queue`
--

CREATE TABLE `delete_tasks_queue` (
  `_id` int(11) NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `path` text NOT NULL,
  `type` varchar(11) NOT NULL,
  `user` varchar(255) NOT NULL,
  `result` varchar(255) NOT NULL,
  `executed` varchar(3) NOT NULL DEFAULT 'no',
  `comment` varchar(255) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `delete_tasks_queue`
--

INSERT INTO `delete_tasks_queue` (`_id`, `file_name`, `path`, `type`, `user`, `result`, `executed`, `comment`, `timestamp`) VALUES
(5, 'dhhfse', 'users_dir/adityathakker', 'file', 'adityathakker', 'success', 'yes', '', '2016-10-25 11:48:07'),
(6, 'rre', 'users_dir/adityathakker', 'file', 'adityathakker', 'success', 'yes', '', '2016-10-25 11:52:56'),
(7, 'uthi ', 'users_dir/adityathakker', 'file', 'adityathakker', 'success', 'yes', '', '2016-10-25 15:05:04'),
(8, 'bhajans', 'users_dir/mom', 'file', 'mom', 'success', 'yes', '', '2016-10-25 15:28:22'),
(9, 'VID-20161025-WA0004.mp4', 'users_dir/atulk', 'file', 'atulk', 'success', 'yes', '', '2016-10-26 10:27:37'),
(10, 'VID-20161017-WA0003.mp4', 'users_dir/adityathakker', 'file', 'adityathakker', 'success', 'yes', '', '2016-10-26 10:37:33');

-- --------------------------------------------------------

--
-- Table structure for table `tasks_queue`
--

CREATE TABLE `tasks_queue` (
  `_id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `username` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `delete_task_id` int(11) DEFAULT NULL,
  `create_task_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `task_type`
--

CREATE TABLE `task_type` (
  `_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `task_type`
--

INSERT INTO `task_type` (`_id`, `name`) VALUES
(1, 'create_file'),
(2, 'delete_file'),
(3, 'update_file'),
(4, 'move_file'),
(5, 'upload_file');

-- --------------------------------------------------------

--
-- Table structure for table `upload_tasks_queue`
--

CREATE TABLE `upload_tasks_queue` (
  `_id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `executed` varchar(3) NOT NULL DEFAULT 'no',
  `path` text NOT NULL,
  `result` varchar(255) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `size` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `upload_tasks_queue`
--

INSERT INTO `upload_tasks_queue` (`_id`, `username`, `timestamp`, `executed`, `path`, `result`, `comment`, `size`) VALUES
(11, 'adityathakker', '2016-10-25 15:13:59', 'yes', 'users_dir/adityathakker', 'success', 'Uploaded', '2539028'),
(12, 'adityathakker', '2016-10-25 15:27:15', 'yes', 'users_dir/adityathakker', 'success', 'Uploaded', '12838828'),
(13, 'atulk', '2016-10-26 09:53:44', 'yes', 'users_dir/atulk', 'success', 'Uploaded', '7095852'),
(14, 'adityathakker', '2016-10-26 10:38:21', 'yes', 'users_dir/adityathakker', 'success', 'Uploaded', '427880'),
(15, 'os', '2016-10-26 10:38:22', 'yes', 'users_dir/os', 'success', 'Uploaded', '2539028'),
(17, 'os', '2016-10-27 02:09:24', 'yes', 'users_dir/os', 'success', 'Uploaded', '12838828');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `_id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`_id`, `username`, `password`, `name`) VALUES
(2, 'adityathakker', 'aditya12', 'Aditya Thakker'),
(3, 'dthakker', 'dthakker', 'Dharmesh Thakker'),
(4, 'dadi', 'dadi', 'Dadi'),
(5, 'mom', 'mom', 'mom'),
(6, 'atulk', 'atulk', 'Atul Kabadi'),
(7, 'os', 'os', 'os');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `create_tasks_queue`
--
ALTER TABLE `create_tasks_queue`
  ADD PRIMARY KEY (`_id`);

--
-- Indexes for table `delete_tasks_queue`
--
ALTER TABLE `delete_tasks_queue`
  ADD PRIMARY KEY (`_id`);

--
-- Indexes for table `tasks_queue`
--
ALTER TABLE `tasks_queue`
  ADD PRIMARY KEY (`_id`),
  ADD KEY `tasks_queue_username_fk` (`username`),
  ADD KEY `tasks_queue_task_type_fk` (`type`),
  ADD KEY `tasks_queue_create_task_id_fk` (`create_task_id`),
  ADD KEY `tasks_queue_delete_task_id_fk` (`delete_task_id`);

--
-- Indexes for table `task_type`
--
ALTER TABLE `task_type`
  ADD PRIMARY KEY (`_id`);

--
-- Indexes for table `upload_tasks_queue`
--
ALTER TABLE `upload_tasks_queue`
  ADD PRIMARY KEY (`_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `create_tasks_queue`
--
ALTER TABLE `create_tasks_queue`
  MODIFY `_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;
--
-- AUTO_INCREMENT for table `delete_tasks_queue`
--
ALTER TABLE `delete_tasks_queue`
  MODIFY `_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `tasks_queue`
--
ALTER TABLE `tasks_queue`
  MODIFY `_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `task_type`
--
ALTER TABLE `task_type`
  MODIFY `_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `upload_tasks_queue`
--
ALTER TABLE `upload_tasks_queue`
  MODIFY `_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `tasks_queue`
--
ALTER TABLE `tasks_queue`
  ADD CONSTRAINT `tasks_queue_create_task_id_fk` FOREIGN KEY (`create_task_id`) REFERENCES `create_tasks_queue` (`_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tasks_queue_delete_task_id_fk` FOREIGN KEY (`delete_task_id`) REFERENCES `delete_tasks_queue` (`_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tasks_queue_username_fk` FOREIGN KEY (`username`) REFERENCES `users` (`_id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
