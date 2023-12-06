import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class Phone extends Actor {
    private boolean activada;
    private GreenfootImage phoneImage = new GreenfootImage("phone.png");
    private int xOffset = 0;
    private int yOffset = 0;
    private int maxDesplazamiento = 1;
    
    public Phone() {
        activada = false;
        phoneImage.scale(100,100);
        setImage(phoneImage);
    }

  
      public void act() {
        // Comportamiento del teléfono
        setLocation(getX() + xOffset, getY() + yOffset);
    }

    public void vibration() {
        aplicarDesplazamientoAleatorio();
    }
    public void stopVibration() {
        yOffset = 0; 
        xOffset = 0;
         // Detiene la vibración al reiniciar yOffset a cero
    }
    
    private void aplicarDesplazamientoAleatorio() {
        xOffset = Greenfoot.getRandomNumber(2 * maxDesplazamiento + 1) - maxDesplazamiento;
        yOffset = Greenfoot.getRandomNumber(2 * maxDesplazamiento + 1) - maxDesplazamiento;
    }
}

