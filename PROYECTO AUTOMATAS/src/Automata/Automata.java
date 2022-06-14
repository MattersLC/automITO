package Automata;

import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Automata implements Cloneable, Proceso {

    private HashSet<String> estadosFinales;
    private String estadoInicial = "";
    private HashSet<TransicionesA> transiciones;

    /**
     * Constructor
     */
    public Automata() {
        this.transiciones = new HashSet();
        this.estadosFinales = new HashSet();
    }

    /**
     * Agrega la transición al autómata
     *
     * @param e1 Estado origen
     * @param simbolo Símbolo de entrada
     * @param e2 Estado destino
     */
    public void agregarTransicion(String e1, char simbolo, String e2) {
        this.transiciones.add(new TransicionesA(e1, simbolo, e2));
    }

    /**
     * Agrega la transición pasada por parámetro al autómata
     */
    public void agregarTransicion(TransicionesA trans) {
        this.transiciones.add(trans);
    }

    /**
     * Obtiene el estado destino de una transición del autómata
     *
     * @param estado Estado origen
     * @param simbolo Símbolo de entrada
     * @return Estado destino
     */
    public String getTransicion(String estado, char simbolo) {
        for (TransicionesA t : this.transiciones) {
            if (t.getEstadoO().equals(estado) && t.getSimbolo() == simbolo) {
                return t.getEstadoD();
            }
        }

        return "";
    }

    /**
     * Obtiene el estado inicial del autómata
     */
    public String getEstadoInicial() {
        return estadoInicial;
    }

    /**
     * Establece el estado inicial del autómata
     */
    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    /**
     * Establece los estados finales del autómata (1...N)
     */
    public void setEstadosFinales(HashSet<String> estadosFinales) {
        this.estadosFinales = estadosFinales;
    }

    /**
     * Obtiene el conjunto de estados finales del autómata
     */
    public HashSet<String> getEstadosFinales() {
        return estadosFinales;
    }

    /**
     * Obtiene el conjunto de transiciones del autómata
     */
    public HashSet<TransicionesA> getTransiciones() {
        return transiciones;
    }

    /**
     * Agrega el estado final pasado por parámetro al autómata
     */
    public void addEstadoFinal(String estadoFinal) {
        this.estadosFinales.add(estadoFinal);
    }

    /**
     * Devuelve verdadero si el estado final pasado por parámetro pertenece al
     * conjunto de estados finales del autómata
     */
    @Override
    public boolean esFinal(String estado) {
        return this.estadosFinales.contains(estado);
    }

    /**
     * Elimina el símbolo de entrada pasado por parámetro del autómata
     */
    public void eliminarSimbolo(char s) //Elimina las transiciones que usan ese simbolo
    {
        for (TransicionesA t : this.transiciones) {
            if (t.getSimbolo() == s) {
                this.transiciones.remove(t);
            }
        }
    }

    /**
     * Elimina el estado pasado por parámetro del autómata
     */
    public void eliminarEstado(String e) //Eliminar las transiciones que usan ese estado
    {
        HashSet<TransicionesA> eliminar = new HashSet();
        for (TransicionesA t : this.transiciones) {
            if (t.getEstadoO().equals(e) || t.getEstadoD().equals(e)) //Si aparece en el origen o destino de una transicion
            {
                eliminar.add(t);    //La eliminamos
            }
        }
        this.transiciones.removeAll(eliminar);
    }

    /**
     * Elimina la transición pasada por parámetro
     */
    public void eliminarTransicion(TransicionesA t) {
        this.transiciones.remove(t);
    }

    /**
     * Recorre la cadena de entrada, Si el estado al que evoluciona al leer el último símbolo de
     * entrada de la cadena es un estado final, la cadena es reconocida
     *
     * @param cadena Símbolos de entrada a reconocer por el autómata
     * @return verdadero si la cadena es reconocida por el autómata (pertenece a
     * su lenguaje formado)
     * @throws java.lang.Exception Si el autómata no es válido
     */
    @Override
    public boolean reconocer(String cadena) throws Exception {
        //CONTROL DE EXCEPCIONES
        if (this.estadoInicial.equals("")) {
            throw new Exception("Error: no ha indicado ningún estado inicial!");
        }
        if (this.getEstadosFinales().isEmpty()) {
            throw new Exception("Error: no ha indicado ningún estado final!");
        }
        
        char[] simbolo = cadena.toCharArray();
        String estado = this.getEstadoInicial();

        for (int i = 0; i < simbolo.length; i++) {
            estado = getTransicion(estado, simbolo[i]);
            if (estado.equals("")) {
                throw new Exception("Error: transicion con caracter '" + simbolo[i] + "' no válida!");
            }
        }

        return esFinal(estado);
    }

    /**
     * Permite la representación en texto del autómata
     *
     * @return
     */
    @Override
    public String toString() {
        String mensaje = "";
        HashSet<String> estados = new HashSet();

        mensaje += "ESTADOS\n";

        for (TransicionesA t : this.transiciones) {
            estados.add(t.getEstadoO());
            estados.add(t.getEstadoD());
        }

        mensaje += "ESTADO INICIAL: " + this.estadoInicial + "\n";
        mensaje += "ESTADOS FINALES: \n";
        for (String e : estadosFinales) {
            mensaje += e;
        }

        for (String e : estados) {
            mensaje += e + "\n";
        }

        mensaje += "\nTRANSICIONES:\n";
        for (TransicionesA t : this.transiciones) {
            mensaje += t + "\n";
        }

        return mensaje;
    }

    /**
     * Simula un AFD
     *
     * @param args
     */
    public static void main(String[] args) {
        Automata automata = new Automata();
    }

    /**
     * Devuelve una copia del autómata. Se crean nuevos objetos, no se clonan
     * las referencias
     *
     * @return
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Automata copia = null;
        try {
            copia = (Automata) super.clone(); //Hace una copia binaria de los objetos
        } catch (CloneNotSupportedException ex) {
            System.out.println("Clone no soportado");
        }
        //Como el clone de HashSet hace solo una copia superficial, tenemos que copia a mano los elementos
        copia.estadosFinales = new HashSet<>();
        for (String estado : this.estadosFinales) {
            copia.estadosFinales.add(estado);
        }

        copia.transiciones = new HashSet<TransicionesA>();
        for (TransicionesA t : this.transiciones) {
            copia.transiciones.add(t);
        }

        return copia;
    }
}
