package com.cave.dao;

import com.cave.beans.Utilisateur;
import com.cave.beans.UtilisateurAuth;
import com.cave.beans.UtilisateurShort;

import java.util.List;
import java.util.Map;

public interface UtilisateurDao {

    String setSqlUpdate(String sql) throws DAOException;

    List<UtilisateurShort> listerPersDataUsers() throws DAOException;

    Long creer(Utilisateur utilisateur, Boolean isAdmin) throws DAOException;

    Long creer(UtilisateurAuth utilisateur, Boolean isAdmin, Integer isPayed) throws DAOException;

    void update(Utilisateur utilisateur, Boolean isAdmin) throws DAOException;

    void update(UtilisateurAuth utilisateur, Boolean isAdmin, Integer isPayed) throws DAOException;

    void supprimer(Long id) throws DAOException;

    Utilisateur trouver(String email) throws DAOException;

    UtilisateurShort trouverShort(String email) throws DAOException;

    Map<String, String> trouverNomMDP(String email) throws DAOException;

    Utilisateur trouver(long id) throws DAOException;

    Utilisateur verifier(String email, String motDePasse) throws DAOException;

    Long trouverIdParEmail(String email) throws DAOException;

}
