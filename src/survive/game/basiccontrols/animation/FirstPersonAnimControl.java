/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survive.game.basiccontrols.animation;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import survive.game.basiccontrols.movement.FirstPersonControl;

/**
 * Control responsible for playing the right FirstPersonCharacter Animation based on the FirstPersonControls State.
 * TODO: Either use an AnimationControl for the player or attach the players arms and head to the camera in the FirstPersonControl.
 * @author Elias
 */
public class FirstPersonAnimControl extends AbstractControl {

    private FirstPersonControl firstPersonControl;
    
    //private Node arm;
    private Node head;
    
    //private Vector3f initArmTrans;
    private Vector3f initHeadTrans;
       
    //private final Vector3f armAmplitude = new Vector3f(0f, 0.2f, 0f);
    private final Vector3f headAmplitude = new Vector3f(0f, 0.1f, 0f);
    
    //private final Vector3f armFrequency = new Vector3f(0f, 10f, 0f);
    private final Vector3f headFrequency = new Vector3f(0f, 10f, 0f);
    
    
    private float time = 0f;
    
    public FirstPersonAnimControl() {
                
    }
    
    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        
        if(spatial == null) return;
        
        this.firstPersonControl = spatial.getControl(FirstPersonControl.class);
        if(firstPersonControl == null) {
            throw new IllegalStateException("Cannot add FirstPersonAnimControl to a Spatial without a FirstPersonControl!");
        }
        this.head = firstPersonControl.getHead();
        //initArmTrans  = this.arm.getLocalTranslation().clone();
        initHeadTrans = this.head.getLocalTranslation().clone();
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        //Animate the characters head going up-down while walking or running
        
        //Animate the characters arms swinging while walking or running
        
        time += tpf;
    
        //float yArmAmp = armAmplitude.y;
        float yHeadAmp = headAmplitude.y;
        
        if(firstPersonControl.getWalkDirection().lengthSquared() == 0 || firstPersonControl.isDucked() || !firstPersonControl.isOnGround()){
            //yArmAmp = 0f;
            yHeadAmp = 0f;
            
            //No further calculations needed when player isn´t moving or ducked or jumping
            //return;
        }
        
        float ampFactor = firstPersonControl.getForwardDir().dot(firstPersonControl.getWalkDirection().normalize());
        
        float runFactor = firstPersonControl.isRunning() ? 2f : 1f;
        
        //float yArm = yArmAmp * ampFactor * FastMath.sin(time * firstPersonControl.y);
        float yHead = yHeadAmp * FastMath.sin(time * headFrequency.y * runFactor);
        
        if(yHead != 0f){
            //arm.setLocalTranslation(initArmTrans.x, initArmTrans.y + yArm, initArmTrans.z);
            head.setLocalTranslation(initHeadTrans.x, initHeadTrans.y + yHead, initHeadTrans.z);
        } else {
            //head.setLocalTranslation(initHeadTrans);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
    
}
