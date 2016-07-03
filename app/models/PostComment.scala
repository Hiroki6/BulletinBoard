package models

import scalikejdbc._

case class PostComment(id: Long, content: String, post_id: Long)

object PostComment extends SQLSyntaxSupport[PostComment]{

  override val tableName = "post_comment"
  override val columns = Seq("id", "content", "post_id")

  val c = PostComment.syntax("c")

  def apply(p: ResultName[PostComment])(rs: WrappedResultSet) = new PostComment(rs.long(p.id), rs.string(p.content), rs.long(p.post_id))
  def create(content: String, post_id: Long)(implicit session: DBSession = autoSession):
  PostComment = {
    val id = withSQL {
      insert.into(PostComment).namedValues(
        column.content -> content,
        column.post_id -> post_id
      )
    }.updateAndReturnGeneratedKey.apply()
    PostComment(id = id, content = content, post_id = post_id)
  }

  def findByPostId(post_id: Long)(implicit session: DBSession = autoSession):
  List[PostComment] = {
    withSQL { select.from(PostComment as c).where.eq(c.post_id, post_id) }
      .map { rs => PostComment(
        id = rs.long(c.resultName.id),
        content = rs.string(c.resultName.content),
        post_id = rs.long(c.resultName.post_id)
      )
      }.list.apply()
  }
}

