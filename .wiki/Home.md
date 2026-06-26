# Wiki — DS_B

Documentação do projeto **DS_B** (JavaFX 17.0.18).

## Índice

- **[VS Code (IDE)](VS-Code.md)** — uso do VS Code como IDE nas duas variantes: arquivos `.vscode/`, build, execução e debug.
- **[Git LFS](Git-LFS.md)** — uso do Git Large File Storage neste repositório: o que é rastreado, motivo, configuração, e fluxo de clone/contribuição.

## Visão geral

O repositório contém dois ambientes equivalentes do mesmo projeto JavaFX, um por sistema operacional:

| Variante           | SO      | SDK incluído                          | Tamanho aprox. |
|--------------------|---------|---------------------------------------|----------------|
| `javafxWindows_B`  | Windows | `javafx-sdk-17.0.18` (binários `.dll`) | ~112 MB        |
| `javafxLinux_B`    | Linux   | `javafx-sdk-17.0.18` (binários `.so`)  | ~134 MB        |

Cada variante traz o **SDK do JavaFX versionado junto** para garantir build reprodutível sem download externo. Por serem binários grandes, esses SDKs são armazenados via **Git LFS** — ver [Git LFS](Git-LFS.md).

Ambas as variantes já estão configuradas para usar o **VSCode como IDE** (pasta `.vscode/` com `settings.json`, `tasks.json` e `launch.json`).
