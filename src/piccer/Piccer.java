/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package piccer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author တစ်သုံည
 */
public class Piccer {

    static ReadPixels rpx = new ReadPixels();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String s =  ReadPixels.readPixels("test.png", 50, 60);
        try {
            PrintWriter writer = new PrintWriter("result.txt","UTF-8");
            writer.print(s);
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Piccer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Piccer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
