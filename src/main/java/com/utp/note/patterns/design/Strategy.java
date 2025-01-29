package com.utp.note.patterns.design;

/**
 *
 * El patrón Strategy es un patrón de diseño de comportamiento
 * que permite definir una familia de algoritmos, encapsularlos
 * y hacerlos intercambiables. Esto permite que el algoritmo
 * varíe independientemente de los clientes que lo utilicen.
 * Este patrón es útil cuando tienes varios algoritmos para
 * realizar una tarea, y puedes elegir cuál utilizar en tiempo
 * de ejecución según las condiciones.
 */

public class Strategy {

    public interface EstrategiaOperacion {
        int calcular(int valor);
    }

    public static class Suma implements EstrategiaOperacion {
        public int calcular(int valor) {
            return valor + 5; // Suma 5
        }
    }

    public static class Resta implements EstrategiaOperacion {
        public int calcular(int valor) {
            return valor - 3; // Resta 3
        }
    }

    public static class Contexto {
        private EstrategiaOperacion estrategia;

        public void setEstrategia(EstrategiaOperacion estrategia) {
            this.estrategia = estrategia;
        }

        public int ejecutarOperacion(int valor) {
            return estrategia.calcular(valor);
        }

    }

    public static void main(String[] args) {
        Contexto contexto = new Contexto();

        contexto.setEstrategia(new Suma());
        System.out.println(contexto.ejecutarOperacion(10)); // 15

        contexto.setEstrategia(new Resta());
        System.out.println(contexto.ejecutarOperacion(10)); // 7
    }

}
