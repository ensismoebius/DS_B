# Git LFS — DS_B

Este repositório usa **Git Large File Storage (LFS)** para versionar os binários do SDK do JavaFX. Esta página documenta tudo que é necessário para clonar, usar e contribuir sem quebrar o histórico.

## Por que LFS?

Os SDKs do JavaFX (`javafx-sdk-17.0.18`) ficam versionados dentro do repositório para garantir builds reprodutíveis. Eles contêm binários grandes:

- `javafxLinux_B/.../lib/libjfxwebkit.so` — **~114 MB**
- `javafxWindows_B/.../bin/jfxwebkit.dll` — **~90 MB**

O GitHub **rejeita** arquivos acima de **100 MB** e **avisa** acima de 50 MB em blobs Git normais. O Git LFS substitui o conteúdo binário por **ponteiros de texto** no histórico e guarda o conteúdo real em um armazenamento separado, contornando esse limite e mantendo o histórico leve.

## O que é rastreado

Regras em [`.gitattributes`](../.gitattributes):

```gitattributes
*.dll  filter=lfs diff=lfs merge=lfs -text
*.jar  filter=lfs diff=lfs merge=lfs -text
*.so   filter=lfs diff=lfs merge=lfs -text
javafxWindows_B/javafx-sdk-17.0.18/**  filter=lfs diff=lfs merge=lfs -text
javafxLinux_B/javafx-sdk-17.0.18/**    filter=lfs diff=lfs merge=lfs -text
```

Resumo:

- **Por extensão**: todos os `*.dll`, `*.so`, `*.jar` do repositório.
- **Por diretório**: **tudo** dentro das árvores `javafx-sdk-17.0.18/` das duas variantes (glob `/**`), inclusive arquivos pequenos de licença — para manter o SDK inteiro consistente no LFS.

No total, **145 arquivos** estão sob LFS (≈225 MB de objetos).

> **Nota sobre `*.jar` e `.gitignore`**: o [`.gitignore`](../.gitignore) ignora `*.jar`. Os `.jar` do SDK estão versionados porque foram adicionados pelo glob de diretório do LFS (que tem precedência ao já estarem rastreados). Não há conflito para os arquivos já commitados.

> **Notas sobre `.gitattributes`**: existem linhas redundantes/legadas (entradas específicas de arquivo e uma referência antiga a `javafxWindows_A/`, diretório que não existe nesta cópia). São inofensivas — os globs de diretório já cobrem tudo —, mas podem ser limpas em um commit futuro.

## Pré-requisito: instalar o Git LFS

Antes de clonar ou commitar:

```bash
# Arch Linux
sudo pacman -S git-lfs
# Debian/Ubuntu
sudo apt install git-lfs

git lfs install   # configura os filtros LFS no Git (uma vez por máquina)
```

## Clonar o repositório

Com o Git LFS instalado, o clone normal já baixa os binários:

```bash
git clone git@github.com:ensismoebius/DS_B.git
```

Se clonou **sem** o LFS instalado (os arquivos vêm como ponteiros de texto), baixe o conteúdo real depois:

```bash
git lfs install
git lfs pull
```

Verificar o que está sob LFS:

```bash
git lfs ls-files
```

## Contribuir / adicionar binários

Os filtros aplicam o LFS automaticamente para arquivos que casam com `.gitattributes`. Fluxo normal:

```bash
git add <arquivos>
git commit -m "..."
git push
```

Para rastrear um **novo** padrão ou diretório:

```bash
git lfs track "caminho/para/dir/**"   # atualiza .gitattributes
git add .gitattributes
```

## Histórico: como os SDKs foram migrados para o LFS

Os binários grandes foram, originalmente, commitados como **blobs Git normais** (antes do LFS), o que fazia o `push` ser rejeitado pelo limite de 100 MB do GitHub. A correção reescreveu o histórico convertendo esses blobs em ponteiros LFS:

```bash
git lfs track "javafxWindows_B/javafx-sdk-17.0.18/**"
git lfs track "javafxLinux_B/javafx-sdk-17.0.18/**"
git add --renormalize .                    # converte arquivos já rastreados
git lfs migrate import --everything --above=50MB   # reescreve histórico p/ blobs >50MB
git push --force origin main               # histórico reescrito
```

> **Atenção**: `git lfs migrate import` e `push --force` **reescrevem o histórico**. Em repositório compartilhado, combine com a equipe antes — quem tem clones antigos precisará refazer o clone ou um `git reset --hard origin/main`.

## Solução de problemas

- **Arquivos aparecem como ponteiro de texto** (poucas linhas com `version https://git-lfs...`): rode `git lfs install && git lfs pull`.
- **`push` rejeitado por tamanho**: confirme que o arquivo casa com algum padrão do `.gitattributes` (`git check-attr filter <arquivo>` deve mostrar `filter: lfs`). Se entrou no histórico como blob normal, use `git lfs migrate import`.
- **Conferir um arquivo**: `git check-attr filter diff merge -- <arquivo>`.
