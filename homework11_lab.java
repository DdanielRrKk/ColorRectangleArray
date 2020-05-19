package dom11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class lab11 {

	public static void main(String[] args) {
		String filename="D:/test/rects.txt";
		RectangleCollection rc=new RectangleCollection(filename);
		rc.addRectangle(new ColorRectangle(2,2,400,400,255));
		rc.printColl();
		
		System.out.println("Show Big Rectangle: "+rc.calcUnionColl());
		System.out.println("Show Min: "+rc.calcMin());
		System.out.println("Show Max: "+rc.calcMax());
		System.out.println("Show Nested: "+rc.calcNested());
		System.out.println("Show Reverse List: "+rc.reverseList());
		System.out.println("Show Total Area: "+rc.calcSumArea());
		System.out.println("Show Total Perimeter: "+rc.calcSumPerimeter());
		System.out.println("Show Size: "+rc.calcSize());
	}
	
	

}

class RectangleCollection {
	ColorRectangle cr=new ColorRectangle();
	private SortedSet<ColorRectangle> ts=new TreeSet<ColorRectangle>();
	
	RectangleCollection(){}
	RectangleCollection(String filename){
		BufferedReader reader;
		try{
			reader=new BufferedReader(new FileReader(filename));
			String line=reader.readLine();
			int i=0;
			while(i!=4) {
				String splitLine[]=line.split(" ");
				int x1=Integer.parseInt(splitLine[0]);
				int y1=Integer.parseInt(splitLine[1]);
				int x2=Integer.parseInt(splitLine[2]);
				int y2=Integer.parseInt(splitLine[3]);
				long c=Long.parseLong(splitLine[4]);
				ColorRectangle rec=new ColorRectangle(x1,y1,x2,y2,c);
				ts.add(rec);
				i++;
				line=reader.readLine();
			}
			reader.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addRectangle(ColorRectangle rec) {ts.add(rec);}
	
	public ColorRectangle calcUnionColl() {
		ColorRectangle rec=ts.first();
		int xx1=rec.getX1(), xx2=rec.getX2(), yy1=rec.getY1(), yy2=rec.getY2();
		for(ColorRectangle it : ts) {
			if(xx1<it.getX1()) {xx1=it.getX1();}
			if(xx2<it.getX2()) {xx2=it.getX2();}
			if(yy1<it.getY1()) {yy1=it.getY1();}
			if(yy2<it.getY2()) {yy2=it.getY2();}
		}
		ColorRectangle bigr=new ColorRectangle(xx1,xx2,yy1,yy2,0);
		return bigr;
	}
	
	public ColorRectangle calcMin() {
		ColorRectangle temp=ts.first();
		for(ColorRectangle it : ts) {
			if(temp.calcArea() > it.calcArea()) {temp=it;}
		}
		return temp;
	}
	
	public ColorRectangle calcMax() {
		ColorRectangle temp=ts.first();
		for(ColorRectangle it : ts) {
			if(temp.calcArea() < it.calcArea()) {temp=it;}
		}
		return temp;
	}
	
	public int calcNested() {
		int count=0;
		for(ColorRectangle colr1 : ts) {
			for(ColorRectangle colr2 : ts) {
				if(colr1.isInside(colr2)) {
					count++;
				}
			}
		}
		return count;
	}
	
	public int calcSize() {return ts.size();}
	
	public void printColl() {System.out.println("TreeSet: "+ts);}
	
	public double calcSumArea() {
		double res=0;
		for(ColorRectangle it : ts) {
			res=res+it.calcArea();
		}
		return res;
	}
	
	public double calcSumPerimeter() {
		double oneP=0, totalP=0;
		for(ColorRectangle it : ts) {
			oneP=it.getX1()+it.getX2()+it.getY1()+it.getY2();
			totalP=totalP+oneP;
		}
		return totalP;
	}
	
	public List reverseList() {
		List<ColorRectangle> lcr=new ArrayList<ColorRectangle>(ts);
		Collections.reverse(lcr);
		return lcr;
	}
	
	public boolean findRect(ColorRectangle toFind) {return (ts.contains(toFind));}
}

class Color{
	private long red, green, blue, rgb;
	
	private void setCombinedColor() {rgb = (256*256*red)+(256*256*green)+(256*256*blue);}
	public long getCC() { return rgb; }
	
	public Color() {red=0; green=0; blue=0; rgb=(256*256*red)+(256*256*green)+(256*256*blue);}
	public Color(long c) { blue=c; green=c; red=c; rgb=(256*256*red)+(256*256*green)+(256*256*blue);}
	
	public void setRed(long r) { red=r; }
	public void setGreen(long g) { green=g; }
	public void setBlue(long b) { blue=b; }
	
	public long getRed() { return red; }
	public long getGreen() { return green; }
	public long getBlue() { return blue; }
	
	public boolean equals(Object r) {
		if (this == r) return true;
		if (r==null||r.getClass()!=this.getClass()) return false;	
		Color color = (Color) r;
		return (color.red==this.red && color.green==this.green && color.blue==this.blue && color.rgb == this.rgb);
	}
	
	public String toString() {return (" RGB:"+rgb);}
}

class ColorRectangle extends Color implements Comparable<ColorRectangle>{
	private int iX1, iX2, iY1, iY2; Color col = new Color();
	
	public ColorRectangle() {iX1=0; iX2=0; iY1=0; iY2=0;}
	public ColorRectangle(int x1,int y1,int x2,int y2,long c) {super(c);iX1=x1; iX2=x2; iY1=y1; iY2=y2;}
	
	public void setX1(int i) {iX1=i;}
	public void setX2(int i) {iX2=i;}
	public void setY1(int i) {iY1=i;}
	public void setY2(int i) {iY2=i;}
	public void setColor(Color c) {col=c;}
	
	public int getX1() {return iX1;}
	public int getX2() {return iX2;}
	public int getY1() {return iY1;}
	public int getY2() {return iY2;}
	
	public int calcArea() {return ((Math.max(this.iX1, this.iX2)-Math.min(this.iX1, this.iX2))*(Math.max(this.iY1, this.iY2)-Math.min(this.iY1, this.iY2)));}
	
	@Override
	public String toString() { return "x1: "+iX1+" x2: "+iX2+" y1: "+iY1+" y2: "+iY2+" color: "+this.getCC();}
	
	public boolean equals(ColorRectangle r) {
        if(!(r instanceof ColorRectangle)) {
            return false;
        }
        ColorRectangle rect = r;
        return(this.calcArea()==rect.calcArea()&&this.getRed()==rect.getRed()&&this.getGreen()==rect.getGreen()&&this.getBlue()==rect.getBlue());
    }
	public void translateX(int iPoints) {
        this.iX1+=iPoints;
        this.iX2+=iPoints;
    }
    public void translateY(int iPoints) {
        this.iY1+=iPoints;
        this.iY2+=iPoints;
    }
    public void translate(int iPoints) {
        this.translateX(iPoints);
        this.translateY(iPoints);
    }
    
	public boolean isInside(ColorRectangle rec) { return(rec.getX1() > iX1 && rec.getX2() < iX2 &&  rec.getY1() > iY1 && rec.getY2() < iY2)?true:false; }	
	
	public ColorRectangle unionRect(ColorRectangle r) {
	    int x1= Math.min(Math.min(this.iX1,this.iX2),Math.min(r.getX1(), r.getX2()));
	    int x2= Math.max(Math.min(this.iX1,this.iX2),Math.max(r.getX1(), r.getX2()));
	    int y1= Math.min(Math.min(this.iY1,this.iY2),Math.min(r.getY1(), r.getY2()));
	    int y2= Math.max(Math.min(this.iY1,this.iY2),Math.max(r.getY1(), r.getY2()));
	    long RGBmix = 256*256*this.getRed()+256*this.getGreen()+1*this.getBlue();
	    return new ColorRectangle (x1,x2,y1,y2,RGBmix);
	}

	public ColorRectangle intersectionRect(ColorRectangle r) {
	    int x1=Math.min(this.iX1, r.iX2);
	    int x2=Math.max(this.iX1, r.iX2);
	    int y1=Math.min(this.iY1, r.iY2);
	    int y2=Math.max(this.iY1, r.iY2);
	    long RGBmix = 256*256*this.getRed()+256*this.getGreen()+1*this.getBlue();
	    return new ColorRectangle (x1,x2,y1,y2,RGBmix);
    }
	@Override
	public int compareTo(ColorRectangle r) {
		return Double.compare(this.calcArea(),r.calcArea());
	}
	
}
