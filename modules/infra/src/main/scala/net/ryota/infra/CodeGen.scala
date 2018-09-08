package net.ryota.infra

import com.typesafe.config.ConfigFactory

object CodeGen extends App {
  val dbConfig = ConfigFactory.load().getConfig("database")
  val slickDriver = dbConfig.getString("slickDriver")
  val jdbcDriver = dbConfig.getString("driver")
  val url = dbConfig.getString("url")
  val outputDir = "modules/infra/src/main/scala"
  val pkg = "net.ryota.infra"
  val user = dbConfig.getString("user")
  val password = dbConfig.getString("password")

  slick.codegen.SourceCodeGenerator.main(
    Array(slickDriver, jdbcDriver, url, outputDir, pkg, user, password))
}