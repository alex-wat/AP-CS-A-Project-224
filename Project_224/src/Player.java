import com.googlecode.lanterna.TextColor;

import java.util.ArrayList;

public class Player {
    int level;
    ArrayList<String> items;
    String name;
    TextColor playerColor;

    Player(String nameParam, TextColor col) {
        level = 1;
        name = nameParam;
        items = new ArrayList<String>();
        playerColor = col;
    }

    public TextColor getPlayerColor(){
        return playerColor;
    }
    public void setPlayerColor(TextColor colo){
         playerColor = colo;
    }
    public String getPlayerName(){
        return name;
    }
    public void setPlayerName(String namo){
        name = namo;
    }
    public int getPlayerLevel(){
        return level;
    }
    public void setPlayerLevel(int lev){
        level = lev;
    }
    public ArrayList<String> getPlayerItems(){
        return items;
    }
    public void setPlayerItems(ArrayList<String> ite){
        items = ite;
    }
}
