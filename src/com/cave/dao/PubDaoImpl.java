package com.cave.dao;

import com.cave.beans.Pub;
import com.cave.tools.Const;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.cave.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.cave.dao.DAOUtilitaire.initialisationRequetePreparee;

public class PubDaoImpl implements PubDao {

    private static final String SQL_SELECT_PAR_ID = "SELECT * FROM pub WHERE i_id = ?";
    private static final String SQL_SELECT_POUR_UTILISATEUR = "SELECT * FROM pub WHERE i_id_utilisateur = ?";
    private static final String SQL_SELECT_ALL_FIRST_PAYED = "SELECT i_id, i_id_utilisateur, v_code_html, v_image, v_url, is_first, is_second, d_date_synchro, d_creat FROM pub " +
            "left join utilisateur on pub.i_id_utilisateur=utilisateur.id " +
            "WHERE pub.is_first=1 and utilisateur.is_payed >= ?";
    private static final String SQL_SELECT_ALL_SECOND_PAYED = "SELECT i_id, i_id_utilisateur, v_code_html, v_image, v_url, is_first, is_second, d_date_synchro, d_creat FROM pub " +
            "left join utilisateur on pub.i_id_utilisateur=utilisateur.id " +
            "WHERE pub.is_second=1 and utilisateur.is_payed >= ?";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM pub WHERE i_id = ?";
    private static final String SQL_UPDATE = "UPDATE pub SET i_id_utilisateur = ?, v_code_html = ?, v_image = ?, v_url = ?, " +
            "is_first = ?, is_second = ?, d_date_synchro=NOW() WHERE i_id = ?";
    private static final String SQL_INSERT = "INSERT INTO pub (i_id_utilisateur, v_code_html, v_image, v_url, is_first, is_second) " +
            "VALUES (?,?,?,?,?,?)";
    private static final String MESSAGE_DAO = " ";

    private static DAOFactory daoFactory;

    public PubDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public List<Pub> listerPourUtilisateur(Long id_utilisateur) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Pub> pubs = new ArrayList<>();
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_POUR_UTILISATEUR, false,
                    id_utilisateur);
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            while (resultSet.next()) {
                pubs.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return pubs;
    }

    @Override
    public List<Pub> listerFirst() throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Pub> pubs = new ArrayList<Pub>();
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_ALL_FIRST_PAYED, false, Const.PAID_TO_FIRST_AD);
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            while (resultSet.next()) {
                pubs.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return pubs;
    }

    @Override
    public List<Pub> listerSecond() throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Pub> pubs = new ArrayList<>();
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_ALL_SECOND_PAYED, false, Const.PAID_TO_SECOND_AD);
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            while (resultSet.next()) {
                pubs.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return pubs;
    }

    @Override
    public void creer(Pub pub) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true,
                    pub.getIdUtilisateur(), pub.getCodeHtml(), pub.getImage(), pub.getUrl(), pub.getIsFirst(), pub.getIsSecond());
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if (statut == 0) {
                throw new DAOException(MESSAGE_DAO);
            }
            /* Récupération de l'id auto-généré par la requête d'insertion */
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if (valeursAutoGenerees.next()) {
                /*
                 * Puis initialisation de la propriété id du bean Utilisateur
                 * avec sa valeur
                 */
                pub.setId(valeursAutoGenerees.getLong(1));
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
    public void update(Pub pub) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, false,
                    pub.getIdUtilisateur(), pub.getCodeHtml(), pub.getImage(), pub.getUrl(), pub.getIsFirst(), pub.getIsSecond(), pub.getId());

            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if (statut == 0) {
                throw new DAOException(MESSAGE_DAO);
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
        }
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
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(preparedStatement, connexion);
        }
    }

    @Override
    public Pub trouver(long id) throws DAOException {
        return trouverG(SQL_SELECT_PAR_ID, id);
    }

    private Pub trouverG(String sql, Object... objets) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Pub pub = null;

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
                pub = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return pub;
    }

    private static Pub map(ResultSet resultSet) throws SQLException {
        Pub pub = new Pub();
        pub.setId(resultSet.getLong("i_id"));
        pub.setIdUtilisateur(resultSet.getLong("i_id_utilisateur"));
        pub.setCodeHtml(resultSet.getString("v_code_html"));
        pub.setImage(resultSet.getString("v_image"));
        pub.setUrl(resultSet.getString("v_url"));
        pub.setIsFirst(resultSet.getInt("is_first") == 1);
        pub.setIsSecond(resultSet.getInt("is_second") == 1);
        pub.setDateSynchro(resultSet.getTimestamp("d_date_synchro"));
        pub.setDateCreat(resultSet.getTimestamp("d_creat"));
        return pub;
    }
}
