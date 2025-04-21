package fr.esgi.rent.configuration;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;

import java.util.Random;

@Dependent
public class Configuration {

    @Produces
    public Random random() {
        return new Random();
    }

}
