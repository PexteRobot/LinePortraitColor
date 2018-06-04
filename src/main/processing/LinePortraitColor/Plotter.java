import java.util.ArrayList;

public class Plotter {
  final Rect canvas;
  final Palette palette;
  final Rect water;
  
  final double pixelSize;
  final double brushSize;
  final double halfBrushSize;
  final int maxColorAge;
  final IndexedColorPalette indexedColorPalette;
  
  private ArrayList<PlotterCommand> plot;
  
  Plotter(double pixelSize, double brushSize, int maxColorAge){
    this.pixelSize = pixelSize;
    this.brushSize = brushSize;
    halfBrushSize = brushSize / 2;
    this.maxColorAge = maxColorAge;
    
    indexedColorPalette = new IndexedColorPalette();
    
    canvas = new Rect(0, 0, 210, 290);
    palette = new Palette(-45 , 40, indexedColorPalette);
    water = new Rect(-100, 40, 20, 150);
  }
  void buildPlot(Color[][] image){
    System.out.println("Building plot");
    ArrayList<PlotterCommand> plot = new ArrayList();
    int[][] indexedImage = indexedColorPalette.indexColors(image);
    for(int brushColorIndex = 1; brushColorIndex < indexedColorPalette.colors.length; brushColorIndex++){
      int colorAge = 1;
      for (double y = halfBrushSize; y <= canvas.height - halfBrushSize; y+= brushSize) {
        Point2D start = null;
        double pixelY = y / pixelSize;
        if((int)pixelY >= indexedImage.length){
          break;
        }
        for (double x = halfBrushSize; x < canvas.height - halfBrushSize;) {
          double pixelX = x / pixelSize;
          double lPixelX = (x - halfBrushSize) / pixelSize;
          if( ((int)pixelX) >= indexedImage[(int)pixelY].length){
            if(start != null){
              plot.add(new PathPlotterCommand(start.x, start.y, x - halfBrushSize, y));
            }
            break;
          }
          int imageColor = indexedImage[(int)pixelY][(int)pixelX];
          int lImageColor = indexedImage[(int)pixelY][(int)lPixelX];
          if(imageColor == brushColorIndex){
            if(start == null) {
              plot.add(new ColorPlotterCommand(brushColorIndex));
              start = new Point2D(x, y);
              x++;
              colorAge = 1;
            }
            else if (colorAge >= maxColorAge){
              colorAge = 0;
              plot.add(new PathPlotterCommand(start.x, start.y, x, y));
              start = new Point2D(x + halfBrushSize, y);
              x += brushSize;
            }
            else {
              colorAge++;
              x++;
            }
          }
          else if (start != null) {
            if (colorAge > maxColorAge){
              plot.add(new ColorPlotterCommand(brushColorIndex));
              colorAge = 0;
            }
            plot.add(new PathPlotterCommand(start.x, start.y, x - halfBrushSize, y));
            x += brushSize;
            start = null;
          }
          else {
            x++;
            colorAge++;
          }
        }
      }
    }
    
    this.plot = plot;
  }
  ArrayList<PlotterCommand> getPlot(){
    if(plot == null)
      throw new IllegalStateException("Plot not built");
    return plot;
  }
}
