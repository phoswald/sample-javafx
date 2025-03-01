package com.github.phoswald.sample.canvas;

import java.nio.IntBuffer;

import javafx.scene.image.Image;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;

class MandelbrotBuilder {
    
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLUE = 0xFF0000FF;

    private final double ptMinReal = -2.5;
    private final double ptMinImag = 2.5;
    private final double ptMaxReal = 2.5;
    private final double ptMaxImag = -2.5;
    
    private final int width;
    private final int height;
    private final int[] pixelArray;
    private final PixelBuffer<IntBuffer> pixelBuffer;
    private final WritableImage image;
    private int iterMax = 2;
    
    MandelbrotBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixelArray = new int[width * height];
        this.pixelBuffer = new PixelBuffer<>(
                width, height, 
                IntBuffer.wrap(pixelArray), 
                PixelFormat.getIntArgbPreInstance());
        this.image = new WritableImage(pixelBuffer);
    }
    
    Image getImage() {
        return image;
    }

    void updateImage() {
        double deltaX = (ptMaxReal - ptMinReal) / width;
        double deltaY = (ptMaxImag - ptMinImag) / height;
        for(int index = 0, y = 0; y < height; y++) {
            for(int x = 0; x < width; x++, index++) {
                double iterReal = 0.0;
                double iterImag = 0.0;
                double ptReal = ptMinReal + x * deltaX;
                double ptImag = ptMinImag + y * deltaY;
                int iterCur = 0;
                while(iterReal * iterReal + iterImag * iterImag <= 4.0 && iterCur < iterMax) {
                    double iterNextReal = iterReal * iterReal - iterImag * iterImag + ptReal;
                    double iterNextImag = 2 * iterReal * iterImag + ptImag;
                    iterReal = iterNextReal;
                    iterImag = iterNextImag;
                    iterCur++;
                }
                if(iterCur == iterMax) {
                    pixelArray[index] = BLACK;
                } else {
                    pixelArray[index] = (iterCur & 1) == 1 ? WHITE : BLUE;
                }
            }
        }
        iterMax++;
        pixelBuffer.updateBuffer(pb -> null);
    }
}
