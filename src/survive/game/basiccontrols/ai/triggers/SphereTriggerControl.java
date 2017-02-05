/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survive.game.basiccontrols.ai.triggers;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Elias
 */
public class SphereTriggerControl extends AbstractControl {
    
    private PhysicsSpace physicsSpace;
    private GhostControl ghostControl;
    private float radius;
    
    private float checkTimer = 0f;
    private float checkTime;
    
    private IntersectionTriggerListener intersectionListener;
    
    public SphereTriggerControl(PhysicsSpace physicsSpace, float radius) {
        this(physicsSpace, radius, 1 / 10f);
    }
    
    public SphereTriggerControl(PhysicsSpace physicsSpace, float radius, float checkTime) {
        this.physicsSpace = physicsSpace;
        this.radius = radius;
        this.checkTime = checkTime;
    }
    
    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        
        if(spatial == null) return;
        
        if(ghostControl == null) {
            this.ghostControl = new GhostControl(new SphereCollisionShape(radius));
        }
        this.spatial.addControl(ghostControl);
        physicsSpace.add(ghostControl);
    }

    @Override
    protected void controlUpdate(float tpf) {
        checkTimer += tpf;
        
        //If it is again time to check for overlapping objects (Default 10Hz)
        if (checkTimer >= checkTime) {
            checkTimer = 0;
            
            //Dont need to check if there is nobody to inform
            if(intersectionListener == null) return;
            
            //If there are overlapping objects
            if (ghostControl.getOverlappingCount() > 0) {
                List<PhysicsCollisionObject> objects = ghostControl.getOverlappingObjects();
                for (Iterator<PhysicsCollisionObject> it = objects.iterator(); it.hasNext();) {
                    PhysicsCollisionObject physicsCollisionObject = it.next();
                    
                    Spatial targetEntity = spatialFromPhysicsObj(physicsCollisionObject);
                    if (targetEntity != null) {
                        //Can be used by sub-classes to control information behaviour
                        //if(validateListenerInform(targetEntity)) {
                            //Inform the listener about intersecting spatial
                            intersectionListener.onIntersection(targetEntity);
                        //}              
                    }
                }
            }
        }     
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    } 
    
    public void setIntersectionListener(IntersectionTriggerListener triggerListener) {
        this.intersectionListener = triggerListener;
    }
    
    private Spatial spatialFromPhysicsObj(PhysicsCollisionObject physicsCollisionObject) {
        Object obj = physicsCollisionObject.getUserObject();
        if (obj instanceof Spatial) {
            return (Spatial) obj;
        }
        return null;
    }
}
