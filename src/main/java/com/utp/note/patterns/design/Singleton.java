package com.utp.note.patterns.design;


/*
* - conexion de base de datos
* - logger
* - archivos de configuracion
* - sesion de usuario
* */
public class Singleton {

    public static class Operaciones {
        private static Operaciones instancia;
        private int numero = 10;

        private Operaciones() {
        }

        public static Operaciones getInstance() {
            if (instancia == null) {
                instancia = new Operaciones();
            }
            return instancia;
        }

        public int sumar(int valor) {
            numero += valor;
            return numero;
        }

        public int restar(int valor) {
            numero -= valor;
            return numero;
        }
    }


    public static void main(String[] args) {
        Operaciones operacion = Operaciones.getInstance();
        System.out.println(operacion.sumar(5)); // 15
        System.out.println(operacion.restar(3)); // 12
    }


}


