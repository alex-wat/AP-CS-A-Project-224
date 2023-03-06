import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.ansi.ANSITerminal;

import java.io.IOException;

public class Dungeon {
    Terminal terminal;
    Screen screen;
    TextGraphics tg;
    int offset = 0;
    int currentX;
    int currentY;
    int PlayerX;
    int PlayerY;
    int dungeonWidth;
    int dungeonHeight;
    Player player;
    private Room[][] Rooms;
    public Dungeon(Player mc, int widthParam, int heightParam) throws IOException {
        currentX = 1;
        currentY = 1;
        PlayerX = 1;
        PlayerY = 1;
        player = mc;
        terminal = new DefaultTerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);
        tg = screen.newTextGraphics();
        screen.startScreen();
        dungeonWidth = widthParam;
        dungeonHeight = heightParam;
        Rooms = new Room[dungeonWidth][dungeonHeight];
        //puts a room in the Rooms array
        for(int w = 0; w<widthParam; w++) {
            for(int h = 0; h<heightParam; h++) {
                int randVar1 = Room.getRandomNumber(3,10);
                int randVar2 = Room.getRandomNumber(3,10);
                Rooms[w][h] = new Room(randVar1, randVar2);
            }
        }
        printRoom();
        customizePlayer();
        printRoom();
    }
    //end of constructor

    public String[][] currentTiles() {
        return Rooms[currentX][currentY].getTiles();
    }
    public int currentWidth() {
        return Rooms[currentX][currentY].getWidth();
    }
    public int currentHeight() {
        return Rooms[currentX][currentY].getHeight();
    }

    public void customizePlayer() throws IOException {
        //write this l8er
        String nam = "";
        printDungeon("Enter name: ");
        while(true) {
            KeyStroke ks = screen.readInput();
            if(ks.getKeyType().equals(KeyType.Backspace)) {
                if(nam.length()>0) nam = nam.substring(0, nam.length() - 1);
                printDungeon("Enter name: "+nam);
                continue;
            }
            if(ks.getKeyType().equals(KeyType.Enter)) {
                break;
            }
            if(ks.getCharacter()!=null) {
                nam+=ks.getCharacter();
                printDungeon("Enter name: "+nam);
            }
        }
        player.setPlayerName(nam);
        switch(menuDungeon("Player color", new String[]{"White", "Yellow", "Red", "Blue", "Green"})) {
            case 1:
                player.setPlayerColor(new TextColor.RGB(255, 255, 255));
                break;
            case 2:
                player.setPlayerColor(new TextColor.RGB(255, 252, 0));
                break;
            case 3:
                player.setPlayerColor(new TextColor.RGB(237, 36, 79));
                break;
            case 4:
                player.setPlayerColor(new TextColor.RGB(36, 73, 237));
                break;
            case 5:
                player.setPlayerColor(new TextColor.RGB(113, 138, 115));
                break;
        }
    }
    public void setTileColor(int xp, int yp) {
        switch(currentTiles()[xp][yp]) {
            case "C":
                tg.setForegroundColor(new TextColor.RGB(255, 140, 0));
                break;
            case "G":
                tg.setForegroundColor(new TextColor.RGB(255, 140, 0));
                break;
            case "W":
                tg.setForegroundColor(new TextColor.RGB(255, 140, 0));
                break;
            case "R":
                tg.setForegroundColor(new TextColor.RGB(255, 140, 0));
                break;
            case "S":
                tg.setForegroundColor(new TextColor.RGB(255, 140, 0));
                break;
            case "K":
                tg.setForegroundColor(new TextColor.RGB(255, 140, 0));
                break;
            case "#":
                tg.setForegroundColor(new TextColor.RGB(230, 0, 255));
                break;
            case "$":
                tg.setForegroundColor(new TextColor.RGB(230, 0, 255));
                break;
            case "%":
                tg.setForegroundColor(new TextColor.RGB(230, 0, 255));
                break;
            case "^":
                tg.setForegroundColor(new TextColor.RGB(230, 0, 255));
                break;
            case "o":
                tg.setForegroundColor(new TextColor.RGB(230, 0, 255));
                break;
            case "*":
                tg.setForegroundColor(new TextColor.RGB(230, 0, 255));
                break;
        }
    }
    public void printRoom() throws IOException {
        for(int w = 0; w<currentWidth(); w++) {
            for(int h = 0; h<currentHeight(); h++) {
                TextColor col = tg.getForegroundColor();
                setTileColor(w, h);
                tg.putString(w+offset, h+offset, currentTiles()[w][h]);
                tg.setForegroundColor(col);
                screen.refresh();
            }
        }
        for(int w = 0; w<dungeonWidth; w++) {
            for(int h = 0; h<dungeonHeight; h++) {
                tg.putString(h+30, w, "·");
            }
        }
        tg.putString(currentY+30, currentX, "X");
        TextColor col = tg.getForegroundColor();
        tg.setForegroundColor(player.getPlayerColor());
        tg.putString(PlayerX+offset, PlayerY+offset, "@");
        tg.setForegroundColor(col);
        tg.putString(31+dungeonHeight, 1,"Current Room: ("+(currentX+1)+", "+(currentY+1)+")");
        screen.refresh();
    }
    public void printDungeon(String par) throws IOException {
        tg.putString(0, dungeonWidth+1, "                          ");
        tg.putString(0, dungeonWidth+1, par);
        screen.refresh();
    }
    public int menuDungeon(String title, String[] par) throws IOException {
        tg.putString(0, dungeonWidth+1, "                                     ");
        for(int n = 0; n<par.length; n++) {
            tg.putString(0, dungeonWidth+n+2, "                                    ");
            tg.putString(1, dungeonWidth+n+2, (n+1)+". "+par[n]);
        }
        tg.putString(0, dungeonWidth+2, "*");
        tg.putString(0, dungeonWidth+1, title);
        int pos = 1;
        screen.refresh();
        while(true) {
            KeyStroke ks = screen.readInput();
            if(ks.getCharacter()!=null) {
                if (ks.getCharacter().equals('w')) {
                    tg.putString(0, dungeonWidth+1+pos, " ");
                    pos--;
                    if(pos<1) pos = par.length;
                    tg.putString(0, dungeonWidth+1+pos, "*");
                    screen.refresh();
                }
                if (ks.getCharacter().equals('s')) {
                    tg.putString(0, dungeonWidth+1+pos, " ");
                    pos++;
                    if(pos>par.length) pos = 1;
                    tg.putString(0, dungeonWidth+1+pos, "*");
                    screen.refresh();
                }
                if (ks.getCharacter().equals('e')) {
                    break;
                }
            }
        }
        for(int n = 0; n<=par.length; n++) {
            tg.putString(0, dungeonWidth+n+1, "                             ");
        }
        return pos;
    }
    public void pickUpMenu(String ite) throws IOException{
        if(menuDungeon("You see a "+ite, new String[]{"Pick it up", "Leave it"})==1) {
            currentTiles()[PlayerX][PlayerY] = "·";
            player.getPlayerItems().add(ite);
            printDungeon("You picked up a "+ite);
        }
    }
    public void pickUpMenu(String ite, String actualIte) throws IOException { //overflow if specif
        if(menuDungeon("You see a "+ite, new String[]{"Pick it up", "Leave it"})==1) {
            currentTiles()[PlayerX][PlayerY] = "·";
            player.getPlayerItems().add(actualIte);
            printDungeon("You picked up a "+actualIte);
        }
    }
    public void killCreatureMenu(String enemy) throws IOException {
        if (menuDungeon("A " + enemy + " is chilling here", new String[]{"Kill it", "Leave it"}) == 1) {
            currentTiles()[PlayerX][PlayerY] = "·";
            player.setPlayerLevel(player.getPlayerLevel() + 1);
            printDungeon("Wow you really just killed him");
        }
    }
    public String getOneOfThree(String one, String two, String three) {
        String tng = "";
        switch (Room.getRandomNumber(1, 3)) {
            case 1: tng = one;break;
            case 2: tng = two;break;
            case 3: tng = three;break;
        }
        return tng;
    }
    public void run() throws IOException {
        KeyStroke ks = screen.readInput();
        //System.out.println(ks);
        if(ks.getCharacter()!=null) {
            if (ks.getCharacter().equals('s')&&PlayerY!=currentHeight() - 1&&!"+|-".contains(currentTiles()[PlayerX][PlayerY+1])) {
                TextColor col = tg.getForegroundColor();
                setTileColor(PlayerX, PlayerY);
                tg.putString(PlayerX+offset, PlayerY+offset, currentTiles()[PlayerX][PlayerY]);
                tg.setForegroundColor(col);
                PlayerY++;
            }
            if (ks.getCharacter().equals('w')&&PlayerY!=0&&!"+|-".contains(currentTiles()[PlayerX][PlayerY-1])) {
                TextColor col = tg.getForegroundColor();
                setTileColor(PlayerX, PlayerY);
                tg.putString(PlayerX+offset, PlayerY+offset, currentTiles()[PlayerX][PlayerY]);
                tg.setForegroundColor(col);
                PlayerY--;
            }
            if (ks.getCharacter().equals('d')&&PlayerX!=currentWidth() - 1&&!"+|-".contains(currentTiles()[PlayerX+1][PlayerY])) {
                TextColor col = tg.getForegroundColor();
                setTileColor(PlayerX, PlayerY);
                tg.putString(PlayerX+offset, PlayerY+offset, currentTiles()[PlayerX][PlayerY]);
                tg.setForegroundColor(col);
                PlayerX++;
            }
            if (ks.getCharacter().equals('a')&&PlayerX!=0&&!"+|-".contains(currentTiles()[PlayerX-1][PlayerY]))  {
                TextColor col = tg.getForegroundColor();
                setTileColor(PlayerX, PlayerY);
                tg.putString(PlayerX+offset, PlayerY+offset, currentTiles()[PlayerX][PlayerY]);
                tg.setForegroundColor(col);
                PlayerX--;
            }
            if (ks.getCharacter().equals('e')) {
                switch (currentTiles()[PlayerX][PlayerY]) {
                    case "D":
                        int doorPos = -1;
                        if(PlayerX == 0) {
                            doorPos = 1; //up
                        }
                        if(PlayerX == currentWidth() - 1) {
                            doorPos = 2; //right
                        }
                        if(PlayerY == 0) {
                            doorPos = 4; //left
                        }
                        if(PlayerY == currentHeight() - 1) {
                            doorPos = 3; //down
                        }
                        if((currentX==0&&doorPos==4)||(currentX==dungeonWidth-1&&doorPos==3)||(currentY==0&&doorPos==1)||(currentY==dungeonHeight-1&&doorPos==2)){
                            if(1==menuDungeon("This is a door to the outside", new String[]{"Leave", "Don't Leave"})) {
                                printDungeon("No.");
                            }
                            break;
                        }
                        //System.out.println(doorPos);
                        screen.clear();
                        //sets player at corresponding door for next room
                        switch (doorPos) {
                            case 3: //top of new room
                                currentX++;
                                for(int n = 0; n<currentWidth(); n++) {
                                    if(currentTiles()[n][0].equals("D"))
                                        PlayerX = n;
                                }
                                PlayerY = 0;
                                break;
                            case 2: //left of new room
                                currentY++;
                                for(int n = 0; n<currentHeight(); n++) {
                                    if(currentTiles()[0][n].equals("D"))
                                        PlayerY = n;
                                }
                                PlayerX = 0;
                                break;
                            case 1: //right of new room
                                currentY--;
                                for(int n = 0; n<currentHeight(); n++) {
                                    if(currentTiles()[currentWidth()-1][n].equals("D"))
                                        PlayerY = n;
                                }
                                PlayerX = currentWidth()-1;
                                break;
                            case 4: //bottom of new room
                                currentX--;
                                for(int n = 0; n<currentWidth(); n++) {
                                    if(currentTiles()[n][currentHeight()-1].equals("D"))
                                        PlayerX = n;
                                }
                                PlayerY = currentHeight()-1;
                                break;
                        }
                        //System.out.println("entered ("+currentX+", "+currentY+")");
                        printRoom();
                        break;
                    case "*":
                        pickUpMenu("trinket", getOneOfThree("ring", "necklace", "bracelet"));
                        break;
                    case "#":
                        pickUpMenu("piece of clothing", getOneOfThree("scarf", "hat", "shirt"));
                        break;
                    case "$":
                        pickUpMenu("bag of money", getOneOfThree("small bag of money", "bag of money", "big bag of money"));
                        break;
                    case "%":
                        pickUpMenu("weapon", getOneOfThree("sword", "mace", "club"));
                        break;
                    case "o":
                        pickUpMenu("bag", getOneOfThree("bag of gold", "bag of merch", "bag of worms"));
                        break;
                    case "^":
                        pickUpMenu("bit of food", getOneOfThree("moldy bread", "ice cream", "dry beef"));
                        break;
                    case "G":
                        killCreatureMenu("goblin");
                        break;
                    case "K":
                        killCreatureMenu("kobold");
                        break;
                    case "W":
                        killCreatureMenu("wyvern");
                        break;
                    case "R":
                        killCreatureMenu("rogue");
                        break;
                    case "S":
                        killCreatureMenu("skeleton");
                        break;
                    case "C":
                        killCreatureMenu("cat");
                        break;
                }
                }
            }
            //here
        if (ks.getCharacter().equals('q')) {
            screen.clear();
            TextColor col = tg.getForegroundColor();
            tg.setForegroundColor(player.getPlayerColor());
            tg.putString(0, 0, player.getPlayerName());
            tg.putString(0, 1, "Level: "+player.getPlayerLevel());
            tg.putString(0, 2, "Items:");
            for(int n = 0; n<player.getPlayerItems().size(); n++) {
                tg.putString(0, 3+n, player.getPlayerItems().get(n));
            }
            //end of drawing
            tg.setForegroundColor(col);
            screen.refresh();
            while (true) {
                ks = screen.readInput();
                if(ks!=null) {
                    if(ks.getCharacter().equals('q')) {
                        screen.clear();
                        printRoom();
                        break;
                    }
                }
            }
        }
        TextColor col = tg.getForegroundColor();
        tg.setForegroundColor(player.getPlayerColor());
        tg.putString(PlayerX+offset, PlayerY+offset, "@");
        tg.setForegroundColor(col);
        screen.refresh();
    }
}
