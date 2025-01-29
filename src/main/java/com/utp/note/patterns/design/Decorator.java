package com.utp.note.patterns.design;

/**
 * El patrón Decorator es un patrón estructural que permite
 * agregar funcionalidad a un objeto de manera dinámica,
 * sin alterar su estructura. Se usa para extender las capacidades
 * de un objeto, de forma similar a cómo se "decora" algo,
 * añadiendo capas de comportamiento sin cambiar el objeto original.
 *
 * -
 */

public class Decorator {

    public interface Operacion {
        int calcular(int valor);
    }

    public static class Sumar implements Operacion {
        public int calcular(int valor) {
            return valor + 5;
        }
    }

    public static class MultiplicarDecorator implements Operacion {
        private Operacion operacion;

        public MultiplicarDecorator(Operacion operacion) {
            this.operacion = operacion;
        }

        public int calcular(int valor) {
            return operacion.calcular(valor) * 2;
        }
    }

    public static void main(String[] args) {
        Operacion operacion = new Sumar();
        Operacion operacionDecorada = new MultiplicarDecorator(operacion);
        System.out.println(operacionDecorada.calcular(15));
    }
}
