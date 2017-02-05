package survive.game.player;

import survive.game.basiccontrols.movement.FirstPersonControl;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.util.ArrayList;
import survive.Resources;
import survive.game.basiccontrols.animation.FirstPersonAnimControl;
import survive.game.world.World;
import survive.game.world.entities.AbstractEntityFactory;
import survive.game.world.entities.EntityID;
import survive.game.basicuserdata.Health;
import survive.game.player.controls.PlayerControl;

/**
 *
 * @author Elias
 */
public class PlayerFactory extends AbstractEntityFactory {

    //Spatials
    //The players head node
    private Node playerHead;
    
    //The players arm which should be used to hold diferent objects
    //Default this is the right arm
    private Node playerArm;
    
    private CameraNode camNode;
    
    //The x-Offset of the arm relative to the player
    private float rightArmOffset_X = 0.5f;
    
    //Physics Properties 
    private Vector3f jumpForce = new Vector3f(0f, 1500f, 0f);
    private Vector3f gravity = new Vector3f(0f, 100f, 0f);
    
    //Factor that defines how much the size (collision shape) is scaled when ducked
    private float duckedFactor;   
    
    //The mass of the player
    private float mass;
    
    //The height of the player
    private float height;
    
    //The radius of the players capsule collision shape
    private float radius;
    
    //Input
    //
    private float leftRightMouseSensitivity;
    private float upDownMouseSensitivity;
    
    //Movement
    private float walkSpeed;
    private float runSpeed;
    
    private float inAirDampingFactor;
    private float duckedDampingFactor;
    
    //Inventory 
    private int numberOfInventorySlots;
    
    //Health
    private int maxHealth;
    
    public PlayerFactory(World world, String parentNode, InputManager inputManager){
        super(world, parentNode, EntityID.PLAYER, Resources.Materials.LIGHTING);
        this.world = world;
       
        //TODO: Load this values from a file      
        duckedFactor = 0.5f;   
        mass = 80f;
        height = 1.8f;
        radius = 0.5f;
        
        //Default mouse sensitivity factors
        leftRightMouseSensitivity = 16f;
        upDownMouseSensitivity = 50f;
        
        //default Movement factors
        walkSpeed = 10f;
        runSpeed = 30f;
        
        inAirDampingFactor = 0.5f;
        duckedDampingFactor = 0.3f;
        
        //default Inventory factors
        numberOfInventorySlots = 9;
        
        maxHealth = 20;
    }
    
    /**
     * Dont call this from the outside!!!
     * This method is used internally by the createSpatial() method
     * @return 
     */
    @Override
    public Spatial createGeometry(float x, float y, float z) {
                
        Node player = new Node("player");
        player.setShadowMode(RenderQueue.ShadowMode.Cast);
        playerHead = new Node("Player Head");
        player.attachChild(playerHead);
        
        //Attach arms to the player
        //TODO: set arms local translation etc.
        Node arms = new Node("Player Arm");
        arms.attachChild(new Node("Left Arm"));
        playerArm = new Node("Right Arm");
        
        //TODO: Make this constants
        playerArm.setLocalTranslation(-rightArmOffset_X, height * 0.75f, 2f);
        arms.attachChild(playerArm);
        camNode = new CameraNode("CamNode", world.getCam());
        playerHead.attachChild(camNode);
        camNode.attachChild(arms);
        player.setLocalTranslation(x, y, z);
        
        //UserData
        //Body 
        player.setUserData("head", playerHead);
        player.setUserData("hand", playerArm);
        
        //Movement
        player.setUserData("mass", mass);
        player.setUserData("height", height);
        player.setUserData("radius", radius);
        player.setUserData("walkSpeed", walkSpeed);
        player.setUserData("runSpeed", runSpeed);
        
        //Gameplay
        player.setUserData("health", new Health(maxHealth));
        player.setUserData("inventory", new PlayerInventory(numberOfInventorySlots));
        
        return player;
    }

    /**
     * Dont call this from the outside!!!
     * This method is used internally by the createSpatial() method
     * @return 
     */
    @Override
    public ArrayList<Control> createControls(float x, float y, float z) {
        
        controls.clear();
        
        //TODO: Maybe use Array here for better Performence - Cache Misses!!!
        FirstPersonControl firstPersonControl = new FirstPersonControl(radius, height, mass, 
                                                leftRightMouseSensitivity, upDownMouseSensitivity, 
                                                walkSpeed, runSpeed, inAirDampingFactor, 
                                                duckedDampingFactor, playerHead);
        
        //Set physics properties of the player
        //Processed by the physics simulation
        firstPersonControl.setJumpForce(jumpForce.clone());
        firstPersonControl.warp(new Vector3f(x, y, z));
        firstPersonControl.setDuckedFactor(duckedFactor);        
        firstPersonControl.setCameraNode(camNode);
        
        controls.add(firstPersonControl);      
        controls.add(new FirstPersonAnimControl());      
        controls.add(new PlayerControl(world, firstPersonControl));
        
        return controls;
    }   
}
