class IndexedColorPalette {
  public final Color[] colors;
  public int skippedColorIndex;
  
  IndexedColorPalette() {
    skippedColorIndex = 0;
    colors = new Color[]{
      new Color(0xff, 0xff, 0xff),
            new Color(0x86, 0xa8, 0x3b),
            new Color(0xb3, 0x9a, 0x10),
            new Color(0x89, 0x7e, 0x15),
            new Color(0xc1, 0x83, 0x3b),
            new Color(0xca, 0x46, 0x30),
            new Color(0x87, 0x30, 0x47),
            new Color(0xac, 0x36, 0x48),
            new Color(0x87, 0x3d, 0x21),
            new Color(0x96, 0x5c, 0x1f),
            new Color(0x89, 0x7d, 0x1c),
            new Color(0x31, 0x33, 0x3d),
            new Color(0x6e, 0x7d, 0x9a)
      
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
  
  int[][] indexColors(Color[][] img) {
    int[][] result = new int[img.length][img[0].length];
    for(int i = 0; i < result.length; i++){
      for(int j = 0; j < result[i].length; j++){
        result[i][j] = findClosestColorIndex(img[i][j]);
      }
    }
    return result;
  }
  
  int findClosestColorIndex(Color c) {
    double minDistance = Double.MAX_VALUE;
    int result = -1;
    for(int i = 0; i < colors.length; i++) {
      Color paletteColor = colors[i];
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
  
  double calculateColorDistance(Color c1, Color c2){
    //return Math.abs(c1.red - c2.red) + Math.abs(c1.green - c2.green) + Math.abs(c1.blue - c2.blue);
    return Math.pow(c1.red - c2.red, 2) + Math.pow(c1.green - c2.green, 2) + Math.pow(c1.blue - c2.blue, 2);
  }
  
  Color getColorByIndex(int index) {
    return colors[index];
  }
}
