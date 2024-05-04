import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Wektor {
    final int cialo, dlugosc;
    final int[] wspolrzedne;

    Wektor(int[] wspolrzedne, int cialo){
        int[] noweWspolrzedne = new int[wspolrzedne.length];
        for(int i = 0; i < wspolrzedne.length; i++){
            noweWspolrzedne[i] = wspolrzedne[i]%cialo;
            if (wspolrzedne[i] >= cialo || wspolrzedne[i] < 0){
                System.out.println("Dokonano unormowania na "+i+"-tej wspolrzednej\nElement poza cialem!");
            }
        }
        this.wspolrzedne = noweWspolrzedne;
        this.dlugosc = wspolrzedne.length;
        this.cialo = cialo;
    }

    public Wektor skalujWektor(int a){
        //zwraca wektor pomnozony przez stala a modulo cialo
        int[] noweWspolrzedne= new int[dlugosc];
        for(int i = 0; i<dlugosc;i++){
            noweWspolrzedne[i] = (a*wspolrzedne[i])%cialo;
        }
        return new Wektor(noweWspolrzedne,cialo);
    }

    public static Wektor dodajWektory(List<Wektor> listaWektorow){
        // dodaje wektory z listy
        int noweCialo = listaWektorow.getFirst().cialo;
        int dlugosc = listaWektorow.getFirst().dlugosc;
        int[] noweWspolrzedne = new int[listaWektorow.getFirst().dlugosc];
        Iterator<Wektor> iterator = listaWektorow.iterator();
        while (iterator.hasNext()){
            Wektor wektor = iterator.next();
            if(wektor.dlugosc != dlugosc || wektor.cialo != noweCialo){
                System.out.println("Dodawane wektory maja rozne ciala lub dlugosci");
                return null;
            }
            for(int i = 0; i < dlugosc; i++){
                noweWspolrzedne[i] += wektor.wspolrzedne[i];
            }
        }
        for(int i = 0; i < dlugosc; i++){
            noweWspolrzedne[i] = (noweWspolrzedne[i])%noweCialo;
        }
        return new Wektor(noweWspolrzedne,noweCialo);
    }


    @Override
    public String toString() {
        // zwraca napis przedstawiajacy wektor w orientacji poziomej
        String wynik = "(";
        for(int i = 0; i < dlugosc; i++){
            wynik+=wspolrzedne[i];
            if(i < dlugosc-1) wynik+=", ";
        }
        wynik+=")^T";
        return wynik;
    }

    public boolean equals(Object o) {
        // sprawdzanie rownosci
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wektor wektor = (Wektor) o;
        if (this.cialo != wektor.cialo || this.dlugosc != wektor.dlugosc){
            System.out.println("Zle cialo lub dlugosc!\n");
            return false;
        }
        for (int i = 0; i<this.dlugosc;i++){
            if (this.wspolrzedne[i] != wektor.wspolrzedne[i]){
                return false;
            }
        }
        return true;
    }

    public int hammingDistance(Wektor wektor){
        // do obliczania odleglosci Hamminga
        if (this.dlugosc != wektor.dlugosc || this.cialo != wektor.cialo){
            System.out.println("Wektory z innych kodow");
            return -1;
        }
        int hammingDistance = 0;
        for(int i = 0; i < this.dlugosc; i++){
            if (this.wspolrzedne[i] != wektor.wspolrzedne[i]) hammingDistance+=1;
        }
        return hammingDistance;

    }

    public int getWpolrzedna(int i){
        return this.wspolrzedne[i];
    }

    public void zaszumWektor(){
        //zaszumia wektor wg wzoru
        Random ml = new Random(2137);
        int[] noweWspolrzedne = new int[dlugosc];
        for(int wspolrzedna = 0; wspolrzedna < dlugosc; wspolrzedna++){
            int losowanie = ml.nextInt(100);
            if (losowanie < 95) {
                noweWspolrzedne[wspolrzedna] = this.getWpolrzedna(wspolrzedna);
            } else {
                noweWspolrzedne[wspolrzedna] = (this.getWpolrzedna(wspolrzedna)+3)%5;
            }
        }
    }
}
