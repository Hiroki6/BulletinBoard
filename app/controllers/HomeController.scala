package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import models._

@Singleton
class HomeController @Inject() (implicit webJarAssets: WebJarAssets) extends Controller {

  def index = Action {
    val res = "トピック一覧"
    Ok(views.html.index(res, Topic.findAll, webJarAssets))
  }

}