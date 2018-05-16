/**
 * 
 */

/**
 * @author Moussa
 *
 */
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main {

	public static int negative(int p)
	{
        int a = (p>>24)&0xff;
        int r = (p>>16)&0xff;
        int g = (p>>8)&0xff;
        int b = p&0xff;

        //subtract RGB from 255
        r = 255 - r;
        g = 255 - g;
        b = 255 - b;

        //set new RGB value
        p = (a<<24) | (r<<16) | (g<<8) | b;
        
        return p;
	}
	
	public static int average(int p)
	{
		int a = (p>>24) & 0xff;
        int r = (p>>16) & 0xff;
        int g = (p>>8) & 0xff;
        int b = p & 0xff;

        // calculate average
        int avg = (r+g+b)/3;
        
        return avg;
	}
	
	
	public static void GrayScale() {

		try {
			File input = new File("image.jpg");
			BufferedImage image = ImageIO.read(input);
			int width = image.getWidth();
			int height = image.getHeight();

			for (int i = 0; i < height; i++) {

				for (int j = 0; j < width; j++) {

					int p = negative(image.getRGB(j, i));
					image.setRGB(j, i, p);
					
					/*
					Color c = new Color(image.getRGB(j, i));
					
					int red = (int) (c.getRed() * 0.299);
					int green = (int) (c.getGreen() * 0.587);
					int blue = (int) (c.getBlue() * 0.114);
					 
					//int p = img.getRGB(x,y); Avg = (R+G+B)/3
					
					int g = red + green + blue;
					Color newColor = new Color(g, g, g);

					image.setRGB(j, i, newColor.getRGB());
					*/
				}
			}
			
			int arr[][] = new int[width][height];
			for(int i = 0; i < width; i++)
			    for(int j = 0; j < height; j++)
			        arr[i][j] = image.getRGB(i, j);
			
			File ouptut = new File("grayscale.jpg");
			ImageIO.write(image, "jpg", ouptut);

		} catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}

	public static void RandomPixil() {
        // Image file dimensions
        int width = 640, height = 320;
 
        // Create buffered image object
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

 
        // create random values pixel by pixel
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                /*int a = (int)(Math.random()*256); //generating
                int r = (int)(Math.random()*256); //values
                int g = (int)(Math.random()*256); //less than
                int b = (int)(Math.random()*256); //256
    
                int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel*/
            		Color color = new Color( (int) (Math.random() * 0xff) , (int)(Math.random() * 0xff), (int) (Math.random() * 0xff));
                img.setRGB(x, y, color.getRGB() );
            }
        }
 
        // write image
        try
        {
            // file object
            File f = new File("random.jpg");
            ImageIO.write(img, "jpg", f);
        }
        catch(IOException e)
        {
            System.out.println("Error: " + e);
        }
	}
	
	
	public static void Filter1() {
		try {
			File input = new File("image.jpg");
			BufferedImage image = ImageIO.read(input);
			int width = image.getWidth();
			int height = image.getHeight();

		    BufferedImage new_img = new BufferedImage( 2*width, 2*height, BufferedImage.TYPE_INT_RGB);
		    
		    int pp = 0;
		    
			for (int i = 0; i < width ; i++) {

				for (int j = 0; j < height; j++) {

					int p = image.getRGB(i,j);
					
					/*
					new_img.setRGB(i*2, j*2, p);
					new_img.setRGB(i*2, j*2+1, p);
					new_img.setRGB(i*2+1, j*2, p);
					new_img.setRGB(i*2+1, j*2+1, p);	
					*/
					
					new_img.setRGB(i*2, j*2, p);
					
					new_img.setRGB(i*2, j*2+1, (p+pp)/2 );
					new_img.setRGB(i*2+1, j*2, (p+pp)/2 );
					
					new_img.setRGB(i*2+1, j*2+1, p);	
					
					
				}
			} 
			
			File f = new File("resized.jpg");
            ImageIO.write(new_img, "jpg", f);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}			
	}
	
	public static void Resize() {
		try {
			File input = new File("image.png");
			BufferedImage image = ImageIO.read(input);
			int width = image.getWidth();
			int height = image.getHeight();

		    BufferedImage new_img = new BufferedImage( 2*width, 2*height, BufferedImage.TYPE_INT_ARGB);
		    
		    int pp = 0;
		    
			for (int i = 1; i < width ; i++) {

				for (int j = 1; j < height; j++) {

					int p = image.getRGB(i,j);
					int p1 = image.getRGB(i-1,j);
					int p2 = image.getRGB(i,j-1);
					int p3 = image.getRGB(i-1,j-1);
					
					int p5 = (p1 + p2 + p3) / 4;
					
					/*
					new_img.setRGB(i*2, j*2, p);
					new_img.setRGB(i*2, j*2+1, p);
					new_img.setRGB(i*2+1, j*2, p);
					new_img.setRGB(i*2+1, j*2+1, p);	
					*/
					
					//Nearest-neighbor interpolation: replacing every pixel with multiple pixels of the same color
					new_img.setRGB(i*2, j*2, p);
					
					new_img.setRGB(i*2, j*2+1, p);
					new_img.setRGB(i*2+1, j*2, p);
					
					new_img.setRGB(i*2+1, j*2+1, p);	
					pp = p;
					
				}
			} 
			
			File f = new File("resized.png");
            ImageIO.write(new_img, "png", f);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}			
	}
	
	// EPX-Scale2× algorithm
	public static void EPX_Scale2X(BufferedImage image, BufferedImage image2 , int i, int j) 
	{
		int P = image.getRGB(i,j);
		
		int A = image.getRGB(i,j-1);
		int B = image.getRGB(i+1,j);
		int C = image.getRGB(i-1,j);
		int D = image.getRGB(i,j+1);
		
		int P1, P2, P3, P4;
		
		P1=P; P2=P; P3=P; P4=P;
		
		if (C==A && C!=D && A!=B) P1=A;
		if (A==B && A!=C && B!=D) P2=B;
		if (D==C && D!=B && C!=A) P3=C;
		if (B==D && B!=A && D!=C) P4=D;
		 
		 
		image2.setRGB(i-1, j-1, P1);
		image2.setRGB(i+1, j-1, P2);
		image2.setRGB(i-1, j+1, P3);
		image2.setRGB(i+1, j+1, P4);
		image2.setRGB(i, j, P);
	}
	
	public static void Quality() {
		try {
			File input = new File("Potter.jpg");
			BufferedImage image = ImageIO.read(input);
			int width = image.getWidth();
			int height = image.getHeight();

			BufferedImage image2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			    
			for (int i = 1; i < width-1 ; i++) {

				for (int j = 1; j < height-1; j++) {
					/**/
					int p0 = image.getRGB(i,j);
					int p1 = image.getRGB(i-1,j-1);
					int p2 = image.getRGB(i-1,j);
					int p3 = image.getRGB(i-1,j+1);
					int p4 = image.getRGB(i,j-1);
					int p5 = image.getRGB(i,j+1);
					int p6 = image.getRGB(i+1,j-1);
					int p7 = image.getRGB(i+1,j);
					int p8 = image.getRGB(i+1,j+1);
					
					//int p = ( p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 ) / 8;
					
					int[] arr = {p1, p2, p3, p4, p5, p6, p7, p8};
					int a=0, r=0, g=0, b=0;
					for (int x : arr)
					{
						a += (x>>24) & 0xff;
				        r += (x>>16) & 0xff;
				        g += (x>>8) & 0xff;
				        b += x & 0xff;
					}
					
					 
					 int p = /*((a/=8)<<24) |*/ (r/=8<<16) | ((g/=8)<<8) | (b/8);
					 image2.setRGB(i, j, p);
					
				}
			} 
			
			File f = new File("quality.jpg");
            ImageIO.write(image2, "jpg", f);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}			
	}

	
	public static void Sierpinski(Graphics2D g2d, int SIZE, int level) throws IOException
	{
 		// compute triangle endpoints and begin recursion
        int triangleHeight = (int) Math.round(SIZE * Math.sqrt(3.0) / 2.0);
        Point p1 = new Point(0, triangleHeight);
        Point p2 = new Point(SIZE / 2, 0);
        Point p3 = new Point(SIZE, triangleHeight);
        
        drawFigure(level, g2d, p1, p2, p3);
	}
	
    // Draws a Sierpinski fractal to the given level inside the triangle
    // whose vertices are (p1, p2, p3).
    public static void drawFigure(int level, Graphics2D g2d, Point p1, Point p2, Point p3) {
        if (level == 1) {
        	    return;
            /* base case: simple triangle
            Polygon poly = new Polygon();
            poly.addPoint(p1.x, p1.y);
            poly.addPoint(p2.x, p2.y);
            poly.addPoint(p3.x, p3.y);
            g2d.fillPolygon(poly);
            */
        } else {
            // recursive case, split into 3 triangles
            Point p4 = midpoint(p1, p2);
            Point p5 = midpoint(p2, p3);
            Point p6 = midpoint(p1, p3);

            g2d.setColor(new Color(p4.x));
            
            Polygon poly = new Polygon();
            poly.addPoint(p4.x, p4.y);
            poly.addPoint(p5.x, p5.y);
            poly.addPoint(p6.x, p6.y);
            g2d.fillPolygon(poly);
            
            // recurse on 3 triangular areas
            drawFigure(level - 1, g2d, p4, p5, p6);
            drawFigure(level - 1, g2d, p1, p4, p6);
            drawFigure(level - 1, g2d, p4, p2, p5);
            drawFigure(level - 1, g2d, p6, p5, p3);
        }
    }

    // returns the midpoint of p1 and p2
    public static Point midpoint(Point p1, Point p2) {
        return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
    }
    

	private static BufferedImage resize(BufferedImage img, int height, int width) 
	{
	        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	        
	        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2d = resized.createGraphics();
	        g2d.drawImage(tmp, 0, 0, null);
	        g2d.dispose();
	        return resized;
	 }   
	
	  /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param scaledWidth absolute width in pixels
     * @param scaledHeight absolute height in pixels
     * @throws IOException
     */
    public static void resize(String inputImagePath,
            String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
 
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());
 
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
 
        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);
 
        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }
 
    /**
     * Resizes an image by a percentage of original size (proportional).
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param percent a double number specifies percentage of the output image
     * over the input image.
     * @throws IOException
     */
    public static void resize(String inputImagePath, String outputImagePath, double percent) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }
    
    public static void DrawLine(int level, Graphics2D g2d, Point p1, Point p2) 
    {
        if (level == 1) 
        		return;
        else 
        {
	        	g2d.setColor(new Color((int)(Math.random()*256)));
	    	 	g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
	    	 	
            // recursive case, split
            Point p3 = midpoint(p1, p2);
            Point p4 = midpoint(p1, p3);
            Point p5 = midpoint(p3, p2);
            
            Point p41 = new Point(p4) , p42 = new Point(p4);
            Point p51 = new Point(p5), p52 = new Point(p5);

            double d = Math.abs(p4.distance(p5) / 2);
            if ( p4.x == p5.x )
            {
	            	p41.x -= d;
	        		p42.x += d;
	        		p51.x -= d;
	        		p52.x += d;
            }
            else //if ( p4.y == p5.y )
            {
	            	p41.y -= d;
	        		p42.y += d;
            		p51.y -= d;
            		p52.y += d;
            }
            
            // recurse
            DrawLine(level - 1, g2d, p41, p42);
            DrawLine(level - 1, g2d, p51, p52);
        }
    }
    
    public static void Line(int level, Graphics2D g2d, int size)
    {
 		DrawLine(level, g2d, new Point(0, size/2), new Point(size, size/2));
 		DrawLine(level, g2d, new Point(size/2, 0), new Point(size/2, size));
    }
    
    public static void Circle(int level, Graphics2D g2d, Point p1, int d) 
    {
    		if (level == 1)
    			return;
        
    		int r = d/2;
    		
    		g2d.draw(new Ellipse2D.Double( p1.x-r, p1.y-r, d, d)); 
    		//g2d.draw(new Ellipse2D.Double( p1.x-r, p1.y-r, r, r));
	    Circle( level-1,  g2d, new Point(p1.x-r, p1.y), r);
	    Circle( level-1,  g2d, new Point(p1.x+r, p1.y), r);
    }
	
	public static void IMG(int level, Graphics2D g2d, Point p1, int w, int h) throws IOException 
	{
		BufferedImage image = ImageIO.read(new File("Potter.jpg"));
        
		if (level == 0 || w == 0 || h == 0)
		{
			System.out.println(level);
			return;
		}
		
		Image tmp = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
	    g2d.drawImage(tmp, p1.x, p1.y, null);
        
        if( w >	h ) //(p1.x + w) >= g2d.getDeviceConfiguration().getBounds().getWidth() )
        		IMG(level-1, g2d, new Point(p1.x, p1.y + h), w/2, h); //height
        else
        		IMG(level-1, g2d, new Point(p1.x + w, p1.y), w, h/2); 
	}
	
	public static void Tree(int level, Graphics2D g2d, Point p1, double length, double angle) throws IOException 
	{
		if (level == 0)
			return;
		
		int x2 = p1.x + (int) (Math.cos(Math.toRadians(angle)) * length);
        int y2 = p1.y + (int) (Math.sin(Math.toRadians(angle)) * length);
        Point p2 = new Point(x2, y2);
        
	 	g2d.setColor(new Color((int)(Math.random()*0xffffff))); //g2d.setColor(Color.GREEN);
	 	g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
	    
	    // recurse
	    Tree(level-1, g2d, p2, length*0.7, angle + 25);
	    Tree(level-1, g2d, p2, length*0.7, angle - 25);
	}
	
	public static void Fractal() throws IOException
	{
				int size = 2000;
		 		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB); //BufferedImage.TYPE_INT_ARGB //Transparency.BITMASK
			    
		 		Graphics2D g2d = image.createGraphics();
		 		//g2d.setColor(Color.black);
		 		
		 		//Line(20, g2d, size);
		 		Sierpinski(g2d, size, 8);
		 		//Circle(20,  g2d, new Point(1000, 1000), 1024);
		 	    //IMG(21, g2d, new Point(0,0), size, size/2);
		 		//Tree(21, g2d, new Point(size/2, size), size/3.5, -90);
		 		
		 		g2d.dispose();
			    ImageIO.write(image, "png", new File("fractal.png"));
	}

	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//GrayScale();
		RandomPixil();
		//Resize();
		//Quality();
		Fractal();
		
		System.out.println("Done!");
	}

}
