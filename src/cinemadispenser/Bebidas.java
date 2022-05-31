/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinemadispenser;

/**
 *
 * @author octavio
 */
class Bebidas {

    public Tipo tipo;

    public Bebidas(Tipo tipo) {
        this.tipo = tipo;
    }

    public enum Tipo {
        CocaCola(3),
        FantaNaranja(3),
        Agua(3);

        public int coste;

        Tipo(int coste) {
            this.coste = coste;
        }
    }
}
