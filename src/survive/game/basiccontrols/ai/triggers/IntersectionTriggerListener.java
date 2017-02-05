/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survive.game.basiccontrols.ai.triggers;

import com.jme3.scene.Spatial;

/**
 *
 * @author Elias
 */
public interface IntersectionTriggerListener {
    
    /**
     * 
     * @param other
     * @return true if other listeners should be informed too 
     */
    public boolean onIntersection(Spatial other);
    
}
