import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class gasParticle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import greenfoot.*;

public class GasParticle extends Actor {
    private double concentracion;

    public GasParticle(double concentracion) {
        GreenfootImage particleImage = new GreenfootImage("red_circle.png");
        particleImage.scale(5, 5);
        setImage(particleImage);
        this.concentracion = concentracion;

    }

    public void act() {
        move();
    }
    
   private void move() {
        int velocidad = Greenfoot.getRandomNumber(20); // Velocidad aleatoria entre 0 y 2
        int direccion = Greenfoot.getRandomNumber(360); // Dirección de movimiento aleatoria

        int nuevaX = getX() + (int) (Math.cos(Math.toRadians(direccion)) * velocidad);
        int nuevaY = getY() + (int) (Math.sin(Math.toRadians(direccion)) * velocidad);

        if (nuevaX >= 0 && nuevaX < getWorld().getWidth() && nuevaY >= 0 && nuevaY < getWorld().getHeight()) {
            setLocation(nuevaX, nuevaY);
        }
    }
}