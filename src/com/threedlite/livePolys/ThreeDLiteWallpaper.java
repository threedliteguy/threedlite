package com.threedlite.livePolys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import com.threedlite.model.StockObject;
import com.threedlite.model.ThreeDObject;
import com.threedlite.model.ThreeDPoint;
import com.threedlite.model.ThreeDSprite;

public class ThreeDLiteWallpaper extends BaseWallpaper {


	private List<ThreeDSprite> objects = new ArrayList<ThreeDSprite>();

	boolean initialized = false;

	public ThreeDLiteWallpaper() {
	}

	protected void reinit(int disperse) {


		try {

			initialized = false;
			
			objects.clear();

			ThreeDSprite tetra = StockObject.TETRAHEDRON(100);
			ThreeDSprite oct = StockObject.OCTAHEDRON(100);
			ThreeDSprite cube = StockObject.CUBE(100);
			ThreeDSprite ico = StockObject.ICOSAHEDRON(100);
			ThreeDSprite dod = StockObject.DODECAHEDRON(100);
			ThreeDSprite cubeoct = StockObject.CUBOCTAHEDRON(100);
			ThreeDSprite rdod = StockObject.RHOMBIC_DODECAHEDRON(100);
			ThreeDSprite sb = StockObject.SOCCER_BALL(100);
			ThreeDSprite geo = StockObject.GEODESIC(100);


			objects.add(tetra); 
			objects.add(oct);   
			objects.add(cube);  
			objects.add(ico);    
			objects.add(cubeoct); 
			objects.add(rdod);   
			objects.add(dod);    
			objects.add(geo);    
			objects.add(sb);     

			for (int i = 0; i < objects.size(); i++)
			{
				ThreeDSprite object = objects.get(i);
				object.translate(new ThreeDPoint(rnd(disperse)-disperse*.5, rnd(disperse)-disperse*.5, (double)(10-i)));
				object.velocity = new ThreeDPoint(rnd(6)-3, rnd(6)-3, 0);
				object.rotationDirection = new ThreeDPoint(rnd(1), rnd(1), rnd(1));
				object.rotationRate = ThreeDPoint.angleInRadians(rnd(5));
				object.color =  Color.rgb((int)rnd(255), (int)rnd(255), (int)rnd(255));
			}


			sortObjectArray(objects);  

		} catch (Exception e) {
			Log.e("threedlite", "exception - on  create: "+e.getMessage());
		}

		initialized = true;
	}

	double rnd(double r)
	{
		return Math.random()*r;
	}

	public void sortObjectArray(List<ThreeDSprite> a)   // replace by a faster sort
	{
		Collections.sort(a, new Comparator<ThreeDObject>() {

			public int compare(ThreeDObject lhs, ThreeDObject rhs) {
				return (int)(lhs.center.z - rhs.center.z);
			}

		});
	}




	@Override
	protected void draw(Canvas canvas) {

		try {

			if (!initialized) return;

			canvas.drawColor(Color.BLACK);

			sortObjectArray(objects);  

			int maxx = canvas.getWidth()/2;
			int maxy = canvas.getHeight()/2;
			ThreeDSprite o;
			for (int k = 0; k < objects.size(); k++)
			{
				o = objects.get(k); 

				o.move();

				o.paint(canvas);

				if (o.center.x > maxx)  o.velocity.x = -Math.abs(o.velocity.x);
				if (o.center.x < -maxx) o.velocity.x = Math.abs(o.velocity.x);
				if (o.center.y > maxy)  o.velocity.y = -Math.abs(o.velocity.y);
				if (o.center.y < -maxy) o.velocity.y = Math.abs(o.velocity.y);
				if (o.center.z > 2000)  o.velocity.z = -Math.abs(o.velocity.z);
				if (o.center.z < -2000) o.velocity.z = Math.abs(o.velocity.z);
			}

		} catch (Exception e) {
			Log.e("threedlite", "exception - on  draw: "+e.getMessage());
		}
	}

}