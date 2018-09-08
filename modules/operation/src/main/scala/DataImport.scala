import java.time.ZonedDateTime
import slick.jdbc.PostgresProfile.api._

import net.ryota.infra.Tables
import scala.concurrent.ExecutionContext.Implicits.global
object DataImport extends App {
  val now = ZonedDateTime.now

  val feed = Tables.FeedsRow(
    id = now.toEpochSecond.toString,
    title = "title",
    describe = "describe",
    createdAt = new java.sql.Date(now.toEpochSecond)
  )


  val query = Tables.Feeds.take(100)

  val db = Database.forConfig("database")
  try {
    db.run(query.result).foreach(_.foreach {
      row => printf(row.describe)
    })
  }
  catch {
    case t: Throwable =>
      println(t)

  }
  finally {
    println("finally")
    db.close()
  }


}
