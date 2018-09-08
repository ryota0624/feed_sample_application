import java.time.ZonedDateTime

import slick.jdbc.PostgresProfile.api._
import net.ryota.infra.Tables

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object DataImport extends App {
  lazy val db = Database.forConfig("database")

  def insertFeed() {
    val now = ZonedDateTime.now

    val feed = Tables.FeedsRow(
      id = now.toEpochSecond.toString,
      title = s"title${now.toEpochSecond.toString}",
      describe = "describe",
      createdAt = java.sql.Date.valueOf(now.toLocalDate)
    )

    val writeStatement = Tables.Feeds += feed


    val writeF = db.run(writeStatement)

    Await.result(writeF, Duration.Inf)

  }

  def showFeeds() {
    val query = Tables.Feeds

    try {
      db.run(query.result).foreach(_.foreach {
        row => println(row.describe)
      })
    } finally {
      db.close()
    }
  }


}
