package Viikko5;

import fi.uef.cs.tra.*;

import java.util.*;

public class TRAII_23_X5_pohja implements TRAII_23_X5 {

    /**
     * ITSEARVIOINTI TÃ„HÃ„N:
     *
     *
     *
     *
     **/
    /**
     * Kaikki erilaiset annetusta solmusta lahtoSolmu lÃ¤htevÃ¤t
     * korkeintaan maxPaino painoiset yksinkertaiset polut.
     * Polut palautetaan polkujen joukkona.
     * Polut palautetaan solmujen listana siten, ettÃ¤ polun
     * perÃ¤kkÃ¤isten solmujen vÃ¤lillÃ¤ on kaari syÃ¶teverkossa.
     * Polulla on vÃ¤hintÃ¤Ã¤n kaksi solmua (ja yksi kaari).
     * Polun paino on polun kaarten painojen summa.
     * Verkossa ei ole negatiivispainoisia kaaria.
     * Yksikertaisella polulla ei ole kehÃ¤Ã¤ (ts. siinÃ¤ ei ole mitÃ¤Ã¤n solmua kahdesti)
     * @param verkko syÃ¶teverkko
     * @param lahtoSolmu lÃ¤htÃ¶solmu
     * @param maxPaino polkujen maksimipaino
     * @return polkujen joukko
     */
    @Override
    public Set<List<Vertex>> kaikkiMaxPPolut(Graph verkko, Vertex lahtoSolmu, float maxPaino) {
        HashSet<List<Vertex>> tulos = new HashSet<>();
        // TODO: tÃ¤stÃ¤ alkaa polkujen haku, apumetodeja saa ja kannattaa kÃ¤yttÃ¤Ã¤

        //Väritetään solmut valkoisiksi
        varitaSolmut(verkko, DiGraph.WHITE);
        //Käydään lähtösolmun naapurit läpi
        for(Vertex solmu : lahtoSolmu.neighbors()){
            if(lahtoSolmu.getEdge(solmu).getWeight()<=maxPaino){
                //Tehdään lista solmuja varten
                List<Vertex> listaSolmuille = new LinkedList<>();
                //Lisätään lähtösolmu listaan
                listaSolmuille.add(lahtoSolmu);
                //Väritetään lähtösolmu harmaaksi
                lahtoSolmu.setColor(DiGraph.GRAY);

                //Lisätään listaan lähtösolmun naapurisolmu
                listaSolmuille.add(solmu);
                tulos.add(listaSolmuille);

                etsiPolut(solmu,maxPaino,tulos,listaSolmuille, lahtoSolmu.getEdge(solmu).getWeight());
                //Värjätään lähtösolmu valkoiseksi
                lahtoSolmu.setColor(DiGraph.WHITE);
                //Värjätään lähtösolmun naapurisolmu valkoiseksi
                solmu.setColor(DiGraph.WHITE);

            }
        }
        return tulos;
    }
    private static void varitaSolmut(AbstractGraph g, int vari) {
        //Värjätään verkon kaikki solmut for each loopissa
        for (Vertex v : g.vertices())
            v.setColor(vari);
    }
    private static void etsiPolut(Vertex solmu, float maxPaino, HashSet<List<Vertex>> tulos, List<Vertex> listaSolmuille, float painot){
        //Värjätään solmu harmaaksi
        solmu.setColor(DiGraph.GRAY);

        //Käydään solmun kaaret läpi
        for(Edge kaari : solmu.edges()){

            //Jos käsiteltävän kaaren päätesolmu on valkoinen niin...
            if(kaari.getEndPoint(solmu).getColor() == DiGraph.WHITE){
                //Mikäli tallennettu paino + käsiteltävän kaaren paino on pienempi tai yhtäsuuri kuin maxpaino
                if(painot + kaari.getWeight()<=maxPaino){
                    //Luodaan uusi lista uutta polkua varten
                    List<Vertex> lista = new LinkedList<>(listaSolmuille);

                    //Lisätään käsiteltävän kaaren päätesolmu listaan
                    lista.add(kaari.getEndPoint(solmu));
                    //Lisätään lista hashsettiin
                    tulos.add(lista);

                    //Tehdään rekursio kutsu ja lisätään paino muuttujan käsiteltävän kaaren paino
                    etsiPolut(kaari.getEndPoint(solmu), maxPaino, tulos, lista, painot+kaari.getWeight());
                    //Värjätään kaaren päätesolmu valkoiseksi
                    kaari.getEndPoint(solmu).setColor(DiGraph.WHITE);
                }
            }
        }
    }

}
