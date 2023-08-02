# _Repository archived_

本リポジトリは、jao Minecraft Server の Minecraft サーバサービス終了に伴いアーカイブ化されました。  
今後、原則として本リポジトリの内容がメンテナンスされることはありません。

本リポジトリに含まれるコンテンツは、継続して [LICENSE](LICENSE) で明示されているライセンスの下で公開されます。なお、事前予告なく非公開となる可能性もあります。

---

# TimingsGenerator

![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/jaoafa/TimingsGenerator)
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/jaoafa/TimingsGenerator/Java%20CI)
![GitHub](https://img.shields.io/github/license/jaoafa/TimingsGenerator)

Timings の URL を定期的に自動生成する Paper プラグインです。

本リポジトリは [okocraft/TimingsGenerator](https://github.com/okocraft/TimingsGenerator) をフォークし、一部の不具合を修正・機能追加したものです。

## Requirements

- Java 17
- **Paper** (Minecraft Version 1.15+, Tested with 1.18.1, not working with Spigot)

## Usage

- サーバーディレクトリの `/plugins/` に配置し、サーバーを再起動 (または PlugMan でロード) する。
- `/plugins/TimingsGenerator/config.yml` にて生成間隔を設定できる (デフォルトは 1h)。
- 生成した URL は `/plugins/TimingsGenerator/logs/` にて日付ごとのファイルに記録される。
- なお、 Timings の仕様により 30 日前のものは閲覧できないため、ロード時にログファイルも自動削除する (設定可)。

## Append function

- ログディレクトリが作成されなかったため、これを作成するようにしました
- ログファイルへ追記はされるものの新規作成されなかったため、存在しない場合に作成するようにしました
- ログファイルへの出力時、TPS 値を出力するようにしました
- ログファイルへの出力時、メッセージのうちレポート URL のみを出力するようにしました

## License

フォーク元のライセンスを継承し、 [GNU General Public License v3.0](/LICENSE) にて運用するものとします。
