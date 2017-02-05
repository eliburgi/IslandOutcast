package survive.game.player;

import survive.game.basicuserdata.inventory.Inventory;
import survive.game.world.items.Item;

/**
 *
 * @author Elias
 */
public class PlayerInventory extends Inventory {
    
    private int selectedIndex = 0;
    
    public PlayerInventory(int count) {
        super(count);
        selectedIndex = count / 2;
    }
    
    public void selectNextItem() {
        selectedIndex = ++selectedIndex % getCount();
    }
    
    public void selectPreviousItem() {
        selectedIndex = selectedIndex <= 0 ? getCount() - 1 : --selectedIndex;
    }
    
    public int getSelectedIndex() {
        return selectedIndex;
    }
    
    public Item getSelectedItem() {
        return get(selectedIndex);
    }
    
    public void removeSelectedItem() {
        removeItem(selectedIndex);
    }
}
