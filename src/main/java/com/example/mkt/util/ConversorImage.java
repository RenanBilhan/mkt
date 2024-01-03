package com.example.mkt.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ConversorImage {

    public static byte[] converterParaImagem(MultipartFile foto) throws IOException {
        return foto.getBytes();
    }

    public static String converterParaBase(byte[] fotoPerfil){
        byte[] fotoBase = Base64.encodeBase64(fotoPerfil);
        return new String(fotoBase);
    }

    public static boolean isImageFile(MultipartFile imagem){
        if(imagem.isEmpty()){
            return false;
        }

        String originalFileName = imagem.getOriginalFilename();

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
