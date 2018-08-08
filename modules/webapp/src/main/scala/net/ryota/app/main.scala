package net.ryota.app

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.{Http, server}
import akka.stream.ActorMaterializer
import net.ryota.serif.domains.Serif

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object main extends App with Route {
  implicit lazy val actorSystem: ActorSystem = ActorSystem("feed-app")
  implicit lazy val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = actorSystem.dispatcher
  val host = sys.props.get("http.host") getOrElse "0.0.0.0"

  val port = 8080
  val logger = Logging(actorSystem, getClass)
  val binding = Http().bindAndHandle(routes(logger.info), host, port)

  logger.info("start application")

  binding.foreach {
    _ => logger.info("success start server")
  }

  binding.failed.foreach {
    case err: Exception =>
      logger.error(err, s"Failed to bind to $host $port")
  }
  sys.addShutdownHook {
    binding.foreach(_.unbind())
    actorSystem.registerOnTermination {
      logger.info(s"actor system terminated")
    }
    actorSystem.terminate()
  }
//  StdIn.readLine() // let it run until user presses return
//  binding
//    .flatMap(_.unbind()) // trigger unbinding from the port
//    .onComplete { _ =>
//      logger.info("stop server")
//      system.terminate()
//    }
}

trait Route {
  import akka.http.scaladsl.server.Directives._

  def routes(log:String => Unit): server.Route = pathSingleSlash {
    get {
      log("Access!")
      complete(Serif("Hello").emphasis())
    }
  }
}