import java.util.Random;

public class HashEncadeado {
    class No{
        int chave;
        int valor;
        No proximo;

        No(int chave, int valor){
            this.chave = chave;
            this.valor = valor;
            this.proximo = null;
        }
    }

    static int tamanhoDados = 100;
    int[] tabelaChaves  = new int[tamanhoDados];
    int[] tabelaValores = new int[tamanhoDados];
    No[]  tabelaLista   = new No[tamanhoDados];

    static int qtdPosicoesVazias = tamanhoDados;
    static int qtdListas = 0;
    static int tamanhoMaiorLista = 0; static No inicioMaiorLista = null;
    static No tresUltimosElementos = null;



    void inserirTresUltimos(int chave, int valor){
        No novoNo = new No(chave, valor);

        if(tresUltimosElementos == null){
            tresUltimosElementos = novoNo;
            return;
        }

        No atual = tresUltimosElementos;
        int contagem = 1;
        while(atual.proximo != null){
            atual = atual.proximo;
            contagem++;
        }

        atual.proximo = novoNo;
        contagem++;

        if(contagem > 3){
            tresUltimosElementos = tresUltimosElementos.proximo;
        }
    }

    // Inicialização com valores impossíveis
    public HashEncadeado(){
        for(int i = 0; i < tamanhoDados; i++){
            tabelaChaves[i] = -1;
            tabelaLista[i] = null;
        }
    }

    int funcaoHash(int chave){
        return chave % tamanhoDados;
    }


    int buscaHash(int chaveBuscada){
        int idx = funcaoHash(chaveBuscada);

        // Era o elemento do vetor
        if(tabelaChaves[idx] == chaveBuscada) return tabelaValores[idx];

        // Não era o elemento, precisa olhar pela lista encadeada
        No atual = tabelaLista[idx];
        while(atual != null && atual.chave != chaveBuscada && atual.chave < chaveBuscada){
            atual = atual.proximo;
        }

        if(atual != null && atual.chave == chaveBuscada) return atual.valor;
        else {
            System.out.println("CHAVE NÃO ENCONTRADA.");
            return -1;
        }
    }

    
    void insereHash(int novaChave, int novoValor){
        int idx = funcaoHash(novaChave);


        if(tabelaChaves[idx] == -1){ // É o primeiro elemento na posição
            tabelaChaves[idx] = novaChave;
            tabelaValores[idx] = novoValor;
            qtdPosicoesVazias--;
        }
        else { // Não é o primeiro elemento na posição
            No novoNo = new No(novaChave, novoValor);
            inserirTresUltimos(novaChave, novoValor);

            if(tabelaLista[idx] == null){ // Não havia nós inseridos
                tabelaLista[idx] = novoNo;
                qtdListas++;
            }
            else { // Havia nós inseridos
                No atual = tabelaLista[idx];


                // primeira posição
                if(atual.chave >= novaChave){
                    novoNo.proximo = atual;
                    tabelaLista[idx] = novoNo;
                }
                else{
                    // posição aleatória
                    while(atual.proximo != null && atual.proximo.chave < novaChave){
                        atual = atual.proximo;
                    }

                    if(atual.proximo == null) atual.proximo = novoNo;
                    else {
                        novoNo.proximo = atual.proximo;
                        atual.proximo = novoNo;
                    }
                }
            }

            // Calcula tamanho da maior lista
            int tamanhoListaAtual = 0;
            No temp = tabelaLista[idx];
            while (temp != null) {
                tamanhoListaAtual++;
                temp = temp.proximo;
            }

            if (tamanhoListaAtual > tamanhoMaiorLista) {
                tamanhoMaiorLista = tamanhoListaAtual;
                inicioMaiorLista = tabelaLista[idx];
            }
        }
    }



    public static void main(String[] args){
        HashEncadeado hash = new HashEncadeado();
        Random random = new Random(1090);

        int quantidadeParaInserir = 1000;

        // Inserção
        for (int i = 0; i < quantidadeParaInserir; i++) {
            int chaveAleatoria = random.nextInt(100001);
            int valorAleatorio = random.nextInt(100001);
            
            hash.insereHash(chaveAleatoria, valorAleatorio);
        }

        System.out.println("Maior lista encadeada gerada:");
        No atualMaiorLista = inicioMaiorLista;
        while(atualMaiorLista != null){
            System.out.print("[Chave: " + atualMaiorLista.chave + " | Valor: " + atualMaiorLista.valor + "]");
            if(atualMaiorLista.proximo != null){
                System.out.print(" -> ");
            }

            atualMaiorLista = atualMaiorLista.proximo;
        }
        System.out.println();

        System.out.println("-----------------------------------------------------------");
        System.out.println("Quantidade de listas encadeadas geradas: " + qtdListas);
        System.out.println("Quantidade de posições vazias: " + qtdPosicoesVazias);

        No atual3Ultimos = tresUltimosElementos; int contagem = 998;
        while(atual3Ultimos != null){
            System.out.print(contagem + "° elemento: ");
            System.out.println("[Chave: " + atual3Ultimos.chave + " | Valor: " + atual3Ultimos.valor + "]");
            atual3Ultimos = atual3Ultimos.proximo;
            contagem++;
        }

        int resposta = hash.buscaHash(1);
        System.out.println("Valor encontrado: " + resposta);
        resposta = hash.buscaHash(89500);
        System.out.println("Valor encontrado: " + resposta);
    }
}
