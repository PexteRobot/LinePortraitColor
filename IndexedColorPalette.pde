class IndexedColorPalette {
  private final color[] colors;
  private int skippedColorIndex;
  
  IndexedColorPalette() {
    skippedColorIndex = 0;
    colors = new color[]{
      color(0xff, 0xff, 0xff),
      color(0x86, 0xa8, 0x3b),
      color(0xb3, 0x9a, 0x10),
      color(0x89, 0x7e, 0x15),
      color(0xc1, 0x83, 0x3b),
      color(0xca, 0x46, 0x30),
      color(0x87, 0x30, 0x47),
      color(0xac, 0x36, 0x48),
      color(0x87, 0x3d, 0x21),
      color(0x96, 0x5c, 0x1f),
      color(0x89, 0x7d, 0x1c),
      color(0x31, 0x33, 0x3d),
      color(0x6e, 0x7d, 0x9a)
      
      //color(0xd4, 0xc7, 0x01),
      //color(0xbe, 0x54, 0x00),
      //// color(0xe6, 0xe4, 0xe4),
      //color(0xff, 0xff, 0xff),
      //color(0xfc, 0xb2, 0x00),
      //color(0xe2, 0x01, 0x01),
      //color(0xda, 0x5c, 0x01),
      //color(0x78, 0x01, 0x00),
      //color(0xbe, 0x00, 0x00),
      //color(0x01, 0x47, 0x4b),
      //color(0x00, 0x65, 0x1e),
      //color(0x21, 0x15, 0x4d),
      //color(0x01, 0x27, 0x66),
      //color(0x26, 0x15, 0x12),
      //color(0x09, 0x0a, 0x0f),
      //color(0x17, 0x20, 0x0f),
      //color(0x64, 0x00, 0x46),
      //color(0x19, 0x13, 0x1d),
      //color(0x83, 0x01, 0x32),
    };
  }
  
  int[][] indexColors(PImage img) {
    int[][] result = new int[img.height][img.width];
    for(int i = 0; i < result.length; i++){
      for(int j = 0; j < result[i].length; j++){
        result[i][j] = findClosestColorIndex(img.pixels[i * img.width + j]);
      }
    }
    return result;
  }
  
  int findClosestColorIndex(color c) {
    double minDistance = Double.MAX_VALUE;
    int result = -1;
    for(int i = 0; i < colors.length; i++) {
      color paletteColor = colors[i];
      double distance = calculateColorDistance(c, paletteColor);
      if(distance < minDistance) {
        minDistance = distance;
        result = i;
      }
    }
    if(result == skippedColorIndex)
      return -1;
    return result;
  }
  
  double calculateColorDistance(color c1, color c2){
    return Math.abs(red(c1) - red(c2)) + Math.abs(green(c1) - green(c2)) + Math.abs(blue(c1) - blue(c2));
    //return Math.pow(red(c1) - red(c2), 2) + Math.pow(green(c1) - green(c2), 2) + Math.pow(blue(c1) - blue(c2), 2);
  }
  
  color getColorByIndex(int index) {
    return colors[index];
  }
}
