/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survive.game;

import com.jme3.bullet.BulletAppState;
import survive.BaseAppState;
import survive.GameApplication;
import survive.game.player.states.PlayerState;
import survive.game.world.World;
import survive.game.world.debug.EntitySpawnState;
import survive.game.world.timing.WorldTimeState;

/**
 *
 * @author Elias
 */
public class Game extends BaseAppState {
    
    //The game world
    private World world;
    private BulletAppState bulletAppState;
    
    //The player
    private PlayerState player;
    private boolean isNewGame = true;
    
    /**
     * Create game systems here
     */
    public Game() {
           
    }
    
    @Override
    protected void init() {
        //Create Physics
        bulletAppState = new BulletAppState();
        if(Globals.PHYSICS_DEBUG) {
            bulletAppState.setDebugEnabled(true);
        }
        if(Globals.PHYSICS_THREADED) {
            bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        }   
        stateManager.attach(bulletAppState);
        
        //Create a new world
        world = new World();
        stateManager.attach(world);
        //TODO
        //world.setNewWorld(isNewGame);
        
        //Create a new player
        player = new PlayerState(world);
        stateManager.attach(player);
        
        stateManager.attach(new EntitySpawnState());
    }
    
    @Override
    protected void enabled() {
        //Resume physics simulation, show world, ...
        world.setEnabled(true);
        
        //Resume player input, HUD, ...
        player.setEnabled(true);        
    }

    @Override
    protected void disabled() {
        //Pause physics simulation, hide world, ...
        world.setEnabled(false);
        
        //Pause player input, HUD, ...
        player.setEnabled(false);
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
    }
    
    public World getWorld() {
        return world;
    }
    
    public PlayerState getPlayer() {
        return player;
    }
    
}
