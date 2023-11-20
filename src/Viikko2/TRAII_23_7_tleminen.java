package Viikko2;


// TRAII_23_X2_testi.java SJ
// Testiluokka TRAII 2023 tehtÃ¤viin X2 ja 5-7


import java.util.*;

public class TRAII_23_7_tleminen {

    static TRAII_23_X2 testattava = new TRAII_23_X2_tleminen();
    //                                              ^^^^ oma tunnus tÃ¤hÃ¤n


    public static void main(String[] args) {

        // komentoriviparametrina maksimikoko kuvaukselle
        int N = 1000000;
        if (args.length > 0)
            N = Integer.parseInt(args[0]);

        // satunnaislukusiemen
        int siemen = 42;
        if (args.length > 1)
            siemen = Integer.parseInt(args[1]);
        Random rnd = new Random(siemen);

        // tulostusten mÃ¤Ã¤rÃ¤
        int print = 3;
        if (args.length > 2)
            print = Integer.parseInt(args[2]);


        // kutsutaan ensin ilman tulostuksia 3 sekunnin ajan
        lammita(N, rnd, 3);

        // sitten varsinainen testiajo
        testaaX2(N, rnd, print);


    } // main()


    /**
     * Testaa tehtÃ¤vÃ¤Ã¤ syÃ¶tekoolla 1..n
     * @param n maksimi syÃ¶tekoko
     * @param rnd satunnaislukugeneraattori
     * @param print tulostusten mÃ¤Ã¤rÃ¤
     */
    static void testaaX2(int n, Random rnd, int print) {
        int k = 1;
        //Map<Double, Double> M = new HashMap<>();
       PriorityQueue<Double> M = new PriorityQueue<>();
        while (M.size() < n) {
            while (M.size() < k)
                M.add(rnd.nextDouble());
            testaaX2(M, print);
            k *= 2;
        }
    }


    /**
     * Kutsuu testausta ilman tulostuksia kunnes annettu aika on kuulunut.
     * @param n maksimi syÃ¶tekoko
     * @param rnd satunnaislukugeneraattori
     * @param sek suorituksen kesto
     */
    static void lammita(int n, Random rnd, int sek) {

        System.out.println("LÃ¤mmitys alkaa " + sek + "s");
        long loppu = System.nanoTime() + sek*1000L*1000*1000;
        while (System.nanoTime() < loppu)
            testaaX2(n, rnd, 0);
        System.out.println("LÃ¤mmitys loppuu");
    }


    /**
     * Testaa tehtÃ¤vÃ¤Ã¤ X2 kuvauksella M
     * @param M kuvaus jota testataan
     * @param print tulostusten mÃ¤Ã¤rÃ¤
     */
    static void testaaX2(PriorityQueue<Double> M, int print) {

        if (print > 1)
            System.out.println("\nTesti, Map = " + M.getClass().toString() + " n = " + M.size());

        long alku = System.nanoTime();

        long tulos = priorityqueueNopeus(M);

        long loppu = System.nanoTime();

        if (print > 0) System.out.println("  tulos = " + tulos + " ns");

        if (loppu-alku > 1000L*1000*1000)
            System.out.println("Varoitus: testi oli tarpeettoman hidas (yli 1s)");

        if (print > 3)  // sÃ¤Ã¤dÃ¤ tÃ¤stÃ¤ jos haluat nÃ¤hdÃ¤ paljonko testisi kesti
            System.out.println("Testi kesti " + ((loppu-alku)/1000000.0) + " ms");


    }
    static long priorityqueueNopeus(PriorityQueue<Double> M) {

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
                M.contains(avain);
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

