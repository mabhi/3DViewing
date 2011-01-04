/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Projection3d;

import Utility.Edge3D;
import Utility.Matrix;
import Utility.Vector3D;
import java.awt.Point;

/**
 *
 * @author Abhijit Mukherjee
 */




public class Object3D {

    public enum ObjectType{
        CUBE,
        SPHERE,
        TEAPOT,
        OTHER
    };

    private int numOfEdges;
    private int numOfVertices;
    private Vector3D[] vertices;
    private Vector3D objectCenter;
    private Edge3D[] edges;
    public ObjectType objectType;
    private Point[] projectedPoints;
    //by default it takes the shape of a cube...

    public Object3D(){
        this.objectType = ObjectType.CUBE;
        setUpObjectVertices();
    }

    // copy constuctor
    public Object3D(Object3D _object){
        this.objectType = _object.objectType;
        this.numOfVertices = _object.numOfVertices;
        this.numOfEdges = _object.numOfEdges;

        vertices = new Vector3D[numOfVertices];
        for(int i=0 ; i<numOfVertices; i++)
            vertices[i] = new Vector3D(_object.vertices[i]);

        edges = new Edge3D[numOfEdges];
        for(int i=0 ; i<numOfEdges; i++)
            edges[i] = new Edge3D(_object.edges[i]);

        this.objectCenter = new Vector3D(_object.getObjectCenter());
    }

    public Vector3D getObjectCenter() {
        return objectCenter;
    }


    public void setUpObjectVertices(){

        switch(objectType){
            case CUBE:
                setCube();
                break;

            case SPHERE:
                setSphere();
                break;

            case TEAPOT:
                setTeapot();
                break;

            default:
                break;

        }
    }

    private void setCube(){
        numOfVertices = 8;
        int k = 30;
        vertices = new Vector3D[numOfVertices];
        vertices[0] = new Vector3D( -k, -k, -k );
        vertices[1] = new Vector3D( -k, -k,  k );
        vertices[2] = new Vector3D( -k,  k, -k );
        vertices[3] = new Vector3D( -k,  k,  k );
        vertices[4] = new Vector3D(  k, -k, -k );
        vertices[5] = new Vector3D(  k, -k,  k );
        vertices[6] = new Vector3D(  k,  k, -k );
        vertices[7] = new Vector3D(  k,  k,  k );

        objectCenter = new Vector3D(0, 0, 0);
        numOfEdges = 12;
        edges = new Edge3D[numOfEdges];
        edges[ 0] = new Edge3D( 0, 1 );
        edges[ 1] = new Edge3D( 0, 2 );
        edges[ 2] = new Edge3D( 0, 4 );
        edges[ 3] = new Edge3D( 1, 3 );
        edges[ 4] = new Edge3D( 1, 5 );
        edges[ 5] = new Edge3D( 2, 3 );
        edges[ 6] = new Edge3D( 2, 6 );
        edges[ 7] = new Edge3D( 3, 7 );
        edges[ 8] = new Edge3D( 4, 5 );
        edges[ 9] = new Edge3D( 4, 6 );
        edges[10] = new Edge3D( 5, 7 );
        edges[11] = new Edge3D( 6, 7 );
    }


    private void setTeapot(){

    }


    private void setSphere(){

    }

    public Edge3D[] getEdges(){
            return edges;
    }

    public Vector3D[] getVertices(){
            return vertices;
    }

    public int getNumOfEdges() {
        return numOfEdges;
    }

    public int getNumOfVertices() {
        return numOfVertices;
    }

    
    public void transform3D(Matrix m){
        for (int i = 0; i < vertices.length; i++)
            vertices[i].multiply(m);

        objectCenter.multiply(m);
    }

    public Object3D Move_Transform(Matrix m){
        transform3D(m);
        Object3D object = new Object3D(this);

        return object;
    }

    public Point[] getProjectedPoints() {
        return projectedPoints;
    }

    
    public void perspective3D(int scr_x,int scr_y){

        Point[] points;
        points = new Point[ vertices.length ];

        int j;
        float hw = scr_x / 2;
        float hh = scr_y / 2;
        int viewDistance = 200;
        float x1,y1,z1;
        float near = 100;  // distance from eye to near plane
        float nearToObj = 4.5f;  // distance from near plane to center of object
        for ( j = 0; j < vertices.length; ++j ) {

            x1 = vertices[j].element[0];
            y1 = vertices[j].element[1];
            z1 = vertices[j].element[2];

//            x1 = x1/(z1+near+nearToObj);
//            y1 = y1/(z1+near+nearToObj);
            x1 = x1 * viewDistance/z1 ;
            y1 = y1 * viewDistance/z1 ;

            // the 0.5 is to round off when converting to int
            points[j] = new Point(
                (int)(hw + x1 + 0.5),
                (int)(hh - y1 + 0.5)
            );
        }
        projectedPoints = points;
    }
}
