package com.cave.dao;

import static com.cave.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.cave.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cave.beans.Position;
import com.cave.beans.Rangee;

public class RangeeDaoImpl implements RangeeDao {
    private static final String SQL_SELECT_PAR_ID_COMPARTIMENT = "SELECT id, reference_r, id_compartiment FROM rangee WHERE id_compartiment = ? ORDER BY reference_r DESC";
    private static final String SQL_SELECT_PAR_ID_COMPARTIMENT_ASC = "SELECT id, reference_r, id_compartiment FROM rangee WHERE id_compartiment = ? ORDER BY reference_r ASC";
    private static final String SQL_SELECT_PAR_ID_UTILISATEUR = "SELECT cave.id_utilisateur, utilisateur.nom, utilisateur.date_inscription, "
            + "utilisateur.email, utilisateur.mot_de_passe, compartiment.id_cave, cave.nom, cave.nbr_compartiment, cave.nbr_row, rangee.id_compartiment, "
            + "compartiment.reference_c, rangee.id AS id_rangee, rangee.reference_r FROM rangee INNER JOIN compartiment ON "
            + "compartiment.id = rangee.id_compartiment INNER JOIN cave ON cave.id = compartiment.id_cave INNER JOIN utilisateur ON "
            + "utilisateur.id = cave.id_utilisateur WHERE utilisateur.id = ?;";
    private static final String SQL_SELECT_PAR_ID = "SELECT id, reference_r, id_compartiment FROM rangee WHERE id = ? ORDER BY reference_r DESC";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM rangee WHERE id_compartiment = ? and reference_r > ?";
    private static final String MESSAGE_DAO = " ";

    private static DAOFactory daoFactory;

    public RangeeDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /*
     * *******************************usedALL***********************************
     * *** *********
     */
    @Override
    public void creer(Long id_compartiment, Integer lastRangee, Integer nbrRangee) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sqlInsert = "INSERT INTO Rangee (id_compartiment, reference_r) VALUES";
        while (lastRangee < nbrRangee - 1) {
            lastRangee++;
            sqlInsert = sqlInsert + "(" + id_compartiment + "," + lastRangee + "),";
        }
        lastRangee++;
        sqlInsert = sqlInsert + "(" + id_compartiment + "," + lastRangee + ");";
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, sqlInsert, false);
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if (statut == 0) {
                throw new DAOException(MESSAGE_DAO);
            }

        } catch (

                SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

    }

    @Override
    public List<Rangee> listerPourCompartiment(Long id_compartriment) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Rangee> rangees = new ArrayList<Rangee>();
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ID_COMPARTIMENT, false,
                    id_compartriment);
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            while (resultSet.next()) {
                rangees.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return rangees;
    }

    @Override
    public List<Rangee> listerPourCompartimentAsc(Long id_compartriment) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Rangee> rangees = new ArrayList<Rangee>();
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ID_COMPARTIMENT_ASC, false,
                    id_compartriment);
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            while (resultSet.next()) {
                rangees.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return rangees;
    }

    @Override
    public void supprimerLastRangee(Long id_compartiment, Integer nbrRangee) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        Long id_rangeeCurr = null;
        Rangee lastRangee = null;

        List<Rangee> rangees = listerPourCompartimentAsc(id_compartiment);
        if (rangees != null) {
            for (Rangee rangeeCurr : rangees) {
                lastRangee = rangeeCurr;
                id_rangeeCurr = rangeeCurr.getId();
            }
        }
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE_PAR_ID, false,
                    id_compartiment, nbrRangee);
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if (statut == 0) {
                throw new DAOException(MESSAGE_DAO);
            } else {
                lastRangee.setId(null);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(preparedStatement, connexion);
        }

    }

    @Override
    public List<Rangee> listerPourUtilisateur(Long idSessionUtilisateur) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Rangee> rangees = new ArrayList<Rangee>();
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ID_UTILISATEUR, false,
                    idSessionUtilisateur);
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            while (resultSet.next()) {
                rangees.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return rangees;
    }

    @Override
    public Rangee trouver(long id) throws DAOException {

        return trouver(SQL_SELECT_PAR_ID, id);
    }

    private Rangee trouver(String sql, Object... objets) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Rangee rangee = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement un id) et exécution.
             */
            preparedStatement = initialisationRequetePreparee(connexion, sql, false, objets);
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if (resultSet.next()) {
                rangee = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return rangee;
    }

    /*
     * la correspondance (le mapping) entre une ligne issue de la table des
     * Producteurs (un ResultSet) et un bean Producteur.
     */
    private static Rangee map(ResultSet resultSet) throws SQLException {
        Rangee rangee = new Rangee();
        Long id_rangee = resultSet.getLong("id");
        rangee.setId(id_rangee);
        rangee.setReferenceR(resultSet.getInt("reference_r"));

        PositionDao positionDao = daoFactory.getPositionDao();
        List<Position> positions = positionDao.lister(id_rangee);
        if (!positions.isEmpty()) {
            rangee.setPositions(positions);
            rangee.setNbrPositions(positions.size());
        } else {
            rangee.setNbrPositions(0);
        }
        return rangee;
    }
}
