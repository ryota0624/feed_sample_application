package net.ryota.infra

import java.io.File

object CodeGen extends App {
  val slickDriver = "slick.jdbc.PostgresProfile"
  val jdbcDriver = "org.postgresql.Driver"
  val url = "jdbc:postgresql://localhost/sample_app_development"
  val outputDir = "modules/infra/src/main/scala"
  val pkg = "net/ryota/infra"
  val user = "postgres"
  val password = ""

  val currentDir = new File(".").getAbsoluteFile().getParent()
  println(currentDir)
  slick.codegen.SourceCodeGenerator.main(
    Array(slickDriver, jdbcDriver, url, outputDir, pkg, user, password))
}