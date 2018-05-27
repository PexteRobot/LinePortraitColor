class Plotter {
  final Rect canvas;
  final Palette palette;
  final Rect water;
  
  final int imagePixelStepY;
  final int maxColorAge;
  final IndexedColorPalette indexedColorPalette;
  final double brushSize = 2;
  final double halfBrushSize = brushSize / 2;
  
  private ArrayList<PlotterCommand> plot;
  
  Plotter(int imagePixelStepY, int maxColorAge){
    this.imagePixelStepY = imagePixelStepY;
    this.maxColorAge = maxColorAge;
    
    indexedColorPalette = new IndexedColorPalette();
    
    canvas = new Rect(0, 0, 210, 290);
    palette = new Palette(-45 , 40, indexedColorPalette);
    water = new Rect(-100, 40, 20, 150);
  }
  void buildPlot(PImage image){
    ArrayList<PlotterCommand> plot = new ArrayList();
    int[][] indexedImage = indexedColorPalette.indexColors(image);
    for(int brushColorIndex = 0; brushColorIndex < indexedColorPalette.colors.length; brushColorIndex++){
      int colorAge = maxColorAge + 1;
      for (int y = 0; y < indexedImage.length; y+= imagePixelStepY) {
        Point2D start = null;
        for (int x = 0; x < indexedImage[y].length; x++) {
          int imageColor = indexedImage[y][x];
          if(imageColor == brushColorIndex){
            if(start == null) {
              start = new Point2D(x, y);
            }
            else if (colorAge > maxColorAge){
              if (brushColorIndex >= 0){
                plot.add(new ColorPlotterCommand(brushColorIndex));
              }
              colorAge = 0;
              plot.add(new PathPlotterCommand(start.x + halfBrushSize, start.y, x - halfBrushSize, y));
              start = new Point2D(x, y);
            }
            colorAge++;
          }
          else if (start != null) {
            if (colorAge > maxColorAge){
              plot.add(new ColorPlotterCommand(brushColorIndex));
              colorAge = 0;
            }
            plot.add(new PathPlotterCommand(start.x + halfBrushSize, start.y, x - halfBrushSize, y));
            start = null;
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
