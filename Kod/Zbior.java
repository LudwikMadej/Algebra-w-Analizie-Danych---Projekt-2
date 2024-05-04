import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Zbior <DowolnyTyp>{
    protected boolean pusty;
    protected List<DowolnyTyp> zawartosc;

    Zbior(List<DowolnyTyp> dane){
        this.zawartosc = dane;
        this.pusty = true;
    }

    Zbior(){
        this.pusty=true;
        this.zawartosc = new ArrayList<>();
    }

    boolean dodajElement(DowolnyTyp element){
        this.pusty = false;
        Iterator<DowolnyTyp> iterator = zawartosc.iterator();
        while (iterator.hasNext()){
            DowolnyTyp elementZbioru = iterator.next();
            if(elementZbioru.equals(element)){
                return false;
            }
        }
        zawartosc.add(element);
        return true;
    }

    public List<DowolnyTyp> getZawartosc() {
        return zawartosc;
    }

    //TODO : usuwanie elementu (mi niepotrzebne)
}
