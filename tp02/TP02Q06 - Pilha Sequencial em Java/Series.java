import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.util.Queue;

class Pilha {
  Series[] series;
  int added;

  Pilha() {
    this(10);
  }

  Pilha(int tamanho) {
    series = new Series[tamanho];
    added = 0;
  }

  void empilhar(Series serie) throws Exception {
    if (added >= series.length) {
      throw new Exception("Error! nao cabe, nao conseguimos inserir! lista cheia!");
    }
    series[added] = serie;
    added++;
  }

  Series desempilhar() throws Exception {
    if (added == 0)
      throw new Exception("Erro!");
    return series[--added];
  }

  void imprimir(Pilha series) {
    for (int i = series.added-1; i >= 0; i--) {
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
    totalaProcessar = MyIO.readInt();
    Pilha pilha = new Pilha(totalInseridos + totalaProcessar);
    for (i = 0; i < totalInseridos; i++) {
      pilha.empilhar(ler("/tmp/series/" + allIn[i]));
    }
    i = 0;
    while (i < totalaProcessar) {
      allIn2[i++] = MyIO.readLine();
    }

    verifyProcess(pilha, allIn2, totalaProcessar);
  }

  public static void verifyProcess(Pilha pilha, String[] allIn2, int totalaProcessar) throws Exception {
    Series[] removed = new Series[9];
    int count = 0;
    for (int i = 0; i < totalaProcessar; i++) {
      String[] tratament = allIn2[i].split(" ");
      if (tratament[0].charAt(0) == 'I') {
        pilha.empilhar(ler("/tmp/series/" + tratament[1]));
      } else if (tratament[0].charAt(0) == 'R') {
        removed[count++] = pilha.desempilhar();
      }
    }
    for (int i = 0; i < count; i++) {
      System.out.println("(R) " + removed[i].getNome());
    }
    pilha.imprimir(pilha);
  }

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
    for (int j = 0; j < i - 1; j++)
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
