package com.cave.dao;

import com.cave.beans.Blog;

import java.util.List;

public interface BlogDao {

    List<Blog> lister() throws DAOException;

    void creer(Blog blog) throws DAOException;

    void update(Blog blog) throws DAOException;

    void supprimer(Long id) throws DAOException;

    Blog trouver(long id ) throws DAOException;

}
