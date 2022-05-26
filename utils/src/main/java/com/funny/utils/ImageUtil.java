package com.funny.utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.*;
import java.util.Iterator;


public class ImageUtil {
    private static final float DEFAULT_QUALITY = 0.2125f;




    public static BufferedImage getImage(String source) throws IOException {
        File file = new File(source);
        if (!FileUtil.exist(file)) {
            throw new IllegalArgumentException("FILE LOAD FAIL");
        }

        return ImageIO.read(new FileInputStream(file));
    }


    public static BufferedImage getImage(File file) throws IOException {
        if (!FileUtil.exist(file)) {
            throw new IllegalArgumentException("FILE LOAD FAIL");
        }

        return ImageIO.read(file);
    }


    public static BufferedImage resize(BufferedImage source,int width,int height) {
        if (width <= 0) {
            width = 1;
        }

        if (height <= 0) {
            height = 1;
        }

        return processing(source,width,height);
    }


    public static BufferedImage cut(File source,Rectangle rectangle) throws IOException {
        if (!FileUtil.exist(source)) {
            throw new IllegalArgumentException("FILE LOAD FAIL");
        }

        int width = rectangle.width;
        int height = rectangle.height;

        int x = rectangle.x;
        int y = rectangle.y;

        BufferedImage sourceImage = getImage(source.getAbsolutePath());
        BufferedImage targetImage = new BufferedImage(width,height,Transparency.TRANSLUCENT);

        Graphics graphics = targetImage.getGraphics();
        graphics.drawImage(sourceImage,0,0,width,height,x,y,width+x,height+y,null);
        return targetImage;
    }


    public static BufferedImage magnifyRatio(BufferedImage source,int widthRatio,int heightRatio) {
        if (widthRatio <= 0) {
            widthRatio = 1;
        }

        if (heightRatio <= 0) {
            heightRatio = 1;
        }

        int width = source.getWidth() * widthRatio;
        int height = source.getHeight() * heightRatio;
        return processing(source,width,height);
    }


    public static BufferedImage shrinkRatio(BufferedImage source,int widthRatio,int heightRatio) {
        if (widthRatio <= 0) {
            widthRatio = 1;
        }

        if (heightRatio <= 0) {
            heightRatio = 1;
        }

        int width = source.getWidth() / widthRatio;
        int height = source.getHeight() / heightRatio;
        return processing(source,width,height);
    }


    private static BufferedImage processing(BufferedImage source,int width,int height) {
        BufferedImage newImage = new BufferedImage(width,height,source.getType());

        Graphics graphics = newImage.getGraphics();
        graphics.drawImage(source,0,0,width,height,null);
        graphics.dispose();

        return newImage;
    }


    public static void executeRgb(BufferedImage source,int alpha) throws IOException {
        getRgb(source,true,alpha);
    }


    public static String[][] getRgb(BufferedImage source) throws IOException {
        return getRgb(source,false,0);
    }


    public static String[][] getRgb(BufferedImage source,boolean execute,int alpha) throws IOException {
        int width = source.getWidth();
        int height = source.getHeight();

        int minX = source.getMinX();
        int minY = source.getMinY();

        int[] rgbs = new int[3];
        String[][] list = new String[width][height];
        for (int i=minX;i<minY;i++) {
            for (int j=minY;j<height;j++) {
                int rgb = source.getRGB(i,j);
                rgbs[0] = (rgb & 0xff0000) >> 16;
                rgbs[1] = (rgb & 0xff00) >> 8;
                rgbs[2] = (rgb & 0xff);

                list[i][j] = rgbs[0] + "," + rgbs[1] + "," + rgbs[2];

                if (execute) {
                    int R = rgbs[0];
                    int G = rgbs[1];
                    int B = rgbs[2];
                    if (((255 - R) < 30) && ((255 - G) < 30) && ((255 - B) < 30)) {
                        rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
                        source.setRGB(i,j,rgb);
                    }
                }
            }
        }

        return list;
    }


    public static void crop(String source,String target,int x,int y,int width,int height,String readFormat,String writeFormat) throws IOException {
        FileInputStream file = new FileInputStream(source);
        ImageInputStream image = ImageIO.createImageInputStream(file);

        Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(readFormat);
        ImageReader reader = iterator.next();
        reader.setInput(image,true);

        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rectangle = new Rectangle(x,y,width,height);
        param.setSourceRegion(rectangle);

        BufferedImage bufferedImage = reader.read(0,param);
        ImageIO.write(bufferedImage,writeFormat,new File(target));
    }


    public static void reduceByRatio(String source,String target,int widthRatio,int heightRatio) throws IOException {
        File sourceFile = new File(source);
        if (!FileUtil.exist(sourceFile)) {
            throw new IllegalArgumentException("FILE LOAD FAIL");
        }

        String prefix = FileUtil.suffix(sourceFile);
        FileOutputStream targetFile = new FileOutputStream(target);

        BufferedImage sourceImage = getImage(sourceFile);
        BufferedImage targetImage = shrinkRatio(sourceImage,widthRatio,heightRatio);
        ImageIO.write(targetImage,prefix,targetFile);
    }


    public static void reduceProportion(String source,String target,int ratio) throws IOException {
        reduceByRatio(source,target,ratio,ratio);
    }


    public static void gray(String source,String target,String format) throws IOException {
        BufferedImage sourceImage = getImage(source);
        ColorSpace colorSpace = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(colorSpace,null);
        sourceImage = op.filter(sourceImage,null);

        ImageIO.write(sourceImage,format,new File(target));
    }


    public static void addWaterMark(String source,String mark,String dest,int x,int y,float alpha) throws IOException {
        BufferedImage bufferedImage = addWaterMark(source,mark,x,y,alpha);
        ImageIO.write(bufferedImage,FileUtil.fileType(source),new File(dest));
    }


    public static void addWaterMark(String source,String mark,String dest,String format,int x,int y,float alpha) throws IOException {
        BufferedImage bufferedImage = addWaterMark(source,mark,x,y,alpha);
        ImageIO.write(bufferedImage,format,new File(dest));
    }


    public static BufferedImage addWaterMark(String source,String mark,int x,int y,float alpha) throws IOException {
        Image sourceImage = getImage(source);
        BufferedImage sourceBufferdImage = new BufferedImage(sourceImage.getWidth(null),sourceImage.getHeight(null),BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = sourceBufferdImage.createGraphics();
        graphics.drawImage(sourceImage,0,0,null);

        Image markImage = getImage(mark);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
        graphics.drawImage(markImage,x,y,null);
        graphics.dispose();

        return sourceBufferdImage;
    }


    public static void addTextMark(String source,String mark,String dest,Font font,Color color,float x,float y,float alpha) throws IOException {
        BufferedImage bufferedImage = addTextMark(source,mark,font,color,x,y,alpha);
        ImageIO.write(bufferedImage,FileUtil.fileType(source),new File(dest));
    }


    public static void addTextMark(String source,String mark,String dest,String format,Font font,Color color,float x,float y,float alpha) throws IOException {
        BufferedImage bufferedImage = addTextMark(source,mark,font,color,x,y,alpha);
        ImageIO.write(bufferedImage,format,new File(dest));
    }


    public static BufferedImage addTextMark(String source,String mark,Font font,Color color,float x,float y,float alpha) throws IOException {
        Font textFont = (font == null) ? new Font(null,20,13) : font;

        Image sourceImage = getImage(source);
        BufferedImage sourceBufferedImage = new BufferedImage(sourceImage.getWidth(null),sourceImage.getHeight(null),BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = sourceBufferedImage.createGraphics();
        graphics.drawImage(sourceImage,0,0,null);
        graphics.setColor(color);
        graphics.setFont(textFont);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
        graphics.drawString(mark,x,y);
        graphics.dispose();

        return sourceBufferedImage;
    }


    public static void compress(String source,String dest,float quality,int width,int height,boolean auto) throws IOException {
        BufferedImage bufferedImage = compress(source,quality,width,height,auto);
        ImageIO.write(bufferedImage,FileUtil.fileType(source),new File(dest));
    }


    public static void compress(String source,String dest,String format,float quality,int width,int height,boolean auto) throws IOException {
        BufferedImage bufferedImage = compress(source,quality,width,height,auto);
        ImageIO.write(bufferedImage,format,new File(dest));
    }



    public static BufferedImage compress(String source,float quality,int width,int height,boolean auto) throws IOException {
        if (quality < 0F || quality > 1F) {
            quality = DEFAULT_QUALITY;
        }

        Image sourceImage = getImage(source);
        int sourceWitdh = sourceImage.getWidth(null);
        int sourceHeight = sourceImage.getHeight(null);

        int compWidth = (width > 0) ? width : sourceWitdh;
        int compHeight = (height > 0) ? height : sourceHeight;

        if (auto) {
            double widthRate = sourceWitdh / (width + 0.1);
            double heightRate = sourceHeight / (height + 0.1);
            double rate = (widthRate > heightRate) ? widthRate : heightRate;

            compWidth = ConverterUtil.toIntValue(sourceWitdh/rate);
            compHeight = ConverterUtil.toIntValue(sourceHeight/rate);
        }

        BufferedImage sourceBufferedImage = new BufferedImage(compWidth,compHeight,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = sourceBufferedImage.createGraphics();
        graphics.drawImage(sourceImage,0,0,compWidth,compHeight,null);
        graphics.dispose();

        return sourceBufferedImage;
    }


    public static void transparency(String source,String dest) throws IOException {
        BufferedImage bufferedImage = transparency(source);
        ImageIO.write(bufferedImage,FileUtil.fileType(source),new File(dest));
    }


    public static void transparency(String source,String dest,String format) throws IOException {
        BufferedImage bufferedImage = transparency(source);
        ImageIO.write(bufferedImage,format,new File(dest));
    }


    public static BufferedImage transparency(String source) throws IOException {
        int alpha = 0;
        BufferedImage sourceImage = ImageIO.read(new FileInputStream(source));
        executeRgb(sourceImage,alpha);

        BufferedImage targetImage = new BufferedImage(sourceImage.getWidth(null),sourceImage.getHeight(null),BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = targetImage.createGraphics();
        graphics.drawImage(targetImage,0,0,null);
        graphics.dispose();

        return targetImage;
    }


    public static void convert(String source,String format,String dest) throws IOException {
        BufferedImage bufferedImage = getImage(source);
        ImageIO.write(bufferedImage,format,new File(dest));
    }


    public static void convert(BufferedImage source,String format,String dest) throws IOException {
        ImageIO.write(source,format,new File(dest));
    }


    private ImageUtil() {
        throw new IllegalStateException("THIS IS A UTILITY CLASS");
    }
}
