/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers.sections.setup

import config.FrontendAppConfig
import config.featureSwitch.FeatureSwitching
import connectors.DataCacheConnector
import controllers.BaseNavigationController
import controllers.actions._
import forms.AboutYouFormProvider
import javax.inject.Inject
import models.WhatDoYouWantToFindOut.IR35
import models._
import models.requests.DataRequest
import navigation.SetupNavigator
import pages.sections.setup.{WhatDoYouWantToFindOutPage, WhoAreYouPage}
import play.api.data.Form
import play.api.mvc._
import services.{CompareAnswerService, DecisionService}
import views.html.sections.setup.WhoAreYouView

import scala.concurrent.Future

class WhoAreYouController @Inject()(identify: IdentifierAction,
                                    getData: DataRetrievalAction,
                                    requireData: DataRequiredAction,
                                    aboutYouFormProvider: AboutYouFormProvider,
                                    controllerComponents: MessagesControllerComponents,
                                    view: WhoAreYouView,
                                    compareAnswerService: CompareAnswerService,
                                    dataCacheConnector: DataCacheConnector,
                                    decisionService: DecisionService,
                                    navigator: SetupNavigator,
                                    implicit val appConfig: FrontendAppConfig) extends BaseNavigationController(controllerComponents,
  compareAnswerService, dataCacheConnector, navigator, decisionService) with FeatureSwitching {

  val form: Form[AboutYouAnswer] = aboutYouFormProvider()

  private def renderedView(mode: Mode, form: Form[AboutYouAnswer] = form)(implicit request: DataRequest[_]) = {
    val showAgency = request.userAnswers.getAnswer(WhatDoYouWantToFindOutPage) match {
      case Some(IR35) => true
      case _ => false
    }
    view(routes.WhoAreYouController.onSubmit(mode), fillForm(WhoAreYouPage, form), mode, showAgency)
  }

  def onPageLoad(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData) { implicit request =>
    Ok(renderedView(mode))
  }

  def onSubmit(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    form.bindFromRequest().fold(
      formWithErrors => Future.successful(BadRequest(renderedView(mode, formWithErrors))),
      value => redirect(mode, value, WhoAreYouPage)
    )
  }
}