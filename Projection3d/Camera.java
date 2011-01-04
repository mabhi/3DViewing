/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Projection3d;

import Utility.Matrix;
import Utility.Vector3D;

/**
 *
 * @author Abhijit Mukherjee
 */
public class Camera {

  public Matrix matCam;
  public Vector3D cameraPosition;
  public Vector3D cameraTarget;
  public Vector3D cameraUpVector;
  public Vector3D cameraRightVector;

  public float radius = 1;
  public float moveDist = 1f;

  private float hRadians;
  private float vRadians;

    public Camera(){
        matCam = new Matrix();
    }

    public Camera(Vector3D vLookAt, Vector3D vPosition){
        this();
        cameraPosition = vPosition;
        setTarget(vLookAt);
    }

    public Camera(Vector3D vPosition){
        this();
        cameraPosition = vPosition;

    }

    public void setCamera( Vector3D vRight,
                           Vector3D vUp,
                           Vector3D vForward
                           )
    {
        matCam.identity();
        
        matCam.element[0][0]=vRight.element[0];
        matCam.element[0][1]=vRight.element[1];
        matCam.element[0][2]=vRight.element[2];
        matCam.element[0][3]=0;

        matCam.element[1][0]=vUp.element[0];
        matCam.element[1][1]=vUp.element[1];
        matCam.element[1][2]=vUp.element[2];
        matCam.element[1][3]=0;

        matCam.element[2][0]=vForward.element[0];
        matCam.element[2][1]=vForward.element[1];
        matCam.element[2][2]=vForward.element[2];
        matCam.element[2][3]=0;

        Matrix transMatrix = new Matrix();
        Vector3D vPos = new Vector3D(cameraPosition);
        vPos.negative();
        transMatrix.translate(vPos);

        matCam.multiply(transMatrix);

    }// End of SetCamera(..)

    /*
     * Returns the camera matrix to be multiplied with the
     * object vertices ...
     */
    public Matrix getMatCam() {
        return matCam;
    }


// End of class Camera

//  Camera Matrix
//  | right.x    up.x     forward.x     0 |
//  | right.y    up.y     forward.y     0 |
//  | right.z    up.z     forward.z     0 |  ; (rotation4x4)
//  | 0          0            0         1 |


/*
        | Ux  Uy  Uz  0|
        | Vx  Vy  Vz  0|
        | Nx  Ny  Nz  0|
        |  0   0   0  1|
where U is the "right" vector, V the "up" vector and N the direction you are looking.

*/

    public void setTarget(Vector3D target)
    {
	Vector3D projectedTarget;

	target.sub(cameraPosition);
	projectedTarget = target;
//When target or normal is parallel to the up vector ...
	if(Math.abs(target.element[0]) < 0.00001f && Math.abs(target.element[2]) < 0.00001f) { // YZ plane

		projectedTarget.element[0] = 0.0f;
		projectedTarget.normalize();

		cameraRightVector = new Vector3D(1.0f, 0.0f, 0.0f);
		cameraUpVector = projectedTarget.cross_product(cameraRightVector);

		cameraTarget = target;
		cameraRightVector = cameraUpVector.cross_product(cameraTarget);

	}

	else { // XZ plane

		projectedTarget.element[1] = 0.0f;
		projectedTarget.normalize();

		cameraUpVector = new Vector3D(0.0f, 1.0f, 0.0f);
		cameraRightVector = cameraUpVector.cross_product(projectedTarget);
               

		cameraTarget = target;
		cameraUpVector = cameraTarget.cross_product(cameraRightVector);
	}

	cameraTarget.normalize();
	cameraRightVector.normalize();
	cameraUpVector.normalize();

        setCamera(cameraRightVector, cameraUpVector, cameraTarget);
    }
}