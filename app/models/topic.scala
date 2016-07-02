package models

import scalikejdbc._

case class Topic(id: Long, name: String)

object Topic extends SQLSyntaxSupport[Topic]{

  override val tableName = "topic"
  override val columns = Seq("id", "name")

  def create(name: String)(implicit session: DBSession = autoSession):
    Topic = {
      val id = withSQL {
        insert.into(Topic).namedValues(
          column.name -> name
        )
      }.updateAndReturnGeneratedKey.apply()
    Topic(id = id, name = name)
  }

  def find(name: String)(implicit session: DBSession = autoSession):
    Option[Topic] = {
      val t = Topic.syntax("t")
      withSQL { select.from(Topic as t).where.eq(t.name, name) }
        .map { rs => Topic(
          id = rs.long(t.resultName.id),
          name = rs.string(t.resultName.name)
        )
        }.single.apply()
  }
}

