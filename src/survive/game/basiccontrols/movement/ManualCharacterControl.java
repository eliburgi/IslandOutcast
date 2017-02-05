/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survive.game.basiccontrols.movement;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;

/**
 *
 * @author Elias
 */
public class ManualCharacterControl extends ManualControl {

    private BetterCharacterControl betterCharacterControl;
    
    public ManualCharacterControl() {
        
    }
    
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        
        if(spatial == null) return;
        
        this.betterCharacterControl = spatial.getControl(BetterCharacterControl.class);
        if(this.betterCharacterControl == null) {
            throw new IllegalStateException("Cannot add ManualCharacterControl to Spatial without CharacterControl");
        }
        
        //TODO: Maybe get speed aand etc. here from spatial if it has such a property
    }
    
    @Override
    public void steerX(float value) {

    }

    @Override
    public void steerY(float value) {

    }

    @Override
    public void moveX(float value) {

    }

    @Override
    public void moveY(float value) {

    }

    @Override
    public void moveZ(float value) {

    }

    public Vector3f getLocation() {
        return null;
    }

    public Vector3f getAimDirection() {
        return null;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if(!isEnabled()) return;
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }  
}
