/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utility;

/**
 *
 * @author Abhijit Mukherjee
 */
public class Matrix {
    public float element[][];

    public Matrix(boolean zeroMatrix ){

        this();
        if(zeroMatrix){
            element[0][0]=element[1][1]=element[2][2]=element[3][3] = 0.0f;
        }
    }

    public Matrix()
    {
        element = new float[4][4];

        this.identity();
    }



    public Matrix(Matrix m)
    {
        element = new float[4][4];

        for(int i = 0; i < 4; ++i)
          for (int j = 0; j < 4; ++j)
            element[i][j] = m.element[i][j];
    }

    //specifically for uvn camera matrix...
    public Matrix(Vector3D u, Vector3D v, Vector3D n){
        this();
        element[0][0] = u.element[0];
        element[0][1] = u.element[1];
        element[0][2] = u.element[2];

        element[1][0] = u.element[0];
        element[1][1] = u.element[1];
        element[1][2] = u.element[2];

        element[2][0] = u.element[0];
        element[2][1] = u.element[1];
        element[2][2] = u.element[2];

    }

    public void identity ()
    {
        for(int i = 0; i < 4; ++i)
          for(int j = 0; j < 4; ++j)
            if (i == j)
              element[i][j] = 1;
            else
              element[i][j] = 0;
    }

    public float[] getRow(int i){
        return element[i];
    }

    public Matrix inverse()
    {
        Matrix m = new Matrix();

        // from row 1 to row 4

        for (int j = 0; j < 4; j++)
        {
              // normalize the diagonal element

              float e = element[j][j];
              for (int i = 0; i < 4; i++)
              {
                element[i][j] = element[i][j] / e;
                m.element[i][j] = m.element[i][j] /e;
              }

          // make other element to be 0

            for (int k = 0; k < 4; k++)
            {
                if (k != j)
                {
                    e = -element[j][k];
                    //same column
                    for (int i = 0; i < 4; i++)
                    {
                        element[i][k] = element[i][k] + e * element[i][j];
                        m.element[i][k] = m.element[i][k] + e * m.element[i][j];
                    }
                }
            }
        }
        return m;
    }

//m matrix row fixed adn col varying * self matrix col fixed and row varying
    public void multiply(Matrix m)
    {
        float	e1,e2,e3,e4;
	int	i,j;
        float[][] e = new float[4][4];
	for(i = 0;i < 4;i++)
	{
            e1 = m.element[i][0];
            e2 = m.element[i][1];
            e3 = m.element[i][2];
            e4 = m.element[i][3];

            e[i][0] = e1 * element[0][0] + e2 * element[1][0] + e3 * element[2][0] + e4 * element[3][0];
            e[i][1] = e1 * element[0][1] + e2 * element[1][1] + e3 * element[2][1] + e4 * element[3][1];
            e[i][2] = e1 * element[0][2] + e2 * element[1][2] + e3 * element[2][2] + e4 * element[3][2];
            e[i][3] = e1 * element[0][3] + e2 * element[1][3] + e3 * element[2][3] + e4 * element[3][3];
	}

        for(i = 0; i < 4; ++i)
          for(j = 0; j < 4; ++j)
              element[i][j] = e[i][j];
    }
    
    public void scale(float f) 
    {
        Matrix m = new Matrix();

        for(int i = 0; i < 4; ++i)
            element[i][i] = f;

        this.multiply(m);
    }
      
    public void scale(float sx,float sy,float sz) {
        Matrix m = new Matrix();
        m.element[0][0] = sx;
        m.element[1][1] = sy;
        m.element[2][2] = sz;
        this.multiply(m);
    }



      // rotate along x axis
    public void rotatex(float degree)
    {
        float d;
        float cos, sin;

        d = (float) (degree * Math.PI/180.);
        sin = (float) Math.sin(d);
        cos = (float) Math.cos(d);

        rotatex(sin, cos);
    }

      // rotate along x axis
    public void rotatex(float sin, float cos)
    {
        Matrix m = new Matrix();

        m.element[1][1] =  cos;
        m.element[2][2] =  cos;
        m.element[1][2] = -sin;
        m.element[2][1] =  sin;

        this.multiply(m);
    }

      // rotate along y axis
    public void rotatey(float degree)
    {
        float d;
        float cos, sin;

        d = (float) (degree * Math.PI/180.);
        sin = (float) Math.sin(d);
        cos = (float) Math.cos(d);

        rotatey(sin, cos);
    }

      // rotate along y axis
    public void rotatey(float sin, float cos)
    {
        Matrix m = new Matrix();

        m.element[0][0] =  cos;
        m.element[2][2] =  cos;
        m.element[0][2] =  sin;
        m.element[2][0] = -sin;

        this.multiply(m);
    }

      // rotate along z axis
    public void rotatez(float degree)
    {
        float d;
        float cos, sin;

        d = (float) (degree * Math.PI/180.);
        sin = (float) Math.sin(d);
        cos = (float) Math.cos(d);

        rotatez(sin, cos);
    }

      // rotate along z axis
    public void rotatez(float sin, float cos)
    {
        Matrix m = new Matrix();

        m.element[0][0] =  cos;
        m.element[1][1] =  cos;
        m.element[0][1] = -sin;
        m.element[1][0] =  sin;

        this.multiply(m);
    }    
    
// rotate along a given axis and point 
  public void rotate(Vector3D point, Vector3D axis, int angle)
  {
    float sinx, cosx;   // angle rotate along x axis
    float siny, cosy;	// angle rotate along y axis
    float yz_m;		// the magnitude of the yz component of axis
    float m;		// total magnitude of the axis vector

    Matrix matrix = new Matrix();

    yz_m = (float)Math.sqrt(axis.element[1] * axis.element[1] + 
                            axis.element[2] * axis.element[2]);
    if (yz_m == 0)
    {
      sinx = 0;
      cosx = 1;
    }
    else
    {
      sinx = axis.element[1]/yz_m;
      cosx = axis.element[2]/yz_m;
    }

    m = axis.magnitude();
    if (m == 0)
    {
      siny = 0;
      cosy = 1;
    }
    else
    {
      siny = -axis.element[0]/m;
      cosy =  yz_m/m;
    }

    // first map the origin to the point, then rotate z axis around x axis
    // (up/down), then rotate z axix around y axis (left/right), then
    // this map the z axix to be the axix vector given, then rotate around
    // this z axix to the angle given.

    Vector3D origin = new Vector3D(point);
    origin.negative();
    matrix.translate(origin);
    matrix.rotatex(sinx, cosx);
    matrix.rotatey(siny, cosy);
    matrix.rotatez(angle);

    // reverse the above process

    matrix.rotatey(-siny, cosy);
    matrix.rotatex(-sinx, cosx);
    matrix.translate(point);

    this.multiply(matrix);
  }

    public void translate(Vector3D vec)
    {
        translate(vec.element[0], vec.element[1], vec.element[2]);
    }

    public void translate (float x, float y, float z)
    {
        Matrix m = new Matrix();
        m.element[0][3] = x;
        m.element[1][3] = y;
        m.element[2][3] = z;

        this.multiply(m);
    }
  
    public void print()
    {
        for (int j = 0; j < 4; j++)
          System.out.println(element[0][j] + " " + element[1][j] +
                             " " + element[2][j] + " " + element[3][j]);
    }
}
