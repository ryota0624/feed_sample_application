# Feedが流れるアプリケーション(予定)

## TODO
- slf4jなんやねん
- logback使い方
- logger作る
- dockerコンテナにpostgres内臓


## project structure

```
- modules
    - domain: feedアプリケーションのコアロジック
    - webapp: webappモジュール
    - util: scala系共通モジュール
```

### docker系

- localPublish -> ローカルにイメージができあがる
- stage -> dockerfileができあがる


### TableGen
```
sbt
project infra
run
```

### DB

```
-- DDL generated by Postico 1.4.2
-- Not all database features are supported. Do not use for backup.

-- Table Definition ----------------------------------------------

CREATE TABLE feeds (
    id text PRIMARY KEY,
    title text NOT NULL,
    describe text NOT NULL,
    created_at date NOT NULL
);

-- Indices -------------------------------------------------------

CREATE UNIQUE INDEX feeds_pkey ON feeds(id text_ops);

```

### LOG
http://slick.lightbend.com/doc/3.2.0-M1/config.html