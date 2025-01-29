package com.utp.note.patterns.design;

public class Builder {

    private int numero;
    private int suma;
    private int resta;

    public static class builder {
        private int numero = 0;
        private int suma = 0;
        private int resta = 0;

        public builder setNumero(int numero) {
            this.numero = numero;
            return this;
        }

        public builder setSuma(int suma) {
            this.suma = suma;
            return this;
        }

        public builder setResta(int resta) {
            this.resta = resta;
            return this;
        }

        public Builder build() {
            Builder calculadora = new Builder();
            calculadora.numero = this.numero;
            calculadora.suma = this.suma;
            calculadora.resta = this.resta;
            return calculadora;
        }
    }

    public int calcular() {
        return numero + suma - resta;
    }


    public static void main(String[] args) {
        Builder calculadora = new Builder.builder()
                .setNumero(10)
                .setSuma(5)
                .setResta(3)
                .build();

        System.out.println(calculadora.calcular());
    }
}
