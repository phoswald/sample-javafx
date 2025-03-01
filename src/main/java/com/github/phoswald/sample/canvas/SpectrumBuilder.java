package com.github.phoswald.sample.canvas;

import java.nio.IntBuffer;

import javafx.scene.image.Image;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;

class SpectrumBuilder {

    Image createImage(int width, int height) {
        int[] pixelArray = new int[width * height];
        PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer<>(
                width, height, 
                IntBuffer.wrap(pixelArray), 
                PixelFormat.getIntArgbPreInstance());
        WritableImage image = new WritableImage(pixelBuffer);
        for(int index = 0, y = 0; y < height; y++) {
            for(int x = 0; x < width; x++, index++) {
                pixelArray[index] = rgb(x, y, 0);
            }
        }
        return image;
    }
    
    private static int rgb(int r, int g, int b) {
        return 0xFF000000 | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF));
    }
}
