package Viikko4;

import fi.uef.cs.tra.*;

import java.util.HashSet;
import java.util.Set;


public class TRAII_23_X4_tleminen implements TRAII_23_X4 {

    /**
     * ITSEARVIOINTI TÃ„HÃ„N:
     * Algoritmini ensimmäisen osan aikavaativuus on O(v+e), jossa v on solmujen määrä ja e on kaarien määrä verkossa
     * Aikavaativuus toisessa osassa on O(v*e), jossa v on solmujen määrä ja e on kaarien määrä verkossa.
     * Ensimmäinen osa on oikein toimiva algoritmi ja tekee vaaditun asian mielestäni erinomaisesti. Mielestäni
     * siihen ei ole parannusehdotuksia, sillä itse en ainakaan saa sitä tehokkaammaksi.
     * Toinen osa myös toimii ja palauttaa myös kaikki halutut asiat, mutta se voisi olla nopeampi ja aikavaativuuttakin
     * voisi ehkä parantaa jotenkin, mutta en tiedä miten. Toisen osan testissä menee noin päälle
     * 2minuuttia, joka on mielestäni hidas vaikka algoritmi toimiikin kun sen saa ajettua.
     * Parannusehdotuksena siis tekisin algoritmistani tehokkaamman ja nopeamman. <- koskee toista osaa.
     * Tähän x4 tehtävään meni eniten aikaan ikinä mitä olen käyttänyt jonkin tehtävän tekemiseen mutta olen
     * ylpeä että sain oikean vastauksen aikaan vaikka se ei ehkä ole kaikkein tehokkain mahdollinen algoritmi.
     *
     **/

    /** Palauttaa joukkona kaikki ne suunnatun verkon solmut joihin ei johda yhtÃ¤Ã¤n kaarta.
     *
     * @param G syÃ¶teverkko
     * @return juurisolmujen joukko
     */
    @Override
    public Set<Vertex> juuriSolmut(DiGraph G) {
        Set<Vertex> tulos = new HashSet<>();
        // TODO, apumetodeja saa kÃ¤yttÃ¤Ã¤
        //Lisätään solmut hashsettiin
        for (Vertex solmu : G.vertices()){
            tulos.add(solmu);
        }
        //Käydään solmut läpi ja kutsutaan metodia joka solmun kohdalla
        for (Vertex v : G.vertices()){
            onkoJuurisolmu(v, tulos);
        }

        return tulos;
    }

    private static void onkoJuurisolmu(Vertex solmu, Set<Vertex> tulos){
        //metodissa käydään kaikki solmun kaaret läpi for each loopissa
        for (Edge e : solmu.edges()){
            //Mikäli "tulos" hashsetistä löytyy solmun kaaren loppusolmu niin poistetaan se hashsetistä
            if(tulos.contains(e.getEndPoint())){
                tulos.remove(e.getEndPoint());
            }
        }
    }


    /**
     * Palauttaa moniko suunnatun verkon G komponentti on puu  (eli
     * sellainen komponentti jossa ei ole paluu-, ristikkÃ¤is- tai etenemiskaaria.
     *
     * @param G syÃ¶teverkko
     * @return ehjien puiden lukumÃ¤Ã¤rÃ¤
     */
    @Override
    public int puidenLukumaara(DiGraph G) {

        Set<Vertex> potentiaalisetPuunJuuret = juuriSolmut(G);

        //Tehdään muuttuja johon lasketaan puiden lukumäärä
        int puunLukumaaralaskuri = 0;
        //Kutsutaan metodia joka värittää kaikki solmut valkoiseksi
        varitetaanSolmut(G);

        //Käydään potentiaaliset puunjuuret for each loopissa läpi ja kutsutaan metodia onkoPuu()
        for (Vertex v : potentiaalisetPuunJuuret){
            boolean puuLoytyi = onkoPuu(v,G);

            //Mikäli metodi palauttaa true niin lisätään puiden lukumäärää +1
            if(puuLoytyi){
                puunLukumaaralaskuri++;
            }
        }


        return puunLukumaaralaskuri; // TODO: tÃ¤hÃ¤n oikea palautusarvo
    }
    private static void varitetaanSolmut(AbstractGraph g) {
        //Väritetään kaikki verkon solmut for each loopissa
        for (Vertex v : g.vertices())
            v.setColor(AbstractGraph.WHITE);
    }
    private static boolean onkoPuu(Vertex solmu, DiGraph g) {
        //Tutkitaan onko solmussa käyty jo, jos on niin palautetaan false
        if(solmu.getColor() != DiGraph.WHITE){
            return false;
        }
        //Värjätään solmu harmaaksi
        solmu.setColor(DiGraph.GRAY);

        //Tehdään muuttuja johon lasketaan kuinka monta kaarta menee käsiteltävään solmuun
        int tulevatKaaret = 0;
        //Käsitellään verkon kaikki kaaret läpi for each loopissa
        for (Edge e : g.edges()){
            //Jos kaaren loppusolmu on sama kuin käsiteltävä solmu, niin...
            if(e.getEndPoint() == solmu){
                //Lisätään +1 koska kaari menee solmuun
                tulevatKaaret++;
            }
            //Mikäli solmuun mentäviä kaaria on yli 1 niin palautetaan false
            if(tulevatKaaret > 1){
                return false;
            }
        }

        //Käydään solmun naapurit läpi for each loopissa
        for (Vertex x : solmu.neighbors()){
            //Kutsutaan rekursiota ja mikäli rekursio palauttaa false, palautetaan false
            if (!onkoPuu(x, g)) {
                return false;
            }
        }
        //Muuten palautetaan true
        return true;
    }

}