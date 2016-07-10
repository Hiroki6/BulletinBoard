package models

import scalikejdbc._

case class PostComment(id: Long, content: String, post_id: Long)

object PostComment extends SQLSyntaxSupport[PostComment] {

  override val tableName = "post_comment"
  override val columns = Seq("id", "content", "post_id")

  val c = PostComment.syntax("c")
  val p = TopicPost.p

  def apply(c: ResultName[PostComment])(rs: WrappedResultSet): PostComment =
    PostComment(rs.long(c.id), rs.string(c.content), rs.long(c.post_id))

  def create(content: String, post_id: Long)(implicit session: DBSession = autoSession): PostComment = {
    val id = withSQL {
      insert.into(PostComment).namedValues(
        column.content -> content,
        column.post_id -> post_id
      )
    }.updateAndReturnGeneratedKey.apply()
    PostComment(id = id, content = content, post_id = post_id)
  }

  def findByPostId(post_id: Long)(implicit session: DBSession = autoSession): List[PostComment] = {
    withSQL { select.from(PostComment as c).where.eq(c.post_id, post_id) }
      .map(PostComment(c.resultName)).list.apply()
  }

  def countByTopicId(topicId: Long)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(PostComment as c).where.in(c.post_id,
        (select(p.result.id).from(TopicPost as p).where.eq(p.topic_id, topicId)))
    }.map(_.long(1)).single.apply().get
  }
}

