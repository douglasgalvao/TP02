
class Lista {
    Series objSeries[];
    int inseridos;

    Lista() {
        this(10);
    }

    Lista(int tamanho) {
        objSeries = new Series[tamanho];
        inseridos = 0;
    }

    // inseridos = 3;

    void insereInicio(Series serie) {
        if (inseridos >= objSeries.length) {
            new Exception("there's impossible to insert, no memory avaible");
        }

        for (int i = inseridos; i > 0; i--) {
            objSeries[i] = objSeries[i-1];
        }
        objSeries[0] = serie;
        inseridos++;
    }

    void insereFim(Series serie){
        if (inseridos >= objSeries.length) {
            new Exception("there's impossible to insert, no memory avaible");
        }

        objSeries[inseridos] = serie;
        inseridos++;
    }

    void inserePos (Series serie, int posicao){
        if(posicao > inseridos || posicao < 0 || inseridos >= objSeries.length){
            new Exception("there's impossible to insert, no memory avaible");
        }

        for(int i = inseridos; i > posicao ; i--){
            objSeries[i] = objSeries[i-1];
        }

    }
}

public class Classe {
    public static void main(String[] args) {
        Lista listaDoDavid = new Lista();
        listaDoDavid.insereInicio(serie);
    }
}
