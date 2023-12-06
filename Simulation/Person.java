import greenfoot.*;

public class Person extends Actor {
    private boolean alertaGas = false;
    private int tiempoParaEscapar = 600; // 10 segundos a una tasa de 60 act por segundo
    private GreenfootImage personImage = new GreenfootImage("person.png");
    
    public Person(){
        personImage.scale(100,100);
        setImage(personImage);
    }
    
    
    
}
