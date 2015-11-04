# Arquitetura de Software

<a name="index"/>
## Índice
1. [Introdução](#introduction)
2. [Vista Lógica](#logicalview)
3. [Vista de Implementação](#implementationview)
4. [Vista de Processo](#processview)
5. [Vista de Distribuição](#deploymentview)
6. [Contribuição do Grupo](#group_contribution)

<a name="introduction"/>
## Introdução

No *Terasology* a arquitetura do software é muito importante pois tem de suportar o desenvolvimento paralelo de módulos diferentes a ritmos diferentes.
Para isso, os responsáveis pelo projeto publicaram vários documentos e *threads* no seu fórum que permitem aos *developers* externos ter uma noção de como as coisas têm de funcionar para garantir que o jogo funciona minimamente bem por muito maus ou incompletos que estejam os módulos. [Nesta *thread*](http://forum.terasology.org/threads/architecture-vision.690/) do fórum do *Terasology* são elicitados os objetivos da arquitetura implementada (embora esta não seja uma implementação muito forte/rígida). É de salientar que os módulos não devem causar conflitos entre eles, não podem causar falhas no jogo completo (por exemplo devido a exceções por "tratar") e deve haver uma clara separação da implementação dos módulos e do *engine* em si.

É detalhada mais profundamente [nesta página](https://github.com/MovingBlocks/Terasology/wiki/Entity-System-Architecture) a arquitetura do sistema de entidades do jogo que é importante para programar módulos ou *resources* do jogo. Para além disso, [nesta página](https://github.com/MovingBlocks/Terasology/wiki/Codebase-Structure) é explicada em detalhe a importação do código dos diferentes módulos através da tecnologia [*Gradle*](http://gradle.org/).

A nossa abordagem à arquitetura do *Terasology* será baseada no modelo de 4+1 vistas de arquitetura de software. As 4 vistas representam a vista lógica, vista de implementação, vista de processo e vista de distribuição, sendo que a vista adicional (+1) se trata da vista de casos de utilização, cujo diagrama foi já apresentado no [último relatório](https://github.com/andrelago13/Terasology/blob/master/ESOF-docs/2%20-%20Requirements%20Management.md) apresentado.

<a name="logicalview"/>
## Vista Lógica

É importante referir que para esta análise nos vamos focar apenas no [*engine*](https://github.com/andrelago13/Terasology/tree/master/engine/src/main/java/org/terasology) do jogo porque esse era o propósito inicial do projeto e porque a diversidade e quantidade de módulos existente reduz a importância da sua arquitetura, uma vez que os módulos apenas têm de ser feitos de forma a serem integrados no *engine*. Contudo, vamos abordar a arquitetura esperada dos módulos e a sua integração no *engine*.

De acordo com o que é referido por *Immortius* (principal responsável pela arquitetura de software do projeto) [nesta thread](http://forum.terasology.org/threads/architecture-vision.690/) do fórum do *Terasology*, a arquitetura do projeto foi concebida da seguinte forma:
<a name="package_diagram"/>
![Terasology package diagram](/ESOF-docs/resources/packagediagram.png)

No diagrama acima foram ocultadas as dependências entre todos os packages dos quais o package "*context*" é dependente por uma questão de simplicidade e porque não acrescentariam informação relevante para a análise da arquitetura do projeto. Para além disso, os módulos "moduleLoading", "moduleX", "moduleXassets" e "engineAPI" são módulos que não se encontram na realidade implementados mas que foram adicionados por nós para se perceber melhor a arquitetura planeada. A sua descrição é feita mais à frente.

O package "game" representa o início do jogo, ou seja, as classes utilizadas na inicialização do programa. Este package seria responsável por guardar e carregar as definições (*settings*) do jogador. Este package utiliza então o package "engine" para dar início ao jogo, sendo que é este o package responsável por todas as ações do jogo, desde os mecanismos em si (colisões, audio, input), até aos menus do jogo e inclusão dos módulos. Esta é simbolizada pelo package "moduleLoading" que é uma funcionalidade utilizada pelo *engine* para carregar os módulos selecionados pelo utilizador na *GUI*. O package "moduleX" representa um módulo genérico. Cada módulo possui os seus próprios *assets* para além dos *assets* do *engine*. Para além disso, cada módulo pode depender de outros módulos de uma forma hierárquica que permita o carregamento dos vários módulos simultaneamente. O *engine* disponibiliza uma API (representada simbolicamente por "engineAPI") que permite aos módulos a utilização de várias funcionalidades do *engine* sem colocar em perigo a integridade do sistema ou a privacidade dos ficheiros dos utilizadores.

O package "engine" utiliza o package "context" que gere a *state machine* implementada pelo *engine*, utilizando todos os restantes módulos para isso. É também importante referir que o package "world" possui uma camada inferior (o package "logic") que gere toda a lógica do jogo em si, confinando-a num só sítio.

De uma forma geral, a arquitetura utilizada é adequada na medida em que separa as diferentes funcionalidades implementadas pelo *engine*, fornecendo ao mesmo tempo uma API de imensa utilizade aos módulos que mantém a consistência do sistema. Para além disso, a colocação dos módulos numa camada "externa" facilita o desenvolvimento dos módulos de uma forma (possivelmente) independente e segura.

<a name="implementationview"/>
## Vista de Implementação

De seguida encontra-se a análise da arquitetura do projeto do ponto de vista da implementação, detalhando-se os principais componentes e interação entre eles no âmbito da disponibilização ou utilização de interfaces por parte destes.

![Terasology package diagram](/ESOF-docs/resources/componentdiagram.png)

Através do diagrama de componentes apresentado acima é percetível que a única interação que o jogo em si (a aplicação Java) faz é com a interface disponibilizada pelo *engine*, sendo este a "tratar" de tudo o resto. Em primeiro lugar, o engine dá uso aos métodos disponibilizados pelo carregador de módulos para incorporar no novo jogo os módulos definidos pelo utilizador. Para além disso, como também está apresentado, cada um dos módulos incluídos tem acesso à API disponibilizada pelo *engine* que lhes permite, como foi referido acima, aumentar as suas funcionalidades sem causar perigo à consistência e segurança do jogo. Para além disso, o *engine* utiliza a interface do "Contexto" para dar origem ao jogo em si, uma vez que é no "Contexto" que se encontram as dependências necessárias para que o jogo seja possível (física, *rendering*, entre outras, como é apresentado no [diagrama anterior](#package_diagram).

Por fim, através da interface do "Contexto" é possível dar uso à interface disponibilizada pelo componente "Network", que por sua vez permite disponibilizar ou associar um jogo através da Internet ou rede local.

Uma vez mais, a abordagem é a correta e é a que se encontra implementada no projeto. A arquitetura elaborada traz inúmeras vantagens no que diz respeito à modularização dos componentes do jogo bem como à separação de partes diferentes do jogo que podem ser trabalhadas e melhoradas separadamente com bastante facilidade.

<a name="processview"/>
## Vista de Processo

Através do diagrama de atividades do *Terasology* é fácil perceber a sequência de processos que uma sessão de jogo pode conter:

![Terasology activity diagram](/ESOF-docs/resources/activitydiagram.png)

Como se encontra apresentado no diagrama, o início do jogo passa por inicializar o *engine* do jogo que, por sua vez, cria o contexto (*context*). O contexto é responsável por interagir com todos os restantes componentes do jogo, desde GUI a *rendering* ou carregamento de módulos. Nesta fase inicial, como o passo seguinte é inicializar a GUI que permite o uso do Menu, apenas essas interações são necessárias. Depois de inicializada a GUI, há a seleção do modo de jogo (*local singleplayer*, *online multiplayer*, ...) e dos módulos a carregar. Esta informação é passada ao "Context" que trata de interagir com o carregador de módulos para carregar os módulos indicados, interagindo também com outros componentes que são necessários, por exemplo, para o modo de jogo (por exemplo, se o jogador quiser jogar através da Internet será necessária alguma interação com o módulo de *Network*). Começa então o jogo em si, onde a interação por parte do "Context" é máxima pois todos (ou quase todos) os restantes módulos (*rendering*, física, ...) são necessários para jogar o jogo, independentemente do modo. A partir do jogo é possível regressar ao menu inicial ou sair diretamente do jogo, chegando ao fim do processo. Também é possível terminar o jogo a partir do menu inicial.

<a name="deploymentview"/>
## Vista de Distribuição

O diagrama de distribuição em UML modela a distribuição física de artefatos em nós.
![Terasology deployment diagram](/ESOF-docs/resources/deploymentdiagram.png)

Através do diagrama é possível perceber como se pode um dispositivo ligar a outro(s) para jogar o jogo. Começando pelo dispositivo PC que executa o *Terasology*, ele pode ligar-se apenas a um outro dispositivo - um servidor local ou um servidor dedicado. Ambos executam também o *Terasology* para permitir a ligação de novos dispositivos, contudo, eles podem estar ligados a mais do que um dispositivo. A diferença é que o servidor local apenas pode ser acedido por dispositivos na mesma rede, enquanto um servidor dedicado pode ser acedido em redes diferentes ligadas pela Internet. Para além disso, o servidor local pode jogar o jogo ao mesmo tempo que "difunde" a informação necessária para que outros se juntem à sessão, enquanto o servidor dedicado apenas disponibiliza esta mesma informação, sem jogar o jogo enquanto o faz. Por fim, cada um dos tipos de servidores pode ligar-se a um ambiente de execução de Java presente noutros computadores, uma vez que isto é necessário para aplicações em Java. Estes ambientes de execução também estão limitados a uma ligação apenas, seja a um servidor dedicado ou a um servidor local.

<a name="group_contribution"/>
## Contribuição do Grupo

 - [André Machado](https://github.com/andremachado94) (up201202865@fe.up.pt): 2 horas
 - [André Lago](https://github.com/andrelago13) (up201303313@fe.up.pt): 2 horas
 - [Gustavo Silva](https://github.com/gtugablue) (up201304143@fe.up.pt): 2 horas
 - [Marina Camilo](https://github.com/Aniiram) (up201307722@fe.up.pt): 2 horas
