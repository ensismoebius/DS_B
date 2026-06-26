# DS_B — Projeto JavaFX

Projeto de estudo em **JavaFX 17.0.18**, com dois ambientes prontos: `javafxWindows_B` (Windows) e `javafxLinux_B` (Linux). Pacote `br.gov.sp.etec`, classe principal `Principal`.

## Estrutura

```
DS_B/
├── javafxWindows_B/          # Variante Windows
│   ├── src/br/gov/sp/etec/   # Código-fonte Java
│   ├── javafx-sdk-17.0.18/   # SDK JavaFX (Windows) — rastreado via Git LFS
│   └── .vscode/              # Configuração do VSCode como IDE
├── javafxLinux_B/            # Variante Linux
│   ├── src/br/gov/sp/etec/   # Código-fonte Java
│   ├── javafx-sdk-17.0.18/   # SDK JavaFX (Linux) — rastreado via Git LFS
│   └── .vscode/              # Configuração do VSCode como IDE
├── .gitattributes           # Regras do Git LFS
└── README.md
```

## Pré-requisitos

- **JDK 17+**
- **Git LFS** instalado e inicializado (`git lfs install`) — obrigatório para baixar os SDKs do JavaFX. Veja a [documentação detalhada do LFS](.wiki/Git-LFS.md).
- **VSCode** com a extensão *Extension Pack for Java*.

## Como abrir no VSCode (IDE)

Os dois ambientes já vêm configurados como projeto VSCode. Abra a pasta da variante desejada (`javafxWindows_B` ou `javafxLinux_B`) diretamente no VSCode. A pasta `.vscode/` de cada uma fornece:

- **`settings.json`** — define `src` como fonte, `bin` como saída e as libs do JavaFX (`javafx-sdk-17.0.18/lib/**/*.jar`).
- **`tasks.json`** — tarefas `javac: compile` (compila) e `java: run` (executa).
- **`launch.json`** — configuração de depuração *Debug Principal* com os `vmArgs` do módulo JavaFX.

Execute via **Run and Debug** (`F5`) ou pela paleta de tarefas (`Ctrl+Shift+B`).

## Execução manual (linha de comando)

```bash
# A partir de javafxLinux_B ou javafxWindows_B
mkdir -p bin
find src -name "*.java" | xargs javac --module-path javafx-sdk-17.0.18/lib \
  --add-modules javafx.controls,javafx.fxml -d bin
java --module-path javafx-sdk-17.0.18/lib \
  --add-modules javafx.controls,javafx.fxml -cp bin br.gov.sp.etec.Principal
```

## Documentação (Wiki)

Detalhes completos do projeto ficam no diretório [`.wiki/`](.wiki/):

- **[Início](.wiki/Home.md)** — visão geral e índice.
- **[Git LFS](.wiki/Git-LFS.md)** — tudo sobre o uso do Git LFS neste repositório (o que é rastreado, por quê, e como clonar/contribuir).
