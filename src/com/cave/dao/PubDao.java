package com.cave.dao;

import com.cave.beans.Pub;

import java.util.List;

public interface PubDao {

    List<Pub> listerPourUtilisateur(Long id_utilisateur) throws DAOException;

    List<Pub> listerFirst() throws DAOException;

    List<Pub> listerSecond() throws DAOException;

    void creer(Pub pub) throws DAOException;

    void update(Pub pub) throws DAOException;

    void supprimer(Long id) throws DAOException;

    Pub trouver(long id) throws DAOException;

}
