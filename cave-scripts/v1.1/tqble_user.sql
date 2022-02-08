ALTER TABLE bdd_cave.Utilisateur ADD is_payed INT(10) NULL DEFAULT 0 AFTER date_inscription;
ALTER TABLE bdd_cave.Utilisateur ADD is_wineproducer INT(1) NULL DEFAULT 0 AFTER is_payed;
ALTER TABLE bdd_cave.Blog ADD image varchar(1000) DEFAULT NULL;

DROP TABLE IF EXISTS bdd_cave.App_erreur;
CREATE TABLE IF NOT EXISTS bdd_cave.App_erreur
(
    i_id integer NOT NULL AUTO_INCREMENT,
    i_id_utilisateur integer,
    v_code_erreur text,
    v_txt_erreur text,
    d_date_synchro datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    d_creat datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (i_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE bdd_cave.App_erreur
  ADD CONSTRAINT fkey_id_utilisateur FOREIGN KEY (i_id_utilisateur) REFERENCES bdd_cave.Utilisateur (id) ON DELETE CASCADE;