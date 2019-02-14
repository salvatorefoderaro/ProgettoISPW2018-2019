package it.uniroma2.ispw.fersa.Test.testUpdateavailability;

import it.uniroma2.ispw.fersa.Control.controller;

public class controllerSingleton {

    /**
     * Costruttore privato, in quanto la creazione dell'istanza deve essere controllata.
     */
    private controllerSingleton() {}

    /**
     * La classe Contenitore viene caricata/inizializzata alla prima esecuzione di getInstance()
     * ovvero al primo accesso a Contenitore.ISTANZA, ed in modo thread-safe.
     * Anche l'inizializzazione dell'attributo statico, pertanto, viene serializzata.
     */
    private static class Contenitore {
        private final static controller ISTANZA = new controller();
    }

    /**
     * Punto di accesso al Singleton. Ne assicura la creazione thread-safe
     * solo all'atto della prima chiamata.
     * @return il Singleton corrispondente
     */
    public static controller getInstance() {
        return Contenitore.ISTANZA;
    }
}