package com.example.myforum_springboot.utils;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    private static String img = null;

    // 文件上传
    public static String uploadImgFile(HttpServletRequest request, MultipartFile photo) {
        if (!photo.isEmpty()) {
            String oldFileName = photo.getOriginalFilename();
            String prefix = FilenameUtils.getExtension(oldFileName);
            if (!checkImgFile(prefix)) {
                return "非图片类型";
            }
            File filePath = new File("src/main/resources/static/upload/");
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            String newFilename = UUID.randomUUID().toString().replace("-", "") + ".jpg";
            try {
                photo.transferTo(new File(filePath.getAbsolutePath() + "\\" + newFilename));
                img = "/upload/" + newFilename;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return img;
        }
        return "图片为空";
    }

    // 检验文件是否为图片格式
    public static boolean checkImgFile(String fileName) {
        String suffixList = "jpg,gif,png,ico,bmp,jpeg";
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (suffixList.contains(suffix.trim().toLowerCase())) {
            return true;
        }
        return false;
    }

    // 文件删除
    public static boolean deleteImgFile(String filePath) {
        String realPath = "src/main/resources/static" + filePath;
        File delFile = new File(realPath);
        if (delFile.isFile() && delFile.exists()) {
            delFile.delete();
            return true;
        }
        return false;
    }

    // 文件批量删除
    public static boolean deleteManyFiles(String[] filePath) {
        for (int i = 0; i < filePath.length; i++) {
            String realPath = "src/main/resources/static" + filePath[i];
            File delFile = new File(realPath);
            if (delFile.isFile() && delFile.exists()) {
                delFile.delete();
            }
        }
        return true;
    }
}

