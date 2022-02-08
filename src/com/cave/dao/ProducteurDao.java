package com.cave.dao;

import com.cave.beans.Producteur;
import com.cave.beans.ProducteurShort;

import java.util.List;

public interface ProducteurDao {

    Producteur trouver(long id) throws DAOException;

    void update(Producteur producteur) throws DAOException;

    void update(ProducteurShort producteurShort) throws DAOException;

    List<Producteur> listerPourUtilisateur(Long id_utilisateur);

    List<ProducteurShort> listerShortPourUtilisateur(Long id_utilisateur);

    Long getIdTheSameProdOfUser(Long id_utilisateur, Producteur producteur) throws DAOException;

    Long copyFromProducteur(Long idUtilisateur, Long idProducteur) throws DAOException;

    List<Producteur> listerCommon(Integer paid, Long userId) throws DAOException;

    List<ProducteurShort> listerCommonShort(Integer paid, Long userId) throws DAOException;

    Producteur creerPourUtilisateur(Producteur producteur) throws DAOException;

    Long creerPourUtilisateur(ProducteurShort producteurShort) throws DAOException;

    void supprimer(Long id) throws DAOException;

    Producteur trouver(Producteur producteur) throws DAOException;

    ProducteurShort getShortById(Long id_Producteur) throws DAOException;
}
