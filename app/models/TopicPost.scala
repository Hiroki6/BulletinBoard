package models

import scalikejdbc._

case class TopicPost(id: Long, content: String, topic_id: Long)

object TopicPost extends SQLSyntaxSupport[TopicPost] {

  override val tableName = "topic_post"
  override val columns = Seq("id", "content", "topic_id")

  val p = TopicPost.syntax("p")

  def apply(p: ResultName[TopicPost])(rs: WrappedResultSet): TopicPost =
    TopicPost(rs.long(p.id), rs.string(p.content), rs.long(p.topic_id))

  def create(content: String, topicId: Long)(implicit session: DBSession = autoSession): TopicPost = {
    val id = withSQL {
      insert.into(TopicPost).namedValues(
        column.content -> content,
        column.topic_id -> topicId
      )
    }.updateAndReturnGeneratedKey.apply()
    TopicPost(id = id, content = content, topic_id = topicId)
  }

  def find(id: Long)(implicit session: DBSession = autoSession): Option[TopicPost] = {
    withSQL { select.from(TopicPost as p).where.eq(p.id, id) }
      .map(TopicPost(p.resultName)).single.apply()
  }

  def findByTopicId(topicId: Long)(implicit session: DBSession = autoSession): List[TopicPost] = {
    withSQL { select.from(TopicPost as p).where.eq(p.topic_id, topicId) }
      .map(TopicPost(p.resultName)).list.apply()
  }

  def deleteByTopicId(topicId: Long)(implicit session: DBSession = autoSession) {
    withSQL {
      delete.from(TopicPost).where.eq(TopicPost.column.topic_id, topicId)
    }.update.apply()
  }
}

