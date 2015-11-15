# Testes de Software

<a name="index"/>
## Índice
1. [Introdução](#introduction)
2. [Grau de Testabilidade](#degreeoftestability)
3. [Estatísticas de Teste](#teststatistics)
4. [Bug Report Resolvido](#bugreportsolution)
5. [Contribuição do Grupo](#group_contribution)

<a name="introduction"/>
## Introdução

Ao longo deste relatório vamos analisar o *Terasology* no que diz respeito a testes de software. Na [primeira secção](#degreeoftestability) será feita a análise do grau de testabilidade do projeto, ou seja, o quão fácil é de testar os diferentes componentes do jogo, e como poderiam ser melhorados esses métodos de teste. De seguida, serão apresentadas algumas [estatísticas de teste](#teststatistics) do projeto, no que diz respeito a número de testes, cobertura, entre outros aspetos. Por fim, será relatado o processo de [correção de um bug](#bugreportsolution) relatado no projeto.

<a name="degreeoftestability"/>
## Grau de Testabilidade

Tanto se analisarmos o *engine* como os módulos do **Terasology**, é claro que os testes apenas podem ser aplicados aos seus sub-componentes tendo em conta a grande complexidade e multifuncionalidade de qualquer um deles como um todo. Por exemplo, um módulo em específico, como o *rendering*, embora possua uma só grande funcionalidade (traduzir as informações do jogo para imagens visíveis), subdivide-se em diversas pequenas tarefas (representação das entidades como conjuntos de polígonos, cálculo da cor dos pixéis, etc.). Neste sentido, a análise do grau de testabilidade deve-se estender a pequenos sub-componentes do software para que se perceba exatamente o quão fácil e útil é testar o software.

No que diz respeito à controlabilidade dos componentes sob teste (CUT - Component Under Test) é aparente que depende do componente em si. Isto é, quando se trata de um componente do *engine*, é provável que apresente controlabilidade reduzida uma vez que o engine interage com uma quantidade muito grande de módulos, logo, é mais difícil de prever para alguns casos quais são todas as situações possíveis. Por outro lado, um componente de um módulo deverá apresentar uma controlabilidade mais elevada pois a sua interação limita-se a outros componentes do módulo (ou, em alguns casos, outros módulos externos ao jogo) e ao *engine* em si. Deste modo, a controlabilidade mais elevada (por norma) dos componentes dos módulos torna mais fácil a execução de testes nestes em relação a alguns componentes do *engine*. Contudo, componentes mais "interiores" do *engine* poderão apresentar a mesma facilidade (quando considerada apenas a controlabilidade) uma vez que possuem interações mais limitadas e funcionalidades mais concentradas.

# TODO
The testability of software components (modules, classes) is determined by factors such as:
- Controllability: The degree to which it is possible to control the state of the component under test (CUT) as required for testing.
- Observability: The degree to which it is possible to observe (intermediate and final) test results.
- Isolateability: The degree to which the component under test (CUT) can be tested in isolation.
- Separation of concerns: The degree to which the component under test has a single, well defined responsibility.
- Understandability: The degree to which the component under test is documented or self-explaining.
- Heterogeneity: The degree to which the use of diverse technologies requires to use diverse test methods and tools in parallel.

<a name="teststatistics"/>
## Estatísticas de Teste

# TODO
 Number of tests (# tests unitários; # tests de sistema, # tests de desempenho, ...)
     % coverage (given by tools like EclEmma)
     Code coverage: is it any good? (see http://avandeursen.com/2013/11/19/test-coverage-not-for-managers/)

<a name="bugreportsolution"/>
## Bug Report Resolvido

# TODO
3) [Opcional] Take a bug report, create test cases to reproduce it, and fix it, eventually using automated software fault diagnosis techniques. (grade >18)

<a name="group_contribution"/>
## Contribuição do Grupo

 - [André Machado](https://github.com/andremachado94) (up201202865@fe.up.pt): x horas
 - [André Lago](https://github.com/andrelago13) (up201303313@fe.up.pt): x horas
 - [Gustavo Silva](https://github.com/gtugablue) (up201304143@fe.up.pt): x horas
 - [Marina Camilo](https://github.com/Aniiram) (up201307722@fe.up.pt): x horas
