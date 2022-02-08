package com.cave.dao;

import com.cave.beans.Bouteille;
import com.cave.beans.BouteilleOfProducer;
import com.cave.beans.BouteillePost;

import java.util.List;

public interface BouteilleDao {
    Bouteille creerPourUtilisateur(Bouteille bouteille) throws DAOException;

    Long creerPourUtilisateur(BouteillePost bouteille) throws DAOException;

    /*Long copyFromProducteur(Long idUtilisateur, Long idProducteur, Long idBouteille, String img) throws DAOException;*/

    Bouteille trouver(long id) throws DAOException;

    void update(Bouteille bouteille) throws DAOException;

    void update(BouteillePost bouteille) throws DAOException;

    List<Bouteille> listerPourUtilisateur(Long id_utilisateur) throws DAOException;

    List<Bouteille> listerCommon(Integer paid, Long id_utilisateur) throws DAOException;

    List<Bouteille> listerPourProducteur(Long id_producteur) throws DAOException;

    List<BouteilleOfProducer> listerPourProducteurShort(Long id_producteur) throws DAOException;

    List<Bouteille> listerTheSameBouteillesOfUser(Long id_utilisateur, Bouteille bouteille) throws DAOException;

    void supprimer(Long id) throws DAOException;

    void ajouterCommentaire(String commentaire, Long id) throws DAOException;

    void changerLC(Integer quantite_Acheter, Long id) throws DAOException;

   /* Bouteille trouver(Bouteille bouteille) throws DAOException;*/

    void changerEvaluation(Integer evaluation, Long id) throws DAOException;

}
