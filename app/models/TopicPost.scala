package models

import scalikejdbc._

case class TopicPost(id: Long, content: String, topic_id: Long)

object TopicPost extends SQLSyntaxSupport[TopicPost]{

  override val tableName = "topic_post"
  override val columns = Seq("id", "content", "topic_id")

  val p = TopicPost.syntax("p")

  def apply(p: ResultName[TopicPost])(rs: WrappedResultSet): TopicPost =
    TopicPost(rs.long(p.id), rs.string(p.content), rs.long(p.topic_id))

  def create(content: String, topic_id: Long)(implicit session: DBSession = autoSession):
    TopicPost = {
      val id = withSQL {
        insert.into(TopicPost).namedValues(
          column.content -> content,
          column.topic_id -> topic_id
        )
      }.updateAndReturnGeneratedKey.apply()
      TopicPost(id = id, content = content, topic_id = topic_id)
    }

  def find(id: Long)(implicit session: DBSession = autoSession):
  Option[TopicPost] = {
    withSQL { select.from(TopicPost as p).where.eq(p.id, id) }
      .map(TopicPost(p.resultName)).single.apply()
  }

  def findByTopicId(topic_id: Long)(implicit session: DBSession = autoSession):
    List[TopicPost] = {
      withSQL { select.from(TopicPost as p).where.eq(p.topic_id, topic_id) }
        .map(TopicPost(p.resultName)).list.apply()
  }
}

