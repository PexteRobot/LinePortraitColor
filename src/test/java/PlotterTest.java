import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlotterTest {
    ImagingConfig imagingConfig;

    Plotter plotter;
    PlotterCommandStrategy plotterCommandStrategy;

    int color1index;
    Color color1;
    ColorPlotterCommand color1plotterCommand;

    int color2index;
    Color color2;
    ColorPlotterCommand color2plotterCommand;

    @Before
    public void setup(){
        imagingConfig = new ImagingConfig();
        imagingConfig.brushSize = 2;
        imagingConfig.maxColorAge = 2;
        imagingConfig.maxStrokeLength = 10;

        plotter = new Plotter(imagingConfig);

        plotterCommandStrategy = new SimplePlotterCommandStrategy();

        color1index = 1;
        color1 = plotter.indexedColorPalette.colors[color1index];
        color1plotterCommand = new ColorPlotterCommand(color1index);

        color2index = 2;
        color2 = plotter.indexedColorPalette.colors[color2index];
        color2plotterCommand = new ColorPlotterCommand(color2index);
    }

    @Test
    public void test1pixelImageSize11(){
        Color[][] image = buildImage(1, 1, color1, 0, 0, 1, 1);

        plotter.buildPlot(image, plotterCommandStrategy);
        ArrayList<PlotterCommand> plot = plotter.getPlot();

        assertTrue(plot.isEmpty()); // there is no way to paint 1 mixel with 2 pixel brush
    }

    @Test
    public void test2pixelImageSize22(){
        Color[][] image = buildImage(2, 2, color1, 0, 0, 2, 2);

        plotter.buildPlot(image, plotterCommandStrategy);
        ArrayList<PlotterCommand> plot = plotter.getPlot();

        assertEquals(
                Arrays.asList(
                        color1plotterCommand,
                        new PathPlotterCommand(1, 1, 1, 1)
                ),
                plot
        );
        System.out.println(plot);
    }

    @Test
    public void test2pixelImageSize22padding(){
        Color[][] image = buildImage(2, 3, color1, 0, 1, 2, 2);

        plotter.buildPlot(image, plotterCommandStrategy);
        ArrayList<PlotterCommand> plot = plotter.getPlot();

        assertEquals(
                Arrays.asList(
                        color1plotterCommand,
                        new PathPlotterCommand(2, 1, 2, 1)
                ),
                plot
        );
        System.out.println(plot);
    }

    @Test
    public void test2pixelImageSize32(){
        Color[][] image = buildImage(2, 3, color1, 0, 0, 2, 3);

        plotter.buildPlot(image, plotterCommandStrategy);
        ArrayList<PlotterCommand> plot = plotter.getPlot();

        assertEquals(
                Arrays.asList(
                        color1plotterCommand,
                        new PathPlotterCommand(1, 1, 2, 1)
                ),
                plot
        );
        System.out.println(plot);
    }

    @Test
    public void test2pixelImageSize62(){
        Color[][] image = buildImage(2, 6, color1, 0, 0, 2, 6);

        plotter.buildPlot(image, plotterCommandStrategy);
        ArrayList<PlotterCommand> plot = plotter.getPlot();

        assertEquals(
                Arrays.asList(
                        color1plotterCommand,
                        new PathPlotterCommand(1, 1, 3, 1),
                        new PathPlotterCommand(4, 1, 5, 1)
                ),
                plot
        );
        System.out.println(plot);
    }

    @Test
    public void test2pixelImageSize42colors2(){
        Color[][] image = buildImage(2, 4, color1, 0, 0, 2, 2);
        fillColor(image, color2, 0, 2, 2, 2);

        plotter.buildPlot(image, plotterCommandStrategy);
        ArrayList<PlotterCommand> plot = plotter.getPlot();

        assertEquals(
                Arrays.asList(
                        color1plotterCommand,
                        new PathPlotterCommand(1, 1, 1, 1),
                        color2plotterCommand,
                        new PathPlotterCommand(3, 1, 3, 1)
                ),
                plot
        );
        System.out.println(plot);
    }

    @Test
    public void test2pixelImageSize4(){
        imagingConfig.maxColorAge = 10;
        imagingConfig.maxStrokeLength = 10;

        Color[][] image = buildImage(2, 10, color1, 0, 2, 2, 8);

        plotter.buildPlot(image, plotterCommandStrategy);
        ArrayList<PlotterCommand> plot = plotter.getPlot();

        assertEquals(
                Arrays.asList(
                        color1plotterCommand,
                        new PathPlotterCommand(3, 1, 9, 1)
                ),
                plot
        );
        System.out.println(plot);
    }

    @Test
    public void test2pixelImageSize42(){
        imagingConfig.maxColorAge = 10;
        imagingConfig.maxStrokeLength = 1;

        Color[][] image = buildImage(2, 10, color1, 0, 2, 2, 8);

        plotter.buildPlot(image, plotterCommandStrategy);
        ArrayList<PlotterCommand> plot = plotter.getPlot();

        assertEquals(
                Arrays.asList(
                        color1plotterCommand,
                        new PathPlotterCommand(3, 1, 4, 1),
                        new PathPlotterCommand(5, 1, 7, 1),
                        new PathPlotterCommand(8, 1, 9, 1)
                ),
                plot
        );
        System.out.println(plot);
    }

    private Color[][] buildImage(int height, int width, Color color, int colorY, int colorX, int colorHeight, int colorWidth){
        Color[][] result = new Color[height][width];
        Color background = plotter.indexedColorPalette.colors[plotter.indexedColorPalette.skippedColorIndex];
        for(int y = 0; y < result.length; y++)
            for(int x = 0; x < result[y].length; x++)
                result[y][x] = background;
        fillColor(result, color, colorY, colorX, colorHeight, colorWidth);
        return result;
    }
    private void fillColor(Color[][] result, Color color, int colorY, int colorX, int colorHeight, int colorWidth){
        for(int y = colorY; y < colorY + colorHeight; y++)
            for(int x = colorX; x < colorX + colorWidth; x++)
                result[y][x] = color;
    }
}
