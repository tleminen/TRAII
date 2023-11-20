package Viikko4;

import fi.uef.cs.tra.AbstractGraph;
import fi.uef.cs.tra.DiGraph;
import fi.uef.cs.tra.Graph;
import fi.uef.cs.tra.Vertex;

import javax.swing.plaf.DimensionUIResource;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class TRAII_23_t16_pohja {

    public static void main(String[] args) {

        // defaults
        int vertices = 8;
        int edges = 5;

        if (args.length > 0)
            vertices = Integer.parseInt(args[0]);

        if (args.length > 1)
            edges = Integer.parseInt(args[1]);

        int seed = vertices+edges;

        if (args.length > 2)
            seed = Integer.parseInt(args[2]);

        Graph graph;

        System.out.println("\nVerkko (kaksi puuta, ei kehÃ¤Ã¤): ");
        // graph = GraphMaker.createGraph(vertices, edges, seed);
        graph = GraphMaker.createFlora(0, 0, 2, 0, vertices);
        System.out.println(GraphMaker.toString(graph, 0));
        List<Vertex> keha = jokuKeha(graph);
        System.out.println("KehÃ¤: " + keha);

        System.out.println("\nLisÃ¤tÃ¤Ã¤n kehÃ¤: ");
        GraphMaker.addRandomCycle(graph, 3, false);
        keha = jokuKeha(graph);
        System.out.println("KehÃ¤: " + keha);

    }

    static List<Vertex> jokuKeha(Graph G) {
        varita(G, Graph.WHITE);

        // TODO
        // vihje: hae kehÃ¤ syvyyssuuntaisella haulla
        List<Vertex> listaSolmuille = new LinkedList<>();
        List<Vertex> tulos = new LinkedList<>();
        //Käydään verkon solmut läpi
        for(Vertex solmu : G.vertices()){
            //Jos solmu on valkoinen niin kutsutaan metodia
            if(solmu.getColor()== DiGraph.WHITE){
                if(kehanEtsinta(solmu,null,listaSolmuille)){
                    //Lisätään tulos listaan listasolmuille listan viimeinen solmu
                    tulos.add(listaSolmuille.get(listaSolmuille.size()-1));
                    //Käydään loput solmut läpi
                    for(int i = listaSolmuille.size()-2 ; i>=0; i--){
                        tulos.add(listaSolmuille.get(i));
                        //Niin kauan lisätään for each loopiissa solmuja tulos listaan
                        // ennen kuin törmätään tilanteeseen, että tulos listan eka alkio
                        // on sama kuin listasolmuille listan alkio
                        if(listaSolmuille.get(i) == tulos.get(0)){
                            break;
                        }
                    }
                    return tulos;
                }
            }
        }
        return null;
    }

    private static boolean kehanEtsinta(Vertex solmu, Vertex edellinenSolmu, List<Vertex> tulos){
        //väritetään solmu harmaaksi
        solmu.setColor(DiGraph.GRAY);
        //Lisätään solmu tulos listaan
        tulos.add(solmu);

        //Käydään solmun naapurit läpi for each loopissa
        for(Vertex v : solmu.neighbors()){
            //Jos käsiteltävän solmun väri on harmaa ja käsiteltävä solmu ei ole edellinen solmu
            if(v.getColor() == DiGraph.GRAY && v != edellinenSolmu){
                //Lisätään solmu listaan
                tulos.add(v);
                return true;  
            }
            //Jos käsiteltävä solmun väri on valkoinen tehdään rekursio kutsu
            if(v.getColor() == DiGraph.WHITE){
                //Merkitään uudeksi solmuksi käsiteltävä solmu ja edelliseksi solmuksi se solmu, jonka
                //naapureita käytiin läpi for each loopissa
                if (kehanEtsinta(v,solmu,tulos)){
                    //Jos metodista tulee true palautetaan true
                    return true;
                }
            }
        }
        //Poistetaan listasta viimeinen
        tulos.remove(tulos.size()-1);
        //Palautetaan siis false, koska kehää ei löytynyt
        return false;
    }




    // syvyyssuuntainen lÃ¤pikynti vÃ¤rittÃ¤en vÃ¤rillÃ¤ c
    static void dfsColor(Vertex v, int color) {
        v.setColor(color);

        for (Vertex w : v.neighbors())
            if (w.getColor() != color)
                dfsColor(w, color);
    }

    // verkko annetun vÃ¤riseksi
    static void varita(AbstractGraph g, int c) {
        for (Vertex v : g.vertices())
            v.setColor(c);
    }



}
