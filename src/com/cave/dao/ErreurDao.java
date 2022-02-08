package com.cave.dao;

import com.cave.beans.Erreur;

import java.util.List;

public interface ErreurDao {

    void creer(Erreur erreur) throws DAOException;

    List<Erreur> listerErreurs() throws DAOException;

    void supprimer(Long id) throws DAOException;

}
