package Viikko3;

import fi.uef.cs.tra.AbstractGraph;
import fi.uef.cs.tra.DiGraph;
import fi.uef.cs.tra.Graph;
import fi.uef.cs.tra.Vertex;

import java.util.*;

public class TRAII_23_t11_12_pohja {

    public static void main(String[] args) {

        // defaults
        int vertices = 5;
        int edges = 7;

        if (args.length > 0)
            vertices = Integer.parseInt(args[0]);

        if (args.length > 1)
            edges = Integer.parseInt(args[1]);

        int seed = vertices+edges+5;    // tÃ¤stÃ¤kin voi vaihdella minkÃ¤laisia verkkoja syntyy

        if (args.length > 2)
            seed = Integer.parseInt(args[2]);


        // Luodaan satunnainen verkko
        DiGraph graph = GraphMaker.createDiGraph(vertices, edges, seed);
        System.out.println("\nVerkko (numerot ovat solmujen nimiÃ¤, kirjaimet kaarten nimiÃ¤):");
        System.out.println(graph);

        System.out.println("\nSeuraajat kullekin solmulle:");
        for (Vertex v : graph.vertices()) {
            System.out.println(v + " : " + seuraajienJoukko(graph, v));
        }


        int polkuja = 15; // testaa max 15 polkua
        System.out.println("\nPolkuja:");
        for (Vertex v1 : graph.vertices()) {
            for (Vertex v2 : graph.vertices()) {
                if (v1 == v2)
                    continue;
                System.out.println("" + v1 + "->" + v2 + " : " + jokuPolku(graph, v1, v2));
                if (polkuja-- <= 0)
                    break;
            }
        }





    } // main()


    /**
     * Solmun seuraajien joukko.
     * Solmun seuraajien joukko ovat ne solmut joihin on polku annetusta solmusta.
     * @param G tarkasteltava verkko (ei vÃ¤lttÃ¤mÃ¤ttÃ¤ tarvita)
     * @param solmu aloitussolmu
     * @return kaikki solmut joihin on polku solmusta solmu
     */

    //AIKAVAATIVUUS O(n+e), n = solmujen määrä, e = kaarten määrä
    static Set<Vertex> seuraajienJoukko(DiGraph G, Vertex solmu) {
        varita(G, DiGraph.WHITE);
        Set<Vertex> s = new LinkedHashSet<>();

        // TODO
        //Kutsutaan rekursiota
        rekursio(solmu, s);
        return s;
    }
    private static void rekursio(Vertex solmu2, Set<Vertex> joukko){
        //asetetaan solmu harmaaksi
        solmu2.setColor(DiGraph.GRAY);
        //Käydään solmun naapurit läpi for each loopilla
        for (Vertex x : solmu2.neighbors()){
            //Mikäli solmu on valkoinen niin lisätään se linkedhashsettiin ja kutsutaan rekursiota
            if(x.getColor() == DiGraph.WHITE){
                joukko.add(x);
                rekursio(x,joukko);
            }
        }
    }


    /**
     * Joku polku solmusta alku solmuun loppu.
     * Versio joka rakentaa polkua rekursiossa edetessÃ¤ (ja purkaa jollei maalia lÃ¶ydy)
     * @param G tarkasteltava verkko (tarvitaan pohjavÃ¤ritykseen)
     * @param alku polun alkusolmu
     * @param loppu polun loppusolmu
     * @return lista polun solmuista, tai tyhjÃ¤ lista jollei polkua ole olemassa
     */

    //AIKAVAATIVUUS O(n+e), n = solmujen määrä, e = kaarten määrä
    static List<Vertex> jokuPolku(DiGraph G, Vertex alku, Vertex loppu) {

        GraphMaker.varita(G, DiGraph.WHITE);
        List<Vertex> tulos = new LinkedList<>();
        // TODO
        tulos.add(alku);

        //Kutsutaan rekursiota joka palauttaa joko true tai false.
        // true = mikäli polku on olemassa
        // false = mikäli polkua ei ole
        boolean polkuLoyty = rekursioJokuPolku(alku,loppu,tulos);
        //Mikäli true palautetaan tulos linkedlista
        if(polkuLoyty){
            return tulos;
        }
        //Mikäli false palautetaan uusi tyhjä linkedlista
        else {
            return new LinkedList<>();
        }
    }

    private static boolean rekursioJokuPolku(Vertex eka, Vertex vika, List<Vertex> linkedTulos){
        //asetetaan solmu harmaaksi
        eka.setColor(DiGraph.GRAY);
        //Käydään solmun naapurit läpi for each loopilla
        for(Vertex x : eka.neighbors()){
            //Jos käsiteltävän solmun naapuri on sama kuin vika solmu niin lisätään vika solmu listaan
            //ja palautetaan true
            if (x.equals(vika)) {
                linkedTulos.add(vika);
                return true;
            }
            //Mikäli solmu on valkoinen niin lisätään se linkedlistaan ja kutsutaan rekursiota
            if(x.getColor() == DiGraph.WHITE){
                linkedTulos.add(x);
                rekursioJokuPolku(x,vika,linkedTulos);
                //Mikäli listan viimeinen solmu on sama kuin vika solmu niin palautetaan true
                if(linkedTulos.get(linkedTulos.size()-1).equals(vika)){
                    return true;
                }
                //Mikäli ei ole niin poistetaan viimeinen solmu listasta
                else {
                    linkedTulos.remove(linkedTulos.size()-1);
                }
            }
        }
        //Muuten palautetaan false
        return false;
    }



    /**
     * Syvyyssuuntainen lÃ¤pikÃ¤ynti (tekemÃ¤ttÃ¤ verkolle mitÃ¤Ã¤n)
     * Siis runko.
     *
     * @param G lÃ¤pikÃ¤ytÃ¤vÃ¤ verkko
     */
    static void dfsStart(DiGraph G) {
        for (Vertex v : G.vertices())                // kaikki solmut valkoisiksi
            v.setColor(DiGraph.WHITE);
        for (Vertex v : G.vertices())                // aloitus vielÃ¤ kÃ¤ymÃ¤ttÃ¶mistÃ¤ solmuista
            if (v.getColor() == DiGraph.WHITE)
                dfsRekursio(v);
    }



    // esimerkkejÃ¤


    /**
     * Syvyyssuuntainen lÃ¤pikÃ¤ynti solmusta node alkaen
     *
     * @param node aloitussolmu
     */
    static void dfsRekursio(Vertex node) {
        // tÃ¤hÃ¤n toimenpide solmulle node (jos esijÃ¤rjestys)
        node.setColor(DiGraph.GRAY);
        for (Vertex v : node.neighbors())                // vielÃ¤ kÃ¤ymÃ¤ttÃ¶mÃ¤t
            if (v.getColor() == DiGraph.WHITE)            // naapurit
                dfsRekursio(v);
        // tÃ¤hÃ¤n toimenpide solmulle node (jos jÃ¤lkijÃ¤rjestys)
    }


    /**
     * VÃ¤ritÃ¤ verkon kaikki solmut.
     * @param G vÃ¤ritettÃ¤vÃ¤ verkko
     * @param c vÃ¤ri jota kÃ¤ytetÃ¤Ã¤n
     */
    static void varita(AbstractGraph G, int c) {
        for (Vertex v : G.vertices())
            v.setColor(c);
    }



}
