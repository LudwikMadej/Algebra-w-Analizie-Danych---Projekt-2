public class Main {
    public static void main(String[] args) {
        //Zadanie 6
        int[] wspolrzedne1 = {1,0,0,2,4};
        int[] wspolrzedne2 = {0,1,0,1,0};
        int[] wspolrzedne3 ={0,0,1,5,6};
        Wektor wektorGenerujacy1 = new Wektor(wspolrzedne1,7);
        Wektor wektorGenerujacy2 = new Wektor(wspolrzedne2,7);
        Wektor wektorGenerujacy3 = new Wektor(wspolrzedne3,7);
        ZbiorWektorow zbior = new ZbiorWektorow();
        zbior.dodajElement(wektorGenerujacy1);
        zbior.dodajElement(wektorGenerujacy2);
        zbior.dodajElement(wektorGenerujacy3);
        System.out.println("Zbior wektorow a: \n" + zbior +"\n jest baza: "+ zbior.czyBaza());
        ZbiorWektorow przesrzenKodowa = zbior.generujPrzestrzen();
        System.out.println("Przestrzen kodowa wygenerowana za pomocą a");
        System.out.println(przesrzenKodowa);

        //Zadanie 7
        int[] wspolrzedne = {1,0,0,5,2};
        Wektor wektorDoOdkodowania = new Wektor(wspolrzedne, 7);
        Baza B = new Baza(zbior.zawartosc);
        int[] dekodowanie = B.odkodujWektor(wektorDoOdkodowania);
        Cialo.wyplujTablice(dekodowanie);

        //Zadanie 8
        System.out.println("Zadanie 8.");
        //a
        System.out.println("a)");
        Macierz losowaMacierz = Macierz.generujLosowaMacierz(4,10,5);
        System.out.println(losowaMacierz);

        //b
        System.out.println("b)");
        System.out.println(losowaMacierz.wyplujDlaWolframa());
        System.out.print("To trzeba wklepac w mathematicę lub wolfram");

        //c
        System.out.println("c)");
        int[][] zawartoscMacierzyG = {
                                        {1,0,0,0,0,4,4,2,0,1,1},
                                        {0,1,0,0,0,3,0,2,2,1,0},
                                        {0,0,1,0,0,2,0,1,1,1,1},
                                        {0,0,0,1,1,0,0,0,4,3,0}
                                    };
        Macierz macierzG = new Macierz(zawartoscMacierzyG, 5);
        System.out.println(macierzG);


        //d
        System.out.print("d)");
        Baza BazaG = new Baza(macierzG.konwertujNaUkladWektorow(false).zawartosc);
        System.out.println("Uklad wektorow w macierzy G jest baza: "+BazaG.czyBaza());
        Wektor zakodowanyWektor = macierzG.zakodujWektor(new Wektor(losowaMacierz.getKolumna(2),5));
        System.out.println("Oto zakodowany wektor"+new Wektor(losowaMacierz.getKolumna(2),5)+" : "+ zakodowanyWektor);

        //e
        System.out.println("e)");
        ZbiorWektorow wiadomosc = losowaMacierz.konwertujNaUkladWektorow(true);
        System.out.println("Macierz przed zakodowaniem: ");
        System.out.println(losowaMacierz.konwertujNaUkladWektorow(true));
        wiadomosc.zakodujUklad(macierzG);
        wiadomosc.zaszumWektory();
        System.out.println("Wektory po zakodowaniu i zaszumieniu: \n"+wiadomosc);

        //f
        System.out.println("f)");
        System.out.println(wiadomosc);
        wiadomosc.odkodujUklad(BazaG);
        System.out.println("Odkodowana wiadomosc: \n"+wiadomosc);

        //g
        System.out.println("g)");
        Macierz odkodowanaMacierz = new Macierz(wiadomosc);
        System.out.println("Macierz po odkodowaniu: ");
        System.out.println(odkodowanaMacierz);

        //h
        System.out.println("h)");
        System.out.println("Do porownania");
        System.out.println("Macierz przed wszystkimi operacjami: ");
        System.out.println(losowaMacierz.konwertujNaUkladWektorow(true));
        System.out.println("Macierz po przejsciach: ");
        System.out.println(odkodowanaMacierz);

        //i
        System.out.println("i)");
        System.out.println(odkodowanaMacierz.wyplujDlaWolframa());


    }
}