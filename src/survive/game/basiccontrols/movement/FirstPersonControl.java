package survive.game.basiccontrols.movement;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;

/**
 * Class handling First-Person Movement
 * @author Elias
 */
public class FirstPersonControl extends BetterCharacterControl {
    
    // <editor-fold desc="Movement">
    
    //Booleans determining if player is moving into a given direction
    private boolean movingLeft;
    private boolean movingRight;
    private boolean movingForward;
    private boolean movingBackward;
    private boolean running;
    
    //The players forward direction
    private Vector3f forwardDir = new Vector3f();
    
    //Factor for fine tuning left-right mouse movement
    //The higher the factor, the faster the mouse movement
    //The lower, the slower is the mouse movement
    //Used for turning the player around the y-Axis
    private float leftRightMouseSensitivity;
    
    //Same as left-right but with up-down movement of the mouse
    private float upDownMouseSensitivity;
    
    //The players walk speed (velocity) in WU/s (World Units)
    private float walkSpeed;
    
    //The players run speed (velocity) in WU/S 
    private float runSpeed;
    
    //Factor for slowing the players movements when he is in the air (jumping)
    //The higher the more the player can control his direction when being in-air
    private float inAirDampingFactor;
    
    //Factor for slowing the players movements when he is ducking
    //The higher the faster the player can get when being ducked
    private float duckedDampingFactor;
    
     //</editor-fold>
    
    // <editor-fold desc="First Person View">

    //The players head node
    //Must be attached to the player node
    //Camera gets attached to this node
    //This node is also used for up-down looking (rotation about x-Axis)
    private Node head;
    
    //The heads current rotation around the x-Axis 
    private float pitch = 0f;
    
    //Final for rotating about yaw - save memory
    private final Quaternion yawRotation = new Quaternion();
    
    //Final for rotating about pitch - save memory
    private final Quaternion pitchRotation = new Quaternion();
    
    //</editor-fold>
    
    // <editor-fold desc="Constructors">
    
    /**
     * 
     * @param radius Radius for the capsule shape of the players collision shape - See BetterCharacterControl!
     * @param height Height for the capsule shape of the players collision shape - See BetterCharacterControl!
     * @param mass Players Mass - See BetterCharacterControl!
     * @param leftRightMouseSensitivity Factor for fine tuning left-right mouse movement
     * @param upDownMouseSensitivity Factor for fine tuning up-down mouse movement
     * @param walkSpeed The players walk speed (velocity) in WU/s (World Units)
     * @param runSpeed The players run speed (velocity) in WU/S 
     * @param head The players head node - Must be attached to the player node
     */
    public FirstPersonControl(float radius, float height, float mass, 
                              float leftRightMouseSensitivity, float upDownMouseSensitivity, 
                              float walkSpeed, float runSpeed, 
                              float inAirDampingFactor, float duckedDampingFactor,
                              Node head){
        
        super(radius, height, mass);
        rigidBody.setCcdMotionThreshold(100f);
        
        this.leftRightMouseSensitivity = leftRightMouseSensitivity;
        this.upDownMouseSensitivity = upDownMouseSensitivity;
        this.walkSpeed = walkSpeed;
        this.runSpeed = runSpeed;
        
        this.inAirDampingFactor = inAirDampingFactor;
        this.duckedDampingFactor = duckedDampingFactor;
               
        this.head = head;
        
        //Set the players head y-Translation to the players height
        head.setLocalTranslation(0f, height, 0f);
    }
    
    // </editor-fold>
    
    
    /**
     * Update player movement 
     * @param tpf time delta
     */
    @Override
    public void update(float tpf){
        super.update(tpf);
        
        //Stop all movement from the player (if there is no input, player should do nothing)
        walkDirection.set(0f, 0f, 0f);
        
        //The players forward direction vector - facing in the direction the player is facing
        forwardDir = spatial.getWorldRotation().mult(Vector3f.UNIT_Z);
        
        //The players left direction vector - facing to the players left side
        Vector3f leftDir = spatial.getWorldRotation().mult(Vector3f.UNIT_X);
        
        //Check if the player should be moved according to input
        if(movingForward){
            walkDirection.addLocal(forwardDir);
        } if(movingBackward){
            walkDirection.subtractLocal(forwardDir);
            running = false;
        } if(movingRight){
            walkDirection.subtractLocal(leftDir);
        } if(movingLeft){
            walkDirection.addLocal(leftDir);
        }
        
        //Check if the players presses backwards when he is in-air (jumping)
        //If so, stop backward movement (you cant go backwards when jumping!)
        if(!isOnGround() && movingBackward){
            walkDirection.addLocal(forwardDir);
        }
        
        //Calculate inAirDampingFactor
        //1 if the player is onGround - else inAirDampingFactor  
        float dampingFactor = (isOnGround()) ? 1f : inAirDampingFactor;
        
        //Calculate duckedDampingFactor
        //1 if the player isn´t ducked - else duckedDampingFactor
        float duckedMovingFactor = isDucked() ? duckedDampingFactor : 1f;
        
        //Check if the player is running
        //If so multiply by the running speed
        float movingFactor = (running && !isDucked()) ? runSpeed : walkSpeed;
        
        //Normalize walkDirection and scale it by the given factors
        //Scaling determines the speed
        walkDirection.normalizeLocal().multLocal(movingFactor * dampingFactor * duckedMovingFactor);
    }
    
    public void setMovingLeft(boolean movingLeft){
        this.movingLeft = movingLeft;
    }
    
    public void setMovingRight(boolean movingRight){
        this.movingRight = movingRight;
    }
    
    public void setMovingForward(boolean movingForward){
        this.movingForward = movingForward;
    }
    
    public void setMovingBackward(boolean movingBackward){
        this.movingBackward = movingBackward;
    }
    
    public void setRunning(boolean running){
        //Running should not be possible if the player is ducked
        if(!isDucked()){
           this.running = running;
        } else if(!running){
            this.running = running;
        }
    }
    
    public boolean isWalking() {
        return movingForward || movingBackward || movingLeft || movingRight;
    }
    
    public boolean isRunning() {
        return isWalking() && running;
    }
    
    public void toggleRunning(){
        setRunning(!running);
    }
    
    public void setDucking(boolean ducking){
        setDucked(ducking);
            
        //Check if the player has ducked
        if(isDucked()){
            //set the heads height to a lower value (to simulate of being ducked)
            Vector3f headPos = head.getLocalTranslation();
            head.setLocalTranslation(headPos.x, height * getDuckedFactor(), headPos.z);

            //Running should not be possible if the player is ducked
            running = false;
        } else {
            //Player unducked - so set the heads height to normal height
            head.setLocalTranslation(0f, height, 0f);
        }
    }
    
    public void rotateBody(float value, float tpf){
        rotate(value * tpf * leftRightMouseSensitivity);
    }
    
    public void lookUpDownHead(float value, float tpf){
        lookUpDown(value * tpf * upDownMouseSensitivity);
    }
    
    // <editor-fold desc="Camera">
    
    /**
     * Attaches a camera on the players head and therefore starts first person view
     * Note: flyByCam has to be deactivated in order for this to work
     * @param cam The cam object which should be attached to the player, camera follows players movement (on players eye location)
     */
    public void setCameraNode(CameraNode camNode){    
        //Make the camera follow the head
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
    }
    
    // </editor-fold>
    
    // <editor-fold desc="Rotation">
    
    /**
     * Rotates the player spatial with the given factor about his y-Axis (yaw)
     * @param angle the higher the more the player will be rotated, negative value rotates on opposite direction
     */
    private void rotate(float angle){
        //TODO: Remove new here - make a object member
        yawRotation.fromAngleAxis(FastMath.PI * angle, Vector3f.UNIT_Y);
        yawRotation.multLocal(viewDirection);
        
        setViewDirection(viewDirection);
    }
    
    /**
     * Rotates the player´s head spatial about the x-Axis (pitch) - maximum head angle is clampded to 90 and -90 degrees
     * @param angle the higher the more the player´s head will be rotated, negative value rotates on opposite direction
     */
    private void lookUpDown(float angle){
        pitch += angle;
        pitch = FastMath.clamp(pitch, -FastMath.HALF_PI, FastMath.HALF_PI);
        head.setLocalRotation(pitchRotation.fromAngles(pitch, 0f, 0f));
    }
    
    /**
     * 
     * @return true if the player is moving left 
     */
    public boolean isMovingLeft(){
        return movingLeft;
    }
    
    /**
     * 
     * @return true if the player is moving right
     */
    public boolean isMovingRight(){
        return movingRight;
    }
    
    /**
     * 
     * @return true if the player is moving forward
     */
    public boolean isMovingForward(){
        return movingForward;
    }
    
    /**
     * 
     * @return true if the player is moving backward
     */
    public boolean isMovingBackward(){
        return movingBackward;
    }
    
    
    /**
     * 
     * @return the players head node 
     */
    public Node getHead(){
        return head;
    }
    
    /**
     * 
     * @return the players forward direction
     */
    public Vector3f getForwardDir(){
        return forwardDir;
    }
    
    public float getHeight() {
        return height;
    }
    
    // </editor-fold>
}
