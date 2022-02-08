package com.cave.tools;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cave.beans.Bouteille;
import com.cave.beans.Position;
import com.cave.beans.Producteur;
import com.cave.beans.Utilisateur;
import jxl.write.WriteException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class WriteExcel {

    public String write(Utilisateur sessionUtilisateur, String objectxml, String[] headerArray) throws IOException, WriteException, URISyntaxException {

        Boolean xmlLoaded;

        AWSCredentials credentials = new BasicAWSCredentials("AKIA55PLC5ST4SSPX2WE",
                "oJfgxXLhKf/vucLADruBlA/3Ws9GoGI45SuFhmQW");
        AmazonS3 s3client = new AmazonS3Client(credentials);

        HSSFWorkbook excelWorkBook = new HSSFWorkbook();
        HSSFSheet sheet;
        switch (objectxml) {
            case "Producteurs":
                sheet = excelWorkBook.createSheet("Producteurs");
                createLabel(sheet, headerArray);
                List<Producteur> listProducteurs = sessionUtilisateur.getProducteurs();
                createContentProducteur(sheet, listProducteurs);
                break;
            case "Bouteilles":
                sheet = excelWorkBook.createSheet("Bouteilles");
                createLabel(sheet, headerArray);
                List<Bouteille> listBouteilles = sessionUtilisateur.getBouteilles();
                createContentBouteille(sheet, listBouteilles);
                break;
            case "Caves":
                sheet = excelWorkBook.createSheet("Caves");
                break;
            default:
                break;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            excelWorkBook.write(byteArrayOutputStream);
            byteArrayOutputStream.close();
        } catch (IOException e) {
            // LOGGER.error(e.getMessage());
        }

        ByteArrayInputStream bi = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        Long contentLength = Long.valueOf(byteArrayOutputStream.toByteArray().length);
        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType("application/vnd.ms-excel");
        objectMetaData.setContentLength(contentLength);
        s3client.putObject(new PutObjectRequest("caveweb/xls", "cave.xls", bi, objectMetaData).withCannedAcl(CannedAccessControlList.PublicRead));
        bi.close();

        xmlLoaded = true;
        if (xmlLoaded) {
            return "path";
        }
        return null;

    }

    private void createLabel(HSSFSheet sheet, String[] headerArray) {
        HSSFRow rowhead = sheet.createRow((short) 0);
        for (int i = 0; i < headerArray.length; i++) {
            rowhead.createCell(i).setCellValue(headerArray[i]);
        }
    }

    private void createContentProducteur(HSSFSheet sheet, List<Producteur> listProducteurs) {
        int i = 1;
        for (Producteur o : listProducteurs) {
            HSSFRow row = sheet.createRow((short) i);
            row.createCell(0).setCellValue(o.getId().toString());
            row.createCell(1).setCellValue(o.getNom());
            row.createCell(2).setCellValue(o.getAdresse());
            row.createCell(3).setCellValue(o.getContact());
            i++;
        }
    }

    private String getStringXml(Position position) {
        return position.getNomCave() + " " + position.getReferenceC() + "R" + position.getReferenceR() + "P" + position.getReferenceP() + "/";
    }

    private void createContentBouteille(HSSFSheet sheet, List<Bouteille> listBouteilles) {
        int i = 1;
        for (Bouteille o : listBouteilles) {
            List<Position> positions = o.getPositions();
            String positionsString = "";
            if (positions != null && !positions.isEmpty()) {
                for (Position p : positions) {
                    positionsString = positionsString + getStringXml(p);
                }
            }
            HSSFRow row = sheet.createRow((short) i);
            row.createCell(0).setCellValue(o.getId().toString());
            row.createCell(1).setCellValue(o.getNom());
            row.createCell(2).setCellValue(o.getPays());
            row.createCell(3).setCellValue(o.getRegion());
            row.createCell(4).setCellValue(o.getAppelation());
            row.createCell(5).setCellValue(o.getCru());
            row.createCell(6).setCellValue(o.getCouleur());
            row.createCell(7).setCellValue(o.getTaille().toString());
            row.createCell(8).setCellValue(o.getUrlAchat());
            row.createCell(9).setCellValue(o.getNbrListCourses().toString());
            row.createCell(10).setCellValue(o.getPrixAchat().toString());
            row.createCell(11).setCellValue(o.getPrixActuelle().toString());
            row.createCell(12).setCellValue(o.getDateDeProduction().toString());
            row.createCell(13).setCellValue(o.getDateGarder().toString());
            row.createCell(14).setCellValue(o.getEvaluation().toString());
            row.createCell(15).setCellValue(o.getCommentaire());
            row.createCell(16).setCellValue(positionsString);
            row.createCell(17).setCellValue(o.getNomProducteur() + "(" + o.getIdProducteur() + ")");
            i++;
        }
    }
}


