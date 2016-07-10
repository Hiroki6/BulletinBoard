package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{ I18nSupport, MessagesApi }
import models._

case class TopicForm(name: String)
@Singleton
class TopicController @Inject() (implicit webJarAssets: WebJarAssets, val messagesApi: MessagesApi) extends Controller with I18nSupport {

  val topicForm = Form(
    mapping(
      "new topic" -> nonEmptyText
    )(TopicForm.apply)(TopicForm.unapply)
  )

  def createTopic = Action {
    val res = "トピックの作成"
    Ok(views.html.createTopic(res, topicForm, webJarAssets))
  }

  def create = Action { implicit request =>
    topicForm.bindFromRequest.fold(
      error => BadRequest(topicForm.bindFromRequest.error("new topic").get.message),
      form => {
        val topic = Topic.create(name = form.name)
        Redirect(routes.TopicPostController.show(topic.id))
      }
    )
  }

  def delete(id: Long) = Action {
    PostComment.countByTopicId(id) match {
      case 0 => {
        TopicPost.deleteByTopicId(id)
        Topic.delete(id)
        Redirect(routes.HomeController.index)
      }
      case _ => BadRequest("このトピックにコメントがあるため削除できません")
    }

  }

}