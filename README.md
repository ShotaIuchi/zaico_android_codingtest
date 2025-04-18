# 株式会社 ZAICO Android エンジニアコーディングテスト

## 概要

本プロジェクトは株式会社 ZAICO（以下弊社）が、弊社に Android エンジニアを希望する方に出す課題のベースプロジェクトです。
下記の概要を詳しく読んだ上で課題を取り組んでください。

## アプリ仕様

本アプリは zaico API を利用して、在庫情報を取得・表示と作成をするアプリです。

<img src="sample/app_sample.gif" width="320">

### zaico API の仕様と使い方

API 仕様は以下の通りです。

[zaico API Document](https://zaicodev.github.io/zaico_api_doc/)

zaico の API を利用するため zaico のアカウントを登録し、API トークンを取得して使用してください。

[API 利用に関するドキュメント](https://support.zaico.co.jp/hc/ja/articles/4406632009625-zaico-API%E3%82%92%E4%BD%BF%E3%81%A3%E3%81%A6%E5%9C%A8%E5%BA%AB%E3%83%87%E3%83%BC%E3%82%BF%E3%82%92%E6%93%8D%E4%BD%9C%E3%81%99%E3%82%8B)

ビルドしたアプリで動作確認をスムーズに行えるように、まずは zaico から在庫登録してデータを作成してください。

[在庫登録のドキュメント](https://support.zaico.co.jp/hc/ja/articles/9425011130265--WEB-%E5%9C%A8%E5%BA%AB%E3%83%87%E3%83%BC%E3%82%BF%E3%82%92%E7%99%BB%E9%8C%B2%E3%81%99%E3%82%8B)

### 動作確認済の開発環境

- IDE：Android Studio Meerkat | 2024.3.1 Patch 1

  ```
  Build #AI-243.24978.46.2431.13208083, built on March 13, 2025
  Runtime version: 21.0.5+-13047016-b750.29 amd64
  VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
  Toolkit: sun.awt.windows.WToolkit
  Windows 10.0
  Kotlin plugin: K2 mode
  GC: G1 Young Generation, G1 Concurrent GC, G1 Old Generation
  Memory: 2048M
  Cores: 8
  Registry:
  ide.experimental.ui=true
  ```

- Kotlin：2.0.21
- Java：17
- Gradle：8.9.1
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

次の選考を開始する前までに[課題](https://github.com/ShotaIuchi/zaico_android_codingtest/issues/1)を確認・対応し、出来た所までで問題ありませんので、リポジトリのアドレスをご連絡ください。
