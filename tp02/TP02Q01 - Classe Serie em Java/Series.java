import java.io.*;
import java.net.*;
import java.text.ParseException;

class Series {

  private String nome;
  private String formato;
  private String duration;
  private String paisFrom;
  private String originLanguage;
  private String originEmiTv;
  private String originalTransmition;
  private int nTemporadas;
  private int nEpisodes;

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
    Series serie = new Series();
    String[] text = lerHtmls(endereco);
    serie.setNome(toGetNome(text));
    serie.setFormato(toGetFormato(text));
    serie.setDuration(toGetDuration(text));
    serie.setPaisFrom(toGetPaisFrom(text));
    serie.setOriginLanguage(toGetOriginLanguage(text));
    serie.setOriginEmiTv(toGetOriginEmitv(text));
    serie.setOrigininalTransmition(toGetOriginalTransmition(text));
    serie.setNtemporadas(toGetNumTemporadas(text));
    serie.setNepisodes(toGetNumEpisodes(text));
    return serie;
  }

  // Clonar Objeto

  public static Series clonarObjeto(String endereco, Series aSerClonado) {
    String[] text = lerHtmls(endereco);
    Series clone = new Series(serie.setNome(toGetNome(text)), serie.setFormato(toGetFormato(text)),
        serie.setDuration(toGetDuration(text)), serie.setPaisFrom(toGetPaisFrom(text)),
        serie.setOriginLanguage(toGetOriginLanguage(text)), serie.setOriginEmiTv(toGetOriginEmitv(text)),
        serie.setOrigininalTransmition(toGetOriginalTransmition(text)), serie.setNtemporadas(toGetNumTemporadas(text)),
        serie.setNepisodes(toGetNumEpisodes(text)));
    return serie;
  }

  // Funçao que Imprimite os Atributos do Objeto por meio das Funçoes Get();
  public static void imprimir(Series serie) {
    System.out.println(serie.getNome() + " " + serie.getFormato() + " " + serie.getDuration() + " "
        + serie.getPaisFrom() + " " + serie.getOriginLanguage() + " " + serie.getOriginEmiTv() + " "
        + serie.getOriginalTransmition() + " " + serie.getNtemporadas() + " " + serie.getNepisodes());
  }

  // Funçao que recebe um endereço de arquivo, lê e o retorna em forma de String[]
  // contendo a cada parcela do i uma linha
  public static String[] lerHtmls(String caminho) {
    String[] text = new String[2000];
    int i = 0;
    boolean arq = Arq.openRead(caminho, "UTF-8");
    if (arq) {
      while (Arq.hasNext() && i < text.length) {
        text[i++] = Arq.readLine();
      }
      Arq.close();
    } else
      System.out.println("ERROR");
    return (text);
  }

  // Funçoes GET() para obter atributos (sera chamado no metodo Ler)

  public static String toGetNome(String[] text) {
    String nome = "";
    int linha = 0;
    while (!text[linha].contains("firstHeading") && linha < text.length) {
      linha++;
    }
    if (text[linha].contains("firstHeading")) {
      nome = pegaAtributoComRestricoes(text[linha]);
    } else {
      nome = "Erro";
    }
    return (nome);
  }

  public static String toGetFormato(String[] text) {
    String formato = "";
    int linha = 0;
    // int indexlinha = 0;
    while (!text[linha].contains("infobox_v2") && linha < text.length) {
      linha++;
    }
    while (!text[linha].contains("Formato") && linha < text.length) {
      linha++;
    }
    linha++;
    formato = pegaAtributo(text[linha]);
    /*
     * if (!text[linha].contains("Formato") && linha < text.length) { while
     * (indexlinha < text[linha].length() && text[linha].charAt(indexlinha) != '>')
     * { indexlinha++; } indexlinha++; while (indexlinha < text[linha].length() &&
     * text[linha].charAt(indexlinha) != '>') { indexlinha++; } indexlinha++; while
     * (indexlinha < text[linha].length() && text[linha].charAt(indexlinha) != '<')
     * { formato += text[linha].charAt(indexlinha++); } }
     */
    return formato;
  }

  public static String toGetDuration(String[] text) {
    MyIO.setCharset("UTF-8");
    String duration = "";
    int linha = 0;
    int indexlinha = 0;
    while (!text[linha].contains("infobox_v2") && linha < text.length) {
      linha++;
    }
    while (!text[linha].contains("Dura") && linha < text.length) {
      linha++;
    }
    if (text[linha].contains("Dura") && linha < text.length) {
      linha++;
      duration = pegaAtributo(text[linha]);
      /*
       * while (text[linha].charAt(indexlinha) != '>' && indexlinha <
       * text[linha].length()) { indexlinha++; } indexlinha++; while
       * (text[linha].charAt(indexlinha) != '<' && indexlinha < text[linha].length())
       * { duration += text[linha].charAt(indexlinha++); }
       */
    } else {
      System.out.println("ERRO");
    }
    return duration;
  }

  public static String toGetPaisFrom(String[] text) {
    String paisFrom = "";
    int linha = 0;
    int indexColuna = 0;
    while (!text[linha].contains("infobox_v2") && linha < text.length) {
      linha++;
    }
    while (!text[linha].contains("de origem") && linha < text.length) {
      linha++;
    }
    if (text[linha].contains("de origem")) {
      linha++;
      paisFrom = pegaAtributoComRestricoes(text[linha]);
      /*
       * text[linha] = text[linha].replaceAll("</a>", "");
       * 
       * indexColuna = text[linha].length() - 1;
       * 
       * while (text[linha].charAt(indexColuna) != '<' && indexColuna > 0) {
       * indexColuna--; } indexColuna--; while (text[linha].charAt(indexColuna) != '>'
       * && indexColuna > 0) { indexColuna--; } indexColuna++; while
       * (text[linha].charAt(indexColuna) != '<' && indexColuna > 0) { paisFrom +=
       * text[linha].charAt(indexColuna++); }
       */
    } else
      System.out.println("ERROR PAISFROM");
    return paisFrom;
  }

  public static String toGetOriginLanguage(String[] text) {
    String toOriginLanguage = "";
    int linha = 0;
    // int indexColuna = 0;
    while (!text[linha].contains("Idioma original") && linha < text.length) {
      linha++;
    }
    if (text[linha].contains("Idioma original")) {
      linha++;
      toOriginLanguage += pegaAtributo(text[linha]);
    }
    return toOriginLanguage;
  }

  public static String toGetOriginEmitv(String[] text) {
    String toOriginEmitv = "";
    int linha = 0;
    int indexColuna = 0;
    while (!text[linha].contains("Emissora de tele")) {
      linha++;
    }
    if (text[linha].contains("Emissora de tele")) {
      linha++;
      toOriginEmitv = pegaAtributo(text[linha]);
      /*
       * text[linha] = text[linha].replaceAll("</a>", "");
       * 
       * indexColuna = text[linha].length() - 1;
       * 
       * while (text[linha].charAt(indexColuna) != '<' && indexColuna > 0) {
       * indexColuna--; } indexColuna--; while (text[linha].charAt(indexColuna) != '>'
       * && indexColuna > 0) { indexColuna--; } indexColuna++; while
       * (text[linha].charAt(indexColuna) != '<' && indexColuna > 0) { toOriginEmitv
       * += text[linha].charAt(indexColuna++); }
       */
    }
    return toOriginEmitv;
  }

  public static String toGetOriginalTransmition(String[] text) {
    String toOriginalTransmition = "";
    int linha = 0;
    int indexlinha = 0;
    while (!text[linha].contains("Transmiss") && linha < text.length) {
      linha++;
    }
    if (text[linha].contains("Transmiss") && linha < text.length) {
      linha++;
      toOriginalTransmition = pegaAtributo(text[linha]);

    }

    return toOriginalTransmition;
  }

  public static int toGetNumTemporadas(String[] text) {
    String numeroStringFormat = "";
    int numeroParsed = 0;
    int linha = 0;
    int indexlinha = 0;
    while (!text[linha].contains("de temporadas") && linha < text.length) {
      linha++;
    }
    if (text[linha].contains("de temporadas") && linha < text.length) {
      linha++;
      while (indexlinha < text[linha].length() - 1 && text[linha].charAt(indexlinha++) != '>')
        ;
      while (indexlinha < text[linha].length() - 1 && text[linha].charAt(indexlinha) != '<'
          && onlyNumbers(text[linha].charAt(indexlinha))) {
        if (onlyNumbers(text[linha].charAt(indexlinha))) {
          numeroStringFormat += text[linha].charAt(indexlinha);
        }
        indexlinha++;
      }

      numeroParsed = Integer.parseInt(numeroStringFormat);
    }
    return numeroParsed;
  }

  public static int toGetNumEpisodes(String[] text) {
    MyIO.setCharset("UTF-8");
    String numeroStringFormat = "";
    int numeroParsed = 0;
    int linha = 0;
    int indexlinha = 0;
    while (linha < text.length && !text[linha].contains("de temporadas")) {
      linha++;
    }
    linha += 5;
    if (text[linha].length() == 4) {
      linha += 2;
    }
    while (indexlinha < text[linha].length() - 1 && text[linha].charAt(indexlinha) != '>') {
      indexlinha++;
    }
    while (indexlinha < text[linha].length() - 1 && text[linha].charAt(indexlinha) != '<') {
      if (onlyNumbers(text[linha].charAt(indexlinha))) {
        numeroStringFormat += text[linha].charAt(indexlinha);
      }
      indexlinha++;
    }
    // System.out.println(numeroStringFormat);
    numeroParsed = Integer.parseInt(numeroStringFormat);
    return (int) numeroParsed;
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

  // Funçoes de verificação. && funçoes para me ajudar
  public static boolean isLetraOrNumber(char a) {
    return (a >= 'a' && a <= 'z' || a >= 'A' && a <= 'Z' || a >= '0' && a <= '9' || a == ' ' || a == '(' || a == ')');
  }

  public static boolean isLetraOrNumber2(char a) {
    return (a >= 'a' && a <= 'z' || a >= 'A' && a <= 'Z' || a >= '0' && a <= '9');
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

  // Funçoes paralelas para me ajudar

  // Funçao main principal ()
  public static void main(String[] args) {
    String entrada = MyIO.readLine();
    String[] allIn = new String[70];
    Series[] serie = new Series[63];
    int i = 0;
    String endereco = "../series/";

    while (!isFim(entrada) && i < serie.length) {
      allIn[i++] = entrada;
      entrada = MyIO.readLine();
    }
    allIn[i] = entrada;
    i = 0;
    while (!isFim(allIn[i]) && i < allIn.length - 1) {
      serie[i++] = new Series();
    }
    i = 0;
    while (!isFim(allIn[i]) && i < serie.length) {
      serie[i] = ler(endereco + allIn[i++]);
    }
    i = 0;
    while (!isFim(allIn[i])) {
      imprimir(serie[i++]);
    }
  }
}
