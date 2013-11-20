/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Whiteboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class Oval extends Shapes{
    
    public void drawShape(Graphics g, Color c, int x, int y, int w, int h, boolean filled, BasicStroke bs)
    {
         Graphics2D g2d =  (Graphics2D) g;
        g2d.setColor(c);
        if(filled)
        g2d.fillOval(x,y,w,h);
        else
        g2d.drawOval(x, y, w, h);
    }
    
}
