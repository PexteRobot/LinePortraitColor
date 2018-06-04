public class Color {
    int red;
    int green;
    int blue;

    public Color(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static Color color(int red, int green, int blue){
        return new Color(red, green, blue);
    }
}
