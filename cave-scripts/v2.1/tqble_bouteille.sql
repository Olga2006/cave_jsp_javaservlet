ALTER TABLE bdd_cave.Bouteille ADD url_achat varchar(100);
ALTER TABLE bdd_cave.Compartiment ADD nom VARCHAR(50) NULL DEFAULT NULL;

DROP TABLE IF EXISTS bdd_cave.Pub;
CREATE TABLE IF NOT EXISTS bdd_cave.Pub
(
    i_id integer NOT NULL AUTO_INCREMENT,
    i_id_utilisateur integer,
    v_code_html text,
    v_image text,
    v_url text,
    is_first INT(1) NULL DEFAULT 0,
    is_second INT(1) NULL DEFAULT 0,
    d_date_synchro datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    d_creat datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (i_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE bdd_cave.Pub
  ADD CONSTRAINT fkey_idutilisateur FOREIGN KEY (i_id_utilisateur) REFERENCES bdd_cave.Utilisateur (id) ON DELETE CASCADE;

ALTER TABLE bdd_cave.Utilisateur ADD is_allowed_ad INT(1) NULL DEFAULT 1 AFTER is_payed;
ALTER TABLE bdd_cave.Producteur ADD is_allowed_cl INT(1) NULL DEFAULT 1;
ALTER TABLE bdd_cave.Bouteille ADD is_allowed_cl INT(1) NULL DEFAULT 1;
ALTER TABLE bdd_cave.Producteur ADD url varchar(100);

ALTER TABLE bdd_cave.Bouteille CHANGE taille taille DECIMAL(10,3) NOT NULL;