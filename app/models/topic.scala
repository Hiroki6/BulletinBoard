package models

import scalikejdbc._

case class Topic(id: Long, name: String)

object Topic extends SQLSyntaxSupport[Topic]{

  override val tableName = "topic"
  override val columns = Seq("id", "name")

  val t = Topic.syntax("t")

  def apply(t: ResultName[Topic])(rs: WrappedResultSet): Topic = Topic(rs.long(t.id), rs.string(t.name))

  def create(name: String)(implicit session: DBSession = autoSession):
    Topic = {
      val id = withSQL {
        insert.into(Topic).namedValues(
          column.name -> name
        )
      }.updateAndReturnGeneratedKey.apply()
      Topic(id = id, name = name)
    }

  def find(id: Long)(implicit session: DBSession = autoSession):
    Option[Topic] = {
      withSQL { select.from(Topic as t).where.eq(t.id, id) }
        .map(Topic(t.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession):
    List[Topic] = {
      withSQL { select.from(Topic as t) }
        .map(Topic(t.resultName)).list.apply()
    }
}

