package com.threedlite.model;

public class ThreeDPoint                  // Used for points, vertexes or vectors.  Includes some useful vector math.
{
	
   public double x = 0;            // I chose not to make ThreeDPoint "immutable" due to the overhead involved in creating a new instance each rotation and translation - so be aware of variable aliasing.
   public double y = 0;
   public double z = 0;
   public double dx = 0;           // The comment above was made many years ago...  I'm adding optional velocity now as a "particle" property.
   public double dy = 0;
   public double dz = 0;
    
   public ThreeDPoint(double x, double y, double z)
   {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public ThreeDPoint(ThreeDPoint a)
   {
      x = a.x;
      y = a.y;
      z = a.z;
   }

   public Object clone()
   {
      return new ThreeDPoint(x, y, z);
   }

   public double getLength()
   {
      return Math.sqrt(x * x + y * y + z * z);
   }

   public double distanceTo(ThreeDPoint a)
   {
      return subtract(a).getLength();
   }

   public boolean near(ThreeDPoint a, double tolerance)
   {
      return (Math.abs(a.x - x) <= tolerance) && (Math.abs(a.y - y) <= tolerance) && (Math.abs(a.z - z) <= tolerance);
   }

   public double angleBetween(ThreeDPoint a)
   {
      double result;
      try
      {
         result = Math.acos(dotProduct(a) / (getLength() * a.getLength()));
      }
      catch (Exception e) 
      {  
         result = 0;               
      }
      return result;
   }

   public ThreeDPoint inverse()
   {
      return new ThreeDPoint(-x, -y, -z);
   }
   
   public ThreeDPoint reflectZ()     // To convert to a left handed coordinate system.
   {
      return new ThreeDPoint(x, y, -z);
   }

   public ThreeDPoint add(ThreeDPoint a)
   {
      return new ThreeDPoint(x + a.x, y + a.y, z + a.z);
   }

   public ThreeDPoint subtract(ThreeDPoint a)
   {
      return new ThreeDPoint(x - a.x, y - a.y, z - a.z);
   }

   public ThreeDPoint scalarMultiply(double s)
   {
      return new ThreeDPoint(s*x, s*y, s*z);
   }

   public double dotProduct(ThreeDPoint a)
   {
      return x * a.x + y * a.y + z * a.z;
   }

   public ThreeDPoint crossProduct(ThreeDPoint a)
   {
      return new ThreeDPoint(y * a.z - z * a.y, z * a.x - x * a.z, x * a.y - y * a.x);
   }

   public void translate(ThreeDPoint a)
   {
      x = x + a.x;
      y = y + a.y;
      z = z + a.z;
   }

   public void rotate(ThreeDPoint axisPoint1, ThreeDPoint axisPoint2, double angle)  // sense of rotation is right handed about P1 to P2
   {

      double r = axisPoint1.distanceTo(axisPoint2);
      if ((r == 0) || (angle == 0)) return;          // no angle or bad vector

      double a = (axisPoint2.x - axisPoint1.x) / r;  // x direction cosine of rotation
      double b = (axisPoint2.y - axisPoint1.y) / r;  // y direction cosine of rotation
      double c = (axisPoint2.z - axisPoint1.z) / r;  // z direction cosine of rotation
      double a2 = a*a;
      double b2 = b*b;
      double c2 = c*c;
      double cosa = Math.cos(angle);
      double sina = Math.sin(angle);

      double x1 = x - axisPoint1.x;  // New variable x1 to avoid aliasing problems (e.g. if this == axisPoint1).
      double y1 = y - axisPoint1.y;
      double z1 = z - axisPoint1.z;

      double x2 = (a2+(1-a2)*cosa)*x1 + (a*b*(1-cosa)-c*sina)*y1 + (a*c*(1-cosa)+b*sina)*z1 + axisPoint1.x;
      double y2 = (a*b*(1-cosa)+c*sina)*x1 + (b2+(1-b2)*cosa)*y1 + (b*c*(1-cosa)-a*sina)*z1 + axisPoint1.y;
      double z2 = (a*c*(1-cosa)-b*sina)*x1 + (b*c*(1-cosa)+a*sina)*y1 + (c2+(1-c2)*cosa)*z1 + axisPoint1.z;

      x = x2;     
      y = y2;
      z = z2;

   }

   public static double angleInRadians(double angleInDegrees)   
   {
      return angleInDegrees * Math.PI/180.0;
   }
   
   public String toString()
   {
      return "(" + Double.toString(x) + ", " + Double.toString(y) + ", " + Double.toString(z) + ")";
   }
   
   public void move() {
	   x = x + dx;
	   y = y + dy;
	   z = z + dz;
   }

   public void accelerate(ThreeDPoint f) {
	   dx = dx + f.x;
	   dy = dy + f.y;
	   dz = dz + f.z;
   }

   
}

