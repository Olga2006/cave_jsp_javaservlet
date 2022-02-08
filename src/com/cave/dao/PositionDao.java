package com.cave.dao;

import com.cave.beans.Position;
import com.cave.beans.PositionShort;

import java.util.List;

public interface PositionDao {

    List<Position> lister(Long id_rangee) throws DAOException;

    Integer getLastPosition(Long id_rangee) throws DAOException;

    void creer(Long id_rangee, Integer lastPosition, Integer nbrPosition) throws DAOException;

    void supprimerLastPosition(Long id_rangee, Integer nbrPosition) throws DAOException;

    List<Position> listerPourBouteille(Long id_bouteille) throws DAOException;

    List<PositionShort> listerPourBouteilleShort(Long id_bouteille) throws DAOException;

    void vider_la_position(Long idPosition);

    void ajouter_la_bouteille(Long idBouteille, String idPosition) throws DAOException;

    void changer_la_position_bouteille(Long id_bouteille, Long id_position, Long id_last_position)
            throws DAOException;

}
