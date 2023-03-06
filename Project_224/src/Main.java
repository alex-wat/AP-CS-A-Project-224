import com.googlecode.lanterna.TextColor;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Player fella = new Player("", new TextColor.RGB(0, 0, 0));
        Dungeon main = new Dungeon(fella, 10, 20);
        while(true) {
            main.run();
        }
    }
}