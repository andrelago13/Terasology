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

![Terasology package diagram](/ESOF-docs/resources/packagediagram.png)

No diagrama acima foram ocultadas as dependências entre todos os packages dos quais o package "*context*" é dependente por uma questão de simplicidade e porque não acrescentariam informação relevante para a análise da arquitetura do projeto. Para além disso, os módulos "moduleLoading", "moduleX", "moduleXassets" e "engineAPI" são módulos que não se encontram na realidade implementados mas que foram adicionados por nós para se perceber melhor a arquitetura planeada. A sua descrição é feita mais à frente.

O package "game" representa o início do jogo, ou seja, as classes utilizadas na inicialização do programa. Este package seria responsável por guardar e carregar as definições (*settings*) do jogador. Este package utiliza então o package "engine" para dar início ao jogo, sendo que é este o package responsável por todas as ações do jogo, desde os mecanismos em si (colisões, audio, input), até aos menus do jogo e inclusão dos módulos. Esta é simbolizada pelo package "moduleLoading" que é uma funcionalidade utilizada pelo *engine* para carregar os módulos selecionados pelo utilizador na *GUI*. O package "moduleX" representa um módulo genérico. Cada módulo possui os seus próprios *assets* para além dos *assets* do *engine*. Para além disso, cada módulo pode depender de outros módulos de uma forma hierárquica que permita o carregamento dos vários módulos simultaneamente. O *engine* disponibiliza uma API (representada simbolicamente por "engineAPI") que permite aos módulos a utilização de várias funcionalidades do *engine* sem colocar em perigo a integridade do sistema ou a privacidade dos ficheiros dos utilizadores.

O package "engine" utiliza o package "context" que gere a *state machine* implementada pelo *engine*, utilizando todos os restantes módulos para isso. É também importante referir que o package "world" possui uma camada inferior (o package "logic") que gere toda a lógica do jogo em si, confinando-a num só sítio.

De uma forma geral, a arquitetura utilizada é adequada na medida em que separa as diferentes funcionalidades implementadas pelo *engine*, fornecendo ao mesmo tempo uma API de imensa utilizade aos módulos que mantém a consistência do sistema. Para além disso, a colocação dos módulos numa camada "externa" facilita o desenvolvimento dos módulos de uma forma (possivelmente) independente e segura.

<a name="implementationview"/>
## Vista de Implementação

<a name="processview"/>
## Vista de Processo

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
