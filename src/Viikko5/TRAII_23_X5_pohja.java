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

        float painot;
        for(Vertex v : lahtoSolmu.neighbors()){
            //Väritetään solmut valkoisiksi
            varitaSolmut(verkko, DiGraph.WHITE);
            //Väritetään lähtösolmu harmaaksi
            lahtoSolmu.setColor(DiGraph.GRAY);
            painot = 0.0F;

            //Tehdään lista solmuja varten
            List<Vertex> listaSolmuille = new LinkedList<>();
            //Lisätään lähtösolmu listaan
            listaSolmuille.add(lahtoSolmu);

            if(lahtoSolmu.getEdge(v).getWeight()<=maxPaino){
                //Lisätään solmujen välinen paino talteen
                painot = lahtoSolmu.getEdge(v).getWeight();
                etsiPolut(lahtoSolmu,v,maxPaino,tulos,listaSolmuille, painot);

            } else if (lahtoSolmu.getEdge(v).getWeight()==maxPaino) {
                tulos.add(listaSolmuille);
            }
        }
        return tulos;
    }
    private static void varitaSolmut(AbstractGraph g, int vari) {
        for (Vertex v : g.vertices())
            v.setColor(vari);
    }
    private static void etsiPolut(Vertex edellinenSolmu, Vertex solmu, float maxPaino, HashSet<List<Vertex>> tulos, List<Vertex> listaSolmuille, float painot){
        solmu.setColor(DiGraph.GRAY);
        for(Edge kaari : solmu.edges()){
            if(painot + kaari.getWeight()>maxPaino && kaari.getEndPoint(solmu) != edellinenSolmu){
                break;
            }
            if(kaari.getEndPoint(solmu).getColor() == DiGraph.GRAY && kaari.getEndPoint(solmu) != edellinenSolmu){
                break;
            }
            //Jos käsiteltävän kaaren päätesolmu on valkoinen ja paino on alle maxpainon
            if(kaari.getEndPoint(solmu).getColor() == DiGraph.WHITE && painot + kaari.getWeight()<=maxPaino){
                if(!listaSolmuille.contains(solmu)){
                    listaSolmuille.add(solmu);
                }
                edellinenSolmu = solmu;
                //Lisätään käsiteltävän kaaren päätesolmu listaan
                listaSolmuille.add(kaari.getEndPoint(solmu));
                //Kasvatetaan painoa
                painot = painot + kaari.getWeight();

                List<Vertex> lista = new LinkedList<>(listaSolmuille);
                //Lisätään lista hashsettiin
                tulos.add(listaSolmuille);
                etsiPolut(edellinenSolmu, kaari.getEndPoint(solmu), maxPaino, tulos, lista, painot);
                solmu.setColor(DiGraph.WHITE);
            }
        }
    }

}
