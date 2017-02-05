package survive.game.basicuserdata.inventory;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import java.io.IOException;
import survive.game.world.items.Item;


/**
 *
 * @author Elias
 */
public class Inventory implements Savable {
    
    private Item[] items;
    
    public Inventory(int count){
        //Create a new array with the given size
        items = new Item[count];
    }
    
    public Item get(int i){        
        if(i < 0 || i > items.length - 1) throw new ArrayIndexOutOfBoundsException("Inventory Index out of Bound! - Inventory get(int index)");
        return items[i];
    }
    
    public int add(Item item){
        int index = getNextFreeIndex();
        
        //Free slot has been found - add item there
        if(index != -1){
            items[index] = item;
        }    
        return index;
    }
    
    public boolean isFull() {
        return getNextFreeIndex() == -1;
    }
    
    private int getNextFreeIndex(){
        for(int i = 0; i < items.length; i++){
            //Free index found - return it
            if(get(i) == null) return i;
        }
        
        //All no free index available - all slots full
        return -1;
    }
    
    /*public boolean isEnoughSpaceForNewItem(){
        return items.size() < maxNumberOfSlots;
    }*/
    
    public int getCount(){
        return items.length;
    }
    
    public void removeItem(int index){
        if(index >= 0 && index < items.length){
            if(items[index] != null){
                items[index] = null;
            }
        }
    }
    
    public void clear() {
        for(int i = 0; i < items.length; i++) {
            removeItem(i);
        }
    }

    public void write(JmeExporter ex) throws IOException {

    }

    public void read(JmeImporter im) throws IOException {

    }
}
