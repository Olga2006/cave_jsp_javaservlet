package com.cave.dao;

import com.cave.beans.Cave;

import java.util.List;

public interface CaveDao {
    void creer(Cave cave) throws DAOException;

    Cave trouver(long id) throws DAOException;

    Cave trouver(Cave cave) throws DAOException;

    void supprimer(Long id_cave) throws DAOException;

    List<Long> listerIdCavesPourUtilisateur(Long id_sessionUtilisateur) throws DAOException;

    void update(Cave cave) throws DAOException;

}
