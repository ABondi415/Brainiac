/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Whiteboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author rsd5098
 */
public class Rectangle extends Shapes{
    
    public void drawShape(Graphics g, Color c, int x, int y, int w, int h, boolean filled, BasicStroke bs)
    {
        Graphics2D g2d =  (Graphics2D) g;
        g2d.setColor(c);
        g2d.setStroke(bs);
        
        if(filled){
        g2d.fillRect(Math.min(x, w), Math.min(y, h), Math.abs(x - w), Math.abs(y - h));
        }
        else{
            g2d.drawRect(Math.min(x, w), Math.min(y, h), Math.abs(x - w), Math.abs(y - h));
        }
    }
    

}
