import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class ImprovedNoise { 

    static BufferedImage image = 
        new BufferedImage(WindowDisplay.WIDTH, WindowDisplay.HEIGHT, BufferedImage.TYPE_INT_RGB);
        
    ArrayList<Float> seeds;
    
    void init() {
        seeds = new ArrayList<Float>();
        
        for (int i = 0; i < 4; i++) {
            seeds.add((float)new Random().nextInt(10000000));
        }
    }
    
    
    BufferedImage getNoiseImage() {

    	for(int x = 0; x < WindowDisplay.WIDTH; x++){
    		for(int y = 0; y < WindowDisplay.HEIGHT; y++){

    			double dx = (double) x / WindowDisplay.WIDTH;
    			double dy = (double) y / WindowDisplay.WIDTH;
                int frequency = 120;
                
                double n1 = noise((dx * frequency), (dy * frequency), seeds.get(0));
                double n2 = noise((dx * frequency/2), (dy * frequency/2), seeds.get(1));
                double n3 = noise((dx * frequency/4), (dy * frequency/4), seeds.get(2));
                double n4 = noise((dx * frequency/16), (dy * frequency/16), seeds.get(3));

                n1 = (n1 - 1) / 2;
                n2 = (n2 - 1) / 2;
                n3 = (n3 - 1) / 2;
                n4 = (n4 - 1) / 2;

                int blue = (int)(n1 * 0xFF) + (int)(n2 * 0xFF) + (int)(n3 * 0xFF) + (int)(n4 * 0xFF);
                int green = blue * 0x100;
                int red = blue * 0x10000;
                
    			int finalValue = red+green+blue;
        		image.setRGB(x, y, finalValue);
        	}
        }

    	return image;
    }
    static double noise(double x, double y, double z) {
        int x1 = (int)Math.floor(x) & 255,
            y1 = (int)Math.floor(y) & 255,
            z1 = (int)Math.floor(z) & 255;

        x -= Math.floor(x);
        y -= Math.floor(y);
        z -= Math.floor(z);

        double x2 = fade(x),
               y2 = fade(y),
               z2 = fade(z);

        int A = p[x1  ]+y1, AA = p[A]+z1, AB = p[A+1]+z1,      // HASH COORDINATES OF
            B = p[x1+1]+y1, BA = p[B]+z1, BB = p[B+1]+z1;      // THE 8 CUBE CORNERS,

        return lerp(z2, lerp(y2, lerp(x2,gradient(p[AA], x  , y  , z   ), 
                                         gradient(p[BA], x-1, y  , z   )),
                                lerp(x2, gradient(p[AB], x  , y-1, z   ), 
                                         gradient(p[BB], x-1, y-1, z   ))),
                       lerp(y2, lerp(x2, gradient(p[AA+1], x  , y  , z-1 ), 
                                         gradient(p[BA+1], x-1, y  , z-1 )),
                                lerp(x2, gradient(p[AB+1], x  , y-1, z-1 ),
                                         gradient(p[BB+1], x-1, y-1, z-1 ))));
    }
    static double fade(double t) { 
        return t * t * t * (t * (t * 6 - 15) + 10); 
    }

    static double lerp(double t, double a, double b) { 
        return a + t * (b - a); 
    }

    static double gradient(int hash, double x, double y, double z) {
        int h = hash & 15;
        double u = h<8 ? x : y;
        double v = h<4 ? y : h==12 || h==14 ? x : z;

        return ((h&1) == 0 ? u : -u) + ((h&2) == 0 ? v : -v);
    }
   static final int p[] = new int[512], permutation[] = { 151,160,137,91,90,15,
   131,13,201,95,96,53,194,233,7,225,140,36,103,30,69,142,8,99,37,240,21,10,23,
   190, 6,148,247,120,234,75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,
   88,237,149,56,87,174,20,125,136,171,168, 68,175,74,165,71,134,139,48,27,166,
   77,146,158,231,83,111,229,122,60,211,133,230,220,105,92,41,55,46,245,40,244,
   102,143,54, 65,25,63,161, 1,216,80,73,209,76,132,187,208, 89,18,169,200,196,
   135,130,116,188,159,86,164,100,109,198,173,186, 3,64,52,217,226,250,124,123,
   5,202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,182,189,28,42,
   223,183,170,213,119,248,152, 2,44,154,163, 70,221,153,101,155,167, 43,172,9,
   129,22,39,253, 19,98,108,110,79,113,224,232,178,185, 112,104,218,246,97,228,
   251,34,242,193,238,210,144,12,191,179,162,241, 81,51,145,235,249,14,239,107,
   49,192,214, 31,181,199,106,157,184, 84,204,176,115,121,50,45,127, 4,150,254,
   138,236,205,93,222,114,67,29,24,72,243,141,128,195,78,66,215,61,156,180
   };
    static { 
        for (int i=0; i < 256 ; i++) p[256+i] = p[i] = permutation[i]; 
    }
}
