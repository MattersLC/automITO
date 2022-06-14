package Grafo;

import Automata.Automata;
import Automata.TransicionesA;
import Automata.TransicionL;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;
import java.util.ArrayList;
import java.util.HashSet;

public class ManejaGrafo {

    private Grafo grafo = new Grafo();
    TransicionesA t ;
    Object parent;
    mxStylesheet stylesheet = grafo.getStylesheet();
    ArrayList<String> estados = new ArrayList<>();
    ArrayList<Object> objEstados = new ArrayList<>();

    /**
     * Genera la representación grafica del AFD
     *
     * @param automata Automata pasado por parametro
     * @param cjtoEstados Conjunto de estados
     * @return objeto mxGraphComponent para su representación gráfica
     * @throws Exception
     */
    public mxGraphComponent generarAFD(Automata automata, HashSet<String> cjtoEstados) throws Exception {
        objEstados.clear();
        estados.clear();
        grafo = new Grafo();

        estados = new ArrayList<>(cjtoEstados);

        try {
            //Añadir los estados al grafo
            for (String estado : estados) {
                if (automata.getEstadosFinales().contains(estado)) {
                    objEstados.add(grafo.insertVertex(parent, null, estado, 100, 200, 50, 50, "ESTADOFINAL"));
                } else if (automata.getEstadoInicial().equals(estado)) {
                    objEstados.add(grafo.insertVertex(parent, null, estado, 100, 200, 50, 50, "ESTADOINICIAL"));
                    
                } else {
                    objEstados.add(grafo.insertVertex(parent, null, estado, 100, 200, 50, 50, "ESTADO"));
                }
            }
            //INSERTAR TRANSICIONES
            for (TransicionesA t : automata.getTransiciones()) {
                grafo.insertEdge(parent, null, "   " + t.getSimbolo(), objEstados.get(estados.indexOf(t.getEstadoO())), objEstados.get(estados.indexOf(t.getEstadoD())), "rounded=1");
            }
            
            //AJUSTES ESTÉTICOS EN EL GRAFO
            mxHierarchicalLayout layout = new mxHierarchicalLayout(grafo);
            layout.setInterRankCellSpacing(50.0);
            layout.setIntraCellSpacing(50.0);
            layout.setDisableEdgeStyle(false);
            layout.execute(grafo.getDefaultParent());

        } finally {
            grafo.getModel().endUpdate();
        }

        //AJUSTAR Y CENTRAR EN LA VENTANA
        double width = grafo.getGraphBounds().getWidth();
        double height = grafo.getGraphBounds().getHeight();

        if (width > 560) {
            width = 500;
        }

        if (height > 460) {
            height = 440;
        }
        grafo.getModel().setGeometry(grafo.getDefaultParent(), new mxGeometry(250 - (width) / 2, 220 - (height) / 2, 0, 0));

        return new mxGraphComponent(grafo);

    }


    /**
     * Representación gráfica del AFD dada una situación de estados
     * activos
     *
     * @param automata
     * @param cjtoEstados
     * @param estadoActivo
     * @return objeto mxGraphComponent para su representación gráfica
     * @throws java.lang.Exception si algo sale mal, se lanzará
     */
    public mxGraphComponent simularAFD(Automata automata, HashSet<String> cjtoEstados, String estadoActivo) throws Exception {
        objEstados.clear();
        estados.clear();
        grafo = new Grafo();

        estados = new ArrayList<>(cjtoEstados);

        try {
            //Añadir los estados al grafo
            for (String estado : estados) {
                if (automata.getEstadosFinales().contains(estado)) {
                    objEstados.add(grafo.insertVertex(parent, null, estado, 100, 200, 50, 50, "ESTADOFINAL"));
                } else if (automata.getEstadoInicial().equals(estado)) {
                    objEstados.add(grafo.insertVertex(parent, null, estado, 100, 200, 50, 50, "ESTADOINICIAL"));
                } else {
                    objEstados.add(grafo.insertVertex(parent, null, estado, 100, 200, 50, 50, "ESTADO"));
                }
            }
            //PINTAR EL ESTADO ACTIVO
            try {
                grafo.setCellStyles(mxConstants.STYLE_FILLCOLOR, "green", new Object[]{objEstados.get(estados.indexOf(estadoActivo))});
            } catch (Exception ex) {
                throw new Exception("Error: caracter no reconocido!");
            }

            //INSERTAR LAS TRANSICIONES
            for (TransicionesA t : automata.getTransiciones()) {
                grafo.insertEdge(parent, null, "   " + t.getSimbolo(), objEstados.get(estados.indexOf(t.getEstadoO())), objEstados.get(estados.indexOf(t.getEstadoD())), "rounded=1");
            }

            //AJUSTES ESTÉTICOS DEL GRAFO
            mxHierarchicalLayout layout = new mxHierarchicalLayout(grafo);
            layout.setInterRankCellSpacing(50.0);
            layout.setIntraCellSpacing(50.0);
            layout.setDisableEdgeStyle(false);
            layout.execute(grafo.getDefaultParent());

        } finally {
            grafo.getModel().endUpdate();
        }
        //AJUSTAR Y CENTRAR EN LA VENTANA
        double width = grafo.getGraphBounds().getWidth();
        double height = grafo.getGraphBounds().getHeight();

        if (width > 560) {
            width = 500;
        }

        if (height > 460) {
            height = 440;
        }
        grafo.getModel().setGeometry(grafo.getDefaultParent(), new mxGeometry(250 - (width) / 2, 220 - (height) / 2, 0, 0));
        return new mxGraphComponent(grafo);
    }


}
