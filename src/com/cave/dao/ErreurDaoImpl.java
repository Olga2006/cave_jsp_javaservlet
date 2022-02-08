package com.cave.dao;

import com.cave.beans.Erreur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.cave.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.cave.dao.DAOUtilitaire.initialisationRequetePreparee;

public class ErreurDaoImpl implements ErreurDao {
    private static final String SQL_SELECT_ALL_ERREURS = "SELECT * FROM app_erreur";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM app_erreur WHERE i_id = ?";
    private static final String SQL_INSERT = "INSERT INTO app_erreur (i_id_utilisateur, v_code_erreur, v_txt_erreur, d_date_synchro, d_creat ) VALUES (?, ?, ?, NOW(), NOW())";

    private static final String MESSAGE_DAO = " ";

    private static DAOFactory daoFactory;

    public ErreurDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void creer(Erreur erreur) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            Long utilisateurId = (erreur.getUtilisateurId() != null) ? erreur.getUtilisateurId() : null;
            preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, utilisateurId,
                    erreur.getCodErreur(), erreur.getTxtErreur());
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if (statut == 0) {
                throw new DAOException(MESSAGE_DAO);
            }
            /* Récupération de l'id auto-généré par la requête d'insertion */
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if (valeursAutoGenerees.next()) {

                erreur.setId(valeursAutoGenerees.getLong(1));
            } else {
                throw new DAOException(
                        MESSAGE_DAO);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
        }
    }

    @Override
    public List<Erreur> listerErreurs() throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Erreur> erreurs = new ArrayList<Erreur>();
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_ALL_ERREURS, false);
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            while (resultSet.next()) {
                erreurs.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return erreurs;
    }


    @Override
    public void supprimer(Long id) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE_PAR_ID, false,
                    id);
            preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            /*
             * if ( statut == 0 ) { throw new DAOException( MESSAGE_DAO ); }
             * else { id = null; }
             */

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(preparedStatement, connexion);
        }

    }


    private static Erreur map(ResultSet resultSet) throws SQLException {
        Erreur erreur = new Erreur();
        erreur.setId(resultSet.getLong("i_id"));
        erreur.setUtilisateurId(resultSet.getLong("i_id_utilisateur"));
        erreur.setCodErreur(resultSet.getString("v_code_erreur"));
        erreur.setDateCreat(resultSet.getTimestamp("d_creat"));
        erreur.setDateSynchro(resultSet.getTimestamp("d_date_synchro"));
        erreur.setTxtErreur(resultSet.getString("v_txt_erreur"));
        return erreur;
    }
}
