package Viikko3;

import java.util.*;


public class TRAII_23_X3_tleminen implements TRAII_23_X3 {

    /**
     * ITSEARVIOINTI:
     * Ratkaisuni on toimiva joka kattaa kaikki tehtävässä vaaditut asiat. Aikavaativuus tulosteen perusteella
     * on vakioaikainen O(1), koska ns/n on hyvin tasaista ja pysyy samoissa luvuissa. Kaikilla neljällä eri jonolla algoritmini
     * toimii samalla tavalla eikä suuria heittoja tule kun tietokoneeni on laturissa ja muita ohjelmia ei pyöri taustalla samaan aikaan
     * esimerkiksi discord tai VSCode
     * Mittaukseen sai käyttää enintään 2sec kutakin alkiomäärää kohti, ja se toteutui ratkaisussani, kun tietokoneeni oli laturissa ja muita ohjelmia
     * ei pyörinyt taustalla. Eli jokaisella jonolla mittaus kesti alle 2s kutakin alkiomäärää kohti.
     * Tein lämmityksen ennen varsinaisen testin ajoa, koska sillä saadaan aikaan tarkempi/parempi aika
     * Kutsuin lämmitys metodia useaan kertaan, jotta saadaan vielä tarkempi aika.
     * Parannusehdotuksena olisin voinut ottaa aikojen mediaanin, mutta koin, että kun otin tietyn alkiomäärän kierroksien keskiarvon
     * niin sain parempaa tulostusta aikaan.
     * En tulosta myöskän mittauksen aikana mitään, koska mikäli tulostaisin niin sekin vaikuttaisi aikaan negatiivisesti.
     * Ratkaisussani on myös suoritettu mittaus usealle alkiomäärälle ja on mitattu oikeaa asiaa. Ratkaisussani mittaukset
     * minimoivat häiriötekijät ja sen näkee tulostuksesta, koska isoja heittoja ei ole paljoa yhtään. Esimerkiksi otin huomioon sen, että
     * kun alkioidenmäärä on tietyn kokoinen, niin silloin tehdään tietty määrä kierroksia sille alkiomäärälle.
     * Mielestäni epävarmuuksia ei mittaukseen jäänyt
     *
     *
     *
     *
     * Testaa jonon Q toiminnan nopeutta.
     * Mittaa ajan alkioiden mÃ¤Ã¤rÃ¤lle n = { min, min*2, min*4, min*8, ... <=max}.
     *
     * Kullekin alkiomÃ¤Ã¤rÃ¤lle n mitataan aika joka kuluu seuraavaan operaatiosarjaan:
     * 1. lisÃ¤tÃ¤Ã¤n jonoon n alkiota
     * 2. n kertaa vuorotellen lisÃ¤tÃ¤Ã¤n jonoon yksi alkio ja otetaan yksi alkio pois
     * 3. poistetaan jonosta n alkiota
     *
     * Viimeinen mitattava alkioiden mÃ¤Ã¤rÃ¤ on siis suurin min*2^k joka on pienempi tai yhtÃ¤ suuri kuin max.
     *
     * Tuloksena palautetaan kuvaus jossa on avaimena kukin testattu syÃ¶tteen koko ja kuvana kyseisen
     * syÃ¶tteen koon mittaustulos nanosekunteina.
     *
     * @param Q testattava jono
     * @param min lisÃ¤ttÃ¤vien/poistettavien alkioiden minimimÃ¤Ã¤rÃ¤
     * @param max lisÃ¤ttÃ¤vien/poistettavien alkioiden mÃ¤Ã¤rÃ¤n ylÃ¤raja
     * @return jÃ¤rjestetty kuvaus jossa on kaikki testitulokset
     */
    @Override
    public SortedMap<Integer, Long> jononNopeus(Queue<Integer> Q, int min, int max) {
        SortedMap<Integer, Long> tulos = new TreeMap<>();
        // TODO tÃ¤hÃ¤n ja muuallekin saa tehdÃ¤ muutoksia kunhan ei muuta otsikkoa
        // apumetodeja saa kÃ¤yttÃ¤Ã¤
        //Lämmitys, jotta saadaan tarkempi aika
        lammitetaan(3,min,max,Q);
        lammitetaan(3,min,max,Q);
        lammitetaan(3,min,max,Q);
        //Ajanotto
        ajanOtto(Q,min,max,tulos);
        return tulos;
    }
    private static void ajanOtto(Queue<Integer> Q, int min, int max,SortedMap<Integer, Long> tulos){
        // tÃ¤mÃ¤ toisto tÃ¤ssÃ¤ esimerkkinÃ¤, mutta jokin tÃ¤llainen tarvitaan
        int kierrosluku = 50;
        for (int alkioMaara = min; alkioMaara <= max; alkioMaara *= 2) {
            // TODO tÃ¤hÃ¤n ainakin pitÃ¤Ã¤ tehdÃ¤ jotain
            //Jos alkiomäärä on miljoonan ja 10 miljoonan välissä niin asetetaan kierrosluku 1
            if (1000000 < alkioMaara && alkioMaara < 100000000){
                kierrosluku = 1;
            }
            //Jos alkiomäärä on alle 100 nin asetetaan kierrosluku 100
            else if ((alkioMaara < 100)) {
                kierrosluku = 100;
            }
            //AJANOTTO ALKAA
            long aloitus = System.nanoTime();
            //Tehdään samalla alkiomäärällä "Kierrosluku" kierrosta mikäli jokin if lauseista toteutuu
            //Muuten tehdään 50 kierrosta
            for(int j = 0; j < kierrosluku; j++) {
                //Jonoon lisäys
                for (int i = 0; i < alkioMaara; i++) {
                    Q.offer(i);
                }
                //Jonosta poisto ja lisäys
                for (int i = 0; i < alkioMaara; i++) {
                    Q.poll();
                    Q.offer(i);
                }
                //Jonosta pois
                for (int i = 0; i < alkioMaara; i++) {
                    Q.poll();
                }
            }
            //AJANOTTO LOPPUU
            long lopetus = System.nanoTime();

            // tulosten tallettaminen
            /* TODO tÃ¤hÃ¤n se mitattu tulos nanosekunteina */
            tulos.put(alkioMaara, (lopetus-aloitus)/kierrosluku);
        }
    }
    //Lämmitys metodi
    private static void lammitetaan(int sek, int min, int max, Queue<Integer> J) {
        SortedMap<Integer, Long> lammitysTulos = new TreeMap<>();
        System.out.println("Lämmitys alkaa " + sek + "s");
        long loppu = System.nanoTime() + sek*1000L*1000*1000;
        while (System.nanoTime() < loppu) {
            ajanOtto(J, min, max,lammitysTulos);
        }
        System.out.println("Lämmitys loppuu");
    }
}
