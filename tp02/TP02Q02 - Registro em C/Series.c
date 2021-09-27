#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
typedef struct Series Series;
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
    return (*a >= 'a' && *a <= 'z' || *a >= 'A' && *a <= 'Z' || *a >= '0' && *a <= '9' || *a == ' ' || *a == '(' || *a == ')'|| *a == '[' || *a == ']');
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

// funçao que quando chamada vai setar toda a struct;
void ler(char *caminho, Series *serie)
{
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
}
// funçao limitante de entradas
bool isFim(char *entrada)
{
    return (entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M');
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

int main()
{
    char **entradas = (char **)calloc(100, sizeof(char *));
    char **urls = (char **)calloc(500, sizeof(char *));
    Series **series = (Series **)calloc(61, sizeof(Series *));
    int i = 0;
    // Separando memoria para as entradas
    for (i = 0; i < 63; i++)
    {
        entradas[i] = (char *)calloc(1000, sizeof(char));
    }
    // Ler Entradas
    i = -1;
    do
    {
        i++;
        fgets(entradas[i], 1000, stdin);
        entradas[i][strlen(entradas[i]) - 1] = '\0';
    } while (!isFim(entradas[i]));
    // Separando memoria para as urls e juntando com entrada
    for (int i = 0; i < 61; i++)
    {
        urls[i] = (char *)calloc(100, sizeof(char));
        strcpy(urls[i], "/tmp/series/");
        strcat(urls[i], entradas[i]);
    }
    // Criando espaço para cada estrutura e chamando a funçao ler
    for (int i = 0; i < 61; i++)
    {
        series[i] = (Series *)calloc(1, sizeof(Series));
    }
    for (int i = 0; i < 61; i++)
    {
        ler(urls[i], series[i]);
        imprimirStruct(series[i]);
        free(urls[i]);
        free(series[i]);
    }
    return 0;
}

/* */