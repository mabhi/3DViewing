/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Projection3d;

import Utility.Matrix;
import Utility.Vector3D;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Abhijit Mukherjee
 */
public class ViewMain extends JFrame implements Runnable,ActionListener{

    public static int width = 800;
    public static int height = 600;
    public static final String ENABLE_THREAD = "enable-animation";
    public static final String DISABLE_THREAD = "disable-animation";
    private   Thread runner ;
    private Canvas3D drawCanvas;
    private Camera ourCamera;
    private Object3D _origObject;
    private Object3D _newObject ;
    private static int bottomPanelHeight = 50;
    private float angleX,angleY,angleZ;

    public ViewMain(String _title) {
        super(_title)    ;
        runner = null;
        _newObject = _origObject = null;
        //center of the object ... in our case
//        Vector3D vLookAt = new Vector3D(0, 0, 0);
        Vector3D vPosition = new Vector3D(0, 0, -10);

        ourCamera = new Camera(vPosition);
        drawCanvas = new Canvas3D(width,height - bottomPanelHeight);
    }
    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        // TODO code application logic here
        Toolkit toolkit =  Toolkit.getDefaultToolkit ();
        Dimension dim = toolkit.getScreenSize();

        ViewMain mainFrame = new ViewMain("Projection Page") ;
        mainFrame.setSize(width, height);

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout(5,5));

        Object3D _cube = new Object3D();
        mainFrame.setOrigObject(_cube);
        mainFrame.objectInitTransform();

        
        container.add(mainFrame.drawCanvas ,BorderLayout.CENTER);
        

        JPanel bottomPane = ViewMain.bottomPanel(mainFrame, dim);

        container.add(bottomPane,BorderLayout.SOUTH);        
        mainFrame.add(container);



        mainFrame.setLocation((dim.width - width)/2, (dim.height - height)/2);
        mainFrame.setVisible(true);
        mainFrame.pack();

        mainFrame.addWindowListener(new WindowAdapter() {
                @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }

                });
    }

    public static JPanel bottomPanel(ViewMain _parent,Dimension _dim){

        JPanel bottomPane = new JPanel();
        bottomPane.setPreferredSize(new Dimension((_dim.width - width)/2,bottomPanelHeight));
        bottomPane.setBorder(LineBorder.createBlackLineBorder());


        JButton startThread = new JButton("Animate");
        startThread.addActionListener(_parent);
        startThread.setActionCommand(ENABLE_THREAD);
        bottomPane.setLayout(new GridBagLayout());
        //Layout controls .....
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;

        bottomPane.add(startThread);

        // end layooing out components ....

        return bottomPane;
    }

    public Object3D getOrigObject() {
        return _origObject;
    }

    public void setOrigObject(Object3D _origObject) {
        this._origObject = _origObject;
    }

    public Camera getOurCamera() {
        return ourCamera;
    }

    public void setOurCamera(Camera ourCamera) {
        this.ourCamera = ourCamera;
    }


    public void objectInitTransform(){
        Matrix m = new Matrix();
        //initially rotate the object along Y
        angleX = 0.0f;
        angleZ = 0.0f;
        angleY = 10.0f;

        m.rotatex(angleX);
        m.rotatey(angleY);
        m.rotatez(angleZ);

        _newObject = new Object3D(_origObject);
        _newObject.transform3D(m);

        m.translate(0, 0, -100);
        _newObject.transform3D(m);
        //moved the center as well,so lookat vector is transformed ..
        ourCamera.setTarget(_newObject.getObjectCenter());
        
        _newObject.transform3D(ourCamera.getMatCam());
        _newObject.perspective3D(width, height - bottomPanelHeight);
        drawCanvas.setObject(_newObject);
    }

    
    public void run() {
        Thread thisThread = Thread.currentThread();
        while(thisThread == runner){
            try {
                Thread.sleep(50);
                }
              catch (InterruptedException e) { }

        }
    }

    public void startAnimate(){
        if (runner == null)
        {
          runner = new Thread(this);
          runner.start();
        }
    }

    public void stopAnimate(){
        if(runner!=null)
            runner = null;
    }
    /*
     * Various component actions are handled ..
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(DISABLE_THREAD)){
            JButton btn = (JButton)e.getSource();
            btn.setActionCommand(ENABLE_THREAD);
            stopAnimate();
            btn.setText("Animate");
        }

        else if(e.getActionCommand().equals(ENABLE_THREAD)){
            JButton btn = (JButton)e.getSource();
            btn.setActionCommand(DISABLE_THREAD);
            startAnimate();
            btn.setText("Stop");
        }
    }
}
