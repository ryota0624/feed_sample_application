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

lazy val shareDependencies = Seq(
  "com.typesafe" % "config" % "1.3.0"
)

lazy val webappDependencies = Seq(
  "com.typesafe.akka" %% "akka-http"   % "10.1.3",
  "com.typesafe.akka" %% "akka-stream" % "2.5.12",
  "io.spray" %%  "spray-json" % "1.3.4",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.4",
) ++ shareDependencies

lazy val infraDependencies = Seq(
  "com.typesafe.slick" %% "slick" % "3.2.3",
  "com.typesafe.slick" %% "slick-codegen" % "3.2.3",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "org.slf4j" % "slf4j-simple" % "1.6.4" // scalikejdbcが依存している？
) ++ shareDependencies

lazy val util = (project in file("modules/util"))

lazy val domain = (project in file("modules/domain"))
  .dependsOn(util)

lazy val infra = (project in file("modules/infra"))
  .dependsOn(util, domain)
  .settings(libraryDependencies ++= infraDependencies)

lazy val webapp = (project in file("modules/webapp"))
  .dependsOn(util, domain, infra)
  .enablePlugins(JavaAppPackaging)
  .settings(Seq(
    maintainer in Docker := "Ryota",
    dockerBaseImage in Docker := "dockerfile/java",
    dockerExposedPorts := Seq(8080)
  ))
  .settings(libraryDependencies ++= webappDependencies)


