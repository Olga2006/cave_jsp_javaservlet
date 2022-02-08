package com.cave.dao;

import com.cave.beans.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static com.cave.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.cave.dao.DAOUtilitaire.initialisationRequetePreparee;

public class BouteilleDaoImpl implements BouteilleDao {
    private static final String SQL_SELECT_POUR_UTILISATEUR = "SELECT bouteille.id, Bouteille.id_producteur, Producteur.nom as nom_producteur, Bouteille.is_allowed_cl, Utilisateur.is_wineproducer AS is_wineproducer,\r\n"
            +
            "Bouteille.nom, Bouteille.couleur, Bouteille.taille, Bouteille.pays, Bouteille.region, Bouteille.appelation, Bouteille.cru, Bouteille.evaluation,  Bouteille.commentaire, Bouteille.nbr_list_courses, Bouteille.date_de_production, \r\n"
            +
            "Bouteille.date_garder, Bouteille.image, Bouteille.id_utilisateur, Bouteille.prix_achat, Bouteille.prix_actuelle,\r\n"
            +
            "Bouteille.commentaire, Bouteille.url_achat, Bouteille.nbr_list_courses \r\n" +
            "FROM Bouteille \r\n" +
            "LEFT OUTER JOIN Producteur ON Producteur.id = Bouteille.id_producteur\r\n" +
            "LEFT OUTER JOIN Utilisateur ON Utilisateur.id = Bouteille.id_utilisateur\r\n" +
            "WHERE Bouteille.id_utilisateur = ? ORDER BY Bouteille.nom";

    private static final String SQL_SELECT_THE_SAME_NO_PROD_POUR_UTILISATEUR = "SELECT Bouteille.id, Bouteille.id_producteur, null as nom_producteur, Bouteille.is_allowed_cl, Utilisateur.is_wineproducer AS is_wineproducer,\r\n"
            +
            "Bouteille.nom, Bouteille.couleur, Bouteille.taille, Bouteille.pays, Bouteille.region, Bouteille.appelation, Bouteille.cru, Bouteille.evaluation,  Bouteille.commentaire, Bouteille.nbr_list_courses, Bouteille.date_de_production, \r\n"
            +
            "Bouteille.date_garder, Bouteille.image, Bouteille.id_utilisateur, Bouteille.prix_achat, Bouteille.prix_actuelle,\r\n"
            +
            "Bouteille.commentaire, Bouteille.url_achat, Bouteille.nbr_list_courses \r\n" +
            "FROM Bouteille \r\n" +
            "LEFT OUTER JOIN Utilisateur ON Utilisateur.id = Bouteille.id_utilisateur\r\n" +
            "WHERE Bouteille.id_utilisateur = ? and Bouteille.id_producteur = null and Bouteille.nom = ? and Bouteille.pays = ? and Bouteille.region = ? and Bouteille.appelation = ? " +
            "and Bouteille.couleur = ? and Bouteille.cru = ?  and Bouteille.date_de_production = ? and Bouteille.taille = ? " +
            "ORDER BY Bouteille.nom";

    private static final String SQL_SELECT_THE_SAME_WITH_PROD_POUR_UTILISATEUR = "SELECT Bouteille.id, Bouteille.id_producteur, Producteur.nom as nom_producteur, Bouteille.is_allowed_cl, Utilisateur.is_wineproducer AS is_wineproducer,\r\n"
            +
            "Bouteille.nom, Bouteille.couleur, Bouteille.taille, Bouteille.pays, Bouteille.region, Bouteille.appelation, Bouteille.cru, Bouteille.evaluation,  Bouteille.commentaire, Bouteille.nbr_list_courses, Bouteille.date_de_production, \r\n"
            +
            "Bouteille.date_garder, Bouteille.image, Bouteille.id_utilisateur, Bouteille.prix_achat, Bouteille.prix_actuelle,\r\n"
            +
            "Bouteille.commentaire, Bouteille.url_achat, Bouteille.nbr_list_courses, Producteur.nom, Producteur.adresse, Producteur.contact \r\n" +
            "FROM Bouteille \r\n" +
            "LEFT OUTER JOIN Producteur ON Producteur.id = Bouteille.id_producteur\r\n" +
            "LEFT OUTER JOIN Utilisateur ON Utilisateur.id = Bouteille.id_utilisateur\r\n" +
            "WHERE Bouteille.id_utilisateur = ? and Bouteille.nom = ? and Bouteille.pays = ? and Bouteille.region = ? and Bouteille.appelation = ? " +
            "and Bouteille.couleur = ? and Bouteille.cru = ?  and Bouteille.date_de_production = ? and Bouteille.taille = ? " +
            "and Producteur.nom = ? and Producteur.adresse = ? and Producteur.contact = ?" +
            "ORDER BY Bouteille.nom";

    private static final String SQL_COPY_FROM_PRODUCTEUR = "INSERT INTO Bouteille(nom, couleur, pays, region, appelation, cru, taille, prix_actuelle, date_de_production, date_garder, image, url_achat, id_producteur, id_utilisateur) " +
            "SELECT nom, couleur, pays, region, appelation, cru, taille, prix_actuelle, date_de_production, date_garder, ?, url_achat, ?, ? FROM Bouteille WHERE id = ?";
    private static final String SQL_SELECT_COMMON = "SELECT Bouteille.id, Bouteille.id_producteur, Producteur.nom as nom_producteur, Bouteille.is_allowed_cl, Utilisateur.is_wineproducer AS is_wineproducer,\r\n"
            +
            "Bouteille.nom, Bouteille.couleur, Bouteille.taille, Bouteille.pays, Bouteille.region, Bouteille.appelation, Bouteille.cru, Bouteille.evaluation,  Bouteille.commentaire, Bouteille.nbr_list_courses, Bouteille.date_de_production, \r\n"
            +
            "Bouteille.date_garder, Bouteille.image, Bouteille.id_utilisateur, Bouteille.prix_achat, Bouteille.prix_actuelle,\r\n"
            +
            "Bouteille.commentaire, Bouteille.url_achat, Bouteille.nbr_list_courses \r\n" +
            "FROM Bouteille \r\n" +
            "LEFT OUTER JOIN Producteur ON Producteur.id = Bouteille.id_producteur\r\n" +
            "LEFT OUTER JOIN Utilisateur ON Utilisateur.id = Bouteille.id_utilisateur\r\n" +
            "WHERE Bouteille.id_utilisateur != ? and Utilisateur.is_wineproducer = 1 and Bouteille.is_allowed_cl = 1 and Utilisateur.is_payed >= ? ORDER BY Bouteille.nom";
    private static final String SQL_SELECT_PAR_ID = "SELECT Bouteille.id, Bouteille.id_producteur, Producteur.nom as nom_producteur, Bouteille.is_allowed_cl, Utilisateur.is_wineproducer AS is_wineproducer, \r\n"
            +
            "Bouteille.nom, Bouteille.couleur, Bouteille.taille, Bouteille.pays, Bouteille.region, Bouteille.appelation, Bouteille.cru, Bouteille.evaluation, Bouteille.commentaire, Bouteille.nbr_list_courses, Bouteille.date_de_production, \r\n"
            +
            "Bouteille.date_garder, Bouteille.image, Bouteille.id_utilisateur, Bouteille.prix_achat, Bouteille.prix_actuelle,\r\n"
            +
            "Bouteille.commentaire, Bouteille.url_achat, Bouteille.nbr_list_courses \r\n" +
            "FROM Bouteille \r\n" +
            "LEFT OUTER JOIN Producteur ON Producteur.id = Bouteille.id_producteur\r\n" +
            "LEFT OUTER JOIN Utilisateur ON Utilisateur.id = Bouteille.id_utilisateur\r\n" +
            "WHERE Bouteille.id = ? ORDER BY Bouteille.nom";
    private static final String SQL_SELECT_POUR_PRODUCTEUR = "SELECT Bouteille.id, Bouteille.id_producteur, Producteur.nom as nom_producteur, Bouteille.is_allowed_cl, Utilisateur.is_wineproducer AS is_wineproducer,\r\n"
            +
            "Bouteille.nom, Bouteille.couleur, Bouteille.taille, Bouteille.pays, Bouteille.region, Bouteille.appelation, Bouteille.cru, Bouteille.evaluation, Bouteille.commentaire, Bouteille.nbr_list_courses, Bouteille.date_de_production, \r\n"
            +
            "Bouteille.date_garder, Bouteille.image, Bouteille.id_utilisateur, Bouteille.prix_achat, Bouteille.prix_actuelle,\r\n"
            +
            "Bouteille.commentaire, Bouteille.url_achat, Bouteille.nbr_list_courses \r\n" +
            "FROM Bouteille \r\n" +
            "LEFT OUTER JOIN Producteur ON Producteur.id = Bouteille.id_producteur\r\n" +
            "LEFT OUTER JOIN Utilisateur ON Utilisateur.id = Bouteille.id_utilisateur\r\n" +
            "WHERE Bouteille.id_producteur = ? ORDER BY Bouteille.nom";

    private static final String SQL_SELECT_SHORT_POUR_PRODUCTEUR = "SELECT Bouteille.id,\r\n" +
            "Bouteille.nom, Bouteille.couleur, Bouteille.taille, Bouteille.pays, Bouteille.region, " +
            "Bouteille.appelation, Bouteille.cru, Bouteille.date_de_production, \r\n" +
            "Bouteille.date_garder FROM Bouteille \r\n" +
            "WHERE Bouteille.id_producteur = ? ORDER BY Bouteille.nom";

    private static final String SQL_SELECT_PAR_NOM = "SELECT Bouteille.id, Bouteille.id_producteur, Producteur.nom as nom_producteur, Bouteille.is_allowed_cl, Utilisateur.is_wineproducer AS is_wineproducer, \r\n"
            +
            "Bouteille.nom, Bouteille.couleur, Bouteille.taille, Bouteille.pays, Bouteille.region, Bouteille.appelation, Bouteille.cru, Bouteille.evaluation, Bouteille.commentaire, Bouteille.nbr_list_courses, Bouteille.date_de_production, \r\n"
            +
            "Bouteille.date_garder, Bouteille.image, Bouteille.id_utilisateur, Bouteille.prix_achat, Bouteille.prix_actuelle,\r\n"
            +
            "Bouteille.commentaire, Bouteille.url_achat, Bouteille.nbr_list_courses \r\n" +
            "FROM Bouteille \r\n" +
            "LEFT OUTER JOIN Producteur ON Producteur.id = Bouteille.id_producteur\r\n" +
            "LEFT OUTER JOIN Utilisateur ON Utilisateur.id = Bouteille.id_utilisateur\r\n" +
            "WHERE Bouteille.id_utilisateur=?  AND Bouteille.nom = ? AND Bouteille.pays =? AND  Bouteille.region =? AND Bouteille.appelation =? AND \r\n" +
            "Bouteille.couleur =? AND Bouteille.cru =? AND Bouteille.date_de_production =? AND Bouteille.taille =? AND \r\n"
            +
            "Bouteille.prix_achat=?";
    private static final String SQL_INSERT = "INSERT INTO Bouteille (nom, couleur, pays, region, appelation, cru, taille, prix_achat, prix_actuelle, \r\n"
            + "date_de_production, date_garder, image, id_producteur, id_utilisateur, url_achat, nbr_list_courses, commentaire, evaluation, is_allowed_cl) \r\n"
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM Bouteille  WHERE id = ?";
    private static final String SQL_UPDATE_COMMENTAIRE = "UPDATE Bouteille SET  commentaire = ? WHERE id = ?";
    private static final String SQL_UPDATE_LIST_COURSES = "UPDATE Bouteille SET  nbr_list_courses = ? WHERE id = ?";
    private static final String SQL_UPDATE_EVALUATION = "UPDATE Bouteille SET  evaluation = ? WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE Bouteille SET  nom = ?, couleur = ?, pays = ?, region = ?, appelation = ?, cru = ?, taille = ?, prix_achat = ?,"
            + " prix_actuelle = ?, date_de_production = ?, date_garder = ?, image = ?, id_producteur = ?, "
            + "url_achat = ?, nbr_list_courses = ?, commentaire = ?, evaluation = ?, is_allowed_cl = ?  WHERE id = ?";
    private static final String MESSAGE_DAO = " ";
    private static DAOFactory daoFactory;
    public static final Integer TRUE_NUMBER = 1;

    public BouteilleDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Bouteille creerPourUtilisateur(Bouteille bouteille) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, bouteille.getNom(),
                    bouteille.getCouleur(), bouteille.getPays(), bouteille.getRegion(), bouteille.getAppelation(),
                    bouteille.getCru(), bouteille.getTaille(), bouteille.getPrixAchat(), bouteille.getPrixActuelle(),
                    bouteille.getDateDeProduction(), bouteille.getDateGarder(), bouteille.getImage(),
                    bouteille.getIdProducteur(), bouteille.getIdUtilisateur(), bouteille.getUrlAchat(),
                    bouteille.getNbrListCourses(), bouteille.getCommentaire(), bouteille.getEvaluation(), bouteille.getIsAllowedCL());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException(MESSAGE_DAO);
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if (valeursAutoGenerees.next()) {
                bouteille.setId(valeursAutoGenerees.getLong(1));
            } else {
                throw new DAOException(MESSAGE_DAO);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
        }
        return bouteille;
    }

    @Override
    public Long creerPourUtilisateur(BouteillePost bouteille) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        Long bouteilleId;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, bouteille.getNom(),
                    bouteille.getCouleur(), bouteille.getPays(), bouteille.getRegion(), bouteille.getAppelation(),
                    bouteille.getCru(), bouteille.getTaille(), bouteille.getPrixAchat(), bouteille.getPrixActuelle(),
                    bouteille.getDateDeProduction(), bouteille.getDateGarder(), bouteille.getImage(),
                    bouteille.getIdProducteur(), bouteille.getIdUtilisateur(), bouteille.getUrlAchat(),
                    bouteille.getNbrListCourses(), bouteille.getCommentaire(), bouteille.getEvaluation(), bouteille.getIsAllowedCL());
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException(MESSAGE_DAO);
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if (valeursAutoGenerees.next()) {
                bouteilleId = valeursAutoGenerees.getLong(1);
            } else {
                throw new DAOException(MESSAGE_DAO);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
        }
        return bouteilleId;
    }

    /*@Override
    public Long copyFromProducteur(Long idUtilisateur, Long idProducteur, Long idBouteille, String img) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        Long id;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_COPY_FROM_PRODUCTEUR, true, img, idProducteur, idUtilisateur, idBouteille);
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException(MESSAGE_DAO);
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if (valeursAutoGenerees.next()) {
                id = valeursAutoGenerees.getLong(1);
            } else {
                throw new DAOException(MESSAGE_DAO);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
        }
        return id;
    }*/

    @Override
    public void update(Bouteille bouteille) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, true, bouteille.getNom(),
                    bouteille.getCouleur(), bouteille.getPays(), bouteille.getRegion(), bouteille.getAppelation(),
                    bouteille.getCru(), bouteille.getTaille(),
                    bouteille.getPrixAchat(), bouteille.getPrixActuelle(),
                    bouteille.getDateDeProduction(), bouteille.getDateGarder(),
                    bouteille.getImage(), bouteille.getIdProducteur(), bouteille.getUrlAchat(), bouteille.getNbrListCourses(),
                    bouteille.getCommentaire(), bouteille.getEvaluation(), bouteille.getIsAllowedCL(), bouteille.getId());

            int statut = preparedStatement.executeUpdate();
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
    public void update(BouteillePost bouteille) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, true, bouteille.getNom(),
                    bouteille.getCouleur(), bouteille.getPays(), bouteille.getRegion(), bouteille.getAppelation(),
                    bouteille.getCru(), bouteille.getTaille(),
                    bouteille.getPrixAchat(), bouteille.getPrixActuelle(),
                    bouteille.getDateDeProduction(), bouteille.getDateGarder(),
                    bouteille.getImage(), bouteille.getIdProducteur(), bouteille.getUrlAchat(), bouteille.getNbrListCourses(),
                    bouteille.getCommentaire(), bouteille.getEvaluation(), bouteille.getIsAllowedCL(), bouteille.getId());
            int statut = preparedStatement.executeUpdate();
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
    public Bouteille trouver(long id) throws DAOException {
        return trouver(SQL_SELECT_PAR_ID, id);
    }

    @Override
    public List<Bouteille> listerPourUtilisateur(Long id_utilisateur) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Bouteille> bouteilles = new ArrayList<>();
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_POUR_UTILISATEUR, false,
                    id_utilisateur);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bouteilles.add(map(resultSet, true));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }
        return bouteilles;
    }

    @Override
    public List<Bouteille> listerCommon(Integer paid, Long userId) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Bouteille> bouteilles = new ArrayList<Bouteille>();
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_COMMON, false, userId, paid);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bouteilles.add(map(resultSet, false));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return bouteilles;
    }

    @Override
    public List<Bouteille> listerPourProducteur(Long id_producteur) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Bouteille> bouteilles = new ArrayList<Bouteille>();
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_POUR_PRODUCTEUR, false,
                    id_producteur);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bouteilles.add(map(resultSet, true));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return bouteilles;
    }

    @Override
    public List<BouteilleOfProducer> listerPourProducteurShort(Long id_producteur) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<BouteilleOfProducer> bouteilles = new ArrayList<>();
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_SHORT_POUR_PRODUCTEUR, false,
                    id_producteur);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bouteilles.add(mapPourProd(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return bouteilles;
    }

    @Override
    public List<Bouteille> listerTheSameBouteillesOfUser(Long id_utilisateur, Bouteille bouteille) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Bouteille> bouteilles = new ArrayList<>();
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            if (bouteille.getProducteur() != null) {
                preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_THE_SAME_WITH_PROD_POUR_UTILISATEUR, false,
                        id_utilisateur, bouteille.getNom(), bouteille.getPays(), bouteille.getRegion(), bouteille.getAppelation(),
                        bouteille.getCouleur(), bouteille.getCru(), bouteille.getDateDeProduction(), bouteille.getTaille(),
                        bouteille.getProducteur().getNom(), bouteille.getProducteur().getAdresse(), bouteille.getProducteur().getContact());
            } else {
                preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_THE_SAME_NO_PROD_POUR_UTILISATEUR, false,
                        id_utilisateur, bouteille.getNom(), bouteille.getPays(), bouteille.getRegion(), bouteille.getAppelation(),
                        bouteille.getCouleur(), bouteille.getCru(), bouteille.getDateDeProduction(), bouteille.getTaille());
            }
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bouteilles.add(map(resultSet, false));
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }
        return bouteilles;
    }

    @Override
    public void supprimer(Long id) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE_PAR_ID, false, id);
            int statut = preparedStatement.executeUpdate();

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

    @Override
    public void ajouterCommentaire(String commentaire, Long id) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_COMMENTAIRE, false, commentaire,
                    id);
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException(MESSAGE_DAO);
            } else {
                commentaire = null;
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(preparedStatement, connexion);
        }

    }

    @Override
    public void changerLC(Integer quantite_Acheter, Long id) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_LIST_COURSES, false,
                    quantite_Acheter, id);
            int statut = preparedStatement.executeUpdate();
            if (statut == 0) {
                throw new DAOException(MESSAGE_DAO);
            } else {
                quantite_Acheter = 0;
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(preparedStatement, connexion);
        }

    }

    @Override
    public void changerEvaluation(Integer evaluation, Long id) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_EVALUATION, false,
                    evaluation, id);
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if (statut == 0) {
                throw new DAOException(MESSAGE_DAO);
            } else {
                evaluation = 0;
            }

        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(preparedStatement, connexion);
        }

    }

    private Bouteille trouver(String sql, Object... objets) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Bouteille bouteille = null;
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, sql, false, objets);
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if (resultSet.next()) {
                bouteille = map(resultSet, true);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return bouteille;
    }


    private static Bouteille map(ResultSet resultSet, Boolean withPositions) throws SQLException {
        Bouteille bouteille = new Bouteille();
        Long id_bouteille = resultSet.getLong("id");
        bouteille.setId(id_bouteille);
        bouteille.setNom(resultSet.getString("nom"));
        bouteille.setCouleur(resultSet.getString("couleur"));
        bouteille.setAppelation(resultSet.getString("appelation"));
        bouteille.setCru(resultSet.getString("cru"));
        bouteille.setPays(resultSet.getString("pays"));
        bouteille.setRegion(resultSet.getString("region"));
        bouteille.setTaille(resultSet.getDouble("taille"));
        bouteille.setPrixAchat(resultSet.getDouble("prix_achat"));
        bouteille.setPrixActuelle(resultSet.getDouble("prix_actuelle"));
        bouteille.setDateDeProduction(resultSet.getInt("date_de_production"));
        bouteille.setDateGarder(resultSet.getInt("date_garder"));
        bouteille.setImage(resultSet.getString("image"));
        bouteille.setIdProducteur(resultSet.getLong("id_producteur"));
        bouteille.setIdUtilisateur(resultSet.getLong("id_utilisateur"));
        bouteille.setNomProducteur(resultSet.getString("nom_producteur"));
        bouteille.setUrlAchat(resultSet.getString("url_achat"));
        bouteille.setCommentaire(resultSet.getString("commentaire"));
        bouteille.setNbrListCourses(resultSet.getInt("nbr_list_courses"));
        bouteille.setEvaluation(resultSet.getInt("evaluation"));
        if (bouteille.getDateGarder() != null && !bouteille.getDateGarder().equals(0) && bouteille.getDateGarder() < Year.now().getValue()) {
            bouteille.setNbrAneeABoir(bouteille.getDateGarder() - Year.now().getValue());
        }
        bouteille.setByWineproducer(resultSet.getInt("is_wineproducer") == TRUE_NUMBER);
        bouteille.setIsAllowedCL(resultSet.getInt("is_allowed_cl") == TRUE_NUMBER);
        if (withPositions) {
            PositionDao positionDao = daoFactory.getPositionDao();
            List<Position> positions = positionDao.listerPourBouteille(id_bouteille);
            if (!positions.isEmpty()) {
                bouteille.setPositions(positions);
            }
            int nbrTotal;
            nbrTotal = positions.size();
            bouteille.setNbrTotal(nbrTotal);
        }
        return bouteille;
    }


    private static BouteilleOfProducer mapPourProd(ResultSet resultSet) throws SQLException {
        BouteilleOfProducer bouteille = new BouteilleOfProducer();
        Long id_bouteille = resultSet.getLong("id");
        bouteille.setId(id_bouteille);
        bouteille.setNom(resultSet.getString("nom"));
        bouteille.setCouleur(resultSet.getString("couleur"));
        bouteille.setAppelation(resultSet.getString("appelation"));
        bouteille.setCru(resultSet.getString("cru"));
        bouteille.setPays(resultSet.getString("pays"));
        bouteille.setRegion(resultSet.getString("region"));
        bouteille.setTaille(resultSet.getDouble("taille"));
        bouteille.setDateDeProduction(resultSet.getInt("date_de_production"));
        bouteille.setDateGarder(resultSet.getInt("date_garder"));
        if (bouteille.getDateGarder() != null && !bouteille.getDateGarder().equals(0) && bouteille.getDateGarder() < Year.now().getValue()) {
            bouteille.setNbrAneeABoir(bouteille.getDateGarder() - Year.now().getValue());
        }
        PositionDao positionDao = daoFactory.getPositionDao();
        List<PositionShort> positions = positionDao.listerPourBouteilleShort(id_bouteille);
        if (!positions.isEmpty()) {
            bouteille.setPositions(positions);
        }
        int nbrTotal;
        nbrTotal = positions.size();
        bouteille.setNbrTotal(nbrTotal);
        return bouteille;
    }

}
