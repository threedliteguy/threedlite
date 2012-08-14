package com.threedlite.model;

import java.util.Vector;

import android.graphics.Canvas;

public abstract class ThreeDObject
{
    
   public int id = 0;                              // An optional id number.
   public ViewScreen screen = new ViewScreen();            // Lighting and projection.
   public ThreeDPoint center = new ThreeDPoint(0, 0, 0);  // A reference point to use with a vertex for rotation or distance calculations.
   protected Vector vertexes = new Vector();       // A list of points that make up the object's vertexes.
       
   public ThreeDObject()
   {
   }

   public ThreeDPoint getVertex(int vertexNumber)
   {
      return (ThreeDPoint)vertexes.elementAt(vertexNumber);
   }

   public void setVertex(ThreeDPoint a, int vertexNumber)
   {
      vertexes.setElementAt(a, vertexNumber);
   }

   public void addVertex(ThreeDPoint point)
   {
      vertexes.addElement(point);
   }

   public void addVertex(double x, double y, double z)   
   {
      addVertex(new ThreeDPoint(x, y, z));
   }

   public int getVertexCount()
   {
      return vertexes.size();
   }

   public void translate(ThreeDPoint direction)
   {
      for (int k = 0; k < getVertexCount(); k++)
      {
         getVertex(k).translate(direction);
      }
      center.translate(direction);
   }

   public void rotate(ThreeDPoint axisPoint1, ThreeDPoint axisPoint2, double angle)  // The two axisPoints determine the axis of rotation
   {
      for (int k = 0; k < getVertexCount(); k++)
      {
         getVertex(k).rotate(axisPoint1, axisPoint2, angle);
      }
      center.rotate(axisPoint1, axisPoint2, angle);
   }
   
   public void moveLocal() {
	  // Sorry, this does not account for centrifugal or corialis forces...
	  for (int k = 0; k < getVertexCount(); k++)
      {
         getVertex(k).move();
      }
   }

   public abstract void paint(Canvas canvas);

   // Replace with quicksort
   public static void sortObjectArray(ThreeDObject a[])
   {
       int c;
       do
       {
           c = 0;
           for(int k = 0; k < a.length - 1; k++)
               if(a[k].center.z > a[k + 1].center.z)
               {
                   ThreeDObject o = a[k];
                   a[k] = a[k + 1];
                   a[k + 1] = o;
                   c++;
               }
        }
       while(c != 0);
   }
}
