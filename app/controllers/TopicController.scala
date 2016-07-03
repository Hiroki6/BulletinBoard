package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import models._

case class TopicForm(name: String)
@Singleton
class TopicController @Inject() (implicit webJarAssets: WebJarAssets, val messagesApi: MessagesApi) extends Controller with I18nSupport{

  val topicForm = Form(
    mapping(
      "name" -> text
    )(TopicForm.apply)(TopicForm.unapply)
  )

  /*def index = Action {
    val res = "トピック一覧"
    Ok(views.html.index(res, webJarAssets, Topic.findAll))
  }*/

  def createTopic = Action {
    val res = "トピックの作成"
    Ok(views.html.createTopic(res, topicForm, webJarAssets))
  }
  /*def show(id: Long) = Action {
    val res = "トピック"
    Topic.find(id) match {
      case Some(topic) => Ok(views.html.topic(res, postForm, webJarAssets, topic))
      case None => NotFound("Not Found")
    }
  }*/

  def create = Action { implicit request =>
    topicForm.bindFromRequest.fold(
      error => BadRequest(topicForm.bindFromRequest.error("name").get.message),
      form => {
        val topic = Topic.create(name = form.name)
        Redirect(routes.TopicPostController.show(topic.id))
      }
    )
  }

}