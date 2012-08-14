package com.threedlite.model;

import android.graphics.Color;
import android.graphics.Point;

public class ViewScreen
{
   
   public double viewPointZ = 5000;
   public double screenPlaneZ = 1000;
   public Point dimensions = new Point(800, 600);
   public double ambientLight = .1;
   public Light [] lights = { new Light(new ThreeDPoint(-1000, 1000, 1000), 1) };
   static double invPi = 1.0/Math.PI;
   
   public int light(ThreeDPoint normal, int color)  // Shading
   {
      double percent = ambientLight;
      for (int k = 0; k < lights.length; k++)
      {
          percent += (Math.PI - lights[k].source.angleBetween(normal)) * invPi * lights[k].intensity;
      }
      if (percent > 1) percent = 1;

      float [] hsv = new float[3];
      Color.colorToHSV(color,hsv);
      hsv[2] = (float)percent;

      return Color.HSVToColor(hsv);
   }

   // Perspective view with origin at screen center: X+ is RIGHT, Y+ is UP, Z+ is out of screen.
   public Point getScreenProjection(ThreeDPoint a)
   { 
      double f = (viewPointZ - screenPlaneZ) / (viewPointZ - a.z);
      return new Point((int)(a.x * f + dimensions.x * .5), (int)(dimensions.y * .5 - a.y * f));
   }
}
