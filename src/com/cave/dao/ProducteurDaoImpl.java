package com.cave.dao;

import com.cave.beans.Bouteille;
import com.cave.beans.Producteur;
import com.cave.beans.ProducteurShort;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.cave.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.cave.dao.DAOUtilitaire.initialisationRequetePreparee;

public class ProducteurDaoImpl implements ProducteurDao {
    private static final String SQL_SELECT_PAR_ID = "SELECT producteur.id, producteur.nom, producteur.adresse, producteur.contact, producteur.id_utilisateur, producteur.is_allowed_cl, producteur.url, utilisateur.is_wineproducer AS is_wineproducer " +
            "FROM producteur\n" +
            "LEFT OUTER JOIN utilisateur ON utilisateur.id = producteur.id_utilisateur " +
            "WHERE producteur.id = ?";
    private static final String SQL_SELECT_POUR_UTILISATEUR = "SELECT DISTINCT producteur.id, producteur.nom, producteur.adresse, producteur.contact, producteur.id_utilisateur, producteur.is_allowed_cl, producteur.url, utilisateur.is_wineproducer AS is_wineproducer "
            + "FROM producteur " +
            "LEFT OUTER JOIN utilisateur ON utilisateur.id = producteur.id_utilisateur " +
            "WHERE producteur.id_utilisateur = ? ORDER BY producteur.nom";
    private static final String SQL_SELECT_ID_THE_SAME_PROD_POUR_UTILISATEUR = "SELECT producteur.id, producteur.nom, producteur.adresse, producteur.contact, producteur.id_utilisateur FROM producteur " +
            "WHERE producteur.id_utilisateur = ? and producteur.nom = ? and producteur.adresse = ? and producteur.contact = ? ORDER BY producteur.nom";
    private static final String SQL_SELECT_COMMON = "SELECT DISTINCT producteur.id, producteur.nom, producteur.adresse, producteur.contact, producteur.id_utilisateur, producteur.is_allowed_cl, producteur.url, utilisateur.is_wineproducer AS is_wineproducer "
            + "FROM producteur " +
            "LEFT OUTER JOIN utilisateur ON utilisateur.id = producteur.id_utilisateur " +
            "WHERE producteur.id_utilisateur != ? and utilisateur.is_wineproducer = 1 and producteur.is_allowed_cl = 1 and utilisateur.is_payed >= ? ORDER BY producteur.nom";
    private static final String SQL_SELECT_PAR_NOM = "SELECT * FROM producteur " +
            "WHERE producteur.id_utilisateur = ? AND "
            + "producteur.nom = ? AND producteur.adresse = ? AND producteur.contact = ? ";
    private static final String SQL_INSERT_POUR_UTILISATEUR = "INSERT INTO producteur (nom, adresse, contact, id_utilisateur, is_allowed_cl, url) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_COPY_FROM_PRODUCTEUR = "INSERT INTO producteur(nom, adresse, contact, url, is_allowed_cl, id_utilisateur) SELECT nom, adresse, contact, url, true, ? FROM producteur WHERE id = ?";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM producteur WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE producteur SET nom = ?, adresse = ?, contact = ?, is_allowed_cl = ?, url=? WHERE id = ? ";
    private static final String MESSAGE_DAO = "ERR DAO";
    private static DAOFactory daoFactory;
    public static final Integer TRUE_NUMBER = 1;

    public ProducteurDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /* ***********************usedALL**************************** */

    @Override
    public List<Producteur> listerPourUtilisateur(Long id_utilisateur) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Producteur> producteurs = new ArrayList<>();
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
                producteurs.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return producteurs;
    }

    @Override
    public List<ProducteurShort> listerShortPourUtilisateur(Long id_utilisateur) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ProducteurShort> producteurs = new ArrayList<>();
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
                producteurs.add(mapShort(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return producteurs;
    }

    @Override
    public ProducteurShort getShortById(Long id_Producteur) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ProducteurShort producteur = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ID, false, id_Producteur);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                producteur = mapShort(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }
        return producteur;

    }

    @Override
    public Long getIdTheSameProdOfUser(Long id_utilisateur, Producteur producteur) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //Long producteurId = null;
        List<Long> listIdProducteurs = new ArrayList<Long>();
        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_ID_THE_SAME_PROD_POUR_UTILISATEUR, false,
                    id_utilisateur, producteur.getNom(), producteur.getAdresse(), producteur.getContact());
            resultSet = preparedStatement.executeQuery();
            //Long producteurIdTest = resultSet.getLong("id");
            while (resultSet.next()) {
                listIdProducteurs.add(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }
        return !listIdProducteurs.isEmpty() ? listIdProducteurs.get(0) : null;
    }

    @Override
    public Long copyFromProducteur(Long idUtilisateur, Long idProducteur) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        Long id;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_COPY_FROM_PRODUCTEUR, true, idUtilisateur, idProducteur);
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if (statut == 0) {
                throw new DAOException(MESSAGE_DAO);
            }
            /* Récupération de l'id auto-généré par la requête d'insertion */
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
    }

    @Override
    public List<Producteur> listerCommon(Integer paid, Long userId) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Producteur> producteurs = new ArrayList<>();
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_COMMON, false,
                    userId, paid);
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            while (resultSet.next()) {
                producteurs.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return producteurs;
    }

    @Override
    public List<ProducteurShort> listerCommonShort(Integer paid, Long userId) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ProducteurShort> producteurs = new ArrayList<>();
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_COMMON, false,
                    userId, paid);
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            while (resultSet.next()) {
                producteurs.add(mapShort(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return producteurs;
    }

    @Override
    public Producteur creerPourUtilisateur(Producteur producteur) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT_POUR_UTILISATEUR, true,
                    producteur.getNom(),
                    producteur.getAdresse(), producteur.getContact(),
                    producteur.getIdUtilisateur(),
                    producteur.getIsAllowedCL(),
                    producteur.getUrl());
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
                producteur.setId(valeursAutoGenerees.getLong(1));
            } else {
                throw new DAOException(MESSAGE_DAO);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
        }
        return producteur;
    }

    @Override
    public Long creerPourUtilisateur(ProducteurShort producteur) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;
        Long producteurId;
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT_POUR_UTILISATEUR, true,
                    producteur.getNom(),
                    producteur.getAdresse(), producteur.getContact(),
                    producteur.getIdUtilisateur(),
                    producteur.getAllowedCL(),
                    producteur.getUrl());
            int statut = preparedStatement.executeUpdate();
            /* Analyse du statut retourné par la requête d'insertion */
            if (statut == 0) {
                throw new DAOException(MESSAGE_DAO);
            }
            /* Récupération de l'id auto-généré par la requête d'insertion */
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if (valeursAutoGenerees.next()) {
                producteurId = valeursAutoGenerees.getLong(1);
            } else {
                throw new DAOException(MESSAGE_DAO);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
        }
        return producteurId;
    }

    @Override
    public void update(Producteur producteur) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, true, producteur.getNom(),
                    producteur.getAdresse(), producteur.getContact(), producteur.getIsAllowedCL(), producteur.getUrl(),
                    producteur.getId());
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
    public void update(ProducteurShort producteur) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, true, producteur.getNom(),
                    producteur.getAdresse(), producteur.getContact(), producteur.getAllowedCL(), producteur.getUrl(),
                    producteur.getId());
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
    public Producteur trouver(long id) throws DAOException {

        return trouver(SQL_SELECT_PAR_ID, id);
    }

    @Override
    public Producteur trouver(Producteur producteur) throws DAOException {
        return trouver(SQL_SELECT_PAR_NOM, producteur.getIdUtilisateur(), producteur.getNom(),
                producteur.getAdresse(), producteur.getContact());
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

    private Producteur trouver(String sql, Object... objets) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Producteur producteur = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, sql, false, objets);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                producteur = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }
        return producteur;
    }

    /*
     * la correspondance (le mapping) entre une ligne issue de la table des
     * Producteurs (un ResultSet) et un bean Producteur.
     */
    private static Producteur map(ResultSet resultSet) throws SQLException {
        Producteur producteur = new Producteur();
        Long id_producteur = resultSet.getLong("id");
        producteur.setId(id_producteur);
        producteur.setNom(resultSet.getString("nom"));
        producteur.setAdresse(resultSet.getString("adresse"));
        producteur.setContact(resultSet.getString("contact"));
        producteur.setUrl(resultSet.getString("url"));
        producteur.setIdUtilisateur(resultSet.getLong("id_utilisateur"));
        producteur.setByWineproducer(resultSet.getInt("is_wineproducer") == TRUE_NUMBER);
        producteur.setIsAllowedCL(resultSet.getInt("is_allowed_cl") == TRUE_NUMBER);
        BouteilleDao bouteilleDao = daoFactory.getBouteilleDao();
        List<Bouteille> bouteilles = bouteilleDao.listerPourProducteur(id_producteur);
        if (!bouteilles.isEmpty()) {
            producteur.setBouteilles(bouteilles);
        }
        return producteur;
    }

    private static ProducteurShort mapShort(ResultSet resultSet) throws SQLException {
        ProducteurShort producteur = new ProducteurShort();
        Long id_producteur = resultSet.getLong("id");
        producteur.setId(id_producteur);
        producteur.setNom(resultSet.getString("nom"));
        producteur.setAdresse(resultSet.getString("adresse"));
        producteur.setContact(resultSet.getString("contact"));
        producteur.setUrl(resultSet.getString("url"));
        producteur.setIdUtilisateur(resultSet.getLong("id_utilisateur"));
        producteur.setByWineproducer(resultSet.getInt("is_wineproducer") == TRUE_NUMBER);
        producteur.setAllowedCL(resultSet.getInt("is_allowed_cl") == TRUE_NUMBER);
        return producteur;
    }

}
