# DS_B — Projeto JavaFX

Aplicação JavaFX empacotada com um SDK JavaFX autocontido para Linux e Windows.
Compilada com **Java 21**, runtime **JavaFX SDK 17.0.18**.

## Estrutura

| Caminho | Descrição |
|---------|-----------|
| `javafxLinux_B/`   | Projeto Eclipse/VS Code + SDK JavaFX para Linux empacotado |
| `javafxWindows_B/` | Projeto Eclipse/VS Code + SDK JavaFX para Windows empacotado |
| `*/src/`           | Código-fonte Java (`br.gov.sp.etec`) |
| `*/javafx-sdk-17.0.18/` | SDK JavaFX empacotado (versionado via **Git LFS**) |

## Pré-requisitos

- JDK 21
- [Git LFS](https://git-lfs.com/) — **obrigatório** para clonar os binários do
  SDK. Sem ele, os arquivos `.jar`/`.so`/`.dll` chegam como ponteiros de texto
  e a aplicação não executa.

```bash
git lfs install
git clone git@github.com:ensismoebius/DS_B.git
```

Caso já tenha clonado sem o LFS:

```bash
git lfs install
git lfs pull
```

## Executar

Abra `javafxLinux_B` (ou `javafxWindows_B`) no Eclipse ou no VS Code e execute
`br.gov.sp.etec.Principal`. O classpath já referencia os jars do SDK empacotado
em `javafx-sdk-17.0.18/lib/`.

## Documentação

A documentação completa do projeto fica na **[wiki](.wiki/)**:

- **[Início](.wiki/Home.md)** — índice da wiki
- **[Git LFS](.wiki/Git-LFS.md)** — como os arquivos grandes são armazenados,
  padrões versionados, configuração e solução de problemas
- **[VS Code (IDE)](.wiki/VS-Code.md)** — uso dos projetos no VS Code: build,
  execução e debug
