package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import models._

@Singleton
class PostCommentController @Inject() (implicit webJarAssets: WebJarAssets, val messagesApi: MessagesApi) extends Controller with I18nSupport{

  val commentForm = Form(
    mapping(
      "新規コメント" -> text
    )(CreateForm.apply)(CreateForm.unapply)
  )

  def show(id: Long) = Action {
    val res = "トピック"
    TopicPost.find(id) match {
      case Some(topicPost) => Ok(views.html.post(res, commentForm, webJarAssets, topicPost, PostComment.findByPostId(id)))
      case None => NotFound("Not Found")
    }
  }

  def create(id: Long) = Action { implicit request =>
    commentForm.bindFromRequest.fold(
      error => BadRequest(commentForm.bindFromRequest.error("新規コメント").get.message),
      form => {
        PostComment.create(content = form.content, post_id = id)
        Redirect(routes.PostCommentController.show(id))
      }
    )
  }
}