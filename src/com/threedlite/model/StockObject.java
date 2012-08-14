package com.threedlite.model;

public class StockObject
{

   public static ThreeDSprite TETRAHEDRON(double sideLength)
   {
      ThreeDSprite o = new ThreeDSprite();
      double s1 = Math.sqrt(3) / 6.0 * sideLength;
      double s2 = 0.5 * sideLength;
      double s3 = Math.sqrt(6) / 12.0 * sideLength;

      o.addVertex(0, 0, 3 * s3);
      o.addVertex(-s1, -s2, -s3);
      o.addVertex(2 * s1, 0, -s3);
      o.addVertex(-s1, s2, -s3);

      int [] f1 = { 0, 1, 2 };
      o.addFace(f1);
      int [] f2 = { 0, 2, 3 };
      o.addFace(f2);
      int [] f3 = { 0, 3, 1 };
      o.addFace(f3);
      int [] f4 = { 3, 2, 1 };
      o.addFace(f4);

      o.center = new ThreeDPoint(0, 0, 0);

      o.createPointConnectionMap();
      
      return o;
   }

   public static ThreeDSprite OCTAHEDRON(double sideLength)
   {
      ThreeDSprite o = new ThreeDSprite();
      double s = Math.sqrt(2) / 2.0 * sideLength;

      o.addVertex(s, 0, 0);
      o.addVertex(0, s, 0);
      o.addVertex(0, 0, s);
      o.addVertex(-s, 0, 0);
      o.addVertex(0, -s, 0);
      o.addVertex(0, 0, -s);

      int [] f1 = { 0, 1, 2 };
      o.addFace(f1);
      int [] f2 = { 0, 2, 4 };
      o.addFace(f2);
      int [] f3 = { 2, 3, 4 };
      o.addFace(f3);
      int [] f4 = { 3, 2, 1 };
      o.addFace(f4);
      int [] f5 = { 0, 5, 1 };
      o.addFace(f5);
      int [] f6 = { 0, 4, 5 };
      o.addFace(f6);
      int [] f7 = { 5, 4, 3 };
      o.addFace(f7);
      int [] f8 = { 5, 3, 1 };
      o.addFace(f8);

      o.center = new ThreeDPoint(0, 0, 0);

      o.createPointConnectionMap();
      
      return o;
   }

   public static ThreeDSprite CUBE(double sideLength)
   {
      ThreeDSprite o = new ThreeDSprite();
      double s = sideLength/2.0;

      o.addVertex(s, s, s);
      o.addVertex(-s, s, s);
      o.addVertex(-s, -s, s);
      o.addVertex(s, -s, s);
      o.addVertex(s, s, -s);
      o.addVertex(-s, s, -s);
      o.addVertex(-s, -s, -s);
      o.addVertex(s, -s, -s);

      int [] f1 = { 0, 1, 2, 3 };
      o.addFace(f1);
      int [] f2 = { 0, 4, 5, 1 };
      o.addFace(f2);
      int [] f3 = { 0, 3, 7, 4 };
      o.addFace(f3);
      int [] f4 = { 2, 6, 7, 3 };
      o.addFace(f4);
      int [] f5 = { 1, 5, 6, 2 };
      o.addFace(f5);
      int [] f6 = { 7, 6, 5, 4 };
      o.addFace(f6);

      o.center = new ThreeDPoint(0, 0, 0);

      o.createPointConnectionMap();
      
      return o;
   }

   public static double ICOSAHEDRON_SIZE = 0.95105651715;
   public static ThreeDSprite ICOSAHEDRON(double sideLength)
   {
      ThreeDSprite o = new ThreeDSprite();
      double rho = ICOSAHEDRON_SIZE * sideLength;
      double phi = ThreeDPoint.angleInRadians(63.43494876);
      double rh = ThreeDPoint.angleInRadians(36);
      double theta;
      double sigma;

      o.addVertex(0, 0, rho);
      o.addVertex(0, 0, -rho);
      for (int k = 1; k < 6; k++)
      {
        theta = k * ThreeDPoint.angleInRadians(72);
        sigma = theta + rh;
        o.addVertex(rho * Math.sin(phi) * Math.cos(theta), rho * Math.sin(phi) * Math.sin(theta), rho * Math.cos(phi));
        o.addVertex(rho * Math.sin(Math.PI - phi) * Math.cos(sigma), rho * Math.sin(Math.PI - phi) * Math.sin(sigma), rho * Math.cos(Math.PI - phi));
      }

      int [] f;
      int v;
      for (int k = 1; k < 6; k++)
      {
        v = k * 2;
        f = new int [3];
        f[0] = 0;
        f[1] = v;
        f[2] = v + 2;
        if (f[2] > 10) f[2] = 2;
        o.addFace(f);   
        f = new int [3];
        f[0] = v;
        f[1] = v + 1;
        f[2] = v + 2;
        if (f[2] > 10) f[2] = 2;
        o.addFace(f);   
        f = new int [3];
        f[0] = v + 1;
        f[1] = v + 3;
        f[2] = v + 2;
        if (f[2] > 10) f[2] = 2;
        if (f[1] > 11) f[1] = 3;
        o.addFace(f);   
        f = new int [3];
        f[0] = 1;
        f[1] = v + 3;
        f[2] = v + 1;
        if (f[1] > 11) f[1] = 3;
        o.addFace(f);   
      }

      o.center = new ThreeDPoint(0, 0, 0);

      o.createPointConnectionMap();
      
      return o;
   }

   public static double DODECAHEDRON_SIZE = 1.4012585382;
   public static ThreeDSprite DODECAHEDRON(double sideLength)
   {
      ThreeDSprite o = new ThreeDSprite();
      double rho = DODECAHEDRON_SIZE * sideLength;
      double phi = 37.37736818;
      double rh = ThreeDPoint.angleInRadians(36);
      double ofs = rh;
      double theta;
      double sigma;

      for (int j = 1; j < 3; j++)
      {
        if (j == 2) 
        {
           phi = 79.187683051;
        }
        phi = ThreeDPoint.angleInRadians(phi);
        for (int k = 1; k < 6; k++)
        {
           theta = k * ThreeDPoint.angleInRadians(72) + ofs;
           sigma = theta + rh;
           o.addVertex(rho * Math.sin(phi) * Math.cos(theta), rho * Math.sin(phi) * Math.sin(theta), rho * Math.cos(phi));
           o.addVertex(rho * Math.sin(Math.PI - phi) * Math.cos(sigma), rho * Math.sin(Math.PI - phi) * Math.sin(sigma), rho * Math.cos(Math.PI - phi));
        }
      }

      int [] f1 = { 0, 2, 4, 6, 8 };
      o.addFace(f1);
      int [] f2 = { 10, 11, 12, 2, 0 };
      o.addFace(f2);
      int [] f3 = { 13, 14, 4, 2, 12 };
      o.addFace(f3);
      int [] f4 = { 15, 16, 6, 4, 14 };
      o.addFace(f4);
      int [] f5 = { 17, 18, 8, 6, 16 };
      o.addFace(f5);
      int [] f6 = { 19, 10, 0, 8, 18 };
      o.addFace(f6);
      int [] f7 = { 9, 7, 5, 3, 1 };
      o.addFace(f7);
      int [] f8 = { 11, 10, 19, 9, 1 };
      o.addFace(f8);
      int [] f9 = { 12, 11, 1, 3, 13 };
      o.addFace(f9);
      int [] f10 = { 14, 13, 3, 5, 15 };
      o.addFace(f10);
      int [] f11 = { 16, 15, 5, 7, 17 };
      o.addFace(f11);
      int [] f12 = { 18, 17, 7, 9, 19 };
      o.addFace(f12);

      o.center = new ThreeDPoint(0, 0, 0);

      o.createPointConnectionMap();
      
      return o;
   }

   public static ThreeDSprite CUBOCTAHEDRON(double sideLength)  
   {
      ThreeDSprite o = new ThreeDSprite();
      double s = sideLength / Math.sqrt(2);

      o.addVertex(s, 0, s);
      o.addVertex(0, -s, s);
      o.addVertex(-s, 0, s);
      o.addVertex(0, s, s);
      o.addVertex(s, s, 0);
      o.addVertex(s, -s, 0);
      o.addVertex(-s, -s, 0);
      o.addVertex(-s, s, 0);
      o.addVertex(s, 0, -s);
      o.addVertex(0, -s,-s);
      o.addVertex(-s, 0, -s);
      o.addVertex(0, s, -s);

      int [] f1 = { 3, 2, 1, 0 };
      o.addFace(f1);
      int [] f2 = { 4, 3, 0 };
      o.addFace(f2);
      int [] f3 = { 5, 0, 1 };
      o.addFace(f3);
      int [] f4 = { 6, 1, 2 };
      o.addFace(f4);
      int [] f5 = { 7, 2, 3 };
      o.addFace(f5);
      int [] f6 = { 4, 0, 5, 8 };
      o.addFace(f6);
      int [] f7 = { 5, 1, 6, 9 };
      o.addFace(f7);
      int [] f8 = { 6, 2, 7, 10 };
      o.addFace(f8);
      int [] f9 = { 7, 3, 4, 11 };
      o.addFace(f9);
      int [] f10 = { 8, 5, 9 };
      o.addFace(f10);
      int [] f11 = { 9, 6, 10 };
      o.addFace(f11);
      int [] f12 = { 10, 7, 11 };
      o.addFace(f12);
      int [] f13 = { 11, 4, 8 };
      o.addFace(f13);
      int [] f14 = { 8, 9, 10, 11 };
      o.addFace(f14);

      o.center = new ThreeDPoint(0, 0, 0);

      o.createPointConnectionMap();
      
      return o;
   }

   public static ThreeDSprite RHOMBIC_DODECAHEDRON(double sideLength)  
   {
      ThreeDSprite o = new ThreeDSprite();
      double s = sideLength / Math.sqrt(3);

      o.addVertex(0, 0, 2*s);
      o.addVertex(s, s, s);
      o.addVertex(s, -s, s);
      o.addVertex(-s, -s, s);
      o.addVertex(-s, s, s);
      o.addVertex(2*s, 0, 0);
      o.addVertex(0, -2*s, 0);
      o.addVertex(-2*s, 0, 0);
      o.addVertex(0, 2*s, 0);
      o.addVertex(s, s, -s);
      o.addVertex(s, -s, -s);
      o.addVertex(-s, -s, -s);
      o.addVertex(-s, s, -s);
      o.addVertex(0, 0, -2*s);

      int [] f1 = { 2, 5, 1, 0 };
      o.addFace(f1);
      int [] f2 = { 3, 6, 2, 0 };
      o.addFace(f2);
      int [] f3 = { 4, 7, 3, 0 };
      o.addFace(f3);
      int [] f4 = { 1, 8, 4, 0 };
      o.addFace(f4);
      int [] f5 = { 5, 9, 8, 1 };
      o.addFace(f5);
      int [] f6 = { 6, 10, 5, 2 };
      o.addFace(f6);
      int [] f7 = { 7, 11, 6, 3 };
      o.addFace(f7);
      int [] f8 = { 8, 12, 7, 4 };
      o.addFace(f8);
      int [] f9 = { 9, 5, 10, 13 };
      o.addFace(f9);
      int [] f10 = { 10, 6, 11, 13 };
      o.addFace(f10);
      int [] f11 = { 11, 7, 12, 13 };
      o.addFace(f11);
      int [] f12 = { 12, 8, 9, 13 };
      o.addFace(f12);

      o.center = new ThreeDPoint(0, 0, 0);

      o.createPointConnectionMap();
      
      return o;
   }

   public static ThreeDSprite SOCCER_BALL(double sideLength)  // Truncated icosahedron/dodecahedorn
   {
      ThreeDSprite o = new ThreeDSprite();
      double factor = Math.tan(ThreeDPoint.angleInRadians(72.0/4.0));
      ThreeDSprite ico = StockObject.ICOSAHEDRON(sideLength/(factor*ICOSAHEDRON_SIZE));
      ThreeDSprite dod = StockObject.DODECAHEDRON(sideLength/(factor*DODECAHEDRON_SIZE));

      for (int i = 0; i < 20; i++)
      {
         int [] f = new int[6];
         for (int j = 0; j < 3; j++)
         {
             ThreeDPoint a = ico.getVertex(ico.getFace(i)[j]);
             ThreeDPoint b = ico.getVertex(ico.getFace(i)[(j+1)%3]);
             ThreeDPoint c = b.subtract(a).scalarMultiply(1.0/3.0);
             
             o.addVertex(a.add(c));
             f[j*2] = o.getVertexCount()-1;
             o.addVertex(a.add(c.scalarMultiply(2)));
             f[j*2+1] = o.getVertexCount()-1;
         }
         o.addFace(f);
      }
      
      for (int i = 0; i < 12; i++)
      {
         ThreeDPoint c = dod.getSymmetricFaceCenter(i);
         int [] f = new int[5];
         for (int j = 0; j < 5; j++)
         {
             ThreeDPoint a = dod.getVertex(dod.getFace(i)[j]);
             ThreeDPoint b = dod.getVertex(dod.getFace(i)[(j+1)%5]);
             ThreeDPoint d = a.add(b).add(c).scalarMultiply(1.0/3.0);
             o.addVertex(d);
             f[j] = o.getVertexCount()-1;
         }
         o.addFace(f);
      }

      o.joinFaces(sideLength / 4.0);
      
      o.center = new ThreeDPoint(0, 0, 0);

      o.createPointConnectionMap();
      
      return o;
   }
   
   public static ThreeDSprite GEODESIC(double sideLength)
   {

      ThreeDSprite o = new ThreeDSprite();
      double factor = Math.tan(ThreeDPoint.angleInRadians(72.0/4.0))*3.0/2.0;
      ThreeDSprite dod = StockObject.DODECAHEDRON(sideLength/(factor*DODECAHEDRON_SIZE));
      double delta = 1.25;

      for (int i = 0; i < 12; i++)
      {
         ThreeDPoint c = dod.getSymmetricFaceCenter(i).scalarMultiply(delta);
         for (int j = 0; j < 5; j++)
         {
             int [] f = new int[3];
             o.addVertex((ThreeDPoint)dod.getVertex(dod.getFace(i)[j]).clone());
             f[0] = o.getVertexCount()-1;
             o.addVertex((ThreeDPoint)dod.getVertex(dod.getFace(i)[(j+1)%5]).clone());
             f[1] = o.getVertexCount()-1;
             o.addVertex((ThreeDPoint)c.clone());
             f[2] = o.getVertexCount()-1;
             o.addFace(f);
         }
      }

      o.joinFaces(sideLength / 4.0);
      
      o.center = new ThreeDPoint(0, 0, 0);

      o.createPointConnectionMap();
      
      return o;
      
   }
   
}
