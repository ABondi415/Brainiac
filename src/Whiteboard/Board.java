/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Whiteboard;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Board extends JPanel implements ActionListener {
    
    //Panel components
    private JPanel menuBar;
    private JButton ovalBut, rectBut, lineBut, freehandBut, eraserBut, clearBut;
    private JSlider sizeSlider;
    
    private Canvas drawCanvas;

    //Graphics
    private Image bg, fg;
    private int oldx, oldy;
    private int strokeSize;
    
    
    public Board() {
        this.setLayout(new BorderLayout());
        this.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent evt){
                this.mousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt){
                this.mouseReleased(evt);
            }
        });
        this.addMouseMotionListener(new MouseAdapter(){
           public void mouseDragged(MouseEvent evt){
               this.mouseDragged(evt);
           }
           
           public void componentResized(MouseEvent evt){
               this.componentResized(evt);
           }
        });
        
        //creates menu and draw canvas
        initComponents();
        
        this.add(menuBar, BorderLayout.NORTH);
        this.add(drawCanvas, BorderLayout.CENTER);
    }
    
    public void paint(Graphics g){
        super.paint(g);
        Graphics pg = drawCanvas.getGraphics();
        bg = drawCanvas.createImage(drawCanvas.getWidth(), drawCanvas.getHeight());
        fg = drawCanvas.createImage(drawCanvas.getWidth(), drawCanvas.getHeight());
        
        Graphics fgg = fg.getGraphics();
        Graphics2D f2d = (Graphics2D) fgg;
        
        pg.drawImage(fg, 0, 0, null);
        Graphics bgg = bg.getGraphics();
        bgg.drawImage(fg, 0, 0, null);
    }
    
    private void mousePressed(MouseEvent evt){
        Object o = evt.getSource();
        
        if (o == drawCanvas){
            oldx = evt.getX();
            oldy = evt.getY();
            
            if (bg == null){
                bg = drawCanvas.createImage(drawCanvas.getWidth(), drawCanvas.getHeight());
                fg = drawCanvas.createImage(drawCanvas.getWidth(), drawCanvas.getHeight());
            }
        }
        
    }

    private void mouseReleased(MouseEvent evt){
        Object o = evt.getSource();
        
        if (o == drawCanvas){
            Graphics bgg = bg.getGraphics();
            bgg.drawImage(fg, 0, 0, null);
        }
    }
    
    private void compResized(MouseEvent evt){
        repaint();
    }
    
    private void mouseDragged(MouseEvent evt){
        Object o = evt.getSource();
        
        if (o == drawCanvas){ 
            //draw background on foreground
            Graphics fgg = fg.getGraphics();
            fgg.drawImage(bg, 0, 0, null);
            
            //draw our stuff to foreground
            Graphics2D f2d = (Graphics2D) fgg;
            
            //for freedraw only
        
                //OLDCODE b.draw(f2d, oldx, oldy, evt.getX(), evt.getY(), dashed, filled, strokeSize, text);
            
                //draw our newly edited foreground to the background
                Graphics bgg = bg.getGraphics();
                bgg.drawImage(fg, 0, 0, null);
                oldx = evt.getX();
                oldy = evt.getY();
            //end for freedraw only
                
            Graphics g = drawCanvas.getGraphics();
            g.drawImage(fg, 0, 0, null);
        }   
    }
    
    private void sliderMoved(ChangeEvent ev) {
        strokeSize = (2* sizeSlider.getValue());
    }

    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        
        if (o == clearBut){
            Graphics bgg = bg.getGraphics();
            bgg.clearRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
            repaint();
        }
    }
    
    
    
    
    
    
    
    private void initComponents(){        
        //Create MenuBar
        //ovalBut = new OvalBut();
        //  ovalBut.addActionListener(this);
        //rectBut = new RectBut();
        //  rectBut.addActionListener(this);
        //lineBut = new LineBut();
        //  lineBut.addActionListener(this);
        freehandBut = new JButton();
            freehandBut.setText("Free Hand");
            freehandBut.addActionListener(this);
        eraserBut = new JButton();
            eraserBut.setText("Eraser");
            eraserBut.addActionListener(this);
        clearBut = new JButton();
            clearBut.setText("Clear Board");
            clearBut.addActionListener(this);
        sizeSlider = new JSlider(); 
            sizeSlider.setValue(1);
                strokeSize = (2*sizeSlider.getValue());
            sizeSlider.setMaximum(10);
            sizeSlider.setMajorTickSpacing(1);
            sizeSlider.setMaximum(10);
            sizeSlider.setPaintTicks(true);
            sizeSlider.addChangeListener(new ChangeListener(){
                public void stateChanged(ChangeEvent ev){
                    //sizeSlider.getRootPane().sliderMoved(ev);
                }
            });
        menuBar = new JPanel();
            menuBar.setPreferredSize(new Dimension(this.getWidth(), 30));
            menuBar.setLayout(new GridLayout(8, 1, 5, 5));
            menuBar.add(freehandBut);
            //menuBar.add(ovalBut);
            //menuBar.add(rectBut);
            //menuBar.add(lineBut);
            menuBar.add(eraserBut);
            menuBar.add(clearBut);
            menuBar.add(sizeSlider);
//
//            menuBar.add(ADD COLOR THINGY);
//       
        
        drawCanvas = new Canvas();   
    }
}
