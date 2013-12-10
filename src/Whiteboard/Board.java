/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Whiteboard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Board extends JPanel {

    private Color c = new Color(0, 0, 0);
    private int oldX, oldY, newX, newY;
    private Image bg, fg;
    private String text;
    private Shapes s;
    private boolean filled = false;
    private BasicStroke bs;
    private Vector<Shapes> v = new Vector<Shapes>();

    private JPanel masterPanel;
    private JPanel drawPanel;
    private JToggleButton eraserButton;
    private JToggleButton freeDrawButton;
    private JButton shapesButton;
    private JToggleButton textButton;
    private JToolBar toolBar;
    private JSlider widthSlider;
    private JButton linebutton;
    private JButton ovalbutton;
    private JButton rectbutton;
    private JPopupMenu shapeMenu;
    private JPanel shapeMenuPanel;
    private JButton clearButton;
    private JButton colorButton;
    
    
    @Override
    public void paint(Graphics g) {

        super.paint(g);
       drawPanel.getGraphics().drawImage(bg, 0, 0, null);
        Graphics2D g2d = (Graphics2D) drawPanel.getGraphics();

        for (int i = 0; i < v.size(); i++) {
            Shapes s = v.elementAt(i);
            BasicStroke bs = new BasicStroke(widthSlider.getValue());
            g2d.setStroke(bs);
            s.drawShape(fg.getGraphics(), c, oldX, oldY, newX, newY, filled, bs);
        }

    }
    


    public Board() {
        final JPanel myself = this;
        masterPanel = new JPanel();
        drawPanel = new JPanel();
        toolBar = new JToolBar();
        freeDrawButton = new JToggleButton();
        shapesButton = new JButton();
        textButton = new JToggleButton();
        eraserButton = new JToggleButton();
        clearButton = new JButton();
        widthSlider = new JSlider();
        colorButton = new JButton();
        linebutton = new JButton();
        ovalbutton = new JButton();
        rectbutton = new JButton();
        linebutton.setText("Line");
        ovalbutton.setText("Oval");
        rectbutton.setText("Rectangle");
        shapeMenu = new JPopupMenu();
        shapeMenuPanel = new JPanel();
        shapeMenuPanel.setLayout(new GridLayout());
        shapeMenuPanel.add(linebutton);
        shapeMenuPanel.add(ovalbutton);
        shapeMenuPanel.add(rectbutton);
        shapeMenu.add(shapeMenuPanel);
        shapeMenu.pack();

        masterPanel.setBackground(new java.awt.Color(255, 255, 255));
        masterPanel.setLayout(new BorderLayout());

       drawPanel.setBackground(new java.awt.Color(255, 255, 255));
        drawPanel.setLayout(new BorderLayout());
        
        colorButton.setBackground(c);

        freeDrawButton.setText("Draw");
        shapesButton.setText("Shapes");
        textButton.setText("Text");
        eraserButton.setText("Eraser");
        clearButton.setText("Clear");
        
        
        toolBar.setLayout(new GridLayout());
        toolBar.add(freeDrawButton);
        toolBar.add(shapesButton);
        toolBar.add(textButton);
        toolBar.add(eraserButton);
        toolBar.add(clearButton);
        toolBar.add(getSlider());
        toolBar.add(colorButton);
       

        //drawPanel.add(toolBar, BorderLayout.NORTH);
        masterPanel.add(toolBar, BorderLayout.NORTH);
        masterPanel.add(drawPanel, BorderLayout.CENTER);
               
        shapesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                Dimension size = shapeMenu.getPreferredSize();
                int x = 5;
                int y = 5;
                shapeMenu.show(drawPanel, x, y);
                
            }
        });
        linebutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                s = new Line();
                freeDrawButton.setSelected(false);
                shapeMenu.setVisible(false);
       
            }
        });
        ovalbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                s = new Oval();
                freeDrawButton.setSelected(false); 
                shapeMenu.setVisible(false);
  
                
            }
        });
        rectbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                s = new Rectangle();
                freeDrawButton.setSelected(false);
                shapeMenu.setVisible(false);

            }
        });
        colorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                JColorChooser jcc = new JColorChooser();
                c = JColorChooser.showDialog(jcc, "Color Selector", null);
                colorButton.setBackground(c);
                
                
            }       
        });
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                Graphics bgg = bg.getGraphics();
                Graphics fgg = drawPanel.getGraphics();
                bgg.clearRect(0, 0, 2000, 2000);//clears a rectangle in the background
                fgg.clearRect(0, 0, 2000, 2000);//clears a rectangle in the foreground

            }
        });

        freeDrawButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s = new Freedraw();
            }
        });

        drawPanel.addMouseMotionListener(new java.awt.event.MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {

                Graphics fgg = fg.getGraphics();
                fgg.drawImage(bg, 0, 0, null);

                BasicStroke bs = new BasicStroke(widthSlider.getValue());

                newX = e.getX();
                newY = e.getY();

                if (s instanceof Freedraw) {
                    s.drawShape(fgg, c, newX, newY, newX, newY, filled, bs);
                   
                    Graphics bgg = bg.getGraphics();
                    bgg.setColor(c);
                    bgg.drawImage(fg, 0, 0, null);
                }
                else
                s.drawShape(fgg, c, oldX, oldY, newX, newY, filled, bs);

                v.add(s);
                Graphics g = drawPanel.getGraphics();
                g.drawImage(fg, 0, 0, null);

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        this.setLayout(new BorderLayout());
       // this.add(drawPanel, BorderLayout.CENTER);
            this.add(masterPanel, BorderLayout.CENTER);
        
        drawPanel.addMouseListener(new java.awt.event.MouseListener() {
            public void mousePressed(MouseEvent e) {

                if (bg == null) {
                    bg = drawPanel.createImage(drawPanel.getWidth(), drawPanel.getHeight());
                    fg = drawPanel.createImage(drawPanel.getWidth(), drawPanel.getHeight());
                    filled = false;
                    v = new Vector<Shapes>();
                    if(s == null)
                    {
                    s = new Freedraw();
                    }                 
                }
                oldX = e.getX();
                oldY = e.getY();
            }

            public void mouseReleased(MouseEvent e) {
                Graphics bgg = bg.getGraphics();
                bgg.setColor(c);
                bgg.drawImage(fg, 0, 0, null);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });

       

    }

    private JSlider getSlider()
  {
    if (this.widthSlider == null) {
      this.widthSlider = new JSlider();
      this.widthSlider.setMaximum(255);
      this.widthSlider.setPaintTicks(true);
      this.widthSlider.setMajorTickSpacing(50);
      this.widthSlider.setPaintLabels(true);
      this.widthSlider.setBackground(new Color(238, 0, 0));
      this.widthSlider.setValue(0);
      this.widthSlider.setMinorTickSpacing(10);
      this.widthSlider.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent e) {
          Board.this.repaint();
        }
      });
    }
    return this.widthSlider;
  }
    
 
    
}
