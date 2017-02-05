/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survive.game.basiccontrols.movement;

/**
 *
 * @author Elias
 */
public abstract class ManualControl extends MovementControl {
    
    public abstract void steerX(float value);
    
    public abstract void steerY(float value);
    
    public abstract void moveX(float value);
    
    public abstract void moveY(float value);
    
    public abstract void moveZ(float value);
    
}
