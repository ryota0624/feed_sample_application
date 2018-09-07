package net/ryota/infra
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.PostgresProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(__DieselSchemaMigrations.schema, Comments.schema, Feeds.schema, Memos.schema, MemoTagRels.schema, MicrateDbVersion.schema, Posts.schema, PostTagRels.schema, TagGroups.schema, Tags.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table __DieselSchemaMigrations
   *  @param version Database column version SqlType(varchar), PrimaryKey, Length(50,true)
   *  @param runOn Database column run_on SqlType(timestamp) */
  case class __DieselSchemaMigrationsRow(version: String, runOn: java.sql.Timestamp)
  /** GetResult implicit for fetching __DieselSchemaMigrationsRow objects using plain SQL queries */
  implicit def GetResult__DieselSchemaMigrationsRow(implicit e0: GR[String], e1: GR[java.sql.Timestamp]): GR[__DieselSchemaMigrationsRow] = GR{
    prs => import prs._
    __DieselSchemaMigrationsRow.tupled((<<[String], <<[java.sql.Timestamp]))
  }
  /** Table description of table __diesel_schema_migrations. Objects of this class serve as prototypes for rows in queries. */
  class __DieselSchemaMigrations(_tableTag: Tag) extends profile.api.Table[__DieselSchemaMigrationsRow](_tableTag, "__diesel_schema_migrations") {
    def * = (version, runOn) <> (__DieselSchemaMigrationsRow.tupled, __DieselSchemaMigrationsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(version), Rep.Some(runOn)).shaped.<>({r=>import r._; _1.map(_=> __DieselSchemaMigrationsRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column version SqlType(varchar), PrimaryKey, Length(50,true) */
    val version: Rep[String] = column[String]("version", O.PrimaryKey, O.Length(50,varying=true))
    /** Database column run_on SqlType(timestamp) */
    val runOn: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("run_on")
  }
  /** Collection-like TableQuery object for table __DieselSchemaMigrations */
  lazy val __DieselSchemaMigrations = new TableQuery(tag => new __DieselSchemaMigrations(tag))

  /** Entity class storing rows of table Comments
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param postId Database column post_id SqlType(serial), AutoInc
   *  @param text Database column text SqlType(text) */
  case class CommentsRow(id: Int, postId: Int, text: String)
  /** GetResult implicit for fetching CommentsRow objects using plain SQL queries */
  implicit def GetResultCommentsRow(implicit e0: GR[Int], e1: GR[String]): GR[CommentsRow] = GR{
    prs => import prs._
    CommentsRow.tupled((<<[Int], <<[Int], <<[String]))
  }
  /** Table description of table comments. Objects of this class serve as prototypes for rows in queries. */
  class Comments(_tableTag: Tag) extends profile.api.Table[CommentsRow](_tableTag, "comments") {
    def * = (id, postId, text) <> (CommentsRow.tupled, CommentsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(postId), Rep.Some(text)).shaped.<>({r=>import r._; _1.map(_=> CommentsRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column post_id SqlType(serial), AutoInc */
    val postId: Rep[Int] = column[Int]("post_id", O.AutoInc)
    /** Database column text SqlType(text) */
    val text: Rep[String] = column[String]("text")
  }
  /** Collection-like TableQuery object for table Comments */
  lazy val Comments = new TableQuery(tag => new Comments(tag))

  /** Entity class storing rows of table Feeds
   *  @param id Database column id SqlType(text), PrimaryKey
   *  @param title Database column title SqlType(text)
   *  @param describe Database column describe SqlType(text)
   *  @param createdAt Database column created_at SqlType(date) */
  case class FeedsRow(id: String, title: String, describe: String, createdAt: java.sql.Date)
  /** GetResult implicit for fetching FeedsRow objects using plain SQL queries */
  implicit def GetResultFeedsRow(implicit e0: GR[String], e1: GR[java.sql.Date]): GR[FeedsRow] = GR{
    prs => import prs._
    FeedsRow.tupled((<<[String], <<[String], <<[String], <<[java.sql.Date]))
  }
  /** Table description of table feeds. Objects of this class serve as prototypes for rows in queries. */
  class Feeds(_tableTag: Tag) extends profile.api.Table[FeedsRow](_tableTag, "feeds") {
    def * = (id, title, describe, createdAt) <> (FeedsRow.tupled, FeedsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(title), Rep.Some(describe), Rep.Some(createdAt)).shaped.<>({r=>import r._; _1.map(_=> FeedsRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(text), PrimaryKey */
    val id: Rep[String] = column[String]("id", O.PrimaryKey)
    /** Database column title SqlType(text) */
    val title: Rep[String] = column[String]("title")
    /** Database column describe SqlType(text) */
    val describe: Rep[String] = column[String]("describe")
    /** Database column created_at SqlType(date) */
    val createdAt: Rep[java.sql.Date] = column[java.sql.Date]("created_at")
  }
  /** Collection-like TableQuery object for table Feeds */
  lazy val Feeds = new TableQuery(tag => new Feeds(tag))

  /** Entity class storing rows of table Memos
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param title Database column title SqlType(varchar), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamp), Default(None)
   *  @param updatedAt Database column updated_at SqlType(timestamp), Default(None) */
  case class MemosRow(id: Long, title: Option[String] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching MemosRow objects using plain SQL queries */
  implicit def GetResultMemosRow(implicit e0: GR[Long], e1: GR[Option[String]], e2: GR[Option[java.sql.Timestamp]]): GR[MemosRow] = GR{
    prs => import prs._
    MemosRow.tupled((<<[Long], <<?[String], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table memos. Objects of this class serve as prototypes for rows in queries. */
  class Memos(_tableTag: Tag) extends profile.api.Table[MemosRow](_tableTag, "memos") {
    def * = (id, title, createdAt, updatedAt) <> (MemosRow.tupled, MemosRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), title, createdAt, updatedAt).shaped.<>({r=>import r._; _1.map(_=> MemosRow.tupled((_1.get, _2, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column title SqlType(varchar), Default(None) */
    val title: Rep[Option[String]] = column[Option[String]]("title", O.Default(None))
    /** Database column created_at SqlType(timestamp), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(timestamp), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table Memos */
  lazy val Memos = new TableQuery(tag => new Memos(tag))

  /** Entity class storing rows of table MemoTagRels
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param tagId Database column tag_id SqlType(bigserial), AutoInc
   *  @param memoId Database column memo_id SqlType(bigserial), AutoInc
   *  @param createdAt Database column created_at SqlType(timestamp), Default(None)
   *  @param updatedAt Database column updated_at SqlType(timestamp), Default(None) */
  case class MemoTagRelsRow(id: Long, tagId: Long, memoId: Long, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching MemoTagRelsRow objects using plain SQL queries */
  implicit def GetResultMemoTagRelsRow(implicit e0: GR[Long], e1: GR[Option[java.sql.Timestamp]]): GR[MemoTagRelsRow] = GR{
    prs => import prs._
    MemoTagRelsRow.tupled((<<[Long], <<[Long], <<[Long], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table memo_tag_rels. Objects of this class serve as prototypes for rows in queries. */
  class MemoTagRels(_tableTag: Tag) extends profile.api.Table[MemoTagRelsRow](_tableTag, "memo_tag_rels") {
    def * = (id, tagId, memoId, createdAt, updatedAt) <> (MemoTagRelsRow.tupled, MemoTagRelsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(tagId), Rep.Some(memoId), createdAt, updatedAt).shaped.<>({r=>import r._; _1.map(_=> MemoTagRelsRow.tupled((_1.get, _2.get, _3.get, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column tag_id SqlType(bigserial), AutoInc */
    val tagId: Rep[Long] = column[Long]("tag_id", O.AutoInc)
    /** Database column memo_id SqlType(bigserial), AutoInc */
    val memoId: Rep[Long] = column[Long]("memo_id", O.AutoInc)
    /** Database column created_at SqlType(timestamp), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(timestamp), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table MemoTagRels */
  lazy val MemoTagRels = new TableQuery(tag => new MemoTagRels(tag))

  /** Entity class storing rows of table MicrateDbVersion
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param versionId Database column version_id SqlType(int8)
   *  @param isApplied Database column is_applied SqlType(bool)
   *  @param tstamp Database column tstamp SqlType(timestamp) */
  case class MicrateDbVersionRow(id: Int, versionId: Long, isApplied: Boolean, tstamp: Option[java.sql.Timestamp])
  /** GetResult implicit for fetching MicrateDbVersionRow objects using plain SQL queries */
  implicit def GetResultMicrateDbVersionRow(implicit e0: GR[Int], e1: GR[Long], e2: GR[Boolean], e3: GR[Option[java.sql.Timestamp]]): GR[MicrateDbVersionRow] = GR{
    prs => import prs._
    MicrateDbVersionRow.tupled((<<[Int], <<[Long], <<[Boolean], <<?[java.sql.Timestamp]))
  }
  /** Table description of table micrate_db_version. Objects of this class serve as prototypes for rows in queries. */
  class MicrateDbVersion(_tableTag: Tag) extends profile.api.Table[MicrateDbVersionRow](_tableTag, "micrate_db_version") {
    def * = (id, versionId, isApplied, tstamp) <> (MicrateDbVersionRow.tupled, MicrateDbVersionRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(versionId), Rep.Some(isApplied), tstamp).shaped.<>({r=>import r._; _1.map(_=> MicrateDbVersionRow.tupled((_1.get, _2.get, _3.get, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column version_id SqlType(int8) */
    val versionId: Rep[Long] = column[Long]("version_id")
    /** Database column is_applied SqlType(bool) */
    val isApplied: Rep[Boolean] = column[Boolean]("is_applied")
    /** Database column tstamp SqlType(timestamp) */
    val tstamp: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("tstamp")
  }
  /** Collection-like TableQuery object for table MicrateDbVersion */
  lazy val MicrateDbVersion = new TableQuery(tag => new MicrateDbVersion(tag))

  /** Entity class storing rows of table Posts
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param title Database column title SqlType(varchar)
   *  @param body Database column body SqlType(text)
   *  @param published Database column published SqlType(bool), Default(false) */
  case class PostsRow(id: Int, title: String, body: String, published: Boolean = false)
  /** GetResult implicit for fetching PostsRow objects using plain SQL queries */
  implicit def GetResultPostsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Boolean]): GR[PostsRow] = GR{
    prs => import prs._
    PostsRow.tupled((<<[Int], <<[String], <<[String], <<[Boolean]))
  }
  /** Table description of table posts. Objects of this class serve as prototypes for rows in queries. */
  class Posts(_tableTag: Tag) extends profile.api.Table[PostsRow](_tableTag, "posts") {
    def * = (id, title, body, published) <> (PostsRow.tupled, PostsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(title), Rep.Some(body), Rep.Some(published)).shaped.<>({r=>import r._; _1.map(_=> PostsRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column title SqlType(varchar) */
    val title: Rep[String] = column[String]("title")
    /** Database column body SqlType(text) */
    val body: Rep[String] = column[String]("body")
    /** Database column published SqlType(bool), Default(false) */
    val published: Rep[Boolean] = column[Boolean]("published", O.Default(false))
  }
  /** Collection-like TableQuery object for table Posts */
  lazy val Posts = new TableQuery(tag => new Posts(tag))

  /** Entity class storing rows of table PostTagRels
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param tagId Database column tag_id SqlType(serial), AutoInc
   *  @param postId Database column post_id SqlType(serial), AutoInc */
  case class PostTagRelsRow(id: Int, tagId: Int, postId: Int)
  /** GetResult implicit for fetching PostTagRelsRow objects using plain SQL queries */
  implicit def GetResultPostTagRelsRow(implicit e0: GR[Int]): GR[PostTagRelsRow] = GR{
    prs => import prs._
    PostTagRelsRow.tupled((<<[Int], <<[Int], <<[Int]))
  }
  /** Table description of table post_tag_rels. Objects of this class serve as prototypes for rows in queries. */
  class PostTagRels(_tableTag: Tag) extends profile.api.Table[PostTagRelsRow](_tableTag, "post_tag_rels") {
    def * = (id, tagId, postId) <> (PostTagRelsRow.tupled, PostTagRelsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(tagId), Rep.Some(postId)).shaped.<>({r=>import r._; _1.map(_=> PostTagRelsRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column tag_id SqlType(serial), AutoInc */
    val tagId: Rep[Int] = column[Int]("tag_id", O.AutoInc)
    /** Database column post_id SqlType(serial), AutoInc */
    val postId: Rep[Int] = column[Int]("post_id", O.AutoInc)
  }
  /** Collection-like TableQuery object for table PostTagRels */
  lazy val PostTagRels = new TableQuery(tag => new PostTagRels(tag))

  /** Entity class storing rows of table TagGroups
   *  @param id Database column id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(varchar), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamp), Default(None)
   *  @param updatedAt Database column updated_at SqlType(timestamp), Default(None) */
  case class TagGroupsRow(id: Long, name: Option[String] = None, createdAt: Option[java.sql.Timestamp] = None, updatedAt: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching TagGroupsRow objects using plain SQL queries */
  implicit def GetResultTagGroupsRow(implicit e0: GR[Long], e1: GR[Option[String]], e2: GR[Option[java.sql.Timestamp]]): GR[TagGroupsRow] = GR{
    prs => import prs._
    TagGroupsRow.tupled((<<[Long], <<?[String], <<?[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table tag_groups. Objects of this class serve as prototypes for rows in queries. */
  class TagGroups(_tableTag: Tag) extends profile.api.Table[TagGroupsRow](_tableTag, "tag_groups") {
    def * = (id, name, createdAt, updatedAt) <> (TagGroupsRow.tupled, TagGroupsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), name, createdAt, updatedAt).shaped.<>({r=>import r._; _1.map(_=> TagGroupsRow.tupled((_1.get, _2, _3, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(bigserial), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(varchar), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Default(None))
    /** Database column created_at SqlType(timestamp), Default(None) */
    val createdAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("created_at", O.Default(None))
    /** Database column updated_at SqlType(timestamp), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
  }
  /** Collection-like TableQuery object for table TagGroups */
  lazy val TagGroups = new TableQuery(tag => new TagGroups(tag))

  /** Entity class storing rows of table Tags
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param label Database column label SqlType(varchar) */
  case class TagsRow(id: Int, label: String)
  /** GetResult implicit for fetching TagsRow objects using plain SQL queries */
  implicit def GetResultTagsRow(implicit e0: GR[Int], e1: GR[String]): GR[TagsRow] = GR{
    prs => import prs._
    TagsRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table tags. Objects of this class serve as prototypes for rows in queries. */
  class Tags(_tableTag: Tag) extends profile.api.Table[TagsRow](_tableTag, "tags") {
    def * = (id, label) <> (TagsRow.tupled, TagsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(label)).shaped.<>({r=>import r._; _1.map(_=> TagsRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column label SqlType(varchar) */
    val label: Rep[String] = column[String]("label")
  }
  /** Collection-like TableQuery object for table Tags */
  lazy val Tags = new TableQuery(tag => new Tags(tag))
}
