import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Macierz {

    private  int[][] zawartosc;
    final int cialo, __iloscWierszy, __iloscKolumn;

    Macierz(int iloscWierszy, int iloscKolumn, int cialo){
        //Kontruktor do tworzenia macierzy o zadanych wymiarach wypelnionej zerami
        this.zawartosc = new int[iloscWierszy][iloscKolumn];
        this.cialo = cialo;
        this.__iloscWierszy = iloscWierszy;
        this.__iloscKolumn = iloscKolumn;
    }

    Macierz(int[][] wnetrze, int cialo){
        //Kontruktor do tworzenia macierzy, gdy znamy jej zawartosc
        this.zawartosc = wnetrze;
        this.cialo = cialo;
        this.__iloscWierszy = wnetrze.length;
        this.__iloscKolumn = wnetrze[0].length;
    }

    Macierz(ZbiorWektorow zbiorWektorow){
        //konstruktor do tworzenia macierzy, kiedy mamy uklad wektorow
        //zapisujemy je kolumnowo
        int iloscWierszy =zbiorWektorow.zawartosc.getFirst().dlugosc;
        int iloscKolumn = zbiorWektorow.zawartosc.size();
        int[][] zawartosc = new int[iloscWierszy][iloscKolumn];
        this.__iloscWierszy = iloscWierszy;
        this.__iloscKolumn = iloscKolumn;
        this.cialo = zbiorWektorow.zawartosc.getFirst().cialo;
        Iterator<Wektor> iterator = zbiorWektorow.zawartosc.iterator();
        for (int kolumna = 0; kolumna < iloscKolumn; kolumna++){
            int[] wspolrzedneWektora = iterator.next().wspolrzedne;
            for(int wiersz = 0; wiersz < iloscWierszy; wiersz++){
                zawartosc[wiersz][kolumna] = wspolrzedneWektora[wiersz];
            }
        }
        this.zawartosc = zawartosc;
    }

    public  int[] getKolumna(int index){
        int[] kolumna = new int[__iloscWierszy];
        for(int wiesz = 0; wiesz < __iloscWierszy; wiesz++){
            kolumna[wiesz] = this.zawartosc[wiesz][index];
        }
        return kolumna;
    }
    public  int[] getWiersz(int index) {
        //musi byc w ten sposob bo inaczej moglibysmy modyfikowac wnetrze macierzy
        int[] wynik = new int[__iloscKolumn];
        for(int kolumna = 0; kolumna < __iloscKolumn; kolumna++){
            wynik[kolumna] = zawartosc[index][kolumna];
        }
        return wynik;
    }

    public int get(int wiersz, int kolumna){
        //sluzy do wydobywania elementu o zadanych wspolrzednych
        if(wiersz < 0 || wiersz >= __iloscWierszy
                || kolumna<0 || kolumna >= __iloscKolumn){
            System.out.println("IndexOutOfRange");
            return -1;
        }
        return this.zawartosc[wiersz][kolumna];
    }
    static Macierz generujLosowaMacierz(int iloscWierszy, int iloscKolumn, int cialo){
        //jak sama nazwa wskazuje, generujemy macierz o wyrazach z ciala Z(cialo)
        Random ml = new Random(2137);
        int[][] nowaZawartosc = new int[iloscWierszy][iloscKolumn];
        for(int wiersz = 0; wiersz < iloscWierszy; wiersz++){
            for(int kolumna = 0; kolumna < iloscKolumn; kolumna++){
                nowaZawartosc[wiersz][kolumna] = ml.nextInt(cialo);
            }
        }
        return new Macierz(nowaZawartosc, cialo);
    }

    @Override
    public String toString() {
        String wynik = "Macierz, o elementach w ciele" + this.cialo+"\n";
        for(int[] wiersz: zawartosc){
            for(int element: wiersz){
                wynik+=element+" ";
            }
            wynik+="\n";
        }
        return wynik;
    }

    public String wyplujDlaWolframa(){
        //zwraca forme, ktora mozna wpisac bezposrednio do Wolframa lub innych programow tego typu
        String wynik = "{";
        for(int wiersz = 0; wiersz< __iloscWierszy; wiersz++){
            wynik+="{";
            for(int i = 0; i<__iloscKolumn; i++){
                wynik+=this.zawartosc[wiersz][i];
                if (i < __iloscKolumn-1) wynik+=",";
            }
            wynik+="}";
            if (wiersz < __iloscWierszy -1) wynik+=",";
        }
        wynik+="}";
        return wynik;
    }

    public Macierz transponuj(){
        //zwraca transponowana macierz, nie zmianiajac bazowej
        int[][] nowaZawartosc = new int[__iloscKolumn][];
        for (int kolumna = 0; kolumna < __iloscKolumn; kolumna++){
            nowaZawartosc[kolumna] = this.getKolumna(kolumna);
        }
        return new Macierz(nowaZawartosc,cialo);
    }
    public ZbiorWektorow konwertujNaUkladWektorow(boolean kolumnowo){
        //konwertuje macierz na uklad wektorow, nie zmieniajac macierzy
        // kolumnowo == true dla wektorow zapisanych w orientacji pionowej, przeciwnie dla poziomej
        Macierz doSciagania = this;
        if (!kolumnowo) doSciagania = this.transponuj();
        List<Wektor> zbiorek = new ArrayList<>();
        for(int kolumna = 0; kolumna < doSciagania.__iloscKolumn; kolumna++){
            zbiorek.add(new Wektor(doSciagania.getKolumna(kolumna),cialo));
        }
        return new ZbiorWektorow(zbiorek);
    }

    Wektor zakodujWektor(Wektor w){
        //koduje wektor
        int[] noweWspolrzedne = new int[__iloscKolumn];
        for(int wspolrzedna = 0; wspolrzedna<__iloscKolumn; wspolrzedna++){
            int[] kolumna = getKolumna(wspolrzedna);
            for(int k = 0; k < w.dlugosc; k++){
                noweWspolrzedne[wspolrzedna] += (w.getWpolrzedna(k)*kolumna[k]);
                noweWspolrzedne[wspolrzedna] = noweWspolrzedne[wspolrzedna]%w.cialo;
            }
        }
        return new Wektor(noweWspolrzedne, w.cialo);
    }

    public int getCialo() {
        return cialo;
    }
}
