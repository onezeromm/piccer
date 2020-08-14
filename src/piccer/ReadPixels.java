/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piccer;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 *
 * @author တစ်သုံည
 */
public class ReadPixels {
    static File f = null;
    static Dictionary<String, Character> dict = new Hashtable<String, Character>();
    static String finalTextImage = "";
    static int c = 0;
    static String go = "0123456789";
    static char[] text = new char[9];
    
    public static void main(String[] foo) {
    
  }
    
  public static int getRed(int pixel){
      int red = (pixel >> 16) & 0xff;
      return red;
  }
  public static int getGreen(int pixel){
      int green = (pixel >> 8) & 0xff;
      return green;
  }
  public static int getBlue(int pixel){
       int blue = (pixel) & 0xff;
      return blue;
  }

  private static void generateText(BufferedImage image, int resolutionPercent, int colorRange) {

    int oldW = image.getWidth();
    int oldH = image.getHeight();
	
    float viewableSizePercentage = 0.5f;
    
    BufferedImage newResizedImg = resize(image, (int)(oldW * viewableSizePercentage), (int)(oldH * viewableSizePercentage));
    
    int w = newResizedImg.getWidth();
    int h = newResizedImg.getHeight();
    
    //System.out.println("w,h: " + w + ", " + h);
    int pixShifter = resolutionPercent / 100;
    
    for (int i = 0; i < h; i= i +(pixShifter * 2))
    {
        for (int j = 0; j < w; j = j + pixShifter)
        {
                //System.out.println("x,y: " + j + ", " + i);
                int pixel = newResizedImg.getRGB(j, i);
                String key = genNstorePixs(getRed(pixel), getGreen(pixel), getBlue(pixel), colorRange);
                finalTextImage += dict.get(key);
        }
        finalTextImage += '\n';
    }
    
    System.out.println("Done");
  }
  
  private static String genNstorePixs(int r, int g, int b, int colorRange){
      
      String key = "";
      int check = 255;
      int ri = 0,gi = 0,bi = 0;
        do
        {
            ri++;
            check = check - colorRange;
        }while(!(r > check));

        do
        {
            gi++;
            check = check - colorRange;
        }while(!(g > check));

        do
        {
            bi++;
            check = check - colorRange;
        }while(!(b > check));

        key = String.format("%1$s,%2$s,%3$s",String.valueOf(ri),String.valueOf(gi),String.valueOf(bi));
        //System.out.println(key);

        if(!key.equals("1,1,1"))
        {
            if(dict.get(key) == null)
            {
                dict.put(key, text[c]);
                c++;
                if(c >= 9)
                {
                    c= 0;
                }
            }
        }
        else
        {
            if(dict.get(key) == null)
            {
                dict.put(key,'0');
            }
        }

    return key;
  }

  public static BufferedImage resize(BufferedImage img, int newW, int newH) {  
      int w = img.getWidth();  
      int h = img.getHeight();  
      BufferedImage dimg = new BufferedImage(newW, newH, img.getType());  
      Graphics2D g = dimg.createGraphics();  
      g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);  
      g.dispose();  
      return dimg;  
  }  
  
  public static String readPixels(String filePath, int resolutionPercentage, int colorRange) {
      
      text = go.toCharArray();
      
    try {
        f = new File(filePath);
      // get the BufferedImage, using the ImageIO class
      BufferedImage image = ImageIO.read(f);
      generateText(image, resolutionPercentage, colorRange);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    return finalTextImage;
  }
}