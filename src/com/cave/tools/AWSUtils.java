package com.cave.tools;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.cave.beans.Erreur;
import com.cave.beans.Utilisateur;
import com.cave.dao.ErreurDao;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class AWSUtils {
    public static final String AWS_BUCKET_NAME = "caveweb";
    public static final String AWS_ACCESSKEY = "AKIA55PLC5ST3MNFZWVB";
    public static final String AWS_SECRETKEY = "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";

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    public boolean deleteImage(String image, ErreurDao erreurDao, Long idUtilisateur, String page) {
        /* Create S3 Client Object */
        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.EU_WEST_3)
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(AWS_ACCESSKEY, AWS_SECRETKEY)))
                .build();
        try {

            /* Send Delete Object Request */
            /* Delete single object 's3.png' */

            s3.deleteObject(AWS_BUCKET_NAME, image);
            return true;

        } catch (AmazonServiceException e) {
            String msg = "The call was transmitted successfully, but Amazon S3 couldn't process it, so it returned an error response.";
            //e.printStackTrace();
            erreurDao.creer(new Erreur(idUtilisateur, page, msg + e.getMessage()));
            return false;
        } catch (SdkClientException e) {
            String msg = "Amazon S3 couldn't be contacted for a response, or the client couldn't parse the response from Amazon S3.";
            //e.printStackTrace();
            erreurDao.creer(new Erreur(idUtilisateur, page, msg + e.getMessage()));
            return false;
        } finally {

            if (s3 != null) {
                s3.shutdown();
            }
        }
    }

    public boolean CopyImage (String fromImg, String toImg, ErreurDao erreurDao, Long idUtilisateur, String page) {
        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.EU_WEST_3)
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(AWS_ACCESSKEY, AWS_SECRETKEY)))
                .build();

        try {
            s3.copyObject(new CopyObjectRequest(AWS_BUCKET_NAME, fromImg, AWS_BUCKET_NAME, toImg)
                    .withCannedAccessControlList(CannedAccessControlList.PublicRead));

            return true;
        } catch (AmazonServiceException e) {
            erreurDao.creer(new Erreur(idUtilisateur, page, e.getErrorMessage()));
            return false;
        }
    }

    public boolean SaveImage (BufferedImage image, ErreurDao erreurDao, Long idUtilisateur, String page) {
        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.EU_WEST_3)
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(AWS_ACCESSKEY, AWS_SECRETKEY)))
                .build();
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buffer = os.toByteArray();
            InputStream is = new ByteArrayInputStream(buffer);
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(buffer.length);
            s3.putObject(new PutObjectRequest(AWS_BUCKET_NAME, "test", is, meta));
            return true;
        } catch (AmazonServiceException e) {
            erreurDao.creer(new Erreur(idUtilisateur, page, e.getErrorMessage()));
            return false;
        }
    }
}