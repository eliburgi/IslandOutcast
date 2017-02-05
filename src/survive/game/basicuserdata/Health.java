/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survive.game.basicuserdata;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import java.io.IOException;

/**
 *
 * @author Elias
 */
public class Health implements Savable {
    
    //The maximum amount of health
    private int maxHealth;
    
    //The current amount of health
    private int health;
    
    public Health(int maxHealth){
        this(maxHealth, maxHealth);
    }
    
    public Health(int maxHealth, int health){
        this.maxHealth = maxHealth;
        setHealth(health);
    }
    
    /**
     * Sets the maximum health of this control
     * @param maxHealth the amount of maximum health
     */
    public void setMaxHealth(int maxHealth){
        this.maxHealth = maxHealth;
    }
    
    /**
     * 
     * @return the maximum health 
     */
    public int getMaxHealth(){
        return maxHealth;
    }
    
    /**
     * Sets the current health to the given value
     * If he value is less than zero or greater than maxHealth the value is clamped to the given borders
     * @param health the amount of current health
     */
    public final void setHealth(int health){
        this.health = Math.min(Math.max(health, 0), maxHealth);
    }
    
    /**
     * 
     * @return the current health
     */
    public float getHealth(){
        return health;
    }
    
    /**
     * Subtracts the given amount off the current health
     * If the health reaches zero, destroyed is set to true
     * @param damage 
     */
    public void damage(int damage){
        setHealth(health - damage);
    }
    
    /**
     * Adds the given amount to the current health to increase it
     * Clamped to 0 and maxHealth
     * @param heal the amount that should be added to the current health
     */
    public void heal(int heal){
        setHealth(health + heal);
    }
    
    /**
     * True if this control is destroyed (health reached zero or below)
     * @return 
     */
    public boolean isDestroyed(){
        return health <= 0;
    }   

    //Needed for userdata
    public void write(JmeExporter ex) throws IOException {

    }

    public void read(JmeImporter im) throws IOException {

    }
}
