/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utility;

/**
 *
 * @author Abhijit Mukherjee
 */
public class Vector3D {

 public float element[];

  public Vector3D()
  {
    element = new float[4];
    element[0] = element[1] = element[2] = 0;
    element[3] = 1;
  }

  public Vector3D(Vector3D v)
  {
    element = new float[4];
    for (int i = 0; i < 4; i++)
      element[i] = v.element[i];
  }

  public Vector3D(float x, float y, float z)
  {
    element = new float[4];
    element[0] = x;
    element[1] = y;
    element[2] = z;
    element[3] = 1;
  }

  public void set_element(float x, float y, float z)
  {
    element[0] = x;
    element[1] = y;
    element[2] = z;
    element[3] = 1;
  }

  public void make_vector()
  {
    element[3] = 0;
  }

  public void make_point()
  {
    element[3] = 1;
  }

  public void add(Vector3D v)
  {
    for (int i = 0; i < 4; i++)
      element[i] += v.element[i];
  }

  public void sub(Vector3D v)
  {
    for (int i = 0; i < 4; i++)
      element[i] -= v.element[i];
  }

  public void negative()
  {
    for (int i = 0; i < 3; i++)
      element[i] = - element[i];

    
  }

  public void scale (float f)
  {
    for (int i = 0; i < 3; i++)
      element[i] *= f;
  }

  public float magnitude()
  {
    float square = 0;

    for (int i = 0; i < 3; i++)
      square += element[i] * element[i];

    return (float) Math.sqrt(square);
  }

  public float dot_product(Vector3D v)
  {
    float f = 0;

    for (int i = 0; i < 3; i++)
      f += element[i] * v.element[i];

    return f;
  }

  /*
   * V1 x V2 = (V1y * V2z - V1z * V2y, V1z * V2x - V1x * V2z, V1x * V2y - V1y * V2x)
   */
  public Vector3D cross_product(Vector3D v2)
  {
    Vector3D cross = new Vector3D();

    cross.element[0] = element[1] * v2.element[2] -
                       element[2] * v2.element[1];
    cross.element[1] = element[2] * v2.element[0] -
                       element[0] * v2.element[2];
    cross.element[2] = element[0] * v2.element[1] -
                       element[1] * v2.element[0];

    return cross;
  }

  public void orthogonal()
  {
    Matrix m = new Matrix();
    m.rotatez(90);
    this.multiply(m);
  }

  public void multiply(Matrix m)
  {
    Vector3D result = new Vector3D();
//Column major ..
    result.element[0]=element[0]*m.element[0][0] + element[1]*m.element[0][1] + element[2]*m.element[0][2] + m.element[0][3];
    result.element[1]=element[0]*m.element[1][0] + element[1]*m.element[1][1] + element[2]*m.element[1][2] + m.element[1][3];
    result.element[2]=element[0]*m.element[2][0] + element[1]*m.element[2][1] + element[2]*m.element[2][2] + m.element[2][3];
    
    this.element = result.element;
  }

  // perspective transformation of a vector
  public void perspective(int distance)
  {
      element[0] = (element[0] / element[2]) * distance;
      element[1] = (element[1] / element[2]) * distance;
      element[2] = - 1 / element[2];
  }

   public float length(Vector3D a)
   {
      float result = a.element[0]*a.element[0] + a.element[1]*a.element[1] +
              a.element[2]*a.element[2];
      result = (float)Math.sqrt(result);
      return result;
   }// End length(..)

   public void normalizeDeviceCoordiantes(){
       //divide by w-clip value
        element[0] /= element[3];
        element[1] /= element[3];
        element[2] /= element[3];
   }


   public Vector3D normalize(Vector3D a)
   {
      Vector3D result = new Vector3D();
      float len = length(a);
      result.element[0] =  a.element[0] / len;
      result.element[1] =  a.element[1] / len;
      result.element[2] =  a.element[2] / len;
      return result;
   }// End normalize(..)



   public void normalize()
   {
      float len = length(this);
      element[0] /= len;
      element[1] /= len;
      element[2] /= len;

   }// End normalize(..)

  public void print()
  {
    System.out.print("(" + element[0] + "," + element[1] + "," +
                     element[2] + "," + element[3] + ")");
  }

}
