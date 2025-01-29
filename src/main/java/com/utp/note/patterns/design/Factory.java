package com.utp.note.patterns.design;

/*
 * El patrón Factory se utiliza cuando se necesita crear objetos
 * de manera flexible sin especificar la clase exacta del objeto
 * que se va a crear. En lugar de instanciar un objeto directamente,
 * el patrón Factory delega la creación de objetos a una clase de fábrica.
 * Este patrón es útil cuando tienes una familia de objetos relacionados,
 * pero no quieres acoplar el código a una implementación específica.
 */


public class Factory {

    public interface Operacion {
        int calcular(int valor);
    }


    public static class Restar implements Operacion {
        public int calcular(int valor) {
            return valor - 5;
        }
    }


    public static class Sumar implements Operacion {
        public int calcular(int valor) {
            return valor + 10;
        }
    }


    public static Operacion crearOperacion(String tipo) {
        if (tipo.equals("sumar")) {
            return new Sumar();
        } else if (tipo.equals("restar")) {
            return new Restar();
        }
        return null;
    }


    public static void main(String[] args) {
        Operacion operacion = crearOperacion("sumar");
        System.out.println(operacion.calcular(10)); // 20

        operacion = crearOperacion("restar");
        System.out.println(operacion.calcular(10)); // 5
    }
}
