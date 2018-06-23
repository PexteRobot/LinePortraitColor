import java.util.ArrayList;

/**
 * @author Andrei Osipenko
 */
public class SimplePlotterCommandStrategy implements PlotterCommandStrategy {
    @Override
    public ArrayList<PlotterCommand> generatePlotterCommands(Color[][] image, ImagingConfig imagingConfig, Rect canvas, IndexedColorPalette indexedColorPalette) {
        double brushSize = imagingConfig.brushSize;
        double maxColorAge = imagingConfig.maxColorAge;
        final double halfBrushSize = brushSize / 2;
        System.out.println("Building plot");
        ArrayList<PlotterCommand> plot = new ArrayList();
        int[][] indexedImage = indexedColorPalette.indexColors(image);
        for(int brushColorIndex = 1; brushColorIndex < indexedColorPalette.colors.length; brushColorIndex++){
            int colorAge = 0;
            int strokeLength = 0;
            for (double y = halfBrushSize; y <= canvas.height - halfBrushSize; y+= brushSize) {
                Point2D start = null;
                double pixelY = y;
                if((int)pixelY >= indexedImage.length){
                    break;
                }
                for (double x = halfBrushSize; x < canvas.width - halfBrushSize;) {
                    double pixelX = x;
                    double lPixelX = (x - halfBrushSize);
                    if( ((int)pixelX) >= indexedImage[(int)pixelY].length){
                        if(start != null){
                            plot.add(new PathPlotterCommand(start.x, start.y, x - halfBrushSize, y));
                        }
                        break;
                    }

                    int imageColor = indexedImage[(int)pixelY][(int)pixelX];
                    int lImageColor = indexedImage[(int)pixelY][(int)lPixelX];

                    double rPixelX = (x + halfBrushSize);
                    int rImageColor = ((int)rPixelX < indexedImage[(int)pixelY].length) ? indexedImage[(int)pixelY][(int)rPixelX] : -1;
                    if(imageColor == brushColorIndex){
                        if(start == null) {
                            if(!(lImageColor != imageColor && rImageColor == imageColor)) {
                                if (colorAge == 0) {
                                    System.out.println("age=" + colorAge);
                                    plot.add(new ColorPlotterCommand(brushColorIndex));
                                    colorAge = 0;
                                }
                                start = new Point2D(x, y);
                                colorAge++;
                                strokeLength++;
                            }
                            x++;
                        }
                        else if (strokeLength >= imagingConfig.maxStrokeLength){
                            strokeLength = 0;
                            plot.add(new PathPlotterCommand(start.x, start.y, x, y));
                            start = new Point2D(x + halfBrushSize, y);
                            colorAge++;
                            x += brushSize;
                        }
                        else if (colorAge >= maxColorAge){
                            colorAge = 0;
                            strokeLength = 0;
                            plot.add(new ColorPlotterCommand(brushColorIndex));
                            plot.add(new PathPlotterCommand(start.x, start.y, x, y));
                            start = new Point2D(x + halfBrushSize, y);
                            x += brushSize;
                        }
                        else {
                            colorAge++;
                            strokeLength++;
                            x++;
                        }
                    }
                    else if (start != null) {
                        if (colorAge > maxColorAge){
                            plot.add(new ColorPlotterCommand(brushColorIndex));
                            colorAge = 0;
//                            strokeLength = 0;
                        }
                        plot.add(new PathPlotterCommand(start.x, start.y, x - halfBrushSize, y));
                        colorAge += (int)(x - start.x) + 1;
                        x += brushSize;
                        strokeLength = 0;
                        start = null;
                    }
                    else {
                        x++;
//                        colorAge++;
//                        strokeLength++;
                    }
                }
            }
        }
        return plot;
    }
}
