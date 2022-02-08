package com.cave.dao;

import com.cave.beans.*;
import com.cave.tools.Const;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cave.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.cave.dao.DAOUtilitaire.initialisationRequetePreparee;

public class UtilisateurDaoImpl implements UtilisateurDao {
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM utilisateur";
    private static final String SQL_SELECT_PAR_ID = "SELECT * FROM utilisateur WHERE id = ?";
    private static final String SQL_SELECT_PAR_EMAIL_MDP = "SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ? ";
    private static final String SQL_SELECT_PAR_EMAIL = "SELECT * FROM utilisateur WHERE email = ?";
    private static final String SQL_SELECT_MDP_NOM_PAR_EMAIL = "SELECT nom, mot_de_passe FROM utilisateur WHERE email = ?";
    private static final String SQL_SELECT_ID_PAR_EMAIL = "SELECT id FROM utilisateur WHERE email = ?";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM utilisateur WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE utilisateur SET email = ?, mot_de_passe = ?, nom = ?, is_wineproducer = ?, is_allowed_ad = ?  WHERE id = ? ";
    private static final String SQL_UPDATE_BY_ADMIN = "UPDATE utilisateur SET email = ?, mot_de_passe = ?, nom = ?, is_payed = ?, is_wineproducer = ?, is_allowed_ad = ?  WHERE id = ? ";
    private static final String SQL_INSERT = "INSERT INTO utilisateur (email, mot_de_passe, nom, date_inscription, is_wineproducer, is_allowed_ad) VALUES (?, ?, ?, NOW(), ?, ?)";
    private static final String SQL_INSERT_BY_ADMIN = "INSERT INTO utilisateur (email, mot_de_passe, nom, date_inscription, is_payed, is_wineproducer, is_allowed_ad) VALUES (?, ?, ?, NOW(), ?, ?, ?)";
    private static final String MESSAGE_DAO = " ";
    private static DAOFactory daoFactory;


    public UtilisateurDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public String setSqlUpdate(String sql) throws DAOException {
        String resultat;
        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            /* Récupération d'une connexion depuis la Factory */
            conn = daoFactory.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            resultat = "true";
        } catch (SQLException e) {
            resultat = MESSAGE_DAO + e;
        } finally {
            fermeturesSilencieuses(resultSet, stmt, conn);
        }
        return resultat;
    }


    @Override
    public List<UtilisateurShort> listerPersDataUsers() throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<UtilisateurShort> utilisateurs = new ArrayList<UtilisateurShort>();
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_ALL_USERS, false);
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            while (resultSet.next()) {
                utilisateurs.add(mapPersonalData(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return utilisateurs;
    }

    @Override
    public Long creer(Utilisateur utilisateur, Boolean isAdmin) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        Long id;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            if (isAdmin) {
                preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT_BY_ADMIN, true, utilisateur.getEmail(),
                        utilisateur.getMotDePasse(), utilisateur.getNom(), utilisateur.getIsPayed(),
                        utilisateur.getIsWineproducer(), utilisateur.getIsAllowedAD());
            } else {
                preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, utilisateur.getEmail(),
                        utilisateur.getMotDePasse(), utilisateur.getNom(), utilisateur.getIsWineproducer(),
                        utilisateur.getIsAllowedAD());
            }
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
                id = valeursAutoGenerees.getLong(1);
            } else {
                throw new DAOException(
                        MESSAGE_DAO);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
        }
        return id;
    }

    @Override
    public Long creer(UtilisateurAuth utilisateur, Boolean isAdmin, Integer isPayed ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        Long id;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            if (isAdmin) {
                preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT_BY_ADMIN, true, utilisateur.getEmail(),
                        utilisateur.getMotDePasse(), utilisateur.getNom(), isPayed,
                        utilisateur.getIsWineproducer(), utilisateur.getIsAllowedAD());
            } else {
                preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, utilisateur.getEmail(),
                        utilisateur.getMotDePasse(), utilisateur.getNom(), utilisateur.getIsWineproducer(),
                        utilisateur.getIsAllowedAD());
            }
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
                id = valeursAutoGenerees.getLong(1);
            } else {
                throw new DAOException(
                        MESSAGE_DAO);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
        }
        return id;
    }

    @Override
    public void update(Utilisateur utilisateur, Boolean isAdmin) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            if (isAdmin) {
                preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_BY_ADMIN, true, utilisateur.getEmail(),
                        utilisateur.getMotDePasse(), utilisateur.getNom(), utilisateur.getIsPayed(), utilisateur.getIsWineproducer(),
                        utilisateur.getIsAllowedAD(), utilisateur.getId());
            } else {
                preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, true, utilisateur.getEmail(),
                        utilisateur.getMotDePasse(), utilisateur.getNom(), utilisateur.getIsWineproducer(),
                        utilisateur.getIsAllowedAD(), utilisateur.getId());
            }

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
    public void update(UtilisateurAuth utilisateur, Boolean isAdmin, Integer isPayed) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            if (isAdmin) {
                preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_BY_ADMIN, true, utilisateur.getEmail(),
                        utilisateur.getMotDePasse(), utilisateur.getNom(), isPayed, utilisateur.getIsWineproducer(),
                        utilisateur.getIsAllowedAD(), utilisateur.getId());
            } else {
                preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, true, utilisateur.getEmail(),
                        utilisateur.getMotDePasse(), utilisateur.getNom(), utilisateur.getIsWineproducer(),
                        utilisateur.getIsAllowedAD(), utilisateur.getId());
            }

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
    public Utilisateur trouver(String email) throws DAOException {
        return trouverG(SQL_SELECT_PAR_EMAIL, email);
    }

    @Override
    public UtilisateurShort trouverShort(String email) throws DAOException {
        return trouverGShort(SQL_SELECT_PAR_EMAIL, email);
    }

    @Override
    public Map<String, String> trouverNomMDP(String email) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Map<String, String> nomMdp = new HashMap<>();

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement un id) et exécution.
             */
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_MDP_NOM_PAR_EMAIL, false, email);
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String mdp = resultSet.getString("mot_de_passe");
                nomMdp.put(nom, mdp);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return nomMdp;
        //return trouverG(SQL_SELECT_MDP_PAR_EMAIL, email);
    }

    @Override
    public Long trouverIdParEmail(String email) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Long id_utilisateur = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement un id) et exécution.
             */
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_ID_PAR_EMAIL, false, email);
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if (resultSet.next()) {
                id_utilisateur = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return id_utilisateur;
        //return trouverG(SQL_SELECT_MDP_PAR_EMAIL, email);
    }

    @Override
    public Utilisateur trouver(long id) throws DAOException {
        return trouverG(SQL_SELECT_PAR_ID, id);
    }

    @Override
    public Utilisateur verifier(String email, String motDePasse) throws DAOException {
        return trouverG(SQL_SELECT_PAR_EMAIL_MDP, email, motDePasse);
    }

    /*
     * Méthodes génériques
     */
    private Utilisateur trouverG(String sql, Object... objets) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Utilisateur utilisateur = null;

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
                utilisateur = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return utilisateur;
    }


    private UtilisateurShort trouverGShort(String sql, Object... objets) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UtilisateurShort utilisateur = null;

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
                utilisateur = mapPersonalData(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return utilisateur;
    }

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des utilisateurs (un
     * ResultSet) et un bean Utilisateur.
     */
    private static Utilisateur map(ResultSet resultSet) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        Long id_sessionUtilisateur = resultSet.getLong("id");
        utilisateur.setId(id_sessionUtilisateur);
        utilisateur.setEmail(resultSet.getString("email"));
        utilisateur.setMotDePasse(resultSet.getString("mot_de_passe"));
        utilisateur.setNom(resultSet.getString("nom"));
        utilisateur.setDateInscription(resultSet.getTimestamp("date_inscription"));
        utilisateur.setIsPayed(resultSet.getInt("is_payed"));
        utilisateur.setIsWineproducer(resultSet.getInt("is_wineproducer") == Const.TRUE_NUMBER);
        utilisateur.setInCommonList(utilisateur.getIsPayed() >= Const.PAID_TO_COMMON_LIST);
        utilisateur.setInSecondAD(utilisateur.getIsPayed() >= Const.PAID_TO_SECOND_AD);
        utilisateur.setInFirstAD(utilisateur.getIsPayed() >= Const.PAID_TO_FIRST_AD);
        utilisateur.setInAllowedAD(utilisateur.getIsPayed() >= Const.PAID_TO_IN_ALLOWED_AD);
        utilisateur.setIsAllowedAD(resultSet.getInt("is_allowed_ad") == Const.TRUE_NUMBER);
        utilisateur.setIsAdmin(Const.EMAIL_ADMIN.equals(utilisateur.getEmail()));

        CaveDao caveDao = daoFactory.getCaveDao();
        List<Cave> caves = new ArrayList<Cave>();
        List<Long> id_caves = caveDao.listerIdCavesPourUtilisateur(id_sessionUtilisateur);
        for (Long currIdCave : id_caves) {
            caves.add(caveDao.trouver(currIdCave));
        }
        utilisateur.setCaves(caves);
        BouteilleDao bouteilleDao = daoFactory.getBouteilleDao();
        utilisateur.setBouteilles(bouteilleDao.listerPourUtilisateur(id_sessionUtilisateur));
        ProducteurDao producteurDao = daoFactory.getProducteurDao();
        utilisateur.setProducteurs(producteurDao.listerPourUtilisateur(id_sessionUtilisateur));
        if (utilisateur.getIsWineproducer()) {
            PubDao pubDao = daoFactory.getPubDao();
            List<Pub> pubs = pubDao.listerPourUtilisateur(utilisateur.getId());
            utilisateur.setPubs(pubs);
        }
        return utilisateur;
    }

    private static UtilisateurShort mapPersonalData(ResultSet resultSet) throws SQLException {
        UtilisateurShort utilisateur = new UtilisateurShort();
        utilisateur.setId(resultSet.getLong("id"));
        utilisateur.setEmail(resultSet.getString("email"));
        utilisateur.setMotDePasse(resultSet.getString("mot_de_passe"));
        utilisateur.setNom(resultSet.getString("nom"));
        utilisateur.setDateInscription(resultSet.getTimestamp("date_inscription"));
        utilisateur.setIsPayed(resultSet.getInt("is_payed"));
        utilisateur.setIsWineproducer(resultSet.getInt("is_wineproducer") == Const.TRUE_NUMBER);
        utilisateur.setIsAllowedAD(resultSet.getInt("is_allowed_ad") == Const.TRUE_NUMBER);
        utilisateur.setInCommonList(utilisateur.getIsPayed() >= Const.PAID_TO_COMMON_LIST);
        utilisateur.setInSecondAD(utilisateur.getIsPayed() >= Const.PAID_TO_SECOND_AD);
        utilisateur.setInFirstAD(utilisateur.getIsPayed() >= Const.PAID_TO_FIRST_AD);
        utilisateur.setInAllowedAD(utilisateur.getIsPayed() >= Const.PAID_TO_IN_ALLOWED_AD);
        utilisateur.setIsAdmin(Const.EMAIL_ADMIN.equals(utilisateur.getEmail()));

        return utilisateur;
    }

}
