# 株式会社ZAICO Android エンジニアコーディングテスト

## 概要

本プロジェクトは株式会社ZAICO（以下弊社）が、弊社に Android エンジニアを希望する方に出す課題のベースプロジェクトです。
下記の概要を詳しく読んだ上で課題を取り組んでください。

## アプリ仕様

本アプリは zaico API を利用して、在庫情報を取得・表示と作成をするアプリです。

<img src="sample/app_sample.gif" width="320">

### zaico APIの仕様と使い方

API 仕様は以下の通りです。

[zaico API Document](https://zaicodev.github.io/zaico_api_doc/)

zaicoのAPIを利用するためzaicoのアカウントを登録し、APIトークンを取得して使用してください。

[API利用に関するドキュメント](https://support.zaico.co.jp/hc/ja/articles/4406632009625-zaico-API%E3%82%92%E4%BD%BF%E3%81%A3%E3%81%A6%E5%9C%A8%E5%BA%AB%E3%83%87%E3%83%BC%E3%82%BF%E3%82%92%E6%93%8D%E4%BD%9C%E3%81%99%E3%82%8B)

ビルドしたアプリで動作確認をスムーズに行えるように、まずはzaicoから在庫登録してデータを作成してください。

[在庫登録のドキュメント](https://support.zaico.co.jp/hc/ja/articles/9425011130265--WEB-%E5%9C%A8%E5%BA%AB%E3%83%87%E3%83%BC%E3%82%BF%E3%82%92%E7%99%BB%E9%8C%B2%E3%81%99%E3%82%8B)

### 動作確認済の開発環境

- IDE：Android Studio Ladybug Feature Drop | 2024.2.2 Patch 1
- Kotlin：1.9.24
- Java：17
- Gradle：8.8.1
- minSdk：28
- targetSdk：35

※ ライブラリの利用はオープンソースのものに限ります。
※ 環境は適宜更新してください。

### アプリ動作

1. 登録されている在庫一覧の表示
2. 在庫詳細データの表示
3. 在庫データの追加・検索

## 課題取り組み方法

本プロジェクトを [**Duplicate** してください](https://help.github.com/en/github/creating-cloning-and-archiving-repositories/duplicating-a-repository)（Fork しないようにしてください。必要ならプライベートリポジトリにしても大丈夫です）。今後のコミットは全てご自身のリポジトリで行ってください。

次の選考を開始する前までに課題を確認・対応し、出来た所までで問題ありませんので、リポジトリのアドレスをご連絡ください。