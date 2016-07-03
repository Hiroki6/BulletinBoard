package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import models._

case class CreateForm(content: String)
@Singleton
class TopicController @Inject() (implicit webJarAssets: WebJarAssets, val messagesApi: MessagesApi) extends Controller with I18nSupport{

  val topicForm = Form(
    mapping(
      "新規トピック" -> text
    )(CreateForm.apply)(CreateForm.unapply)
  )

  def createTopic = Action {
    val res = "トピックの作成"
    Ok(views.html.createTopic(res, topicForm, webJarAssets))
  }

  def create = Action { implicit request =>
    topicForm.bindFromRequest.fold(
      error => BadRequest(topicForm.bindFromRequest.error("name").get.message),
      form => {
        val topic = Topic.create(name = form.content)
        Redirect(routes.TopicPostController.show(topic.id))
      }
    )
  }

}