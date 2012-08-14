package com.threedlite.model;

public class Light
{
   public ThreeDPoint source = new ThreeDPoint(0, 0, 0);
   public double intensity = .2;

   Light(ThreeDPoint source, double intensity)
   {
      this.source = source;
      this.intensity = intensity;
   }
}

