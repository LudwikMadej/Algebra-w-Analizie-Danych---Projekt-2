import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Baza extends ZbiorWektorow {

    Baza(){
        super();
    }

    Baza(List<Wektor> lista){
        super(lista);
    }
    int[] WspolrzedneWektoraWBazie(Wektor w){
        int dlugoscPermutacji = this.zawartosc.size();
        int cialo = this.zawartosc.getFirst().cialo;
        int[][] permutacje = Cialo.wariacje(cialo,dlugoscPermutacji);
        for(int[] permutacja:permutacje){
            List<Wektor> doDodania = new ArrayList<>();
            for (int i = 0; i < permutacja.length; i++){
                doDodania.add(this.zawartosc.get(i).skalujWektor(permutacja[i]));
            }
            Wektor wektorWynikowy = Wektor.dodajWektory(doDodania);
            if (wektorWynikowy.equals(w)) return permutacja;
        }
        return null;
    }

    public int[] odkodujWektor(Wektor w){
        if (!this.czyBaza()) return null;
        int minHammingDistace = w.dlugosc;
        ZbiorWektorow rozpinanaPrzestrzen = this.generujPrzestrzen();
        Iterator<Wektor> iterator = rozpinanaPrzestrzen.zawartosc.iterator();
        while (iterator.hasNext()){
            Wektor sprawdzany = iterator.next();
            int hammingDistace = w.hammingDistance(sprawdzany);
            if (hammingDistace == 0) return this.WspolrzedneWektoraWBazie(sprawdzany);
            if (hammingDistace < minHammingDistace) minHammingDistace = hammingDistace;
        }
        Iterator<Wektor> iterator2 = rozpinanaPrzestrzen.zawartosc.iterator();
        List<Wektor> doWybrania = new ArrayList<>();
        while (iterator2.hasNext()){
            Wektor potencjalny = iterator2.next();
            if(w.hammingDistance(potencjalny) == minHammingDistace)
                doWybrania.add(potencjalny);
        }
        Random ml = new Random(2137);
        Wektor wybranyWektor = doWybrania.get(ml.nextInt(doWybrania.size()));
        System.out.println(wybranyWektor);
        return WspolrzedneWektoraWBazie(wybranyWektor);
    }
}
