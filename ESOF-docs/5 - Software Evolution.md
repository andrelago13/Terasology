# Desenvolvimento de uma *feature*

<a name="index"/>
## Índice
1. [Introdução](#introduction)
2. [Identificação da *feature*](#feature_identification)
3. [Componentes que implementam a *feature* a desenvolver](#feature_components)
4. [Evolução da *feature*](#feature_evolution)
5. [Submissão do *patch*](#feature_submission)
5. [Conclusão](#conclusion)
5. [Contribuição do Grupo](#group_contribution)

<a name="introduction"/>
## Introdução

Uma vez que o *Terasology* é um projeto ainda em crescimento e evolução, há ainda uma lista grande de tarefas a desenvolver e de problemas a corrigir. Contudo, o projeto está aberto ao desenvolvimento de novas funcionalidades desde que o seu interesse para o projeto seja relevante.
Ao longo deste relatório será descrito o processo de identificação, desenvolvimento e submissão de uma funcionalidade nova do projeto *Terasology*.

<a name="feature_identification"/>
## Identificação da *feature*

Existem duas formas de facilitar a descoberta de possíveis funcionalidades a adicionar ao projeto.
Uma delas seria a de procurar *threads* já existentes no [fórum do *Terasology*](http://forum.terasology.org/forum/), inclusivé na secção dedicada a [sugestões](http://forum.terasology.org/forum/suggestions.21/). Esta alternativa permite identificar mais fácilmente funcionalidades desejadas pela comunidade envolvente do projeto e ter uma melhor noção das suas vantagens e desvantagens para o projeto. Muitas destas funcionalidades encontram-se ainda longe de estarem implementadas, pelo que a sua execução pode exigir bastante tempo.
A outra possibilidade consiste em explorar as [*issues*](https://github.com/MovingBlocks/Terasology/issues) listadas no repositório do projeto. Note-se que, embora sejão chamadas de *issues*, muitas vezes estes tópicos não são propriamente *bugs* a corrigir mas sim sugestões relacionadas com funcionalidades já existentes. Assim, esta pode ser uma forma de encontrar funcionalidades mais próximas da realidade já existente no projeto, enquadrando-se mais com as suas necessidades atuais.

A *feature* escolhida foi encontrada através desta segunda via, na [*issue* #1890](https://github.com/MovingBlocks/Terasology/issues/1890). Como já foi referido em relatórios anteriores, o *Terasology* permite a inclusão em tempo de execução de diversos módulos de jogo. Assim, existe um ecrã que permite a seleção dos módulos a incluir no jogo, sendo que neste ecrã está presente um painel que permite visualizar a descrição dos módulos de forma individual. Contudo, acontece que alguns módulos podem depender de outros módulos, sendo que essa informação não é mostrada de forma alguma ao utilizador. Assim, esta *issue* sugeria a implementação de uma forma de visualizar estas dependências, uma vez que anteriormente já tinha sido desenvolvido um sistema que descarrega automáticamente os módulos dos quais um determinado módulo depende.

A proposta efetuada aos responsáveis pelos projetos consistia em adicionar à descrição de cada módulo a lista de dependências deste no caso de existirem dependências, ou uma mensagem indicativa do contrário. O aspeto desejado da solução seria o seguinte:

<a name="issue_goal"/>
![Issue goal](/ESOF-docs/resources/issue1890.png)

<a name="feature_components"/>
## Componentes que implementam a *feature* a desenvolver

Após análise do código e documentação do projeto e alguma discussão com alguns dos responsáveis pelo projeto, foram identificados os ficheiros a modificar para permitir a implementação desta funcionalidade:

- [Ficheiro SelectModulesScreen.java](https://github.com/MovingBlocks/Terasology/blob/develop/engine/src/main/java/org/terasology/rendering/nui/layers/mainMenu/SelectModulesScreen.java)

Neste ficheiro encontra-se a implementação da classe SelectModulesScreen que é responsável por construir o ecrã de seleção de módulos no jogo. Assim, foi possível identificar que era neste ficheiro que era construída a *string* correspondente à descrição dos módulos, pelo que essa deveria ser modificada de forma a incluir a lista de dependências. Para além disso, uma vez que o jogo permite a seleção de diferentes linguagens, deveriam ser utilizadas técnicas que permitissem que a mensagem se adequasse à linguagem escolhida pelo utilizador.

- [Ficheiros de linguagem](https://github.com/MovingBlocks/Terasology/tree/develop/engine/src/main/resources/assets/i18n)

Nos ficheiros presentes nesta pasta encontram-se presentes as traduções de alguns textos (essencialmente *labels*) presentes nos menus do jogo. Assim, seria necessário adicionar os textos apresentados na lista de dependências nestes ficheiros para permitir a apresentação de mensagens adequadas à linguagem do jogo.

<a name="feature_evolution"/>
## Evolução da *feature*

O primeiro passo no desenvolvimento da funcionalidade proposta consistiu em adicionar aos ficheiros de linguagem as strings correspondentes a "Module dependencies:" e "This module has no dependencies.", bem como as respetivas traduções. ([link para alterações efetuadas](https://github.com/gtugablue/Terasology/commit/8d60a1b4caf046e3e216f89e13e35276e77c66f3))

De seguida, foi modificada a criação do texto de descrição de módulos de forma a incluir a etiqueta pretendida bem como a lista de dependências do módulo, no caso de esta existir. ([link para alterações efetuadas](https://github.com/gtugablue/Terasology/commit/be2ecd6bc2142e8d4ce2fd86ecfdadad47f56548)) 

Por recomendação de um dos responsáveis do projeto ([Martin Steiger](https://github.com/msteiger)) foi utilizado o plugin CheckStyle para garantir que o código adicionado mantinha o estilo usado ao longo de todo o projeto. As instruções para utilização deste plugin encontram-se na [documentação do projeto](https://github.com/MovingBlocks/Terasology/wiki/Checkstyle). ([link para alterações efetuadas](https://github.com/gtugablue/Terasology/commit/e1eaf76bd75c57b7927479d53d4adccd900d4b50))

Por fim, foi utilizada a interface [TranslationSystem](https://github.com/MovingBlocks/Terasology/blob/develop/engine/src/main/java/org/terasology/i18n/TranslationSystem.java) para permitir a escolha dos textos adequados à linguagem definida pelo utilizador do jogo. ([link para alterações efetuadas](https://github.com/gtugablue/Terasology/commit/4454d63bfb325f39b29eaec9f19736d7f6d73224))

<a name="feature_submission"/>
## Submissão do *patch*

O *patch* correspondente à implementação desta funcionalidade foi enviado através da [*pull request* #2049](https://github.com/MovingBlocks/Terasology/pull/2049#commits-pushed-f9aec00). Uma vez aprovada esta *pull request*, a funcionalidade implementada passa a fazer parte do código "oficial" do projeto e a [*issue*](https://github.com/MovingBlocks/Terasology/issues/1890) através da qual a funcionalidade tinha sido sugerida será fechada ou sofrerá uma alteração às respetivas *labels* para indicar que foi parcialmente concluída.

<a name="conclusion"/>
## Conclusão

Embora houvessem outras formas mais sofisticadas ou com melhor apresentação de implementar esta funcionalidade (por exemplo, um painel com *tabs* que permitisse alternar entre a desrição e a lista de dependências do projeto), o código por nós realizado permitiu a implementação de uma funcionalidade nova ao projeto que apresenta alguma utilidade para os utilizadores e *developers* que utilizem o jogo. Assim, foi conseguido um contributo positivo para o projeto sem que este se realizasse em torno de um *bug* ou erro por corrigir.
Para além disso, a implementação de uma nova funcionalidade num projeto desta dimensão permitiu uma melhor perceção de como é possível contribuir para projetos grandes sem ser conhecido todo o seu código ou estrutura.

<a name="conclusion"/>
## Contribuição do Grupo

 - [André Machado](https://github.com/andremachado94) (up201202865@fe.up.pt): 21%
 - [André Lago](https://github.com/andrelago13) (up201303313@fe.up.pt): 29%
 - [Gustavo Silva](https://github.com/gtugablue) (up201304143@fe.up.pt): 29%
 - [Marina Camilo](https://github.com/Aniiram) (up201307722@fe.up.pt): 21%
