package Viikko5;

import fi.uef.cs.tra.*;

import java.util.LinkedList;
import java.util.List;


public class TRAII_23_t19_20_pohja {

    public static void main(String[] args) {

        // defaults
        int vertices = 7;
        int edges = 7;

        if (args.length > 0)
            vertices = Integer.parseInt(args[0]);

        if (args.length > 1)
            edges = Integer.parseInt(args[1]);

        int seed = vertices+edges;

        if (args.length > 2)
            seed = Integer.parseInt(args[2]);

        Graph graph;


        System.out.println("\nVerkko1: ");
        graph = GraphMaker.createGraph(vertices, edges, seed);
        System.out.print(GraphMaker.toString(graph, 0));

        System.out.print("YhtenÃ¤inen: ");
        boolean yhten = onkoYhtenainen(graph);
        System.out.println(yhten + "\n");
        if (! yhten) {   // kutsutaan tehtÃ¤vÃ¤Ã¤ 19
            System.out.println("TÃ¤ydennys:");
            taydennaYhtenaiseksi(graph);
            System.out.print(GraphMaker.toString(graph, 0));
        }

        yhten = onkoYhtenainen(graph);
        System.out.println("YhtenÃ¤inen nyt: " + yhten);

        if (yhten) {
            System.out.print("\nLeikkaussolmut:   ");
            List<Vertex> ls = leikkausSolmut(graph);
            System.out.println(ls);
            if (! ls.isEmpty()) {
                System.out.println("TÃ¤ydennys:");
                taydenna2yhtenaiseksi(graph);
            }
            System.out.print("Leikkaussolmut nyt:   ");
            System.out.println(leikkausSolmut(graph));
            System.out.println(GraphMaker.toString(graph, 0));
        }


    }

    /**
     * Onko annettu verkko yhtenÃ¤inen vai ei.
     * @param G syoteverkko
     * @return true jos verkko on yhtenÃ¤inen, muuten false
     */
    static boolean onkoYhtenainen(Graph G) {
        // kaikki valkoisiksi
        varita(G, Graph.WHITE);

        // syvyyssyyntainen lÃ¤pikynti jostain solmusta
        Vertex w = G.firstVertex();
        dfsColor(w, Graph.BLACK);

        // onko vielÃ¤ valkoiseksi jÃ¤Ã¤neitÃ¤ solmuja
        for (Vertex v : G.vertices())
            if (v.getColor() == Graph.WHITE)
                return false;
        return true;
    }

    /**
     * TÃ¤ydennÃ¤ epÃ¤yhtenÃ¤inne verkko yhtenÃ¤iseksi lisÃ¤Ã¤mÃ¤llÃ¤ kaaria komponenttien vÃ¤lille.
     * Jos verkko on jo yhtenÃ¤inen, ei tarvitse tehdÃ¤ mitÃ¤Ã¤n.
     * @param G syÃ¶teverkko
     */
    static void taydennaYhtenaiseksi(Graph G) {

        // TODO
        varita(G, DiGraph.WHITE);
        List<Vertex> lista = new LinkedList<>();
        for(Vertex x : G.vertices()){
            if(katsoKaaret(x,G, lista)){
                break;
            }
        }
    }   // taydennaYhtenaiseksi()
    private static boolean katsoKaaret(Vertex solmu, Graph g, List<Vertex> lista){
        solmu.setColor(DiGraph.GRAY);
        int kaaret = 0;
        for(Edge e : g.edges()){
            if(e.getEndPoint() == solmu){
                kaaret++;
            }
        }
        if(kaaret == 0){
            solmu.addEdge(lista.get(lista.size()-1));
            return true;
        }
        for (Vertex v : solmu.neighbors()){
            if(v.getColor()!=DiGraph.GRAY){
                lista.add(solmu);
                katsoKaaret(v,g,lista);
            }
        }
        return false;
    }





    /**
     * syvyyssuuntainen lÃ¤pikynti vÃ¤ritten vÃ¤rillÃ¤ c
     * @param v aloitussolmu
     * @param color kÃ¤ytettÃ¤vÃ¤ vÃ¤ri
     */
    static void dfsColor(Vertex v, int color) {
        v.setColor(color);

        for (Vertex w : v.neighbors())
            if (w.getColor() != color)
                dfsColor(w, color);
    }

    /**
     * VÃ¤ritÃ¤ verkon kaikki solmut annetun vÃ¤risiksi.
     * @param G vÃ¤ritettÃ¤vÃ¤ verkko
     * @param c kÃ¤ytettÃ¤vÃ¤ vÃ¤ri
     */
    static void varita(AbstractGraph G, int c) {
        for (Vertex v : G.vertices())
            v.setColor(c);
    }


    /**
     * TÃ¤ydennÃ¤ verkko 2-yhtenÃ¤iseksi.
     * LisÃ¤Ã¤ kaaria siten, ettei verkossa enÃ¤Ã¤ ole leikkaussolmuja eikÃ¤ siltoja.
     * @param G syÃ¶teverkko
     */
    static void taydenna2yhtenaiseksi(Graph G) {

        // TODO

    }

    /**
     * Leikkaussolmut yhtenÃ¤isestÃ¤ verkosta.
     * @param g syÃ¶teverkko
     * @return leikkaussolmujen lista (tyhjÃ¤ lista jollei ole leikkaussolmuja)
     */
    static LinkedList<Vertex> leikkausSolmut(Graph g) {

        // numeroidaan solmut, kunkin solmun index-kenttÃ¤Ã¤n
        GraphMaker.getVertexArrayIndex(g);
        int n = g.size();

        // solmujen ja kaarten pohjavÃ¤ri
        varita(g, Graph.WHITE);
        for (Edge e : g.edges())
            e.setColor(Graph.WHITE);

        // taulukot
        int[] dfsnumber = new int[n];
        int[] low = new int[n];
        int i = 0;
        LinkedList<Vertex> L = new LinkedList<Vertex>();
        // komponentti kerrallaan
        for (Vertex v : g.vertices())
            if (v.getColor() == Graph.WHITE)
                i = numberdfs(v, dfsnumber, low, i, L, null);

        return L;
    }

    // fdsnumerointi taulukkoon, samalla luokittelee kaaret
    // puukaaret mustiksi, paluukaaret harmaiksi
    // isa on solmu josta tÃ¤tÃ¤ kutsutaan
    static int numberdfs(Vertex v, int[] dfsnumber, int[] low, int i,
                         LinkedList<Vertex> L, Vertex isa) {
        v.setColor(Graph.BLACK);
        dfsnumber[v.getIndex()] = i++;

        // dfs rekursio ja kaarien luokittelu
        // naapurien lÃ¤pikÃ¤ynnin kaarien avulla
        for (Edge e : v.edges()) {

            // kaari on jo kÃ¤sitelty toiseen suuntaan (isÃ¤-kaari,
            // tai jÃ¤lkelÃ¤isen paluukaari
            if (e.getColor() != Graph.WHITE)
                continue;

            // naapurisolmu
            Vertex w = e.getEndPoint(v);

            if (w.getColor() == Graph.WHITE) {
                e.setColor(Graph.BLACK);    // puukaari
                // rekursiokutsu
                i = numberdfs(w, dfsnumber, low, i, L, v);
            } else if (w.getColor() == Graph.BLACK)
                e.setColor(Graph.GRAY); // paluukaari
        } // for(v.edges)

        // kaikkien rekursiokutsujen jÃ¤lkeen (jÃ¤lkijÃ¤rjestys)
        // low-arvon laskenta
        int min = dfsnumber[v.getIndex()];
        for (Edge e : v.edges()) {
            Vertex w = e.getEndPoint(v);
            if (w == isa)   // isÃ¤Ã¤ ei lasketa
                continue;

            // lasten low-luvut
            if (e.getColor() == Graph.BLACK) {
                if (low[w.getIndex()] < min)
                    min = low[w.getIndex()];

                // esi-isien (joihin paluukaari) dfsnumerot
            } else if (e.getColor() == Graph.GRAY) {
                if (dfsnumber[w.getIndex()] < min)
                    min = dfsnumber[w.getIndex()];
            }

        }   // for(v.edges)

        low[v.getIndex()] = min;

        // leikkaussolmujen tunnistus
        if (v.getIndex() == 0) { // juurisolmu (vain yksi)
            int poikia = 0;
            // lasten lkm dfs-puussa
            for (Edge e : v.edges())
                if (e.getColor() == Graph.BLACK)
                    poikia++;
            if (poikia > 1)
                L.add(v);

            // muut solmut
        } else {
            // poika w, jolle low[w] >= dfnumber[v]
            for (Edge e : v.edges())
                if (e.getColor() == Graph.BLACK) {
                    Vertex w = e.getEndPoint(v);
                    if (low[w.getIndex()] >= dfsnumber[v.getIndex()]) {
                        L.add(v);
                        break;
                    }
                } // if BLACK
        } // else

        return i;   // palautetaan numeroitujen solmujen mÃ¤Ã¤rÃ¤
    }   // numberdfs()

    // \leikkaussolmut


}
