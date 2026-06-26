# Git LFS

Este repositório usa o **[Git Large File Storage (LFS)](https://git-lfs.com/)**
para armazenar os SDKs JavaFX empacotados e outros binários grandes. Em vez de
gravar o conteúdo binário no histórico do git, o LFS grava um pequeno
**arquivo de ponteiro** e envia os bytes reais para o armazenamento LFS no
remoto (GitHub).

## Por quê

O repositório empacota duas distribuições completas do JavaFX SDK 17.0.18
(Linux + Windows): bibliotecas nativas (`.so`, `.dll`), jars de módulos e
documentos legais. Manter esses binários no git comum incharia o histórico e
deixaria cada clone mais lento. O LFS mantém a árvore de trabalho utilizável
enquanto os bytes pesados ficam fora do histórico.

Além do tamanho do histórico, há um limite rígido: o GitHub **rejeita** blobs
git comuns acima de **100 MB**. Neste repositório, `libjfxwebkit.so` (~114 MB) e
`jfxwebkit.dll` (~90 MB) ultrapassam esse limite — só entram via LFS.

No último commit: **145 arquivos** são versionados pelo LFS
(verifique a contagem atual com `git lfs ls-files | wc -l`).

## O que é versionado

As regras de versionamento ficam no [`.gitattributes`](../.gitattributes) da
raiz:

```gitattributes
*.jar filter=lfs diff=lfs merge=lfs -text
*.dll filter=lfs diff=lfs merge=lfs -text
*.so  filter=lfs diff=lfs merge=lfs -text
javafxWindows_B/javafx-sdk-17.0.18/bin/jfxwebkit.dll filter=lfs diff=lfs merge=lfs -text
javafxLinux_B/javafx-sdk-17.0.18/lib/libjfxwebkit.so filter=lfs diff=lfs merge=lfs -text
javafxWindows_B/javafx-sdk-17.0.18/** filter=lfs diff=lfs merge=lfs -text
javafxLinux_B/javafx-sdk-17.0.18/**   filter=lfs diff=lfs merge=lfs -text
```

Resumo das regras:

| Padrão | Efeito |
|--------|--------|
| `*.jar`, `*.dll`, `*.so` | Qualquer jar / lib nativa em qualquer lugar → LFS |
| `javafxLinux_B/javafx-sdk-17.0.18/**`   | Todo arquivo sob o SDK Linux → LFS |
| `javafxWindows_B/javafx-sdk-17.0.18/**` | Todo arquivo sob o SDK Windows → LFS |

As duas linhas de arquivo único (`jfxwebkit.dll`, `libjfxwebkit.so`) são
redundantes diante dos globs `**`, mantidas por clareza sobre os dois maiores
binários.

## Configuração (clone novo)

O LFS precisa estar instalado **antes** de clonar, senão os arquivos do SDK
chegam como ponteiros de texto e a aplicação não inicia.

```bash
# uma vez por máquina
git lfs install

git clone git@github.com:ensismoebius/DS_B.git
```

Já clonou sem o LFS? Baixe os bytes reais:

```bash
git lfs install
git lfs pull
```

## Fluxo de trabalho diário

Com o LFS instalado, ele é transparente — `git add` / `commit` / `push` /
`pull` cuidam dos ponteiros e dos envios automaticamente.

Adicionar um novo padrão versionado:

```bash
git lfs track "*.bin"        # grava a regra no .gitattributes
git add .gitattributes
```

Reaplicar os filtros do LFS a arquivos já commitados antes de serem versionados:

```bash
git add --renormalize .
git commit -m "Migrate existing files to LFS"
```

### Migrar binários grandes já no histórico

Se um binário grande entrou no histórico como blob git comum (antes do LFS), o
`push` é rejeitado pelo limite do GitHub. Reescreva o histórico convertendo os
blobs em ponteiros LFS:

```bash
git lfs migrate import --everything --above=50MB   # reescreve blobs > 50 MB
git push --force origin main
```

> **Atenção**: `migrate import` e `push --force` **reescrevem o histórico**. Em
> repositório compartilhado, combine com a equipe antes — clones antigos
> precisarão refazer o clone ou um `git reset --hard origin/main`.

## Inspecionar o estado do LFS

```bash
git lfs ls-files          # lista arquivos versionados (oid + caminho)
git lfs ls-files | wc -l  # conta os arquivos
git lfs status            # arquivos LFS em stage/modificados
git lfs env               # endpoint + config do cache local de objetos
```

Endpoint atual: `https://github.com/ensismoebius/DS_B.git/info/lfs`
Cache local de objetos: `.git/lfs/objects`

Um arquivo de ponteiro (o que de fato fica no git) tem esta cara:

```
version https://git-lfs.github.com/spec/v1
oid sha256:<hash>
size <bytes>
```

## Solução de problemas

| Sintoma | Causa | Correção |
|---------|-------|----------|
| Aplicação não inicia; jars são arquivos de texto minúsculos | Clonado sem LFS | `git lfs install && git lfs pull` |
| `smudge filter lfs failed` | LFS não instalado | `git lfs install`, depois refaça o checkout |
| Novo binário commitado como blob bruto | Padrão ausente no `.gitattributes` | `git lfs track "<padrão>"`, depois `git add --renormalize .` |
| `push` rejeitado por arquivo > 100 MB | Blob grande já no histórico | `git lfs migrate import --above=50MB`, depois `git push --force` |
| Texto do ponteiro exibido no lugar do binário | Arquivo obtido antes do pull do LFS | `git lfs checkout` ou `git lfs pull` |

---

Voltar para o [Início](Home.md) · [README](../README.md).
