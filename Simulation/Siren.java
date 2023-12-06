import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import greenfoot.*;

public class Siren extends Actor {
    private boolean activada;
    private GreenfootImage sirenDesactivateImage = new GreenfootImage("sirena_apagada.png");
    private GreenfootImage sirenActivateImage = new GreenfootImage("sirena.png");

    public Siren() {
        activada = false;
        sirenDesactivateImage.scale(50,50);
        sirenActivateImage.scale(50,50);
        setImage(sirenDesactivateImage);
        
 
    }

    public void act() {
        if (activada) {
            // Si la sirena está activada, configura la imagen de la sirena encendida
            setImage(sirenActivateImage);
            // Puedes agregar aquí el código para hacer que la sirena suene o realice alguna animación
        } else {
            // Configura la imagen de la sirena apagada
            setImage(sirenDesactivateImage);
        }
    }

    public void activarSirena() {
        activada = true;
    }

    public void desactivarSirena() {
        activada = false;
    }
}


