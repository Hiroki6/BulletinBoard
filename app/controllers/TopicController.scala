package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, Lang, Messages, MessagesApi}
import models._

case class CreateForm(name: String)
@Singleton
class TopicController @Inject() (implicit webJarAssets: WebJarAssets, val messagesApi: MessagesApi) extends Controller with I18nSupport{

  val createForm = Form(
    mapping(
      "name" -> text
    )(CreateForm.apply)(CreateForm.unapply)
  )

  def index = Action{
    val res = "トピック一覧"
    Ok(views.html.index(res, webJarAssets, Topic.findAll))
  }

  def show(id: Int) = Action{
    val res = "トピック"
    Topic.find(id) match {
      case Some(topic) => Ok(views.html.showTopic(res, webJarAssets, topic))
      case None => NotFound("Not Found")
    }
  }

  def createFormView = Action {
    val res = "トピックの作成"
    Ok(views.html.create(res, createForm, webJarAssets))
  }

  def create = Action { implicit request =>
    createForm.bindFromRequest.fold(
      error => {
        BadRequest("Error")
      },
      {
        case CreateForm(n) => {
          Topic.create(n)
          Redirect(routes.TopicController.index)
        }
      }
    )
  }

}