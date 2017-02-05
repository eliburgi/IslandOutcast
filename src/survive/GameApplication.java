package survive;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import survive.game.graphics.GraphicSettingState;
import survive.input.Input;
import survive.resource.EffectManager;
import survive.resource.ModelManager;
import survive.resource.ScreenManager;
import survive.resource.SoundManager;
import survive.resource.TextureManager;
import survive.screens.GameScreen;
import survive.screens.MainMenuScreen;
import survive.screens.OptionScreen;
import survive.screens.PauseScreen;
import survive.screens.SplashScreen;
import survive.game.world.entities.EntityID;
import survive.nifty.NiftyAppState;


/**
 * Diplomarbeit
 * @author Elias
 */
public class GameApplication extends SimpleApplication {
    
    private ModelManager modelManager;
    private TextureManager textureManager;
    private SoundManager soundManager;
    private EffectManager effectManager;
    private ScreenManager screenManager;
    
    private int cnt = 0;
    
    public static void main(String[] args) {
        GameApplication app = new GameApplication();
        /*AppSettings settings = new AppSettings(true);
        settings.setTitle("Island Outlast");
        //settings.setResolution(1280, 1024);
        settings.setFullscreen(true);
        settings.setFrameRate(60);
        app.setSettings(settings);
        app.setShowSettings(false);*/
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        //Create all Ressource Managers
        modelManager = new ModelManager(this);
        textureManager = new TextureManager(this);
        soundManager = new SoundManager(this);
        effectManager = new EffectManager(this);
        screenManager = new ScreenManager(this);
        
        //Load resources
        loadModels();
        loadTextures();
        loadSounds();
        loadEffects();
        loadScreens();
        
        //init all key configurations
        initInput();
        
        //Fly Cam is not needed in our game
        stateManager.detach(stateManager.getState(FlyCamAppState.class));
        
        stateManager.attach(new NiftyAppState());
        stateManager.attach(new GraphicSettingState());
        //Start Game
        //screenManager.enterScreen(Resources.Screens.MAIN_MENU);
    }
    
    @Override
    public void update() {
        super.update();
        if(cnt > 1) return;
        
        getScreenManager().enterScreen(Resources.Screens.MAIN_MENU);
        cnt = 2;
    }
    
    private void loadModels() {
        //Load Materials
        modelManager.loadMaterial(Resources.Materials.LIGHTING, "Materials/lighting.j3m");
        modelManager.loadMaterial(Resources.Materials.WATER, "Materials/WaterMaterial.j3m");
        modelManager.loadMaterial(Resources.Materials.WAVE, "Materials/Wave.j3m");
        modelManager.loadMaterial(Resources.Materials.SUN, "Materials/Sun.j3m");   
        
        //Load models
        modelManager.loadModel(EntityID.PLAYER, "Models/Jaime/Jaime.j3o");
        
        //Landscape Objects
        modelManager.loadModel(EntityID.Landscape.TREE_FIR, "Models/Entities/Trees/Fir/Tree1.j3o");
        modelManager.loadModel(EntityID.Landscape.TREE_OAK1, "Models/Entities/Trees/oak/oak1.j3o");
        modelManager.loadModel(EntityID.Landscape.TREE_OAK2, "Models/Entities/Trees/oak/oak2.j3o");
        modelManager.loadModel(EntityID.Landscape.TREE_DEADTREE, "Models/Entities/Trees/deadtree/deadtree.j3o");
        modelManager.loadModel(EntityID.Landscape.TREE_PALM, "Models/Entities/Trees/palm/palm.j3o");
        modelManager.loadModel(EntityID.Landscape.ROCK_SMALL, "Models/Entities/Rock/rock1.j3o");
        modelManager.loadModel(EntityID.Landscape.ROCK_BIG, "Models/Entities/Rock/rock2.j3o");
        modelManager.loadModel(EntityID.Landscape.GRASS_DEFAULT, "Models/Entities/Grass/grass.j3o");
        modelManager.loadModel(EntityID.Landscape.GRASS_FERN, "Models/Entities/Grass/fern.j3o");
        modelManager.loadModel(EntityID.Landscape.CACTUS, "Models/Entities/Cactus/cactus.j3o");
        modelManager.loadModel(EntityID.Landscape.BUSH, "Models/Entities/Bush/bush.j3o");
        modelManager.loadModel(EntityID.Landscape.REED, "Models/Entities/Reed/reed.j3o");
        modelManager.loadModel(EntityID.Landscape.SAPPLING_OAK1_PLACED, "Models/Entities/Sapplings/oak1_sappling/oak1Sappling.j3o");
        modelManager.loadModel(EntityID.Landscape.CAMPFIRE_PLACED, "Models/Entities/Campfire/campfire.j3o");
        
        //Animals
        modelManager.loadModel(EntityID.Animals.FOX, "Models/Entities/Animals/Fox/fox.j3o");
        
        //Items
        modelManager.loadModel(EntityID.Items.STONE, "Models/Items/Resources/stone/stone.j3o");
        modelManager.loadModel(EntityID.Items.WOOD, "Models/Items/Resources/wood/wood.j3o");
        modelManager.loadModel(EntityID.Items.APPLE, "Models/Items/Resources/apple/apple_small.j3o");
        modelManager.loadModel(EntityID.Items.AXE, "Models/Items/Tools/Axe/axe.j3o");
        modelManager.loadModel(EntityID.Items.SWORD, "Models/Items/Tools/Sword/sword.j3o");
        modelManager.loadModel(EntityID.Items.SAPPLING_OAK1_DROPPED, "Models/Entities/Sapplings/oak1_sappling/oak1Sappling.j3o");
        modelManager.loadModel(EntityID.Items.CAMPFIRE_DROPPED, "Models/Entities/Campfire/campfire.j3o");
    }
    
    private void loadTextures() {
        textureManager.loadTexture(Resources.Textures.SKY_TOP, "Textures/Sky/Box_Top.png");
        textureManager.loadTexture(Resources.Textures.SKY_BOT, "Textures/Sky/Box_Bottom.png");
        textureManager.loadTexture(Resources.Textures.SKY_LEFT, "Textures/Sky/Box_Left.png");
        textureManager.loadTexture(Resources.Textures.SKY_RIGHT, "Textures/Sky/Box_Right.png");
        textureManager.loadTexture(Resources.Textures.SKY_FRONT, "Textures/Sky/Box_Front.png");
        textureManager.loadTexture(Resources.Textures.SKY_BACK, "Textures/Sky/Box_Back.png");
        textureManager.loadTexture(Resources.Textures.CROSSHAIR_1, "Interface/gui/crosshair.png");
    }
    
    private void loadSounds() {
        //TODO: Load sound and music here
    }
    
    private void loadEffects() {
        //TODO: Load particle effects here
    }
    
    private void loadScreens() {
        screenManager.loadScreen(Resources.Screens.SPLASH,    new SplashScreen());
        screenManager.loadScreen(Resources.Screens.MAIN_MENU, new MainMenuScreen());
        screenManager.loadScreen(Resources.Screens.OPTIONS,   new OptionScreen());
        screenManager.loadScreen(Resources.Screens.GAME,      new GameScreen());
        screenManager.loadScreen(Resources.Screens.PAUSE,     new PauseScreen());
    }

    private void initInput() {
        inputManager.clearMappings();
        inputManager.addMapping(Input.ACTION_OPEN_OPTIONS_IN_GAME, new KeyTrigger(KeyInput.KEY_ESCAPE));
        inputManager.addMapping(Input.ACTION_RESTART, new KeyTrigger(KeyInput.KEY_P));
    }
    
    public ModelManager getModelManager() {
        return modelManager;
    }

    public TextureManager getTextureManager() {
        return textureManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public EffectManager getEffectManager() {
        return effectManager;
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }
}
