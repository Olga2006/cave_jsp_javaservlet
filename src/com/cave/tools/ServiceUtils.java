package com.cave.tools;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;


public class ServiceUtils {
    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    public String getValeurChampString (HttpServletRequest request, String nomChamp) {
        String valeur = request.getParameter(nomChamp);
        if (valeur == null || valeur.trim().length() == 0) {
            return null;
        } else {
            return valeur.trim();
        }
    }


    public Timestamp getValeurChampTimestamp(HttpServletRequest request, String nomChamp) {
        String valeur = getValeurChampString(request,nomChamp);
        if(valeur == null) {
            return null;
        }
        return Timestamp.valueOf(valeur);
    }

    public Integer getValeurChampInt(HttpServletRequest request, String nomChamp) {
        String valeur = getValeurChampString(request,nomChamp);
        if(valeur == null) {
            return null;
        }
        return Integer.parseInt(valeur);
    }

    public Integer getValeurChampIntNoNullable(HttpServletRequest request, String nomChamp) {
        String valeur = getValeurChampString(request,nomChamp);
        if(valeur == null) {
            return 0;
        }
        return Integer.parseInt(valeur);
    }

    public Long getValeurChampLong(HttpServletRequest request, String nomChamp) {
        String valeur = getValeurChampString(request,nomChamp);
        if(valeur == null) {
            return null;
        }
        return Long.parseLong(valeur);
    }

    public List<Long> getValeurChampListLong(HttpServletRequest request, String nomChamp) {
        String valeur = getValeurChampString(request,nomChamp);
        if(valeur == null) {
            return null;
        }
        return null;

        //return Long.parseLong(valeur);
    }

    public Boolean getValeurChampBool(HttpServletRequest request, String nomChamp) {
        String valeur = getValeurChampString(request,nomChamp);
        if(valeur == null) {
            return null;
        }
        return valeur.equals("true");
    }

    public Boolean getValeurChampBoolNoNullable(HttpServletRequest request, String nomChamp) {
        String valeur = getValeurChampString(request,nomChamp);
        if(valeur == null) {
            return false;
        }
        return valeur.equals("true");
    }

    public Boolean getNNBoolean (Boolean val) {
        if (val == null || !val.booleanValue()){
            return false;
        }
        return val;
    }

    public boolean sendMailConfirm() {
        try {

            return true;
        } catch (Exception e) {
            return false;
        }

    }

}