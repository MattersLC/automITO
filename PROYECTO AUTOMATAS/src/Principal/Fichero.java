package Principal;

import Automata.Automata;
import Automata.TransicionesA;
import Automata.TransicionL;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Fichero. Esta clase se encargará de manejar los ficheros de entrada y
 * salida del autómata.
 *
 * @author Víctor M. Rodríguez y Fran J. Beltrán
 */
public class Fichero {

    private Path ruta, salida;
    private final static Charset ENCODING = StandardCharsets.UTF_8;
    private String estado_inicial;
    private HashSet<String> estados_finales;
    private HashSet<TransicionesA> transiciones_AFD;
    private HashSet<TransicionL> transiciones_L;
    private HashSet<String> conjuntoEstados = new HashSet();
    private HashSet<String> conjuntoSimbolos = new HashSet();

    /**
     * Constructor que inicializa todos los conjuntos de datos a usar.
     *
     * @param nombreFichero Ruta del fichero que vamos a usar.
     */
    public Fichero(String nombreFichero) {
        this.estados_finales = new HashSet();
        this.transiciones_AFD = new HashSet();
        this.transiciones_L = new HashSet();
        this.conjuntoSimbolos = new HashSet();
        this.conjuntoEstados = new HashSet();
        this.ruta = Paths.get(nombreFichero);
    }

    /**
     * Constructor que inicializa todos los conjuntos de datos a usar.
     *
     */
    public Fichero() {
        this.estados_finales = new HashSet();
        this.transiciones_AFD = new HashSet();
        this.transiciones_L = new HashSet();
        this.conjuntoSimbolos = new HashSet();
        this.conjuntoEstados = new HashSet();
    }

    /**
     * Permite leer un fichero de un automata de tipo AFD
     *
     * @throws IOException Si algo no va correctamente, lanzará la excepción
     */
    public void procesarAFD() throws IOException {
        try ( Scanner scanner = new Scanner(ruta, ENCODING.name())) {
            // Saltamos los estados
            String linea = scanner.nextLine();
            String valores[] = linea.split(" ");

            for (int i = 1; i < valores.length; i++) {
                conjuntoEstados.add(valores[i]);
            }

            // Leemos el estado inicial
            linea = scanner.nextLine();
            valores = linea.split(" ");

            estado_inicial = valores[1];
            System.out.println("ESTADO INICIAL LEIDO: " + valores[1]);

            // Leemos los estados finales
            linea = scanner.nextLine();
            valores = linea.split(" ");

            for (int i = 1; i < valores.length; i++) {
                estados_finales.add(valores[i]);
                System.out.println("ESTADO FINAL LEIDO: " + valores[i]);
            }

            linea = scanner.nextLine();
            linea = scanner.nextLine();

            while (scanner.hasNextLine() && !linea.equals("FIN")) {
                transiciones_AFD.add(procesarLineaAFD(linea));
                linea = scanner.nextLine();
            }
        }
    }

    /**
     * Procesa una linea de contenido de un fichero de tipo AFD
     */
    public TransicionesA procesarLineaAFD(String contenido) {
        String valores[] = contenido.split(" ");

        this.conjuntoSimbolos.add(valores[2].split("'")[1]); //Separar las comillas simples

        TransicionesA temp = new TransicionesA(valores[1], valores[2].charAt(1), valores[3]);

        return temp;
    }

    
    /**
     * Procesa una linea de contenido de un fichero de tipo AFND, pero la linea
     * es de TransicionL
     *
     * @param contenido Linea que vamos a procesar
     * @return
     */
    public TransicionL procesarLineaL(String contenido) {
        String valores[] = contenido.split(" ");

        this.conjuntoEstados.add(valores[1]);

        HashSet<String> destinos = new HashSet();

        for (int i = 2; i < valores.length; i++) {
            destinos.add(valores[i]);
        }

        TransicionL temp = new TransicionL(valores[1], destinos);

        return temp;

    }

    /**
     * Genera el automata listo para poder visualizarlo gráficamente
     *
     * @return
     */
    public Automata generarAutomataAFD() {
        Automata temp = new Automata();

        for (TransicionesA valor : this.transiciones_AFD) {
            temp.agregarTransicion(valor);
        }

        temp.setEstadoInicial(estado_inicial);
        temp.setEstadosFinales(estados_finales);

        return temp;
    }

    /**
     * Genera el automata listo para poder visualizarlo gráficamente
     *
     * @return
     */
    
    /**
     * Genera el fichero de salida de un automata
     *
     * @param automata Automata del que vamos a generar el fichero
     * @param nombreFichero Nombre del fichero que vamos a exportar
     * @param cjtoEstados Conjunto de estados del automata
     */
    public void generarFichero(Automata automata, String nombreFichero, HashSet<String> cjtoEstados) {
        try {
            this.salida = Paths.get(nombreFichero);
            FileWriter fw = new FileWriter(salida.toFile());
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);
            String mensaje = "ESTADOS:";

            this.conjuntoEstados = cjtoEstados;
            this.estado_inicial = automata.getEstadoInicial();
            this.estados_finales = automata.getEstadosFinales();
            this.transiciones_AFD = automata.getTransiciones();

            for (TransicionesA trans : automata.getTransiciones()) {
                this.conjuntoSimbolos.add(String.valueOf(trans.getSimbolo()));
            }

            for (String valor : this.conjuntoEstados) {
                mensaje += " " + valor;
            }

            salida.println(mensaje);
            salida.println("INICIAL: " + this.estado_inicial);
            mensaje = "FINALES: ";

            for (String valor : this.estados_finales) {
                mensaje += valor + " ";
            }

            salida.println(mensaje);
            salida.println("TRANSICIONES:");

            for (TransicionesA valor : this.transiciones_AFD) {
                salida.println(valor);
            }

            salida.println("FIN");
            salida.close();
            bw.close();

        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Genera el fichero de salida de un automata
     *
     * @param automata Automata del que vamos a generar el fichero
     * @param nombreFichero Nombre del fichero que vamos a exportar
     * @param cjtoEstados Conjunto de estados del automata
     */
   
    /**
     * Devuelve el conjunto de estados
     *
     * @return
     */
    public HashSet<String> getConjuntoEstados() {
        return conjuntoEstados;
    }

    /**
     * Devuelve el conjunto de simbolos
     *
     * @return
     */
    public HashSet<String> getConjuntoSimbolos() {
        return conjuntoSimbolos;
    }

    /**
     * Método que nos sirve para probar el funcionamiento de la clase
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Fichero temp = new Fichero("D://2.txt");
        Fichero temp2 = new Fichero("D://1.txt");

        temp.procesarAFD();
        //temp.generarFichero(temp2.generarAutomataAFD(), "D://pruebaAFD.txt");
    }
}
