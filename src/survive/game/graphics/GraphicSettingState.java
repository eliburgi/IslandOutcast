/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package survive.game.graphics;

import com.jme3.system.AppSettings;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import survive.BaseAppState;

/**
 *
 * @author Elias
 */
public class GraphicSettingState extends BaseAppState {
    
    private boolean fullscreen;
    
    public void setFullscreen(boolean b) {
        if(fullscreen != b && b) {
            this.fullscreen = b;
            GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            AppSettings settings = app.getContext().getSettings();
            settings.setResolution(device.getDisplayMode().getWidth(), device.getDisplayMode().getHeight());
            //settings.setFrequency(modes[i].getRefreshRate());
            //settings.setBitsPerPixel(modes[i].getBitDepth());
            settings.setFullscreen(device.isFullScreenSupported());
            app.setSettings(settings);
            app.restart();
        } else if(fullscreen != b && !b) {
            this.fullscreen = b;
            GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            AppSettings settings = app.getContext().getSettings();
            settings.setResolution(1280, 1024);
            //settings.setFrequency(modes[i].getRefreshRate());
            //settings.setBitsPerPixel(modes[i].getBitDepth());
            settings.setFullscreen(false);
            app.setSettings(settings);
            app.restart();
        }
    }

    @Override
    protected void init() {

    }

    @Override
    protected void enabled() {

    }

    @Override
    protected void disabled() {

    }
    
}
