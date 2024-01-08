package com.example.mkt.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ConversorImage {

    public static byte[] converterParaImagem(MultipartFile photo) throws IOException {
        return photo.getBytes();
    }

    public static String converterParaBase(byte[] photoProfile){
        byte[] photoBase = Base64.encodeBase64(photoProfile);
        return new String(photoBase);
    }

    public static boolean isImageFile(MultipartFile image){
        if(image.isEmpty()){
            return false;
        }

        String originalFileName = image.getOriginalFilename();

        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String[] imageExtensions = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        for (String ext : imageExtensions){
            if(fileExtension.equalsIgnoreCase(ext)){
                return true;
            }
        }
        return false;
    }
}
