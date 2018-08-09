name := "scala"

version := "0.1"

scalaVersion := "2.12.6"

lazy val sharedScalacOptions = Seq(
  "-language:higherKinds",
  "-language:implicitConversions",
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlint",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused-import",
  "-Xfatal-warnings",
)


lazy val webappDependencies = Seq(
  "com.typesafe.akka" %% "akka-http"   % "10.1.3",
  "com.typesafe.akka" %% "akka-stream" % "2.5.12"
)

lazy val util = (project in file("modules/util"))

lazy val domain = (project in file("modules/domain"))
  .dependsOn(util)

lazy val infra = (project in file("modules/infra"))
  .dependsOn(util, domain)

lazy val webapp = (project in file("modules/webapp"))
  .dependsOn(util, domain, infra)
  .enablePlugins(JavaAppPackaging)
  .settings(Seq(
    maintainer in Docker := "Ryota",
    dockerBaseImage in Docker := "dockerfile/java",
    dockerExposedPorts := Seq(8080)
  ))
  .settings(libraryDependencies ++= webappDependencies)


