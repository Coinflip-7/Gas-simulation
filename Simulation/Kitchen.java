import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.IOException;
/**
 * Write a description of class Kitchen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Kitchen extends World
{

    /**
     * Constructor for objects of class Kitchen.
     * 
     */
    public Kitchen() throws IOException
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1);
        particleGeneration(10000,590, 10, 0.8);
        Siren siren = new Siren();
        Phone phone = new Phone();
        Person person = new Person();
        addObject(new Sensor(siren, phone), 300, 200);
        addObject(new Sensor2(siren, phone), 300, 300);
        addObject(siren, 100, 100);
        addObject(phone, 200, 300);
        addObject(person, 100, 300);

        prepare();
    }

    private void particleGeneration(int cantidad, int x, int y, double concentration) {
        for (int i = 0; i < cantidad; i++) {
            GasParticle particle = new GasParticle(concentration);
            addObject(particle, x, y);
        }
    }

    public void sendMessage(String message, int x, int y) {
        GreenfootImage imagen = new GreenfootImage(message, 24, Color.WHITE, Color.BLACK);
        addObject(new Message(imagen), x, y);
    }
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
    }
}
