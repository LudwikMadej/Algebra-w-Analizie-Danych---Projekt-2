import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ZbiorWektorow extends Zbior<Wektor>{
    ZbiorWektorow(List<Wektor> dane) {
        this.zawartosc = dane;
        this.pusty = (dane.size()==0);
    }

    ZbiorWektorow(){
        super();
    }

    ZbiorWektorow generujPrzestrzen(){
        //sluzy do generowania wszystkich kombinacji liniowych wektorow ze zbioru
        //nieoptymalne dla zbiorow nie bedacych bazami (niektore wektory pojawiaja sie kilka razy)
        List<Wektor> przestrzen = new ArrayList<>();
        int[][] listaWspolczynnikow = Cialo.wariacje(zawartosc.getFirst().cialo, zawartosc.size());
        for(int[] permutacja: listaWspolczynnikow){
            List<Wektor> doDodania = new ArrayList<>();
            for(int i = 0; i < permutacja.length;i++){
                doDodania.add(this.zawartosc.get(i).skalujWektor(permutacja[i]));
            }
            przestrzen.add(Wektor.dodajWektory(doDodania));
        }
        return new ZbiorWektorow(przestrzen);
    }

    boolean czyBaza(){
        //sprawdzamy, czy zadany uklad wektorow jest baza
        int[] zeroweWspolrzedne = new int[zawartosc.getFirst().dlugosc];
        Wektor wektorZerowy = new Wektor(zeroweWspolrzedne,zawartosc.getFirst().cialo);
        int dlugoscWariacji = this.zawartosc.size();
        int cialo = this.zawartosc.getFirst().cialo;
        int[][] wariacja = Cialo.wariacje(cialo,dlugoscWariacji);
        boolean skipnij = true;
        for(int[] permutacja:wariacja){
            if (skipnij) {
                skipnij = false;
                continue; // pomijamy pierwsza wariacje zmiennych, bo wszystkie rowne 0
            }
            List<Wektor> doDodania = new ArrayList<>();
            for (int i = 0; i < permutacja.length; i++){
                doDodania.add(zawartosc.get(i).skalujWektor(permutacja[i]));

            }
            Wektor wektorWynikowy = Wektor.dodajWektory(doDodania);
            if (wektorWynikowy.equals(wektorZerowy)) {return false;}
        }
        return true;
    }

    @Override
    public String toString() {
        // WARNING: ładnie wypisuje dla wektorow z elementami  0<= element < 10
        //zwraca zapisany kolumnowo uklad wektorow
        if (this.pusty) {return ("Zbior jest pusty");}
        int dlugosc = this.zawartosc.getFirst().dlugosc;
        String wynik ="";
        String[] skladowe = new String[this.zawartosc.getFirst().dlugosc];
        for (int i = 0; i < skladowe.length; i++){
            skladowe[i] = "";
        }
        Iterator<Wektor> iterator = this.zawartosc.iterator();
        while (iterator.hasNext()) {
            Wektor w = iterator.next();
            // rozbijamy wypisywanie na poszczegolne wiersze kolumny, dzieki temu iterujemy
            // po zbiorze tylko i wylacznie raz
            for (int wspolrzedna = 0; wspolrzedna < dlugosc; wspolrzedna++) {
                skladowe[wspolrzedna]+=w.wspolrzedne[wspolrzedna];
                if (iterator.hasNext()) skladowe[wspolrzedna] +=" ";
            }
        }
        for(String skladowa: skladowe){
            wynik+=skladowa+"\n";
        }
        return wynik;
    }

    public Wektor wybierzLosowy(){
        //wybiera losowy wektor ze zbioru
        if (zawartosc.size() == 0) {return null;}
        Random ml = new Random(2137);
        return zawartosc.get(ml.nextInt(zawartosc.size()));
    }

    public void zaszumWektory(){
        //zaszumia wektory ze zbiory wedle polecenia
        for(int i=0; i < zawartosc.size(); i++){
            zawartosc.get(i).zaszumWektor();
        }
    }

    public void zakodujUklad(Macierz baza){
        //koduje uklad wektorow wg podanej bazy w postaci macierzowej
        for(int i=0; i < zawartosc.size(); i++){
            zawartosc.set(i, baza.zakodujWektor(zawartosc.get(i)));
        }
    }

    public Wektor kombinacjaLiniowa(int[] wspolrzedne){
        //zwraca wektor bedacy kombinacja liniowa wektorow o zadanych wspolrzednych w ukladzie
        List<Wektor> doDodania = new ArrayList<>();
        for(int i = 0; i < wspolrzedne.length; i++){
            doDodania.add(zawartosc.get(i).skalujWektor(wspolrzedne[i]));
        }
        return Wektor.dodajWektory(doDodania);
    }

    public void odkodujUklad(Baza baza){
        int cialko = baza.zawartosc.getFirst().cialo;
        //dla kazdego w wektorow z ukladu przeprowadzamy odkodowanie wg zadanego algorytmu
        for(int i=0; i < zawartosc.size(); i++){
            zawartosc.set(i, new Wektor(baza.odkodujWektor(zawartosc.get(i)), cialko));
        }
    }

}
