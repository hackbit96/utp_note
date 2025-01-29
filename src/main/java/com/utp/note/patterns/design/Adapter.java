package com.utp.note.patterns.design;

/*
 * El patrón Adapter es muy útil cuando necesitas integrar clases o interfaces
 * incompatibles en tu sistema, permitiendo que trabajen juntas de manera fluida
 * sin necesidad de modificar el código original de las clases involucradas.
 * Este patrón actúa como un intermediario que adapta una interfaz de una clase
 * para que se ajuste a otra interfaz esperada.
 */

public class Adapter {

    public static class OperacionIncompatible {
        public int sumaValor(int valor) {
            return valor + 5; // Suma 5
        }

        public int restaValor(int valor) {
            return valor - 3; // Resta 3
        }
    }


    public interface Operacion {
        int operar(int valor);
    }

    public static class OperacionAdapter implements Operacion {
        private OperacionIncompatible operacionIncompatible;

        public OperacionAdapter(OperacionIncompatible operacionIncompatible) {
            this.operacionIncompatible = operacionIncompatible;
        }

        @Override
        public int operar(int valor) {
            return operacionIncompatible.sumaValor(valor);  // o operacionIncompatible.restaValor(valor);
        }
    }

    public static void main(String[] args) {
        OperacionIncompatible operacionIncompatible = new OperacionIncompatible();
        Operacion operacion = new OperacionAdapter(operacionIncompatible);

        System.out.println(operacion.operar(10)); // 15 (usando sumaValor)

        // Si cambiamos el método en el adapter a restaValor, el resultado sería diferente
         Operacion operacionResta = new OperacionAdapter(operacionIncompatible);
         System.out.println(operacionResta.operar(10)); // 7 (usando restaValor)
    }
}
