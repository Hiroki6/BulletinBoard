package models

import scalikejdbc._
import SQLInterpolation._

case class Topic(id: Long, name: String)

object Topic extends SQLSyntaxSupport[Topic]{
  override val tableName = "topic"
  override columnNames = Seq("id", "name")

  def create(name: String)(implicit session: DBSession):
    Topic = {
      val id = withSQL {
        insert.into(Topic).namedValues(
          column.name -> name
        )
      }.updateAndReturnGeneratedKey.apply()
    Topic(id, name)
  }

  def find(name: String)(implicit session: DBSession):
    Option[Topic] = {
      val t = Topic.syntax("t")
      withSQL { select.from(Topic as m).where.eq(t.name, name) }
        .map { rs => Topic(
          id = rs.long(t.resultName.id),
          name = rs.string(t.resultName.name)
        )
        }.single.apply()
  }
}