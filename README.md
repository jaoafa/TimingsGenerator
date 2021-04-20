![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/okocraft/TimingsGenerator)
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/okocraft/TimingsGenerator/Java%20CI)
![GitHub](https://img.shields.io/github/license/okocraft/TimingsGenerator)

# TimingsGenerator

Timings の URL を定期的に自動生成する Paper プラグインです。

本リポジトリは [okocraft/TimingsGenerator](https://github.com/okocraft/TimingsGenerator) をフォークし、一部の不具合を修正・機能追加したものです。

## Requirements

- Java 11+
- **Paper** (Minecraft Version 1.15+, Tested with 1.16.5, not working with Spigot)

## Usage

- サーバーディレクトリの `/plugins/` に配置し、サーバーを再起動 (または PlugMan でロード) する。
- `/plugins/TimingsGenerator/config.yml` にて生成間隔を設定できる (デフォルトは 1h)。
- 生成した URL は `/plugins/TimingsGenerator/logs/` にて日付ごとのファイルに記録される。
- なお、 Timings の仕様により 30 日前のものは閲覧できないため、ロード時にログファイルも自動削除する (設定可)。

## Append function

- ログディレクトリが作成されなかったため、これを作成するようにしました
- ログファイルへ追記はされるものの新規作成されなかったため、存在しない場合に作成するようにしました
- ログファイルへの出力時、TPS値を出力するようにしました
- ログファイルへの出力時、メッセージのうちレポートURLのみを出力するようにしました

## License

フォーク元のライセンスを継承し、 [GNU General Public License v3.0](/LICENSE) にて運用するものとします。
