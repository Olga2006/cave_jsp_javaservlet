-- Adminer 4.7.7 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DELIMITER ;;

DROP PROCEDURE IF EXISTS `afficher_all_data_cave`;;
CREATE PROCEDURE `afficher_all_data_cave`(IN `param_id_cave` INT)
BEGIN
DROP TEMPORARY TABLE IF EXISTS all_data_cave;
CREATE TEMPORARY TABLE all_data_cave
SELECT cave.id , cave.nom, cave.id_utilisateur, cave.nbr_compartiment, cave.nbr_row, bouteille.id AS id_bouteille,
bouteille.couleur, bouteille.cru,
bouteille.prix_achat, bouteille.prix_actuelle
FROM bouteille
RIGHT OUTER JOIN positions ON bouteille.id = positions.id_bouteille
RIGHT OUTER JOIN rangee ON rangee.id = positions.id_rangee
RIGHT OUTER JOIN compartiment ON compartiment.id = rangee.id_compartiment
RIGHT OUTER JOIN cave ON cave.id = compartiment.id_cave
WHERE cave.id = param_id_cave;

SELECT DISTINCT(id) into @id
FROM all_data_cave;
SELECT DISTINCT(nom) into @nom
FROM all_data_cave;
SELECT DISTINCT(id_utilisateur) into @id_utilisateur
FROM all_data_cave;
SELECT DISTINCT(nbr_compartiment) into @nbr_compartiment
FROM all_data_cave;
SELECT DISTINCT(nbr_row) into @nbr_row
FROM all_data_cave;

SELECT COUNT(id_bouteille) into @nbr_rouge
FROM all_data_cave  WHERE couleur LIKE 'Rouge';
SELECT COUNT(id_bouteille) into @nbr_blanc
FROM all_data_cave  WHERE couleur LIKE 'Blanc';
SELECT COUNT(id_bouteille) into @nbr_rose
FROM all_data_cave  WHERE couleur LIKE 'Ros?';
SELECT COUNT(id_bouteille) into @nbr_jaune
FROM all_data_cave  WHERE couleur LIKE 'Jaune';
SELECT COUNT(id_bouteille) into @nbr_effervescent
FROM all_data_cave  WHERE couleur LIKE 'Effervescent';
SELECT COUNT(id_bouteille) into @nbr_liquoreux
FROM all_data_cave  WHERE couleur LIKE 'Liquoreux';

SELECT COUNT(id_bouteille) into @nbr_gc
FROM all_data_cave  WHERE cru LIKE 'Grand Cru';
SELECT COUNT(id_bouteille) into @nbr_pc
FROM all_data_cave  WHERE cru LIKE 'Premier Cru';
SELECT COUNT(id_bouteille) into @nbr_cb
FROM all_data_cave  WHERE cru LIKE 'Cru Bourgeois';
SELECT COUNT(id_bouteille) into @nbr_cc
FROM all_data_cave  WHERE cru LIKE 'Cru class?';
SELECT COUNT(id_bouteille) into @nbr_nc
FROM all_data_cave  WHERE cru LIKE 'Non Class?';
SELECT COUNT(id_bouteille) into @nbr_v
FROM all_data_cave  WHERE cru LIKE 'Village';


SELECT COUNT(id_bouteille) into @nbr_total
FROM all_data_cave;
SELECT SUM(prix_actuelle) into @prix_total_actuelle
FROM all_data_cave;
SELECT SUM(prix_achat) into @prix_total_achat
FROM all_data_cave;

SELECT @id AS id,
 @nom AS nom,  @id_utilisateur AS id_utilisateur,  @nbr_compartiment AS nbr_compartiment,  @nbr_row AS nbr_row,
@nbr_rouge AS nbr_rouge ,  @nbr_blanc AS nbr_blanc , @nbr_rose AS nbr_rose, @nbr_jaune AS nbr_jaune, @nbr_effervescent AS  nbr_effervescent,
@nbr_liquoreux AS  nbr_liquoreux,
@nbr_gc AS nbr_gc, @nbr_pc AS nbr_pc, @nbr_cb AS nbr_cb, @nbr_cc AS nbr_cc, @nbr_nc AS nbr_nc, @nbr_v AS nbr_v,
@nbr_total AS nbr_total,
@prix_total_actuelle AS prix_total_actuelle,
@prix_total_achat AS prix_total_achat ;

END;;

DROP PROCEDURE IF EXISTS `afficher_all_utilisateur`;;
CREATE PROCEDURE `afficher_all_utilisateur`()
BEGIN
SELECT * FROM utilisateur;
END;;

DROP PROCEDURE IF EXISTS `afficher_table_utilisateur`;;
CREATE PROCEDURE `afficher_table_utilisateur`(`param_id_utilisateur` INT)
BEGIN
SELECT cave.id_utilisateur, utilisateur.nom as nom_utilisateur, utilisateur.date_inscription, utilisateur.email,
    utilisateur.mot_de_passe, compartiment.id_cave, cave.nom as nom_cave, cave.nbr_compartiment, cave.nbr_row,
    rangee.id_compartiment, compartiment.reference_c, positions.id_rangee, rangee.reference_r, positions.id as id_position, positions.reference_p,
    positions.id_bouteille
    FROM positions
    RIGHT OUTER JOIN rangee ON rangee.id = positions.id_rangee
    INNER JOIN compartiment ON compartiment.id = rangee.id_compartiment
    INNER JOIN cave ON cave.id = compartiment.id_cave
    INNER JOIN utilisateur ON utilisateur.id = cave.id_utilisateur WHERE utilisateur.id = param_id_utilisateur;
END;;

DROP PROCEDURE IF EXISTS `cree_cave`;;
CREATE PROCEDURE `cree_cave`(`param_nom` VARCHAR(20), `param_id_utilisateur` INT, `param_nbr_compartiment` INT, `param_nbr_row` INT, OUT `param_id_cave` INT)
BEGIN
  INSERT INTO cave (nom, id_utilisateur, nbr_compartiment, nbr_row) VALUES (param_nom, param_id_utilisateur, param_nbr_compartiment, param_nbr_row);
  SELECT cave.id INTO param_id_cave FROM cave WHERE nom = param_nom AND id_utilisateur = param_id_utilisateur;
END;;

DROP PROCEDURE IF EXISTS `cree_cave_avec_compartiments`;;
CREATE PROCEDURE `cree_cave_avec_compartiments`(`param_nom` VARCHAR(20), `param_id_utilisateur` INT, `param_nbr_compartiment` INT, `param_nbr_row` INT)
BEGIN
CALL cree_cave(param_nom, param_id_utilisateur, param_nbr_compartiment, param_nbr_row, @id_cave);
SELECT @id_cave;
CALL cree_comps_rows_pour_cave(param_nbr_compartiment, param_nbr_row, @id_cave );
END;;

DROP PROCEDURE IF EXISTS `cree_compartiments_a_pour_cave`;;
CREATE PROCEDURE `cree_compartiments_a_pour_cave`(`param_nbr_compartiment` INT, `param_id_cave` INT)
BEGIN
    DECLARE v_i INT;
    SET v_i = param_nbr_compartiment;

    REPEAT
        INSERT INTO Compartiment (reference_c, id_cave) VALUES ( CONCAT ( 'A',v_i ), param_id_cave);
        SET v_i = v_i - 1;
        UNTIL v_i = 0
        END REPEAT;
END;;

DROP PROCEDURE IF EXISTS `cree_compartiments_b_pour_cave`;;
CREATE PROCEDURE `cree_compartiments_b_pour_cave`(`param_nbr_compartiment` INT, `param_id_cave` INT)
BEGIN
    DECLARE v_i INT;
    SET v_i = param_nbr_compartiment;

    REPEAT
        INSERT INTO Compartiment (reference_c, id_cave) VALUES (CONCAT ( 'B',v_i ), param_id_cave);
        SET v_i = v_i - 1;
        UNTIL v_i = 0
        END REPEAT;
END;;

DROP PROCEDURE IF EXISTS `cree_comps_rows_pour_cave`;;
CREATE PROCEDURE `cree_comps_rows_pour_cave`(`param_nbr_compartiment` INT, `param_nbr_row` INT, `param_id_cave` INT)
corps_procedure:BEGIN
        CALL cree_compartiments_a_pour_cave(param_nbr_compartiment , param_id_cave);

        IF param_nbr_row = 2 THEN
        CALL cree_compartiments_b_pour_cave(param_nbr_compartiment , param_id_cave);
        LEAVE corps_procedure;
        END IF;

END;;

DROP PROCEDURE IF EXISTS `remplacer_bouteille`;;
CREATE PROCEDURE `remplacer_bouteille`(`param_id_bouteille` INT, `param_id_position` INT, `param_id_last_position` INT)
BEGIN
UPDATE positions SET  id_bouteille = param_id_bouteille WHERE id = param_id_position;
UPDATE positions SET  id_bouteille = null WHERE id = param_id_last_position;
END;;

DELIMITER ;

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE `utilisateur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(60) NOT NULL,
  `mot_de_passe` char(60) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `date_inscription` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_payed` int(10) DEFAULT '0',
  `is_allowed_ad` int(1) DEFAULT '1',
  `is_wineproducer` int(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `producteur`;
CREATE TABLE `producteur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(30) NOT NULL,
  `adresse` varchar(80) DEFAULT NULL,
  `contact` varchar(20) DEFAULT NULL,
  `id_utilisateur` int(11) NOT NULL,
  `is_allowed_cl` int(1) DEFAULT '1',
  `url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fkpr_id_utilisateur` (`id_utilisateur`),
  CONSTRAINT `fkpr_id_utilisateur` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `bouteille`;
CREATE TABLE `bouteille` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_producteur` int(11) DEFAULT NULL,
  `couleur` varchar(30) NOT NULL,
  `cru` varchar(30) NOT NULL,
  `appelation` varchar(60) NOT NULL,
  `region` varchar(60) NOT NULL,
  `pays` varchar(30) NOT NULL,
  `taille` decimal(10,3) NOT NULL,
  `prix_achat` decimal(11,2) DEFAULT '0.00',
  `prix_actuelle` decimal(11,2) DEFAULT '0.00',
  `date_de_production` int(4) DEFAULT NULL,
  `date_garder` int(4) DEFAULT NULL,
  `nom` varchar(60) NOT NULL,
  `image` varchar(1000) DEFAULT NULL,
  `id_utilisateur` int(11) NOT NULL,
  `commentaire` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `nbr_list_courses` int(4) DEFAULT '0',
  `evaluation` int(1) DEFAULT '0',
  `url_achat` varchar(100) DEFAULT NULL,
  `is_allowed_cl` int(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fkb_id_producteur` (`id_producteur`),
  KEY `fkb_id_utilisateur` (`id_utilisateur`),
  CONSTRAINT `fkb_id_producteur` FOREIGN KEY (`id_producteur`) REFERENCES `producteur` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fkb_id_utilisateur` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `cave`;
CREATE TABLE `cave` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `id_utilisateur` int(11) NOT NULL,
  `nbr_compartiment` int(2) NOT NULL,
  `nbr_row` int(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fkc_id_utilisateur` (`id_utilisateur`),
  CONSTRAINT `fkc_id_utilisateur` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `compartiment`;
CREATE TABLE `compartiment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reference_c` varchar(3) NOT NULL,
  `id_cave` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fkc_id_cave` (`id_cave`),
  CONSTRAINT `fkc_id_cave` FOREIGN KEY (`id_cave`) REFERENCES `cave` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `rangee`;
CREATE TABLE `rangee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reference_r` int(3) NOT NULL,
  `id_compartiment` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fkr_id_compartiment` (`id_compartiment`),
  CONSTRAINT `fkr_id_compartiment` FOREIGN KEY (`id_compartiment`) REFERENCES `compartiment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `positions`;
CREATE TABLE `positions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reference_p` int(3) NOT NULL,
  `id_rangee` int(11) DEFAULT NULL,
  `id_bouteille` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fkp_id_bouteille` (`id_bouteille`),
  KEY `fkp_id_rangee` (`id_rangee`),
  CONSTRAINT `fkp_id_bouteille` FOREIGN KEY (`id_bouteille`) REFERENCES `bouteille` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fkp_id_rangee` FOREIGN KEY (`id_rangee`) REFERENCES `rangee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `pub`;
CREATE TABLE `pub` (
  `i_id` int(11) NOT NULL AUTO_INCREMENT,
  `i_id_utilisateur` int(11) DEFAULT NULL,
  `v_code_html` text,
  `v_image` text,
  `v_url` text,
  `is_first` int(1) DEFAULT '0',
  `is_second` int(1) DEFAULT '0',
  `d_date_synchro` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `d_creat` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`i_id`),
  KEY `fkp_id_utilisateur` (`i_id_utilisateur`),
  CONSTRAINT `fkp_id_utilisateur` FOREIGN KEY (`i_id_utilisateur`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `app_erreur`;
CREATE TABLE `app_erreur` (
  `i_id` int(11) NOT NULL AUTO_INCREMENT,
  `i_id_utilisateur` int(11) DEFAULT NULL,
  `v_code_erreur` text,
  `v_txt_erreur` text,
  `d_date_synchro` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `d_creat` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`i_id`),
  KEY `fka_id_utilisateur` (`i_id_utilisateur`),
  CONSTRAINT `fka_id_utilisateur` FOREIGN KEY (`i_id_utilisateur`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom_article` varchar(50) NOT NULL,
  `subtheme` varchar(50) DEFAULT NULL,
  `nom_auteur` varchar(20) DEFAULT NULL,
  `article1` text NOT NULL,
  `article2` text,
  `article3` text,
  `date_edition` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `image` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
