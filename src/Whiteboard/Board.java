/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Whiteboard;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private Vector<JTextField> fields = new Vector<>();

    private Canvas drawPanel;
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
    private JPopupMenu textMenu;
    private JPanel shapeMenuPanel;
    private JPanel textMenuPanel;
    private JTextField textMenuField;
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
        drawPanel = new Canvas();
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
        textMenu = new JPopupMenu();
        textMenuPanel = new JPanel();
        textMenuPanel.setLayout(new BorderLayout());
        textMenuField = new JTextField();
        Dimension d = new Dimension(200, 25);
        textMenuField.setPreferredSize(d);
        textMenuPanel.add(textMenuField, BorderLayout.CENTER);
        textMenu.add(textMenuPanel);
        //textMenu.pack();
        

        //this.setBackground(new java.awt.Color(255, 255, 255));
        this.setLayout(new BorderLayout());

        drawPanel.setBackground(Color.WHITE);
        //drawPanel.setForeground(Color.WHITE);

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

        this.add(toolBar, BorderLayout.NORTH);
        this.add(drawPanel, BorderLayout.CENTER);

        textButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (textButton.isSelected()) {
                    freeDrawButton.setSelected(false);
                    eraserButton.setSelected(false);
                    int x = 183;
                    int y = 5;
                    textMenu.show(drawPanel, x, y);
                }
            }
        });

        eraserButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (eraserButton.isSelected()) {
                    freeDrawButton.setSelected(false);
                    textButton.setSelected(false);
                    s = new Freedraw();
                }
            }
        });

        shapesButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                Dimension size = shapeMenu.getPreferredSize();
                int x = 40;
                int y = 5;
                shapeMenu.show(drawPanel, x, y);
            }
        });
        linebutton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                freeDrawButton.setSelected(false);
                eraserButton.setSelected(false);
                textButton.setSelected(false);
                s = new Line();
                shapeMenu.setVisible(false);
                repaint();
            }
        });
        ovalbutton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                freeDrawButton.setSelected(false);
                eraserButton.setSelected(false);
                textButton.setSelected(false);
                s = new Oval();
                shapeMenu.setVisible(false);
            }
        });
        rectbutton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                freeDrawButton.setSelected(false);
                eraserButton.setSelected(false);
                textButton.setSelected(false);
                s = new Rectangle();
                shapeMenu.setVisible(false);
            }
        });

        colorButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JColorChooser jcc = new JColorChooser();
                c = JColorChooser.showDialog(jcc, "Color Selector", null);
                colorButton.setBackground(c);

            }
        });
        clearButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                freeDrawButton.setSelected(false);
                eraserButton.setSelected(false);
                textButton.setSelected(false);
                clearPanel();
            }
        });

        freeDrawButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                eraserButton.setSelected(false);
                textButton.setSelected(false);
                s = new Freedraw();
            }
        });

        drawPanel.addMouseMotionListener(new java.awt.event.MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                if (!textButton.isSelected()) {
                    Graphics fgg = fg.getGraphics();
                    fgg.drawImage(bg, 0, 0, null);

                    BasicStroke bs = new BasicStroke(widthSlider.getValue() / 4);

                    newX = e.getX();
                    newY = e.getY();

                    if (s instanceof Freedraw && !eraserButton.isSelected()) {
                        freeDrawButton.setSelected(true);
                        s.drawShape(fgg, c, newX, newY, newX, newY, filled, bs);
                        Graphics bgg = bg.getGraphics();
                        bgg.setColor(c);
                        bgg.drawImage(fg, 0, 0, null);
                    } else if (s instanceof Freedraw && eraserButton.isSelected()) {
                        s.drawShape(fgg, Color.WHITE, newX, newY, newX, newY, filled, bs);
                        Graphics bgg = bg.getGraphics();
                        bgg.setColor(c);
                        bgg.drawImage(fg, 0, 0, null);
                    } else {
                        s.drawShape(fgg, c, oldX, oldY, newX, newY, filled, bs);
                    }
                    v.add(s);
                    Graphics g = drawPanel.getGraphics();
                    g.drawImage(fg, 0, 0, null);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });

        drawPanel.addMouseListener(new java.awt.event.MouseListener() {
            public void mousePressed(MouseEvent e) {
                if (!textButton.isSelected()){
                if (bg == null) {
                    bg = drawPanel.createImage(drawPanel.getWidth(), drawPanel.getHeight());
                    fg = drawPanel.createImage(drawPanel.getWidth(), drawPanel.getHeight());
                    filled = false;
                    v = new Vector<Shapes>();
                    if (s == null) {
                        s = new Freedraw();
                    }
                }
                oldX = e.getX();
                oldY = e.getY();
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (bg != null){
                    Graphics bgg = bg.getGraphics();
                    bgg.setColor(c);
                    bgg.drawImage(fg, 0, 0, null);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (textButton.isSelected()){
                    textMenu.setVisible(false);
                    //textButton.setSelected(false);
                    System.out.println("Drawing text!");
                    //Graphics bgg = bg.getGraphics();  //Create new graphics items for double buffering.  
                    //Graphics fgg = fg.getGraphics();
                    //fgg.setColor(c); //Sets the color.  
                    String stringToDraw = textMenuField.getText();
                    //fgg.drawString(stringToDraw, e.getX(), e.getY());
                    //repaint();
                    Graphics2D g2d = (Graphics2D)drawPanel.getGraphics();
                    g2d.setColor(c);
                    g2d.drawString(text, e.getX(), e.getY());
                    //v.add(text);
                }
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
    public void clearPanel(){
        try {
            Graphics bgg = bg.getGraphics();
            Graphics fgg = drawPanel.getGraphics();
            bgg.clearRect(0, 0, 2000, 2000);//clears a rectangle in the background
            fgg.clearRect(0, 0, 2000, 2000);//clears a rectangle in the foreground
        }
        catch (NullPointerException ex){}
    }

    private JSlider getSlider() {
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
