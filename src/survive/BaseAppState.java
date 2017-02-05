package survive;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

/**
 *
 * @author Elias
 */
public abstract class BaseAppState extends AbstractAppState {
    
    protected GameApplication   app;
    protected Node              rootNode; 
    protected Node              guiNode;  
    protected AssetManager      assetManager;
    protected AppStateManager   stateManager;
    protected InputManager      inputManager;
    protected ViewPort          viewPort;
    protected Camera            cam;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){
        super.initialize(stateManager, app);
        
        this.app = (GameApplication) app;
        this.rootNode     = this.app.getRootNode();
        this.guiNode      = this.app.getGuiNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = stateManager;
        this.inputManager = this.app.getInputManager();
        this.viewPort     = this.app.getViewPort();
        this.cam = this.app.getCamera();
        
        init();
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        
        if(enabled) {
            enabled();
        } else {
            disabled();
        }
    }
    
    //Methods for avoiding to write the whole initialize and setEnabled function all over again
    protected abstract void init();
    protected abstract void enabled();
    protected abstract void disabled();

    /**
     * @return the SimpleApplication
     */
    public GameApplication getApplication(){
        return app;
    }
    
    /**
     * @return the rootNode
     */
    public Node getRootNode() {
        return rootNode;
    }

    public Node getGuiNode(){
        return guiNode;
    }
    
    /**
     * @return the assetManager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * @return the stateManager
     */
    public AppStateManager getStateManager() {
        return stateManager;
    }

    /**
     * @return the inputManager
     */
    public InputManager getInputManager() {
        return inputManager;
    }

    /**
     * @return the viewPort
     */
    public ViewPort getViewPort() {
        return viewPort;
    }
    
    public Camera getCam(){
        return cam;
    }
}
