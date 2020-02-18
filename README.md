# JEF - Java Esmalteria Fiscal
<p align="center">
<img src="https://github.com/leonardopn/JEF/blob/master/src/model/images/icon.png?raw=true" alt="some text" width=400 height=400”></img>
</p>

----

## Descrição
> Software escrito em Java para o gerenciamento de caixa, horários, clientes e funcionários de uma esmalteria local.

---

## Status

![](https://img.shields.io/badge/version-v2.5.5-green)
[![GitHub issues](https://img.shields.io/github/issues/leonardopn/JEF)](https://github.com/leonardopn/JEF/issues)
[![GitHub forks](https://img.shields.io/github/forks/leonardopn/JEF)](https://github.com/leonardopn/JEF/network)
[![(Version)](https://img.shields.io/badge/license-GNU%20LGPL%20version%202.1-green.svg?style=flat-square)](https://github.com/leonardopn/JEF/blob/master/LICENSE.md)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a4a222961e704e15af7a9f63f61e373f)](https://www.codacy.com/manual/leonardopn/JEF?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=leonardopn/JEF&amp;utm_campaign=Badge_Grade)

---

## Dependências

 - [mariadb-connector-j](https://github.com/mariadb-corporation/mariadb-connector-j) >= v4.2
 - [controlsfx](https://github.com/controlsfx/controlsfx) = v8.40.16
 - [JFoenix](https://github.com/jfoenixadmin/JFoenix) = v8.0.8
 - [JavaFX SDK](https://gluonhq.com/products/javafx/) = v8
 - [Java SE](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) = v8u241

---
## Instalação

>O software foi desenvolvido com o propósito de uso em comércio próprio. O funcionamento está todo baseado para esse comércio, se deseja utilizar todas as funções sem problemas, é preciso criar um fork que se adapte à estrutura de banco de dados de sua preferência.

- 1º

    * Faça um clone do repositório ou baixe o código fonte  [AQUI](https://github.com/leonardopn/JEF/archive/master.zip).

- 2º

    * Instale todas as depêndencias citadas e importe em seu projeto.

- 3º 

    * <b>Crie o arquivo de conexão com o banco de dados ( </b><u>db.properties</u><b> ) dentro da seguinte localização:</b>

    *Windows: C:\Users\USUARIO\Documents\JEF_DATA*

    *Linux: /home/USUARIO/Documentos/JEF_DATA*

    <b>Exemplo de arquivo:</b>

    ```s
    user=USUARIO_DO_BANCO
    password=SENHA_DE_ACESSO
    dburl=jdbc:mariadb://ENDERECO_DO_BANCO:3306/NOME_DO_BANCO
    useSSL=false
    ```

    * <b>Crie o arquivo vazico de LOGS (</b><u>logQuery.txt</u><b>) dentro da seguinte localização:</b>

    *Windows: C:\Users\USUARIO\Documents\JEF_DATA\Logs*

    *Linux: /home/USUARIO/Documentos/JEF_DATA/logs*
---

## Funcionamento

>Na atual versão do programa (v2.5.5) o design apresentava o seguinte visual:

[![deepin-screen-recorder_Select-area_20200217212959.gif](https://s5.gifyu.com/images/deepin-screen-recorder_Select-area_20200217212959.gif)](https://gifyu.com/image/7WcL)


----
## Histórico de Versão
* <b>v2.5.5</b>
    * <b>FIX:</b> </br>Resolvido bug no caixa onde não atualizava o valor de dinheiro em caixa quando fazia uma operação de receita, despesa, pacote ou pagamento sem atualizar a tela de caixa;

    * <b>MUDANÇAS:</b> 
    </br>Atualização geral no visual do programa;
    </br>Implementação de um LOG de operações e erros durante a execução do programa;</br>
* <b>v2.5</b>
    * <b>FIX:</b> </br>bug no caixa;

    * <b>MUDANÇAS:</b> </br>
    Implementação de busca por filtro - OPERAÇÕES;</br>
    Implementação de inserção e exclusão - OPERAÇÕES;</br>
    Implementação de novos cálculos;</br>
    Modificações visuais para clientes;</br>
    Implementação de busca por filtro - CLIENTES;</br>
    Modificações visuais para login - LOGIN;</br>
    Modificações visuais a ViewSobre - SOBRE;</br>
    Implementação de SPLASH ART de carregamento;</br>
    
* <b>v2.0</b>
    * <b>FIX:</b> </br>bug no caixa;

    * <b>MUDANÇAS:</b> </br>
    Implementação do padrão MVC;</br>
    Implementação do padrão MultiThreading;</br>
    Detalhes visuais em diversar views;</br>
    Diversas outras implementações;</br>
    
* <b>v1.5</b>
    * <b>FIX:</b> </br>bug na atualização de cliente;

    * <b>MUDANÇAS:</b> </br>
    Implementações visuais e novas funções para clientes e funcionários;
* <b>v1.0</b>
    * Programa funcionando com a primeira versão estável;

---
## Meta

<center><b>-=Leonardo Petta do Nascimento=-</b></center></br> 

Facebook: [@leonardo.petta.nascimento](https://www.facebook.com/leonardo.petta.nascimento)</br> 
Email: leonardocps9@protonmail.com
</br> 
Linkedin: [Leonardo Petta Do Nascimento](https://www.linkedin.com/in/leonardo-petta-do-nascimento-75674015b/)

Distribuído sob a licença ```GNU LGPL version v2.1``` . Consulte [```LICENCE```](https://github.com/leonardopn/JEF/blob/master/LICENSE.md) para obter mais informações.

---

## Agradecimentos

1. [Maria DB Foundation;](https://mariadb.org/)
2. [FX Features;](http://fxexperience.com/)
3. [JFoenix;](http://www.jfoenix.com/)
4. [Java FX;](https://openjfx.io/)
---