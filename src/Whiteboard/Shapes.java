/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Whiteboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;


public abstract class Shapes {

    private int x;
    private int y;
    private int w;
    private int h;
    
    public abstract void drawShape(Graphics g, Color c, int x, int y, int w, int h, boolean filled, BasicStroke bs);
}
