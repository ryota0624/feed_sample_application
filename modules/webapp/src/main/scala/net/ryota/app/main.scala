package net.ryota.app

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.{Http, server}
import akka.stream.ActorMaterializer
import net.ryota.serif.domains.Serif

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object main extends App with Route {
  implicit lazy val system: ActorSystem = ActorSystem("feed-app")
  implicit lazy val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = system.dispatcher
  val interface = "localhost"
  val port = 8080
  val logger = Logging(system, getClass)
  val binding = Http().bindAndHandle(routes, interface, port)

  StdIn.readLine() // let it run until user presses return
  binding
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ ⇒ system.terminate()) // and shutdown when done


}

trait Route {
  import akka.http.scaladsl.server.Directives._

  val routes: server.Route = pathSingleSlash {
    get {
      complete(Serif("Hello").emphasis())
    }
  }
}