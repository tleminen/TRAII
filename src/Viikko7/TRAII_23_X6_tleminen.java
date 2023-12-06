package Viikko7;

import java.util.*;

public class TRAII_23_X6_tleminen implements TRAII_23_X6 {

    /**
     * Itsearviointi tÃ¤hÃ¤n:
     * Algoritmini on oikein toimiva ja se ottaa huomioon kaikki tehtävässä vaaditut asiat.
     * Aikavaativuus on vaikea arvioida, mutta jos lonkalta heittäisin niin se olisi O(n^3) missä
     * n on käsiteltävien lukujen määrä listassa.
     * Parannusehdotuksena sanoisin, että algoritmini valitsisi laatikoista aina sellaisen laatikon missä
     * lisättävä luku saisi laatikon täydeksi eikä silleen miten minun algoritmi tekee että sattumanvaraisesti
     * lisää vapaaseen laatikkoon mihin luku mahtuu. Tämä saattaisi vähentää laatikoiden määrää?
     */

    /**
     * Jaottelee kokoelman C kokonaisluvut mahdollisimman vÃ¤hiin listoihin siten, ettÃ¤ kunkin listan alkioiden summa on
     * korkeintaan laatikonKoko.
     *
     * @param L            jaoteltavat alkiot
     * @param laatikonKoko kunkin tuloslistan maksimikapasiteetti
     * @param maxAika      suurin kÃ¤ytettÃ¤vissÃ¤ oleva aika (sekunteja)
     * @return tuloslistojen lista
     */
    @Override
    public List<List<Integer>> laatikkoJako(final ArrayList<Integer> L, int laatikonKoko, int maxAika) {
        // TODO
        //Kopioidaan saatu lista L järjestelyä varten
        ArrayList<Integer> kopioL = new ArrayList<>(L);
        //Luodaan lista mihin lisätään aina paras mahdollinen lista joka sisältää vähiten laatikoita
        List<List<Integer>> lopullinenTulos = new ArrayList<>();

        //Toistojenmäärä
        int toistot = 5;
        //Mikäli listan koko on vähemmän kuin 200, niin tehdään useampi kierros
        if(kopioL.size() < 200){
            toistot = 2000;
        }

        for(int i = 0; i < toistot; i++) {
            //Järjestetään lista suurimmasta pienimpään
            kopioL.sort(Collections.reverseOrder());

            //Tehdään lista laatikoille
            List<List<Integer>> tulos = new ArrayList<>();

            if (kopioL.isEmpty()) {
                // tyhjÃ¤llÃ¤ syÃ¶tteellÃ¤ tyhjÃ¤ tulos
                return tulos;
            }


            // epÃ¤tyhjÃ¤lle syÃ¶tteelle luodaan uusi avoin laatikko
            List<Integer> avoinLaatikko = new ArrayList<>();
            //Lisätään laatikko tulos listaan
            tulos.add(avoinLaatikko);

            // kÃ¤ydÃ¤Ã¤n syÃ¶tteen luvut lÃ¤pi
            for (int luku : kopioL) {
                if (luku > laatikonKoko || luku < 1) // tÃ¤mÃ¤ olisi virhe syÃ¶tteessÃ¤, ei pitÃ¤isi esiintyÃ¤
                    throw new RuntimeException("Liian iso/pieni alkio: " + luku + " (max = " + laatikonKoko + ")");

                //Tehdään boolean muuttuja mikä kertoo onko lukua lisätty laatikkoon vai ei
                boolean lisatty = false;

                //Käydään laatikoille tehty lista for each loopissa läpi
                for (List<Integer> laatikko : tulos) {

                    //Luodaan muuttuja johon tallennetaan jokaisen listasta löytyvän laatikon paino
                    int laatikonPaino = 0;
                    //selvitetään laatikon paino
                    //Käydään laatikossa sisältävät luvut läpi for each loopissa missä kasvatetaan aina
                    //int muuttujaa "laatikonpaino" laatikossa sisältävien lukujen määrällä
                    for (int laatikonLuku : laatikko) {
                        laatikonPaino = laatikonPaino + laatikonLuku;
                    }

                    //Mikäli laatikon paino ja käsiteltävän luvun yhteissumma on vähemmän tai saman verran kuin laatikonkoko...
                    if (laatikonPaino + luku <= laatikonKoko) {
                        //Lisätään laatikkoon kyseinen luku
                        laatikko.add(luku);
                        //Merkitään boolean lisatty true, koska lisäsimme laatikkoon luvun
                        lisatty = true;
                        //mennään breakilla pois listalaatikoiden läpi käynnistä
                        break;
                    }
                }
                //jos luku ei mahtunut mihinkään laatikoihin, niin...
                if (!lisatty) {
                    //Luodaan uusi tyhjä laatikko
                    avoinLaatikko = new ArrayList<>();
                    //Lisätään laatikkoon käsiteltävä luku
                    avoinLaatikko.add(luku);
                    //Lisätään laatikko tulos arraylistaan, missä on muutkin laatikot
                    tulos.add(avoinLaatikko);
                }
            }
            //Ensimmäinen lisäys tehdään
            if (i == 0){
                lopullinenTulos = tulos;
            }
            //Mikäli laatikoiden määrä on tulos listassa vähemmän kuin lopullisessa tulos listassa niin...
            else if(tulos.size() < lopullinenTulos.size()){
                //Tehdään uusi lista sille listalle, missä on vähemmän laatikoita (Säilytetään paras tulos)
                lopullinenTulos = new ArrayList<>(tulos);
            }
        }
        return lopullinenTulos;
    }

}
