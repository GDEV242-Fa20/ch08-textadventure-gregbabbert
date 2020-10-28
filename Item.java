import java.util.HashMap;

/**
 * This class is used to create an item. To create an item a
 * description and a weight needs to be provided.
 *
 * @author Greg Babbert
 * @version 10/27/2020
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String itemDescription;
    private int itemWeight; 
    //private HashMap<Room> room; 
    
    /**
     * Constructor for objects of class Item
     */
    public Item(String itemDescription, int itemWeight)
    {
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
    }
    
    /**
     * @return The short description of the item
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return itemDescription;
    }
    
    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return itemDescription + "It weighs: " + itemWeight+ "lbs.\n";
    }
}
