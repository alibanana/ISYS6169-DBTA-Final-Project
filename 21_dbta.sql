-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 23, 2020 at 06:14 PM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `21_dbta`
--

-- --------------------------------------------------------

--
-- Table structure for table `branch`
--

CREATE TABLE `branch` (
  `branch_id` varchar(8) NOT NULL,
  `branch_name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `branch`
--

INSERT INTO `branch` (`branch_id`, `branch_name`, `address`, `phone`) VALUES
('BRC00001', 'Citos', 'Cilandak Town Square, Lantai 1, Jl. TB Simatupang, Fatmawati, Jakarta Selatan', '6281218548571'),
('BRC00002', 'Kokas', 'Kota Kasablanka, Jl Casablanca Raya Kav.88 Unit 71 Lt : LG Floor Jakarta Selatan', '6281299685928 '),
('BRC00003', 'Gandaria City', 'Gandaria City, Lantai Upper Ground, Jl. Sultan Iskandar Muda, Gandaria, Jakarta Selatan', '6281218672529');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `employee_id` varchar(8) NOT NULL,
  `employee_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `position_id` varchar(8) NOT NULL,
  `branch_id` varchar(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`employee_id`, `employee_name`, `password`, `position_id`, `branch_id`) VALUES
('EMP00001', 'Alifio Rasyid', 'alifio', 'POS00001', ''),
('EMP00002', 'Muchsin Hisyam', 'muchsin', 'POS00002', 'BRC00002'),
('EMP00003', 'Fauzan Athallah', 'fauzan123', 'POS00003', 'BRC00002'),
('EMP00004', 'Jason', 'jason', 'POS00003', 'BRC00003');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` varchar(8) NOT NULL,
  `employee_id` varchar(8) NOT NULL,
  `datetime` datetime NOT NULL,
  `branch_id` varchar(8) NOT NULL,
  `total` int(11) NOT NULL,
  `cash` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `employee_id`, `datetime`, `branch_id`, `total`, `cash`) VALUES
('ORD00001', 'EMP00002', '2020-01-13 18:34:13', 'BRC00002', 270500, 0),
('ORD00002', 'EMP00003', '2020-01-21 14:08:06', 'BRC00002', 204000, 250000),
('ORD00003', 'EMP00003', '2020-01-21 14:08:35', 'BRC00002', 140000, 150000),
('ORD00004', 'EMP00002', '2020-01-21 14:09:04', 'BRC00002', 185000, 200000),
('ORD00005', 'EMP00002', '2020-01-21 17:43:42', 'BRC00003', 35000, 50000),
('ORD00006', 'EMP00002', '2020-01-21 18:10:10', 'BRC00002', 70000, 100000),
('ORD00007', 'EMP00002', '2020-01-21 18:19:33', 'BRC00002', 28000, 29000),
('ORD00008', 'EMP00002', '2020-01-21 18:20:10', 'BRC00002', 28000, 28000),
('ORD00009', 'EMP00002', '2020-01-24 00:07:30', 'BRC00002', 126000, 500000),
('ORD00010', 'EMP00002', '2020-01-24 00:10:05', 'BRC00002', 28000, 100000);

-- --------------------------------------------------------

--
-- Table structure for table `orders_details`
--

CREATE TABLE `orders_details` (
  `order_id` varchar(8) NOT NULL,
  `product_id` varchar(8) NOT NULL,
  `qty` int(11) NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders_details`
--

INSERT INTO `orders_details` (`order_id`, `product_id`, `qty`, `description`) VALUES
('ORD00002', 'PRO00024', 2, ''),
('ORD00002', 'PRO00023', 3, ''),
('ORD00002', 'PRO00004', 1, ''),
('ORD00005', 'PRO00024', 1, ''),
('ORD00001', 'PRO00025', 1, 'q'),
('ORD00001', 'PRO00024', 1, ''),
('ORD00001', 'PRO00021', 2, ''),
('ORD00001', 'PRO00024', 2, ''),
('ORD00006', 'PRO00034', 1, ''),
('ORD00004', 'PRO00023', 3, ''),
('ORD00004', 'PRO00032', 3, ''),
('ORD00004', 'PRO00020', 1, ''),
('ORD00007', 'PRO00023', 1, ''),
('ORD00003', 'PRO00004', 2, ''),
('ORD00003', 'PRO00007', 1, ''),
('ORD00008', 'PRO00023', 1, ''),
('ORD00009', 'PRO00024', 3, ''),
('ORD00009', 'PRO00031', 3, 'AIR'),
('ORD00010', 'PRO00023', 1, '');

-- --------------------------------------------------------

--
-- Table structure for table `position`
--

CREATE TABLE `position` (
  `position_id` varchar(8) NOT NULL,
  `position_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `position`
--

INSERT INTO `position` (`position_id`, `position_name`) VALUES
('POS00001', 'Area Manager'),
('POS00002', 'Branch Manager'),
('POS00003', 'Cashier');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `product_id` varchar(8) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `TypeID` varchar(8) NOT NULL,
  `price` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `product_name`, `TypeID`, `price`) VALUES
('PRO00001', 'Salted Egg Chicken (R)', 'TYP00001', 35000),
('PRO00002', 'Sambal Chicken (R)', 'TYP00001', 40000),
('PRO00003', 'Brown Butter Chicken (R)', 'TYP00001', 35000),
('PRO00004', 'Salted Egg Chicken (L)', 'TYP00001', 50000),
('PRO00005', 'Sambal Chicken (L)', 'TYP00001', 60000),
('PRO00006', 'Brown Butter Chicken (L)', 'TYP00001', 50000),
('PRO00007', 'Salted Egg Dori (R)', 'TYP00002', 40000),
('PRO00008', 'Sambal Dori (R)', 'TYP00002', 45000),
('PRO00009', 'Brown Butter Dori (R)', 'TYP00002', 40000),
('PRO00010', 'Salted Egg Dori (L)', 'TYP00002', 60000),
('PRO00011', 'Sambal Dori (L)', 'TYP00002', 65000),
('PRO00012', 'Brown Butter Dori (L)', 'TYP00002', 60000),
('PRO00013', 'Salted Egg Chicken (L) (No Egg)', 'TYP00001', 80000),
('PRO00014', 'Sambal Chicken (L) (No Egg)', 'TYP00001', 100000),
('PRO00015', 'Brown Butter Chicken (L) (No Egg)', 'TYP00001', 80000),
('PRO00016', 'Salted Egg Dori (L) (No Egg)', 'TYP00002', 100000),
('PRO00017', 'Sambal Dori (L) (No Egg)', 'TYP00002', 105000),
('PRO00018', 'Brown Butter Dori (L) (No Egg)', 'TYP00002', 100000),
('PRO00019', 'Duo Jumbo SE & BB', 'TYP00003', 62000),
('PRO00020', 'Duo Jumbo SE & SA', 'TYP00003', 65000),
('PRO00021', 'Duo Jumbo BB & SA', 'TYP00003', 65000),
('PRO00022', 'Duo Jumbo SE & BB', 'TYP00003', 62000),
('PRO00023', 'Chicken Wings', 'TYP00004', 28000),
('PRO00024', 'Chipslah Original', 'TYP00004', 35000),
('PRO00025', 'Chipslah Spicy', 'TYP00004', 35500),
('PRO00026', 'Fish Skin', 'TYP00004', 62500),
('PRO00027', 'Shaker Fries', 'TYP00004', 20000),
('PRO00028', 'Sauce', 'TYP00005', 5000),
('PRO00029', 'Rice', 'TYP00005', 5000),
('PRO00030', 'Egg', 'TYP00005', 5000),
('PRO00031', 'Bottled Drink', 'TYP00006', 7000),
('PRO00032', 'Nestle Beverages', 'TYP00006', 12000),
('PRO00033', 'Mineral Water', 'TYP00006', 5000),
('PRO00034', 'Set Lah 1 (Chicken)', 'TYP00007', 70000),
('PRO00035', 'Set Lah 2 (Chicken)', 'TYP00007', 70000),
('PRO00036', 'Set Lah 3 (Chicken)', 'TYP00007', 70000),
('PRO00037', 'Set Lah 1 (Dori)', 'TYP00007', 75000),
('PRO00038', 'Set Lah 2 (Dori)', 'TYP00007', 80000),
('PRO00039', 'Set Lah 3 (Dori)', 'TYP00007', 75000);

-- --------------------------------------------------------

--
-- Table structure for table `product_type`
--

CREATE TABLE `product_type` (
  `TypeID` varchar(8) NOT NULL,
  `Type` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product_type`
--

INSERT INTO `product_type` (`TypeID`, `Type`) VALUES
('TYP00001', 'Chicken'),
('TYP00002', 'Dori'),
('TYP00003', 'Eatlah Duo'),
('TYP00004', 'Sides'),
('TYP00005', 'Extras'),
('TYP00006', 'Drinks'),
('TYP00007', 'Set Lah');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `branch`
--
ALTER TABLE `branch`
  ADD PRIMARY KEY (`branch_id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`employee_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `employee_id` (`employee_id`),
  ADD KEY `branch_id` (`branch_id`);

--
-- Indexes for table `orders_details`
--
ALTER TABLE `orders_details`
  ADD KEY `orders_details_ibfk_1` (`order_id`),
  ADD KEY `orders_details_ibfk_2` (`product_id`);

--
-- Indexes for table `position`
--
ALTER TABLE `position`
  ADD PRIMARY KEY (`position_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `TypeID` (`TypeID`);

--
-- Indexes for table `product_type`
--
ALTER TABLE `product_type`
  ADD PRIMARY KEY (`TypeID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`branch_id`);

--
-- Constraints for table `orders_details`
--
ALTER TABLE `orders_details`
  ADD CONSTRAINT `orders_details_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `orders_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE;

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`TypeID`) REFERENCES `product_type` (`TypeID`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
