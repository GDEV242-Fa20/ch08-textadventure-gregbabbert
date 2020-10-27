/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, foyer, living_room, garden, shed, bathroom, secret_room, office, library, dining_room, kitchen, family_room, guest_room, master_bedroom, mysterious_hallway, evil_lair;
      
        // create the rooms
        outside = new Room("outside the entrance of a verrryyyyyyy spooky mansion");
        foyer = new Room("inside the mansion's foyer, it feels like someone's watching you..");
        living_room = new Room("in the living room, it doesn't look very welcoming");
        garden = new Room("in the garden, it's hard to see with all the mist");
        shed = new Room("in the garden's tool shed, it looks like there's an axe missing..");
        bathroom = new Room("in the bathroom, it hasn't been cleaned in ages");
        secret_room = new Room("in a secret room, woah");
        office = new Room("in the office, look at all the creepy documents");
        library = new Room("in the library, a lot of these books have suspicious titles");
        dining_room = new Room("in the dining room, it's very dark in here");
        kitchen = new Room("in the kitchen, there's rats everywhere");
        family_room = new Room("in the family room, the family portrait looks kind of sinister");
        guest_room = new Room("in the guest room, I wonder why it doesn't say anyone's left");
        master_bedroom = new Room("in the master bedroom, this room deosn't feel right");
        mysterious_hallway = new Room("in a secret passageway! There's a bright light at the end of the tunnel..");
        evil_lair = new Room("in an evil lair! The ghost of the original family is there watching you");
        
        // initialise room exits
        outside.setExit("north", foyer);
        
        foyer.setExit("north", bathroom);
        foyer.setExit("east", dining_room);
        foyer.setExit("south", outside);
        foyer.setExit("west", living_room);
        
        living_room.setExit("north", garden);
        living_room.setExit("east", foyer);
        
        garden.setExit("north", shed);
        garden.setExit("east", office);
        garden.setExit("south", living_room);
        
        shed.setExit("south", garden);
        
        bathroom.setExit("south", foyer);
        bathroom.setExit("east", kitchen);
        
        secret_room.setExit("north", office);
        
        office.setExit("north", library);
        office.setExit("east", family_room);
        office.setExit("south", secret_room);
        office.setExit("west", garden);
        
        library.setExit("south", office);
        
        dining_room.setExit("north", kitchen);
        dining_room.setExit("west", foyer);
        
        kitchen.setExit("north", family_room);
        kitchen.setExit("south", dining_room);
        kitchen.setExit("west", bathroom);
        
        family_room.setExit("north", guest_room);
        family_room.setExit("east", master_bedroom);
        family_room.setExit("south", kitchen);
        family_room.setExit("west", office);
        
        guest_room.setExit("south", family_room);
        
        master_bedroom.setExit("west", family_room);
        master_bedroom.setExit("east", mysterious_hallway);
        
        mysterious_hallway.setExit("west", master_bedroom);
        mysterious_hallway.setExit("east", evil_lair);
        
        evil_lair.setExit("west", mysterious_hallway);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Harry's Haunted Mansion!");
        System.out.println("Harry's Haunted Mansion! is a new, creepy adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
                
            case LOOK:
                look();
                break;
                
            case SING:
                sing();
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You've come across an abandoned Mansion,");
        System.out.println("It belonged to someone named Harry.");
        System.out.println("Walk around and see what you can find.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * This command prints out the long description of the current room.
    */
    private void look() {
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * This command prints out the long description of the current room.
    */
    private void sing() {
        System.out.println("You just sang a song, it sounded great.");
    }
    
}
