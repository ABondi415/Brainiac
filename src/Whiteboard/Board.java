/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Whiteboard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.*;

public class Board extends JPanel implements MouseListener {

    private Color c;
    private int oldX, oldY, newX, newY;
    private Image bg, fg;
    private String text;
    private Shapes s;
    private int type;
    private boolean filled = false;
    private BasicStroke bs;
    private Vector<Shapes> v = new Vector();

    private JToggleButton clearButton;
    private JPanel colorPanel;
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
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //super.paint(g);
        drawPanel.getGraphics().drawImage(bg, 0, 0, null);
    }

    public Board() {
        
        drawPanel = new JPanel();
        toolBar = new JToolBar();
        freeDrawButton = new JToggleButton();
        shapesButton = new JButton();
        textButton = new JToggleButton();
        eraserButton = new JToggleButton();
        clearButton = new JToggleButton();
        widthSlider = new JSlider();
        colorPanel = new JPanel();
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
        //shapeMenu.setVisible(false);

        drawPanel.setBackground(new java.awt.Color(255, 255, 255));
        drawPanel.setLayout(new BorderLayout());
        drawPanel.addMouseListener(this);

        colorPanel.setBackground(new java.awt.Color(102, 102, 102));

        freeDrawButton.setText("Draw");
        shapesButton.setText("Shapes");
        shapesButton.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                Object o = e.getSource();
                if (o == shapesButton) {
                    //shapeMenu = new JFrame();
//                    linebutton.setText("Line");
//                    ovalbutton.setText("Oval");
//                    rectbutton.setText("Rectangle");
//                    shapeMenu.setLayout(new GridLayout());
//                    shapeMenu.add(linebutton);
//                    shapeMenu.add(ovalbutton);
//                    shapeMenu.add(rectbutton);
//                    shapeMenu.pack();
//                    shapeMenu.setVisible(true);
                    Dimension size = shapeMenu.getPreferredSize();
                    int x = (shapesButton.getWidth() - size.width);
                    int y = shapesButton.getHeight();
                    //shapeMenu.show(shapesButton, x, y);
                    shapeMenu.show(drawPanel, x, y);
                }
            }
        });
        textButton.setText("Text");
        eraserButton.setText("Eraser");
        clearButton.setText("Clear");
        toolBar.setLayout(new GridLayout());
        toolBar.add(freeDrawButton);
        toolBar.add(shapesButton);
        toolBar.add(textButton);
        toolBar.add(eraserButton);
        toolBar.add(clearButton);
        toolBar.add(colorPanel);

        drawPanel.add(toolBar, BorderLayout.NORTH);

        drawPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Object o = e.getSource();
                if (o == drawPanel) {
                    Graphics fgg = fg.getGraphics();
                    fgg.drawImage(bg, 0, 0, null);

                    BasicStroke bs = new BasicStroke(widthSlider.getValue());

                    newX = e.getX();
                    newY = e.getY();
                    s.drawShape(fgg, c, oldX, oldY, newX, newY, filled, bs);

                    v.add(s);
                    Graphics g = drawPanel.getGraphics();
                    g.drawImage(fg, 0, 0, null);
                }
            }
        });
        
        drawPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Object o = e.getSource();
                if (o == shapesButton) {
                    //shapeMenu = new JFrame();
//                    linebutton.setText("Line");
//                    ovalbutton.setText("Oval");
//                    rectbutton.setText("Rectangle");
//                    shapeMenu.setLayout(new GridLayout());
//                    shapeMenu.add(linebutton);
//                    shapeMenu.add(ovalbutton);
//                    shapeMenu.add(rectbutton);
//                    shapeMenu.pack();
//                    shapeMenu.setVisible(true);
                    Dimension size = shapeMenu.getPreferredSize();
                    int x = (shapesButton.getWidth() - size.width);
                    int y = shapesButton.getHeight();
                    //shapeMenu.show(shapesButton, x, y);
                    shapeMenu.show(drawPanel, x, y);
                }
                if (o == linebutton) {
                    s = new Line();
                    freeDrawButton.setSelected(false);
                    shapeMenu.setVisible(false);
                }
                if (o == ovalbutton) {
                    s = new Line();
                    freeDrawButton.setSelected(false);
                    shapeMenu.setVisible(false);
                }
                if (o == rectbutton) {
                    s = new Line();
                    freeDrawButton.setSelected(false);
                    shapeMenu.setVisible(false);
                }
                if (o == colorPanel) {
                    JColorChooser jcc = new JColorChooser();
                    c = JColorChooser.showDialog(jcc, "Color Selector", null);
                    colorPanel.setBackground(c);
                }
            }

            public void mousePressed(MouseEvent e) {
                Object o = e.getSource();
                if (o == drawPanel) {
                    if (bg == null) {
                        bg = drawPanel.createImage(drawPanel.getWidth(), drawPanel.getHeight());
                        fg = drawPanel.createImage(drawPanel.getWidth(), drawPanel.getHeight());
                    }
                    oldX = e.getX();
                    oldY = e.getY();
                }
            }

            public void mouseReleased(MouseEvent e) {
                Object o = e.getSource();
                if (o == drawPanel) {
                    Graphics bgg = bg.getGraphics();
                    bgg.setColor(c);
                    bgg.drawImage(fg, 0, 0, null);
                }
            }

//            public void mouseEntered(MouseEvent e) {
//                Object o = e.getSource();
//                if (o == drawPanel) {
//
//                }
//            }
//
//            public void mouseExited(MouseEvent e) {
//                Object o = e.getSource();
//                if (o == drawPanel) {
//
//                }
//            }
    });
        this.setLayout(new BorderLayout());
        this.add(drawPanel, BorderLayout.CENTER);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object o = e.getSource();
        if (o == drawPanel){
            
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
