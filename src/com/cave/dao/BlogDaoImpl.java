package com.cave.dao;

import com.cave.beans.Blog;
import com.cave.beans.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.cave.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.cave.dao.DAOUtilitaire.initialisationRequetePreparee;

public class BlogDaoImpl implements BlogDao {

    private static final String SQL_SELECT = "SELECT *  FROM blog";
    private static final String SQL_SELECT_PAR_ID = "SELECT * FROM blog WHERE id = ?";
    private static final String SQL_DELETE_PAR_ID = "DELETE FROM blog WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE blog SET nom_article = ?, subtheme = ?, nom_auteur = ?, article1 = ?, " +
            "article2 = ?, article3 = ?, date_edition=?, image=? WHERE id = ? ";
    private static final String SQL_INSERT = "INSERT INTO blog (nom_article, subtheme, nom_auteur, article1, article2, article3, date_edition, image) " +
            "VALUES (?,?,?,?,?,?,?,?)";
    private static final String MESSAGE_DAO = " ";

    private static DAOFactory daoFactory;

    public BlogDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public List<Blog> lister() throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Blog> blogs = new ArrayList<Blog>();
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT, false);
            resultSet = preparedStatement.executeQuery();
            /*
             * Parcours de la ligne de données de l'éventuel ResulSet retourné
             */
            while (resultSet.next()) {
                blogs.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(MESSAGE_DAO + e);
        } finally {
            fermeturesSilencieuses(resultSet, preparedStatement, connexion);
        }

        return blogs;
    }

    @Override
    public void creer(Blog blog) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true,
                    blog.getNomArticle(), blog.getSubtheme(), blog.getNomAuteur(), blog.getArticle1(), blog.getArticle2(),
                    blog.getArticle3(), blog.getDateEdition(), blog.getImage());
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
                blog.setId(valeursAutoGenerees.getLong(1));
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
    public void update(Blog blog) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, true,
                    blog.getNomArticle(), blog.getSubtheme(), blog.getNomAuteur(), blog.getArticle1(), blog.getArticle2(),
                    blog.getArticle3(), blog.getDateEdition(), blog.getImage(), blog.getId());
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
    public Blog trouver(long id ) throws DAOException {
        return trouverG( SQL_SELECT_PAR_ID, id );
    }

    private Blog trouverG( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Blog blog = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            /*
             * Préparation de la requête avec les objets passés en arguments
             * (ici, uniquement un id) et exécution.
             */
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if ( resultSet.next() ) {
                blog = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( MESSAGE_DAO + e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return blog;
    }

    private static Blog map(ResultSet resultSet) throws SQLException {
        Blog blog = new Blog();
        Long id_blog = resultSet.getLong("id");
        blog.setId(id_blog);
        blog.setNomArticle(resultSet.getString("nom_article"));
        blog.setNomAuteur(resultSet.getString("nom_auteur"));
        blog.setSubtheme(resultSet.getString("subtheme"));
        blog.setDateEdition(resultSet.getTimestamp("date_edition"));
        blog.setArticle1(resultSet.getString("article1"));
        blog.setArticle2(resultSet.getString("article2"));
        blog.setArticle3(resultSet.getString("article3"));
        blog.setImage( resultSet.getString( "image" ) );
        return blog;
    }
}
