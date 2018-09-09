package net.ryota.infra

import slick.jdbc.JdbcBackend.SessionDef

import scala.concurrent.{ExecutionContext, Future}


object AsyncDBAction {
  def apply[R](body: => R): AsyncDBAction[R] = new AsyncDBAction(_ => body)
}


class AsyncDBAction[R](val body: Unit => R) {
  def run(implicit context: ExecutionContext, dbSession: Any): Future[R] = Future {
    withSession(dbSession) { () =>
      body()
    }
  }

  def map[R2](f: R => R2): AsyncDBAction[R2] = AsyncDBAction {
    body.andThen(f)()
  }

  def flatMap[R2](f: R => AsyncDBAction[R2]): AsyncDBAction[R2] = {
    val result = body.andThen(f)
    AsyncDBAction {
      result().body()
    }
  }
}
