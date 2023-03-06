import java.lang.Math;

public class Room {
    private int width;
    private int height;
    private String[][] tiles;
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public Room(int roomWidth, int roomHeight) {
        width = roomWidth;
        height = roomHeight;

        tiles = new String[width][height];
        for(int w = 0; w<width; w++) {
            for(int h = 0; h<height; h++) {
                int randVar = getRandomNumber(1,10); //generates num 1-10
                switch(randVar) {
                    default:
                        tiles[w][h] = "·";
                }
            }
        }
        if(getRandomNumber(1,3)==1) {
            String tng = "";
            switch (getRandomNumber(1, 6)) {
                case 1: tng = "*"; break;
                case 2: tng = "#"; break;
                case 3: tng = "$"; break;
                case 4: tng = "%"; break;
                case 5: tng = "^"; break;
                case 6: tng = "o"; break;
            }
            tiles[getRandomNumber(1, width-2)][getRandomNumber(1, height-2)] = tng;
        }
        if(getRandomNumber(1,5)==1) {
            String tng = "";
            switch (getRandomNumber(1, 6)) {
                case 1: tng = "G"; break;
                case 2: tng = "W"; break;
                case 3: tng = "R"; break;
                case 4: tng = "S"; break;
                case 5: tng = "K"; break;
                case 6: tng = "C"; break;
            }
            tiles[getRandomNumber(1, width-2)][getRandomNumber(1, height-2)] = tng;
        }
        for(int w = 0; w<width; w++) {
            tiles[w][0] = "-";
            tiles[w][height-1] = "-";
        }
        for(int h = 0; h<height; h++) {
            tiles[0][h] = "|";
            tiles[width-1][h] = "|";
        }
        tiles[0][0] = "+";
        tiles[width-1][0] = "+";
        tiles[0][height-1] = "+";
        tiles[width-1][height-1] = "+";
        tiles[getRandomNumber(1,  width - 2)][0] = "D"; //random point on top side is 'D'
        tiles[getRandomNumber(1,  width - 2)][height - 1] = "D"; //random point on bottom side is 'D'
        tiles[0][getRandomNumber(1,  height - 2)] = "D"; //random point on left side is 'D'
        tiles[width - 1][getRandomNumber(1,  height - 2)] = "D"; //random point on right side is 'D'
    }
    //end of constructor

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public String[][] getTiles() {
        return tiles;
    }

    public void print() {
        for(int w = 0; w<width; w++) {
            for(int h = 0; h<height; h++) {
                System.out.print(tiles[w][h]);
            }
            System.out.println();
        }
    }
}
