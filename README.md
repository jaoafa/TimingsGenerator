![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/okocraft/TimingsGenerator)
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/okocraft/TimingsGenerator/Java%20CI)
![GitHub](https://img.shields.io/github/license/okocraft/TimingsGenerator)

# TimingsGenerator

Timings の URL を定期的に自動生成する Paper プラグインです。

## Requirements

- Java 11+
- **Paper** (Minecraft Version 1.15+, not working with Spigot)

## Usage

サーバーディレクトリの `/plugins/` に配置し、サーバーを再起動 (または PlugMan でロード) する。

`/plugins/TimingsGenerator/config.yml` にて生成間隔を設定できる (デフォルトは 3h)。

生成した URL は `/plugins/TimingsGenerator/logs/` にて日付ごとのファイルに記録される。

なお、 Timings の仕様により30日前のものは閲覧できないため、ロード時にログファイルも自動削除する (設定可)。