package com.cave.dao;

import java.util.List;

import com.cave.beans.Rangee;

public interface RangeeDao {

    List<Rangee> listerPourCompartiment( Long id_compartriment ) throws DAOException;

    List<Rangee> listerPourUtilisateur( Long idSessionUtilisateur ) throws DAOException;

    Rangee trouver( long id ) throws DAOException;

    void creer( Long id_compartiment, Integer lastRangee, Integer nbrRangee);

    void supprimerLastRangee( Long id_compartiment, Integer nbrRangee) throws DAOException;

    List<Rangee> listerPourCompartimentAsc( Long id_compartriment ) throws DAOException;

}
