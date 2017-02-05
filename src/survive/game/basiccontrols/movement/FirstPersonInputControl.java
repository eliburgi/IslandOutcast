/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survive.game.basiccontrols.movement;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author Elias
 */
public class FirstPersonInputControl extends AbstractControl implements ActionListener, AnalogListener {

    private final String MOVE_LEFT          = "FPS_Move_Left";
    private final String MOVE_RIGHT         = "FPS_Move_Right";
    private final String MOVE_BACKWARD      = "FPS_Move_Backward";
    private final String MOVE_FORWARD       = "FPS_Move_Forward";
    private final String TOGGLE_RUNNING     = "FPS_Toggle_Running";
    private final String JUMP               = "FPS_Jump";
    private final String DUCK               = "FPS_Duck";
    private final String ROTATE_LEFT        = "FPS_Rotate_Left";
    private final String ROTATE_RIGHT       = "FPS_Rotate_Right";
    private final String LOOK_UP            = "FPS_Look_Up";
    private final String LOOK_DOWN          = "FPS_Look_Down";
    
    private InputManager inputManager;
    private FirstPersonControl firstPersonControl;
    
    //Default Movement Keys
    private int left            = KeyInput.KEY_A;
    private int right           = KeyInput.KEY_D;
    private int backward        = KeyInput.KEY_S;
    private int forward         = KeyInput.KEY_W;
    private int toggleRunning   = KeyInput.KEY_R;
    private int jump            = KeyInput.KEY_SPACE;
    private int duck            = KeyInput.KEY_LSHIFT;
    private int rotateLeft      = MouseInput.AXIS_X;
    private int rotateRight     = MouseInput.AXIS_X;
    private int lookUp          = MouseInput.AXIS_Y;
    private int lookDown        = MouseInput.AXIS_Y;
    
    /**
     * Creates a new FirstPersonControl with default key mappings (W, A, S, D, etc.).
     * @param inputManager 
     */
    public FirstPersonInputControl(InputManager inputManager) {
        this.inputManager = inputManager;
        
        initKeyMappings();
        listenForKeys();
    }
    
    /**
     * Creates a new FirstPersonControl with the specified Keys for controlling movement.
     * @param inputManager
     * @param left
     * @param right
     * @param backward
     * @param forward
     * @param toggleRunning
     * @param jump
     * @param duck
     * @param rotateLeft
     * @param rotateRight
     * @param lookUp
     * @param lookDown 
     */
    public FirstPersonInputControl(InputManager inputManager, int left, int right, int backward, int forward, int toggleRunning, int jump, int duck, int rotateLeft, int rotateRight, int lookUp, int lookDown) {
        this.inputManager = inputManager;
        
        this.left = left;
        this.right = right;
        this.backward = backward;
        this.forward = forward;
        this.toggleRunning = toggleRunning;
        this.jump = jump;
        this.duck = duck;
        this.rotateLeft = rotateLeft;
        this.rotateRight = rotateRight;
        this.lookUp = lookUp;
        this.lookDown = lookDown;
        
        initKeyMappings();
        listenForKeys();
    }
    
    private void initKeyMappings() {
        inputManager.addMapping(MOVE_LEFT, new KeyTrigger(left));
        inputManager.addMapping(MOVE_RIGHT, new KeyTrigger(right));
        inputManager.addMapping(MOVE_BACKWARD, new KeyTrigger(backward));
        inputManager.addMapping(MOVE_FORWARD, new KeyTrigger(forward));
        inputManager.addMapping(TOGGLE_RUNNING, new KeyTrigger(toggleRunning));
        inputManager.addMapping(JUMP, new KeyTrigger(jump));
        inputManager.addMapping(DUCK, new KeyTrigger(duck));
        inputManager.addMapping(ROTATE_LEFT, new MouseAxisTrigger(rotateLeft, false));
        inputManager.addMapping(ROTATE_RIGHT, new MouseAxisTrigger(rotateRight, true));
        inputManager.addMapping(LOOK_DOWN, new MouseAxisTrigger(lookDown, false));
        inputManager.addMapping(LOOK_UP, new MouseAxisTrigger(lookUp, true));
    }
    
    private void listenForKeys() {
        inputManager.addListener(this, MOVE_LEFT,
                                       MOVE_RIGHT,
                                       MOVE_FORWARD,
                                       MOVE_FORWARD,
                                       TOGGLE_RUNNING,
                                       JUMP,
                                       DUCK,
                                       ROTATE_LEFT,
                                       ROTATE_RIGHT,
                                       LOOK_DOWN,
                                       LOOK_UP);   
    }
    
    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        
        if(spatial == null) return;
        
        this.firstPersonControl = spatial.getControl(FirstPersonControl.class);
        if(firstPersonControl == null) {
            throw new IllegalStateException("Cannot add FirstPersonInputControl to a Spatial without a FirstPersonControl!");
        }
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        
        if(enabled) {
            //Listen for keys
            listenForKeys();
        } else {
            //Stop listening for keys
            inputManager.removeListener(this);
        }
    }
    
    @Override
    protected void controlUpdate(float tpf) {

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) { }

    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals(MOVE_LEFT)){
            firstPersonControl.setMovingLeft(isPressed);
        }
        if(name.equals(MOVE_RIGHT)){ 
            firstPersonControl.setMovingRight(isPressed);
        }
        if(name.equals(MOVE_FORWARD)){
            firstPersonControl.setMovingForward(isPressed);
        }
        if(name.equals(MOVE_BACKWARD)){
            firstPersonControl.setMovingBackward(isPressed);
        }
        if(name.equals(JUMP) && isPressed){           
            firstPersonControl.jump();
        }
        if(name.equals(DUCK)){
            firstPersonControl.setDucking(isPressed);
        }
        if(name.equals(TOGGLE_RUNNING) && isPressed){
            firstPersonControl.toggleRunning();
        }
    }

    public void onAnalog(String name, float value, float tpf) {
        if(name.equals(ROTATE_LEFT)){
            firstPersonControl.rotateBody(-value, tpf);
        }
        if(name.equals(ROTATE_RIGHT)){ 
            firstPersonControl.rotateBody(value, tpf);
        }
        if(name.equals(LOOK_UP)){
            firstPersonControl.lookUpDownHead(value, tpf);
        }
        if(name.equals(LOOK_DOWN)){ 
            firstPersonControl.lookUpDownHead(-value, tpf);
        }
    }   
}
