package com.threedlite.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class ThreeDSolid extends ThreeDObject
{
   public int color = Color.GRAY;
   public boolean transparent = false;
   static double rt = Math.PI/2.0;
   protected Vector<int[]> faces = new Vector<int[]>();
   protected List<Object[]> edgeLengthList = new ArrayList<Object[]>();

   public int [] getFace(int face)
   {
      return faces.elementAt(face);
   }

   public void setFace(int [] a, int face)
   {
      faces.setElementAt(a, face);
   }

   public void addFace(int [] face)
   {
      faces.addElement(face);
   }

   public int getFaceCount()
   {
      return faces.size();
   }
   
   public void createPointConnectionMap() {
	     for (int i = 0; i < faces.size(); i++) {
	    	 int[] face = faces.get(i);
	    	 for (int j = 0; j < face.length; j++) {
	    		 ThreeDPoint a = (ThreeDPoint) vertexes.get(face[j]);
	    		 ThreeDPoint b = (ThreeDPoint) vertexes.get(face[(j == face.length-1 ? 0 : (j+1))]);
	    		 double d = a.distanceTo(b);
	    		 edgeLengthList.add(new Object[]{a,b,d});
	    	 }
	     }
   }
   
   public List<Object[]> getEdgeLengthList() {
	   return edgeLengthList;
   }
   
   public double springConstant = 1;
   

   public void joinFaces(double tolerance)             // Join faces together at edges by combining closeby vertexes // HACK efficiency for large objects - need this?
   {
      ThreeDPoint a = null;
	  ThreeDPoint b = null;
      for (int i = 0; i < getVertexCount(); i++)
      {
        for (int j = 0; j < getVertexCount(); j++)
        {
           a = getVertex(i);
           b = getVertex(j);
           if ((i != j) && (a.near(b, tolerance)))
           { 
              vertexes.removeElementAt(j);
              for (int k = 0; k < getFaceCount(); k++)  // re-reference faces
              {
                 int [] face = getFace(k);
                 for (int k1 = 0; k1 < face.length; k1++)
                 {
                    if (face[k1] == j) face[k1] = i;
                    if (face[k1] > j) face[k1] = face[k1] - 1;
                 }
              }

           }
        }
      }
   }

   public ThreeDPoint getSymmetricFaceCenter(int face)   // average vertexes to get center point
   {
      int [] theFace = getFace(face);
      ThreeDPoint ctr = new ThreeDPoint(0,0,0);  
      for (int j = 0; j < theFace.length; j++)
      {
         ctr.translate(getVertex(theFace[j]));  // translate() is a mutating method (faster than add()).
      }
      return ctr.scalarMultiply(1.0/theFace.length);
   }
   
   public ThreeDPoint getNormal(int [] face)   // Assumes face points are co-planar with at least 3 points.  
   {
      ThreeDPoint u = getVertex(face[1]).subtract(getVertex(face[0]));
      ThreeDPoint v = getVertex(face[2]).subtract(getVertex(face[0]));
      return u.crossProduct(v);
   }

   public void paint (Canvas canvas)
   {
       for (int k = 0; k < getFaceCount(); k++)
       {
         paintFace(getFace(k), canvas);
       }
   }

   public void paintFace (int [] face, Canvas canvas)
   {
      ThreeDPoint normal = null; 
      double angle;

      if (transparent)
      {
         normal = null;
         angle = 0;
      } else {
         normal = getNormal(face);
         angle = normal.angleBetween(new ThreeDPoint(0, 0, screen.viewPointZ));
      }

      if (angle < rt)               // Face is visible.
      {
         Point p;
         int [] x = new int [face.length];
         int [] y = new int [face.length];
         for (int k = 0; k < face.length; k++)
         {
            p = screen.getScreenProjection(getVertex(face[k]));
            x[k] = p.x;
            y[k] = p.y;
         }
         
         Paint paint = new Paint();
         paint.setAntiAlias(true);
         if (transparent)
         {
        	 paint.setStyle(Paint.Style.STROKE);
             paint.setColor(color);
         } else {
        	 paint.setStyle(Paint.Style.FILL);
        	 paint.setColor(screen.light(normal, color)); 
         }
         
         Path path = new Path();
         path.moveTo(x[0],y[0]);
         for (int i = 0; i < face.length;i++) path.lineTo(x[i],y[i]);
         path.lineTo(x[0], y[0]);
         canvas.drawPath(path, paint);       
         
      }

   }

}
