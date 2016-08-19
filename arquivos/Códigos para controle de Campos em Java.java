
/*****************************************************************************************
 **  Autor: Ildomar Coelho                                                              **
 **  Data: 08/2016                                                                      **
 **                                                                                     **
 **  Versão: 1.0                                                                        **
 **                                                                                     **
 **  Objetivo Geral:                                                                    **
 **     Preservar estudo de tratamento de campos de telas JFrames feitas em Java.       **
 **     Para que o conhecimentoadquirido possa ser REUTILIZADO	e COMPARTILHADO.        **
 **                                                                                     **
 **  Objetivos Específicos:                                                             **
 **     * Agilizar trabalhos futuros;                                                   **
 **     * Elaborar programas de qualidade;                                              **
 **     * Evitar ao máximo deixar "brechas" para o usuário inserir erros no programa;   **
 **     * Centralizar linhas de códigos que servirão na maioria dos projetos.           **
 **	                                                                                    **
 ****************************************************************************************/



 /*================================   INTRODUÇÃO   ======================================

  Este documento apresenta trechos aleatórios de linhas de códigos, ou seja, não são 
  classes prontas para serem inseridas ao projeto. Portanto cabe ao programador estudar
  cada trecho e adaptá-los ao projeto.
  Será dado uma descrição simples e, se possível, um exemplo de uso de cada trecho. 
  
  Para controle de campos será usado uma classe extendida da classe PlainDocument. Além
  de Eventos da classe KeyAdapter nos próprios campos.
  
 */
 
                            █████ TRECHO I █████
 /*==============  EXTRUTURA DA CLASSE EXTENDIDA DA PlainDocument   =====================
 
 
 */
 /* Classe "ControleDigitos" extendida da classe "PlainDocument */
 public class ControleDigitos extends PlainDocument { 
	private int tamanhoMaximo; // Variável que recebe o parâmetro "tamanhoMaximo".
	 
	private String nomeCampo; // Variável que recebe o parâmetro "nomeCampo".
	 /* A varável "nomeCampo" serve como um identificador do campo que será
		inserido o filtro desta classe. É o parâmetro "nomeCampo" que torna possível
		o uso desta classe para implememtar vários filtros */
		
	 /* Método construtor onde determinamos os parâmetros que queremos receber de quem 
	 implementar esta classe. */
	 public ControleDigitos(int tamanhoMaximo, String nomeCampo) {
		super();
		// O filtro só pode ser usado em campos com no mínimo um caractere.
		if(tamanhoMaximo<=0){
		// Caso o tamanho passado
			throw new IllegalArgumentException("Especifique uma Quantidade de digitos para o Campo!");
		}
		/* A variável tamanho máximo deve ser passada como parâmetro ao instanciar
		   esta classe. Esta variável determinar quantos digitos podem ser inseridos
		   no campo (digitos >= 1). */
		this.tamanhoMaximo=tamanhoMaximo;
		this.nomeCampo = nomeCampo;
	}	 
	 
	/*O método abaixo é uma implementação da Classe Pai. É onde colocamos
	   os filtros para serem enviados a esta que tratará de acordo.
	   Repare que através do parâmetro recebido (nomeCampo) podemos controlar qual
	   filtro será usado no campo que instanciou esta classe.
	  Este método é usado, por um evento de teclas, pela classe pai,
	   Ou seja, cada vez que o usuário digitar um caractere no campo a classe pai
	   coleta o texto do campo e verifica se os caracteres do texto está de acordo
       com o filtro passado e se a quantidade de digitos não ultrapassa o valor
	   setado pelo parâmetro "tamanhoMaximo".
	  Os parâmetros "offset" e "attr" são usados apenas pela classe pai e não por
	  nós. Nós estamos usando a variável "tamanhoMaximo" para controlar o numero 
	  máximo de digitos, através do laço if. Ou seja, quando detectamos, através do if,
	  que o campo já atingiu o valor máximo regido pela variável "tamanhoMaximo", não 
	  deixamos mais que a classe pai seja usada e isso impede que o valor seja inserido
	  no campo através do método "insertString()"
	*/	 
	@Override //SobreEscreve método pai
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException{
		if(str==null || getLength() == tamanhoMaximo)
			return;
		
		try{
			//Coleta a quantia atual de caracteres no campo somada com a quantia a ser inserida.
			int quantiaTotal = (getLength()+str.length());
			//Compara para saber se ainda tem espaço para inserir no campo.
			if(quantiaTotal <= tamanhoMaximo){
		/* É neste laço switch que você deve colocar os filtros desejado
			e marcar com um identificador que deve ser passado pelo campo que va utilizar
			tal filtro.
			Exemplo: se eu quiser implementar filtro para codigo de barras,
			basta eu saber os limites que devo colocar no campo como:
			 -> Só pode aceitar números (0 a 9);
			 -> Só pode ser digitados, neste caso, 13 dígitos;
			Assim,
			aqui eu coloco um filtro identificado de "txtCodBarras" (pode ser qualquer String),
			e onde eu for instanciar faço da seguinte forma:
			campo.setDocument(new ControleDigitos(13, "txtCodBarras")); 
			onde "campo" é o JTextField que receberá o filtro;
			"13" é o limite de quantia máxima de digitos;
			"'txtCodBarras'" o identificador que será usado aqui pelo switch.
			Abaixo segue exemplos de implementação de alguns filtros.
			*/
				switch(nomeCampo){ // 
				case "txtCodBarras":
					// Aceita só números de 0 a 9.
					super.insertString(offset, str.replaceAll("[^0-9]",""), attr);
					return;
				case "numerosReais": 
					// Aceita números de 0 a 9 mais vírgula, ponto e sinal de menos (traço).
					super.insertString(offset, str.replaceAll("[^0-9|.|,|-]",""), attr);
					return;
				case "numerosReaisPositivos":
					// Aceita números de 0 a 9 mais vírgula e ponto.
					super.insertString(offset, str.replaceAll("[^0-9|.|,]",""), attr);
					return;
				case "pontoPorVirgula":
					// Aceita todos os caracteres mas SUBSTITUI ponto por vírgula.
					super.insertString(offset, str.replaceAll("[.]",","), attr);
					return;
				case "colocaAspasNoParentese":
			// Aceita todos os caracteres mas SUBSTITUI abre-parenteses por "("")" (ignore as aspas externas).
					super.insertString(offset, str.replaceAll("[(]","(\"\")"), attr);
					return;
				default:
				   // Aceita todos os caracteres.
					super.insertString(offset, str.replaceAll("",""), attr);
					return;
				}
			}//fim_se
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "erro Controle Digitos = "+e.getMessage());
		}//fim_try
			
	}//fim Método insertString
	
	 
	 

 }//FIM Classe
 
                            █████ TRECHO II █████
 /*=========== IMPLEMENTANDO CAMPO PARA CÓDIGO DE BARRAS DE 13 DÍGITOS  =================
 
 O trecho abaixo implementa filtro para o campo txtCodBarras de modo que só aceita os
 caracteres de 0 a 9 e não permite a inserção de mais que 13 caracteres no campo.
 
 OBS. Faz uso da estrutura apresentada no TRECHO I.
 
 */
 
 /* Insere um filtro Document do tipo ControleDigitos, passando 13 como valor do 
  parâmetro tamanhoMaximo e "txtCodBarras" como valor do parâmetro nomeCampo. */
 txtCodBarras.setDocument(new ControleDigitos(13,"txtCodBarras"));
 
 /* Implementa também o evento KeyAdapter para podermos captar cada tecla digitada 
  e podermos fazer algumas coisinhas rsrs.
  No método KeyTyped(), na varável 'e' do tipo KeyEvent, recebemos o objeto relacionado 
  à última tecla pressionada. Por isso através do método getKeyChar() podemos coletar
  o código decimal ASCII relacionado à esta tecla. Exmplo: na tabela ASCII o código  
  da tecla ENTER é 13. */  
 txtCodBarras.addKeyListener(new KeyAdapter() {
 
	/* Este método é chamado autmomaticamente a cada tecla pressionada.
		Logo podemos usá-lo para tomar decisões como por exmplo, comparar o texto
		atual no campo para sabermos se satisfaz algum requisito, como por exemplo:
		Podemos determinar que ao ser pressionada a tecla ENTER significa que o 
		usuário já completou a inserção e deseja que o valor seja tratado, assim:
		-> Foi pressionado ENTER? 
		   Se sim então trata os dados e muda para o próximo campo. */
	public void keyTyped(java.awt.event.KeyEvent e) {
		//Coleta o valor decimal da tecla pressionada.
		int k = e.getKeyChar();
		//Se a tecla for ENTER e já contiver 3 ou mais caracteres (obs. ovalor 3 não é regra).	
		if(k==10 && txtCodBarras.getText().length()>=3){ 
			/* Chama um método externo que pode servir para fazer uma busca no Banco de Dados,
			ou um cadastro, ou o que o programador desejar fazer com este codigo de barras. */
			trataCodBarras(txtCodBarras.getText());
			//Limpa o texto do campo para esperar novo dado
			txtCodBarras.setText("");
		}  	 
	}
}); //fIM
 
 /*  OU SEJA, neste trecho vimos como implementar simultaneamente, no mesmo campo,
     Filtro de Texto e Evento de teclado.
	 Com isso, sua criatividade é o limite. */
 
 
 								█████ TRECHO III █████
 /*=================== IMPLEMENTANDO CAMPO PARA NUMEROS REAIS  ==========================
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 