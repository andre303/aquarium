/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

public class NewClass extends JFrame{                        //Клас базового вікна
    private static final int DEFAULT_WIDTH = 400;       //Містить вкладений клас
    private static final int DEFAULT_HEIGHT = 400;      //MouseComponent

    public NewClass(int aglae_quantity)
    {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        add(new NewClass.MouseComponent(aglae_quantity));
    }


    class MouseComponent extends JComponent {        //Клас, що додає до форми обробник подій миші
        private int SIDELENGTH = 10;
        private int SIDEHEIGHT = 30;
         private int aglae_quantity;
        private ArrayList<Rectangle2D> fishes;
        private ArrayList<Rectangle2D> aglae;
        private Rectangle2D current;
        Graphics2D g2;

        public int getAglae_quantity() {
            return aglae_quantity;
        }

        public void setAglae_quantity(int aglae_quantity) {
            this.aglae_quantity = aglae_quantity;
        }

        public void setFishes(ArrayList<Rectangle2D> fishes) {
            this.fishes = fishes;
        }

        public void setAglae(ArrayList<Rectangle2D> aglae) {
            this.aglae = aglae;
        }

        public MouseComponent(int aglae_quantity)
        {
            this.aglae_quantity = aglae_quantity;
            fishes = new ArrayList<Rectangle2D>();
            aglae = new ArrayList<Rectangle2D>();

            current = null;

            addMouseListener(new MouseHandler(this));
            addMouseMotionListener(new MouseMotionHandler());
            addAglae();
        }

        public int getSIDELENGTH() {
            return SIDELENGTH;
        }

        public ArrayList<Rectangle2D> getFishes() {
            return fishes;
        }

        public ArrayList<Rectangle2D> getAglae() {
            return aglae;
        }

        public void paintComponent(Graphics grphs){
            g2 = (Graphics2D)grphs;

            for(Rectangle2D fish : fishes){
                g2.setColor(Color.YELLOW);
                g2.fill(fish);
                g2.setColor(Color.BLACK);
                g2.draw(fish);
            }

            for(Rectangle2D agl : aglae){
                g2.setColor(Color.GREEN);
                g2.fill(agl);
                g2.draw(agl);
            }
        }

            /*Color RandomColor(){
                int R = (int)(Math.random()*255);
                int G = (int)(Math.random()*255);
                int B = (int)(Math.random()*255);
                return new Color(R,G,B);
            }*/

        public Rectangle2D find(Point2D point)
        {
            for(Rectangle2D fish : fishes){
                if(fish.contains(point)) return fish;
            }
            return null;
        }

        public void add(Point2D point)
        {
            double x = point.getX();
            double y = point.getY();

            current = new Rectangle2D.Double(x-SIDELENGTH/2, y-SIDELENGTH/2, SIDELENGTH, SIDELENGTH);
            fishes.add(current);
            repaint();
        }

        public void addAglae(){
            for(int i = 0; i < aglae_quantity; i++){
                double x = (Math.random()*DEFAULT_WIDTH-SIDELENGTH) + SIDELENGTH;
                double y = (Math.random()*DEFAULT_HEIGHT-SIDEHEIGHT) + SIDEHEIGHT;
                current = new Rectangle2D.Double(x, y, SIDELENGTH, SIDEHEIGHT);
                aglae.add(current);
            }
            repaint();
        }
        Point2D point;
        public void eatAglae(){
            for (int idx = 0; idx < fishes.size(); idx++) {
                for(int agx = 0; agx < aglae.size(); agx++){
                    if(aglae.get(agx).contains(fishes.get(idx).getX(), fishes.get(idx).getY()))
                    {
                        aglae.remove(agx);
                        fishes.get(idx).setRect(fishes.get(idx).getX(), fishes.get(idx).getY(),
                                fishes.get(idx).getWidth() + 50, fishes.get(idx).getHeight() + 50);
                    }
                }
            }
        }

        private class MouseMotionHandler implements MouseMotionListener
        {



            @Override
            public void mouseDragged(MouseEvent me) {
                if(current != null)
                {
                    int x = me.getX();
                    int y = me.getY();

                    current.setFrame(x-SIDELENGTH/2, y-SIDELENGTH/2, SIDELENGTH, SIDELENGTH);
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent me) {
                double x, y;
                int r;

                //System.out.println( (int)(Math.random()*11) - 5 ); // целое число из [-5;5]
                for ( Rectangle2D fish : fishes)
                {
                    r = (int)(Math.random()*4);
                    x = fish.getX();
                    y = fish.getY();
                    switch (r) {
                        case 0:
                            if((x+5 + fish.getWidth()) > DEFAULT_WIDTH)
                                fish.setRect( 0, y, fish.getWidth(), fish.getHeight());
                            else
                                fish.setRect( x+5, y, fish.getWidth(), fish.getHeight());
                            break;
                        case 1:
                            if((x-5) < 0)
                                fish.setRect( DEFAULT_WIDTH, y, fish.getWidth(), fish.getHeight());
                            else
                                fish.setRect( x-5, y, fish.getWidth(), fish.getHeight());
                            break;
                        case 2:
                            if((y+5 + fish.getHeight()) > DEFAULT_HEIGHT)
                                fish.setRect( x, 15, fish.getWidth(), fish.getHeight());
                            else
                                fish.setRect( x, y+5, fish.getWidth(), fish.getHeight());
                            break;
                        case 3:
                            if((y-5) < 15)
                                fish.setRect( x, DEFAULT_HEIGHT, fish.getWidth(), fish.getHeight());
                            else
                                fish.setRect( x, y-5, fish.getWidth(), fish.getHeight());
                            break;
                    }
                    eatAglae();
                    repaint();
                }
            }
        }

        private class MouseHandler extends MouseAdapter{

            public MouseComponent mcmp;
            String json_aglaes="";
            String json_fishes="";

            public MouseHandler(MouseComponent mcmp) {
                this.mcmp = mcmp;
            }

            @Override
            public void mousePressed(MouseEvent me){
                if(SwingUtilities.isLeftMouseButton(me)) {
                    current = find(me.getPoint());
                    if (current == null)
                        add(me.getPoint());
                }
                else if(SwingUtilities.isRightMouseButton(me))
                {
                    json_aglaes = new Gson().toJson(mcmp.getAglae(), new TypeToken<ArrayList<Rectangle2D>>(){}.getType());
                    json_fishes = new Gson().toJson(mcmp.getFishes(), new TypeToken<ArrayList<Rectangle2D>>(){}.getType());
                    mcmp.setAglae(new ArrayList<Rectangle2D>());
                    mcmp.setFishes(new ArrayList<Rectangle2D>());
                    repaint();
                }
                else if(SwingUtilities.isMiddleMouseButton(me))
                {
                    Object aglaes = new Gson().fromJson(json_aglaes, new TypeToken<ArrayList<Rectangle2D>>(){}.getType());
//                    mcmp.setAglae();
//                    mcmp.setFishes((ArrayList<Rectangle2D>) new Gson().fromJson(json_fishes, new TypeToken<ArrayList<Rectangle2D>>(){}.getType()));
                    repaint();
                }
            }

            @Override
            public void mouseClicked(MouseEvent me){

                    current = find(me.getPoint());

                //if(current != null && me.getClickCount() >= 2)
                //remove(current);
            }

        }}}

