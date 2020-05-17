package com.zhiming.travel.picture.util;

import com.zhiming.travel.picture.bo.PictureBO;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

/**
 * @author HuangZhiMing
 */
public class FileUtil {

    public static String genUUIDFileName(MultipartFile file) {
        return genFileName(UUID.randomUUID().toString(), file);
    }

    public static String genIdFileName(Long id, MultipartFile file) {
        return genFileName(id.toString(), file);
    }

    public static String genFileName(String name, MultipartFile file) {
        String fileType = file.getContentType();
        if (fileType != null) {
            String suffix = fileType.substring(fileType.indexOf("/") + 1);
            return name.concat(".").concat(suffix);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static PictureBO parsePictureInfo(MultipartFile file) throws IOException {
        BufferedImage image = ImageIO.read(file.getInputStream());
        PictureBO pictureBO = new PictureBO();
        pictureBO.setWidth(image.getWidth());
        pictureBO.setHeight(image.getHeight());
        pictureBO.setSize(file.getSize());
        return pictureBO;
    }
}
