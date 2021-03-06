import java.util.Random;
import java.io.Writer;
import java.io.FileWriter;
import java.io.BufferedWriter;

// version: 0.1

PImage img;
int steps = 5;
float threshold = 1.0; 
PlotterVisualizer plotterVisualizer;
void setup() {
//  size (210,290);
//  img = loadImage("monl.jpg");

  //size (600, 600);
  //img = loadImage("dali.jpg");

  size (600, 600);
  background(255);
  // img = loadImage("palitra12f.jpg");
   //img = loadImage("palitra12f.png");
  //img = loadImage("GR24.png");
  //img = loadImage("agatha.png");
  //img = loadImage("natash-aqua.png");
  img = loadImage("natash-aqua-sharp.png");

  ImagingConfig imagingConfig = new ImagingConfig();
  imagingConfig.brushSize = 1;
  imagingConfig.maxColorAge = 100;
  imagingConfig.maxStrokeLength = 100;
  Plotter plotter = new Plotter(imagingConfig);

  PlotterCommandStrategy plotterCommandStrategy = new SimplePlotterCommandStrategy();

  plotter.buildPlot(convertImage(img), plotterCommandStrategy);
  plotterVisualizer = new PlotterVisualizer(plotter);
  new GCodeExporter(plotter, 210, 290).export(new File("g-codes.txt"));
}
void draw() {
  plotterVisualizer.draw();
}
Color[][] convertImage(PImage image){
  Color[][] result = new Color[image.height][];
  for(int y = 0; y < image.height; y++){
    result[y] = new Color[image.width];
    for(int x = 0; x < image.width; x++){
      color c = image.get(x, y);
      result[y][x] = new Color((int)red(c), (int)green(c), (int)blue(c));
    }
  }
  return result;
}
