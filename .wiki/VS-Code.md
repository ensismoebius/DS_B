# VS Code (IDE)

Ambos os projetos — `javafxLinux_B` e `javafxWindows_B` — estão configurados
para uso direto no **VS Code** como IDE, além do Eclipse. Cada um traz sua
própria pasta `.vscode/` com build, execução e debug prontos.

## Pré-requisitos

- [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
  (inclui Language Support for Java™ e o Debugger)
- JDK 21 instalado e visível ao VS Code
- Binários do JavaFX SDK baixados via **[Git LFS](Git-LFS.md)** (sem eles os
  jars chegam como ponteiros de texto e nada compila)

Abra **a pasta do projeto** (`javafxLinux_B` ou `javafxWindows_B`) diretamente
no VS Code — não a raiz do repositório — para que os caminhos relativos em
`.vscode/` funcionem.

## Arquivos `.vscode/`

Cada projeto contém três arquivos:

### `settings.json`

Diz ao Java Language Server como montar o classpath do projeto:

```json
{
  "java.project.sourcePaths": ["src"],
  "java.project.outputPath": "bin",
  "java.project.referencedLibraries": [
    "javafx-sdk-17.0.18/lib/**/*.jar"
  ]
}
```

Isso habilita IntelliSense, navegação e detecção de erros referenciando os jars
do SDK JavaFX empacotado.

### `launch.json`

Configuração de execução/debug usada pelo botão **Run and Debug**. Idêntica nos
dois projetos exceto pelo `name`/`projectName`. É **multiplataforma** (mesmos
`vmArgs` em Linux e Windows):

```json
{
  "type": "java",
  "request": "launch",
  "mainClass": "br.gov.sp.etec.Principal",
  "vmArgs": "--module-path ${workspaceFolder}/javafx-sdk-17.0.18/lib --add-modules javafx.controls,javafx.fxml",
  "preLaunchTask": "javac: compile"
}
```

`--module-path` + `--add-modules` são obrigatórios: o JavaFX 17 é modular e não
está no JDK 21.

### `tasks.json`

Duas tarefas: `javac: compile` (build) e `java: run` (executar sem debug).

- **`javafxLinux_B`** usa comandos Unix (`mkdir -p`, `find`, `xargs`).
- **`javafxWindows_B`** traz um override `windows` em PowerShell para que o build
  funcione no shell nativo do Windows, mantendo a variante Unix como padrão
  (Git Bash / WSL):

```json
"command": "mkdir -p bin && find src -name \"*.java\" | xargs javac --module-path javafx-sdk-17.0.18/lib --add-modules javafx.controls,javafx.fxml -d bin",
"windows": {
  "command": "if (!(Test-Path bin)) { New-Item -ItemType Directory bin | Out-Null }; javac --module-path javafx-sdk-17.0.18/lib --add-modules javafx.controls,javafx.fxml -d bin (Get-ChildItem -Recurse -Filter *.java src).FullName"
}
```

A tarefa `java: run` é igual nos dois sistemas — o CLI `java` aceita os mesmos
argumentos.

## Fluxo de uso

| Ação | Como |
|------|------|
| Compilar | **Terminal → Run Build Task** (`Ctrl+Shift+B`) → `javac: compile` |
| Executar (sem debug) | **Terminal → Run Task** → `java: run` |
| Debug | Aba **Run and Debug** → `Debug Principal (...)` (compila antes via `preLaunchTask`) |

## Observações de plataforma

- `javafxLinux_B` empacota libs nativas Linux (`.so`); `javafxWindows_B`
  empacota libs nativas Windows (`.dll`).
- Cada projeto roda no SO correspondente. O projeto Windows **não** executa no
  Linux (e vice-versa) — as libs nativas não são compatíveis entre plataformas.
- O classpath do JavaFX vem do SDK empacotado, então não é preciso instalar
  JavaFX separadamente.

---

Voltar para o [Início](Home.md) · [README](../README.md).
