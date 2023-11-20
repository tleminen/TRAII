package Viikko1;

import fi.uef.cs.tra.BTree;
import fi.uef.cs.tra.BTreeNode;

import java.util.HashSet;
import java.util.Set;

public class TRAII_23_X1_tleminen implements TRAII_23_X1 {
    /**
     * ITSEARVIOINTI TÃ„HÃ„N:
     * Mielestäni algoritmini on parhaimpia tapoja ratkaista tehtävä, koska se on toimiva ja sen aikavaativuus
     * on paras mahdollinen mitä on mahdollista saada kyseiseen tehtävään. Aikavaativuus on O(n), koska puu käydään läpi
     * kerran kun etsitään lehtisolmuja. Kaikki muut operaatiot ovat vakioaikaisia eli O(1), esimerkiksi hashsetin
     * .add() on vakioaikainen. Täten aikavaativuus algoritmissani on O(n), jossa n on solmujen määrä puussa
     *
     **/
    /**
     * Puun lehtisolmut.
     * Palauttaa joukkona kaikki ne puun T solmut joilla ei ole yhtÃ¤Ã¤n lasta.
     * @param T syÃ¶tepuu
     * @param <E> puun alkioiden tyyppi (ei kÃ¤ytetÃ¤ muuten kuin muuttujien parametrointiin)
     * @return lehtisolmujen joukko
     */
    @Override
    public <E> Set<BTreeNode<E>> lehtiSolmut(BTree<E> T) {
        //TODO
        Set<BTreeNode<E>> lehdet = new HashSet<>();
        BTreeNode<E> juurisolmu = T.getRoot();
        //Jos puu on tyhjä niin palautetaan tyhjä joukko
        if(juurisolmu == null){
            return lehdet;
        }
        //rekursio kutsu
        rekursio(juurisolmu,lehdet);

        //palautetaan joukko, jossa on kaikki puun T solmut, joilla ei ole yhtäkään lasta
        return lehdet;
    }
    private <E> void rekursio(BTreeNode<E> solmu,  Set<BTreeNode<E>> lehtisolmut){
        //Jos solmulla on vasen lapsi siirrytään rekursion avulla vasemman lapsen solmuun
        if(solmu.getLeftChild() != null){
            rekursio(solmu.getLeftChild(),lehtisolmut);
        }
        //Jos solmulla on oikea lapsi siirrytään rekursion avulla oikean lapsen solmuun
        if(solmu.getRightChild() != null){
            rekursio(solmu.getRightChild(),lehtisolmut);
        }
        //Jos solmulla ei ole oikeaa tai vasenta lasta, niin lisätään kyseinen solmu "lehtisolmut" joukkoon
        if(solmu.getLeftChild() == null && solmu.getRightChild() == null){
            lehtisolmut.add(solmu);
        }
    }

}
