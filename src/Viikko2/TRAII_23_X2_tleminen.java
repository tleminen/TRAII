package Viikko2;

import java.util.*;

public class TRAII_23_X2_tleminen implements TRAII_23_X2 {
    /**
     * ITSEARVIOINTI TĆ„HĆ„N:
     * Olen mitannut tehtävässä oikeaa asiaa ja monella eri syötekoolla, jotta minimoitaisiin  virhelähteet
     * Lämmitys on tehty tössä tehtävässä valmiiksi, joka auttaa saamaan tarkemman/paremman ajan
     * En tulosta mittauksen aikana mitään, koska sekin vaikuttaisi muuten aikaan negatiivisesti.
     * Minimoin myös virhelähteet ottamalla aikaa silloin kun tietokoneeni on laturissa ja muita prosesseja
     * ei pyöri samaan aikaan tietokoneella (esim zoom tai discord)
     * Otin usean ajon mediaanin, koska se suodattaa pois mittavirheet
     * En osaa sanoa mitä epävarmuuksia mittaukseeni vielä jäi
     * Treemapissa aikavaativuus on O(logn), missä n on syötekoko
     * aikavaativuuden huomaa kun ajaa testiohjelman ja kun syötekoko kasvaa 2x niin silloin containskey
     * aika nanosekuntteina kasvaa 1-2ns sitä mukaan kun syötekoko kasvaa
     * Hashmapissa aikavaativuus on O(1), koska kaikki testit ovat max 1ns päässä toisistaan ja sekin johtuu pyöristyksestä
     *
     **/
    /**
     * Mittaa annetun kuvauksen containsKey -operaation aikavaativuuden nanosekunteina.
     * Mittaa ns. normaalin onnistuneen suorituksen. Ei siis minimiĆ¤ tai maksimia.
     * Ei muuta kuvausta (lisĆ¤Ć¤ tai poista alkioita).
     *
     * @param M testattava kuvaus
     * @return containsKey operaation normikesto nanosekunteina
     */

    @Override
    public long containsKeyNopeus(Map<Double, Double> M) {

        // TODO
        //Tehdään random olio
        Random satunnainen = new Random();
        //Tehdään taulukkolista aikoja varten, jotta voimme palauttaa aikojen mediaanin
        List<Long> aikaTaulukko = new ArrayList<>();
        //Kierrosmäärä
        int kierrokset = 2048;
        while(kierrokset < 1000000) {
            //Tallennetaan satunnainen luku avaimeksi suoritusta varten
            double avain = satunnainen.nextDouble();
            //Aloitetaan ajanotto
            long aloitus = System.nanoTime();
            //Suoritus alkaa
            for (int i = 0; i < kierrokset; i++) {
                M.containsKey(avain);
            }
            //Lopetetaan ajanotto
            long lopetus = System.nanoTime();
            //Lisätään aika taulukkolistaan (Jaetaan aika kierrosmäärillä jotta saadaan keskimääräinen containskey mennyt aika)
            aikaTaulukko.add((lopetus - aloitus)/kierrokset);
            //nostetaan kierrosten määrä potenssiin 2
            kierrokset = (int) Math.pow(kierrokset,2);
        }
        //Järjestettään taulukkolista
        Collections.sort(aikaTaulukko);
        int taulukkoKoko = aikaTaulukko.size();
        // taulukkolista on aina parillinen, joten mediaani on taulukkolistan keskimmäinen arvo
        int mediaani = taulukkoKoko / 2;
        //Palautetaan mediaani
        return aikaTaulukko.get(mediaani);
    }
}
