/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Projection3d;

import Utility.Edge3D;
import Utility.Vector3D;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 *
 * @author Abhijit Mukherjee
 */
public class Canvas3D extends Canvas{

  // Double Buffering offscreen Image and Graphics
    private Image offImage;
    private Graphics offGraphics;
    private int canvasWidth;
    private int canvasHeight;
    private boolean wireFrame;
    private Object3D object;

    public Canvas3D(int _w,int _h){
        canvasHeight = _h;
        canvasWidth = _w;

    }

    public Canvas3D(int _w,int _h, Object3D _object){
        this(_w,_h);
        object = new Object3D(_object);
        wireFrame = true;

        
    }


    public void setObject(Object3D object) {
        this.object = object;
    }

    public void setWireFrame(boolean wireFrame) {
        this.wireFrame = wireFrame;
    }


    public void drawObject(Graphics g){

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, canvasWidth, canvasHeight);
        g.setColor( Color.white );
        
        Point []points = object.getProjectedPoints();
        Edge3D []edges = object.getEdges();
        if(points != null){
            for (int j = 0; j < edges.length; ++j ) {
            //System.out.println(edges[j].a + "\t"+ edges[j].b);
                g.drawLine(
                    points[ edges[j].a ].x, points[ edges[j].a ].y,
                    points[ edges[j].b ].x, points[ edges[j].b ].y
                    );
                
                }

            }
    }
    // modify the update function, do not clear view window at this time
    @Override
    public void update(Graphics g)
    {
        paint(g);
    }

    // paint the object and world axis on view window
    @Override
    public void paint(Graphics g)
    {
        if(offGraphics == null){
            offImage = createImage(canvasWidth,canvasHeight);
            offGraphics = offImage.getGraphics();
        }
        drawObject(offGraphics);
        g.drawImage( offImage, 0, 0, this );

        //update(g);
    }

}
