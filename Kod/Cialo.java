import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cialo {
    final int cialo;

    Cialo(int i){
        this.cialo = i;
    }

    //funkcja potrzeban do dzialania funkcji permutacje
    private static void rekurencjaPermutacyjna(List<Integer[]> listaPermutacji, Integer[] permutacja,
                                        int wspolrzedna, int dlugoscPermutacji, int cialo){
        for(int i = 0; i < cialo;i++){
            Integer[] permutacjaPrim = new Integer[dlugoscPermutacji];
            for(int z= 0; z< dlugoscPermutacji; z++){
                permutacjaPrim[z] = permutacja[z];
            }
            permutacjaPrim[wspolrzedna] = i;
            if (wspolrzedna < dlugoscPermutacji-1){
                rekurencjaPermutacyjna(listaPermutacji,permutacjaPrim,wspolrzedna+1,dlugoscPermutacji, cialo);
            }
            if (wspolrzedna == dlugoscPermutacji-1) listaPermutacji.add(permutacjaPrim);
        }
    }
     public static int[][] wariacje(int cialo, int dlugoscWariacji){
        List<Integer[]> listaWariacji = new ArrayList<>();
        Integer[] wariacja = new Integer[dlugoscWariacji];
        rekurencjaPermutacyjna(listaWariacji,wariacja,0,dlugoscWariacji, cialo);
        int[][] permutacje = new int[(int) Math.pow(cialo,dlugoscWariacji)][];
        int index = 0;
        Iterator<Integer[]> iterator = listaWariacji.iterator();
        while (iterator.hasNext()){
            //tworzymy nowy obiekt, zeby stracic referencje na stary
            int[] przetlumaczonaWariacja = new int[dlugoscWariacji];
            Integer[] tlumaczonaWariacja = iterator.next();
            for(int i = 0; i < dlugoscWariacji;i++) {
                przetlumaczonaWariacja[i] = tlumaczonaWariacja[i];
            }
            permutacje[index] = przetlumaczonaWariacja;
            index+=1;
        }
        return permutacje;
    }

    public static void wyplujTablice(int[] tablica){
        //do wypisywania tablic
        if (tablica ==  null) {
            System.out.println("null");
            return;
        }
        System.out.print("[");
        for(int i = 0; i < tablica.length; i++){
            System.out.print(tablica[i]);
            if (i < tablica.length-1) System.out.print(",");
        }
        System.out.print("]\n");
    }
}
