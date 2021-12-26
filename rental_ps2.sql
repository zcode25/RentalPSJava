-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 26 Des 2021 pada 03.57
-- Versi server: 10.4.18-MariaDB
-- Versi PHP: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rental_ps2`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `members`
--

CREATE TABLE `members` (
  `id_members` char(5) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `tel` char(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `members`
--

INSERT INTO `members` (`id_members`, `name`, `email`, `tel`) VALUES
('M0001', 'Adam Zein', 'adamzein@gmail.com', '081316671373'),
('M0002', 'Budi Aprianto', 'budiaprianto@gmail.com', '081345212345'),
('M0003', 'Ahmad Arifin', 'ahmadarifin@gmail.com', '081243784321'),
('M0004', 'Aditya Ramadhan', 'adityaramadhan@gmail.com', '081323453214'),
('M0005', 'Asep Kurniawan', 'asepkurniawan@gmail.com', '081312454321'),
('M0006', 'Hermawan', 'hermawan@gmail.com', '081324565432'),
('M0007', 'Setiawan', 'setiawan@gmail.com', '081323453212');

-- --------------------------------------------------------

--
-- Struktur dari tabel `packages`
--

CREATE TABLE `packages` (
  `id_packages` char(5) NOT NULL,
  `name_packages` varchar(50) NOT NULL,
  `durasi` int(5) NOT NULL,
  `harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `packages`
--

INSERT INTO `packages` (`id_packages`, `name_packages`, `durasi`, `harga`) VALUES
('D0001', 'Paket 1 Jam', 1, 3000),
('D0002', 'Paket 2 Jam', 2, 5000),
('D0003', 'Paket 3 Jam', 3, 7000),
('D0004', 'Paket 4 Jam', 4, 10000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `places`
--

CREATE TABLE `places` (
  `id_places` char(5) NOT NULL,
  `type_ps` varchar(50) NOT NULL,
  `merek_monitor` varchar(50) NOT NULL,
  `lebar_monitor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `places`
--

INSERT INTO `places` (`id_places`, `type_ps`, `merek_monitor`, `lebar_monitor`) VALUES
('P0001', 'PS 3', 'LG', 27),
('P0002', 'PS 3', 'LG', 27),
('P0003', 'PS 4', 'Polytron', 32),
('P0004', 'PS 4', 'Polytron', 32),
('P0005', 'PS 5', 'Polytron', 32);

-- --------------------------------------------------------

--
-- Struktur dari tabel `transactions`
--

CREATE TABLE `transactions` (
  `id_transactions` char(5) NOT NULL,
  `id_members` char(5) NOT NULL,
  `id_packages` char(5) NOT NULL,
  `id_places` char(5) NOT NULL,
  `tanggal` date NOT NULL,
  `mulai` time NOT NULL,
  `selesai` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `transactions`
--

INSERT INTO `transactions` (`id_transactions`, `id_members`, `id_packages`, `id_places`, `tanggal`, `mulai`, `selesai`) VALUES
('T0001', 'M0001', 'D0003', 'P0003', '2021-11-24', '17:53:44', '20:53:44'),
('T0002', 'M0002', 'D0002', 'P0001', '2021-11-24', '19:25:28', '21:25:28'),
('T0003', 'M0007', 'D0004', 'P0002', '2021-11-24', '22:59:59', '02:59:59'),
('T0004', 'M0004', 'D0004', 'P0001', '2021-11-24', '23:03:38', '03:03:38'),
('T0005', 'M0005', 'D0001', 'P0005', '2021-11-24', '23:07:00', '00:07:00'),
('T0006', 'M0003', 'D0002', 'P0003', '2021-11-24', '23:07:57', '01:07:57'),
('T0007', 'M0006', 'D0004', 'P0004', '2021-11-24', '23:09:07', '03:09:07');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `members`
--
ALTER TABLE `members`
  ADD PRIMARY KEY (`id_members`);

--
-- Indeks untuk tabel `packages`
--
ALTER TABLE `packages`
  ADD PRIMARY KEY (`id_packages`);

--
-- Indeks untuk tabel `places`
--
ALTER TABLE `places`
  ADD PRIMARY KEY (`id_places`);

--
-- Indeks untuk tabel `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id_transactions`),
  ADD KEY `id_members` (`id_members`),
  ADD KEY `id_packages` (`id_packages`),
  ADD KEY `id_places` (`id_places`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `transactions`
--
ALTER TABLE `transactions`
  ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`id_members`) REFERENCES `members` (`id_members`) ON UPDATE CASCADE,
  ADD CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`id_places`) REFERENCES `places` (`id_places`) ON UPDATE CASCADE,
  ADD CONSTRAINT `transactions_ibfk_3` FOREIGN KEY (`id_packages`) REFERENCES `packages` (`id_packages`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
