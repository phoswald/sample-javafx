package com.github.phoswald.sample.canvas;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Instant;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

class ImageExporter {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    void export(Image image) {
        String fileName = "target/" + Instant.now().toEpochMilli();
        export(image, fileName, "png");
        export(image, fileName, "jpeg");
        export(image, fileName, "bmp");
    }
    
    private void export(Image image, String fileName, String fileExt) {
        try {
            File file = new File(fileName + "." + fileExt);
            logger.info("Writing file: {}", file);
            // jpeg and bmp do not support alpha channels, must use TYPE_INT_RGB 
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image,
                    new BufferedImage((int) image.getWidth(), (int) image.getHeight(), BufferedImage.TYPE_INT_RGB)); 
            ImageIO.write(bufferedImage, fileExt, file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
