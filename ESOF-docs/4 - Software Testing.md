# Testes de Software

<a name="index"/>
## Índice
1. [Introdução](#introduction)
2. [Grau de Testabilidade](#degreeoftestability)
3. [Estatísticas de Teste](#teststatistics)
4. [Bug Report Resolvido](#bugreportsolution)
  1. [Informação](#bugreportsolution_info)
  2. [Resolução](#bugreportsolution_solving)
  3. [*Pull Request*](#bugreportsolution_pr)
5. [Contribuição do Grupo](#group_contribution)

<a name="introduction"/>
## Introdução

Ao longo deste relatório vamos analisar o *Terasology* no que diz respeito a testes de software. Na [primeira secção](#degreeoftestability) será feita a análise do grau de testabilidade do projeto, ou seja, o quão fácil é de testar os diferentes componentes do jogo, e como poderiam ser melhorados esses métodos de teste. De seguida, serão apresentadas algumas [estatísticas de teste](#teststatistics) do projeto, no que diz respeito a número de testes, cobertura, entre outros aspetos. Por fim, será relatado o processo de [correção de um bug](#bugreportsolution) relatado no projeto.

<a name="degreeoftestability"/>
## Grau de Testabilidade

Tanto se analisarmos o *engine* como os módulos do **Terasology**, é claro que os testes apenas podem ser aplicados aos seus sub-componentes tendo em conta a grande complexidade e multifuncionalidade de qualquer um deles como um todo. Por exemplo, um módulo em específico, como o *rendering*, embora possua uma só grande funcionalidade (traduzir as informações do jogo para imagens visíveis), subdivide-se em diversas pequenas tarefas (representação das entidades como conjuntos de polígonos, cálculo da cor dos pixéis, etc.). Neste sentido, a análise do grau de testabilidade deve-se estender a pequenos sub-componentes do software para que se perceba exatamente o quão fácil e útil é testar o software.

<a name="controlability"/>
#### Controlabilidade

No que diz respeito à controlabilidade dos componentes sob teste (CUT - Component Under Test) é aparente que depende do componente em si. Isto é, quando se trata de um componente do *engine*, é provável que apresente controlabilidade reduzida uma vez que o engine interage com uma quantidade muito grande de módulos, logo, é mais difícil de prever para alguns casos quais são todas as situações possíveis. Por outro lado, um componente de um módulo deverá apresentar uma controlabilidade mais elevada pois a sua interação limita-se a outros componentes do módulo (ou, em alguns casos, outros módulos externos ao jogo) e ao *engine* em si. Deste modo, a controlabilidade mais elevada (por norma) dos componentes dos módulos torna mais fácil a execução de testes nestes em relação a alguns componentes do *engine*. Contudo, componentes mais "interiores" do *engine* poderão apresentar a mesma facilidade (quando considerada apenas a controlabilidade) uma vez que possuem interações mais limitadas e funcionalidades mais concentradas.

#### Observabilidade

O **Terasology** utiliza uma ferramenta de testes automáticos chamada [*Jenkins*](https://jenkins-ci.org/), através da qual é possível efetuar os testes unitários em JUnit presentes no projeto sempre qe há uma *build* do projeto ou uma *pull request*. Assim, através da [página do **Terasology do *Jenkins*](http://jenkins.terasology.org/job/Terasology/) e da respetiva [página de estatísticas](http://jenkins.terasology.org/view/Statistics/) é fácil de perceber os resultados dos testes unitários. A página de estatísticas permite ver os resultados, quantidade e cobertura dos testes realizados aos diferentes módulos do projeto. Para além disso, a página principal permite a visualização de forma simples (através dos gráficos ilustrados ao longo do lado direito da página) de algumas informações relativas aos testes efetuados, das quais se destacam:

- Checkstyle - Confere o estilo do código para verificar se as mesmas convenções são usadas em todos os sítios
- FindBugs - Procura fragmentos de código cuja estrutura possa induzir a bugs ou outros erros
- Open Tasks - Procura comentários com "*TODO*" no código para listar a quantidade de tarefas por fazer
- Code Coverage - Quantidade de linhas de código cobertas pelos testes

Assim, concluímos que a observabilidade dos resultados dos testes, quando aplicados nestes módulos "principais" do projeto, é excelente pois é de fácil interpretação e porque é fácil, através do *Jenkins*, perceber a origem das diversas falhas possíveis. No caso dos módulos desenvolvidos por contribuidores externos, os testes são algo facultativo, ou seja, da inteira responsabilidade do seu autor, pelo que a sua observabilidade depende também da técnica usada para os implementar.

#### Isolabilidade

A isolabilidade do *CUT* em questão está um pouco relacionada com a sua controlabilidade. A forma como um componente pode ser testado de forma isolada ou independente depende da forma como ele se relaciona com outros módulos, ou mesmo da forma como depende deles. Por exemplo, quando se pretende testar uma função de um módulo A que utiliza uma outra função do módulo B, o sucesso dos testes realizados à função de A depende não só do código dessa mesma função como da função do módulo B, ou seja, os testes podem falhar mesmo que o código da função de A esteja completamente correto. Assim, determinar um grau geral de isolabilidade para o código do projeto seria incorreto pois é impossível de prever em que casos a isolabilidade é ou não ideal, contudo, a isolabilidade dos componentes sob teste é provávelmente maior nos módulos em comparação com o *engine* em si pelos mesmos motivos apresentados no [parágrafo sobre controlabilidade](#controlability).

#### Separação de responsabilidades

A separação de responsabilidades entre módulos está bastante bem definida (como foi referido no [relatório anterior](https://github.com/andrelago13/Terasology/blob/master/ESOF-docs/3%20-%20Software%20Architecture.md). Cada um dos módulos principais possui uma tarefa (ou conjunto de tarefas) da qual é responsável, contudo, essa tarefa apresenta muitas vezes uma complexidade elevada (por exemplo, tal como já foi referido, a tarefa de *rendering* embora seja uma só apresenta uma elevada complexidade pela variedade e dificuldade dos sub-problemas que engloba). Assim, consideramos que a separação de responsabilidades é feita de uma forma correta no **Terasology** uma vez que tarefas grandes são designadas a módulos específicos que repartem as tarefas em sub-problemas resolvidos no seu interior.

#### Documentação e facilidade de leitura

A maior parte dos módulos do **Terasology**, principalmente o *engine* e os 80 módulos principais, encontram-se bem documentados, principalmente nas partes de código que não se classifica como auto-explicativo. Embora existam falhas na *wiki* do projeto, ou zonas que ainda não foram propriamente documentadas (tal como é referido em [algumas *issues*](https://github.com/MovingBlocks/Terasology/issues) do projeto, a documentação do código é geralmente vasta e completa, pois de outra forma seria muito difícil a compreensão do código por parte de contribuidores externos. A relativamente grande quantidade de contribuidores externos, aliada à diversidade de módulos criados por esses mesmos contribuidores, comprova em parte a qualidade e diversidade da documentação do código das partes essenciais e mais "públicas" do projeto.

# TODO
#### Heterogeneidade de tecnologias e métodos

The testability of software components (modules, classes) is determined by factors such as:
- Controllability: The degree to which it is possible to control the state of the component under test (CUT) as required for testing.
- Observability: The degree to which it is possible to observe (intermediate and final) test results.
- Isolateability: The degree to which the component under test (CUT) can be tested in isolation.
- Separation of concerns: The degree to which the component under test has a single, well defined responsibility.
- Understandability: The degree to which the component under test is documented or self-explaining.
- Heterogeneity: The degree to which the use of diverse technologies requires to use diverse test methods and tools in parallel.

<a name="teststatistics"/>
## Estatísticas de Teste

As estatísticas relativas aos testes automáticos realizados no Jenkins do *Terasology* podem ser consultadas na [respetiva página](http://jenkins.terasology.org/view/Statistics/), já referida. Deve-se notar que os testes a que nos referimos dizem respeito ao *engine* e a alguns outros módulos.

Número de testes unitários automáticos: 2047
Percentagem de cobertura geral do projeto: 25,45%
Percentagem de cobertura no módulo principaç (Terasology): 25,75%
Melhor percentagem de cobertura (NanoMarkovChains): 93,65%
Pior percentagem de cobertura maior que 0% (TerasologyStable): 19,72%

A maior parte dos testes são referentes ao *engine* e outros módulos mais essenciais do jogo, sendo que a maior parte dos módulos, mesmo alguns dos 80 principais, não possui testes implementados. Isto é um grande defeito do projeto pois dificulta a validação do código de novos módulos, e mesmo de alterações aos módulos já existentes, contudo, o principal responsável pela área mais técnica do projeto ([Cervator](https://github.com/Cervator)) admite [nesta thread do fórum do *Terasology*](http://forum.terasology.org/threads/development-methodology-and-hi-students-from-porto.1387/) que fazer mais e melhores testes é um dos objetivos atuais do projeto, mas que a sua execução não é de todo fácil por não haver ninguém dedicado a tempo inteiro ao projeto.

De qualquer modo, a cobertura geral dos testes nos módulos onde estes foram implementados é atualmente má, pelo que deverá ser feito um esforço para aumentar a quantidade e talvez qualidade de testes.

<a name="bugreportsolution"/>
## Bug Report Resolvido
Foi resolvido o *bug* [#1633](https://github.com/MovingBlocks/Terasology/issues/1633), listado nas *issues* do projeto Terasology e que faz parte da *milestone* "v1.0.0" (primeiro lançamento real do *engine*).

<a name="bugreportsolution_info"/>
### Informação
O *bug* podia ser reproduzido da seguinte forma:
1. Entrar num local com água, pelo menos 2 blocos de profundidade e adjacente a blocos não líquidos.
2. "Cavar" os dois blocos adjacentes à água.
3. Sair da água diretamente para o espaço adjacente criado, sem emergir.

O resultado esperado seria o jogador voltar a ter movimento normal (andar). No entanto, o que se verificava é que o jogador continuava a nadar apesar de já não estar em água. Isto permitia inclusivamente voar pelo mundo.

<a name="bugreportsolution_solving"/>
### Resolução
A metodologia de resolução do *bug* passou pela aplicação do ciclo de desenvolvimento do *Test-Driven Development*.
Primeiro, foram criados testes unitários para reproduzir a falha. Para isso, foi criada a classe [KinematicCharacterMoveTest](https://github.com/MovingBlocks/Terasology/blob/fedae81b0a2579f054a8e95362d3ca0e6722797f/engine-tests/src/test/java/org/terasology/logic/characters/KinematicCharacterMoverTest.java).

![Teste a falhar](/ESOF-docs/resources/test_failing.png "Teste a falhar")

Após efetuar alterações ao código para eliminar o *bug*, correu-se novamente os testes e verificou-se que estes passaram com sucesso.

<a name="bugreportsolution_pr"/>
### *Pull Request*

Após a resolução do bug foi efetuado um [*commit*](https://github.com/MovingBlocks/Terasology/commit/fedae81b0a2579f054a8e95362d3ca0e6722797f) e um [*Pull Request*](https://github.com/MovingBlocks/Terasology/pull/2011) para o repositório original do projeto. O código foi testado automaticamente pelo sistema *Jenkins* e passou sem falhas nos testes. As informações sobre a *build* podem ser consultadas aqui: http://jenkins.terasology.org/job/TerasologyPRs/310/.

O *Pull Request* foi aceite e foi feito *merge* com o *developer branch*: https://github.com/MovingBlocks/Terasology/pull/2011

<a name="group_contribution"/>
## Contribuição do Grupo

 - [André Machado](https://github.com/andremachado94) (up201202865@fe.up.pt): x horas
 - [André Lago](https://github.com/andrelago13) (up201303313@fe.up.pt): x horas
 - [Gustavo Silva](https://github.com/gtugablue) (up201304143@fe.up.pt): 8 horas
 - [Marina Camilo](https://github.com/Aniiram) (up201307722@fe.up.pt): x horas
