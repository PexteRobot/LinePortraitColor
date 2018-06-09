import java.util.ArrayList;

public class Plotter {
  final Rect canvas;
  final Palette palette;
  final Rect water;

  final double pixelSize;
  final ImagingConfig imagingConfig;
  final IndexedColorPalette indexedColorPalette;

  private ArrayList<PlotterCommand> plot;
  
  Plotter(double pixelSize, ImagingConfig imagingConfig){
    this.pixelSize = pixelSize;
    this.imagingConfig = imagingConfig;
    
    indexedColorPalette = new IndexedColorPalette();
    
    canvas = new Rect(0, 0, 210, 290);
    palette = new Palette(-45 , 40, indexedColorPalette);
    water = new Rect(-100, 40, 20, 150);
  }
  void buildPlot(Color[][] image, PlotterCommandStrategy plotterCommandStrategy){
    this.plot = plotterCommandStrategy.generatePlotterCommands(image, pixelSize, imagingConfig, canvas, indexedColorPalette);
  }
  ArrayList<PlotterCommand> getPlot(){
    if(plot == null)
      throw new IllegalStateException("Plot not built");
    return plot;
  }
}
