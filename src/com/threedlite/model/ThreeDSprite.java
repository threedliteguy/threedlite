package com.threedlite.model;

public class ThreeDSprite extends ThreeDSolid
{
   public ThreeDPoint acceleration = new ThreeDPoint(0, 0, 0);       
   public ThreeDPoint velocity = new ThreeDPoint(0, 0, 0);       
   public ThreeDPoint rotationDirection = new ThreeDPoint(0, 0, 1);   // rotation axis
   public double rotationRate = 0;                                    // radians/frame  
   
   public void move()
   {
      velocity.add(acceleration);
      translate(velocity);
      rotate(center, center.add(rotationDirection), rotationRate);
   }
}
