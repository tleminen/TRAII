package Viikko1;

// TRAII_23_t3_4_pohja.java SJ

/**
 3. Kirjoita algoritmi, joka saa syÃ¶tteenÃ¤Ã¤n kokoelman, ja palauttaa tuloksenaan sen alkion,
 joka esiintyi syÃ¶tteessÃ¤ useimmin. (Jos mahdollisia tuloksia on monta, niin algoritmisi voi
 palauttaa niistÃ¤ minkÃ¤ tahansa.) Vertaile alkioita alkion .equals() -operaatiolla. KÃ¤ytÃ¤ apuna
 kuvausta (Map<E, Integer>). MikÃ¤ on algoritmisi aikavaativuus? Ota pohjaa ja esimerkkiÃ¤
 Moodlesta.
 4. Vertaa tehtÃ¤vÃ¤n 3 toimintaa kun apuvÃ¤linekuvauksena on (a) HashMap tai (b) TreeMap.
 Kirjoita ohjelma joka mittaa nÃ¤iden nopeutta kun syÃ¶te kasvaa. Miten selitÃ¤t tulokset? Ota
 pohjaa ja esimerkkiÃ¤ Moodlesta.

 */

import java.util.*;

public class TRAII_23_t3_4_pohja {


    // PÃ¤Ã¤ohjelman kÃ¤yttÃ¶:
    // java TRAII_23_t3_4_pohja [N] [siemen]
    // missÃ¤ N on alkioiden mÃ¤Ã¤rÃ¤
    // ja siemen on satunnaislukusiemen

    public static void main(String[] args) {

        // á¸±okoelman koko
        int N = 1000000;
        if (args.length > 0)
            N = Integer.parseInt(args[0]);


        // satunnaislukusiemen
        int siemen = N;
        if (args.length > 1)
            siemen = Integer.parseInt(args[1]);


        // ensin pieni lista
        Random r = new Random(siemen);
        LinkedList<Integer> L = randomLinkedList(20, r);

        // tulostetaan lista jos alkioita ei ole paljoa
        if (L.size() <= 30) {
            System.out.println(L);
        }
        Ajastin2 at = null;
        Integer useimmin = null;


        at = new Ajastin2("" + L.size());
        useimmin = useimmin(L);
        at.stop();
        System.out.println("aika: " + at + ", " +
                (at.time() * 1.0 / N) + " ns/elem"+" Hashmapissa");
        System.out.println("Useimmin esiintyi " + useimmin);

        // sitten vÃ¤hÃ¤n isompi
        L = randomLinkedList(N, r);

        // tulostetaan lista jos alkioita ei ole paljoa
        if (N <= 30) {
            System.out.println(L);
        }

        at = new Ajastin2("" + L.size());
        useimmin = useimmin(L);
        at.stop();
        System.out.println("aika: " + at + ", " +
                (at.time() * 1.0 / N) + " ns/elem" + " Hashmapissa");
        System.out.println("Useimmin esiintyi " + useimmin);

        // TODO: tehtÃ¤vÃ¤ 4: vertaa tehokkuuksia
        //Tallennetaan hashmapin aika muuttujaan double hashmapAika
        double hashmapAika = at.time() * 1.0 / N;
        //Tehdään sama asia treemapille, mitä tehtävässä on tehty hashmapille
        //Eli kellotetaan treemapin aika
        at = new Ajastin2("" + L.size());
        useimmin = useimminTree(L);
        at.stop();
        System.out.println("aika: " + at + ", " +
                (at.time() * 1.0 / N) + " ns/elem" + " Treemapissa");
        System.out.println("UseimminTree esiintyi " + useimmin);
        ////Tallennetaan treemapin aika muuttujaan double treemapAika
        double treemapAika = at.time() * 1.0 / N;
        //Lasketaan treemapin ja hashmapin välinen aikaero
        String vastaus = String.format("%.2f",treemapAika-hashmapAika);
        System.out.println("Treemapilla meni: " + vastaus +" ns/elem aikaa kauemmin");



    } // main()
    //Tehdään sama mitä tehtiin hashmapille mutta tällä kertaa treemapille
    public static <E> E useimminTree(Collection<E> C) {
        if(C.isEmpty()){
            return null;
        }
        TreeMap<E,Integer> map = new TreeMap<>();
        for (E alkio : C){
            if (map.containsKey(alkio)){
                map.put(alkio,map.get(alkio)+1);
            }
            else{
                map.put(alkio,1);
            }
        }
        //Alkio joka esiintyy eniten
        E maxAvain = null;
        //Esiintymiskerrat maximi
        int maxArvo = 0;
        for(Map.Entry<E,Integer> x : map.entrySet()){
            if(x.getValue() > maxArvo){
                maxAvain = x.getKey();
                maxArvo = x.getValue();
            }
        }

        return maxAvain;

    }


    /**
     * MikÃ¤ alkio esiintyy useimmin kokoelmassa C?
     * Jos usea alkio esiintyy yhtÃ¤ usein, palautetaan niistÃ¤ yksi.
     * @param C SyÃ¶tekokoelma
     * @param <E> alkiotyyppi
     * @return yleisin alkio, tai null jos kokoelman on tyhjÃ¤
     * aikavaativuus on O(n), missä n on alkioiden lukumäärä. algoritmista löytyy for loop missä käydään alkiot läpi
     * containskey() on vakioaikainen O(1), ja kaikki muut ovat myös vakioaikaisia
     */
    public static <E> E useimmin(Collection<E> C) {
        //Jos collection tyhjä palautetaan null
        if(C.isEmpty()){
            return null;
        }
        //luodaan hashmappi
        HashMap<E,Integer> map = new HashMap<>();
        //Käydään collection läpi for each loopissa
        for (E alkio : C){
            //Mikäli hashmapissa oleva alkio/avain on sama kuin alkio, niin...
            if (map.containsKey(alkio)){
                //Lisätään sen alkion/avaimen esiintymiskertaa +1
                map.put(alkio,map.get(alkio)+1);
            }
            //Muuten jos alkio/avainta ei löydy vielä hashmapista, niin lisätään se hashmappiin ja sen esiintymiskerraksi 1
            else{
                map.put(alkio,1);
            }
        }
        //Luodaan apumuuttujat
        //Alkio joka esiintyy eniten
        E maxAvain = null;
        //Esiintymiskerrat maximi
        int maxArvo = 0;
        //Käydään hashmap for each loopissa läpi
        for(Map.Entry<E,Integer> x : map.entrySet()){
            //Mikäli kyseisen alkio/avaimen esiintymiskerta on enemmän kuin maxArvo, niin...
            if(x.getValue() > maxArvo){
                //tallennetaan kyseinen alkio/avain uudeksi suurimmaksi alkioksi/avaimeksi
                maxAvain = x.getKey();
                //Ja tallennetaan uudeksi maxArvoksi kyseisen alkion/avaimen esiintymiskertojen määrä
                maxArvo = x.getValue();
            }
        }

        //Palautetaan alkio joka esiintyi syötteessä useimmin
        return maxAvain; // TODO

    }


    public static LinkedList<Integer> randomLinkedList(int n, int seed) {
        Random r = new Random(seed);
        LinkedList<Integer> V = new LinkedList<>();
        for (int i = 0; i < n; i++)
            V.add(r.nextInt(n));
        return V;
    }

    public static LinkedList<Integer> randomLinkedList(int n, Random r) {
        LinkedList<Integer> V = new LinkedList<>();
        for (int i = 0; i < n; i++)
            V.add(r.nextInt(n));
        return V;
    }


} // class

