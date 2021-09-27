#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
typedef struct Series Series;
typedef struct Lista Lista;
#define MAX_NUMER_OF_LINES 2600;
struct Series
{
    char *nome;
    char *formato;
    char *duracao;
    char *paisFrom;
    char *originalLanguage;
    char *originalEmissor;
    char *orignalTransmistion;
    int numeroTemp;
    int numeroEpisodes;
};

bool isLetraorNumber(char *a)
{
    return (*a >= 'a' && *a <= 'z' || *a >= 'A' && *a <= 'Z' || *a >= '0' && *a <= '9' || *a == ' ' || *a == '(' || *a == ')' || *a == '[' || *a == ']');
}
bool So_se_for_mesmo(char *a)
{
    return (*a >= 'a' && *a <= 'z' || *a >= 'A' && *a <= 'Z' || *a >= '0' && *a <= '9');
}
bool isLetraorNumber2(char *a)
{
    return (*a >= 'a' && *a <= 'z' || *a >= 'A' && *a <= 'Z' || *a >= '0' && *a <= '9');
}
bool onlyNumbers(char *a)
{
    return (*a >= '0' && *a <= '9');
}
// funçoes de set(para setar a struct Series)
void setNome(char *attribute, Series *serie)
{
    serie->nome = attribute;
}
void setFormato(char *attribute, Series *serie)
{
    serie->formato = attribute;
}
void setDuracao(char *attribute, Series *serie)
{
    serie->duracao = attribute;
}
void setPaisFrom(char *attribute, Series *serie)
{
    serie->paisFrom = attribute;
}
void setOriginLanguage(char *attribute, Series *serie)
{
    serie->originalLanguage = attribute;
}
void setOriginalEmissor(char *attribute, Series *serie)
{
    serie->originalEmissor = attribute;
}
void setOriginalTransmition(char *attribute, Series *serie)
{
    serie->orignalTransmistion = attribute;
}
void setNumTemp(int attribute, Series *serie)
{
    serie->numeroTemp = attribute;
}
void setNumEpisodes(int attribute, Series *serie)
{
    serie->numeroEpisodes = attribute;
}

// funçoes de get()
char *getNome(Series *serie)
{
    return (serie->nome);
}
char *getFormato(Series *serie)
{
    return (serie->formato);
}
char *getDuracao(Series *serie)
{
    return (serie->duracao);
}
char *getPaisFrom(Series *serie)
{
    return (serie->paisFrom);
}
char *getOriginalLanguage(Series *serie)
{
    return (serie->originalLanguage);
}
char *getOriginalEmissor(Series *serie)
{
    return (serie->originalEmissor);
}
char *getOriginalTranmistion(Series *serie)
{
    return (serie->orignalTransmistion);
}
int getNumTemp(Series *serie)
{
    return (serie->numeroTemp);
}
int getNumEpisodes(Series *serie)
{
    return (serie->numeroEpisodes);
}

// funçao pega Atributo (char*)
char *pegaAtributo(char *linha)
{
    int indiceColuna = 0, indiceAtributo = 0;
    char *atributo = (char *)calloc(250, sizeof(char));
    while (indiceColuna < strlen(linha))
    {
        if (strlen(linha) > indiceColuna && linha[indiceColuna] == '>')
        {
            if (isLetraorNumber(&linha[indiceColuna + 1]) && indiceColuna < strlen(linha))
            {
                indiceColuna++;
                while (strlen(linha) > indiceColuna && linha[indiceColuna] != '<')
                {
                    atributo[indiceAtributo++] = linha[indiceColuna++];
                }
            }
            else if (linha[indiceColuna + 1] == '&')
            {
                atributo[indiceAtributo++] = '(';
            }
        }
        indiceColuna++;
    }
    return (atributo);
}
char *pegaAtributo2(char *linha)
{
    int indiceColuna = 0, indiceAtributo = 0;
    char *atributo = (char *)calloc(250, sizeof(char));
    while (indiceColuna < strlen(linha) - 1)
    {
        if (strlen(linha) - 1 > indiceColuna + 1 && linha[indiceColuna] == '>')
        {

            if (isLetraorNumber2(&linha[indiceColuna + 1]))
            {
                indiceColuna++;
                while (strlen(linha) > indiceColuna && linha[indiceColuna] != '<')
                {
                    atributo[indiceAtributo++] = linha[indiceColuna++];
                }
            }
        }
        indiceColuna++;
    }
    return (atributo);
}

// funçoes de tratamento do html
char *tratamentoNome(char **html)
{
    char *nome = (char *)calloc(100, sizeof(char));
    int indiceLinha = 0, indiceColuna = 0;
    while (strstr(html[indiceLinha], "firstHeading") == NULL && indiceLinha < 2500)
    {
        indiceLinha++;
    }

    nome = pegaAtributo2(html[indiceLinha]);

    return nome;
}
char *tratamentoFormato(char **html)
{
    char *nome = (char *)calloc(100, sizeof(char));
    int indiceLinha = 0, indiceColuna = 0;
    while (strstr(html[indiceLinha], "Formato") == NULL && indiceLinha < 2500)
    {
        indiceLinha++;
    }
    indiceLinha++;

    nome = pegaAtributo(html[indiceLinha]);

    return nome;
}
char *tratamentoDuracao(char **html)
{
    char *duration = (char *)calloc(100, sizeof(char));
    int indiceLinha = 0, indiceColuna = 0;
    while (strstr(html[indiceLinha], "Duração") == NULL && indiceLinha < 2500)
    {
        indiceLinha++;
    }
    indiceLinha++;

    duration = pegaAtributo(html[indiceLinha]);

    return duration;
}
char *tratamentoPaisFrom(char **html)
{
    char *paisFrom = (char *)calloc(100, sizeof(char));
    int indiceLinha = 0, indiceColuna = 0;
    while (strstr(html[indiceLinha], "País de origem") == NULL && indiceLinha < 2500)
    {
        indiceLinha++;
    }
    indiceLinha++;

    paisFrom = pegaAtributo2(html[indiceLinha]);

    return paisFrom;
}
char *tratamentoOriginLanguage(char **html)
{
    char *language = (char *)calloc(100, sizeof(char));
    int indiceLinha = 0, indiceColuna = 0;
    while (strstr(html[indiceLinha], "Idioma original") == NULL && indiceLinha < 2500)
    {
        indiceLinha++;
    }
    indiceLinha++;

    language = pegaAtributo(html[indiceLinha]);

    return language;
}
char *tratamentoOriginalEmissor(char **html)
{
    char *originalEmissor = (char *)calloc(100, sizeof(char));
    int indiceLinha = 0, indiceColuna = 0;
    while (strstr(html[indiceLinha], "Emissora de televisão original") == NULL && indiceLinha < 2500)
    {
        indiceLinha++;
    }
    indiceLinha++;

    originalEmissor = pegaAtributo2(html[indiceLinha]);

    return originalEmissor;
}
char *tratamentoOriginalTransmition(char **html)
{
    char *originalEmissor = (char *)calloc(100, sizeof(char));
    int indiceLinha = 0, indiceColuna = 0;
    while (strstr(html[indiceLinha], "Transmissão original") == NULL && indiceLinha < 2500)
    {
        indiceLinha++;
    }
    indiceLinha++;

    originalEmissor = pegaAtributo(html[indiceLinha]);

    return originalEmissor;
}
int tratamentoNumTemp(char **html)
{
    char *numeroTemporadas = (char *)calloc(100, sizeof(char));
    int indiceLinha = 0, indiceColuna = 0;
    while (strstr(html[indiceLinha], "N.º de temporadas") == NULL && indiceLinha < 2500)
    {
        indiceLinha++;
    }
    indiceLinha++;
    numeroTemporadas = pegaAtributo(html[indiceLinha]);
    int numero = atoi(numeroTemporadas);
    return numero;
}
int tratamentoNumEpisodes(char **html)
{
    char *numeroEpisodes = (char *)calloc(100, sizeof(char));
    int indiceLinha = 0, indiceColuna = 0;
    while (strstr(html[indiceLinha], "N.º de episódios") == NULL && indiceLinha < 2500)
    {
        indiceLinha++;
    }
    indiceLinha++;
    numeroEpisodes = pegaAtributo2(html[indiceLinha]);
    int numero = atoi(numeroEpisodes);
    return numero;
}
char *Concatenation(char *a)
{
    char *b = (char *)calloc(50, sizeof(char));
    strcpy(b, "../series/");
    strcat(b, a);
    return b;
}
// funçao que retorna nosso texto(em que vamos extrair dados para a struct)
char **lerHtml(char *caminho)
{
    char **html = (char **)calloc(2600, sizeof(char *));
    int i;
    for (i = 0; i < 2600; i++)
    {
        html[i] = (char *)calloc(5000, sizeof(char));
    }
    FILE *arquivo = fopen(caminho, "r");
    if (arquivo != NULL)
    {
        i = 0;
        while (!feof(arquivo))
        {
            fgets(html[i], 5000, arquivo);
            i++;
        }
    }
    else
    {
        printf("ERROR");
    }
    fclose(arquivo);
    return html;
}

Series **Alocador_de_Series(Series **series, int tamanho)
{
    series = (Series **)calloc(tamanho, sizeof(Series *));
    for (int i = 0; i < tamanho; i++)
    {
        series[i] = (Series *)calloc(1, sizeof(Series));
    }
    return series;
}
// funçao que quando chamada vai setar toda a struct;
Series *ler(char *caminho)
{
    Series *serie = (Series *)calloc(1, sizeof(Series));
    char **html = lerHtml(caminho);
    setNome(tratamentoNome(html), serie);
    setFormato(tratamentoFormato(html), serie);
    setDuracao(tratamentoDuracao(html), serie);
    setPaisFrom(tratamentoPaisFrom(html), serie);
    setOriginLanguage(tratamentoOriginLanguage(html), serie);
    setOriginalEmissor(tratamentoOriginalEmissor(html), serie);
    setOriginalTransmition(tratamentoOriginalTransmition(html), serie);
    setNumTemp(tratamentoNumTemp(html), serie);
    setNumEpisodes(tratamentoNumEpisodes(html), serie);

    // desalocar Texto html
    for (int i = 0; i < 2600; i++)
    {
        free(html[i]);
    }
    return serie;
}
// funçao limitante de entradas
bool isFim(char *entrada)
{
    return (entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M');
}

struct Lista
{
    Series **series;
    int added;
    int tamanho;
};

Lista Inicia_Lista(Lista list, int tamanho)
{
    list.series = (Series **)calloc(tamanho, sizeof(Series *));
    for (int i = 0; i < tamanho; i++)
    {
        list.series[i] = (Series *)calloc(1, sizeof(Series));
    }
    list.added = 0;
    list.tamanho = tamanho;
    return list;
}

Lista inserir_Serie_Inicio(Lista list, Series *serie)
{
    if (list.added >= list.tamanho)
    {
        printf("Error");
        system("pause");
    }
    for (int i = list.added; i > 0; i--)
    {
        list.series[i] = list.series[i - 1];
    }
    list.series[0] = serie;
    list.added++;
}

Lista inserir_Serie_Fim(Lista list, Series *serie)
{
    if (list.added >= list.tamanho)
    {
        printf("Error");
        system("pause");
    }
    list.series[list.added] = serie;
    list.added++;
    return list;
}

Lista inserir(Lista list, Series *serie, int pos)
{
    if (list.added >= list.tamanho || pos < 0 || pos > list.added)
    {
        printf("Error");
        system("pause");
    }
    for (int i = list.added; i > pos; i--)
    {
        list.series[i] = list.series[i - 1];
    }
    list.series[pos] = serie;
    list.added++;
    return list;
}

Series *remover_Series_Inicio(Lista list)
{
    if (list.added == 0)
    {
        printf("Error");
        system("pause");
    }
    Series *resp = list.series[0];
    list.added = list.added - 1;
    for (int i = 0; i < list.added; i++)
    {
        list.series[i] = list.series[i + 1];
    }
    return resp;
}

Series *remover_Series_Fim(Lista list)
{
    if (list.added == 0)
    {
        printf("Erroraaa");
        system("pause");
    }
    list.added = list.added - 1;
    return list.series[list.added];
}

Series *remover(Lista list, int pos)
{
    if (list.added == 0 || pos < 0 || pos > list.added)
    {
        printf("Error");
        system("pause");
    }
    Series *resp = list.series[pos];
    list.added = list.added - 1;
    for (int i = pos; i < list.added; i++)
    {
        list.series[i] = list.series[i + 1];
    }
    return resp;
}

void imprimirStruct(Series *a)
{
    printf("%s %s %s %s %s %s %s %i %i\n",
           getNome(a),
           getFormato(a),
           getDuracao(a),
           getPaisFrom(a),
           getOriginalLanguage(a),
           getOriginalEmissor(a),
           getOriginalTranmistion(a),
           getNumTemp(a),
           getNumEpisodes(a));
}

void imprimir_Lista(Lista list)
{
    for (int i = 0; i < list.added; i++)
    {
        imprimirStruct(list.series[i]);
    }
}

Lista tratar_Operacoes(char **Entradas2, Lista list, int total_de_Operacoes)
{
    Series **series_Removeds = Alocador_de_Series(series_Removeds, 9);
    int countRemoved = 0;
    for (int i = 0; i < total_de_Operacoes; i++)
    {
        char *espaco_Auxiliar = (char *)calloc(50, sizeof(char));
        char *espaco_Auxiliar2 = (char *)calloc(50, sizeof(char));
        int countAux = 0;
        int countAux2 = 0;
        if (Entradas2[i][0] == 'I' && Entradas2[i][1] == 'I')
        {
            for (int j = 3; j < strlen(Entradas2[i]) - 1; j++)
            {
                espaco_Auxiliar[countAux++] = Entradas2[i][j];
            }
            list = inserir_Serie_Inicio(list, ler(Concatenation(espaco_Auxiliar)));
            free(espaco_Auxiliar);
        }
        else if (Entradas2[i][0] == 'I' && Entradas2[i][1] == 'F')
        {
            for (int j = 3; j < strlen(Entradas2[i]) - 1; j++)
            {
                espaco_Auxiliar[countAux++] = Entradas2[i][j];
            }

            list = inserir_Serie_Fim(list, ler(Concatenation(espaco_Auxiliar)));
            free(espaco_Auxiliar);
        }
        else if (Entradas2[i][0] == 'I' && Entradas2[i][1] == '*')
        {
            for (int j = 3; onlyNumbers(&Entradas2[i][j]); j++)
            {
                espaco_Auxiliar[countAux++] = Entradas2[i][j];
            }
            for (int j = 6; j < strlen(Entradas2[i]) - 1; j++)
            {
                espaco_Auxiliar2[countAux2++] = Entradas2[i][j];
            }
            list = inserir(list, ler(Concatenation(espaco_Auxiliar2)), atoi(espaco_Auxiliar));
            free(espaco_Auxiliar);
            free(espaco_Auxiliar2);
        }
        else if (Entradas2[i][0] == 'R' && Entradas2[i][1] == 'I')
        {
            series_Removeds[countRemoved++] = remover_Series_Inicio(list);
            printf("(R) %s\n", series_Removeds[countRemoved - 1]->nome);
        }
        else if (Entradas2[i][0] == 'R' && Entradas2[i][1] == 'F')
        {
            series_Removeds[countRemoved++] = remover_Series_Fim(list);
            printf("(R) %s\n", series_Removeds[countRemoved - 1]->nome);
        }
        else if (Entradas2[i][0] == 'R' && Entradas2[i][1] == '*')
        {
            for (int j = 3; onlyNumbers(&Entradas2[i][j]); j++)
            {
                espaco_Auxiliar[countAux++] = Entradas2[i][j];
            }
            series_Removeds[countRemoved++] = remover(list, atoi(espaco_Auxiliar));
            printf("(R) %s\n", series_Removeds[countRemoved - 1]->nome);
        }
    }
    return list;
}

int main()
{
    Lista list;
    int i = 0, numeroInteracao, tamanho;
    char **entradas = (char **)calloc(50, sizeof(char *));
    char **newlines = (char **)calloc(50, sizeof(char *));
    char **entradas2 = (char **)calloc(50, sizeof(char *));
    for (i = 0; i < 50; i++)
    {
        entradas[i] = (char *)calloc(50, sizeof(char));
        entradas2[i] = (char *)calloc(50, sizeof(char));
        newlines[i] = (char *)calloc(50, sizeof(char));
    }
    i = 0;
    fgets(entradas[i], 50, stdin);
    entradas[i][strlen(entradas[i]) - 1] = '\0';
    while (!isFim(entradas[i]))
    {
        strcpy(newlines[i], "../series/");
        strcat(newlines[i], entradas[i]);
        i++;
        fgets(entradas[i], 50, stdin);
        entradas[i][strlen(entradas[i]) - 1] = '\0';
    }
    free(entradas[--i]);
    scanf("%i", &numeroInteracao);
    tamanho = i + numeroInteracao;
    list = Inicia_Lista(list, tamanho);
    /*for (i = 0; i < tamanho - numeroInteracao + 1; i++)
    {
        list = inserir_Serie_Fim(list, ler(newlines[i]));
    }*/
    fgets(entradas[tamanho - numeroInteracao], 50, stdin);
    for (i = 0; i < numeroInteracao; i++)
    {
        fgets(entradas2[i], 50, stdin);
    }
    list = tratar_Operacoes(entradas2, list, numeroInteracao);
    imprimir_Lista(list);
    return 0;
}

/* */