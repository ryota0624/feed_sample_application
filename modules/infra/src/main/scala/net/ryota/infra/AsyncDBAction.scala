package net.ryota.infra

import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.PostgresProfile.backend.Database
case class Ctx(executionContext: ExecutionContext, database: Database)

object Ctx {
  implicit def ctxToEc(ctx: Ctx): ExecutionContext = ctx.executionContext
  implicit def ctxToDb(ctx: Ctx): Database = ctx.database
}

object AsyncDBAction {
  def apply[R](body: => R): AsyncDBAction[R] = new AsyncDBAction({ implicit ctx: Ctx => Future.apply(body)(ctx) })

  def withCtx[R](body: Ctx => R): AsyncDBAction[R] = new AsyncDBAction[R]({ctx: Ctx => Future.apply(body(ctx))(ctx) })

  def withCtxFuture[R](body: Ctx => Future[R]): AsyncDBAction[R] = new AsyncDBAction[R]({
    ctx: Ctx => body(ctx)
  })

}

class AsyncDBAction[R](val body: Ctx => Future[R]) {
  def run(implicit ctx: Ctx): Future[R] = body(ctx)


  def map[R2](f: R => R2): AsyncDBAction[R2] = AsyncDBAction.withCtxFuture { ctx: Ctx =>
    body(ctx).map(f)(ctx)
  }

  def flatMap[R2](f: R => AsyncDBAction[R2]): AsyncDBAction[R2] = AsyncDBAction.withCtxFuture { ctx =>
      val result = body(ctx)
      result.map(f)(ctx).flatMap {
        action => action.body(ctx)
      } (ctx)
    }

}