package busca_largura_8_puzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Main {

	// Fun�o que testa se o objetivo foi atingido
	public static boolean testaObjetivo(Noh_arvore no) {

		// preenche a matriz com o valor de objetivo pretendido
		int valor = 1;
		int[][] objetivo = new int[3][3];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				objetivo[i][j] = valor;
				valor++;
			}
		}
		objetivo[2][2] = 0;

		return Arrays.deepEquals(objetivo, no.getEstado());
	}

	// Fun��o que gera um novo n�
	public static Noh_arvore geraNoh(int profundidade, Noh_arvore no_pai, int[][] estado, String acao_tomada) {

		int[][] estado_gerado = estado.clone();

		return new Noh_arvore(profundidade, no_pai, estado_gerado, acao_tomada);
	}

	// gera o estado baseado na movimenta��o
	public static int[][] geraEstado(int[][] estado_pai, String acao_tomada) {

		int[][] estado_filho = estado_pai.clone();
		int posx = 0, posy = 0;
		int v_aux;

		// varre a matriz 3x3 para encontrar o espa�o vazio(0)
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (estado_filho[i][j] == 0) {
					posx = i;
					posy = j;
					break;
				}
			}
		}
		switch (acao_tomada) {
		case "ACIMA":
			if (posx - 1 >= 0) {
				v_aux = estado_filho[posx - 1][posy];
				estado_filho[posx - 1][posy] = estado_filho[posx][posy];
				estado_filho[posx][posy] = v_aux;
			}
			break;
		case "ABAIXO":
			if (posx + 1 <= 2) {
				v_aux = estado_filho[posx + 1][posy];
				estado_filho[posx + 1][posy] = estado_filho[posx][posy];
				estado_filho[posx][posy] = v_aux;
			}
			break;
		case "ESQ":
			if (posy - 1 >= 0) {
				v_aux = estado_filho[posx][posy - 1];
				estado_filho[posx][posy - 1] = estado_filho[posx][posy];
				estado_filho[posx][posy] = v_aux;
			}
			break;
		case "DIR":
			if (posy + 1 <= 2) {
				v_aux = estado_filho[posx][posy + 1];
				estado_filho[posx][posy + 1] = estado_filho[posx][posy];
				estado_filho[posx][posy] = v_aux;
			}
			break;
		default:
			break;
		}
		if (!Arrays.deepEquals(estado_pai, estado_filho))
			return estado_filho;
		return null;
	}

	// fun��o que exibe o estado atual da matriz do jogo
	public static void exibeMatriz(int[][] estado) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(estado[i][j]);
			}
			System.out.println();
		}
	}

	// Fun��o que verifica se o valor de estado do n� avaliado est� em explored ou
	// frontier
	public static boolean containExploredFrontier(LinkedList<Noh_arvore> frontier, Set<Noh_arvore> explored,
			Noh_arvore no) {
		boolean retorno_explored = false;
		boolean retorno_frontier = false;

		for (Noh_arvore noh_front : frontier) {
			if (Arrays.deepEquals(noh_front.getEstado(), no.getEstado())) {
				retorno_frontier = true;
				break;
			}
		}

		for (Noh_arvore noh_exp : explored) {
			if (Arrays.deepEquals(noh_exp.getEstado(), no.getEstado())) {
				retorno_explored = true;
				break;
			}
		}

		return retorno_explored || retorno_frontier;
	}

	// Fun��o da busca em largura
	public static void buscaLargura(Noh_arvore no) {
		LinkedList<Noh_arvore> frontier = new LinkedList<Noh_arvore>();
		Set<Noh_arvore> explored = new HashSet<Noh_arvore>();
		Noh_arvore noh = null;
		Noh_arvore noh_filho = null;
		String[] acoes = { "ACIMA", "ABAIXO", "ESQ", "DIR" };
		int profundidade = 1;
		boolean objetivo = false;

		if (testaObjetivo(no)) {
			exibeMatriz(no.getEstado());
			System.out.println("Solu��o Alcan�ada!!");
			System.out.println("Profundidade: " + no.getProfundidade());

		} else {
			System.out.println("resto processamento");
			frontier.add(no);

			while (true) {
				if (frontier.isEmpty()) {
					System.out.println("Solu��o falhou");
				} else {
					noh = frontier.pop();
					explored.add(noh);

					for (String acao : acoes) {
						if (geraEstado(no.getEstado(), acao) != null) {
							noh_filho = geraNoh(profundidade, noh, geraEstado(no.getEstado(), acao), acao);

							if (!containExploredFrontier(frontier, explored, noh_filho)) {
								if (testaObjetivo(noh_filho)) {
									System.out.println("objetivo alcan�ado!! " + "profundidade: " + profundidade);
									objetivo = true;
									break;
								} else {
									frontier.add(noh_filho);
								}
							}
						}

					}
					profundidade++;
				}
				// verifica flag de objetivo alcan�ado para sair do la�o mais externo
				if (objetivo)
					break;
			}

		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int[][] objetivo2 = new int[3][3];
		int valor = 1;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				objetivo2[i][j] = valor;
				valor++;
			}
		}

		objetivo2[2][2] = 0;

		// n� inicial com o estado inicial
		Noh_arvore no = new Noh_arvore(0, null, objetivo2, null);

		// buscaLargura(no);

	}

}
