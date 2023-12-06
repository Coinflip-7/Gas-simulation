import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.io.Closeable;
import java.io.IOException;

/**
 * Write a description of class Sensor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import org.json.JSONObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Sensor extends Actor implements Closeable {
    private double umbralConcentracion = 0.3; // Umbral de concentración para activar la alarma
    private GreenfootImage sensorImage = new GreenfootImage("sensor.png");
    private Siren siren;
    private Phone phone;
    private final static String QUEUE_NAME = "rules";
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private int delayCounter = 50; 

    public Sensor(Siren siren, Phone phone) throws IOException{
        sensorImage.scale(50, 50);
        setImage(sensorImage);
        this.siren = siren;
        this.phone = phone;
        // Connect to rabbitMQ server
        this.factory = new ConnectionFactory();
        this.factory.setHost("localhost");
        try
        {
            this.connection = factory.newConnection();
        }
        catch (java.util.concurrent.TimeoutException te)
        {
            te.printStackTrace();
        }
        this.channel = connection.createChannel();
        // declare queue to send messages
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    }

    public void act() {
        try {
            if (delayCounter > 0) {
                delayCounter--;
                return; // Skip the particle detection while delaying
            }
            verificarConcentracion();
            delayCounter = 50; // reset delay
        } catch (IOException ioe) {
            ioe.printStackTrace();
    }
    }

    private void verificarConcentracion() throws IOException {
    int x = getX();
    int y = getY();
    int radioDeteccion = 50; // Ajusta el radio de detección según tus necesidades

    List<GasParticle> particulas = getObjectsInRange(radioDeteccion, GasParticle.class);

    if (!particulas.isEmpty()) {
        int cantidadParticulas = particulas.size();
        double concentracion = cantidadParticulas / (Math.PI * radioDeteccion);
        Kitchen world = (Kitchen) getWorld();

        if (concentracion >= umbralConcentracion) {
            world.sendMessage("Se ha detectado una fuga de gas, ¡Corre!", 300, 50);
            siren.activarSirena();
            phone.vibration();

            // Get the current time
            LocalDateTime currentTime = LocalDateTime.now();
            // Format the time as a string
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTime = currentTime.format(formatter);

            // Create a JSON object with additional information
            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("Date", formattedTime);
            jsonMessage.put("Origin", "sensor1");
            jsonMessage.put("Concentration", concentracion);

            // Convert the JSON object to a string and send it
            String message = jsonMessage.toString();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        } else {
            siren.desactivarSirena();
            phone.stopVibration();
        }
    }
}
    
    @Override
    public void close() throws IOException {
        if (channel != null && channel.isOpen()) {
            try
            {
                channel.close();
            }
            catch (java.util.concurrent.TimeoutException te)
            {
                te.printStackTrace();
            }
        }
        if (connection != null && connection.isOpen()) {
            connection.close();
        }
    }
    
    
    
    
}

