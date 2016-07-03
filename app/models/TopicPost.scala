package models

import scalikejdbc._

case class TopicPost(id: Long, content: String, topic_id: Long)

object TopicPost extends SQLSyntaxSupport[TopicPost]{

  override val tableName = "topic_post"
  override val columns = Seq("id", "content", "topic_id")

  val p = TopicPost.syntax("p")

  //def apply(rs: WrappedResltSet) = new TopicPost(rs.long("id"), rs.string("content"), rs.long("topic_id"))
  def apply(p: ResultName[TopicPost])(rs: WrappedResultSet) = new TopicPost(rs.long(p.id), rs.string(p.content), rs.long(p.topic_id))
  /*def apply(p: ResultName[TopicPost], t: ResultName[Topic])(rs: WrappedResultSet) = {
    apply(p)(rs).copy(topic = rs.longOpt(t.id).map(_ => Topic(t)(rs)))
  }*/
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

  def findByTopicId(topic_id: Long)(implicit session: DBSession = autoSession):
    List[TopicPost] = {
      withSQL { select.from(TopicPost as p).where.eq(p.topic_id, topic_id) }
        .map { rs => TopicPost(
          id = rs.long(p.resultName.id),
          content = rs.string(p.resultName.content),
          topic_id = rs.long(p.resultName.topic_id)
        )
        }.list.apply()
  }
}

