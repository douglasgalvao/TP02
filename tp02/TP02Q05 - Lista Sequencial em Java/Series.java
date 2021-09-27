import java.io.*;
import java.net.*;
import java.text.ParseException;

class Lista {
  Series[] series;
  int added;

  Lista() {
    this(5);
  }

  Lista(int tamanho) {
    series = new Series[tamanho];
    added = 0;
  }

  void inserirInicio(Series serie) throws Exception {
    if (added >= series.length)
      throw new Exception("Erro! linha 16");
    for (int i = added; i > 0; i--) {
      series[i] = series[i - 1];
    }
    series[0] = serie;
    added++;
  }

  void inserirFim(Series serie) throws Exception {
    if (added >= series.length) {
      throw new Exception("Error! nao cabe, nao conseguimos inserir! lista cheia!");
    }
    series[added] = serie;
    added++;
  }

  void inserir(Series serie, int pos) throws Exception {
    if (added >= series.length || pos < 0 || pos > added)
      throw new Exception("Erro!");
    // levar elementos para o fim do array
    for (int i = added; i > pos; i--) {
      series[i] = series[i - 1];
    }
    series[pos] = serie;
    added++;
  }

  Series removerInicio() throws Exception {
    if (added == 0)
      throw new Exception("Erro!");
    Series resp = series[0];
    added--;
    for (int i = 0; i < added; i++) {
      series[i] = series[i + 1];
    }
    return resp;
  }

  Series removerFim() throws Exception {
    if (added == 0)
      throw new Exception("Erro!");
    return series[--added];
  }

  Series remover(int pos) throws Exception {
    if (added == 0 || pos < 0 || pos >= series.length)
      throw new Exception("Erro!");
    Series resp = series[pos];
    added--;
    for (int i = pos; i < added; i++) {
      series[i] = series[i + 1];
    }
    return resp;
  }

  void imprimir(Lista series) {
    for (int i = 0; i < series.added; i++) {
      series.series[i].imprimir();
    }
  }
}

class Series {
  public static int numeroCompare = 0;
  private String nome;
  private String formato;
  private String duration;
  private String paisFrom;
  private String originLanguage;
  private String originEmiTv;
  private String originalTransmition;
  private int nTemporadas;
  private int nEpisodes;

  // Funçao main principal ()
  public static void main(String[] args) throws Exception {
    String entrada = MyIO.readLine();
    String[] allIn = new String[70];
    int totalInseridos = 0, totalaProcessar = 0;
    String[] allIn2 = new String[70];
    int i = 0;
    String endereco = "/tmp/series/";
    // Ler a PRIMEIRA PARTE
    while (!isFim(entrada)) {
      allIn[i] = entrada;
      totalInseridos++;
      i++;
      entrada = MyIO.readLine();
    }

    i = 0;
    // Pegar o Inteiro (N) Segunda parte tratamentos
    totalaProcessar = MyIO.readInt();
    while (i < totalaProcessar) {
      allIn2[i] = MyIO.readLine();
      i++;
    }

    long inicio = System.currentTimeMillis();
    Lista list = new Lista(totalInseridos + totalaProcessar);
    for (i = 0; i < totalInseridos; i++) {
      list.inserirFim(ler(endereco + allIn[i]));
    }
    long total = System.currentTimeMillis() - inicio;
    //System.out.println("INICIALIZACAO DA LISTA: " + total);

    inicio = System.currentTimeMillis();
    verifyProcess(list, allIn2, totalaProcessar);
    total = System.currentTimeMillis() - inicio;
    //System.out.println("CHAMADA DE FUNCAO: " + total);
  }

  // Processo de verificação ,
  // Inserção (I*), InserçãoInicio(II) , InserçãoFIM(IF)
  // , Remoção(R*) , (RI) , (RF)
  public static void verifyProcess(Lista list, String[] allIn2, int totalaProcessar) throws Exception {
    Series[] removed = new Series[9];
    int count = 0;
    for (int i = 0; i < totalaProcessar; i++) {
      String[] tratament = allIn2[i].split(" ");
      if (tratament[0].compareTo("II") == 0) {
        list.inserirInicio(ler("/tmp/series/" + tratament[1]));
      } else if (tratament[0].compareTo("IF") == 0) {
        list.inserirFim(ler("/tmp/series/" + tratament[1]));
      } else if (tratament[0].compareTo("I*") == 0) {
        list.inserir(ler("/tmp/series/" + tratament[2]), Integer.parseInt(tratament[1]));
      } else if (tratament[0].compareTo("RI") == 0) {
        removed[count++] = list.removerInicio();
      } else if (tratament[0].compareTo("RF") == 0) {
        removed[count++] = list.removerFim();
      } else if (tratament[0].compareTo("R*") == 0) {
        removed[count++] = list.remover(Integer.parseInt(tratament[1]));
      }
    }
    for (int i = 0; i < count; i++) {
      System.out.println("(R) " + removed[i].getNome());
    }
    list.imprimir(list);

  }

  // Metodo de Inserção
  public static void insertionSort(Series[] series, int totalInseridos) {
    for (int i = 1; i < totalInseridos; i++) {
      Series tmp = series[i];
      int j = i - 1;
      while (j >= 0 && series[j].getNome().compareTo(tmp.getNome()) > 0) {
        series[j + 1] = series[j];
        j--;
      }
      series[j + 1] = tmp;
    }
  }

  // Pesquisa Binaria
  public static boolean toBinarySearch(Series[] a, String b, int totalInseridos) {
    boolean found = false;
    int esq = 0, dir = totalInseridos, meio = (esq + dir) / 2;
    while (esq <= dir && b.compareTo(a[meio].getNome()) != 0) {
      meio = (esq + dir) / 2;
      if (b.compareTo(a[meio].getNome()) > 0) {
        esq = meio + 1;
        numeroCompare++;
      } else if (b.compareTo(a[meio].getNome()) < 0) {
        dir = meio - 1;
        numeroCompare++;
      }

    }
    if (verificaDuasStrings(a[meio].getNome(), b)) {
      numeroCompare++;
      found = true;
    }
    return (found);
  }

  // Verifica duas
  public static boolean verificaDuasStrings(String a, String b) {
    return (a.equals(b));
  }

  // Construtor que recebe seus Atributos a serem setados manualmente.
  public Series(String nome, String format, String duration, String paisFrom, String originLanguage, String originEmiTv,
      String originalTransmition, int nTemporadas, int nEpisodes) {
    setNome(nome);
    setFormato(format);
    setDuration(duration);
    setPaisFrom(paisFrom);
    setOriginLanguage(originLanguage);
    setOriginEmiTv(originEmiTv);
    setOrigininalTransmition(originalTransmition);
    setNtemporadas(nTemporadas);
    setNepisodes(nEpisodes);
  }

  // Construtor Vazio();
  public Series() {
    setNome("");
    setFormato("");
    setDuration("");
    setPaisFrom("");
    setOriginEmiTv("");
    setOriginLanguage("");
    setOrigininalTransmition("");
    setNtemporadas(0);
    setNepisodes(0);
  }

  // Funçao que ler e seta aos atributos ao Objeto
  public static Series ler(String endereco) {
    String[] text = lerHtmls(endereco);
    // serie.setNome(toGetNome(text));
    // serie.setFormato(toGetFormato(text));
    // serie.setDuration(toGetDuration(text));
    // serie.setPaisFrom(toGetPaisFrom(text));
    // serie.setOriginLanguage(toGetOriginLanguage(text));
    // serie.setOriginEmiTv(toGetOriginEmitv(text));
    // serie.setOrigininalTransmition(toGetOriginalTransmition(text));
    // serie.setNtemporadas(toGetNumTemporadas(text));
    // serie.setNepisodes(toGetNumEpisodes(text));
    return LerObjeto(text);
  }

  // Clonar Objeto

  public static Series clonarObjeto(Series aSerClonado) {
    Series clone = new Series();
    clone.setNome(aSerClonado.getNome());
    clone.setFormato(aSerClonado.getFormato());
    clone.setDuration(aSerClonado.getDuration());
    clone.setPaisFrom(aSerClonado.getPaisFrom());
    clone.setOriginLanguage(aSerClonado.getOriginLanguage());
    clone.setOriginEmiTv(aSerClonado.getOriginEmiTv());
    clone.setOrigininalTransmition(aSerClonado.getOriginalTransmition());
    clone.setNtemporadas(aSerClonado.getNtemporadas());
    clone.setNepisodes(aSerClonado.getNepisodes());
    return clone;
  }

  // Funçao que Imprimite os Atributos do Objeto por meio das Funçoes Get();
  public void imprimir() {
    System.out.println(this.getNome() + " " + this.getFormato() + " " + this.getDuration() + " " + this.getPaisFrom()
        + " " + this.getOriginLanguage() + " " + this.getOriginEmiTv() + " " + this.getOriginalTransmition() + " "
        + this.getNtemporadas() + " " + this.getNepisodes());
  }

  // Funçao que recebe um endereço de arquivo, lê e o retorna em forma de String[]
  // contendo a cada parcela do i uma linha
  public static String[] lerHtmls(String caminho) {
    String[] text = new String[3000];
    int i = 0;
    boolean arq = Arq.openRead(caminho, "UTF-8");
    if (arq) {
      while (Arq.hasNext()) {
        text[i++] = Arq.readLine();
      }
    } else
      System.out.println("ERROR");
    Arq.close();
    String[] text2 = new String[i];
    for (int j = 0; j < i-1; j++)
      text2[j] = text[j];
    return (text2);
  }

  // Funçoes GET() para obter atributos (sera chamado no metodo Ler)

  public static Series LerObjeto(String[] text) {
    Series serie = new Series();
    String nome = "";
    String formato = "";
    String duration = "";
    String paisFrom = "";
    String toOriginLanguage = "";
    String toOriginEmitv = "";
    String toOriginalTransmition = "";
    int NumberTemp = -1;
    int NumberEp = -1;

    int count = 0;
    int size = text.length - 1;
    for (int linha = 0; count < 9 && linha < size; linha++) {
      // System.out.println(text[linha]+"--"+linha+"--"+count);
      if (text[linha].contains("firstHeading")) {
        nome = pegaAtributoComRestricoes(text[linha]);
        // System.out.println("nome: " + nome);
        count++;
      }
      if (text[linha].contains("Formato</td>")) {
        formato = pegaAtributo(text[linha + 1]);
        // System.out.println("formato: " + formato);
        count++;
      }
      if (text[linha].contains("\">Dura")) {
        duration = pegaAtributo(text[linha + 1]);
        // System.out.println("duration: " + duration);
        count++;
      }
      if (text[linha].contains("de origem</td>")) {
        paisFrom = pegaAtributoComRestricoes(text[linha + 1]);
        // System.out.println("paisFrom: " + paisFrom);
        count++;
      }
      if (text[linha].contains("Idioma original")) {
        toOriginLanguage += pegaAtributo(text[linha + 1]);
        // System.out.println("toOriginLanguage: " + toOriginLanguage);
        count++;
      }
      if (text[linha].contains("Emissora de tele")) {
        toOriginEmitv = pegaAtributo(text[linha + 1]);
        // System.out.println("toOriginEmitv: " + toOriginEmitv);
        count++;
      }
      if (text[linha].contains("Transmiss")) {
        toOriginalTransmition = pegaAtributo(text[linha + 1]);
        // System.out.println("toOriginalTransmition: " + toOriginalTransmition);
        count++;
      }
      if (text[linha].contains("de temporadas")) {
        NumberTemp = Integer.parseInt(pegaAtributoComRestricoesN(text[linha + 1]));
        // System.out.println("NumberTemp: " + NumberTemp);
        count++;
      }
      if (text[linha].contains("dios</td")) {
        NumberEp = Integer.parseInt(pegaAtributoComRestricoesN(text[linha + 1]));
        // System.out.println("NumberEp: " + NumberEp);
        count++;
      }
    }

    if (count == 9) {
      serie.setNome(nome);
      serie.setFormato(formato);
      serie.setDuration(duration);
      serie.setPaisFrom(paisFrom);
      serie.setOriginLanguage(toOriginLanguage);
      serie.setOriginEmiTv(toOriginEmitv);
      serie.setOrigininalTransmition(toOriginalTransmition);
      serie.setNtemporadas(NumberTemp);
      serie.setNepisodes(NumberEp);
    } else {
      System.out.println(serie.getNome());
      System.out.println(serie.getFormato());
      System.out.println(serie.getDuration());
      System.out.println(serie.getPaisFrom());
      System.out.println(serie.getOriginLanguage());
      System.out.println(serie.getOriginEmiTv());
      System.out.println(serie.getOriginalTransmition());
      System.out.println(serie.getNtemporadas());
      System.out.println(serie.getNepisodes());
      System.out.println("ERRO");
    }

    return serie;
    // int linha = 0;
    // while (!text[linha].contains("firstHeading") && linha < text.length) {
    // linha++;
    // }
    // if (text[linha].contains("firstHeading")) {
    // nome = pegaAtributoComRestricoes(text[linha]);

    // } else {
    // nome = "Erro";
    // }

  }

  // Funçao pega ATRIBUTO (IMPORTANTE) (novo metodo para pegar valores)

  public static String pegaAtributo(String a) {
    String[] str = a.split(">");
    String atributo = "";
    int iC = 0;
    int iL = 0;
    while (iL < str.length) {
      if (isLetraOrNumber(str[iL].charAt(0))) {
        while (iC < a.length() && str[iL].charAt(iC) != '<') {
          atributo += str[iL].charAt(iC++);
        }
        iL++;
        iC = 0;
      } else {
        if (iC < a.length() && str[iL].charAt(0) == '&') {
          atributo += '(';
        }
        iL++;
        iC = 0;
      }
    }
    return (atributo);
  }

  public static String pegaAtributoComRestricoes(String a) {
    String[] str = a.split(">");

    String atributo = "";
    int iC = 0;
    int iL = 0;
    while (iL < str.length) {
      if (isLetraOrNumber2(str[iL].charAt(0))) {
        while (iC < a.length() && str[iL].charAt(iC) != '<') {
          atributo += str[iL].charAt(iC++);
        }
        iL++;
        iC = 0;
      } else {
        iL++;
        iC = 0;
      }
    }
    return (atributo);
  }
  public static String pegaAtributoComRestricoesN(String a) {
    String[] str = a.split(">");

    String atributo = "";
    int iC = 0;
    int iL = 0;
    while (iL < str.length) {
      if (onlyNumbers(str[iL].charAt(0))) {
        while (onlyNumbers(str[iL].charAt(iC)) && iC < a.length() && str[iL].charAt(iC) != '<') {
          atributo += str[iL].charAt(iC++);
        }
        iL++;
        iC = 0;
      } else {
        iL++;
        iC = 0;
      }
    }
    return (atributo);
  }

  // Funçoes de verificação
  public static boolean isLetraOrNumber(char a) {
    return (a >= 'a' && a <= 'z' || a >= 'A' && a <= 'Z' || a >= '0' && a <= '9' || a == ' ' || a == '(' || a == ')');
  }

  public static boolean isLetraOrNumber2(char a) {
    return (a >= 'a' && a <= 'z' || a >= 'A' && a <= 'Z' || a >= '0' && a <= '9' || a == '/');
  }

  public static boolean onlyNumbers(char a) {
    return (a >= '0' && a <= '9');
  }

  // Funçoes get (Adquirir o valor do atributo privado por meio de funçao)

  public String getNome() {
    return this.nome;
  }

  public String getFormato() {
    return this.formato;
  }

  public String getDuration() {
    return this.duration;
  }

  public String getPaisFrom() {
    return this.paisFrom;
  }

  public String getOriginEmiTv() {
    return this.originEmiTv;
  }

  public String getOriginLanguage() {
    return this.originLanguage;
  }

  public String getOriginalTransmition() {
    return this.originalTransmition;
  }

  public int getNtemporadas() {
    return this.nTemporadas;
  }

  public int getNepisodes() {
    return this.nEpisodes;
  }

  // Funçoes set (setar valor nos atributos)

  public void setNome(String nomeSet) {
    this.nome = nomeSet;
  }

  public void setFormato(String nomeSet) {
    this.formato = nomeSet;
  }

  public void setDuration(String nomeSet) {
    this.duration = nomeSet;
  }

  public void setPaisFrom(String nomeSet) {
    this.paisFrom = nomeSet;
  }

  public void setOriginLanguage(String nomeSet) {
    this.originLanguage = nomeSet;
  }

  public void setOriginEmiTv(String nomeSet) {
    this.originEmiTv = nomeSet;
  }

  public void setOrigininalTransmition(String nomeSet) {
    this.originalTransmition = nomeSet;
  }

  public void setNtemporadas(int numeroSet) {
    this.nTemporadas = numeroSet;
  }

  public void setNepisodes(int nomeSet) {
    this.nEpisodes = nomeSet;
  }

  public static boolean isFim(String a) {
    return (a.charAt(0) == 'F' && a.charAt(1) == 'I' && a.charAt(2) == 'M');
  }

}
