# TheMagic
API em java de criação e edição de Jogadores e Cartas baseado no card game Magic the Gathering.
Foram utilizadas as tecnologias java e SpringBoot e para compilação o gradle. Essa API simula a criação de Jogadores e de Cartas, baseada nesses jogadores.
Como banco de dados foi utilizado o [Postgres](https://www.postgresql.org/download/) e o [H2](http://www.h2database.com/html/download.html) para os testes.
Na pasta de queryDB você vai achar os comandos para criação do database, do schema e das tabelas.

# Pontos de Melhoria
Ainda falta adicionar alguns métodos, como armazenar o nome da carta sempre em português, realizar o filtro de cartas por ordem alfabética ou valor e
adicionar a validação de somente um jogador alterar somente sua lista de cartas. Também como melhoria cobrir melhor a aplicação com testes.

Obrigado pela leitura até aqui 🖖.

#🚀 Como executar

* Clone o repositório
* Instale as dependências com `gradle :build`. Utilize alguma IDE e ela realizará essa ação automáticamente(recomendo o IntelliJ).
* Suba a aplicação na IDE no botão de ▶ ou no modo debug 🕷
* Agora você pode acessar localhost:8080 do seu navegador
* A aplicação utiliza JSON para se comunicar, portanto recomendo utilizar o [Postman](https://www.postman.com/downloads/) ou ...
* o [Insomnia](https://insomnia.rest/download) para testar a aplicação. Abaixo segue exemplos dos Bodys para usar.
<p># Método GET (localhost:8080/cartas) ou (localhost:8080/jogadores).<p>
<p># Método POST(criar) Jogadores (localhost:8080/jogadores): <p>
<p>{
	"nome": "Lucas 6",
  "email": "teste314o3@teste.com.br",
  "senha": "teste41hg33"
}<p>
<p># Método POST(criar) Cartas (localhost:8080/cartas): <p>
<p>{
		"id": 19,
		"nome": "Armadura Divina",
		"edicao": "Rara",
		"idioma": "PORTUGUES",
		"laminada": false,
		"valor": 100.5,
		"caracteristica": "ARTEFATO",
		"jogadores": {
			"id": 4,
			"nome": "Lucas Walker",
			"email": "teste3@teste.com.br",
			"senha": "teste3"
		}
	}<p>
  
* Com essas dicas você conseguirá executar a aplicação e realizar a criação e a busca de jogadores e cartas.
