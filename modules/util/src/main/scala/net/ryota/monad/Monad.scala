package net.ryota.monad

import scala.concurrent.{ExecutionContext, Future}

trait Monad[A] { /*全然モナドじゃないけど*/
  def map[B](f: A => B): Monad[B]
  def flatMap[B](f: A => Monad[B]): Monad[B]
}

object Async {
  def apply[R](body: => R): Async[R] = new Async(_ => body)

  def withCtx[R](body: ExecutionContext => R): Async[R] = new Async[R](body)

}

class Async[R](val body: ExecutionContext => R) {
  def run(implicit context: ExecutionContext): Future[R] = Future {
    body()
  }

  def map[R2](f: R => R2): Async[R2] = Async.withCtx { ctx: ExecutionContext =>
    body.andThen(f)(ctx)
  }

  def flatMap[R2](f: R => Async[R2]): Async[R2] = {
    val result = body.andThen(f)
    Async.withCtx { ctx =>
      result(ctx).body(ctx)
    }
  }
}