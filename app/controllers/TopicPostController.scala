package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import models._

case class PostForm(content: String)
@Singleton
class TopicPostController @Inject() (implicit webJarAssets: WebJarAssets, val messagesApi: MessagesApi) extends Controller with I18nSupport{

  val postForm = Form(
    mapping(
      "新規投稿" -> text
    )(PostForm.apply)(PostForm.unapply)
  )

  /*def show(id: Long) = Action {
    val res = "トピック"
    Topic.find(id) match {
      case Some(topic) => Ok(views.html.topic(res, postForm, webJarAssets, topic))
      case None => NotFound("Not Found")
    }
  }*/

  def show(id: Long) = Action {
    val res = "トピック"
    Topic.find(id) match {
      case Some(topic) => Ok(views.html.topic(res, postForm, webJarAssets, topic, TopicPost.findByTopicId(id)))
      case None => NotFound("Not Found")
    }
  }

  def create(id: Long) = Action { implicit request =>
   postForm.bindFromRequest.fold(
      error => BadRequest(postForm.bindFromRequest.error("新規投稿").get.message),
      form => {
        TopicPost.create(content = form.content, topic_id = id)
        Redirect(routes.TopicPostController.show(id))
      }
    )
  }
}