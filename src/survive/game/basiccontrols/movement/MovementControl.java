/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survive.game.basiccontrols.movement;

import com.jme3.math.Vector3f;
import com.jme3.scene.control.AbstractControl;

/**
 * Base interface for autonomous and manual movement controls.
 * @author Elias
 */
public abstract class MovementControl extends AbstractControl {
    public abstract Vector3f getLocation();
    public abstract Vector3f getAimDirection();
}
