package com.Xangars;


import org.apache.camel.spring.Main;

public class MapperEngine {

    public static void main(String[] args) throws Exception {
        // use the Main class from camel-spring
        Main main = new Main();
        // to load Spring XML file

        main.setApplicationContextUri("/META-INF.spring/Xangars.xml");

        // and start (will wait until you stop with ctrl + c)
        main.start();
        System.out.println("\n\nApplication has now been started. You can press ctrl + c to stop.\n\n");
        // echo to console how you can stop
        Thread.sleep(Long.MAX_VALUE);

    }
}