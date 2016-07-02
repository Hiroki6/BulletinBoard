package models

import scalikejdbc._

case class Topic(id: Int, name: String)

object Topic extends SQLSyntaxSupport[Topic]{

  override val tableName = "topic"
  override val columns = Seq("id", "name")

  val t = Topic.syntax("t")
  def create(name: String)(implicit session: DBSession = autoSession):
    Topic = {
      val id = withSQL {
        insert.into(Topic).namedValues(
          column.name -> name
        )
      }.updateAndReturnGeneratedKey.apply()
    Topic(id = id, name = name)
  }

  def find(id: Int)(implicit session: DBSession = autoSession):
    Option[Topic] = {
      withSQL { select.from(Topic as t).where.eq(t.id, id) }
        .map { rs => Topic(
          id = rs.long(t.resultName.id),
          name = rs.string(t.resultName.name)
        )
        }.single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession):
    List[Topic] = {
      withSQL { select.from(Topic as t) }
        .map { rs => Topic(
          id = rs.long(t.resultName.id),
          name = rs.string(t.resultName.name)
          )
        }.list.apply()
  }
}

