import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Plotter {
  final Rect canvas;
  final Palette palette;
  final Rect water;

  final ImagingConfig imagingConfig;
  final IndexedColorPalette indexedColorPalette;

  private ArrayList<PlotterCommand> plot;
  
  Plotter(ImagingConfig imagingConfig){
    this.imagingConfig = imagingConfig;
    
    indexedColorPalette = new IndexedColorPalette();
    
    canvas = new Rect(0, 0, 210, 290);
    palette = new Palette(-45 , 40, indexedColorPalette);
    water = new Rect(-100, 40, 20, 150);
  }
  void buildPlot(Color[][] image, PlotterCommandStrategy plotterCommandStrategy){
    this.plot = plotterCommandStrategy.generatePlotterCommands(image, imagingConfig, canvas, indexedColorPalette);
    exportCommands(new File("color-commands-debug.txt"));
  }
  ArrayList<PlotterCommand> getPlot(){
    if(plot == null)
      throw new IllegalStateException("Plot not built");
    return plot;
  }

  private void exportCommands(File file){
    try(FileWriter fileWriter = new FileWriter(file); BufferedWriter writer = new BufferedWriter(fileWriter)){
      for(PlotterCommand plotterCommand : this.plot){
        writer.append(plotterCommand.toString());
        writer.newLine();
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
