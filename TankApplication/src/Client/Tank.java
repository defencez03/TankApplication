package Client;


public class Tank {
    private String filePath;
    private Integer[] position = new Integer[2];
    private String direction;
    private String color;
    private String win;

    public Tank(int x, int y, String path, String dir) {
        // Инициализация точек для танка
        position[0] = x;
        position[1] = y;
        filePath = path;
        direction = dir;
        win = "l";
    }

    public void setFilePath(String path) { filePath = path; }
    public Integer getX() { return position[0]; }
    public Integer getY() { return position[1]; }
    public void setX(Integer num) { position[0] = num; }
    public void setY(Integer num) { position[1] = num; }
    public String getDir() { return direction; }
    public void setDir(String dir) { direction = dir; }
    public void setWin(String w) { win = w; }
    public String getPath() { return filePath; }
    public String getWin() { return win; }

    // Движение вверх
    public void Up() {
        position[1] -= 20;
        direction = "up";
    }

    // Движение вниз
    public void Down() {
        position[1] += 20;
        direction = "down";
    }

    // Движение влево
    public void Left() {
        position[0] -= 20;
        direction = "left";
    }

    // Движение вправо
    public void Right() {
        position[0] += 20;
        direction = "right";
    }

    // Выстрел
    public Integer[] Fire() {
        Integer[] pos = new Integer[2];

        switch (direction) {
            case "up":
                pos[0] = position[0] + 50;
                pos[1] = position[1];
                break;

            case "down":
                pos[0] = position[0] + 50;
                pos[1] = position[1] + 100;
                break;

            case "left":
                pos[0] = position[0];
                pos[1] = position[1] + 50;
                break;

            case "right":
                pos[0] = position[0] + 100;
                pos[1] = position[1] + 50;
                break;

            default: break;
        }

        return pos;
    }
}
