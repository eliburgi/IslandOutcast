/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survive.game.basiccontrols.ai.fsm;

import com.jme3.scene.control.AbstractControl;


/**
 *
 * @author Elias
 */
public abstract class FSMState extends AbstractControl {
    
    public abstract void enter();
    public abstract void exit();
    
}
